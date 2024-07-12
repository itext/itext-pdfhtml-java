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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.IOException;


@Tag("IntegrationTest")
public class CssOutlineTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssOutlineTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssOutlineTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void cssOutlineTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssOutline01", sourceFolder, destinationFolder);
    }

    @Test
    public void cssOutlineTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssOutline02", sourceFolder, destinationFolder);
    }

    @Test
    public void cssOutlineTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssOutline03", sourceFolder, destinationFolder);
    }

    @Test
    public void cssOutlineTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssOutline04", sourceFolder, destinationFolder, true);
    }

    @Test
    public void cssOutlineTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssOutline05", sourceFolder, destinationFolder);
    }
}
