/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
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
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();

        Border outline = getCertainBorder(cssProps.get(CssConstants.OUTLINE_WIDTH),
                cssProps.get(CssConstants.OUTLINE_STYLE), getSpecificOutlineColorOrDefaultColor(cssProps, CssConstants.OUTLINE_COLOR), em, rem);
        if (outline != null) {
            element.setProperty(Property.OUTLINE, outline);
        }

        if (cssProps.get(CssConstants.OUTLINE_OFFSET) != null && element.<Border>getProperty(Property.OUTLINE) != null) {
            UnitValue unitValue = CssUtils.parseLengthValueToPt(cssProps.get(CssConstants.OUTLINE_OFFSET), em, rem);
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

        UnitValue unitValue = CssUtils.parseLengthValueToPt(outlineWidth, em, rem);
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
            DeviceRgb color = (DeviceRgb) ColorConstants.BLACK;
            float opacity = 1f;
            if (outlineColor != null) {
                if (!CssConstants.TRANSPARENT.equals(outlineColor)) {
                    float[] rgbaColor = CssUtils.parseRgbaColor(outlineColor);
                    color = new DeviceRgb(rgbaColor[0], rgbaColor[1], rgbaColor[2]);
                    opacity = rgbaColor[3];
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
                    outline = new GrooveBorder(color, outlineWidthValue, opacity);
                    break;
                case CssConstants.RIDGE:
                    outline = new RidgeBorder(color, outlineWidthValue, opacity);
                    break;
                case CssConstants.INSET:
                    outline = new InsetBorder(color, outlineWidthValue, opacity);
                    break;
                case CssConstants.OUTSET:
                    outline = new OutsetBorder(color, outlineWidthValue, opacity);
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
