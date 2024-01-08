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
package com.itextpdf.html2pdf.css.multicol;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ColumnsTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/multicol/ColumnsTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/multicol/ColumnsTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertSimpleOnlyColTest() throws IOException, InterruptedException {
        runTest("simpleOnlyColTest");
    }

    @Test
    public void convertSimpleOnlyWidthTest() throws IOException, InterruptedException {
        runTest("simpleOnlyWidthTest");
    }

    @Test
    public void convertSimpleTest() throws IOException, InterruptedException {
        runTest("simpleTest");
    }

    @Test
    public void convertColCColWidthSimpleTest() throws IOException, InterruptedException {
        runTest("colCColWidthSimpleTest");
    }

    @Test
    public void convertLargeNumbersOfColumnsTest() throws IOException, InterruptedException {
        runTest("largeNumbersOfColumnsTest");
    }

    @Test
    public void convertLargeWidthTest() throws IOException, InterruptedException {
        runTest("largeWidthTest");
    }

    @Test
    public void convertSimpleAutoTest() throws IOException, InterruptedException {
        runTest("simpleAutoTest");
    }

    @Test
    public void convertMixedElementsTest() throws IOException, InterruptedException {
        runTest("mixedElementsTest");
    }

    @Test
    public void convertFormMultiPageTest() throws IOException, InterruptedException {
        runTest("formMultiPageTest");
    }

    @Test
    public void convertNestedColContentTest() throws IOException, InterruptedException {
        runTest("nestedColContentTest");
    }

    @Test
    public void convertNestedColCColWContentTest() throws IOException, InterruptedException {
        runTest("nestedColCColWContentTest");
    }

    @Test
    public void convertColumnsAndWidthPropertyTest() throws IOException, InterruptedException {
        runTest("columnsAndWidthPropertyTest");
    }

    @Test
    public void convertOutsidePageContentTest() throws IOException, InterruptedException {
        runTest("outsidePageContentTest");
    }



    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
