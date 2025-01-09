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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class WidthTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/WidthTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/WidthTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void percentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("percentTest", sourceFolder, destinationFolder);
    }

    @Test
    public void maxWidthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("maxWidthTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void minWidthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("minWidthTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void minMaxWidthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("minMaxWidthTest01", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5726 the width for table currently set incorrect.
    public void percentMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("percentMarginTest", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5726 the width for table currently set incorrect.
    public void percentMarginTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("percentMarginTable", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeInlineBlockWidthWithTextIndentTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeInlineBlockWidthWithTextIndentTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeInlineBlockWidthWithTextIndentTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeInlineBlockWidthWithTextIndentTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeInlineBlockWidthWithTextIndentTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeInlineBlockWidthWithTextIndentTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void imageWidthInPercentValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageWidthInPercentValue", sourceFolder, destinationFolder);
    }

    @Test
    public void maxWidthInPixelForTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("maxWidthForChildTablesInPixels", sourceFolder, destinationFolder);
    }
}
