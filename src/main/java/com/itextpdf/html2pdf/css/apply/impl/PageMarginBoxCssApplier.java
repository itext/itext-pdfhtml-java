/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.MarginApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OutlineApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PaddingApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.OverflowPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
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
     * @param styles          a {@link Map} containing the styles
     * @param em              a measurement expressed in em
     * @param rem             a measurement expressed in rem (root em)
     * @param defaultValues   the default values
     * @param containingBlock the containing block
     * @param topPropName     the top prop name
     * @param rightPropName   the right prop name
     * @param bottomPropName  the bottom prop name
     * @param leftPropName    the left prop name
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

        return new float[]{
                top != null ? (float) top : defaultValues[0],
                right != null ? (float) right : defaultValues[1],
                bottom != null ? (float) bottom : defaultValues[2],
                left != null ? (float) left : defaultValues[3]
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

        //TODO DEVSIX-7024 Support outlines for page margin boxes
        OutlineApplierUtil.applyOutlines(boxStyles, context, marginBox);

        marginBox.setProperty(Property.FONT_PROVIDER, context.getFontProvider());
        marginBox.setProperty(Property.FONT_SET, context.getTempFonts());

        if (!(stylesContainer instanceof PageMarginBoxContextNode)) {
            Logger logger = LoggerFactory.getLogger(PageMarginBoxCssApplier.class);
            logger.warn(Html2PdfLogMessageConstant.PAGE_MARGIN_BOX_SOME_PROPERTIES_NOT_PROCESSED);
            return;
        }

        float availableWidth = ((PageMarginBoxContextNode) stylesContainer).getContainingBlockForMarginBox().getWidth();
        float availableHeight = ((PageMarginBoxContextNode) stylesContainer).getContainingBlockForMarginBox().getHeight();
        MarginApplierUtil.applyMargins(boxStyles, context, marginBox, availableHeight, availableWidth);
        PaddingApplierUtil.applyPaddings(boxStyles, context, marginBox, availableHeight, availableWidth);

    }

    /**
     * Parses the box value.
     *
     * @param em            a measurement expressed in em
     * @param rem           a measurement expressed in rem (root em)
     * @param dimensionSize the dimension size
     * @return a float value
     */
    private static Float parseBoxValue(String valString, float em, float rem, float dimensionSize) {
        UnitValue marginUnitVal = CssDimensionParsingUtils.parseLengthValueToPt(valString, em, rem);
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
