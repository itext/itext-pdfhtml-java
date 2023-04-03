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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply a padding.
 */
public final class PaddingApplierUtil {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(PaddingApplierUtil.class);

    /**
     * Creates a new {@link PaddingApplierUtil} instance.
     */
    private PaddingApplierUtil() {
    }

    /**
     * Applies paddings to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyPaddings(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        applyPaddings(cssProps, context, element, 0.0f, 0.0f);
    }

    /**
     * Applies paddings to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     * @param baseValueHorizontal value used by default for horizontal dimension
     * @param baseValueVertical value used by default for vertical dimension
     */
    public static void applyPaddings(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element, float baseValueVertical, float baseValueHorizontal) {
        String paddingTop = cssProps.get(CssConstants.PADDING_TOP);
        String paddingBottom = cssProps.get(CssConstants.PADDING_BOTTOM);
        String paddingLeft = cssProps.get(CssConstants.PADDING_LEFT);
        String paddingRight = cssProps.get(CssConstants.PADDING_RIGHT);

        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        UnitValue paddingTopVal = CssDimensionParsingUtils.parseLengthValueToPt(paddingTop, em, rem);
        UnitValue paddingBottomVal = CssDimensionParsingUtils.parseLengthValueToPt(paddingBottom, em, rem);
        UnitValue paddingLeftVal = CssDimensionParsingUtils.parseLengthValueToPt(paddingLeft, em, rem);
        UnitValue paddingRightVal = CssDimensionParsingUtils.parseLengthValueToPt(paddingRight, em, rem);

        if (paddingTopVal != null) {
            if (paddingTopVal.isPointValue()) {
                element.setProperty(Property.PADDING_TOP, paddingTopVal);
            } else {
                if (baseValueVertical != 0.0f)
                    element.setProperty(Property.PADDING_TOP, new UnitValue(UnitValue.POINT, baseValueVertical * paddingTopVal.getValue() * 0.01f));
                else
                    logger.error(Html2PdfLogMessageConstant.PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED);
            }
        }

        if (paddingBottomVal != null) {
            if (paddingBottomVal.isPointValue()) {
                element.setProperty(Property.PADDING_BOTTOM, paddingBottomVal);
            } else {
                if (baseValueVertical != 0.0f)
                    element.setProperty(Property.PADDING_BOTTOM, new UnitValue(UnitValue.POINT, baseValueVertical * paddingBottomVal.getValue() * 0.01f));
                else
                    logger.error(Html2PdfLogMessageConstant.PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED);
            }
        }

        if (paddingLeftVal != null) {
            if (paddingLeftVal.isPointValue()) {
                element.setProperty(Property.PADDING_LEFT, paddingLeftVal);
            } else {
                if (baseValueHorizontal != 0.0f)
                    element.setProperty(Property.PADDING_LEFT, new UnitValue(UnitValue.POINT, baseValueHorizontal * paddingLeftVal.getValue() * 0.01f));
                else
                    logger.error(Html2PdfLogMessageConstant.PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED);
            }
        }

        if (paddingRightVal != null) {
            if (paddingRightVal.isPointValue()) {
                element.setProperty(Property.PADDING_RIGHT, paddingRightVal);
            } else {
                if (baseValueHorizontal != 0.0f)
                    element.setProperty(Property.PADDING_RIGHT, new UnitValue(UnitValue.POINT, baseValueHorizontal * paddingRightVal.getValue() * 0.01f));
                else
                    logger.error(Html2PdfLogMessageConstant.PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED);
            }
        }
    }

}
