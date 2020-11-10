/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.LogMessageConstant;
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
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
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

        // TODO outlines are currently not supported for page margin boxes, because of the outlines handling specificity (they are handled on renderer's parent level)
        OutlineApplierUtil.applyOutlines(boxStyles, context, marginBox);

        marginBox.setProperty(Property.FONT_PROVIDER, context.getFontProvider());
        marginBox.setProperty(Property.FONT_SET, context.getTempFonts());

        if (!(stylesContainer instanceof PageMarginBoxContextNode)) {
            Logger logger = LoggerFactory.getLogger(PageMarginBoxCssApplier.class);
            logger.warn(LogMessageConstant.PAGE_MARGIN_BOX_SOME_PROPERTIES_NOT_PROCESSED);
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
