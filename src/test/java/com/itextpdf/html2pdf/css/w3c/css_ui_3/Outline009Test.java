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
package com.itextpdf.html2pdf.css.w3c.css_ui_3;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO update cmp file after fixing DEVSIX-3386
public class Outline009Test extends W3CCssTest {
    @Override
    // parent's <outline-style: none;> should override parent's <outline-width: whatever;> to 0 value
    // therefore child's <outline-width: inherit;> should inherit overriden 0 value
    // see CSS Basic User Interface Module Level 3 (CSS3 UI) 4.2. Outline Thickness: the outline-width property (Computed value: absolute length; 0 if the outline style is none. )
    protected String getHtmlFileName() {
        return "outline-009.html";
    }
}
