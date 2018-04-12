package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OutlineApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.html2pdf.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for page margin box containers.
 */
public class PageMarginBoxCssApplier implements ICssApplier {

    /**
     * Parses the page and margin boxes properties (like margins, paddings, etc).
     *
     * @param styles a {@link Map} containing the styles
     * @param em a measurement expressed in em
     * @param rem a measurement expressed in rem (root em)
     * @param defaultValues the default values
     * @param containingBlock the containing block
     * @param topPropName the top prop name
     * @param rightPropName the right prop name
     * @param bottomPropName the bottom prop name
     * @param leftPropName the left prop name
     * @return an array with a top, right, bottom, and top float value
     */
    public static float[] parseBoxProps(Map<String, String> styles, float em, float rem, float[] defaultValues, Rectangle containingBlock,
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
                top != null ? (float)top : defaultValues[0],
                right != null ? (float)right : defaultValues[1],
                bottom != null ? (float)bottom : defaultValues[2],
                left != null ? (float)left : defaultValues[3]
        };
    }

    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        Map<String, String> boxStyles = stylesContainer.getStyles();

        IPropertyContainer marginBox = tagWorker.getElementResult();
        BackgroundApplierUtil.applyBackground(boxStyles, context, marginBox);
        FontStyleApplierUtil.applyFontStyles(boxStyles, context, stylesContainer, marginBox);
        BorderStyleApplierUtil.applyBorders(boxStyles, context, marginBox);
        VerticalAlignmentApplierUtil.applyVerticalAlignmentForCells(boxStyles, context, marginBox);

        // Set overflow to HIDDEN if it's not explicitly set in css in order to avoid overlapping with page content.
        String overflow = CssConstants.OVERFLOW_VALUES.contains(boxStyles.get(CssConstants.OVERFLOW)) ? boxStyles.get(CssConstants.OVERFLOW) : null;
        String overflowX = CssConstants.OVERFLOW_VALUES.contains(boxStyles.get(CssConstants.OVERFLOW_X)) ? boxStyles.get(CssConstants.OVERFLOW_X) : overflow;
        if (overflowX == null || CssConstants.HIDDEN.equals(overflowX)) {
            marginBox.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.HIDDEN);
        } else {
            marginBox.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.VISIBLE);
        }
        String overflowY = CssConstants.OVERFLOW_VALUES.contains(boxStyles.get(CssConstants.OVERFLOW_Y)) ? boxStyles.get(CssConstants.OVERFLOW_Y) : overflow;
        if (overflowY == null || CssConstants.HIDDEN.equals(overflowY)) {
            marginBox.setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.HIDDEN);
        } else {
            marginBox.setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.VISIBLE);
        }

        // TODO outlines are currently not supported for page margin boxes, because of the outlines handling specificity (they are handled on renderer's parent level)
        OutlineApplierUtil.applyOutlines(boxStyles, context, marginBox);

        marginBox.setProperty(Property.FONT_PROVIDER, context.getFontProvider());
        marginBox.setProperty(Property.FONT_SET, context.getTempFonts());

        if (!(stylesContainer instanceof PageMarginBoxContextNode)) {
            Logger logger = LoggerFactory.getLogger(PageMarginBoxCssApplier.class);
            logger.warn(LogMessageConstant.PAGE_MARGIN_BOX_SOME_PROPERTIES_NOT_PROCESSED);
            return;
        }

        PageMarginBoxContextNode pageMarginBoxContextNode = (PageMarginBoxContextNode) stylesContainer;

        float em = CssUtils.parseAbsoluteLength(boxStyles.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        float[] boxMargins = parseBoxProps(boxStyles, em, rem, new float[]{0,0,0,0}, pageMarginBoxContextNode.getContainingBlockForMarginBox(),
                CssConstants.MARGIN_TOP, CssConstants.MARGIN_RIGHT, CssConstants.MARGIN_BOTTOM, CssConstants.MARGIN_LEFT);
        float[] boxPaddings = parseBoxProps(boxStyles, em, rem, new float[]{0,0,0,0}, pageMarginBoxContextNode.getContainingBlockForMarginBox(),
                CssConstants.PADDING_TOP, CssConstants.PADDING_RIGHT, CssConstants.PADDING_BOTTOM, CssConstants.PADDING_LEFT);

        setUnitPointValueProperties(marginBox, new int[]{Property.MARGIN_TOP, Property.MARGIN_RIGHT, Property.MARGIN_BOTTOM, Property.MARGIN_LEFT}, boxMargins);
        setUnitPointValueProperties(marginBox, new int[]{Property.PADDING_TOP, Property.PADDING_RIGHT, Property.PADDING_BOTTOM, Property.PADDING_LEFT}, boxPaddings);

        float[] boxBorders = getBordersWidth(marginBox);
        float marginBorderPaddingWidth = boxMargins[1] + boxMargins[3] + boxBorders[1] + boxBorders[3] + boxPaddings[1] + boxPaddings[3];
        float marginBorderPaddingHeight = boxMargins[0] + boxMargins[2] + boxBorders[0] + boxBorders[2] + boxPaddings[0] + boxPaddings[2];

        // TODO DEVSIX-1050: improve width/height calculation according to "5.3. Computing Page-margin Box Dimensions", take into account height and width properties
        float width = pageMarginBoxContextNode.getPageMarginBoxRectangle().getWidth() - marginBorderPaddingWidth;
        float height = pageMarginBoxContextNode.getPageMarginBoxRectangle().getHeight() - marginBorderPaddingHeight;
        setUnitPointValueProperty(marginBox, Property.WIDTH, width);
        setUnitPointValueProperty(marginBox, Property.HEIGHT, height);
    }

    private static void setUnitPointValueProperties(IPropertyContainer container, int[] properties, float[] values) {
        for (int i = 0; i < properties.length; ++i) {
            setUnitPointValueProperty(container, properties[i], values[i]);
        }
    }

    private static void setUnitPointValueProperty(IPropertyContainer container, int property, float value) {
        UnitValue marginUV = UnitValue.createPointValue(value);
        container.setProperty(property, marginUV);
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

    private static float[] getBordersWidth(IPropertyContainer container) {
        Border border = container.<Border>getProperty(Property.BORDER);
        Border topBorder = container.<Border>getProperty(Property.BORDER_TOP);
        Border rightBorder = container.<Border>getProperty(Property.BORDER_RIGHT);
        Border bottomBorder = container.<Border>getProperty(Property.BORDER_BOTTOM);
        Border leftBorder = container.<Border>getProperty(Property.BORDER_LEFT);

        Border[] borders = {topBorder, rightBorder, bottomBorder, leftBorder};

        if (!container.hasProperty(Property.BORDER_TOP)) {
            borders[0] = border;
        }
        if (!container.hasProperty(Property.BORDER_RIGHT)) {
            borders[1] = border;
        }
        if (!container.hasProperty(Property.BORDER_BOTTOM)) {
            borders[2] = border;
        }
        if (!container.hasProperty(Property.BORDER_LEFT)) {
            borders[3] = border;
        }

        return new float[] {
                borders[0] != null ? borders[0].getWidth() : 0,
                borders[1] != null ? borders[1].getWidth() : 0,
                borders[2] != null ? borders[2].getWidth() : 0,
                borders[3] != null ? borders[3].getWidth() : 0,
        };
    }
}
