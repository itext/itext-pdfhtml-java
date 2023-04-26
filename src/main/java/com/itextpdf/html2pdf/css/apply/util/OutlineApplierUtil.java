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


import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
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
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TransparentColor;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OutlineApplierUtil {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OutlineApplierUtil.class);

    /**
     * Creates a new {@link OutlineApplierUtil} instance.
     */
    private OutlineApplierUtil() {
    }

    /**
     * Applies outlines to an element.
     *
     * @param cssProps the CSS properties
     * @param context  the Processor context
     * @param element  the element
     */
    public static void applyOutlines(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();

        Border outline = getCertainBorder(cssProps.get(CssConstants.OUTLINE_WIDTH),
                cssProps.get(CssConstants.OUTLINE_STYLE), getSpecificOutlineColorOrDefaultColor(cssProps, CssConstants.OUTLINE_COLOR), em, rem);
        if (outline != null) {
            element.setProperty(Property.OUTLINE, outline);
        }

        if (cssProps.get(CssConstants.OUTLINE_OFFSET) != null && element.<Border>getProperty(Property.OUTLINE) != null) {
            UnitValue unitValue = CssDimensionParsingUtils
                    .parseLengthValueToPt(cssProps.get(CssConstants.OUTLINE_OFFSET), em, rem);
            if (unitValue != null) {
                if (unitValue.isPercentValue())
                    LOGGER.error("outline-width in percents is not supported");
                else if (unitValue.getValue() != 0)
                    element.setProperty(Property.OUTLINE_OFFSET, unitValue.getValue());
            }
        }
    }

    /**
     * Creates a {@link Border} instance based on specific properties.
     *
     * @param outlineWidth the outline width
     * @param outlineStyle the outline style
     * @param outlineColor the outline color
     * @param em           the em value
     * @param rem          the root em value
     * @return the border
     */
    public static Border getCertainBorder(String outlineWidth, String outlineStyle, String outlineColor, float em, float rem) {
        if (outlineStyle == null || CssConstants.NONE.equals(outlineStyle)) {
            return null;
        }

        if (outlineWidth == null) {
            outlineWidth = CssDefaults.getDefaultValue(CssConstants.OUTLINE_WIDTH);
        }

        float outlineWidthValue;
        if (CssConstants.BORDER_WIDTH_VALUES.contains(outlineWidth)) {
            if (CssConstants.THIN.equals(outlineWidth)) {
                outlineWidth = "1px";
            } else if (CssConstants.MEDIUM.equals(outlineWidth)) {
                outlineWidth = "2px";
            } else if (CssConstants.THICK.equals(outlineWidth)) {
                outlineWidth = "3px";
            }
        }

        UnitValue unitValue = CssDimensionParsingUtils.parseLengthValueToPt(outlineWidth, em, rem);
        if (unitValue == null) {
            return null;
        }
        if (unitValue.isPercentValue()) {
            LOGGER.error("outline-width in percents is not supported");
            return null;
        }

        outlineWidthValue = unitValue.getValue();
        Border outline = null;
        if (outlineWidthValue > 0) {
            Color color = ColorConstants.BLACK;
            float opacity = 1f;
            if (outlineColor != null) {
                if (!CssConstants.TRANSPARENT.equals(outlineColor)) {
                    TransparentColor tColor = CssDimensionParsingUtils.parseColor(outlineColor);
                    color = tColor.getColor();
                    opacity = tColor.getOpacity();
                } else {
                    opacity = 0f;
                }
            } else if (CssConstants.GROOVE.equals(outlineStyle) || CssConstants.RIDGE.equals(outlineStyle)
                    || CssConstants.INSET.equals(outlineStyle) || CssConstants.OUTSET.equals(outlineStyle)) {
                color = new DeviceRgb(212, 208, 200);
            }
            switch (outlineStyle) {
                case CssConstants.SOLID:
                case CssConstants.AUTO:
                    outline = new SolidBorder(color, outlineWidthValue, opacity);
                    break;
                case CssConstants.DASHED:
                    outline = new DashedBorder(color, outlineWidthValue, opacity);
                    break;
                case CssConstants.DOTTED:
                    outline = new DottedBorder(color, outlineWidthValue, opacity);
                    break;
                case CssConstants.DOUBLE:
                    outline = new DoubleBorder(color, outlineWidthValue, opacity);
                    break;
                case CssConstants.GROOVE:
                    if (color instanceof DeviceRgb) {
                        outline = new GrooveBorder((DeviceRgb)color, outlineWidthValue, opacity);
                    }
                    if (color instanceof DeviceCmyk) {
                        outline = new GrooveBorder((DeviceCmyk)color, outlineWidthValue, opacity);
                    }
                    break;
                case CssConstants.RIDGE:
                    if (color instanceof DeviceRgb) {
                        outline = new RidgeBorder((DeviceRgb)color, outlineWidthValue, opacity);
                    }
                    if (color instanceof DeviceCmyk) {
                        outline = new RidgeBorder((DeviceCmyk)color, outlineWidthValue, opacity);
                    }
                    break;
                case CssConstants.INSET:
                    if (color instanceof DeviceRgb) {
                        outline = new InsetBorder((DeviceRgb)color, outlineWidthValue, opacity);
                    }
                    if (color instanceof DeviceCmyk) {
                        outline = new InsetBorder((DeviceCmyk)color, outlineWidthValue, opacity);
                    }
                    break;
                case CssConstants.OUTSET:
                    if (color instanceof DeviceRgb) {
                        outline = new OutsetBorder((DeviceRgb)color, outlineWidthValue, opacity);
                    }
                    if (color instanceof DeviceCmyk) {
                        outline = new OutsetBorder((DeviceCmyk)color, outlineWidthValue, opacity);
                    }
                    break;
                default:
                    outline = null;
                    break;
            }
        }
        return outline;
    }

    private static String getSpecificOutlineColorOrDefaultColor(Map<String, String> styles, String specificOutlineColorProperty) {
        String outlineColor = styles.get(specificOutlineColorProperty);
        if (outlineColor == null || CssConstants.CURRENTCOLOR.equals(outlineColor)) {
            outlineColor = styles.get(CssConstants.COLOR);
        } else if (CssConstants.INVERT.equals(outlineColor)) {
            LOGGER.warn("Invert color for outline is not supported");
            outlineColor = styles.get(CssConstants.COLOR);
        }
        return outlineColor;
    }
}
