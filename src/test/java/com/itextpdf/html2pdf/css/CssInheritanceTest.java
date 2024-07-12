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
public class CssInheritanceTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssInheritanceTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssInheritanceTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    //em value inherited
    @Test
    public void cssInheritanceTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssInheritance01", sourceFolder, destinationFolder);
    }

    //ex value inherited
    @Test
    public void cssInheritanceTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssInheritance02", sourceFolder, destinationFolder);
    }

    //rem value inherited, shorthand
    @Test
    public void cssInheritanceTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssInheritance03", sourceFolder, destinationFolder);
    }

    //% value inherited, font-size dependent
    @Test
    public void cssInheritanceTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssInheritance04", sourceFolder, destinationFolder);
    }

    //% value inherited, layout dependent
    @Test
    public void cssInheritanceTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssInheritance05", sourceFolder, destinationFolder);
    }

    //smaller value inherited
    @Test
    public void cssInheritanceTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssInheritance06", sourceFolder, destinationFolder);
    }

    @Test
    public void cssFontFamilyInheritanceTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssFontFamilyInheritanceTest01", sourceFolder, destinationFolder);
    }
}
