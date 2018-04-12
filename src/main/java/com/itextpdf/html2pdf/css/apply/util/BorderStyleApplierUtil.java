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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.resolve.CssDefaults;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.GrooveBorder;
import com.itextpdf.layout.borders.InsetBorder;
import com.itextpdf.layout.borders.OutsetBorder;
import com.itextpdf.layout.borders.RidgeBorder;
import com.itextpdf.layout.borders.RoundDotsBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply border styles.
 */
public class BorderStyleApplierUtil {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BorderStyleApplierUtil.class);

    /**
     * Creates a new {@link BorderStyleApplierUtil} instance.
     */
    private BorderStyleApplierUtil() {
    }

    /**
     * Applies borders to an element.
     *
     * @param cssProps the CSS properties
     * @param context  the Processor context
     * @param element  the element
     */
    public static void applyBorders(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();

        Border[] bordersArray = getBordersArray(cssProps, em, rem);
        if (bordersArray[0] != null) {
            element.setProperty(Property.BORDER_TOP, bordersArray[0]);
        }

        if (bordersArray[1] != null) {
            element.setProperty(Property.BORDER_RIGHT, bordersArray[1]);
        }

        if (bordersArray[2] != null) {
            element.setProperty(Property.BORDER_BOTTOM, bordersArray[2]);
        }

        if (bordersArray[3] != null) {
            element.setProperty(Property.BORDER_LEFT, bordersArray[3]);
        }

        BorderRadius[] borderRadii = getBorderRadiiArray(cssProps, em, rem);
        if (borderRadii[0] != null) {
            element.setProperty(Property.BORDER_TOP_LEFT_RADIUS, borderRadii[0]);
        }

        if (borderRadii[1] != null) {
            element.setProperty(Property.BORDER_TOP_RIGHT_RADIUS, borderRadii[1]);
        }

        if (borderRadii[2] != null) {
            element.setProperty(Property.BORDER_BOTTOM_RIGHT_RADIUS, borderRadii[2]);
        }

        if (borderRadii[3] != null) {
            element.setProperty(Property.BORDER_BOTTOM_LEFT_RADIUS, borderRadii[3]);
        }
    }

    /**
     * Gets the array that defines the borders.
     *
     * @param styles the styles mapping
     * @param em     the em value
     * @param rem    the root em value
     * @return the borders array
     */
    public static Border[] getBordersArray(Map<String, String> styles, float em, float rem) {
        Border[] borders = new Border[4];
        Border topBorder = getCertainBorder(styles.get(CssConstants.BORDER_TOP_WIDTH),
                styles.get(CssConstants.BORDER_TOP_STYLE), getSpecificBorderColorOrDefaultColor(styles, CssConstants.BORDER_TOP_COLOR), em, rem);
        borders[0] = topBorder;

        Border rightBorder = getCertainBorder(styles.get(CssConstants.BORDER_RIGHT_WIDTH),
                styles.get(CssConstants.BORDER_RIGHT_STYLE), getSpecificBorderColorOrDefaultColor(styles, CssConstants.BORDER_RIGHT_COLOR), em, rem);
        borders[1] = rightBorder;

        Border bottomBorder = getCertainBorder(styles.get(CssConstants.BORDER_BOTTOM_WIDTH),
                styles.get(CssConstants.BORDER_BOTTOM_STYLE), getSpecificBorderColorOrDefaultColor(styles, CssConstants.BORDER_BOTTOM_COLOR), em, rem);
        borders[2] = bottomBorder;

        Border leftBorder = getCertainBorder(styles.get(CssConstants.BORDER_LEFT_WIDTH),
                styles.get(CssConstants.BORDER_LEFT_STYLE), getSpecificBorderColorOrDefaultColor(styles, CssConstants.BORDER_LEFT_COLOR), em, rem);
        borders[3] = leftBorder;

        return borders;
    }

    /**
     * Creates a {@link Border} instance based on specific properties.
     *
     * @param borderWidth the border width
     * @param borderStyle the border style
     * @param borderColor the border color
     * @param em          the em value
     * @param rem         the root em value
     * @return the border
     */
    public static Border getCertainBorder(String borderWidth, String borderStyle, String borderColor, float em, float rem) {
        if (borderStyle == null || CssConstants.NONE.equals(borderStyle)) {
            return null;
        }

        if (borderWidth == null) {
            borderWidth = CssDefaults.getDefaultValue(CssConstants.BORDER_WIDTH);
        }

        float borderWidthValue;
        if (CssConstants.BORDER_WIDTH_VALUES.contains(borderWidth)) {
            if (CssConstants.THIN.equals(borderWidth)) {
                borderWidth = "1px";
            } else if (CssConstants.MEDIUM.equals(borderWidth)) {
                borderWidth = "2px";
            } else if (CssConstants.THICK.equals(borderWidth)) {
                borderWidth = "3px";
            }
        }

        UnitValue unitValue = CssUtils.parseLengthValueToPt(borderWidth, em, rem);
        if (unitValue == null) {
            return null;
        }
        if (unitValue.isPercentValue()) {
            LOGGER.error("border-width in percents is not supported");
            return null;
        }

        borderWidthValue = unitValue.getValue();
        Border border = null;
        if (borderWidthValue > 0) {
            DeviceRgb color = (DeviceRgb) ColorConstants.BLACK;
            float opacity = 1f;
            if (borderColor != null) {
                if (!CssConstants.TRANSPARENT.equals(borderColor)) {
                    float[] rgbaColor = CssUtils.parseRgbaColor(borderColor);
                    color = new DeviceRgb(rgbaColor[0], rgbaColor[1], rgbaColor[2]);
                    opacity = rgbaColor[3];
                } else {
                    opacity = 0f;
                }
            } else if (CssConstants.GROOVE.equals(borderStyle) || CssConstants.RIDGE.equals(borderStyle)
                    || CssConstants.INSET.equals(borderStyle) || CssConstants.OUTSET.equals(borderStyle)) {
                color = new DeviceRgb(212, 208, 200);
            }
            switch (borderStyle) {
                case CssConstants.SOLID:
                    border = new SolidBorder(color, borderWidthValue, opacity);
                    break;
                case CssConstants.DASHED:
                    border = new DashedBorder(color, borderWidthValue, opacity);
                    break;
                case CssConstants.DOTTED:
                    border = new RoundDotsBorder(color, borderWidthValue, opacity);
                    break;
                case CssConstants.DOUBLE:
                    border = new DoubleBorder(color, borderWidthValue, opacity);
                    break;
                case CssConstants.GROOVE:
                    border = new GrooveBorder(color, borderWidthValue, opacity);
                    break;
                case CssConstants.RIDGE:
                    border = new RidgeBorder(color, borderWidthValue, opacity);
                    break;
                case CssConstants.INSET:
                    border = new InsetBorder(color, borderWidthValue, opacity);
                    break;
                case CssConstants.OUTSET:
                    border = new OutsetBorder(color, borderWidthValue, opacity);
                    break;
                default:
                    border = null;
                    break;
            }
        }
        return border;
    }

    /**
     * Gets the array that defines the borders.
     *
     * @param styles the styles mapping
     * @param em     the em value
     * @param rem    the root em value
     * @return the borders array
     */
    public static BorderRadius[] getBorderRadiiArray(Map<String, String> styles, float em, float rem) {
        BorderRadius[] borderRadii = new BorderRadius[4];

        BorderRadius borderRadius = null;
        UnitValue borderRadiusUV = CssUtils.parseLengthValueToPt(styles.get(CssConstants.BORDER_RADIUS), em, rem);
        if (null != borderRadiusUV) {
            borderRadius = new BorderRadius(borderRadiusUV);
        }

        UnitValue[] borderTopLeftRadiusUV = CssUtils.parseSpecificCornerBorderRadius(styles.get(CssConstants.BORDER_TOP_LEFT_RADIUS), em, rem);
        borderRadii[0] = null == borderTopLeftRadiusUV
                ? borderRadius
                : new BorderRadius(borderTopLeftRadiusUV[0], borderTopLeftRadiusUV[1]);
        UnitValue[] borderTopRightRadiusUV = CssUtils.parseSpecificCornerBorderRadius(styles.get(CssConstants.BORDER_TOP_RIGHT_RADIUS), em, rem);
        borderRadii[1] = null == borderTopRightRadiusUV
                ? borderRadius
                : new BorderRadius(borderTopRightRadiusUV[0], borderTopRightRadiusUV[1]);
        UnitValue[] borderBottomRightRadiusUV = CssUtils.parseSpecificCornerBorderRadius(styles.get(CssConstants.BORDER_BOTTOM_RIGHT_RADIUS), em, rem);
        borderRadii[2] = null == borderBottomRightRadiusUV
                ? borderRadius
                : new BorderRadius(borderBottomRightRadiusUV[0], borderBottomRightRadiusUV[1]);
        UnitValue[] borderBottomLeftRadiusUV = CssUtils.parseSpecificCornerBorderRadius(styles.get(CssConstants.BORDER_BOTTOM_LEFT_RADIUS), em, rem);
        borderRadii[3] = null == borderBottomLeftRadiusUV
                ? borderRadius
                : new BorderRadius(borderBottomLeftRadiusUV[0], borderBottomLeftRadiusUV[1]);

        return borderRadii;
    }

    /**
     * Gets the array that defines the borders.
     *
     * @param styles the styles mapping
     * @param em     the em value
     * @param rem    the root em value
     * @return the borders array
     * @deprecated use {@link #getBorderRadiiArray(Map, float, float)} instead
     */
    @Deprecated
    public static UnitValue getBorderRadius(Map<String, String> styles, float em, float rem) {
        String borderRadius = styles.get(CssConstants.BORDER_RADIUS);
        return CssUtils.parseLengthValueToPt(borderRadius, em, rem);
    }

    private static String getSpecificBorderColorOrDefaultColor(Map<String, String> styles, String specificBorderColorProperty) {
        String borderColor = styles.get(specificBorderColorProperty);
        if (borderColor == null || CssConstants.CURRENTCOLOR.equals(borderColor)) {
            borderColor = styles.get(CssConstants.COLOR);
        }
        return borderColor;
    }

}
