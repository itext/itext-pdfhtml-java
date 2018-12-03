/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.impl.PageMarginBoxCssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.page.PageMarginRunningElementNode;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.AreaBreakRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import com.itextpdf.styledxmlparser.css.CssRuleName;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.ITextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Context processor for specific types of pages: first, left, or right page.
 */
class PageContextProcessor {

    /**
     * The page size.
     */
    private PageSize pageSize;

    /**
     * Marks for page boundaries.
     */
    private Set<String> marks;

    /**
     * The bleed value for the margin.
     */
    private Float bleed;

    /**
     * The margins.
     */
    private float[] margins;

    /**
     * The borders.
     */
    private Border[] borders;

    /**
     * The paddings.
     */
    private float[] paddings;

    /**
     * page background simulation.
     */
    private Div pageBackgroundSimulation;

    /**
     * page borders simulation.
     */
    private Div pageBordersSimulation;

    private PageContextProperties properties;
    private ProcessorContext context;

    private static final float EPSILON = 0.00001f;

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PageContextProcessor.class);

    /**
     * Instantiates a new page context processor.
     *
     * @param properties         the page context properties
     * @param context            the processor context
     * @param defaultPageSize    the default page size
     * @param defaultPageMargins the default page margins
     */
    PageContextProcessor(PageContextProperties properties, ProcessorContext context, PageSize defaultPageSize, float[] defaultPageMargins) {
        this.properties = properties;
        this.context = context;
        reset(defaultPageSize, defaultPageMargins);
    }

    /**
     * Parses the marks.
     *
     * @param marksStr a {@link String} value defining the marks
     * @return a {@link Set} of mark values
     */
    private static Set<String> parseMarks(String marksStr) {
        Set<String> marks = new HashSet<>();
        if (marksStr == null) {
            return marks;
        }
        String[] split = marksStr.split(" ");
        for (String mark : split) {
            if (CssConstants.CROP.equals(mark) || CssConstants.CROSS.equals(mark)) {
                marks.add(mark);
            } else {
                marks.clear();
                break;
            }
        }
        return marks;
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

    /**
     * Re-initializes page context processor based on default current page size and page margins
     * and on properties from css page at-rules. Css properties priority is higher than default document values.
     *
     * @param defaultPageSize    current default page size to be used if it is not defined in css
     * @param defaultPageMargins current default page margins to be used if they are not defined in css
     * @return this {@link PageContextProcessor} instance
     */
    PageContextProcessor reset(PageSize defaultPageSize, float[] defaultPageMargins) {
        Map<String, String> styles = properties.getResolvedPageContextNode().getStyles();
        float em = CssUtils.parseAbsoluteLength(styles.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();


        pageSize = PageSizeParser.fetchPageSize(styles.get(CssConstants.SIZE), em, rem, defaultPageSize);

        UnitValue bleedValue = CssUtils.parseLengthValueToPt(styles.get(CssConstants.BLEED), em, rem);
        if (bleedValue != null && bleedValue.isPointValue()) {
            bleed = bleedValue.getValue();
        }

        marks = parseMarks(styles.get(CssConstants.MARKS));

        parseMargins(styles, em, rem, defaultPageMargins);
        parseBorders(styles, em, rem);
        parsePaddings(styles, em, rem);
        createPageSimulationElements(styles, context);
        prepareMarginBoxesSizing(properties.getResolvedPageMarginBoxes());

        return this;
    }

    /**
     * Gets the page size.
     *
     * @return the page size
     */
    PageSize getPageSize() {
        return pageSize;
    }

    /**
     * Compute layout margins.
     *
     * @return the float values of the margins
     */
    float[] computeLayoutMargins() {
        float[] layoutMargins = Arrays.copyOf(margins, margins.length);
        for (int i = 0; i < borders.length; ++i) {
            float width = borders[i] != null ? borders[i].getWidth() : 0;
            layoutMargins[i] += width;
        }
        for (int i = 0; i < paddings.length; ++i) {
            layoutMargins[i] += paddings[i];
        }
        return layoutMargins;
    }

    /**
     * Finalizes page processing by drawing margins if necessary.
     *
     * @param pageNum          the page to process
     * @param pdfDocument      the {@link PdfDocument} to which content is written
     * @param documentRenderer the document renderer
     */
    void processPageEnd(int pageNum, PdfDocument pdfDocument, DocumentRenderer documentRenderer) {
        drawMarginBoxes(pageNum, pdfDocument, documentRenderer);
    }

    /**
     * Processes a new page by setting the bleed value, adding marks, drawing
     * page backgrounds and borders.
     *
     * @param page the page to process
     */
    void processNewPage(PdfPage page) {
        setBleed(page);
        drawMarks(page);
        drawPageBackgroundAndBorders(page);
    }

    /**
     * Sets the bleed value for a page.
     *
     * @param page the new bleed
     */
    private void setBleed(PdfPage page) {
        if (bleed == null && !marks.isEmpty()) {
            bleed = 6f;
        }
        if (bleed != null) {
            Rectangle box = page.getMediaBox();
            box.increaseHeight((float) bleed * 2);
            box.setWidth(box.getWidth() + (float) bleed * 2);
            page.setMediaBox(box).setBleedBox(box);
            Rectangle trimBox = page.getTrimBox();
            trimBox.moveUp((float) bleed);
            trimBox.moveRight((float) bleed);
            page.setTrimBox(trimBox);
        }
    }

    /**
     * Sets the different page boundaries and draws printer marks on the page
     * (if necessary).
     *
     * @param page the page
     */
    private void drawMarks(PdfPage page) {
        if (marks.isEmpty()) {
            return;
        }

        float horizontalIndent = 48;
        float verticalIndent = 57;

        Rectangle mediaBox = page.getMediaBox();
        mediaBox.increaseHeight(verticalIndent * 2);
        mediaBox.setWidth(mediaBox.getWidth() + horizontalIndent * 2);
        page.setMediaBox(mediaBox);

        Rectangle bleedBox = page.getBleedBox();
        bleedBox.moveUp(verticalIndent);
        bleedBox.moveRight(horizontalIndent);
        page.setBleedBox(bleedBox);

        Rectangle trimBox = page.getTrimBox();
        trimBox.moveUp(verticalIndent);
        trimBox.moveRight(horizontalIndent);
        page.setTrimBox(trimBox);

        PdfCanvas canvas = new PdfCanvas(page);
        if (page.getDocument().isTagged()) {
            canvas.openTag(new CanvasArtifact());
        }
        if (marks.contains(CssConstants.CROP)) {
            float cropLineLength = 24;
            float verticalCropStartIndent = verticalIndent - cropLineLength;
            float horizontalCropStartIndent = horizontalIndent - cropLineLength;
            canvas
                    .saveState()
                    .setLineWidth(0.1f)

                    .moveTo(trimBox.getLeft(), verticalCropStartIndent)
                    .lineTo(trimBox.getLeft(), verticalIndent)
                    .moveTo(horizontalCropStartIndent, trimBox.getTop())
                    .lineTo(horizontalIndent, trimBox.getTop())

                    .moveTo(trimBox.getRight(), verticalCropStartIndent)
                    .lineTo(trimBox.getRight(), verticalIndent)
                    .moveTo(mediaBox.getWidth() - horizontalCropStartIndent, trimBox.getTop())
                    .lineTo(mediaBox.getWidth() - horizontalIndent, trimBox.getTop())


                    .moveTo(trimBox.getLeft(), mediaBox.getHeight() - verticalCropStartIndent)
                    .lineTo(trimBox.getLeft(), mediaBox.getHeight() - verticalIndent)
                    .moveTo(mediaBox.getWidth() - horizontalCropStartIndent, trimBox.getBottom())
                    .lineTo(mediaBox.getWidth() - horizontalIndent, trimBox.getBottom())


                    .moveTo(trimBox.getRight(), mediaBox.getHeight() - verticalCropStartIndent)
                    .lineTo(trimBox.getRight(), mediaBox.getHeight() - verticalIndent)
                    .moveTo(horizontalCropStartIndent, trimBox.getBottom())
                    .lineTo(horizontalIndent, trimBox.getBottom())

                    .stroke()
                    .restoreState();
        }
        if (marks.contains(CssConstants.CROSS)) {
            float horCrossCenterIndent = verticalIndent - 12;
            float verCrossCenterIndent = horizontalIndent - 12;
            float x, y;
            canvas.saveState().setLineWidth(0.1f);

            x = mediaBox.getWidth() / 2;
            y = mediaBox.getHeight() - horCrossCenterIndent;
            drawCross(canvas, x, y, true);

            x = mediaBox.getWidth() / 2;
            y = horCrossCenterIndent;
            drawCross(canvas, x, y, true);

            x = verCrossCenterIndent;
            y = mediaBox.getHeight() / 2;
            drawCross(canvas, x, y, false);

            x = mediaBox.getWidth() - verCrossCenterIndent;
            y = mediaBox.getHeight() / 2;
            drawCross(canvas, x, y, false);

            canvas.restoreState();
        }
        if (page.getDocument().isTagged()) {
            canvas.closeTag();
        }
    }

    /**
     * Draws a cross (used in the {@link #drawMarks(PdfPage)} method).
     *
     * @param canvas          the canvas to draw on
     * @param x               the x value
     * @param y               the y value
     * @param horizontalCross true if horizontal
     */
    private void drawCross(PdfCanvas canvas, float x, float y, boolean horizontalCross) {
        float xLineHalf;
        float yLineHalf;
        float circleR = 6;
        if (horizontalCross) {
            xLineHalf = 30;
            yLineHalf = 12;
        } else {
            xLineHalf = 12;
            yLineHalf = 30;
        }
        canvas
                .moveTo(x - xLineHalf, y)
                .lineTo(x + xLineHalf, y)
                .moveTo(x, y - yLineHalf)
                .lineTo(x, y + yLineHalf);
        canvas.circle(x, y, circleR);

        canvas.stroke();
    }

    /**
     * Draws page background and borders.
     *
     * @param page the page
     */
    private void drawPageBackgroundAndBorders(PdfPage page) {
        Canvas canvas = new Canvas(new PdfCanvas(page), page.getDocument(), page.getBleedBox());
        canvas.enableAutoTagging(page);
        canvas.add(pageBackgroundSimulation);
        canvas.close();
        canvas = new Canvas(new PdfCanvas(page), page.getDocument(), page.getTrimBox());
        canvas.enableAutoTagging(page);
        canvas.add(pageBordersSimulation);
        canvas.close();
    }

    /**
     * Draws margin boxes.
     *
     * @param pageNumber       the page
     * @param pdfDocument      the {@link PdfDocument} to which content is written
     * @param documentRenderer the document renderer
     */
    private void drawMarginBoxes(int pageNumber, PdfDocument pdfDocument, DocumentRenderer documentRenderer) {
        if (properties.getResolvedPageMarginBoxes().isEmpty()) {
            return;
        }
        PdfPage page = pdfDocument.getPage(pageNumber);

        ArrayList<PageMarginBoxContextNode> nodes = new ArrayList<PageMarginBoxContextNode>(Collections.nCopies(16, (PageMarginBoxContextNode) null));
        for (PageMarginBoxContextNode marginBoxContentNode : properties.getResolvedPageMarginBoxes()) {
            nodes.set(mapMarginBoxNameToIndex(marginBoxContentNode.getMarginBoxName()), marginBoxContentNode);
        }

        ArrayList<IElement> elements = new ArrayList<IElement>(Collections.nCopies(16, (IElement) null));
        for (int i = 0; i < 16; i++) {
            if (nodes.get(i) != null) {
                elements.set(i, processMarginBoxContent(nodes.get(i), pageNumber, context));
            }
        }

        ArrayList<IRenderer> renderers = getPMBRenderers(elements, nodes, documentRenderer, pdfDocument);
        for (int i = 0; i < 16; i++)
            if (renderers.get(i) != null)
                draw(renderers.get(i), nodes.get(i), pdfDocument, page, documentRenderer, pageNumber);

    }

    private ArrayList<IRenderer> getPMBRenderers(ArrayList<IElement> elements, ArrayList<PageMarginBoxContextNode> nodes, DocumentRenderer documentRenderer, PdfDocument pdfDocument) {
        ArrayList<IRenderer> renderers = new ArrayList<IRenderer>(Collections.nCopies(16, (IRenderer) null));
        for (int i = 0; i < 4; i++) {
            renderers.set(i * 4, createCornerRenderer(elements.get(i * 4), documentRenderer, pdfDocument, i));
            for (int j = 1; j <= 3; j++) {
                renderers.set(i * 4 + j, createRendererFromElement(elements.get(i * 4 + j), documentRenderer, pdfDocument));
            }
            determineSizes(nodes.subList(i * 4 + 1, i * 4 + 4), renderers.subList(i * 4 + 1, i * 4 + 4), i);
        }
        return renderers;
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

    private void draw(IRenderer renderer, PageMarginBoxContextNode node, PdfDocument pdfDocument, PdfPage page, DocumentRenderer documentRenderer, int pageNumber) {
        LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, node.getPageMarginBoxRectangle())));
        IRenderer rendererToDraw = result.getStatus() == LayoutResult.FULL ? renderer : result.getSplitRenderer();
        if (rendererToDraw != null) {
            TagTreePointer tagPointer = null, backupPointer = null;
            PdfPage backupPage = null;
            if (pdfDocument.isTagged()) {
                tagPointer = pdfDocument.getTagStructureContext().getAutoTaggingPointer();
                backupPage = tagPointer.getCurrentPage();
                backupPointer = new TagTreePointer(tagPointer);
                tagPointer.moveToRoot();
                tagPointer.setPageForTagging(page);
            }

            rendererToDraw.setParent(documentRenderer).draw(new DrawContext(page.getDocument(), new PdfCanvas(page), pdfDocument.isTagged()));

            if (pdfDocument.isTagged()) {
                tagPointer.setPageForTagging(backupPage);
                tagPointer.moveToPointer(backupPointer);
            }
        } else {
            // marginBoxElements have overflow property set to HIDDEN, therefore it is not expected to neither get
            // LayoutResult other than FULL nor get no split renderer (result NOTHING) even if result is not FULL
            LOGGER.error(MessageFormatUtil.format(LogMessageConstant.PAGE_MARGIN_BOX_CONTENT_CANNOT_BE_DRAWN, node.getMarginBoxName()));
        }
    }

    /**
     * Parses the margins.
     *
     * @param styles a {@link Map} containing the styles
     * @param em     a measurement expressed in em
     * @param rem    a measurement expressed in rem (root em)
     */
    private void parseMargins(Map<String, String> styles, float em, float rem, float[] defaultMarginValues) {
        PageSize pageSize = getPageSize();
        margins = PageMarginBoxCssApplier.parseBoxProps(styles, em, rem, defaultMarginValues, pageSize,
                CssConstants.MARGIN_TOP, CssConstants.MARGIN_RIGHT, CssConstants.MARGIN_BOTTOM, CssConstants.MARGIN_LEFT);
    }

    /**
     * Parses the paddings.
     *
     * @param styles a {@link Map} containing the styles
     * @param em     a measurement expressed in em
     * @param rem    a measurement expressed in rem (root em)
     */
    private void parsePaddings(Map<String, String> styles, float em, float rem) {
        float defaultPadding = 0;
        PageSize pageSize = getPageSize();
        paddings = PageMarginBoxCssApplier.parseBoxProps(styles, em, rem, new float[]{defaultPadding, defaultPadding, defaultPadding, defaultPadding},
                pageSize, CssConstants.PADDING_TOP, CssConstants.PADDING_RIGHT, CssConstants.PADDING_BOTTOM, CssConstants.PADDING_LEFT);
    }

    /**
     * Parses the borders.
     *
     * @param styles a {@link Map} containing the styles
     * @param em     a measurement expressed in em
     * @param rem    a measurement expressed in rem (root em)
     */
    private void parseBorders(Map<String, String> styles, float em, float rem) {
        borders = BorderStyleApplierUtil.getBordersArray(styles, em, rem);
    }

    /**
     * Creates the page simulation elements.
     *
     * @param styles  a {@link Map} containing the styles
     * @param context the processor context
     */
    private void createPageSimulationElements(Map<String, String> styles, ProcessorContext context) {
        pageBackgroundSimulation = new Div().setFillAvailableArea(true);
        BackgroundApplierUtil.applyBackground(styles, context, pageBackgroundSimulation);
        pageBackgroundSimulation.getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);

        pageBordersSimulation = new Div().setFillAvailableArea(true);
        pageBordersSimulation.setMargins(margins[0], margins[1], margins[2], margins[3]);
        pageBordersSimulation.setBorderTop(borders[0]);
        pageBordersSimulation.setBorderRight(borders[1]);
        pageBordersSimulation.setBorderBottom(borders[2]);
        pageBordersSimulation.setBorderLeft(borders[3]);
        pageBordersSimulation.getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);
    }

    /**
     * Creates the margin boxes elements.
     *
     * @param resolvedPageMarginBoxes the resolved page margin boxes
     */
    private void prepareMarginBoxesSizing(List<PageMarginBoxContextNode> resolvedPageMarginBoxes) {
        Rectangle[] marginBoxRectangles = calculateMarginBoxRectanglesCornersOnly(resolvedPageMarginBoxes);
        for (PageMarginBoxContextNode marginBoxContentNode : resolvedPageMarginBoxes) {
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

    private IElement processMarginBoxContent(PageMarginBoxContextNode marginBoxContentNode, int pageNumber, ProcessorContext context) {
        IElementNode dummyMarginBoxNode = new PageMarginBoxDummyElement();
        dummyMarginBoxNode.setStyles(marginBoxContentNode.getStyles());
        ITagWorker marginBoxWorker = context.getTagWorkerFactory().getTagWorker(dummyMarginBoxNode, context);
        for (int i = 0; i < marginBoxContentNode.childNodes().size(); i++) {
            INode childNode = marginBoxContentNode.childNodes().get(i);
            if (childNode instanceof ITextNode) {
                String text = ((ITextNode) marginBoxContentNode.childNodes().get(i)).wholeText();
                marginBoxWorker.processContent(text, context);
            } else if (childNode instanceof IElementNode) {
                ITagWorker childTagWorker = context.getTagWorkerFactory().getTagWorker((IElementNode) childNode, context);
                if (childTagWorker != null) {
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
                LOGGER.error(LogMessageConstant.UNKNOWN_MARGIN_BOX_CHILD);
            }
        }

        marginBoxWorker.processEnd(dummyMarginBoxNode, context);

        if (!(marginBoxWorker.getElementResult() instanceof IElement)) {
            throw new IllegalStateException("Custom tag worker implementation for margin boxes shall return IElement for #getElementResult() call.");
        }

        ICssApplier cssApplier = context.getCssApplierFactory().getCssApplier(dummyMarginBoxNode);
        cssApplier.apply(context, marginBoxContentNode, marginBoxWorker);

        return (IElement) marginBoxWorker.getElementResult();
    }

    /**
     * Calculate the margin boxes given the list of margin boxes that have generated content
     *
     * @param resolvedPageMarginBoxes list of context nodes representing the generated margin boxes
     * @return Rectangle[12] containing the calulated bounding boxes of the margin-box-nodes. Rectangles with 0 width and/or heigh
     * refer to empty boxes. The order is TLC(top-left-corner)-TL-TC-TY-TRC-RT-RM-RB-RBC-BR-BC-BL-BLC-LB-LM-LT
     */
    private Rectangle[] calculateMarginBoxRectanglesCornersOnly(List<PageMarginBoxContextNode> resolvedPageMarginBoxes) {
        float topMargin = margins[0];
        float rightMargin = margins[1];
        float bottomMargin = margins[2];
        float leftMargin = margins[3];
        Rectangle withoutMargins = pageSize.clone().applyMargins(topMargin, rightMargin, bottomMargin, leftMargin, false);
        Map<String, PageMarginBoxContextNode> resolvedPMBMap = new HashMap<>();
        for (PageMarginBoxContextNode node : resolvedPageMarginBoxes) {
            resolvedPMBMap.put(node.getMarginBoxName(), node);
        }
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

    private void determineSizes(List<PageMarginBoxContextNode> resolvedPageMarginBoxes, List<IRenderer> renderers, int side) {
        float[][] marginsBordersPaddingsWidths = new float[3][4];
        for (int i = 0; i < 3; i++) {
            if (renderers.get(i) != null) {
                marginsBordersPaddingsWidths[i][0] = getSizeOfOneSide(renderers.get(i), Property.MARGIN_TOP, Property.BORDER_TOP, Property.PADDING_TOP);
                marginsBordersPaddingsWidths[i][1] = getSizeOfOneSide(renderers.get(i), Property.MARGIN_RIGHT, Property.BORDER_RIGHT, Property.PADDING_RIGHT);
                marginsBordersPaddingsWidths[i][2] = getSizeOfOneSide(renderers.get(i), Property.MARGIN_BOTTOM, Property.BORDER_BOTTOM, Property.PADDING_BOTTOM);
                marginsBordersPaddingsWidths[i][3] = getSizeOfOneSide(renderers.get(i), Property.MARGIN_LEFT, Property.BORDER_LEFT, Property.PADDING_LEFT);
            }
        }
        Rectangle withoutMargins = pageSize.clone().applyMargins(margins[0], margins[1], margins[2], margins[3], false);
        Map<String, PageMarginBoxContextNode> resolvedPMBMap = new HashMap<>();
        for (PageMarginBoxContextNode node : resolvedPageMarginBoxes) {
            if (node != null) {
                resolvedPMBMap.put(node.getMarginBoxName(), node);
            }
        }
        DimensionContainer[] dims = new DimensionContainer[3];
        String[] cssRuleName = getRuleNames(side);
        float withoutMarginsWidthOrHeight = side % 2 == 0 ? withoutMargins.getWidth() : withoutMargins.getHeight();
        for (int i = 0; i < 3; i++)
            if (side % 2 == 0) {
                dims[i] = retrievePageMarginBoxWidths(resolvedPMBMap.get(cssRuleName[i]), renderers.get(i), withoutMarginsWidthOrHeight,
                        marginsBordersPaddingsWidths[i][1] + marginsBordersPaddingsWidths[i][3]);
            } else {
                dims[i] = retrievePageMarginBoxHeights(resolvedPMBMap.get(cssRuleName[i]), renderers.get(i), margins[side],
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
            if (resolvedPageMarginBoxes.get(i) != null) {
                resolvedPageMarginBoxes.get(i).setPageMarginBoxRectangle(new Rectangle(result[i]).increaseHeight(EPSILON));
                UnitValue width = UnitValue.createPointValue(result[i].getWidth() - marginsBordersPaddingsWidths[i][1] - marginsBordersPaddingsWidths[i][3]);
                UnitValue height = UnitValue.createPointValue(result[i].getHeight() - marginsBordersPaddingsWidths[i][0] - marginsBordersPaddingsWidths[i][2]);
                if (Math.abs(width.getValue()) < EPSILON || Math.abs(height.getValue()) < EPSILON) {
                    renderers.set(i, null);
                } else {
                    renderers.get(i).setProperty(Property.WIDTH, width);
                    renderers.get(i).setProperty(Property.HEIGHT, height);
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
     * Cap each element of the array to the available dimension
     *
     * @param dimensions         array containing non-capped, calculated dimensions
     * @param availableDimension array containing dimensions, with each element set to available dimension if it was larger before
     */
    void limitIfNecessary(float[] dimensions, float availableDimension) {
        for (int i = 0; i < dimensions.length; i++) {
            if (dimensions[i] > availableDimension) {
                dimensions[i] = availableDimension;
            }
        }
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
     * Check if a calculated dimension value needs to be recalculated
     *
     * @param dim        Dimension container containing min and max dimension info
     * @param dimensions array of calculated auto values for boxes in the given dimension
     * @param index      position in the array to look at
     * @return True if the values in dimensions trigger a recalculation, false otherwise
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
    private int mapMarginBoxNameToIndex(String marginBoxName) {
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
