/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.impl.PageMarginBoxCssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
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
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
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
    private PageMarginBoxBuilder pageMarginBoxHelper;

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
     * Re-initializes page context processor based on default current page size and page margins
     * and on properties from css page at-rules. Css properties priority is higher than default document values.
     *
     * @param defaultPageSize    current default page size to be used if it is not defined in css
     * @param defaultPageMargins current default page margins to be used if they are not defined in css
     * @return this {@link PageContextProcessor} instance
     */
    PageContextProcessor reset(PageSize defaultPageSize, float[] defaultPageMargins) {
        Map<String, String> styles = properties.getResolvedPageContextNode().getStyles();
        float em = CssDimensionParsingUtils.parseAbsoluteLength(styles.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();


        pageSize = PageSizeParser.fetchPageSize(styles.get(CssConstants.SIZE), em, rem, defaultPageSize);

        UnitValue bleedValue = CssDimensionParsingUtils.parseLengthValueToPt(styles.get(CssConstants.BLEED), em, rem);
        if (bleedValue != null && bleedValue.isPointValue()) {
            bleed = bleedValue.getValue();
        }

        marks = parseMarks(styles.get(CssConstants.MARKS));

        parseMargins(styles, em, rem, defaultPageMargins);
        parseBorders(styles, em, rem);
        parsePaddings(styles, em, rem);
        createPageSimulationElements(styles, context);
        pageMarginBoxHelper = new PageMarginBoxBuilder(properties.getResolvedPageMarginBoxes(), margins, pageSize);

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
        drawPageBorders(page);
    }

    /**
     * Draws page background.
     *
     * @param page the page
     * @return pdfCanvas instance if there was a background to draw, otherwise returns null
     */
    PdfCanvas drawPageBackground(PdfPage page) {
        PdfCanvas pdfCanvas = null;
        if (pageBackgroundSimulation != null) {
            pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(),page.getDocument());
            Canvas canvas = new Canvas(pdfCanvas, page.getBleedBox());
            canvas.enableAutoTagging(page);
            canvas.add(pageBackgroundSimulation);
            canvas.close();
        }
        return pdfCanvas;
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
     * Draws page border.
     *
     * @param page the page
     */
    private void drawPageBorders(PdfPage page) {
        if (pageBordersSimulation == null) {
            return;
        }
        Canvas canvas = new Canvas(new PdfCanvas(page), page.getTrimBox());
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
        pageMarginBoxHelper.buildForSinglePage(pageNumber, pdfDocument, documentRenderer, context);
        if (pageMarginBoxHelper.getRenderers() != null) {
            for (int i = 0; i < 16; i++)
                if (pageMarginBoxHelper.getRenderers()[i] != null)
                    draw(pageMarginBoxHelper.getRenderers()[i], pageMarginBoxHelper.getNodes()[i], pdfDocument, pdfDocument.getPage(pageNumber), documentRenderer, pageNumber);
        }
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
            LOGGER.error(
                    MessageFormatUtil.format(
                            Html2PdfLogMessageConstant.PAGE_MARGIN_BOX_CONTENT_CANNOT_BE_DRAWN,
                            node.getMarginBoxName()));
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
        if (!pageBackgroundSimulation.hasOwnProperty(Property.BACKGROUND)
                && !pageBackgroundSimulation.hasOwnProperty(Property.BACKGROUND_IMAGE)) {
            pageBackgroundSimulation = null;
        }
        if (borders[0] == null && borders[1] == null && borders[2] == null && borders[3] == null) {
            pageBordersSimulation = null;
            return;
        }
        pageBordersSimulation = new Div().setFillAvailableArea(true);
        pageBordersSimulation.setMargins(margins[0], margins[1], margins[2], margins[3]);
        pageBordersSimulation.setBorderTop(borders[0]);
        pageBordersSimulation.setBorderRight(borders[1]);
        pageBordersSimulation.setBorderBottom(borders[2]);
        pageBordersSimulation.setBorderLeft(borders[3]);
        pageBordersSimulation.getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);
    }
}
