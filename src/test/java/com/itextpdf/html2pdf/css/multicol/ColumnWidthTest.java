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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class ColumnWidthTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/multicol/ColumnWidthTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/multicol/ColumnWidthTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertSimpleDivTest() throws IOException, InterruptedException {
        runTest("simpleDivTest");
    }

    @Test
    public void convertColumnWidthAutoTest() throws IOException, InterruptedException {
        runTest("columnWidthAutoTest");
    }

    //TODO: DEVSIX-3596 add support of relative units that currently are not supported
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    @Test
    public void convertDifferentUnitsTest() throws IOException, InterruptedException {
        runTest("differentUnitsTest");
    }

    @Test
    public void convertParagraphsInsideContainerTest() throws IOException, InterruptedException {
        runTest("paragraphsInsideContainer");
    }

    @Test
    public void convertOneParagraphSpecifiedWithDifferentWidthTest() throws IOException, InterruptedException {
        runTest("oneParagraphSpecifiedWithDifferentWidthTest");
    }

    @Test
    public void convertMixedElementsInContainerTest() throws IOException, InterruptedException {
        runTest("mixedElementsInContainer");
    }

    @Test
    public void convertNarrowColumnsTest() throws IOException, InterruptedException {
        runTest("narrowColumns");
    }

    @Test
    public void convertLargeColumnsTest() throws IOException, InterruptedException {
        runTest("largeColumns");
    }

    @Test
    public void convertBasicArticleTest() throws IOException, InterruptedException {
        runTest("basicArticleTest");
    }

    @Test
    public void convertBasicDivMultiPageTest() throws IOException, InterruptedException {
        runTest("basicDivMultiPageTest");
    }

    @Test
    public void convertBasicDivTest() throws IOException, InterruptedException {
        runTest("basicDivTest");
    }

    @Test
    public void convertNestedElementsTest() throws IOException, InterruptedException {
        runTest("nestedElementsTest");
    }

    @Test
    public void convertBasicDisplayPropertyTest() throws IOException, InterruptedException {
        runTest("basicDisplayPropertyTest");
    }

    @Test
    public void convertDisplayPropertyWithNestedColumnsTest() throws IOException, InterruptedException {
        runTest("displayPropertyWithNestedColumnsTest");
    }

    @Test
    public void convertBasicDivWithImageTest() throws IOException, InterruptedException {
        runTest("basicDivWithImageTest");
    }

    @Test
    public void convertOverlaidContentInDivWithImageTest() throws IOException, InterruptedException {
        runTest("overlaidContentInDivWithImageTest");
    }

    @Test
    public void convertBasicFlexPropertyTest() throws IOException, InterruptedException {
        runTest("basicFlexPropertyTest");
    }

    @Test
    public void convertOverlaidFlexContentInColumnContainerTest() throws IOException, InterruptedException {
        runTest("overlaidFlexContentInColumnContainerTest");
    }

    @LogMessages(messages = {@LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)})
    @Test
    public void convertBasicFloatPropertyTest() throws IOException, InterruptedException {
        runTest("basicFloatPropertyTest");
    }

    @Test
    public void convertBasicFormTest() throws IOException, InterruptedException {
        runTest("basicFormTest");
    }

    @Test
    public void convertFormWithNestedElementsTest() throws IOException, InterruptedException {
        runTest("formWithNestedElementsTest");
    }

    @Test
    public void convertFormMultiPageTest() throws IOException, InterruptedException {
        runTest("formMultiPageTest");
    }

    @Test
    public void convertFormWithNestedElementsMultiPageTest() throws IOException, InterruptedException {
        runTest("formWithNestedElementsMultiPageTest");
    }

    @Test
    public void convertBasicOlTest() throws IOException, InterruptedException {
        runTest("basicOlTest");
    }

    @Test
    public void convertOlWithTestedElementsTest() throws IOException, InterruptedException {
        runTest("olWithNestedElementsTest");
    }

    @Test
    public void convertBasicSectionTest() throws IOException, InterruptedException {
        runTest("basicSectionTest");
    }

    @Test
    public void convertColumnizedShortParagraphsInTableCellTest() throws IOException, InterruptedException {
        runTest("columnizedShortParagraphsInTableCellTest");
    }

    @Test
    public void convertColumnizedShortPInTableCellWithHeightTest() throws IOException, InterruptedException {
        runTest("columnizedShortPInTableCellWithHeightTest");
    }

    @Test
    public void convertColumnizedSpanInTableCellTest() throws IOException, InterruptedException {
        runTest("columnizedSpanInTableCellTest");
    }

    @Test
    public void convertColumnizedContentInTableTest() throws IOException, InterruptedException {
        runTest("columnizedContentInTableTest");
    }

    @Test
    public void convertBasicUlTest() throws IOException, InterruptedException {
        runTest("basicUlTest");
    }

    @Test
    public void convertUlWithNestedElementsTest() throws IOException, InterruptedException {
        runTest("ulWithNestedElementsTest");
    }

    @Test
    public void convertImagesTest() throws IOException, InterruptedException {
        runTest("imagesTest");
    }

    @Test
    public void convertImagesWithDifferentHeightsTest() throws IOException, InterruptedException {
        runTest("imagesWithDifferentHeightsTest");
    }

    @Test
    public void convertColumnWidthEqualsImagesTest() throws IOException, InterruptedException {
        runTest("columnWidthEqualsImagesTest");
    }

    @Test
    public void diffElementsInsidePTest() throws IOException, InterruptedException {
        runTest("diffElementsInsidePTest");
    }

    //TODO: DEVSIX-7630
    @Test
    public void tableColspanTest() throws IOException, InterruptedException {
        runTest("tableColspanTest");
    }

    //TODO: DEVSIX-7630
    @Test
    public void tableRowspanTest() throws IOException, InterruptedException {
        runTest("tableRowspanTest");
    }

    //TODO: DEVSIX-7630
    @Test
    public void tableColspanRowspanTest() throws IOException, InterruptedException {
        runTest("tableColspanRowspanTest");
    }

    @Test
    public void basicHiTest() throws IOException, InterruptedException {
        runTest("basicHiTest");
    }

    @Test
    public void basicFooterHeaderTest() throws IOException, InterruptedException {
        runTest("basicFooterHeaderTest");
    }

    @Test
    public void basicDlTest() throws IOException, InterruptedException {
        runTest("basicDlTest");
    }

    @Test
    public void basicInlineElementsTest() throws IOException, InterruptedException {
        runTest("basicInlineElementsTest");
    }

    @Test
    public void basicBlockquoteTest() throws IOException, InterruptedException {
        runTest("basicBlockquoteTest");
    }


    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 3))
    @Test
    public void invalidMulticolValuesTest() throws IOException, InterruptedException {
        runTest("invalidMulticolValuesTest");
    }

    @Test
    public void columnWidthPercentageTest() throws IOException, InterruptedException {
        runTest("columnWidthPercentageTest");
    }


    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
