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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Tag("IntegrationTest")
public class FontStyleParameterizedTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FontStyleParameterizedTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FontStyleParameterizedTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }


    public static Iterable<Object[]> rotationRelatedProperties() {
        return Arrays.asList(new Object[][]{
                {"fontWithSerifTest"},
                {"fontWithSansSerifTest"},
                {"monospaceFontTest"},
                {"cursiveFontTest"},
                {"fantasyFontTest"}
        });
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rotationRelatedProperties")
    public void convertToPdfA4Test(String htmlName) throws IOException, InterruptedException {
        String htmlPath = SOURCE_FOLDER + htmlName + ".html";
        String pdfPath = DESTINATION_FOLDER + htmlName + ".pdf";
        String cmpPdfPath = SOURCE_FOLDER + "cmp_" + htmlName + ".pdf";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath));
        Assertions.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, DESTINATION_FOLDER, "diff_"));

    }
}
