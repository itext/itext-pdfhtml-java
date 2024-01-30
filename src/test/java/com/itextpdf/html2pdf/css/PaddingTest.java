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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class PaddingTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/PaddingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/PaddingTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void elementFixedWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("elementFixedWidthTest", sourceFolder, destinationFolder);
    }

    @Test
    public void cellPaddingTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("cellPaddingTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void cellPaddingTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("cellPaddingTest02", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4623 process negative paddings
    public void negativePaddingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("negativePaddings", sourceFolder, destinationFolder);
    }
}
