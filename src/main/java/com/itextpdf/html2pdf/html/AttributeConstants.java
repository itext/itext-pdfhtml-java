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
package com.itextpdf.html2pdf.html;

import com.itextpdf.styledxmlparser.CommonAttributeConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that bundles a series of attribute constants.
 */
public final class AttributeConstants extends CommonAttributeConstants {
    /**
     * Creates a new {@link AttributeConstants} instance.
     */
    private AttributeConstants() {
    }

    /** The Constant ALIGN. */
    public static final String ALIGN = "align";

    /** The Constant ALT. */
    public static final String ALT = "alt";

    /** The Constant ARIA-LABEL. */
    public static final String ARIA_LABEL = "aria-label";

    /** The Constant APPLICATION_NAME. */
    public static final String APPLICATION_NAME = "application-name";

    /** The Constant AUTHOR. */
    public static final String AUTHOR = "author";

    /** The Constant BGCOLOR. */
    public static final String BGCOLOR = "bgcolor";

    /** The Constant BORDER. */
    public static final String BORDER = "border";

    /** The Constant CLASS. */
    public static final String CELLPADDING = "cellpadding";

    /** The Constant CLASS. */
    public static final String CELLSPACING = "cellspacing";

    /** The Constant COLOR. */
    public static final String COLOR = "color";

    /** The Constant COLS. */
    public static final String COL = "col";

    /** The Constant COLS. */
    public static final String COLGROUP = "colgroup";

    /** The Constant COLS. */
    public static final String COLS = "cols";

    /** The Constant COLSPAN. */
    public static final String COLSPAN = "colspan";

    /** The Constant CONTENT. */
    public static final String CONTENT = "content";

    /** The Constant DATA */
    public static final String DATA = "data";

    /** The Constant DESCRIPTION. */
    public static final String DESCRIPTION = "description";

    /** The Constant DIR. */
    public static final String DIR = "dir";

    /** The Constant FACE. */
    public static final String FACE = "face";

    /** The Constant HEIGHT. */
    public static final String HEIGHT = "height";

    /** The Constant HREF. */
    public static final String HREF = "href";

    /** The Constant HSPACE. */
    public static final String HSPACE = "hspace";

    /** The Constant ID. */
    public static final String ID = "id";

    /** The Constant KEYWORDS. */
    public static final String KEYWORDS = "keywords";

    /** The Constant LABEL. */
    public static final String LABEL = "label";

    /** The Constant LANG. */
    public static final String LANG = "lang";

    /** The Constant MEDIA. */
    public static final String MEDIA = "media";

    /** The Constant MULTIPLE. */
    public static final String MULTIPLE = "multiple";

    /** The Constant NAME. */
    public static final String NAME = "name";

    /** The Constant NOSHADE. */
    public static final String NOSHADE = "noshade";

    /** The Constant NOWRAP. */
    public static final String NOWRAP = "nowrap";

    /** The Constant NUMBER. */
    public static final String NUMBER = "number";

    /** The Constant ROWS. */
    public static final String ROW = "row";

    /** The Constant ROWS. */
    public static final String ROWGROUP = "rowgroup";

    /** The Constant ROWS. */
    public static final String ROWS = "rows";

    /** The Constant ROWSPAN. */
    public static final String ROWSPAN = "rowspan";

    /** The Constant SCOPE. */
    public static final String SCOPE = "scope";

    /** The Constant SELECTED. */
    public static final String SELECTED = "selected";

    /** The Constant SIZE. */
    public static final String SIZE = "size";

    /** The Constant SPAN. */
    public static final String SPAN = "span";

    /** The Constant SRC. */
    public static final String SRC = "src";

    /** The Constant STYLE. */
    public static final String STYLE = "style";

    /** The Constant TYPE. */
    public static final String TYPE = "type";

    /** The Constant VALIGN. */
    public static final String VALIGN = "valign";

    /** The Constant VALUE. */
    public static final String VALUE = "value";

    /** The Constant VSPACE. */
     public static final String VSPACE = "vspace";

    /** The Constant WIDTH. */
    public static final String WIDTH = "width";

    /** The Constant TITLE. */
    public static final String TITLE = "title";

    // attribute values

    /** The Constant _1. */
    public static final String _1 = "1";

    /** The Constant A. */
    public static final String A = "A";

    /** The Constant a. */
    public static final String a = "a";

    /** The Constant BOTTOM. */
    public static final String BOTTOM = "bottom";

    /** The Constant BUTTON. */
    public static final String BUTTON = "button";

    /** The Constant CENTER. */
    public static final String CENTER = "center";

    /** The Constant CHECKBOX. */
    public static final String CHECKBOX = "checkbox";

    /** The Constant CHECKED. */
    public static final String CHECKED = "checked";

    /** The Constant DATE. */
    public static final String DATE = "date";

    /** The Constant DATETIME. */
    public static final String DATETIME = "datetime";

    /** The Constant DATETIME_LOCAL. */
    public static final String DATETIME_LOCAL = "datetime_local";

    /** The Constant EMAIL. */
    public static final String EMAIL = "email";

    /** The Constant FILE. */
    public static final String FILE = "file";

    /** The Constant HIDDEN. */
    public static final String HIDDEN = "hidden";

    /** The Constant I. */
    public static final String I = "I";

    /** The Constant i. */
    public static final String i = "i";

    /** The Constant IMAGE. */
    public static final String IMAGE = "image";

    /** The Constant LEFT. */
    public static final String LEFT = "left";

    /** The Constant LTR. */
    public static final String LTR = "ltr";

    /** The Constant MIDDLE. */
    public static final String MIDDLE = "middle";

    /** The Constant MONTH. */
    public static final String MONTH = "month";

    /** The Constant PASSWORD. */
    public static final String PASSWORD = "password";

    /** The Constant PLACEHOLDER. */
    public static final String PLACEHOLDER = "placeholder";

    /** The Constant RADIO. */
    public static final String RADIO = "radio";

    /** The Constant RANGE. */
    public static final String RANGE = "range";

    /** The Constant RESET. */
    public static final String RESET = "reset";

    /** The Constant RIGHT. */
    public static final String RIGHT = "right";

    /** The Constant RTL. */
    public static final String RTL = "rtl";

    /** The Constant SEARCH. */
    public static final String SEARCH = "search";

    /**The Constant START*/
    public static final String START = "start";

    /** The Constant SUBMIT. */
    public static final String SUBMIT = "submit";

    /** The Constant TEL. */
    public static final String TEL = "tel";

    /** The Constant TEXT. */
    public static final String TEXT = "text";

    /** The Constant TIME. */
    public static final String TIME = "time";

    /** The Constant TOP. */
    public static final String TOP = "top";

    /**The Constant URL*/
    public static final String URL = "url";

    /**The Constant WEEK*/
    public static final String WEEK = "week";

    /** The Constant INPUT_TYPE_VALUES. */
    public static final Set<String> INPUT_TYPE_VALUES = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(new String[] {BUTTON, CHECKBOX, COLOR, DATE, DATETIME, DATETIME_LOCAL, EMAIL, FILE, HIDDEN,
                    IMAGE, MONTH, NUMBER, PASSWORD, RADIO, RANGE, RESET, SEARCH, SUBMIT, TEL, TEXT, TIME, URL, WEEK})));

    // iText custom attributes
    /**
     * Class that holds object MIME types.
     */
    public static final class ObjectTypes{
        public static final String SVGIMAGE = "image/svg+xml";
    }

}
