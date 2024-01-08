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
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants.LineCapStyle;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.BaseDirection;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.Leading;
import com.itextpdf.layout.properties.LineHeight;
import com.itextpdf.layout.properties.OverflowWrapPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.TransparentColor;
import com.itextpdf.layout.properties.Underline;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssTypesValidationUtils;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.layout.splitting.BreakAllSplitCharacters;
import com.itextpdf.layout.splitting.KeepAllSplitCharacters;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.util.FontFamilySplitterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class to apply font styles.
 */
public final class FontStyleApplierUtil {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(FontStyleApplierUtil.class);

    private static final float DEFAULT_LINE_HEIGHT = 1.2f;
    private static final float TEXT_DECORATION_LINE_DEFAULT_THICKNESS = .75F;
    private static final float TEXT_DECORATION_LINE_THROUGH_Y_POS = 1 / 4F;
    private static final float TEXT_DECORATION_LINE_OVER_Y_POS = 9 / 10F;
    private static final float TEXT_DECORATION_LIN_UNDER_Y_POS = -1 / 10F;

    /**
     * Creates a {@link FontStyleApplierUtil} instance.
     */
    private FontStyleApplierUtil() {
    }

    /**
     * Applies font styles to an element.
     *
     * @param cssProps        the CSS props
     * @param context         the processor context
     * @param stylesContainer the styles container
     * @param element         the element
     */
    public static void applyFontStyles(Map<String, String> cssProps, ProcessorContext context,
            IStylesContainer stylesContainer, IPropertyContainer element) {
        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        if (em != 0) {
            element.setProperty(Property.FONT_SIZE, UnitValue.createPointValue(em));
        }

        if (cssProps.get(CssConstants.FONT_FAMILY) != null) {
            // TODO DEVSIX-2534
            List<String> fontFamilies = FontFamilySplitterUtil.splitFontFamily(cssProps.get(CssConstants.FONT_FAMILY));
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
                TransparentColor tColor = CssDimensionParsingUtils.parseColor(cssColorPropValue);
                Color color = tColor.getColor();
                float opacity = tColor.getOpacity();
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
            // For list items default behaviour differs from other elements:
            // only the list symbol should be aligned differently
            if (!CssConstants.LIST_ITEM.equals(cssProps.get(CssConstants.DISPLAY))) {
                element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.RIGHT);
            }
        } else if (CssConstants.LTR.equals(direction)) {
            element.setProperty(Property.BASE_DIRECTION, BaseDirection.LEFT_TO_RIGHT);
            if (!CssConstants.LIST_ITEM.equals(cssProps.get(CssConstants.DISPLAY))) {
                element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.LEFT);
            }
        }

        if (stylesContainer instanceof IElementNode
                && ((IElementNode) stylesContainer).parentNode() instanceof IElementNode &&
                CssConstants.RTL.equals(((IElementNode) ((IElementNode) stylesContainer).parentNode()).getStyles()
                        .get(CssConstants.DIRECTION)) &&
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
            } else if (CommonCssConstants.BREAK_WORD.equals(overflowWrap)) {
                element.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.BREAK_WORD);
            } else {
                element.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.NORMAL);
            }

            String wordBreak = cssProps.get(CssConstants.WORD_BREAK);
            if (CssConstants.BREAK_ALL.equals(wordBreak)) {
                element.setProperty(Property.SPLIT_CHARACTERS, new BreakAllSplitCharacters());
            } else if (CssConstants.KEEP_ALL.equals(wordBreak)) {
                element.setProperty(Property.SPLIT_CHARACTERS, new KeepAllSplitCharacters());
            } else if (CommonCssConstants.BREAK_WORD.equals(wordBreak)) {
                // CSS specification cite that describes the reason for overflow-wrap overriding:
                // "For compatibility with legacy content, the word-break property also supports
                //  a deprecated break-word keyword. When specified, this has the same effect
                //  as word-break: normal and overflow-wrap: anywhere, regardless of the actual value
                //  of the overflow-wrap property."
                element.setProperty(Property.OVERFLOW_WRAP, OverflowWrapPropertyValue.ANYWHERE);
                element.setProperty(Property.SPLIT_CHARACTERS, new DefaultSplitCharacters());
            } else {
                element.setProperty(Property.SPLIT_CHARACTERS, new DefaultSplitCharacters());
            }
        }
        setTextDecoration(element, cssProps);

        String textIndent = cssProps.get(CommonCssConstants.TEXT_INDENT);
        if (textIndent != null) {
            UnitValue textIndentValue = CssDimensionParsingUtils.parseLengthValueToPt(textIndent, em, rem);
            if (textIndentValue != null) {
                if (textIndentValue.isPointValue()) {
                    element.setProperty(Property.FIRST_LINE_INDENT, textIndentValue.getValue());
                } else {
                    logger.error(MessageFormatUtil.format(
                            Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED,
                            CommonCssConstants.TEXT_INDENT));
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

    private static void setTextDecoration(IPropertyContainer element, Map<String, String> cssProps) {

        String[] props = new String[] {null};
        final String unparsedProps = cssProps.get(CommonCssConstants.TEXT_DECORATION_COLOR);
        if (unparsedProps != null && !unparsedProps.trim().isEmpty()) {
            props = cssProps.get(CommonCssConstants.TEXT_DECORATION_COLOR).split("\\s+");
        }

        final List<Float> opacityList = new ArrayList<>(props.length);
        final List<Color> colorList = new ArrayList<>(props.length);

        for (final String textDecorationColorProp : props) {
            TransparentColor tColor;
            Color textDecorationColor;
            float opacity = 1f;
            if (textDecorationColorProp == null || CommonCssConstants.CURRENTCOLOR.equals(textDecorationColorProp)) {
                if (element.<TransparentColor>getProperty(Property.FONT_COLOR) != null) {
                    TransparentColor transparentColor = element.<TransparentColor>getProperty(Property.FONT_COLOR);
                    textDecorationColor = transparentColor.getColor();
                    opacity = transparentColor.getOpacity();
                } else {
                    textDecorationColor = ColorConstants.BLACK;
                }
            } else {
                if (textDecorationColorProp.startsWith("hsl")) {
                    logger.error(Html2PdfLogMessageConstant.HSL_COLOR_NOT_SUPPORTED);
                    textDecorationColor = ColorConstants.BLACK;
                } else {
                    tColor = CssDimensionParsingUtils.parseColor(textDecorationColorProp);
                    textDecorationColor = tColor.getColor();
                    opacity = tColor.getOpacity();
                }
            }
            opacityList.add(opacity);
            colorList.add(textDecorationColor);
        }

        final String textDecorationLineProp = cssProps.get(CommonCssConstants.TEXT_DECORATION_LINE);
        if (textDecorationLineProp == null) {
            return;
        }

        final String[] textDecorationArray = textDecorationLineProp.split("\\s+");
        List<Underline> underlineList = new ArrayList<>();

        for (int currentIndex = 0; currentIndex < textDecorationArray.length; currentIndex++) {
            final float opacity = opacityList.size() - 1 > currentIndex ? opacityList.get(currentIndex)
                    : opacityList.get(opacityList.size() - 1);
            final Color color = colorList.size() - 1 > currentIndex ? colorList.get(currentIndex)
                    : colorList.get(colorList.size() - 1);
            final String line = textDecorationArray[currentIndex];

            if (CommonCssConstants.BLINK.equals(line)) {
                logger.error(Html2PdfLogMessageConstant.TEXT_DECORATION_BLINK_NOT_SUPPORTED);
            } else if (CommonCssConstants.LINE_THROUGH.equals(line)) {
                underlineList.add(new Underline(color, opacity, TEXT_DECORATION_LINE_DEFAULT_THICKNESS, 0, 0,
                        TEXT_DECORATION_LINE_THROUGH_Y_POS,
                        LineCapStyle.BUTT));
            } else if (CommonCssConstants.OVERLINE.equals(line)) {
                underlineList.add(new Underline(color, opacity, TEXT_DECORATION_LINE_DEFAULT_THICKNESS, 0, 0,
                        TEXT_DECORATION_LINE_OVER_Y_POS,
                        LineCapStyle.BUTT));
            } else if (CommonCssConstants.UNDERLINE.equals(line)) {
                underlineList.add(new Underline(color, opacity, TEXT_DECORATION_LINE_DEFAULT_THICKNESS, 0, 0,
                        TEXT_DECORATION_LIN_UNDER_Y_POS,
                        LineCapStyle.BUTT));
            } else if (CommonCssConstants.NONE.equals(line)) {
                underlineList = null;
                // if none and any other decoration are used together, none is displayed
                break;
            }
        }
        element.setProperty(Property.UNDERLINE, underlineList);
    }


    private static void setLineHeight(IPropertyContainer elementToSet, String lineHeight, float em, float rem) {
        if (lineHeight != null && !CssConstants.NORMAL.equals(lineHeight) && !CssConstants.AUTO.equals(lineHeight)) {
            if (CssTypesValidationUtils.isNumber(lineHeight)) {
                Float number = CssDimensionParsingUtils.parseFloat(lineHeight);
                if (number != null) {
                    elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createMultipliedValue((float) number));
                } else {
                    elementToSet.setProperty(Property.LINE_HEIGHT, LineHeight.createNormalValue());
                }
            } else {
                UnitValue lineHeightValue = CssDimensionParsingUtils.parseLengthValueToPt(lineHeight, em, rem);
                if (lineHeightValue != null && lineHeightValue.isPointValue()) {
                    elementToSet.setProperty(Property.LINE_HEIGHT,
                            LineHeight.createFixedValue(lineHeightValue.getValue()));
                } else if (lineHeightValue != null) {
                    elementToSet.setProperty(Property.LINE_HEIGHT,
                            LineHeight.createMultipliedValue(lineHeightValue.getValue() / 100f));
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
            if (CssTypesValidationUtils.isNumber(lineHeight)) {
                Float mult = CssDimensionParsingUtils.parseFloat(lineHeight);
                if (mult != null) {
                    element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, (float) mult));
                }
            } else {
                UnitValue lineHeightValue = CssDimensionParsingUtils.parseLengthValueToPt(lineHeight, em, rem);
                if (lineHeightValue != null && lineHeightValue.isPointValue()) {
                    element.setProperty(Property.LEADING, new Leading(Leading.FIXED, lineHeightValue.getValue()));
                } else if (lineHeightValue != null) {
                    element.setProperty(Property.LEADING,
                            new Leading(Leading.MULTIPLIED, lineHeightValue.getValue() / 100));
                }
            }
        } else {
            element.setProperty(Property.LEADING, new Leading(Leading.MULTIPLIED, DEFAULT_LINE_HEIGHT));
        }
    }
}


