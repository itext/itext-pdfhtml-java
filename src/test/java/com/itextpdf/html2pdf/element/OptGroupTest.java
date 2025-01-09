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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class OptGroupTest extends ExtendedHtmlConversionITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/OptGroupTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/OptGroupTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void optGroupBasicTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupBasicTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupBasicTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupBasicTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupEmptyTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupEmptyTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupNestedTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupNestedTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupNestedTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupNestedTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupNoSelectTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupNoSelectTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupStylesTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupStylesTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupHeightTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupHeightTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupWidthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupWidthTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupWidthTest02() throws IOException, InterruptedException {
        // TODO DEVSIX-2444 select props parsing essentially neglects whitespace pre
        convertToPdfAndCompare("optGroupWidthTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupOverflowTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupOverflowTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupOverflowTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupOverflowTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupPseudoTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupPseudoTest01", sourceFolder, destinationFolder);
    }
}
