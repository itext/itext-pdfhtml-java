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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply margins.
 */
public final class MarginApplierUtil {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(MarginApplierUtil.class);

    /**
     * Creates a {@link MarginApplierUtil} instance.
     */
    private MarginApplierUtil() {
    }

    /**
     * Applies margins to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyMargins(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        applyMargins(cssProps, context, element, 0.0f, 0.0f);
    }

    /**
     * Applies margins to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     * @param baseValueHorizontal value used by default for horizontal dimension
     * @param baseValueVertical value used by default for vertical dimension
     */
    public static void applyMargins(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element, float baseValueVertical, float baseValueHorizontal) {
        String marginTop = cssProps.get(CssConstants.MARGIN_TOP);
        String marginBottom = cssProps.get(CssConstants.MARGIN_BOTTOM);
        String marginLeft = cssProps.get(CssConstants.MARGIN_LEFT);
        String marginRight = cssProps.get(CssConstants.MARGIN_RIGHT);

        // The check for display is useful at least for images
        boolean isBlock = element instanceof IBlockElement || CssConstants.BLOCK.equals(cssProps.get(CssConstants.DISPLAY));
        boolean isImage = element instanceof Image;
        
        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();

        if (isBlock || isImage) {
            trySetMarginIfNotAuto(Property.MARGIN_TOP, marginTop, element, em, rem, baseValueVertical);
            trySetMarginIfNotAuto(Property.MARGIN_BOTTOM, marginBottom, element, em, rem, baseValueVertical);
        }

        boolean isLeftAuto = !trySetMarginIfNotAuto(Property.MARGIN_LEFT, marginLeft, element, em, rem, baseValueHorizontal);
        boolean isRightAuto = !trySetMarginIfNotAuto(Property.MARGIN_RIGHT, marginRight, element, em, rem, baseValueHorizontal);

        if (isBlock) {
            if (isLeftAuto && isRightAuto) {
                element.setProperty(Property.HORIZONTAL_ALIGNMENT, HorizontalAlignment.CENTER);
            } else if (isLeftAuto) {
                element.setProperty(Property.HORIZONTAL_ALIGNMENT, HorizontalAlignment.RIGHT);
            } else if (isRightAuto) {
                element.setProperty(Property.HORIZONTAL_ALIGNMENT, HorizontalAlignment.LEFT);
            }
        }

    }

    /**
     * Tries set margin if the value isn't "auto".
     *
     * @param marginProperty the margin property
     * @param marginValue the margin value
     * @param element the element
     * @param em the em value
     * @param rem the root em value
     * @param baseValue value used by default
     * @return false if the margin value was "auto"
     */
    private static boolean trySetMarginIfNotAuto(int marginProperty, String marginValue, IPropertyContainer element, float em, float rem, float baseValue) {
        boolean isAuto = CssConstants.AUTO.equals(marginValue);
        if (isAuto) {
            return false;
        }
        
        Float marginVal = parseMarginValue(marginValue, em, rem, baseValue);
        if (marginVal != null) {
            element.setProperty(marginProperty, UnitValue.createPointValue((float) marginVal));
        }
        return true;
    }

    /**
     * Parses the margin value.
     *
     * @param marginValString the margin value as a {@link String}
     * @param em the em value
     * @param rem the root em value
     * @param baseValue value used my default
     * @return the margin value as a {@link Float}
     */
    private static Float parseMarginValue(String marginValString, float em, float rem, float baseValue) {
        UnitValue marginUnitVal = CssDimensionParsingUtils.parseLengthValueToPt(marginValString, em, rem);
        if (marginUnitVal != null) {
            if (!marginUnitVal.isPointValue()) {
                if (baseValue != 0.0f) {
                    return new Float(baseValue * marginUnitVal.getValue() * 0.01);
                }
                logger.error(Html2PdfLogMessageConstant.MARGIN_VALUE_IN_PERCENT_NOT_SUPPORTED);
                return null;
            }

            return marginUnitVal.getValue();
        } else {
            return null;
        }
    }

}
