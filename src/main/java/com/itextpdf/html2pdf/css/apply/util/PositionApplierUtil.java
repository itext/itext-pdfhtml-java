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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
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
//            float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
//            applyLeftProperty(cssProps, element, em, Property.X);
//            applyTopProperty(cssProps, element, em, Property.Y);
            // TODO
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
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
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
        UnitValue leftVal = CssUtils.parseLengthValueToPt(left, em, rem);
        if (leftVal != null) {
            if (leftVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, leftVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.LEFT));
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
        UnitValue rightVal = CssUtils.parseLengthValueToPt(right, em, rem);
        if (rightVal != null) {
            if (rightVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, rightVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.RIGHT));
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
        UnitValue topVal = CssUtils.parseLengthValueToPt(top, em, rem);
        if (topVal != null) {
            if (topVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, topVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.TOP));
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
        UnitValue bottomVal = CssUtils.parseLengthValueToPt(bottom, em, rem);
        if (bottomVal != null) {
            if (bottomVal.isPointValue()) {
                element.setProperty(layoutPropertyMapping, bottomVal.getValue());
            } else {
                logger.error(MessageFormatUtil.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.BOTTOM));
            }
        }
    }

}
