/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 Apryse Group NV
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
package com.itextpdf.html2pdf.css.verticaltext;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class CombineUprightTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER =
            "./src/test/resources/com/itextpdf/html2pdf/css/verticaltext/CombineUprightTest/";
    private static final String DESTINATION_FOLDER =
            "./target/test/com/itextpdf/html2pdf/css/verticaltext/CombineUprightTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void combineRunLengthsTest() throws IOException, InterruptedException {
        compare("combineRunLengths");
    }

    @Test
    public void combineOrientationsTest() throws IOException, InterruptedException {
        compare("combineOrientations");
    }

    @Test
    public void combineInheritanceTest() throws IOException, InterruptedException {
        compare("combineInheritance");
    }

    @Test
    public void combineWrappingTest() throws IOException, InterruptedException {
        compare("combineWrapping");
    }

    @Test
    public void combineInlineStylesTest() throws IOException, InterruptedException {
        compare("combineInlineStyles");
    }

    @Test
    public void combineHorizontalTest() throws IOException, InterruptedException {
        compare("combineHorizontal");
    }

    private void compare(String name) throws IOException, InterruptedException {
        convertToPdfAndCompare(name, SOURCE_FOLDER, DESTINATION_FOLDER);
        try (PdfDocument pdf = new PdfDocument(new PdfReader(DESTINATION_FOLDER + name + ".pdf"))) {
            Assertions.assertEquals(1, pdf.getNumberOfPages(), name + " should fit on one A4 page");
        }
    }
}
