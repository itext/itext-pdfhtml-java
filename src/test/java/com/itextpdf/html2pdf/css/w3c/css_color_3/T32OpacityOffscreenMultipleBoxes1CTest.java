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
package com.itextpdf.html2pdf.css.w3c.css_color_3;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class T32OpacityOffscreenMultipleBoxes1CTest extends W3CCssTest {
    // In pdf, if several layers of the same color are drawn one atop another, then in case of transparency
    // this place would look more "saturated", than a single layer of the transparent color. However, this is not
    // true for the css, apparently.
    @Override
    protected String getHtmlFileName() {
        return "t32-opacity-offscreen-multiple-boxes-1-c.xht";
    }

    @Test
    @Override
    public void test() throws IOException, InterruptedException {
        super.test();
    }
}
