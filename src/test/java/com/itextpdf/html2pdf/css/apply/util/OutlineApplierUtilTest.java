/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

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

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class OutlineApplierUtilTest extends ExtendedITextTest {

    @Test
    public void grooveBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "groove", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

    @Test
    public void ridgeBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "ridge", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

    @Test
    public void insetBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "inset", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

    @Test
    public void outsetBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "outset", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

}
