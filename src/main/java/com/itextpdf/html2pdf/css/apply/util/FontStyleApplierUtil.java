/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.font.FontFamilySplitter;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.LineHeight;
import com.itextpdf.layout.property.OverflowWrapPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.Underline;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;
import com.itextpdf.styledxmlparser.css.util.CssTypesValidationUtils;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.layout.splitting.BreakAllSplitCharacters;
import com.itextpdf.layout.splitting.KeepAllSplitCharacters;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class to apply font styles.
 */
public final class FontStyleApplierUtil {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(FontStyleApplierUtil.class);

    private static final float DEFAULT_LINE_HEIGHT = 1.2f;

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
        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        if (em != 0) {
            element.setProperty(Property.FONT_SIZE, UnitValue.createPointValue(em));
        }

        if (cssProps.get(CssConstants.FONT_FAMILY) != null) {
            // TODO DEVSIX-2534
            List<String> fontFamilies = FontFamilySplitter.splitFontFamily(cssProps.get(CssConstants.FONT_FAMILY));
            element.setProperty(Property.FONT, fontFamilies.toArray(new String[fontFamilies.size()]));
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
                float[] rgbaColor = CssDimensionParsingUtils.parseRgbaColor(cssColorPropValue);
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
                CssConstants.RTL.equals(((IElementNode) ((IElementNode) stylesContainer).parentNode()).getStyles().get(CssConstants.DIRECTION)) &&
                !element.hasProperty(Property.HORIZONTAL_ALIGNMENT)) {
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

        String whiteSpace = cssProps.get(CssConstants.WHITE_SPACE);
        boolean textWrappingDisabled = CssConstants.NOWRAP.equals(whiteSpace) || CssConstants.PRE.equals(whiteSpace);
        element.setProperty(Property.NO_SOFT_WRAP_INLINE, textWrappingDisabled);

        if (!textWrappingDisabled) {
            String overflowWrap = cssProps.get(CssConstants.OVERFLOW_WRAP);
            if (CssConstants.ANYWHERE.equals(overflowWrap)) {
                element.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.ANYWHERE);
            } else if (CssConstants.BREAK_WORD.equals(overflowWrap)) {
                element.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.BREAK_WORD);
            } else {
                element.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.NORMAL);
            }

            String wordBreak = cssProps.get(CssConstants.WORD_BREAK);
            if (CssConstants.BREAK_ALL.equals(wordBreak)) {
                element.setProperty(Property.SPLIT_CHARACTERS, new BreakAllSplitCharacters());
            } else if (CssConstants.KEEP_ALL.equals(wordBreak)) {
                element.setProperty(Property.SPLIT_CHARACTERS, new KeepAllSplitCharacters());
            } else if (CssConstants.BREAK_WORD.equals(wordBreak)) {
                // CSS specification cite that describes the reason for overflow-wrap overriding:
                // "For compatibility with legacy content, the word-break property also supports
                //  a deprecated break-word keyword. When specified, this has the same effect
                //  as word-break: normal and overflow-wrap: anywhere, regardless of the actual value
                //  of the overflow-wrap property."
                element.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.BREAK_WORD);
                element.setProperty(Property.SPLIT_CHARACTERS, new DefaultSplitCharacters());
            } else {
                element.setProperty(Property.SPLIT_CHARACTERS, new DefaultSplitCharacters());
            }
        }

        float [] colors = new float[4];
        Color textDecorationColor;
        float opacity = 1f;
        String textDecorationColorProp = cssProps.get(CssConstants.TEXT_DECORATION_COLOR);
        if (textDecorationColorProp == null || CssConstants.CURRENTCOLOR.equals(textDecorationColorProp)) {
            if (element.<TransparentColor>getProperty(Property.FONT_COLOR) != null) {
                TransparentColor transparentColor = element.<TransparentColor>getProperty(Property.FONT_COLOR);
                textDecorationColor = transparentColor.getColor();
                opacity = transparentColor.getOpacity();
            } else {
                textDecorationColor = ColorConstants.BLACK;
            }
        } else {
            if (textDecorationColorProp.startsWith("hsl")) {
                logger.error(LogMessageConstant.HSL_COLOR_NOT_SUPPORTED);
                textDecorationColor = ColorConstants.BLACK;
            } else {
                colors = CssDimensionParsingUtils.parseRgbaColor(textDecorationColorProp);
                textDecorationColor = new DeviceRgb(colors[0], colors[1], colors[2]);;
                opacity = colors[3];
            }
        }

        String textDecorationLineProp = cssProps.get(CssConstants.TEXT_DECORATION_LINE);
        if (textDecorationLineProp != null) {
            String[] textDecorationLines = textDecorationLineProp.split("\\s+");
            List<Underline> underlineList = new ArrayList<>();
            for (String textDecorationLine : textDecorationLines) {
                if (CssConstants.BLINK.equals(textDecorationLine)) {
                    logger.error(LogMessageConstant.TEXT_DECORATION_BLINK_NOT_SUPPORTED);
                } else if (CssConstants.LINE_THROUGH.equals(textDecorationLine)) {
                    underlineList.add(new Underline(textDecorationColor, opacity, .75f, 0, 0, 1 / 4f, PdfCanvasConstants.LineCapStyle.BUTT));
                } else if (CssConstants.OVERLINE.equals(textDecorationLine)) {
                    underlineList.add(new Underline(textDecorationColor, opacity, .75f, 0, 0, 9 / 10f, PdfCanvasConstants.LineCapStyle.BUTT));
                } else if (CssConstants.UNDERLINE.equals(textDecorationLine)) {
                    underlineList.add(new Underline(textDecorationColor, opacity, .75f, 0, 0, -1 / 10f, PdfCanvasConstants.LineCapStyle.BUTT));
                } else if (CssConstants.NONE.equals(textDecorationLine)) {
                    underlineList = null;
                    // if none and any other decoration are used together, none is displayed
                    break;
                }
            }
            element.setProperty(Property.UNDERLINE, underlineList);
        }

        String textIndent = cssProps.get(CssConstants.TEXT_INDENT);
        if (textIndent != null) {
            UnitValue textIndentValue = CssDimensionParsingUtils.parseLengthValueToPt(textIndent, em, rem);
            if (textIndentValue != null) {
                if (textIndentValue.isPointValue()) {
                    element.setProperty(Property.FIRST_LINE_INDENT, textIndentValue.getValue());
                } else {
                    logger.error(MessageFormatUtil.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.TEXT_INDENT));
                }
            }
        }

        String letterSpacing = cssProps.get(CssConstants.LETTER_SPACING);
        if (letterSpacing != null && !CssConstants.NORMAL.equals(letterSpacing)) {
            UnitValue letterSpacingValue = CssDimensionParsingUtils.parseLengthValueToPt(letterSpacing, em, rem);
            if (letterSpacingValue.isPointValue()) {
                element.setProperty(Property.CHARACTER_SPACING, letterSpacingValue.getValue());
            } else {
                // browsers ignore values in percents
            }
        }

        String wordSpacing = cssProps.get(CssConstants.WORD_SPACING);
        if (wordSpacing != null) {
            UnitValue wordSpacingValue = CssDimensionParsingUtils.parseLengthValueToPt(wordSpacing, em, rem);
            if (wordSpacingValue != null) {
                if (wordSpacingValue.isPointValue()) {
                    element.setProperty(Property.WORD_SPACING, wordSpacingValue.getValue());
                } else {
                    // browsers ignore values in percents
                }
            }
        }

        String lineHeight = cssProps.get(CssConstants.LINE_HEIGHT);
        setLineHeight(element, lineHeight, em, rem);
        setLineHeightByLeading(element, lineHeight, em, rem);
    }

    private static void setLineHeight(IPropertyContainer elementToSet, String lineHeight, float em, float rem) {
        if (lineHeight != null && !CssConstants.NORMAL.equals(lineHeight) && !CssConstants.AUTO.equals(lineHeight)) {
            if (CssTypesValidationUtils.isNumericValue(lineHeight)) {
                Float number = CssDimensionParsingUtils.parseFloat(lineHeight);
                if (number != null) {
                    elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createMultipliedValue((float)number));
                } else {
                    elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createNormalValue());
                }
            } else {
                UnitValue lineHeightValue = CssDimensionParsingUtils.parseLengthValueToPt(lineHeight, em, rem);
                if (lineHeightValue != null && lineHeightValue.isPointValue()) {
                    elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createFixedValue(lineHeightValue.getValue()));
                } else if (lineHeightValue != null) {
                    elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createMultipliedValue(lineHeightValue.getValue() / 100f));
                } else {
                    elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createNormalValue());
                }
            }
        } else {
            elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createNormalValue());
        }
    }

    private static void setLineHeightByLeading(IPropertyContainer element, String lineHeight, float em, float rem) {
        // specification does not give auto as a possible lineHeight value
        // nevertheless some browsers compute it as normal so we apply the same behaviour.
        // What's more, it's basically the same thing as if lineHeight is not set in the first place
        if (lineHeight != null && !CssConstants.NORMAL.equals(lineHeight) && !CssConstants.AUTO.equals(lineHeight)) {
            if (CssTypesValidationUtils.isNumericValue(lineHeight)) {
                Float mult = CssDimensionParsingUtils.parseFloat(lineHeight);
                if (mult != null) {
                    element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, (float)mult));
                }
            } else {
                UnitValue lineHeightValue = CssDimensionParsingUtils.parseLengthValueToPt(lineHeight, em, rem);
                if (lineHeightValue != null && lineHeightValue.isPointValue()) {
                    element.setProperty(Property.LEADING, new Leading(Leading.FIXED, lineHeightValue.getValue()));
                } else if (lineHeightValue != null) {
                    element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, lineHeightValue.getValue() / 100));
                }
            }
        } else {
            element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, DEFAULT_LINE_HEIGHT));
        }
    }

}
