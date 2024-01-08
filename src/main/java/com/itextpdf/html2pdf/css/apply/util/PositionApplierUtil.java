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
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply a position.
 */
public final class PositionApplierUtil {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(PositionApplierUtil.class);

    /**
     * Creates a new {@link PositionApplierUtil} instance.
     */
    private PositionApplierUtil() {
    }

    /**
     * Applies a position to an element.
     *
     * @param cssProps the CSS properties
     * @param context the propertiescontext
     * @param element the element
     */
    public static void applyPosition(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String position = cssProps.get(CssConstants.POSITION);
        if (CssConstants.ABSOLUTE.equals(position)) {
            element.setProperty(Property.POSITION, LayoutPosition.ABSOLUTE);
            applyLeftRightTopBottom(cssProps, context, element, position);
        } else if (CssConstants.RELATIVE.equals(position)) {
            element.setProperty(Property.POSITION, LayoutPosition.RELATIVE);
            applyLeftRightTopBottom(cssProps, context, element, position);
        } else if (CssConstants.FIXED.equals(position)) {
//            element.setProperty(Property.POSITION, LayoutPosition.FIXED);
//            float em = CssUtils.parseAbsoluteLength(cssProps.get(CommonCssConstants.FONT_SIZE));
//            applyLeftProperty(cssProps, element, em, Property.X);
//            applyTopProperty(cssProps, element, em, Property.Y);
            // TODO DEVSIX-4104 support "fixed" value of position property
        }
    }

    /**
     * Applies left, right, top, and bottom properties.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     * @param position the position
     */
    private static void applyLeftRightTopBottom(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element, String position) {
        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        if (CssConstants.RELATIVE.equals(position) && cssProps.containsKey(CssConstants.LEFT) && cssProps.containsKey(CssConstants.RIGHT)) {
            // When both the right CSS property and the left CSS property are defined, the position of the element is overspecified.
            // In that case, the left value has precedence when the container is left-to-right (that is that the right computed value is set to -left),
            // and the right value has precedence when the container is right-to-left (that is that the left computed value is set to -right).
            boolean isRtl = CssConstants.RTL.equals(cssProps.get(CssConstants.DIRECTION));
            if (isRtl) {
                applyRightProperty(cssProps, element, em, rem, Property.RIGHT);
            } else {
                applyLeftProperty(cssProps, element, em, rem, Property.LEFT);
            }
        } else {
            applyLeftProperty(cssProps, element, em, rem, Property.LEFT);
            applyRightProperty(cssProps, element, em, rem, Property.RIGHT);
        }
        applyTopProperty(cssProps, element, em, rem, Property.TOP);
        applyBottomProperty(cssProps, element, em, rem, Property.BOTTOM);
    }

    /**
     * Applies the "left" property.
     *
     * @param cssProps the CSS properties
     * @param element the element
     * @param em the em value
     * @param rem the root em value
     * @param layoutPropertyMapping the layout property mapping
     */
    private static void applyLeftProperty(Map<String, String> cssProps, IPropertyContainer element, float em, float rem, int layoutPropertyMapping) {
        String left = cssProps.get(CssConstants.LEFT);
        UnitValue leftVal = CssDimensionParsingUtils.parseLengthValueToPt(left, em, rem);
        if (leftVal != null) {
            if (leftVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, leftVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CommonCssConstants.LEFT));
            }
        }
    }

    /**
     * Applies the "right" property.
     *
     * @param cssProps the CSS properties
     * @param element the element
     * @param em the em value
     * @param rem the root em value
     * @param layoutPropertyMapping the layout property mapping
     */
    private static void applyRightProperty(Map<String, String> cssProps, IPropertyContainer element, float em, float rem, int layoutPropertyMapping) {
        String right = cssProps.get(CssConstants.RIGHT);
        UnitValue rightVal = CssDimensionParsingUtils.parseLengthValueToPt(right, em, rem);
        if (rightVal != null) {
            if (rightVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, rightVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CommonCssConstants.RIGHT));
            }
        }
    }

    /**
     * Applies the "top" property.
     *
     * @param cssProps the CSS properties
     * @param element the element
     * @param em the em value
     * @param rem the root em value
     * @param layoutPropertyMapping the layout property mapping
     */
    private static void applyTopProperty(Map<String, String> cssProps, IPropertyContainer element, float em, float rem, int layoutPropertyMapping) {
        String top = cssProps.get(CssConstants.TOP);
        UnitValue topVal = CssDimensionParsingUtils.parseLengthValueToPt(top, em, rem);
        if (topVal != null) {
            if (topVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, topVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CommonCssConstants.TOP));
            }
        }
    }

    /**
     * Applies the "bottom" property.
     *
     * @param cssProps the CSS properties
     * @param element the element
     * @param em the em value
     * @param rem the root em value
     * @param layoutPropertyMapping the layout property mapping
     */
    private static void applyBottomProperty(Map<String, String> cssProps, IPropertyContainer element, float em, float rem, int layoutPropertyMapping) {
        String bottom = cssProps.get(CssConstants.BOTTOM);
        UnitValue bottomVal = CssDimensionParsingUtils.parseLengthValueToPt(bottom, em, rem);
        if (bottomVal != null) {
            if (bottomVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, bottomVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CommonCssConstants.BOTTOM));
            }
        }
    }

}
