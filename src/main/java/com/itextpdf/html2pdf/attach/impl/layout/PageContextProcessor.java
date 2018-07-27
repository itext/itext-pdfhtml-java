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
import com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssRuleName;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.html2pdf.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /** The margin box rectangles. */
    private Rectangle[] marginBoxRectangles;

    /** The margin box elements. */
    private Div[] marginBoxElements;

    /**
     * Instantiates a new page context processor.
     *
     * @param properties the page context properties
     * @param context the processor context
     * @param defaultPageSize the default page size
     */
    PageContextProcessor(PageContextProperties properties, ProcessorContext context, PageSize defaultPageSize) {
        Map<String, String> styles = properties.getResolvedPageContextNode().getStyles();
        float em = CssUtils.parseAbsoluteLength(styles.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();

        pageSize = PageSizeParser.fetchPageSize(styles.get(CssConstants.SIZE), em, rem, defaultPageSize);

        UnitValue bleedValue = CssUtils.parseLengthValueToPt(styles.get(CssConstants.BLEED), em, rem);
        if (bleedValue != null && bleedValue.isPointValue()) {
            bleed = bleedValue.getValue();
        }

        marks = parseMarks(styles.get(CssConstants.MARKS));

        parseMargins(styles, em, rem);
        parseBorders(styles, em, rem);
        parsePaddings(styles, em, rem);
        createPageSimulationElements(styles, context);
        createMarginBoxesElements(properties.getResolvedPageMarginBoxes(), context);
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
     * Processes a new page by setting the bleed value, adding marks, drawing
     * page backgrounds and borders, and margin boxes (if necessary).
     *
     * @param page the page to process
     * @param documentRenderer the document renderer
     */
    void processNewPage(PdfPage page, DocumentRenderer documentRenderer) {
        setBleed(page);
        drawMarks(page);
        drawPageBackgroundAndBorders(page);
        drawMarginBoxes(page, documentRenderer);
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
            box.increaseHeight((float)bleed * 2);
            box.setWidth(box.getWidth() + (float)bleed * 2);
            page.setMediaBox(box).setBleedBox(box);
            Rectangle trimBox = page.getTrimBox();
            trimBox.moveUp((float)bleed);
            trimBox.moveRight((float)bleed);
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
     * @param canvas the canvas to draw on
     * @param x the x value
     * @param y the y value
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
        canvas.add(pageBackgroundSimulation);
        canvas.close();
        canvas = new Canvas(new PdfCanvas(page), page.getDocument(), page.getTrimBox());
        canvas.add(pageBordersSimulation);
        canvas.close();
    }

    /**
     * Draws margin boxes.
     *
     * @param page the page
     * @param documentRenderer the document renderer
     */
    private void drawMarginBoxes(PdfPage page, DocumentRenderer documentRenderer) {
        for (int i = 0; i < 16; ++i) {
            if (marginBoxElements[i] != null) {
                Div curBoxElement = marginBoxElements[i];
                IRenderer renderer = curBoxElement.createRendererSubTree();
                renderer.setParent(documentRenderer);
                LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(page.getDocument().getPageNumber(page), marginBoxRectangles[i])));
                IRenderer rendererToDraw = result.getStatus() == LayoutResult.FULL ? renderer : result.getSplitRenderer();
                rendererToDraw.setParent(documentRenderer).draw(new DrawContext(page.getDocument(), new PdfCanvas(page)));
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
     * @param em a measurement expressed in em
     * @param rem a measurement expressed in rem (root em)
     */
    private void parseMargins(Map<String, String> styles, float em, float rem) {
        float defaultMargin = 36;
        PageSize pageSize = getPageSize();
        margins = parseBoxProps(styles, em, rem, defaultMargin, pageSize,
                CssConstants.MARGIN_TOP, CssConstants.MARGIN_RIGHT, CssConstants.MARGIN_BOTTOM, CssConstants.MARGIN_LEFT);
    }

    /**
     * Parses the paddings.
     *
     * @param styles a {@link Map} containing the styles
     * @param em a measurement expressed in em
     * @param rem a measurement expressed in rem (root em)
     */
    private void parsePaddings(Map<String, String> styles, float em, float rem) {
        float defaultPadding = 0;
        PageSize pageSize = getPageSize();
        paddings = parseBoxProps(styles, em, rem, defaultPadding, pageSize,
                CssConstants.PADDING_TOP, CssConstants.PADDING_RIGHT, CssConstants.PADDING_BOTTOM, CssConstants.PADDING_LEFT);
    }

    /**
     * Parses the borders.
     *
     * @param styles a {@link Map} containing the styles
     * @param em a measurement expressed in em
     * @param rem a measurement expressed in rem (root em)
     */
    private void parseBorders(Map<String, String> styles, float em, float rem) {
        borders = BorderStyleApplierUtil.getBordersArray(styles, em, rem);
    }

    /**
     * Creates the page simulation elements.
     *
     * @param styles a {@link Map} containing the styles
     * @param context the processor context
     */
    private void createPageSimulationElements(Map<String, String> styles, ProcessorContext context) {
        pageBackgroundSimulation = new Div().setFillAvailableArea(true);
        BackgroundApplierUtil.applyBackground(styles, context, pageBackgroundSimulation);

        pageBordersSimulation = new Div().setFillAvailableArea(true);
        pageBordersSimulation.setMargins(margins[0], margins[1], margins[2], margins[3]);
        pageBordersSimulation.setBorderTop(borders[0]);
        pageBordersSimulation.setBorderRight(borders[1]);
        pageBordersSimulation.setBorderBottom(borders[2]);
        pageBordersSimulation.setBorderLeft(borders[3]);
    }

    /**
     * Creates the margin boxes elements.
     *
     * @param resolvedPageMarginBoxes the resolved page margin boxes
     * @param context the processor context
     */
    private void createMarginBoxesElements(List<PageMarginBoxContextNode> resolvedPageMarginBoxes, ProcessorContext context) {
        marginBoxRectangles = calculateMarginBoxRectangles(resolvedPageMarginBoxes);
        marginBoxElements = new Div[16];
        for (PageMarginBoxContextNode marginBoxContentNode : resolvedPageMarginBoxes) {
            int marginBoxInd = mapMarginBoxNameToIndex(marginBoxContentNode.getMarginBoxName());
            Div marginBox = new Div();
            marginBoxElements[marginBoxInd] = marginBox;
            Map<String, String> boxStyles = marginBoxContentNode.getStyles();
            BackgroundApplierUtil.applyBackground(boxStyles, context, marginBox);
            FontStyleApplierUtil.applyFontStyles(boxStyles, context, marginBoxContentNode, marginBox);
            BorderStyleApplierUtil.applyBorders(boxStyles, context, marginBox);
            VerticalAlignmentApplierUtil.applyVerticalAlignmentForCells(boxStyles, context, marginBox);

            float em = CssUtils.parseAbsoluteLength(boxStyles.get(CssConstants.FONT_SIZE));
            float rem = context.getCssContext().getRootFontSize();
            float[] boxMargins = parseBoxProps(boxStyles, em, rem, 0, calculateContainingBlockSizesForMarginBox(marginBoxInd),
                    CssConstants.MARGIN_TOP, CssConstants.MARGIN_RIGHT, CssConstants.MARGIN_BOTTOM, CssConstants.MARGIN_LEFT);
            float[] boxPaddings = parseBoxProps(boxStyles, em, rem, 0, calculateContainingBlockSizesForMarginBox(marginBoxInd),
                    CssConstants.PADDING_TOP, CssConstants.PADDING_RIGHT, CssConstants.PADDING_BOTTOM, CssConstants.PADDING_LEFT);

            marginBox.setMargins(boxMargins[0], boxMargins[1], boxMargins[2], boxMargins[3]);
            marginBox.setPaddings(boxPaddings[0], boxPaddings[1], boxPaddings[2], boxPaddings[3]);
            marginBox.setProperty(Property.FONT_PROVIDER, context.getFontProvider());
            marginBox.setProperty(Property.FONT_SET, context.getTempFonts());
            marginBox.setFillAvailableArea(true);

            if (marginBoxContentNode.childNodes().isEmpty()) {
                // margin box node shall not be added to resolvedPageMarginBoxes if it's kids were not resolved from content
                throw new IllegalStateException();
            }

            // TODO it would be great to reuse DefaultHtmlProcessor, but it seems there is no convenient way of doing so, and maybe it would be an overkill
            IElementNode dummyMarginBoxNode = new JsoupElementNode(new Element(Tag.valueOf(TagConstants.DIV), ""));
            dummyMarginBoxNode.setStyles(boxStyles);
            DivTagWorker marginBoxWorker = new DivTagWorker(dummyMarginBoxNode, context);
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
                }  else {
                    LoggerFactory.getLogger(getClass()).error(LogMessageConstant.UNKNOWN_MARGIN_BOX_CHILD);
                }
            }
            marginBoxWorker.processEnd(dummyMarginBoxNode, context);
            IPropertyContainer workerResult = marginBoxWorker.getElementResult();
            if (workerResult instanceof Div) {
                for (IElement child : ((Div) workerResult).getChildren()) {
                    if (child instanceof IBlockElement) {
                        marginBox.add((IBlockElement) child);
                    } else if (child instanceof Image) {
                        marginBox.add((Image) child);
                    }
                }
            }
            marginBox.setRole(PdfName.Artifact);
        }
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
        Rectangle withoutMargins = pageSize.clone().<Rectangle>applyMargins(topMargin, rightMargin, bottomMargin, leftMargin, false);
        float topBottomMarginWidth = withoutMargins.getWidth() / 3;
        float leftRightMarginHeight = withoutMargins.getHeight() / 3;
        Rectangle[] hardcodedBoxRectangles = new Rectangle[] {
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
     * @param marginBoxInd the margin box index
     * @return the corresponding rectangle
     */
    private Rectangle calculateContainingBlockSizesForMarginBox(int marginBoxInd) {
        if (marginBoxInd == 0 || marginBoxInd == 4 || marginBoxInd == 8 || marginBoxInd == 12) {
            return marginBoxRectangles[marginBoxInd];
        }
        Rectangle withoutMargins = pageSize.clone().<Rectangle>applyMargins(margins[0], margins[1], margins[2], margins[3], false);
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
     * Parses the box props.
     *
     * @param styles a {@link Map} containing the styles
     * @param em a measurement expressed in em
     * @param rem a measurement expressed in rem (root em)
     * @param defaultValue the default value
     * @param containingBlock the containing block
     * @param topPropName the top prop name
     * @param rightPropName the right prop name
     * @param bottomPropName the bottom prop name
     * @param leftPropName the left prop name
     * @return an array with a top, right, bottom, and top float value
     */
    private float[] parseBoxProps(Map<String, String> styles, float em, float rem, float defaultValue, Rectangle containingBlock,
                                  String topPropName, String rightPropName, String bottomPropName, String leftPropName) {
        String topStr = styles.get(topPropName);
        String rightStr = styles.get(rightPropName);
        String bottomStr = styles.get(bottomPropName);
        String leftStr = styles.get(leftPropName);

        Float top = parseBoxValue(topStr, em, rem, containingBlock.getHeight());
        Float right = parseBoxValue(rightStr, em, rem, containingBlock.getWidth());
        Float bottom = parseBoxValue(bottomStr, em, rem, containingBlock.getHeight());
        Float left = parseBoxValue(leftStr, em, rem, containingBlock.getWidth());

        return new float[] {
                top != null ? (float)top : defaultValue,
                right != null ? (float)right : defaultValue,
                bottom != null ? (float)bottom : defaultValue,
                left != null ? (float)left : defaultValue
        };
    }

    /**
     * Parses the box value.
     *
     * @param em a measurement expressed in em
     * @param rem a measurement expressed in rem (root em)
     * @param dimensionSize the dimension size
     * @return a float value
     */
    private static Float parseBoxValue(String valString, float em, float rem, float dimensionSize) {
        UnitValue marginUnitVal = CssUtils.parseLengthValueToPt(valString, em, rem);
        if (marginUnitVal != null) {
            if (marginUnitVal.isPointValue()) {
                return marginUnitVal.getValue();
            }
            if (marginUnitVal.isPercentValue()) {
                return marginUnitVal.getValue() * dimensionSize / 100;
            }
        }

        return null;
    }
}
