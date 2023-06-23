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
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ColumnCountTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/multicol/ColumnCountTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/multicol/ColumnCountTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicArticleTest() throws IOException, InterruptedException {
        runTest("basicArticleTest");
    }

    @Test
    public void convertBasicDivTest() throws IOException, InterruptedException {
        runTest("basicDivTest");
    }

    @Test
    public void convertBasicDivWithImageTest() throws IOException, InterruptedException {
        runTest("basicDivWithImageTest");
    }

    @Test
    public void convertBasicPTest() throws IOException, InterruptedException {
        runTest("basicPTest");
    }

    @Test
    public void diffElementsInsidePTest() throws IOException, InterruptedException {
        runTest("diffElementsInsidePTest");
    }

    //TODO: DEVSIX-7591 support nested multicol layouting
    @Test
    public void convertBasicFormTest() throws IOException, InterruptedException {
        runTest("basicFormTest");
    }

    @Test
    public void convertBasicUlTest() throws IOException, InterruptedException {
        runTest("basicUlTest");
    }

    //TODO: DEVSIX-7591 Support nested multicol layouting
    @Test
    public void convertBasicOlTest() throws IOException, InterruptedException {
        runTest("basicOlTest");
    }

    @Test
    public void convertBasicTableTest() throws IOException, InterruptedException {
        runTest("basicTableTest");
    }

    @Test
    public void tableColspanTest() throws IOException, InterruptedException {
        runTest("tableColspanTest");
    }

    @Test
    public void tableRowspanTest() throws IOException, InterruptedException {
        runTest("tableRowspanTest");
    }

    @Test
    public void tableColspanRowspanTest() throws IOException, InterruptedException {
        runTest("tableColspanRowspanTest");
    }

    //TODO: DEVSIX-7591 Support nested multicol layouting
    @Test
    public void convertBasicSectionTest() throws IOException, InterruptedException {
        runTest("basicSectionTest");
    }

    @Test
    public void convertBasicDivMultiPageDocumentsTest() throws IOException, InterruptedException {
        runTest("basicDivMultiPageTest");
    }

    @Test
    public void convertBasicFormMultiPageDocumentsTest() throws IOException, InterruptedException {
        runTest("basicFormMultiPageTest");
    }

    @Test
    public void convertBigFormMultiPageDocumentsTest() throws IOException, InterruptedException {
        runTest("bigFormMultiPageTest");
    }

    @Test
    public void convertBasicDisplayPropertyTest() throws IOException, InterruptedException {
        runTest("basicDisplayPropertyTest");
    }

    //TODO: DEVSIX-7591 Support nested multicol layouting
    @Test
    public void convertBasicDisplayPropertyWithNestedColumnsTest() throws IOException, InterruptedException {
        runTest("basicDisplayPropertyWithNestedColumnsTest");
    }

    //TODO: DEVSIX-7556
    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)})
    public void convertBasicFloatPropertyTest() throws IOException, InterruptedException {
        runTest("basicFloatPropertyTest");
    }

    @Test
    public void convertBasicFlexPropertyTest() throws IOException, InterruptedException {
        runTest("basicFlexPropertyTest");
    }

    @Test
    public void convertImagesWithDifferentColValuesTest() throws IOException, InterruptedException {
        runTest("imagesWithDifferentColValuesTest");
    }

    @Test
    public void paddingsMarginsBorderBackgrounds() throws IOException, InterruptedException {
        runTest("paddingsMarginsBorderBackgrounds");
    }


    @Test
    public void borderOnlyTest() throws IOException, InterruptedException {
        runTest("borderOnly");
    }


    @Test
    public void paddingOnlyTest() throws IOException, InterruptedException {
        runTest("paddingOnly");
    }


    @Test
    public void marginOnlyTest() throws IOException, InterruptedException {
        runTest("marginOnly");
    }


    @Test
    public void splitInnerParagraphBetweenColumns() throws IOException, InterruptedException {
        runTest("splitInnerParagraphBetweenColumns");
    }

    @Test
    public void splitInnerParagraphWithoutMarginBetweenColumns() throws IOException, InterruptedException {
        runTest("splitInnerParagraphWithoutMarginBetweenColumns");
    }

    @Test
    public void splitEmptyBlockElementsBetweenColumns() throws IOException, InterruptedException {
        runTest("splitEmptyBlockElementsBetweenColumns");
    }


    @Test
    public void splitEmptyParagraphElementsBetweenColumns() throws IOException, InterruptedException {
        runTest("splitEmptyParagraphElementsBetweenColumns");
    }

    @Test
    public void splitEmptyContinuousBlockElementBetweenColumns() throws IOException, InterruptedException {
        runTest("splitEmptyContinuousBlockElementBetweenColumns");
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

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setMulticolEnabled(true).setBaseUri(SOURCE_FOLDER));
    }

    @Test
    public void imagesMultipageTest() throws IOException, InterruptedException {
        runTest("imagesMultipageTest");
    }

    @Test
    public void imagesWithParagraphMultipageTest() throws IOException, InterruptedException {
        runTest("imagesWithParagraphMultipageTest");
    }

    @Test
    public void basicOrphans1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicOrphans1Test",
                SOURCE_FOLDER, DESTINATION_FOLDER, false, new ConverterProperties().setMulticolEnabled(true));
    }

    @Test
    public void basicOrphans2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicOrphans2Test",
                SOURCE_FOLDER, DESTINATION_FOLDER, false, new ConverterProperties().setMulticolEnabled(true));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.WIDOWS_CONSTRAINT_VIOLATED, logLevel = LogLevelConstants.WARN, count = 2)
    })
    public void basicWidows1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicWidows1Test",
                SOURCE_FOLDER, DESTINATION_FOLDER, false, new ConverterProperties().setMulticolEnabled(true));
    }

    @Test
    public void basicWidows2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicWidows2Test",
                SOURCE_FOLDER, DESTINATION_FOLDER, false, new ConverterProperties().setMulticolEnabled(true));
    }
}
