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
package com.itextpdf.html2pdf.attach.impl.layout;

/**
 * Set of constants that will be used as keys to get and set properties.
 */
public class Html2PdfProperty {

    /** The Constant PROPERTY_START. */
    private static final int PROPERTY_START = (1 << 20);

    /** The Constant KEEP_WITH_PREVIOUS works only for top-level elements, i.e. ones that are added to the document directly. */
    public static final int KEEP_WITH_PREVIOUS = PROPERTY_START + 1;

    /** The Constant PAGE_COUNT_TYPE. */
    public static final int PAGE_COUNT_TYPE = PROPERTY_START + 2;

    /** The Constant BODY_STYLING. */
    public static final int BODY_STYLING = PROPERTY_START + 3;

    /** The Constant HTML_STYLING. */
    public static final int HTML_STYLING = PROPERTY_START + 4;

    /** The Constant CAPITALIZE_ELEMENT indicates if an inline element needs to be capitalized. */
    public static final int CAPITALIZE_ELEMENT = PROPERTY_START + 5;
}
