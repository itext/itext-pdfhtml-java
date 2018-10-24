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
import com.itextpdf.html2pdf.attach.impl.layout.util.REMatcher;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.impl.PageMarginBoxCssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.font.FontCharacteristics;
import com.itextpdf.layout.font.FontFamilySplitter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSelectorStrategy;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.AreaBreakRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.css.CssRuleName;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.page.PageMarginRunningElementNode;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.ITextNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
     * Page background simulation.
     */
    private Div pageBackgroundSimulation;

    /**
     * Page borders simulation.
     */
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
        Rectangle[] marginBoxRectanglesSpec = calculateMarginBoxRectanglesSpec(resolvedPageMarginBoxes);
        for (PageMarginBoxContextNode marginBoxContentNode : resolvedPageMarginBoxes) {
            if (marginBoxContentNode.childNodes().isEmpty()) {
                // margin box node shall not be added to resolvedPageMarginBoxes if it's kids were not resolved from content
                throw new IllegalStateException();
            }

            int marginBoxInd = mapMarginBoxNameToIndex(marginBoxContentNode.getMarginBoxName());
            marginBoxContentNode.setPageMarginBoxRectangle(marginBoxRectanglesSpec[marginBoxInd]);
            marginBoxContentNode.setContainingBlockForMarginBox(calculateContainingBlockSizesForMarginBox(marginBoxInd, marginBoxRectanglesSpec[marginBoxInd]));
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

    Rectangle[] calculateMarginBoxRectanglesSpec(List<PageMarginBoxContextNode> resolvedPageMarginBoxes) {
        float topMargin = margins[0];
        float rightMargin = margins[1];
        float bottomMargin = margins[2];
        float leftMargin = margins[3];
        Rectangle withoutMargins = pageSize.clone().applyMargins(topMargin, rightMargin, bottomMargin, leftMargin, false);
        Map<String, PageMarginBoxContextNode> resolvedPMBMap = new HashMap<>();
        for (PageMarginBoxContextNode node : resolvedPageMarginBoxes
        ) {
            resolvedPMBMap.put(node.getMarginBoxName(), node);
        }

        //Define corner boxes
        Rectangle tlc = new Rectangle(0, withoutMargins.getTop(), leftMargin, topMargin);
        Rectangle trc = new Rectangle(withoutMargins.getRight(), withoutMargins.getTop(), rightMargin, topMargin);
        Rectangle blc = new Rectangle(0, 0, leftMargin, bottomMargin);
        Rectangle brc = new Rectangle(withoutMargins.getRight(), 0, rightMargin, bottomMargin);

        //Top calculation
        //Gather necessary input
        float[] topWidthResults = calculatePageMarginBoxDimensions(
                retrievePageMarginBoxWidths(resolvedPMBMap.get(CssRuleName.TOP_LEFT), withoutMargins.getWidth()),
                retrievePageMarginBoxWidths(resolvedPMBMap.get(CssRuleName.TOP_CENTER), withoutMargins.getWidth()),
                retrievePageMarginBoxWidths(resolvedPMBMap.get(CssRuleName.TOP_RIGHT), withoutMargins.getWidth()),
                withoutMargins.getWidth()
        );
        float centerOrMiddleCoord = getStartCoordForCenterOrMiddleBox(withoutMargins.getWidth(), topWidthResults, withoutMargins.getLeft());
        Rectangle[] topResults = new Rectangle[]{
                new Rectangle(withoutMargins.getLeft(), withoutMargins.getTop(), topWidthResults[0], topMargin),
                new Rectangle(centerOrMiddleCoord, withoutMargins.getTop(), topWidthResults[1], topMargin),
                new Rectangle(withoutMargins.getRight() - topWidthResults[2], withoutMargins.getTop(), topWidthResults[2], topMargin)
        };

        //Right calculation
        float[] rightHeightResults = calculatePageMarginBoxDimensions(
                retrievePageMarginBoxHeights(resolvedPMBMap.get(CssRuleName.RIGHT_TOP), rightMargin,withoutMargins.getHeight()),
                retrievePageMarginBoxHeights(resolvedPMBMap.get(CssRuleName.RIGHT_MIDDLE), rightMargin,withoutMargins.getHeight()),
                retrievePageMarginBoxHeights(resolvedPMBMap.get(CssRuleName.RIGHT_BOTTOM), rightMargin,withoutMargins.getHeight()),
                withoutMargins.getHeight()
        );
        centerOrMiddleCoord = getStartCoordForCenterOrMiddleBox(withoutMargins.getHeight(), rightHeightResults,withoutMargins.getBottom());
        Rectangle[] rightResults = new Rectangle[]{
                new Rectangle(withoutMargins.getRight(), withoutMargins.getTop() - rightHeightResults[0], rightMargin, rightHeightResults[0]),
                new Rectangle(withoutMargins.getRight(), centerOrMiddleCoord, rightMargin, rightHeightResults[1]),
                new Rectangle(withoutMargins.getRight(), withoutMargins.getBottom(), rightMargin, rightHeightResults[2])
        };

        //Bottom calculation
        float[] bottomWidthResults = calculatePageMarginBoxDimensions(
                retrievePageMarginBoxWidths(resolvedPMBMap.get(CssRuleName.BOTTOM_LEFT), withoutMargins.getWidth()),
                retrievePageMarginBoxWidths(resolvedPMBMap.get(CssRuleName.BOTTOM_CENTER), withoutMargins.getWidth()),
                retrievePageMarginBoxWidths(resolvedPMBMap.get(CssRuleName.BOTTOM_RIGHT), withoutMargins.getWidth()),
                withoutMargins.getWidth()
        );

        centerOrMiddleCoord = getStartCoordForCenterOrMiddleBox(withoutMargins.getWidth(), bottomWidthResults,withoutMargins.getLeft());
        Rectangle[] bottomResults = new Rectangle[]{
                new Rectangle(withoutMargins.getRight() - bottomWidthResults[2], 0, bottomWidthResults[2], bottomMargin),
                new Rectangle( centerOrMiddleCoord, 0, bottomWidthResults[1], bottomMargin),
                new Rectangle(withoutMargins.getLeft(), 0, bottomWidthResults[0], bottomMargin)
        };
        //Left calculation
        float[] leftHeightResults = calculatePageMarginBoxDimensions(
                retrievePageMarginBoxHeights(resolvedPMBMap.get(CssRuleName.LEFT_TOP),leftMargin,withoutMargins.getHeight()),
                retrievePageMarginBoxHeights(resolvedPMBMap.get(CssRuleName.LEFT_MIDDLE),leftMargin,withoutMargins.getHeight()),
                retrievePageMarginBoxHeights(resolvedPMBMap.get(CssRuleName.LEFT_BOTTOM),leftMargin,withoutMargins.getHeight()),
                withoutMargins.getHeight()
        );
        centerOrMiddleCoord = getStartCoordForCenterOrMiddleBox(withoutMargins.getHeight(), leftHeightResults,withoutMargins.getBottom());
        Rectangle[] leftResults = new Rectangle[]{
                new Rectangle(0, withoutMargins.getTop() - leftHeightResults[0], leftMargin, leftHeightResults[0]),
                new Rectangle(0,  centerOrMiddleCoord, leftMargin, leftHeightResults[1]),
                new Rectangle(0, withoutMargins.getBottom(), leftMargin, leftHeightResults[2])
        };
        //Group & return results
        Rectangle[] groupedRectangles = new Rectangle[]{
                tlc,
                topResults[0],
                topResults[1],
                topResults[2],
                trc,

                rightResults[0],
                rightResults[1],
                rightResults[2],

                brc,
                bottomResults[0],
                bottomResults[1],
                bottomResults[2],
                blc,

                leftResults[2],
                leftResults[1],
                leftResults[0],

        };

        return groupedRectangles;
    }

    private float getStartCoordForCenterOrMiddleBox(float availableDimension, float[] dimensionResults, float offset) {
        return offset + (availableDimension-dimensionResults[1])/2;
    }

    /**
     * See the algorithm detailed at https://www.w3.org/TR/css3-page/#margin-dimension
     *
     * @param dimA
     * @param dimB
     * @param dimC
     * @param availableDimension
     * @return
     */
    float[] calculatePageMarginBoxDimensions(Dimensions dimA, Dimensions dimB, Dimensions dimC, float availableDimension) {
        float maxContentDimensionA, minContentDimensionA, maxContentDimensionB, minContentDimensionB, maxContentDimensionC, minContentDimensionC;
        float[] dimensions = new float[3];

        if (dimA == null && dimB == null && dimC == null) {
            return dimensions;
        }

        //Calculate widths
        //Check if B is present
        if (dimB == null) {
            //Single box present
            if (dimA == null) {
                if (dimC.isAutoDimension()) {
                    //Allocate everything to C
                    return new float[]{0, 0, availableDimension};
                } else {
                    return new float[]{0, 0, dimC.dimension};
                }
            }
            if (dimC == null) {
                if (dimA.isAutoDimension()) {
                    //Allocate everything to A
                    return new float[]{availableDimension, 0, 0};
                } else {
                    return new float[]{dimA.dimension, 0, 0};
                }
            }
            if (dimA.isAutoDimension() && dimC.isAutoDimension()) {
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
            if (dimA != null) {
                maxContentDimensionA = dimA.maxContentDimension;
                minContentDimensionA = dimA.minContentDimension;
            } else {
                maxContentDimensionA = 0;
                minContentDimensionA = 0;
            }
            if (dimC != null) {
                maxContentDimensionC = dimC.maxContentDimension;
                minContentDimensionC = dimC.minContentDimension;
            } else {
                maxContentDimensionC = 0;
                minContentDimensionC = 0;
            }
            //Construct box AC
            float maxContentWidthAC = maxContentDimensionA + maxContentDimensionC;
            float minContentWidthAC = minContentDimensionA + minContentDimensionC;
            //Determine width box B
            maxContentDimensionB = dimB.maxContentDimension;
            minContentDimensionB = dimB.minContentDimension;
            float[] distributedDimensions = distributeDimensionBetweenTwoBoxes(maxContentDimensionB, minContentDimensionB, maxContentWidthAC, minContentWidthAC, availableDimension);

            //Determine width boxes A & C
            float newAvailableDimension = (availableDimension - distributedDimensions[0]) / 2;
            float[] distributedWidthsAC = new float[]{
                    Math.min(minContentDimensionA, newAvailableDimension),
                    Math.min(minContentDimensionC, newAvailableDimension)
            };
            dimensions = new float[]{distributedWidthsAC[0], distributedDimensions[0], distributedWidthsAC[1]};
            setManualDimension(dimA, dimensions, 0);
            setManualDimension(dimB, dimensions, 1);
            setManualDimension(dimC, dimensions, 2);
        }

        if (recalculateIfNecessary(dimA, dimensions, 0) ||
            recalculateIfNecessary(dimB, dimensions, 1) ||
            recalculateIfNecessary(dimC, dimensions, 2)) {
            return calculatePageMarginBoxDimensions(dimA, dimB, dimC, availableDimension);
        }
        return dimensions;
    }

    private void setManualDimension(Dimensions dim, float[] dimensions, int index) {
        if (dim != null && !dim.isAutoDimension()) {
            dimensions[index] = dim.dimension;
        }
    }

    private boolean recalculateIfNecessary(Dimensions dim, float[] dimensions, int index) {
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

    Dimensions retrievePageMarginBoxWidths(PageMarginBoxContextNode pmbcNode, float maxWidth) {
        if (pmbcNode == null) {
            return null;
        } else {
            return new WidthDimensions(pmbcNode, maxWidth);
        }
    }

    Dimensions retrievePageMarginBoxHeights(PageMarginBoxContextNode pmbcNode, float marginWidth, float maxHeight) {
        if (pmbcNode == null) {
            return null;
        } else {
            return new HeightDimensions(pmbcNode, marginWidth, maxHeight);
        }
    }

    class Dimensions {
        float dimension, minDimension, maxDimension;
        float minContentDimension, maxContentDimension;

        Dimensions() {
            dimension = -1;
            minDimension = 0;
            minContentDimension = 0;
            maxDimension = Float.MAX_VALUE;
            maxContentDimension = Float.MAX_VALUE;
        }

        boolean isAutoDimension() {
            return dimension == -1;
        }

        float parseDimension(CssContextNode node, String content, float maxAvailableDimension) {
            String numberRegex = "(\\d+(\\.\\d*)?)", units = "(in|cm|mm|pt|pc|px|%|em|ex)";
            REMatcher matcher = new REMatcher(numberRegex + units);
            matcher.setStringForMatch(content);
            if (matcher.find()) {
                float value = Float.parseFloat(matcher.group(1));
                String unit = matcher.group(3);
                switch (unit) {
                    case "pt":
                        break;
                    case "%":
                        value *= maxAvailableDimension / 100;
                        break;
                    case "pc":
                        value *= 12;
                        break;
                    case "px":
                        value *= 0.75;
                        break;
                    case "in":
                        value *= 72;
                        break;
                    case "cm":
                        value *= 28.3465;
                        break;
                    case "mm":
                        value *= 2.83465;
                        break;
                    case "em": {
                        float fontSize = getFontSize(node);
                        value *= fontSize;
                        break;
                    }
                    case "ex": {
                        // Use 0.5em as heuristic of x-height. Look CSS 2.1 Spec
                        float fontSize = getFontSize(node);
                        value *= 0.5 * fontSize;
                        break;
                    }
                    default:
                        value = 0;
                }
                return value;
            }
            return 0;
        }
    }

    class WidthDimensions extends Dimensions {
        WidthDimensions(CssContextNode node, float maxWidth) {
            String width = node.getStyles().get(CssConstants.WIDTH);
            if (width != null && !width.equals("auto")) {
                dimension = parseDimension(node, width, maxWidth);
            }
            minDimension = getMinWidth(node, maxWidth);
            maxDimension = getMaxWidth(node, maxWidth);
            minContentDimension = getMinContentWidth((PageMarginBoxContextNode) node);
            maxContentDimension = getMaxContentWidth((PageMarginBoxContextNode) node);
        }

        float getMinWidth(CssContextNode node, float maxAvailableWidth) {
            String content = node.getStyles().get(CssConstants.MIN_WIDTH);
            if (content == null) {
                return 0;
            }
            content = content.toLowerCase().trim();
            if (content.equals("inherit")) {
                if (node.parentNode() instanceof CssContextNode) {
                    return getMinWidth((CssContextNode) node.parentNode(), maxAvailableWidth);
                }
                return 0;
            }
            return parseDimension(node, content, maxAvailableWidth);
        }

        float getMaxWidth(CssContextNode node, float maxAvailableWidth) {
            String content = node.getStyles().get(CssConstants.MAX_WIDTH);
            if (content == null) {
                return Float.MAX_VALUE;
            }
            content = content.toLowerCase().trim();
            if (content.equals("inherit")) {
                if (node.parentNode() instanceof CssContextNode) {
                    return getMaxWidth((CssContextNode) node.parentNode(), maxAvailableWidth);
                }
                return Float.MAX_VALUE;
            }
            float dim = parseDimension(node, content, maxAvailableWidth);
            if (dim == 0) {
                return Float.MAX_VALUE;
            }
            return dim;
        }
    }

    class HeightDimensions extends Dimensions {
        HeightDimensions(CssContextNode pmbcNode, float width, float maxHeight) {
            String height = pmbcNode.getStyles().get(CssConstants.HEIGHT);
            if (height != null && !height.equals("auto")) {
                dimension = parseDimension(pmbcNode, height, maxHeight);
            }
            minDimension = getMinHeight(pmbcNode, maxHeight);
            maxDimension = getMaxHeight(pmbcNode, maxHeight);
            minContentDimension = getMinContentHeight((PageMarginBoxContextNode) pmbcNode, width, maxHeight);
            maxContentDimension = getMaxContentHeight((PageMarginBoxContextNode) pmbcNode, width, maxHeight);
        }

        float getMinHeight(CssContextNode node, float maxAvailableHeight) {
            String content = node.getStyles().get(CssConstants.MIN_HEIGHT);
            if (content == null) {
                return 0;
            }
            content = content.toLowerCase().trim();
            if (content.equals("inherit")) {
                if (node.parentNode() instanceof CssContextNode) {
                    return getMinHeight((CssContextNode) node.parentNode(), maxAvailableHeight);
                }
                return 0;
            }
            return parseDimension(node, content, maxAvailableHeight);
        }

        float getMaxHeight(CssContextNode node, float maxAvailableHeight) {
            String content = node.getStyles().get(CssConstants.MIN_HEIGHT);
            if (content == null) {
                return Float.MAX_VALUE;
            }
            content = content.toLowerCase().trim();
            if (content.equals("inherit")) {
                if (node.parentNode() instanceof CssContextNode) {
                    return getMaxHeight((CssContextNode) node.parentNode(), maxAvailableHeight);
                }
                return Float.MAX_VALUE;
            }
            float dim = parseDimension(node, content, maxAvailableHeight);
            if (dim == 0) {
                return Float.MAX_VALUE;
            }
            return dim;
        }
    }

    float[] distributeDimensionBetweenTwoBoxes(float maxContentDimensionA, float minContentDimensionA, float maxContentDimensionC, float minContentDimensionC, float availableDimension) {
        //calculate based on flex space
        //Determine flex factor
        float flexRatioA, flexRatioC, flexSpace, distributedWidthA, distributedWidthC;
        if (maxContentDimensionA + maxContentDimensionC < availableDimension) {
            if (maxContentDimensionA == 0 && maxContentDimensionC == 0) {     //TODO(DEVSIX-1050) float comparison to zero, revisit
                flexRatioA = 1;
                flexRatioC = 1;
            } else {
                flexRatioA = maxContentDimensionA / (maxContentDimensionA + maxContentDimensionC);
                flexRatioC = maxContentDimensionC / (maxContentDimensionA + maxContentDimensionC);
            }
            flexSpace = availableDimension - (maxContentDimensionA + maxContentDimensionC);

            distributedWidthA = maxContentDimensionA + flexRatioA * flexSpace;
            distributedWidthC = maxContentDimensionC + flexRatioC * flexSpace;
        } else if (minContentDimensionA + minContentDimensionC < availableDimension) {
            float factorSum = (maxContentDimensionA - minContentDimensionA) + (maxContentDimensionC - minContentDimensionC);

            if (factorSum == 0) {//TODO(DEVSIX-1050) float comparison to zero, revisit
                flexRatioA = 1;
                flexRatioC = 1;
            } else {
                flexRatioA = (maxContentDimensionA - minContentDimensionA) / factorSum;
                flexRatioC = (maxContentDimensionC - minContentDimensionC) / factorSum;
            }
            flexSpace = availableDimension - (minContentDimensionA + minContentDimensionC);

            distributedWidthA = minContentDimensionA + flexRatioA * flexSpace;
            distributedWidthC = minContentDimensionC + flexRatioC * flexSpace;
        } else {
            if (minContentDimensionA == 0 && minContentDimensionC == 0) {//TODO(DEVSIX-1050) float comparison to zero, revisit
                flexRatioA = 1;
                flexRatioC = 1;
            } else {
                flexRatioA = minContentDimensionA / (minContentDimensionA + minContentDimensionC);
                flexRatioC = minContentDimensionC / (minContentDimensionA + minContentDimensionC);
            }
            flexSpace = availableDimension - (minContentDimensionA + minContentDimensionC);

            distributedWidthA = minContentDimensionA + flexRatioA * flexSpace;
            distributedWidthC = minContentDimensionC + flexRatioC * flexSpace;
        }

        return new float[]{distributedWidthA, distributedWidthC};

    }

    float getMaxContentWidth(PageMarginBoxContextNode pmbcNode) {
        //Check styles?
        //Simulate contents?
        //TODO(DEVSIX-1050): Consider complex non-purely text based contents
        String content = pmbcNode.getStyles().get(CssConstants.CONTENT);

        //Resolve font using context
        String fontFamilyName = pmbcNode.getStyles().get(CssConstants.FONT_FAMILY);
        float fontSize = getFontSize(pmbcNode);
        FontProvider provider = this.context.getFontProvider();
        FontCharacteristics fc = new FontCharacteristics();
        FontSelectorStrategy strategy = provider.getStrategy(content,
                FontFamilySplitter.splitFontFamily(fontFamilyName), fc);
        strategy.nextGlyphs();
        PdfFont currentFont = strategy.getCurrentFont();
        if (currentFont == null) {
            //TODO(DEVSIX-1050) Warn and use pdf default
            try {
                currentFont = PdfFontFactory.createFont();
            } catch (IOException ioe) {
                //TODO throw exception further?
            }
        }
        return currentFont.getWidth(content, fontSize);
    }

    float getMinContentWidth(PageMarginBoxContextNode node) {
        //TODO(DEVSIX-1050): reread spec to be certain that min-content-size does in fact mean the same as max content
        return getMaxContentWidth(node);
    }

    float getMaxContentHeight(PageMarginBoxContextNode pmbcNode, float width, float maxAvailableHeight) {
        //TODO(DEVSIX-1050): Consider complex non-purely text based contents
        String content = pmbcNode.getStyles().get(CssConstants.CONTENT);
        //Use iText layout engine to simulate
        //Resolve font using context
        String fontFamilyName = pmbcNode.getStyles().get(CssConstants.FONT_FAMILY);
        float fontSize = getFontSize(pmbcNode);
        FontProvider provider = this.context.getFontProvider();
        FontCharacteristics fc = new FontCharacteristics();
        FontSelectorStrategy strategy = provider.getStrategy(content,
                FontFamilySplitter.splitFontFamily(fontFamilyName), fc);
        strategy.nextGlyphs();
        PdfFont currentFont = strategy.getCurrentFont();
        Text text = new Text(content);
        text.setFont(currentFont);
        text.setFontSize(fontSize);
        text.setProperty(Property.TEXT_RISE, 0f);
        text.setProperty(Property.TEXT_RENDERING_MODE, PdfCanvasConstants.TextRenderingMode.FILL);
        text.setProperty(Property.SPLIT_CHARACTERS, new DefaultSplitCharacters());
        Paragraph p =new Paragraph(text);
        p.setMargin(0f);
        p.setPadding(0f);
        IRenderer pRend = p.createRendererSubTree();

        LayoutArea layoutArea = new LayoutArea(1, new Rectangle(0, 0, width, maxAvailableHeight));
        LayoutContext minimalContext = new LayoutContext(layoutArea);

        LayoutResult quickLayout = pRend.layout(minimalContext);

        return quickLayout.getOccupiedArea().getBBox().getHeight();
    }

    private float getFontSize(CssContextNode pmbcNode) {
        return FontStyleApplierUtil.parseAbsoluteFontSize(pmbcNode.getStyles().get(CssConstants.FONT_SIZE));
    }

    float getMinContentHeight(PageMarginBoxContextNode node, float width, float maxAvailableHeight) {
        return getMaxContentHeight(node, width,maxAvailableHeight);
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
