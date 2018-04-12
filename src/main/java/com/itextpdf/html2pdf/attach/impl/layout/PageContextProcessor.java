/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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
import com.itextpdf.html2pdf.css.CssRuleName;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.impl.PageMarginBoxCssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.page.PageMarginRunningElementNode;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
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
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.AreaBreakRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Context processor for specific types of pages: first, left, or right page.
 */
class PageContextProcessor {

    /** The page size. */
    private PageSize pageSize;

    /** Marks for page boundaries. */
    private Set<String> marks;

    /** The bleed value for the margin. */
    private Float bleed;

    /** The margins. */
    private float[] margins;

    /** The borders. */
    private Border[] borders;

    /** The paddings. */
    private float[] paddings;

    /** Page background simulation. */
    private Div pageBackgroundSimulation;

    /** Page borders simulation. */
    private Div pageBordersSimulation;

    private PageContextProperties properties;
    private ProcessorContext context;

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
        for (PageMarginBoxContextNode marginBoxContentNode : properties.getResolvedPageMarginBoxes()) {
            IElement curBoxElement = processMarginBoxContent(marginBoxContentNode, pageNumber, context);

            IRenderer renderer = curBoxElement.createRendererSubTree();
            removeAreaBreaks(renderer);
            renderer.setParent(documentRenderer);
            boolean isTagged = pdfDocument.isTagged();
            if (isTagged) {
                LayoutTaggingHelper taggingHelper = renderer.<LayoutTaggingHelper>getProperty(Property.TAGGING_HELPER);
                LayoutTaggingHelper.addTreeHints(taggingHelper, renderer);
            }
            LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, marginBoxContentNode.getPageMarginBoxRectangle())));
            IRenderer rendererToDraw = result.getStatus() == LayoutResult.FULL ? renderer : result.getSplitRenderer();
            if (rendererToDraw != null) {
                TagTreePointer tagPointer = null, backupPointer = null;
                PdfPage backupPage = null;
                if (isTagged) {
                    tagPointer = pdfDocument.getTagStructureContext().getAutoTaggingPointer();
                    backupPage = tagPointer.getCurrentPage();
                    backupPointer = new TagTreePointer(tagPointer);
                    tagPointer.moveToRoot();
                    tagPointer.setPageForTagging(page);
                }

                rendererToDraw.setParent(documentRenderer).draw(new DrawContext(page.getDocument(), new PdfCanvas(page), isTagged));

                if (isTagged) {
                    tagPointer.setPageForTagging(backupPage);
                    tagPointer.moveToPointer(backupPointer);
                }
            } else {
                // marginBoxElements have overflow property set to HIDDEN, therefore it is not expected to neither get
                // LayoutResult other than FULL nor get no split renderer (result NOTHING) even if result is not FULL
                Logger logger = LoggerFactory.getLogger(PageContextProcessor.class);
                logger.error(MessageFormatUtil.format(LogMessageConstant.PAGE_MARGIN_BOX_CONTENT_CANNOT_BE_DRAWN, marginBoxContentNode.getMarginBoxName()));
            }
        }
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
        Rectangle[] marginBoxRectangles = calculateMarginBoxRectangles(resolvedPageMarginBoxes);
        for (PageMarginBoxContextNode marginBoxContentNode : resolvedPageMarginBoxes) {
            if (marginBoxContentNode.childNodes().isEmpty()) {
                // margin box node shall not be added to resolvedPageMarginBoxes if it's kids were not resolved from content
                throw new IllegalStateException();
            }

            int marginBoxInd = mapMarginBoxNameToIndex(marginBoxContentNode.getMarginBoxName());
            marginBoxContentNode.setPageMarginBoxRectangle(marginBoxRectangles[marginBoxInd]);
            marginBoxContentNode.setContainingBlockForMarginBox(calculateContainingBlockSizesForMarginBox(marginBoxInd, marginBoxRectangles[marginBoxInd]));
        }
    }

    private IElement processMarginBoxContent(PageMarginBoxContextNode marginBoxContentNode, int pageNumber, ProcessorContext context) {
        IElementNode dummyMarginBoxNode = new PageMarginBoxDummyElement();
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
                LoggerFactory.getLogger(getClass()).error(LogMessageConstant.UNKNOWN_MARGIN_BOX_CHILD);
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
     * Calculate margin box rectangles.
     *
     * @param resolvedPageMarginBoxes the resolved page margin boxes
     * @return an array of {@link Rectangle} values
     */
    private Rectangle[] calculateMarginBoxRectangles(List<PageMarginBoxContextNode> resolvedPageMarginBoxes) {
        // TODO It's a very basic implementation for now. In future resolve rectangles based on presence of certain margin boxes,
        //      also height and width properties should be taken into account.
        float topMargin = margins[0];
        float rightMargin = margins[1];
        float bottomMargin = margins[2];
        float leftMargin = margins[3];
        Rectangle withoutMargins = pageSize.clone().applyMargins(topMargin, rightMargin, bottomMargin, leftMargin, false);
        float topBottomMarginWidth = withoutMargins.getWidth() / 3;
        float leftRightMarginHeight = withoutMargins.getHeight() / 3;
        Rectangle[] hardcodedBoxRectangles = new Rectangle[]{
                new Rectangle(0, withoutMargins.getTop(), leftMargin, topMargin),
                new Rectangle(rightMargin, withoutMargins.getTop(), topBottomMarginWidth, topMargin),
                new Rectangle(rightMargin + topBottomMarginWidth, withoutMargins.getTop(), topBottomMarginWidth, topMargin),
                new Rectangle(withoutMargins.getRight() - topBottomMarginWidth, withoutMargins.getTop(), topBottomMarginWidth, topMargin),
                new Rectangle(withoutMargins.getRight(), withoutMargins.getTop(), topBottomMarginWidth, topMargin),

                new Rectangle(withoutMargins.getRight(), withoutMargins.getTop() - leftRightMarginHeight, rightMargin, leftRightMarginHeight),
                new Rectangle(withoutMargins.getRight(), withoutMargins.getBottom() + leftRightMarginHeight, rightMargin, leftRightMarginHeight),
                new Rectangle(withoutMargins.getRight(), withoutMargins.getBottom(), rightMargin, leftRightMarginHeight),

                new Rectangle(withoutMargins.getRight(), 0, rightMargin, bottomMargin),
                new Rectangle(withoutMargins.getRight() - topBottomMarginWidth, 0, topBottomMarginWidth, bottomMargin),
                new Rectangle(rightMargin + topBottomMarginWidth, 0, topBottomMarginWidth, bottomMargin),
                new Rectangle(rightMargin, 0, topBottomMarginWidth, bottomMargin),
                new Rectangle(0, 0, leftMargin, bottomMargin),

                new Rectangle(0, withoutMargins.getBottom(), leftMargin, leftRightMarginHeight),
                new Rectangle(0, withoutMargins.getBottom() + leftRightMarginHeight, leftMargin, leftRightMarginHeight),
                new Rectangle(0, withoutMargins.getTop() - leftRightMarginHeight, leftMargin, leftRightMarginHeight),

        };
        return hardcodedBoxRectangles;
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
            return new Rectangle(margins[3], withoutMargins.getWidth());
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
}
