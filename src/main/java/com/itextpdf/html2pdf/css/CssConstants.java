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
package com.itextpdf.html2pdf.css;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CssConstants {

    // properties
    public static final String ALIGN = "align";
    public static final String BACKGROUND = "background";
    public static final String BACKGROUND_ATTACHMENT = "background-attachment";
    public static final String BACKGROUND_BLEND_MODE = "background-blend-mode";
    public static final String BACKGROUND_CLIP = "background-clip";
    public static final String BACKGROUND_COLOR = "background-color";
    public static final String BACKGROUND_IMAGE = "background-image";
    public static final String BACKGROUND_ORIGIN = "background-origin";
    public static final String BACKGROUND_POSITION = "background-position";
    public static final String BACKGROUND_REPEAT = "background-repeat";
    public static final String BACKGROUND_SIZE = "background-size";
    public static final String BLEED = "bleed";
    public static final String BORDER = "border";
    public static final String BORDER_BOTTOM = "border-bottom";
    public static final String BORDER_BOTTOM_COLOR = "border-bottom-color";
    public static final String BORDER_BOTTOM_STYLE = "border-bottom-style";
    public static final String BORDER_BOTTOM_WIDTH = "border-bottom-width";
    public static final String BORDER_COLLAPSE = "border-collapse";
    public static final String BORDER_COLOR = "border-color";
    public static final String BORDER_IMAGE = "border-image";
    public static final String BORDER_LEFT = "border-left";
    public static final String BORDER_LEFT_COLOR = "border-left-color";
    public static final String BORDER_LEFT_STYLE = "border-left-style";
    public static final String BORDER_LEFT_WIDTH = "border-left-width";
    public static final String BORDER_RADIUS = "border-radius";
    public static final String BORDER_RIGHT = "border-right";
    public static final String BORDER_RIGHT_COLOR = "border-right-color";
    public static final String BORDER_RIGHT_STYLE = "border-right-style";
    public static final String BORDER_RIGHT_WIDTH = "border-right-width";
    public static final String BORDER_SPACING = "border-spacing";
    public static final String BORDER_STYLE = "border-style";
    public static final String BORDER_TOP = "border-top";
    public static final String BORDER_TOP_COLOR = "border-top-color";
    public static final String BORDER_TOP_STYLE = "border-top-style";
    public static final String BORDER_TOP_WIDTH = "border-top-width";
    public static final String BORDER_WIDTH = "border-width";
    public static final String BOTH = "both";
    public static final String BOX_SHADOW = "box-shadow";
    public static final String CAPTION_SIDE = "caption-side";
    public static final String CLEAR = "clear";
    public static final String COLOR = "color";
    public static final String CONTENT = "content";
    public static final String COUNTER_INCREMENT = "counter-increment";
    public static final String COUNTER_RESET = "counter-reset";
    public static final String DIRECTION = "direction";
    public static final String DISPLAY = "display";
    public static final String EMPTY_CELLS = "empty-cells";
    public static final String FLOAT = "float";
    public static final String FONT = "font";
    public static final String FONT_FAMILY = "font-family";
    public static final String FONT_FEATURE_SETTINGS = "font-feature-settings";
    public static final String FONT_KERNING = "font-kerning";
    public static final String FONT_LANGUAGE_OVERRIDE = "font-language-override";
    public static final String FONT_SIZE = "font-size";
    public static final String FONT_SIZE_ADJUST = "font-size-adjust";
    public static final String FONT_STRETCH = "font-stretch";
    public static final String FONT_STYLE = "font-style";
    public static final String FONT_SYNTHESIS = "font-synthesis";
    public static final String FONT_VARIANT = "font-variant";
    public static final String FONT_VARIANT_ALTERNATES = "font-variant-alternates";
    public static final String FONT_VARIANT_CAPS = "font-variant-caps";
    public static final String FONT_VARIANT_EAST_ASIAN = "font-variant-east-asian";
    public static final String FONT_VARIANT_LIGATURES = "font-variant-ligatures";
    public static final String FONT_VARIANT_NUMERIC = "font-variant-numeric";
    public static final String FONT_VARIANT_POSITION = "font-variant-position";
    public static final String FONT_WEIGHT = "font-weight";
    public static final String HANGING_PUNCTUATION = "hanging-punctuation";
    public static final String HEIGHT = "height";
    public static final String HSPACE = "hspace";
    public static final String HYPHENS = "hyphens";
    public static final String LETTER_SPACING = "letter-spacing";
    public static final String LINE_HEIGHT = "line-height";
    public static final String LIST_STYLE = "list-style";
    public static final String LIST_STYLE_IMAGE = "list-style-image";
    public static final String LIST_STYLE_POSITION = "list-style-position";
    public static final String LIST_STYLE_TYPE = "list-style-type";
    public static final String MARKS = "marks";
    public static final String MARGIN = "margin";
    public static final String MARGIN_BOTTOM = "margin-bottom";
    public static final String MARGIN_LEFT = "margin-left";
    public static final String MARGIN_RIGHT = "margin-right";
    public static final String MARGIN_TOP = "margin-top";
    public static final String MAX_HEIGHT = "max-height";
    public static final String MIN_HEIGHT = "min-height";
    public static final String OPACITY = "opacity";
    public static final String OUTLINE = "outline";
    public static final String OUTLINE_COLOR = "outline-color";
    public static final String OUTLINE_STYLE = "outline-style";
    public static final String OUTLINE_WIDTH = "outline-width";
    public static final String OVERFLOW_WRAP = "overflow-wrap";
    public static final String PADDING = "padding";
    public static final String PADDING_BOTTOM = "padding-bottom";
    public static final String PADDING_LEFT = "padding-left";
    public static final String PADDING_RIGHT = "padding-right";
    public static final String PADDING_TOP = "padding-top";
    public static final String PAGE_BREAK_AFTER = "page-break-after";
    public static final String PAGE_BREAK_BEFORE = "page-break-before";
    public static final String PAGE_BREAK_INSIDE = "page-break-inside";
    public static final String POSITION = "position";
    public static final String QUOTES = "quotes";
    public static final String SIZE = "size";
    public static final String STYLE = "style";
    public static final String TAB_SIZE = "tab-size";
    public static final String TABLE_LAYOUT = "table-layout";
    public static final String TEXT_ALIGN = "text-align";
    public static final String TEXT_ALIGN_LAST = "text-align-last";
    public static final String TEXT_COMBINE_UPRIGHT = "text-combine-upright";
    public static final String TEXT_DECORATION = "text-decoration";
    public static final String TEXT_INDENT = "text-indent";
    public static final String TEXT_JUSTIFY = "text-justify";
    public static final String TEXT_ORIENTATION = "text-orientation";
    public static final String TEXT_SHADOW = "text-shadow";
    public static final String TEXT_TRANSFORM = "text-transform";
    public static final String TEXT_UNDERLINE_POSITION = "text-underline-position";
    public static final String UNICODE_BIDI = "unicode-bidi";
    public static final String VERTICAL_ALIGN = "vertical-align";
    public static final String VISIBILITY = "visibility";
    public static final String VSPACE = "vspace";
    public static final String WHITE_SPACE = "white-space";
    public static final String WIDTH = "width";
    public static final String WORDWRAP = "word-wrap";
    public static final String WORD_BREAK = "word-break";
    public static final String WORD_SPACING = "word-spacing";
    public static final String WRITING_MODE = "writing-mode";

    // property values
    public static final String ABSOLUTE = "absolute";
    public static final String ALWAYS = "always";
    public static final String ARMENIAN = "armenian";
    public static final String AVOID = "avoid";
    public static final String AUTO = "auto";
    public static final String BLINK = "blink";
    public static final String BLOCK = "block";
    public static final String BOLD = "bold";
    public static final String BOLDER = "bolder";
    public static final String BORDER_BOX = "border-box";
    public static final String BOTTOM = "bottom";
    public static final String CAPITALIZE = "capitalize";
    public static final String CAPTION = "caption";
    public static final String CENTER = "center";
    public static final String CIRCLE = "circle";
    public static final String CJK_IDEOGRAPHIC = "cjk-ideographic";
    public static final String CLOSE_QUOTE = "close-quote";
    public static final String CONTAIN = "contain";
    public static final String CONTENT_BOX = "content-box";
    public static final String COVER = "cover";
    public static final String CROP = "crop";
    public static final String CROSS = "cross";
    public static final String DASHED = "dashed";
    public static final String DECIMAL = "decimal";
    public static final String DECIMAL_LEADING_ZERO = "decimal-leading-zero";
    public static final String DISC = "disc";
    public static final String DOTTED = "dotted";
    public static final String DOUBLE = "double";
    public static final String FIXED = "fixed";
    public static final String GEORGIAN = "georgian";
    public static final String GROOVE = "groove";
    public static final String HEBREW = "hebrew";
    public static final String HIDDEN = "hidden";
    public static final String HIRAGANA = "hiragana";
    public static final String HIRAGANA_IROHA = "hiragana-iroha";
    public static final String ICON = "icon";
    public static final String INHERIT = "inherit";
    public static final String INITIAL = "initial";
    public static final String INLINE = "inline";
    public static final String INLINE_BLOCK = "inline-block";
    public static final String INSET = "inset";
    public static final String INSIDE = "inside";
    public static final String INVERT = "invert";
    public static final String ITALIC = "italic";
    public static final String LANDSCAPE = "landscape";
    public static final String LARGE = "large";
    public static final String LARGER = "larger";
    public static final String LEFT = "left";
    public static final String LIGHTER = "lighter";
    public static final String LINE_THROUGH = "line-through";
    public static final String LOCAL = "local";
    public static final String LOWER_ALPHA = "lower-alpha";
    public static final String LOWER_GREEK = "lower-greek";
    public static final String LOWER_LATIN = "lower-latin";
    public static final String LOWER_ROMAN = "lower-roman";
    public static final String LOWERCASE = "lowercase";
    public static final String LTR = "ltr";
    public static final String MANUAL = "manual";
    public static final String MEDIUM = "medium";
    public static final String MENU = "menu";
    public static final String MESSAGE_BOX = "message-box";
    public static final String MIDDLE = "middle";
    public static final String NO_OPEN_QUOTE = "no-open-quote";
    public static final String NO_CLOSE_QUOTE = "no-close-quote";
    public static final String NO_REPEAT = "no-repeat";
    public static final String NONE = "none";
    public static final String NORMAL = "normal";
    public static final String OBLIQUE = "oblique";
    public static final String OPEN_QUOTE = "open-quote";
    public static final String OUTSIDE = "outside";
    public static final String OUTSET = "outset";
    public static final String OVERLINE = "overline";
    public static final String PADDING_BOX = "padding-box";
    public static final String PORTRAIT = "portrait";
    public static final String PRE = "pre";
    public static final String PRE_LINE = "pre-line";
    public static final String PRE_WRAP = "pre-wrap";
    public static final String RELATIVE = "relative";
    public static final String REPEAT = "repeat";
    public static final String REPEAT_X = "repeat-x";
    public static final String REPEAT_Y = "repeat-y";
    public static final String RIDGE = "ridge";
    public static final String RIGHT = "right";
    public static final String RTL = "rtl";
    public static final String SCROLL = "scroll";
    public static final String SMALL = "small";
    public static final String SMALL_CAPS = "small-caps";
    public static final String SMALL_CAPTION = "small-caption";
    public static final String SMALLER = "smaller";
    public static final String SOLID = "solid";
    public static final String SQUARE = "square";
    public static final String START = "start";
    public static final String STATIC = "static";
    public static final String STATUS_BAR = "status-bar";
    public static final String SUB = "sub";
    public static final String SUPER = "super";
    public static final String TEXT_BOTTOM = "text-bottom";
    public static final String TEXT_TOP = "text-top";
    public static final String THICK = "thick";
    public static final String THIN = "thin";
    public static final String TOP = "top";
    public static final String TRANSPARENT = "transparent";
    public static final String UNDERLINE = "underline";
    public static final String UPPER_ALPHA = "upper-alpha";
    public static final String UPPER_LATIN = "upper-latin";
    public static final String UPPER_ROMAN = "upper-roman";
    public static final String UPPERCASE = "uppercase";
    public static final String X_LARGE = "x-large";
    public static final String X_SMALL = "x-small";
    public static final String XX_LARGE = "xx-large";
    public static final String XX_SMALL = "xx-small";

    // properties possible values
    public static final Set<String> BACKGROUND_SIZE_VALUES = new HashSet<>(
            Arrays.asList(AUTO, COVER, CONTAIN));
    public static final Set<String> BACKGROUND_ORIGIN_OR_CLIP_VALUES = new HashSet<>(
            Arrays.asList(PADDING_BOX, BORDER_BOX, CONTENT_BOX));
    public static final Set<String> BACKGROUND_REPEAT_VALUES = new HashSet<>(
            Arrays.asList(REPEAT, NO_REPEAT, REPEAT_X, REPEAT_Y));
    public static final Set<String> BACKGROUND_ATTACHMENT_VALUES = new HashSet<>(
            Arrays.asList(FIXED, SCROLL, LOCAL));
    public static final Set<String> BACKGROUND_POSITION_VALUES = new HashSet<>(
            Arrays.asList(LEFT, CENTER, BOTTOM, TOP, RIGHT));
    public static final Set<String> BORDER_WIDTH_VALUES = new HashSet<>(
            Arrays.asList(new String[] {THIN, MEDIUM, THICK}));
    public static final Set<String> BORDER_STYLE_VALUES = new HashSet<>(
            Arrays.asList(new String[] {NONE, HIDDEN, DOTTED, DASHED, SOLID, DOUBLE, GROOVE, RIDGE, INSET, OUTSET}));
    public static final Set<String> FONT_ABSOLUTE_SIZE_KEYWORDS = new HashSet<>(Arrays.asList(
            CssConstants.MEDIUM, CssConstants.XX_SMALL, CssConstants.X_SMALL, CssConstants.SMALL, CssConstants.LARGE,
            CssConstants.X_LARGE, CssConstants.XX_LARGE
    ));

    // pseudo-elements
    public static final String AFTER = "after";
    public static final String BEFORE = "before";

    // Functions
    public static final String COUNTER = "counter";
    public static final String COUNTERS = "counters";

    // units of measurement
    public static final String CM = "cm";
    public static final String EM = "em";
    public static final String EX = "ex";
    public static final String IN = "in";
    public static final String MM = "mm";
    public static final String PC = "pc";
    public static final String PERCENTAGE = "%";
    public static final String PT = "pt";
    public static final String PX = "px";
    public static final String REM = "rem";
    public static final String Q = "q";

    // units of resolution
    public static final String DPI = "dpi";
    public static final String DPCM = "dpcm";
    public static final String DPPX = "dppx";
}
