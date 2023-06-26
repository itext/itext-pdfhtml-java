/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ColumnGapTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/multicol/ColumnGapTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/multicol/ColumnGapTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicTest() throws IOException, InterruptedException {
        runTest("basicTest");
    }

    @Test
    public void convertLargeColumnGapValueTest() throws IOException, InterruptedException {
        runTest("largeColumnGapValueTest");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate =
            Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void convertNegativeColumnGapValueTest() throws IOException, InterruptedException {
        runTest("negativeColumnGapValueTest");
    }

    @Test
    public void convertSmallColumnGapValueTest() throws IOException, InterruptedException {
        runTest("smallColumnGapValueTest");
    }

    @Test
    public void convertFloatColumnGapValueTest() throws IOException, InterruptedException {
        runTest("floatColumnGapValueTest");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate =
            Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void convertDifferentUnitsTest() throws IOException, InterruptedException {
        runTest("differentUnitsTest");
    }

    @Test
    public void convertColumnsAndGapTest() throws IOException, InterruptedException {
        runTest("columnsAndGapTest");
    }

    @Test
    public void convertMarginTest() throws IOException, InterruptedException {
        runTest("marginTest");
    }

    @Test
    public void convertPaddingTest() throws IOException, InterruptedException {
        runTest("paddingTest");
    }

    @Test
    public void convertMixedElementsTest() throws IOException, InterruptedException {
        runTest("mixedElementsTest");
    }

    @Test
    public void convertNestedElementsTest() throws IOException, InterruptedException {
        runTest("nestedElementsTest");
    }

    @Test
    public void convertGapShorthandTest() throws IOException, InterruptedException {
        runTest("gapShorthandTest");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setMulticolEnabled(true).setBaseUri(SOURCE_FOLDER));
    }
}
