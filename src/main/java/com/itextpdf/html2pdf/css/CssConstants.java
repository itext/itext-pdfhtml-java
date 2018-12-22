/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
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
package com.itextpdf.html2pdf.css;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CssConstants extends CommonCssConstants {

    /** The Constant BLEED. */
    public static final String BLEED = "bleed";

    /** The Constant BOTH. */
    public static final String BOTH = "both";

    /** The Constant BOX_SIZING. */
    public static final String BOX_SIZING = "box-sizing";


    /** The Constant CLEAR. */
    public static final String CLEAR = "clear";

    /** The Constant CONTENT. */
    public static final String CONTENT = "content";

    /** The Constant COUNTER_INCREMENT. */
    public static final String COUNTER_INCREMENT = "counter-increment";

    /** The Constant COUNTER_RESET. */
    public static final String COUNTER_RESET = "counter-reset";

    /** The Constant DISPLAY. */
    public static final String DISPLAY = "display";

    /** The Constant HEIGHT. */
    public static final String HEIGHT = "height";

    /** The Constant MARKS. */
    public static final String MARKS = "marks";

    /** The Constant MAX_HEIGHT. */
    public static final String MAX_HEIGHT = "max-height";

    /** The Constant MAX_WIDTH. */
    public static final String MAX_WIDTH = "max-width";

    /** The Constant MIN_WIDTH. */
    public static final String MIN_WIDTH = "min-width";

    /** The Constant OUTLINE_OFFSET. */
    public static final String OUTLINE_OFFSET = "outline-offset";

    /** The Constant OVERFLOW. */
    public static final String OVERFLOW = "overflow";

    /** The Constant OVERFLOW_X. */
    public static final String OVERFLOW_X = "overflow-x";

    /** The Constant OVERFLOW_Y. */
    public static final String OVERFLOW_Y = "overflow-y";

    /** The Constant PADDING_INLINE_START. */
    public static final String PADDING_INLINE_START = "padding-inline-start";

    /** The Constant PLACEHOLDER. */
    public static final String PLACEHOLDER = "placeholder";

    /** The Constant SIZE. */
    public static final String SIZE = "size";

    /** The Constant STYLE. */
    public static final String STYLE = "style";

    /** The Constant TABLE_LAYOUT. */
    public static final String TABLE_LAYOUT = "table-layout";

    /** The Constant VERTICAL_ALIGN. */
    public static final String VERTICAL_ALIGN = "vertical-align";

    /** The Constant VISIBLE. */
    public static final String VISIBLE = "visible";

    // property values

    /** The Constant ABSOLUTE. */
    public static final String ABSOLUTE = "absolute";

    /** The Constant BLINK. */
    public static final String BLINK = "blink";

    /** The Constant BLOCK. */
    public static final String BLOCK = "block";

    /** The Constant BREAK_WORD. */
    public static final String BREAK_WORD = "break-word";

    /** The Constant CAPITALIZE. */
    public static final String CAPITALIZE = "capitalize";

    /** The Constant COLLAPSE. */
    public static final String COLLAPSE = "collapse";

    /** The Constant CROP. */
    public static final String CROP = "crop";

    /** The Constant CROSS. */
    public static final String CROSS = "cross";

    /** The Constant FIRST. */
    public static final String FIRST = "first";

    /** The Constant FIRST_EXCEPT. */
    public static final String FIRST_EXCEPT = "first-except";

    /** The Constant INLINE. */
    public static final String INLINE = "inline";

    /** The Constant INLINE_BLOCK. */
    public static final String INLINE_BLOCK = "inline-block";

    /** The Constant INLINE_TABLE. */
    public static final String INLINE_TABLE = "inline-table";

    /** The Constant INVERT. */
    public static final String INVERT = "invert";

    /** The Constant JUSTIFY. */
    public static final String JUSTIFY = "justify";

    /** The Constant LANDSCAPE. */
    public static final String LANDSCAPE = "landscape";

    /** The Constant LAST. */
    public static final String LAST = "last";

    /** The Constant LINE_THROUGH. */
    public static final String LINE_THROUGH = "line-through";

    /** The Constant LOWERCASE. */
    public static final String LOWERCASE = "lowercase";

    /** The Constant LTR. */
    public static final String LTR = "ltr";

    /** The Constant MIDDLE. */
    public static final String MIDDLE = "middle";

    /** The Constant NOWRAP. */
    public static final String NOWRAP = "nowrap";

    /** The Constant OVERLINE. */
    public static final String OVERLINE = "overline";

    /** The Constant PAGE. */
    public static final String PAGE = "page";

    /** The Constant PAGES. */
    public static final String PAGES = "pages";

    /** The Constant PORTRAIT. */
    public static final String PORTRAIT = "portrait";

    /** The Constant PRE. */
    public static final String PRE = "pre";

    /** The Constant PRE_LINE. */
    public static final String PRE_LINE = "pre-line";

    /** The Constant PRE_WRAP. */
    public static final String PRE_WRAP = "pre-wrap";

    /** The Constant RELATIVE. */
    public static final String RELATIVE = "relative";

    /** The Constant RTL. */
    public static final String RTL = "rtl";

    /** The Constant SEPARATE. */
    public static final String SEPARATE = "separate";

    /** The Constant SUB. */
    public static final String SUB = "sub";

    /** The Constant SUPER. */
    public static final String SUPER = "super";

    /** The Constant TABLE. */
    public static final String TABLE = "table";

    /** The Constant TABLE_CELL. */
    public static final String TABLE_CELL = "table-cell";

    /** The Constant TABLE_ROW. */
    public static final String TABLE_ROW = "table-row";

    /** The Constant TEXT_BOTTOM. */
    public static final String TEXT_BOTTOM = "text-bottom";

    /** The Constant TEXT_TOP. */
    public static final String TEXT_TOP = "text-top";

    /** The Constant UNDERLINE. */
    public static final String UNDERLINE = "underline";

    /** The Constant UPPERCASE. */
    public static final String UPPERCASE = "uppercase";

    // properties possible values
    /** The Constant FONT_ABSOLUTE_SIZE_KEYWORDS. */
    public static final Set<String> FONT_ABSOLUTE_SIZE_KEYWORDS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CssConstants.MEDIUM, CssConstants.XX_SMALL, CssConstants.X_SMALL, CssConstants.SMALL, CssConstants.LARGE,
            CssConstants.X_LARGE, CssConstants.XX_LARGE
    )));

    /** The Constant OVERFLOW_VALUES. */
    public static final Set<String> OVERFLOW_VALUES = new HashSet<>(
            Arrays.asList(new String[] {VISIBLE, HIDDEN, SCROLL, AUTO}));

    // pseudo-elements

    /** The Constant AFTER. */
    public static final String AFTER = "after";

    /** The Constant BEFORE. */
    public static final String BEFORE = "before";

    /** The Constant FIRST_LETTER. */
    public static final String FIRST_LETTER = "first-letter";

    /** The Constant FIRST_LINE. */
    public static final String FIRST_LINE = "first-line";

    /** The Constant SELECTION. */
    public static final String SELECTION = "selection";


    // Functions

    /** The Constant COUNTER. */
    public static final String COUNTER = "counter";

    /** The Constant COUNTERS. */
    public static final String COUNTERS = "counters";

    /** The Constant RUNNING. */
    public static final String ELEMENT = "element";

    /** The Constant RUNNING. */
    public static final String RUNNING = "running";

    // units of resolution

    /** The Constant DPI. */
    public static final String DPI = "dpi";
}
