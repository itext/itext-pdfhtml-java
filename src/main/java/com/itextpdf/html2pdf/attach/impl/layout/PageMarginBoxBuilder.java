/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.page.PageMarginRunningElementNode;
import com.itextpdf.html2pdf.css.resolve.DefaultCssResolver;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.AreaBreakRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import com.itextpdf.styledxmlparser.css.CssRuleName;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.node.ITextNode;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// implementation of algorithm specified in https://drafts.csswg.org/css-page-3/#margin-dimension
class PageMarginBoxBuilder {

    IRenderer[] renderers;
    PageMarginBoxContextNode[] nodes;
    float[] margins;
    PageSize pageSize;

    List<PageMarginBoxContextNode> resolvedPageMarginBoxes;

    private static final float EPSILON = 0.00001f;

    public PageMarginBoxBuilder(List<PageMarginBoxContextNode> resolvedPageMarginBoxes, float[] margins, PageSize pageSize) {
        this.resolvedPageMarginBoxes = resolvedPageMarginBoxes;
        this.margins = margins;
        this.pageSize = pageSize;
        Rectangle[] marginBoxRectangles = calculateMarginBoxRectanglesCornersOnly();
        for (PageMarginBoxContextNode marginBoxContentNode : this.resolvedPageMarginBoxes) {
            if (marginBoxContentNode.childNodes().isEmpty()) {
                // margin box node shall not be added to resolvedPageMarginBoxes if it's kids were not resolved from content
                throw new IllegalStateException();
            }

            int marginBoxInd = mapMarginBoxNameToIndex(marginBoxContentNode.getMarginBoxName());
            if (marginBoxRectangles[marginBoxInd] != null)
                marginBoxContentNode.setPageMarginBoxRectangle(new Rectangle(marginBoxRectangles[marginBoxInd]).increaseHeight(EPSILON));
            marginBoxContentNode.setContainingBlockForMarginBox(calculateContainingBlockSizesForMarginBox(marginBoxInd, marginBoxRectangles[marginBoxInd]));
        }
    }

    public void buildForSinglePage(int pageNumber, PdfDocument pdfDocument, DocumentRenderer documentRenderer, ProcessorContext context) {
        if (resolvedPageMarginBoxes.isEmpty()) {
            return;
        }

        nodes = new PageMarginBoxContextNode[16];
        for (PageMarginBoxContextNode marginBoxContentNode : resolvedPageMarginBoxes) {
            nodes[mapMarginBoxNameToIndex(marginBoxContentNode.getMarginBoxName())] = marginBoxContentNode;
        }

        IElement[] elements = new IElement[16];
        for (int i = 0; i < 16; i++) {
            if (nodes[i] != null) {
                elements[i] = processMarginBoxContent(nodes[i], pageNumber, context);
            }
        }

        getPMBRenderers(elements, documentRenderer, pdfDocument);
    }

    public IRenderer[] getRenderers() {
        return renderers;
    }

    public PageMarginBoxContextNode[] getNodes() {
        return nodes;
    }

    private IElement processMarginBoxContent(PageMarginBoxContextNode marginBoxContentNode, int pageNumber, ProcessorContext context) {
        marginBoxContentNode.setStyles(marginBoxContentNode.getStyles());
        DefaultCssResolver cssResolver = new DefaultCssResolver(marginBoxContentNode, context);
        ITagWorker marginBoxWorker = context.getTagWorkerFactory().getTagWorker(marginBoxContentNode, context);
        for (int i = 0; i < marginBoxContentNode.childNodes().size(); i++) {
            INode childNode = marginBoxContentNode.childNodes().get(i);
            if (childNode instanceof ITextNode) {
                String text = ((ITextNode) marginBoxContentNode.childNodes().get(i)).wholeText();
                marginBoxWorker.processContent(text, context);
            } else if (childNode instanceof IElementNode) {
                ITagWorker childTagWorker = context.getTagWorkerFactory().getTagWorker((IElementNode) childNode, context);
                if (childTagWorker != null) {
                    Map<String, String> stringStringMap = cssResolver.resolveStyles(childNode, context.getCssContext());
                    ((IElementNode) childNode).setStyles(stringStringMap);
                    ICssApplier cssApplier = context.getCssApplierFactory().getCssApplier((IElementNode) childNode);
                    if (cssApplier != null) {
                        cssApplier.apply(context, (IStylesContainer) childNode, childTagWorker);
                    }

                    childTagWorker.processEnd((IElementNode) childNode, context);
                    marginBoxWorker.processTagChild(childTagWorker, context);
                }
            } else if (childNode instanceof PageMarginRunningElementNode) {
                PageMarginRunningElementNode runningElementNode = (PageMarginRunningElementNode) childNode;
                RunningElementContainer runningElement = context.getCssContext().getRunningManager()
                        .getRunningElement(runningElementNode.getRunningElementName(), runningElementNode.getRunningElementOccurrence(), pageNumber);
                if (runningElement != null) {
                    marginBoxWorker.processTagChild(runningElement.getProcessedElementWorker(), context);
                }
            } else {
                LoggerFactory.getLogger(this.getClass()).error(Html2PdfLogMessageConstant.UNKNOWN_MARGIN_BOX_CHILD);
            }
        }

        marginBoxWorker.processEnd(marginBoxContentNode, context);

        if (!(marginBoxWorker.getElementResult() instanceof IElement)) {
            throw new IllegalStateException("Custom tag worker implementation for margin boxes shall return IElement for #getElementResult() call.");
        }

        ICssApplier cssApplier = context.getCssApplierFactory().getCssApplier(marginBoxContentNode);
        cssApplier.apply(context, marginBoxContentNode, marginBoxWorker);

        return (IElement) marginBoxWorker.getElementResult();
    }

    private void getPMBRenderers(IElement[] elements, DocumentRenderer documentRenderer, PdfDocument pdfDocument) {
        renderers = new IRenderer[16];
        for (int i = 0; i < 4; i++) {
            renderers[i * 4] = createCornerRenderer(elements[i * 4], documentRenderer, pdfDocument, i);
            for (int j = 1; j <= 3; j++) {
                renderers[i * 4 + j] = createRendererFromElement(elements[i * 4 + j], documentRenderer, pdfDocument);
            }
            determineSizes(i);
        }
    }

    private IRenderer createCornerRenderer(IElement cornerBoxElement, DocumentRenderer documentRenderer, PdfDocument pdfDocument, int indexOfCorner) {
        IRenderer cornerRenderer = createRendererFromElement(cornerBoxElement, documentRenderer, pdfDocument);
        if (cornerRenderer != null) {
            float rendererWidth =
                    margins[indexOfCorner % 3 == 0 ? 3 : 1]
                            - getSizeOfOneSide(cornerRenderer, Property.MARGIN_LEFT,
                            Property.BORDER_LEFT, Property.PADDING_LEFT)
                            - getSizeOfOneSide(cornerRenderer, Property.MARGIN_RIGHT,
                            Property.BORDER_RIGHT, Property.PADDING_RIGHT);

            float rendererHeight =
                    margins[indexOfCorner > 1 ? 2 : 0]
                            - getSizeOfOneSide(cornerRenderer, Property.MARGIN_TOP,
                            Property.BORDER_TOP, Property.PADDING_TOP)
                            - getSizeOfOneSide(cornerRenderer, Property.MARGIN_BOTTOM,
                            Property.BORDER_BOTTOM, Property.PADDING_BOTTOM);

            cornerRenderer.setProperty(Property.WIDTH, UnitValue.createPointValue(rendererWidth));
            cornerRenderer.setProperty(Property.HEIGHT, UnitValue.createPointValue(rendererHeight));

            return cornerRenderer;
        }

        return null;
    }

    private IRenderer createRendererFromElement(IElement element, DocumentRenderer documentRenderer, PdfDocument pdfDocument) {
        if (element != null) {
            IRenderer renderer = element.createRendererSubTree();
            removeAreaBreaks(renderer);
            renderer.setParent(documentRenderer);
            if (pdfDocument.isTagged()) {
                LayoutTaggingHelper taggingHelper = renderer.<LayoutTaggingHelper>getProperty(Property.TAGGING_HELPER);
                LayoutTaggingHelper.addTreeHints(taggingHelper, renderer);
            }
            return renderer;
        }
        return null;
    }

    /**
     * Gets rid of all page breaks that might have occurred inside page margin boxes because of the running elements.
     *
     * @param renderer root renderer of renderers subtree
     */
    private static void removeAreaBreaks(IRenderer renderer) {
        List<IRenderer> areaBreaks = null;
        for (IRenderer child : renderer.getChildRenderers()) {
            if (child instanceof AreaBreakRenderer) {
                if (areaBreaks == null) {
                    areaBreaks = new ArrayList<>();
                }
                areaBreaks.add(child);
            } else {
                removeAreaBreaks(child);
            }
        }
        if (areaBreaks != null) {
            renderer.getChildRenderers().removeAll(areaBreaks);
        }
    }

    private void determineSizes(int side) {
        float[][] marginsBordersPaddingsWidths = new float[3][4];
        for (int i = 0; i < 3; i++) {
            if (renderers[side * 4 + i + 1] != null) {
                marginsBordersPaddingsWidths[i][0] = getSizeOfOneSide(renderers[side * 4 + i + 1], Property.MARGIN_TOP, Property.BORDER_TOP, Property.PADDING_TOP);
                marginsBordersPaddingsWidths[i][1] = getSizeOfOneSide(renderers[side * 4 + i + 1], Property.MARGIN_RIGHT, Property.BORDER_RIGHT, Property.PADDING_RIGHT);
                marginsBordersPaddingsWidths[i][2] = getSizeOfOneSide(renderers[side * 4 + i + 1], Property.MARGIN_BOTTOM, Property.BORDER_BOTTOM, Property.PADDING_BOTTOM);
                marginsBordersPaddingsWidths[i][3] = getSizeOfOneSide(renderers[side * 4 + i + 1], Property.MARGIN_LEFT, Property.BORDER_LEFT, Property.PADDING_LEFT);
            }
        }
        Rectangle withoutMargins = pageSize.clone().applyMargins(margins[0], margins[1], margins[2], margins[3], false);
        Map<String, PageMarginBoxContextNode> resolvedPMBMap = new HashMap<>();
        for (int i = side * 4 + 1; i < side * 4 + 4; i++) {
            if (nodes[i] != null) {
                resolvedPMBMap.put(nodes[i].getMarginBoxName(), nodes[i]);
            }
        }
        DimensionContainer[] dims = new DimensionContainer[3];
        String[] cssRuleName = getRuleNames(side);
        float withoutMarginsWidthOrHeight = side % 2 == 0 ? withoutMargins.getWidth() : withoutMargins.getHeight();
        for (int i = 0; i < 3; i++)
            if (side % 2 == 0) {
                dims[i] = retrievePageMarginBoxWidths(resolvedPMBMap.get(cssRuleName[i]), renderers[side * 4 + i + 1], withoutMarginsWidthOrHeight,
                        marginsBordersPaddingsWidths[i][1] + marginsBordersPaddingsWidths[i][3]);
            } else {
                dims[i] = retrievePageMarginBoxHeights(resolvedPMBMap.get(cssRuleName[i]), renderers[side * 4 + i + 1], margins[side],
                        withoutMarginsWidthOrHeight, marginsBordersPaddingsWidths[i][0] + marginsBordersPaddingsWidths[i][2]);

            }

        float centerOrMiddleCoord, widthOrHeightResults[];
        widthOrHeightResults = calculatePageMarginBoxDimensions(dims[0], dims[1], dims[2], withoutMarginsWidthOrHeight);
        if (side % 2 == 0) {
            centerOrMiddleCoord = getStartCoordForCenterOrMiddleBox(withoutMarginsWidthOrHeight,
                    widthOrHeightResults[1], withoutMargins.getLeft());
        } else {
            centerOrMiddleCoord = getStartCoordForCenterOrMiddleBox(withoutMarginsWidthOrHeight,
                    widthOrHeightResults[1], withoutMargins.getBottom());
        }

        Rectangle[] result = getRectangles(side, withoutMargins, centerOrMiddleCoord, widthOrHeightResults);
        for (int i = 0; i < 3; i++)
            if (nodes[side * 4 + i + 1] != null) {
                nodes[side * 4 + i + 1].setPageMarginBoxRectangle(new Rectangle(result[i]).increaseHeight(EPSILON));
                UnitValue width = UnitValue.createPointValue(result[i].getWidth() - marginsBordersPaddingsWidths[i][1] - marginsBordersPaddingsWidths[i][3]);
                UnitValue height = UnitValue.createPointValue(result[i].getHeight() - marginsBordersPaddingsWidths[i][0] - marginsBordersPaddingsWidths[i][2]);
                if (Math.abs(width.getValue()) < EPSILON || Math.abs(height.getValue()) < EPSILON) {
                    renderers[side * 4 + i + 1] = null;
                } else {
                    renderers[side * 4 + i + 1].setProperty(Property.WIDTH, width);
                    renderers[side * 4 + i + 1].setProperty(Property.HEIGHT, height);
                }
            }
    }

    private String[] getRuleNames(int side) {
        switch (side) {
            case 0:
                return new String[]{CssRuleName.TOP_LEFT, CssRuleName.TOP_CENTER, CssRuleName.TOP_RIGHT};
            case 1:
                return new String[]{CssRuleName.RIGHT_TOP, CssRuleName.RIGHT_MIDDLE, CssRuleName.RIGHT_BOTTOM};
            case 2:
                return new String[]{CssRuleName.BOTTOM_RIGHT, CssRuleName.BOTTOM_CENTER, CssRuleName.BOTTOM_LEFT};
            case 3:
                return new String[]{CssRuleName.LEFT_BOTTOM, CssRuleName.LEFT_MIDDLE, CssRuleName.LEFT_TOP};
        }
        return new String[3];
    }

    private Rectangle[] getRectangles(int side, Rectangle withoutMargins, float centerOrMiddleCoord, float[] results) {
        switch (side) {
            case 0:
                return new Rectangle[]{new Rectangle(withoutMargins.getLeft(), withoutMargins.getTop(),
                        results[0], margins[0]),
                        new Rectangle(centerOrMiddleCoord, withoutMargins.getTop(),
                                results[1], margins[0]),
                        new Rectangle(withoutMargins.getRight() - results[2], withoutMargins.getTop(),
                                results[2], margins[0])};
            case 1:
                return new Rectangle[]{
                        new Rectangle(withoutMargins.getRight(), withoutMargins.getTop() - results[0],
                                margins[1], results[0]),
                        new Rectangle(withoutMargins.getRight(), centerOrMiddleCoord, margins[1],
                                results[1]),
                        new Rectangle(withoutMargins.getRight(), withoutMargins.getBottom(),
                                margins[1], results[2])
                };
            case 2:
                return new Rectangle[]{
                        new Rectangle(withoutMargins.getRight() - results[0], 0,
                                results[0], margins[2]),
                        new Rectangle(centerOrMiddleCoord, 0, results[1], margins[2]),
                        new Rectangle(withoutMargins.getLeft(), 0, results[2], margins[2])
                };
            case 3:
                return new Rectangle[]{
                        new Rectangle(0, withoutMargins.getBottom(), margins[3], results[0]),
                        new Rectangle(0, centerOrMiddleCoord, margins[3], results[1]),
                        new Rectangle(0, withoutMargins.getTop() - results[2],
                                margins[3], results[2])
                };

        }
        return new Rectangle[3];
    }

    private float getSizeOfOneSide(IRenderer renderer, int marginProperty, int borderProperty, int paddingProperty) {
        float marginWidth = 0, paddingWidth = 0, borderWidth = 0;

        UnitValue temp = renderer.<UnitValue>getProperty(marginProperty);
        if (null != temp) {
            marginWidth = temp.getValue();
        }

        temp = renderer.<UnitValue>getProperty(paddingProperty);
        if (null != temp) {
            paddingWidth = temp.getValue();
        }

        Border border = renderer.<Border>getProperty(borderProperty);
        if (null != border) {
            borderWidth = border.getWidth();
        }
        return marginWidth + paddingWidth + borderWidth;
    }

    private DimensionContainer retrievePageMarginBoxWidths(PageMarginBoxContextNode pmbcNode, IRenderer renderer, float maxWidth, float additionalWidthFix) {
        if (pmbcNode == null) {
            return null;
        } else {
            return new WidthDimensionContainer(pmbcNode, maxWidth, renderer, additionalWidthFix);
        }
    }

    private DimensionContainer retrievePageMarginBoxHeights(PageMarginBoxContextNode pmbcNode, IRenderer renderer, float marginWidth, float maxHeight, float additionalHeightFix) {
        if (pmbcNode == null) {
            return null;
        } else {
            return new HeightDimensionContainer(pmbcNode, marginWidth, maxHeight, renderer, additionalHeightFix);
        }
    }

    /**
     * See the algorithm detailed at https://www.w3.org/TR/css3-page/#margin-dimension
     * Divide the available dimension along the A,B and C according to their properties.
     *
     * @param dimA               object containing the dimension-related properties of A
     * @param dimB               object containing the dimension-related properties of B
     * @param dimC               object containing the dimension-related properties of C
     * @param availableDimension maximum available dimension that can be taken up
     * @return float[3] containing the distributed dimensions of A at [0], B at [1] and C at [2]
     */
    private float[] calculatePageMarginBoxDimensions(DimensionContainer dimA, DimensionContainer dimB, DimensionContainer dimC, float availableDimension) {
        float maxContentDimensionA = 0, minContentDimensionA = 0,
                maxContentDimensionB = 0, minContentDimensionB = 0,
                maxContentDimensionC = 0, minContentDimensionC = 0;
        float[] dimensions = new float[3];

        if (isContainerEmpty(dimA) && isContainerEmpty(dimB) && isContainerEmpty(dimC)) {
            return dimensions;
        }

        //Calculate widths
        //Check if B is present
        if (isContainerEmpty(dimB)) {
            //Single box present
            if (isContainerEmpty(dimA)) {
                if (dimC.isAutoDimension()) {
                    //Allocate everything to C
                    dimensions[2] = availableDimension;
                } else {
                    dimensions[2] = dimC.dimension;
                }
            } else if (isContainerEmpty(dimC)) {
                if (dimA.isAutoDimension()) {
                    //Allocate everything to A
                    dimensions[0] = availableDimension;
                } else {
                    dimensions[0] = dimA.dimension;
                }
            } else if (dimA.isAutoDimension() && dimC.isAutoDimension()) {
                //Gather input
                maxContentDimensionA = dimA.maxContentDimension;
                minContentDimensionA = dimA.minContentDimension;
                maxContentDimensionC = dimC.maxContentDimension;
                minContentDimensionC = dimC.minContentDimension;

                float[] distributedWidths =
                        distributeDimensionBetweenTwoBoxes(maxContentDimensionA, minContentDimensionA, maxContentDimensionC, minContentDimensionC, availableDimension);
                dimensions = new float[]{distributedWidths[0], 0f, distributedWidths[1]};
            } else {
                if (!dimA.isAutoDimension()) {
                    dimensions[0] = dimA.dimension;
                } else {
                    dimensions[0] = availableDimension - dimC.dimension;
                }
                if (!dimC.isAutoDimension()) {
                    dimensions[2] = dimC.dimension;
                } else {
                    dimensions[2] = availableDimension - dimA.dimension;
                }
            }
        } else {
            //Check for edge cases
            if (!isContainerEmpty(dimA)) {
                if (dimA.isAutoDimension()) {
                    maxContentDimensionA = dimA.maxContentDimension;
                    minContentDimensionA = dimA.minContentDimension;
                } else {
                    maxContentDimensionA = dimA.dimension;
                    minContentDimensionA = dimA.dimension;
                }
            }
            if (!isContainerEmpty(dimC)) {
                if (dimC.isAutoDimension()) {
                    maxContentDimensionC = dimC.maxContentDimension;
                    minContentDimensionC = dimC.minContentDimension;
                } else {
                    maxContentDimensionC = dimC.dimension;
                    minContentDimensionC = dimC.dimension;
                }
            }
            if (dimB.isAutoDimension()) {
                //Construct box AC
                float maxContentWidthAC, minContentWidthAC;

                maxContentWidthAC = 2 * Math.max(maxContentDimensionA, maxContentDimensionC);
                minContentWidthAC = 2 * Math.max(minContentDimensionA, minContentDimensionC);

                //Determine width box B
                maxContentDimensionB = dimB.maxContentDimension;
                minContentDimensionB = dimB.minContentDimension;
                float[] distributedDimensions = distributeDimensionBetweenTwoBoxes(maxContentDimensionB, minContentDimensionB, maxContentWidthAC, minContentWidthAC, availableDimension);

                //Determine width boxes A & C
                float newAvailableDimension = (availableDimension - distributedDimensions[0]) / 2;
                dimensions = new float[]{newAvailableDimension, distributedDimensions[0], newAvailableDimension};
            } else {
                dimensions[1] = dimB.dimension;
                float newAvailableDimension = (availableDimension - dimensions[1]) / 2;

                if (newAvailableDimension > Float.MAX_VALUE - MinMaxWidthUtils.getEps()) {
                    newAvailableDimension = Float.MAX_VALUE - MinMaxWidthUtils.getEps();
                }
                dimensions[0] = Math.min(maxContentDimensionA, newAvailableDimension) + MinMaxWidthUtils.getEps();
                dimensions[2] = Math.min(maxContentDimensionC, newAvailableDimension) + MinMaxWidthUtils.getEps();
            }
            setManualDimension(dimA, dimensions, 0);
            setManualDimension(dimC, dimensions, 2);
        }

        if (recalculateIfNecessary(dimA, dimensions, 0) ||
                recalculateIfNecessary(dimB, dimensions, 1) ||
                recalculateIfNecessary(dimC, dimensions, 2)) {
            return calculatePageMarginBoxDimensions(dimA, dimB, dimC, availableDimension);
        }
        removeNegativeValues(dimensions);
        return dimensions;
    }

    private boolean isContainerEmpty(DimensionContainer container) {
        return container == null || Math.abs(container.maxContentDimension) < EPSILON;
    }

    private void removeNegativeValues(float[] dimensions) {
        for (int i = 0; i < dimensions.length; i++) {
            if (dimensions[i] < 0) {
                dimensions[i] = 0;
            }
        }
    }

    /**
     * Calculate the starting coordinate in a given dimension for a center of middle box
     *
     * @param availableDimension size of the available area
     * @param dimensionResult    the calculated dimensions of the middle (center) box
     * @param offset             offset from the start of the page (page margins and padding included)
     * @return starting coordinate in a given dimension for a center of middle box
     */
    private float getStartCoordForCenterOrMiddleBox(float availableDimension, float dimensionResult, float offset) {
        return offset + (availableDimension - dimensionResult) / 2;
    }

    /**
     * Set the calculated dimension to the manually set dimension in the passed float array
     *
     * @param dim        Dimension Container containing the manually set dimension
     * @param dimensions array of calculated auto values for boxes in the given dimension
     * @param index      position in the array to replace
     */
    private void setManualDimension(DimensionContainer dim, float[] dimensions, int index) {
        if (dim != null && !dim.isAutoDimension()) {
            dimensions[index] = dim.dimension;
        }
    }

    /**
     * Distribute the available dimension between two boxes A and C based on their content-needs.
     * The box with more content will get more space assigned
     *
     * @param maxContentDimensionA maximum of the dimension the content in A occupies
     * @param minContentDimensionA minimum of the dimension the content in A occupies
     * @param maxContentDimensionC maximum of the dimension the content in C occupies
     * @param minContentDimensionC minimum of the dimension the content in C occupies
     * @param availableDimension   maximum available dimension to distribute
     * @return float[2], distributed dimension for A in [0], distributed dimension for B in [1]
     */
    private float[] distributeDimensionBetweenTwoBoxes(float maxContentDimensionA, float minContentDimensionA, float maxContentDimensionC, float minContentDimensionC, float availableDimension) {
        //calculate based on flex space
        //Determine flex factor
        float maxSum = maxContentDimensionA + maxContentDimensionC;
        float minSum = minContentDimensionA + minContentDimensionC;
        if (maxSum < availableDimension) {
            return calculateDistribution(maxContentDimensionA, maxContentDimensionC, maxContentDimensionA, maxContentDimensionC, maxSum, availableDimension);
        } else if (minSum < availableDimension) {
            return calculateDistribution(minContentDimensionA, minContentDimensionC, maxContentDimensionA - minContentDimensionA, maxContentDimensionC - minContentDimensionC,
                    maxSum - minSum, availableDimension);
        }
        return calculateDistribution(minContentDimensionA, minContentDimensionC, minContentDimensionA, minContentDimensionC, minSum, availableDimension);
    }

    private float[] calculateDistribution(float argA, float argC, float flexA, float flexC, float sum, float availableDimension) {
        float flexRatioA, flexRatioC, flexSpace;
        if (CssUtils.compareFloats(sum, 0f)) {
            flexRatioA = 1;
            flexRatioC = 1;
        } else {
            flexRatioA = flexA / sum;
            flexRatioC = flexC / sum;
        }
        flexSpace = availableDimension - (argA + argC);

        return new float[]{argA + flexRatioA * flexSpace, argC + flexRatioC * flexSpace};
    }

    /**
     * Check if a calculated dimension value needs to be recalculated
     *
     * @param dim        Dimension container containing min and max dimension info
     * @param dimensions array of calculated auto values for boxes in the given dimension
     * @param index      position in the array to look at
     * @return <code>true</code> if the values in dimensions trigger a recalculation, <code>false</code> otherwise
     */
    private boolean recalculateIfNecessary(DimensionContainer dim, float[] dimensions, int index) {
        if (dim != null) {
            if (dimensions[index] < dim.minDimension && dim.isAutoDimension()) {
                dim.dimension = dim.minDimension;
                return true;
            }
            if (dimensions[index] > dim.maxDimension && dim.isAutoDimension()) {
                dim.dimension = dim.maxDimension;
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate the margin boxes given the list of margin boxes that have generated content
     *
     * @return Rectangle[12] containing the calculated bounding boxes of the margin-box-nodes. Rectangles with 0 width and/or height
     * refer to empty boxes. The order is TLC(top-left-corner)-TL-TC-TY-TRC-RT-RM-RB-RBC-BR-BC-BL-BLC-LB-LM-LT
     */
    private Rectangle[] calculateMarginBoxRectanglesCornersOnly() {
        float topMargin = margins[0];
        float rightMargin = margins[1];
        float bottomMargin = margins[2];
        float leftMargin = margins[3];
        Rectangle withoutMargins = pageSize.clone().applyMargins(topMargin, rightMargin, bottomMargin, leftMargin, false);
        //Define corner boxes
        Rectangle tlc = new Rectangle(0, withoutMargins.getTop(), leftMargin, topMargin);
        Rectangle trc = new Rectangle(withoutMargins.getRight(), withoutMargins.getTop(), rightMargin, topMargin);
        Rectangle blc = new Rectangle(0, 0, leftMargin, bottomMargin);
        Rectangle brc = new Rectangle(withoutMargins.getRight(), 0, rightMargin, bottomMargin);

        //Group & return results
        Rectangle[] groupedRectangles = new Rectangle[]{
                tlc,
                null,
                null,
                null,
                trc,

                null,
                null,
                null,

                brc,
                null,
                null,
                null,
                blc,

                null,
                null,
                null,

        };
        return groupedRectangles;
    }

    /**
     * Calculate containing block sizes for margin box.
     *
     * @param marginBoxInd           the margin box index
     * @param pageMarginBoxRectangle a {@link Rectangle} defining dimensions of the page margin box corresponding to the given index
     * @return the corresponding rectangle
     */
    private Rectangle calculateContainingBlockSizesForMarginBox(int marginBoxInd, Rectangle pageMarginBoxRectangle) {
        if (marginBoxInd == 0 || marginBoxInd == 4 || marginBoxInd == 8 || marginBoxInd == 12) {
            return pageMarginBoxRectangle;
        }
        Rectangle withoutMargins = pageSize.clone().applyMargins(margins[0], margins[1], margins[2], margins[3], false);
        if (marginBoxInd < 4) {
            return new Rectangle(withoutMargins.getWidth(), margins[0]);
        } else if (marginBoxInd < 8) {
            return new Rectangle(margins[1], withoutMargins.getHeight());
        } else if (marginBoxInd < 12) {
            return new Rectangle(withoutMargins.getWidth(), margins[2]);
        } else {
            return new Rectangle(margins[3], withoutMargins.getHeight());
        }
    }

    /**
     * Maps a margin box name to an index.
     *
     * @param marginBoxName the margin box name
     * @return the index corresponding with the margin box name
     */
    int mapMarginBoxNameToIndex(String marginBoxName) {
        switch (marginBoxName) {
            case CssRuleName.TOP_LEFT_CORNER:
                return 0;
            case CssRuleName.TOP_LEFT:
                return 1;
            case CssRuleName.TOP_CENTER:
                return 2;
            case CssRuleName.TOP_RIGHT:
                return 3;
            case CssRuleName.TOP_RIGHT_CORNER:
                return 4;
            case CssRuleName.RIGHT_TOP:
                return 5;
            case CssRuleName.RIGHT_MIDDLE:
                return 6;
            case CssRuleName.RIGHT_BOTTOM:
                return 7;
            case CssRuleName.BOTTOM_RIGHT_CORNER:
                return 8;
            case CssRuleName.BOTTOM_RIGHT:
                return 9;
            case CssRuleName.BOTTOM_CENTER:
                return 10;
            case CssRuleName.BOTTOM_LEFT:
                return 11;
            case CssRuleName.BOTTOM_LEFT_CORNER:
                return 12;
            case CssRuleName.LEFT_BOTTOM:
                return 13;
            case CssRuleName.LEFT_MIDDLE:
                return 14;
            case CssRuleName.LEFT_TOP:
                return 15;
        }
        return -1;
    }
}
