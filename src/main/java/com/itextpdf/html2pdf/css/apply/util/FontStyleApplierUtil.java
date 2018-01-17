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
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.Underline;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utilities class to apply font styles.
 */
public final class FontStyleApplierUtil {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(FontStyleApplierUtil.class);

    /**
     * Creates a {@link FontStyleApplierUtil} instance.
     */
    private FontStyleApplierUtil() {
    }

    /**
     * Applies font styles to an element.
     *
     * @param cssProps the CSS props
     * @param context the processor context
     * @param stylesContainer the styles container
     * @param element the element
     */
    public static void applyFontStyles(Map<String, String> cssProps, ProcessorContext context, IStylesContainer stylesContainer, IPropertyContainer element) {
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        if (em != 0) {
            element.setProperty(Property.FONT_SIZE, UnitValue.createPointValue(em));
        }

        if (cssProps.get(CssConstants.FONT_FAMILY) != null) {
            element.setProperty(Property.FONT, cssProps.get(CssConstants.FONT_FAMILY));
        }
        if (cssProps.get(CssConstants.FONT_WEIGHT) != null) {
            element.setProperty(Property.FONT_WEIGHT, cssProps.get(CssConstants.FONT_WEIGHT));
        }
        if (cssProps.get(CssConstants.FONT_STYLE) != null) {
            element.setProperty(Property.FONT_STYLE, cssProps.get(CssConstants.FONT_STYLE));
        }

        String cssColorPropValue = cssProps.get(CssConstants.COLOR);
        if (cssColorPropValue != null) {
            TransparentColor transparentColor;
            if (!CssConstants.TRANSPARENT.equals(cssColorPropValue)) {
                float[] rgbaColor = CssUtils.parseRgbaColor(cssColorPropValue);
                Color color = new DeviceRgb(rgbaColor[0], rgbaColor[1], rgbaColor[2]);
                float opacity = rgbaColor[3];
                transparentColor = new TransparentColor(color, opacity);
            } else {
                transparentColor = new TransparentColor(ColorConstants.BLACK, 0f);
            }
            element.setProperty(Property.FONT_COLOR, transparentColor);
        }

        // Make sure to place that before text-align applier
        String direction = cssProps.get(CssConstants.DIRECTION);
        if (CssConstants.RTL.equals(direction)) {
            element.setProperty(Property.BASE_DIRECTION, BaseDirection.RIGHT_TO_LEFT);
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.RIGHT);
        } else if (CssConstants.LTR.equals(direction)) {
            element.setProperty(Property.BASE_DIRECTION, BaseDirection.LEFT_TO_RIGHT);
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.LEFT);
        }

        if (stylesContainer instanceof IElementNode && ((IElementNode) stylesContainer).parentNode() instanceof IElementNode &&
                CssConstants.RTL.equals(((IElementNode) ((IElementNode) stylesContainer).parentNode()).getStyles().get(CssConstants.DIRECTION))) {
            // We should only apply horizontal alignment if parent has dir attribute or direction property
            element.setProperty(Property.HORIZONTAL_ALIGNMENT, HorizontalAlignment.RIGHT);
        }

        // Make sure to place that after direction applier
        String align = cssProps.get(CssConstants.TEXT_ALIGN);
        if (CssConstants.LEFT.equals(align)) {
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.LEFT);
        } else if (CssConstants.RIGHT.equals(align)) {
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.RIGHT);
        } else if (CssConstants.CENTER.equals(align)) {
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.CENTER);
        } else if (CssConstants.JUSTIFY.equals(align)) {
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.JUSTIFIED);
            element.setProperty(Property.SPACING_RATIO, 1f);
        }

        String textDecorationProp = cssProps.get(CssConstants.TEXT_DECORATION);
        if (textDecorationProp != null) {
            String[] textDecorations = textDecorationProp.split("\\s+");
            List<Underline> underlineList = new ArrayList<>();
            for (String textDecoration : textDecorations) {
                if (CssConstants.BLINK.equals(textDecoration)) {
                    logger.error(LogMessageConstant.TEXT_DECORATION_BLINK_NOT_SUPPORTED);
                } else if (CssConstants.LINE_THROUGH.equals(textDecoration)) {
                    underlineList.add(new Underline(null, .75f, 0, 0, 1 / 4f, PdfCanvasConstants.LineCapStyle.BUTT));
                } else if (CssConstants.OVERLINE.equals(textDecoration)) {
                    underlineList.add(new Underline(null, .75f, 0, 0, 9 / 10f, PdfCanvasConstants.LineCapStyle.BUTT));
                } else if (CssConstants.UNDERLINE.equals(textDecoration)) {
                    underlineList.add(new Underline(null, .75f, 0, 0, -1 / 10f, PdfCanvasConstants.LineCapStyle.BUTT));
                } else if (CssConstants.NONE.equals(textDecoration)) {
                    underlineList = null;
                    // if none and any other decoration are used together, none is displayed
                    break;
                }
            }
            element.setProperty(Property.UNDERLINE, underlineList);
        }

        String textIndent = cssProps.get(CssConstants.TEXT_INDENT);
        if (textIndent != null) {
            UnitValue textIndentValue = CssUtils.parseLengthValueToPt(textIndent, em, rem);
            if (textIndentValue != null) {
                if (textIndentValue.isPointValue()) {
                    element.setProperty(Property.FIRST_LINE_INDENT, textIndentValue.getValue());
                } else {
                    logger.error(MessageFormatUtil.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.TEXT_INDENT));
                }
            }
        }

        String letterSpacing = cssProps.get(CssConstants.LETTER_SPACING);
        if (letterSpacing != null && !letterSpacing.equals(CssConstants.NORMAL)) {
            UnitValue letterSpacingValue = CssUtils.parseLengthValueToPt(letterSpacing, em, rem);
            if (letterSpacingValue.isPointValue()) {
                element.setProperty(Property.CHARACTER_SPACING, letterSpacingValue.getValue());
            } else {
                // browsers ignore values in percents
            }
        }

        String wordSpacing = cssProps.get(CssConstants.WORD_SPACING);
        if (wordSpacing != null) {
            UnitValue wordSpacingValue = CssUtils.parseLengthValueToPt(wordSpacing, em, rem);
            if (wordSpacingValue != null) {
                if (wordSpacingValue.isPointValue()) {
                    element.setProperty(Property.WORD_SPACING, wordSpacingValue.getValue());
                } else {
                    // browsers ignore values in percents
                }
            }
        }

        String lineHeight = cssProps.get(CssConstants.LINE_HEIGHT);
        // specification does not give auto as a possible lineHeight value
        // nevertheless some browsers compute it as normal so we apply the same behaviour.
        // What's more, it's basically the same thing as if lineHeight is not set in the first place
        if (lineHeight != null && !CssConstants.NORMAL.equals(lineHeight) && !CssConstants.AUTO.equals(lineHeight)) {
            if (CssUtils.isNumericValue(lineHeight)) {
                Float mult = CssUtils.parseFloat(lineHeight);
                if (mult != null) {
                    element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, (float)mult));
                }
            } else {
                UnitValue lineHeightValue = CssUtils.parseLengthValueToPt(lineHeight, em, rem);
                if (lineHeightValue != null && lineHeightValue.isPointValue()) {
                    element.setProperty(Property.LEADING, new Leading(Leading.FIXED, lineHeightValue.getValue()));
                } else if (lineHeightValue != null) {
                    element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, lineHeightValue.getValue() / 100));
                }
            }
        } else {
            element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, 1.2f));
        }
    }

    /**
     * Parses the absolute font size.
     *
     * @param fontSizeValue the font size value as a {@link String}
     * @return the font size value as a {@code float}
     */
    public static float parseAbsoluteFontSize(String fontSizeValue) {
        if (CssConstants.FONT_ABSOLUTE_SIZE_KEYWORDS.contains(fontSizeValue)) {
            switch (fontSizeValue) {
                case CssConstants.XX_SMALL:
                    fontSizeValue = "9px";
                    break;
                case CssConstants.X_SMALL:
                    fontSizeValue = "10px";
                    break;
                case CssConstants.SMALL:
                    fontSizeValue = "13px";
                    break;
                case CssConstants.MEDIUM:
                    fontSizeValue = "16px";
                    break;
                case CssConstants.LARGE:
                    fontSizeValue = "18px";
                    break;
                case CssConstants.X_LARGE:
                    fontSizeValue = "24px";
                    break;
                case CssConstants.XX_LARGE:
                    fontSizeValue = "32px";
                    break;
                default:
                    fontSizeValue = "16px";
                    break;
            }
        }
        return CssUtils.parseAbsoluteLength(fontSizeValue);
    }

    /**
     * Parses the relative font size.
     *
     * @param relativeFontSizeValue the relative font size value as a {@link String}
     * @param baseValue the base value
     * @return the relative font size value as a {@code float}
     */
    public static float parseRelativeFontSize(final String relativeFontSizeValue, final float baseValue) {
        if (CssConstants.SMALLER.equals(relativeFontSizeValue)) {
            return (float)(baseValue / 1.2);
        } else if (CssConstants.LARGER.equals(relativeFontSizeValue)) {
            return (float)(baseValue * 1.2);
        }
        return CssUtils.parseRelativeValue(relativeFontSizeValue, baseValue);
    }

}
