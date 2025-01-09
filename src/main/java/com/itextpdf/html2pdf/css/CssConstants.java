/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
package com.itextpdf.html2pdf.css;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CssConstants extends CommonCssConstants {

    /** The Constant AUTO_FIT. */
    public static final String AUTO_FIT = "auto-fit";

    /** The Constant AUTO_FILL. */
    public static final String AUTO_FILL = "auto-fill";

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

    /** The Constant LIST_ITEM. */
    public static final String LIST_ITEM = "list-item";

    /** The Constant MARKS. */
    public static final String MARKS = "marks";

    /** The Constant MAX_HEIGHT. */
    public static final String MAX_HEIGHT = "max-height";

    /** The Constant MAX_WIDTH. */
    public static final String MAX_WIDTH = "max-width";

    /** The Constant MIN_WIDTH. */
    public static final String MIN_WIDTH = "min-width";

    /**  The Constant MIN_MAX. */
    public static final String MINMAX = "minmax";

    /**
     * The Constant OBJECT_FIT.
     */
    public static final String OBJECT_FIT = "object-fit";

    /** The Constant OUTLINE_OFFSET. */
    public static final String OUTLINE_OFFSET = "outline-offset";

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

    // property values

    /** The Constant ABSOLUTE. */
    public static final String ABSOLUTE = "absolute";

    /** The Constant BLINK. */
    public static final String BLINK = "blink";

    /** The Constant BLOCK. */
    public static final String BLOCK = "block";

    /** The Constant CAPITALIZE. */
    public static final String CAPITALIZE = "capitalize";

    /** The Constant CONTENTS. */
    public static final String CONTENTS = "contents";

    /** The Constant COLLAPSE. */
    public static final String COLLAPSE = "collapse";

    /** The Constant CROP. */
    public static final String CROP = "crop";

    /** The Constant CROSS. */
    public static final String CROSS = "cross";

    /** The Constant FILL. */
    public static final String FILL = "fill";

    /** The Constant FIRST. */
    public static final String FIRST = "first";

    /** The Constant FIRST_EXCEPT. */
    public static final String FIRST_EXCEPT = "first-except";

    /** The Constant GRID_AREA. */
    public static final String GRID_AREA = "grid-area";

    /** The Constant INLINE. */
    public static final String INLINE = "inline";

    /** The Constant INLINE_BLOCK. */
    public static final String INLINE_BLOCK = "inline-block";

    /** The Constant INLINE_FLEX. */
    public static final String INLINE_FLEX = "INLINE_FLEX";
    
    
    /** The Constant INLINE_GRID. */
    public static final String INLINE_GRID = "INLINE_GRID";

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

    /** The Constant RUN_IN. */
    public static final String RUN_IN = "run-in";
    
    /** The Constant RTL. */
    public static final String RTL = "rtl";

    /** The Constant SCALE_DOWN. */
    public static final String SCALE_DOWN = "scale-down";

    /** The Constant SEPARATE. */
    public static final String SEPARATE = "separate";

    /** The Constant SUB. */
    public static final String SUB = "sub";

    /** The Constant SUBGRID. */
    public static final String SUBGRID = "subgrid";

    /** The Constant SUPER. */
    public static final String SUPER = "super";

    /** The Constant TABLE. */
    public static final String TABLE = "table";
    
    /** The Constant TABLE_CAPTION. */
    public static final String TABLE_CAPTION = "table-caption";

    /** The Constant TABLE_CELL. */
    public static final String TABLE_CELL = "table-cell";
    
    /** The Constant TABLE_COLUMN. */
    public static final String TABLE_COLUMN = "table-column";
    
    /** The Constant TABLE_COLUMN_GROUP. */
    public static final String TABLE_COLUMN_GROUP = "table-column-group";
    
    /** The Constant TABLE_FOOTER_GROUP. */
    public static final String TABLE_FOOTER_GROUP = "table-footer-group";
    
    /** The Constant TABLE_HEADER_GROUP. */
    public static final String TABLE_HEADER_GROUP = "table-header-group";

    /** The Constant TABLE_ROW. */
    public static final String TABLE_ROW = "table-row";

    /** The Constant TABLE_ROW_GROUP. */
    public static final String TABLE_ROW_GROUP = "table-row-group";

    /** The Constant TEXT_BOTTOM. */
    public static final String TEXT_BOTTOM = "text-bottom";

    /** The Constant TEXT_TOP. */
    public static final String TEXT_TOP = "text-top";

    /** The Constant UNDERLINE. */
    public static final String UNDERLINE = "underline";

    /** The Constant UPPERCASE. */
    public static final String UPPERCASE = "uppercase";

    // properties possible values

    /** The Constant OVERFLOW_VALUES. */
    public static final Set<String> OVERFLOW_VALUES = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(CommonCssConstants.VISIBLE, HIDDEN, SCROLL, AUTO)));

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

    /** The Constant TARGET_COUNTER. */
    public static final String TARGET_COUNTER = "target-counter";

    /** The Constant TARGET_COUNTERS. */
    public static final String TARGET_COUNTERS = "target-counters";

    // units of resolution

    /** The Constant DPI. */
    public static final String DPI = "dpi";
}
