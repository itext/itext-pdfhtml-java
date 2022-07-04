/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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
import com.itextpdf.html2pdf.css.apply.util.enums.BorderEnum;
import com.itextpdf.html2pdf.css.apply.util.enums.BorderRadii;
import com.itextpdf.html2pdf.css.apply.util.enums.RgbaColorProperty;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.GrooveBorder;
import com.itextpdf.layout.borders.InsetBorder;
import com.itextpdf.layout.borders.OutsetBorder;
import com.itextpdf.layout.borders.RidgeBorder;
import com.itextpdf.layout.borders.RoundDotsBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import java.util.Map;
import java.util.Objects;

/**
 * Utilities class to apply border styles.
 */
public class BorderStyleApplierUtil {

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
        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();

        setBorderProperties(cssProps, element, em, rem);
        setBorderRadiusProperties(cssProps, element, em, rem);
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
        borders[BorderEnum.TOP.ordinal()] = getCertainBorder(CssConstants.BORDER_TOP_WIDTH, CssConstants.BORDER_TOP_STYLE,
                CssConstants.BORDER_TOP_COLOR, em, rem, styles);
        borders[BorderEnum.RIGHT.ordinal()] = getCertainBorder(CssConstants.BORDER_RIGHT_WIDTH, CssConstants.BORDER_RIGHT_STYLE,
                CssConstants.BORDER_RIGHT_COLOR, em, rem, styles);
        borders[BorderEnum.BOTTOM.ordinal()] = getCertainBorder(CssConstants.BORDER_BOTTOM_WIDTH, CssConstants.BORDER_BOTTOM_STYLE,
                CssConstants.BORDER_BOTTOM_COLOR, em, rem, styles);
        borders[BorderEnum.LEFT.ordinal()] = getCertainBorder(CssConstants.BORDER_LEFT_WIDTH, CssConstants.BORDER_LEFT_STYLE,
                CssConstants.BORDER_LEFT_COLOR, em, rem, styles);

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
        if (Objects.isNull(borderStyle) || CssConstants.NONE.equals(borderStyle)) {
            return null;
        }

        if (Objects.isNull(borderWidth)) {
            borderWidth = CssDefaults.getDefaultValue(CssConstants.BORDER_WIDTH);
        }

        float borderWidthValue;
        if (CssConstants.BORDER_WIDTH_VALUES.contains(borderWidth)) {
            switch (borderWidth) {
                case CssConstants.THIN:
                    borderWidth = "1px";
                    break;
                case CssConstants.MEDIUM:
                    borderWidth = "2px";
                    break;
                case CssConstants.THICK:
                    borderWidth = "3px";
                    break;
            }
        }

        UnitValue unitValue = CssDimensionParsingUtils.parseLengthValueToPt(borderWidth, em, rem);
        if (Objects.isNull(unitValue) || unitValue.isPercentValue()) {
            return null;
        }

        borderWidthValue = unitValue.getValue();
        Border border = null;
        if (borderWidthValue > 0) {
            DeviceRgb color = (DeviceRgb) ColorConstants.BLACK;
            float opacity = 1f;
            if (borderColor != null) {
                if (!CssConstants.TRANSPARENT.equals(borderColor)) {
                    float[] rgbaColor = CssDimensionParsingUtils.parseRgbaColor(borderColor);
                    color = new DeviceRgb(rgbaColor[
                            RgbaColorProperty.RED.ordinal()],
                            rgbaColor[RgbaColorProperty.GREEN.ordinal()],
                            rgbaColor[RgbaColorProperty.BLUE.ordinal()]);
                    opacity = rgbaColor[RgbaColorProperty.ALPHA.ordinal()];
                } else {
                    opacity = 0f;
                }
            } else if (CssConstants.GROOVE.equals(borderStyle) || CssConstants.RIDGE.equals(borderStyle)
                    || CssConstants.INSET.equals(borderStyle) || CssConstants.OUTSET.equals(borderStyle)) {
                color = new DeviceRgb(212, 208, 200);
            }
            border = getBorder(borderStyle, borderWidthValue, color, opacity);
        }
        return border;
    }

    private static Border getBorder(String borderStyle, float borderWidthValue, DeviceRgb color, float opacity) {
        switch (borderStyle) {
            case CssConstants.SOLID:
                return new SolidBorder(color, borderWidthValue, opacity);
            case CssConstants.DASHED:
                return new DashedBorder(color, borderWidthValue, opacity);
            case CssConstants.DOTTED:
                return new RoundDotsBorder(color, borderWidthValue, opacity);
            case CssConstants.DOUBLE:
                return new DoubleBorder(color, borderWidthValue, opacity);
            case CssConstants.GROOVE:
                return new GrooveBorder(color, borderWidthValue, opacity);
            case CssConstants.RIDGE:
                return new RidgeBorder(color, borderWidthValue, opacity);
            case CssConstants.INSET:
                return new InsetBorder(color, borderWidthValue, opacity);
            case CssConstants.OUTSET:
                return new OutsetBorder(color, borderWidthValue, opacity);
            default:
                return null;
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
    public static BorderRadius[] getBorderRadiiArray(Map<String, String> styles, float em, float rem) {
        BorderRadius[] borderRadii = new BorderRadius[4];

        BorderRadius borderRadius = null;
        UnitValue borderRadiusUV = CssDimensionParsingUtils
                .parseLengthValueToPt(styles.get(CssConstants.BORDER_RADIUS), em, rem);
        if (Objects.nonNull(borderRadiusUV)) {
            borderRadius = new BorderRadius(borderRadiusUV);
        }
        borderRadii[BorderRadii.TOP_LEFT.ordinal()] = getBorderRadius(CssConstants.BORDER_TOP_LEFT_RADIUS,
                borderRadius, em, rem, styles);
        borderRadii[BorderRadii.TOP_RIGHT.ordinal()] = getBorderRadius(CssConstants.BORDER_TOP_RIGHT_RADIUS,
                borderRadius, em, rem, styles);
        borderRadii[BorderRadii.BOTTOM_RIGHT.ordinal()] = getBorderRadius(CssConstants.BORDER_BOTTOM_RIGHT_RADIUS,
                borderRadius, em, rem, styles);
        borderRadii[BorderRadii.BOTTOM_LEFT.ordinal()] = getBorderRadius(CssConstants.BORDER_BOTTOM_LEFT_RADIUS,
                borderRadius, em, rem, styles);

        return borderRadii;
    }

    private static String getSpecificBorderColorOrDefaultColor(Map<String, String> styles, String specificBorderColorProperty) {
        String borderColor = styles.get(specificBorderColorProperty);
        if (borderColor == null || CssConstants.CURRENTCOLOR.equals(borderColor)) {
            borderColor = styles.get(CssConstants.COLOR);
        }
        return borderColor;
    }

    private static Border getCertainBorder(String widthConstant, String styleConstant, String colorConstant,
                                           float em, float rem, Map<String, String> styles) {
        String widthCssValue = styles.get(widthConstant);
        String styleCssValue = styles.get(styleConstant);
        String colorCssValue = getSpecificBorderColorOrDefaultColor(styles, colorConstant);
        return getCertainBorder(widthCssValue, styleCssValue, colorCssValue, em, rem);
    }

    private static BorderRadius getBorderRadius(UnitValue[] borderRadiusUnitValue, BorderRadius borderRadius) {
        if(Objects.isNull(borderRadiusUnitValue)) {
            return borderRadius;
        }
        return new BorderRadius(borderRadiusUnitValue[0], borderRadiusUnitValue[1]);
    }

    private static BorderRadius getBorderRadius(String cssConstant, BorderRadius borderRadius, float em, float rem,
                                                Map<String, String> cssProps) {
        UnitValue[] borderRadiusUnitValue = CssDimensionParsingUtils
                .parseSpecificCornerBorderRadius(cssProps.get(cssConstant), em, rem);
        return getBorderRadius(borderRadiusUnitValue, borderRadius);
    }

    private static void setBorderRadiusProperties(Map<String, String> cssProps, IPropertyContainer element, float em, float rem) {
        BorderRadius[] borderRadii = getBorderRadiiArray(cssProps, em, rem);
        setBorderRadiusProperty(element, borderRadii[BorderRadii.TOP_LEFT.ordinal()], Property.BORDER_TOP_LEFT_RADIUS);
        setBorderRadiusProperty(element, borderRadii[BorderRadii.TOP_RIGHT.ordinal()], Property.BORDER_TOP_RIGHT_RADIUS);
        setBorderRadiusProperty(element, borderRadii[BorderRadii.BOTTOM_RIGHT.ordinal()], Property.BORDER_BOTTOM_RIGHT_RADIUS);
        setBorderRadiusProperty(element, borderRadii[BorderRadii.BOTTOM_LEFT.ordinal()], Property.BORDER_BOTTOM_LEFT_RADIUS);
    }

    private static void setBorderProperties(Map<String, String> cssProps, IPropertyContainer element, float em, float rem) {
        Border[] borders = getBordersArray(cssProps, em, rem);
        setBorderProperty(element, borders[BorderEnum.TOP.ordinal()], Property.BORDER_TOP);
        setBorderProperty(element, borders[BorderEnum.RIGHT.ordinal()], Property.BORDER_RIGHT);
        setBorderProperty(element, borders[BorderEnum.BOTTOM.ordinal()], Property.BORDER_BOTTOM);
        setBorderProperty(element, borders[BorderEnum.LEFT.ordinal()], Property.BORDER_LEFT);
    }

    private static void setBorderRadiusProperty(IPropertyContainer element, BorderRadius borderRadius, int propertyValue) {
        if(Objects.nonNull(borderRadius)) {
            element.setProperty(propertyValue, borderRadius);
        }
    }

    private static void setBorderProperty(IPropertyContainer element, Border border, int propertyValue) {
        if (Objects.nonNull(border)) {
            element.setProperty(propertyValue, border);
        }
    }

}
