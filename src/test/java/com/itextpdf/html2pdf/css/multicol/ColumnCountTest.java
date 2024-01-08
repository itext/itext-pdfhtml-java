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
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/multicol"
            + "/ColumnCountTest/";
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

    @Test
    public void convertBasicFormTest() throws IOException, InterruptedException {
        runTest("basicFormTest");
    }

    @Test
    public void convertBasicUlTest() throws IOException, InterruptedException {
        runTest("basicUlTest");
    }

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

    @Test
    public void convertBasicDisplayPropertyWithNestedColumnsTest() throws IOException, InterruptedException {
        runTest("basicDisplayPropertyWithNestedColumnsTest");
    }

    //TODO: DEVSIX-7556 Support multicol+float elements on basic level
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
    public void tripleNestingTest() throws IOException, InterruptedException {
        runTest("tripleNestingTest");
    }

    @Test
    public void nestingBetweenPagesTest() throws IOException, InterruptedException {
        runTest("nestingBetweenPagesTest");
    }

    @Test
    // TODO DEVSIX-7628 During calculating occupied area of multicol container take into account children border\padding\margin
    public void tripleNestingBetweenPagesTest() throws IOException, InterruptedException {
        runTest("tripleNestingBetweenPagesTest");
    }

    @Test
    // TODO DEVSIX-7628 During calculating occupied area of multicol container take into account children border\padding\margin
    public void childBorderTest() throws IOException, InterruptedException {
        runTest("childBorderTest");
    }

    @Test
    // TODO DEVSIX-7628 During calculating occupied area of multicol container take into account children border\padding\margin
    public void childMarginTest() throws IOException, InterruptedException {
        runTest("childMarginTest");
    }

    @Test
    // TODO DEVSIX-7628 During calculating occupied area of multicol container take into account children border\padding\margin
    public void childPaddingTest() throws IOException, InterruptedException {
        runTest("childPaddingTest");
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
        runTest("basicOrphans1Test");
    }

    @Test
    public void basicOrphans2Test() throws IOException, InterruptedException {
        runTest("basicOrphans2Test");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.WIDOWS_CONSTRAINT_VIOLATED, logLevel =
                    LogLevelConstants.WARN)
    })
    public void basicWidows1Test() throws IOException, InterruptedException {
        runTest("basicWidows1Test");
    }

    @Test
    public void basicWidows2Test() throws IOException, InterruptedException {
        runTest("basicWidows2Test");
    }

    @Test
    public void heightTest() throws IOException, InterruptedException {
        runTest("height");
    }

    @Test
    public void heightToSmallToFitAllClipped() throws IOException, InterruptedException {
        runTest("heightToSmallToFitAllClipped");
    }

    @Test
    public void minHeightToSmallSoEverythingShows() throws IOException, InterruptedException {
        runTest("minHeightToSmallSoEverythingShows");
    }

    @Test
    public void minHeightBiggerSoExtraGap() throws IOException, InterruptedException {
        runTest("minHeightBiggerSoExtraGap");
    }

    @Test
    public void widthTest() throws IOException, InterruptedException {
        runTest("width");
    }

    @Test
    // TODO DEVSIX-7630 Multicol width&height advanced support
    public void widthToBigSoOverflowsOnXAxisIntoInfinity() throws IOException, InterruptedException {
        runTest("widthToBigSoOverflowsOnXAxisIntoInfinity");
    }

    @Test
    public void minWidthBigJustOverflows() throws IOException, InterruptedException {
        runTest("minWidthBigJustOverflows");
    }

    @Test
    public void minWidthContained() throws IOException, InterruptedException {
        runTest("minWidthToLittleSoJustNormalWidth");
    }

    @Test
    public void maxWidthToBig() throws IOException, InterruptedException {
        runTest("maxWidthToBig");
    }

    @Test
    public void maxWidthToSmall() throws IOException, InterruptedException {
        runTest("maxWidthToSmall");
    }

    @Test
    public void widthToBigWrapped() throws IOException, InterruptedException {
        runTest("widthToBigWrapped");
    }

    @Test
    public void maxWidthToBigWrapped() throws IOException, InterruptedException {
        runTest("maxWidthToBigWrapped");
    }

    @Test
    // TODO DEVSIX-7702 Support content overflowing for multicol layouting in case of limited available area
    public void multiColLimitedArea() throws IOException, InterruptedException {
        runTest("multicolLimitedArea");
    }

    @Test
    public void multiColLimitedArea2() throws IOException, InterruptedException {
        runTest("multicolLimitedArea2");
    }

    @Test
    // TODO DEVSIX-7630 Multicol width&height advanced support
    public void multiColLimitedArea3() throws IOException, InterruptedException {
        runTest("multicolLimitedArea3");
    }

    @Test
    // TODO DEVSIX-7702 Support content overflowing for multicol layouting in case of limited available area
    public void multiColLimitedArea4() throws IOException, InterruptedException {
        runTest("multicolLimitedArea4");
    }

    @Test
    // TODO DEVSIX-7702 Support content overflowing for multicol layouting in case of limited available area
    public void multipleAttributes() throws IOException, InterruptedException {
        runTest("multiple_attributes");
    }

    @Test
    public void multipleAttributes1() throws IOException, InterruptedException {
        runTest("multiple_attributes1");
    }

    @Test
    public void multipleAttributes2() throws IOException, InterruptedException {
        runTest("multiple_attributes2");
    }

    @Test
    // TODO DEVSIX-7630 Multicol width&height advanced support
    public void heightMultiPage() throws IOException, InterruptedException {
        runTest("height_multipage");
    }

    @Test
    public void biggerThanColumnDivTest() throws IOException, InterruptedException {
        runTest("biggerThanColumnDivTest");
    }

    @Test
    public void biggerThanColumnImageTest() throws IOException, InterruptedException {
        runTest("biggerThanColumnImageTest");
    }

    @Test
    public void biggerThanColumnImageOverflowHiddenTest() throws IOException, InterruptedException {
        runTest("biggerThanColumnImageOverflowHiddenTest");
    }

    @Test
    public void biggerThanColumnImageOverflowScrollTest() throws IOException, InterruptedException {
        runTest("biggerThanColumnImageOverflowScrollTest");
    }

    @Test
    public void overflowOnMulticolContainerTest() throws IOException, InterruptedException {
        runTest("overflowOnMulticolContainerTest");
    }

    @Test
    public void shortHandResolverTest01() throws IOException, InterruptedException {
        runTest("shortHandResolverTest01");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
