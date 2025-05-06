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
package com.itextpdf.html2pdf.css.grid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.layout.exceptions.LayoutExceptionMessageConstant;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class GridTemplatesTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridTemplatesTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridTemplatesTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void basicColumnOneDivTest() throws IOException, InterruptedException {
        runTest("basicColumnOneDivTest");
    }

    @Test
    public void basicColumnFewDivsTest() throws IOException, InterruptedException {
        runTest("basicColumnFewDivsTest");
    }

    @Test
    public void basicColumnFewDivsWithFrTest() throws IOException, InterruptedException {
        runTest("basicColumnFewDivsWithFrTest");
    }

    @Test
    public void basicColumnFewDivs2Test() throws IOException, InterruptedException {
        runTest("basicColumnFewDivs2Test");
    }

    @Test
    public void basicColumnMultiPageTest() throws IOException, InterruptedException {
        runTest("basicColumnMultiPageTest");
    }

    @Test
    public void basicColumnStartEndTest() throws IOException, InterruptedException {
        runTest("basicColumnStartEndTest");
    }

    @Test
    public void basicColumnStartEnd2Test() throws IOException, InterruptedException {
        runTest("basicColumnStartEnd2Test");
    }

    @Test
    // We need to add a "dry run" for cell balancing without layouting to determine final grid size
    public void basicColumnStartEnd3Test() throws IOException, InterruptedException {
        runTest("basicColumnStartEnd3Test");
    }

    //--------------- without grid-templates-columns and grid-templates-rows ---------------
    @Test
    public void basicOnlyGridDisplayTest() throws IOException, InterruptedException {
        runTest("basicOnlyGridDisplayTest");
    }

    //--------------- grid-templates-rows ---------------
    @Test
    public void basicRowOneDivTest() throws IOException, InterruptedException {
        runTest("basicRowOneDivTest");
    }

    @Test
    public void basicRowFewDivsTest() throws IOException, InterruptedException {
        runTest("basicRowFewDivsTest");
    }

    @Test
    public void basicRowStartEndTest() throws IOException, InterruptedException {
        runTest("basicRowStartEndTest");
    }

    //--------------- grid-templates-columns + grid-templates-rows ---------------

    @Test
    public void basicColumnRowFewDivs1Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs1Test");
    }

    @Test
    public void basicColumnRowFewDivs2Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs2Test");
    }

    @Test
    public void basicColumnRowFewDivs3Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs3Test");
    }

    @Test
    public void basicColumnRowFewDivs4Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs4Test");
    }

    @Test
    public void basicColumnRowStartEndTest() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEndTest");
    }

    @Test
    public void basicColumnRowStartEnd1Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd1Test");
    }

    @Test
    public void basicColumnRowStartEnd2Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd2Test");
    }

    @Test
    public void basicColumnRowStartEnd3Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd3Test");
    }

    @Test
    public void basicColumnRowStartEnd4Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd4Test");
    }

    @Test
    public void basicColumnRowStartEnd5Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd5Test");
    }

    @Test
    public void basicColumnRowStartEnd6Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd6Test");
    }

    @Test
    public void basicColumnRowStartEnd7Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd7Test");
    }

    @Test
    public void basicColumnRowStartEnd8Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd8Test");
    }

    @Test
    public void basicColumnRowStartEnd9Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd9Test");
    }

    @Test
    public void basicColumnRowStartEnd10Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd10Test");
    }

    @Test
    public void basicColumnRowStartEnd11Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd11Test");
    }
    @Test
    public void basicColumnRowStartEnd12Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd12Test");
    }
    @Test
    public void basicColumnRowStartEnd13Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd13Test");
    }
    @Test
    public void basicColumnRowStartEnd14Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd14Test");
    }
    @Test
    public void basicColumnRowStartEnd15Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd15Test");
    }
    @Test
    public void basicColumnRowStartEnd16Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd16Test");
    }
    @Test
    public void basicColumnRowStartEnd17Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd17Test");
    }

    @Test
    public void basicColumnRowStartEnd18Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd18Test");
    }

    @Test
    public void basicColumnRowStartEnd19Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd19Test");
    }

    @Test
    public void basicColumnRowStartEndWithInlineTextTest() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEndWithInlineTextTest");
    }

    @Test
    public void basicGridAfterParagraphTest() throws IOException, InterruptedException {
        runTest("basicGridAfterParagraphTest");
    }

    @Test
    public void basicRowFlowTest() throws IOException, InterruptedException {
        runTest("basicRowFlowTest");
    }

    @Test
    public void basicRowDenseFlowTest() throws IOException, InterruptedException {
        runTest("basicRowDenseFlowTest");
    }

    @Test
    public void basicColumnFlowTest() throws IOException, InterruptedException {
        runTest("basicColumnFlowTest");
    }

    @Test
    public void basicColumnDenseFlowTest() throws IOException, InterruptedException {
        runTest("basicColumnDenseFlowTest");
    }

    @Test
    public void fixedTemplatesAndCellDoesNotHaveDirectNeighborTest() throws IOException, InterruptedException {
        runTest("fixedTemplatesAndCellDoesNotHaveDirectNeighborTest");
    }

    @Test
    public void gridInsideGridTest() throws IOException, InterruptedException {
        runTest("gridInsideGridTest");
    }

    @Test
    public void gridInsideGridOnPageBreakTest() throws IOException, InterruptedException {
        runTest("gridInsideGridOnPageBreakTest");
    }

    @Test
    public void elementDoesntFitContentTest() throws IOException, InterruptedException {
        runTest("elementDoesntFitContentTest");
    }

    @Test
    public void elementDoesntFitTest() throws IOException, InterruptedException {
        runTest("elementDoesntFitTest");
    }

    @Test
    public void elementDoesntFitHorizontallyTest() throws IOException, InterruptedException {
        runTest("elementDoesntFitHorizontallyTest");
    }

    @Test
    public void elementDoesntFitOverflowingToNextPageTest() throws IOException, InterruptedException {
        runTest("elementDoesntFitOverflowingToNextPageTest");
    }

    @Test
    public void elementDoesntFitContentOverflowingToNextPageTest() throws IOException, InterruptedException {
        runTest("elementDoesntFitContentOverflowingToNextPageTest");
    }

    @Test
    public void textsWithOverflowTest() throws IOException, InterruptedException {
        runTest("textsWithOverflowTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, logLevel =
                    LogLevelConstants.WARN)})
    public void imageElementDoesntFitTest() throws IOException, InterruptedException {
        runTest("imageElementDoesntFitTest");
    }

    @Test
    public void manyImageElementsTest() throws IOException, InterruptedException {
        runTest("manyImageElementsTest");
    }

    @Test
    public void imageElementsOn2ndPageTest() throws IOException, InterruptedException {
        runTest("imageElementsOn2ndPageTest");
    }

    @Test
    public void gridWithBrTest() throws IOException, InterruptedException {
        runTest("gridWithBrTest");
    }

    @Test
    public void gridWithPageBreakTest() throws IOException, InterruptedException {
        runTest("gridWithPageBreakTest");
    }

    @Test
    public void gridWithTableTest() throws IOException, InterruptedException {
        runTest("gridWithTableTest");
    }

    @Test
    public void columnFlowOnSplitTest() throws IOException, InterruptedException {
        runTest("columnFlowOnSplitTest");
    }

    @Test
    public void basicGridRemValuesTest() throws IOException, InterruptedException {
        runTest("grid-layout-rem");
    }

    @Test
    public void basicGridEmValuesTest() throws IOException, InterruptedException {
        runTest("grid-layout-em");
    }

    @Test
    public void percentageTemplateHeightTest() throws IOException, InterruptedException {
        runTest("percentageTemplateHeightTest");
    }

    @Test
    public void percentageTemplateHeightWithFixedHeightTest() throws IOException, InterruptedException {
        runTest("percentageTemplateHeightWithFixedHeightTest");
    }

    @Test
    public void percentageFitContentWithFrTest() throws IOException, InterruptedException {
        runTest("percentageFitContentWithFrTest");
    }

    @Test
    public void autoFillRepeatWithGapsTest() throws IOException, InterruptedException {
        runTest("autoFillRepeatWithGapsTest");
    }

    @Test
    public void autoFitWithSingleCellTest() throws IOException, InterruptedException {
        runTest("autoFitWithSingleCellTest");
    }

    @Test
    public void columnFlowAutoFillTest() throws IOException, InterruptedException {
        runTest("columnFlowAutoFillTest");
    }

    @Test
    public void fitContentAndFrTest() throws IOException, InterruptedException {
        runTest("fitContentAndFrTest");
    }

    @Test
    public void fixedFitContentTest() throws IOException, InterruptedException {
        runTest("fixedFitContentTest");
    }

    @Test
    public void fixedRepeatWithGapsTest() throws IOException, InterruptedException {
        runTest("fixedRepeatWithGapsTest");
    }

    @Test
    public void inlineAutoFillTest() throws IOException, InterruptedException {
        runTest("inlineAutoFillTest");
    }

    @LogMessages(messages = @LogMessage(messageTemplate
            = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CANNOT_BE_COMBINED_WITH_INDEFINITE_SIZES))
    @Test
    public void invalidAutoRepeatTest() throws IOException, InterruptedException {
        runTest("invalidAutoRepeatTest");
    }

    @Test
    public void invalidParameterRepeatTest() throws IOException, InterruptedException {
        runTest("invalidParameterRepeatTest");
    }

    @Test
    public void minMaxAutoFillTest() throws IOException, InterruptedException {
        runTest("minMaxAutoFillTest");
    }

    @Test
    public void minMaxAutoFillWithHeightTest() throws IOException, InterruptedException {
        runTest("minMaxAutoFillWithHeightTest");
    }

    @Test
    public void minMaxAutoFillWithMaxHeightTest() throws IOException, InterruptedException {
        runTest("minMaxAutoFillWithMaxHeightTest");
    }

    @Test
    public void mixedRepeatsTest() throws IOException, InterruptedException {
        runTest("mixedRepeatsTest");
    }

    @Test
    public void resolvableAutoFillSimpleTest() throws IOException, InterruptedException {
        runTest("resolvableAutoFillSimpleTest");
    }

    @Test
    public void resolvableAutoFitWithMinMaxTest() throws IOException, InterruptedException {
        runTest("resolvableAutoFitWithMinMaxTest");
    }

    @Test
    public void severalValuesAutoFillTest() throws IOException, InterruptedException {
        runTest("severalValuesAutoFillTest");
    }

    @Test
    public void autoFitOnIntrinsicAreaTest() throws IOException, InterruptedException {
        runTest("autoFitOnIntrinsicAreaTest");
    }

    @Test
    public void autoFillWithDefiniteMinMaxTest() throws IOException, InterruptedException {
        runTest("autoFillWithDefiniteMinMaxTest");
    }

    @Test
    public void autoFillWithIndefiniteMinMaxTest() throws IOException, InterruptedException {
        runTest("autoFillWithIndefiniteMinMaxTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutExceptionMessageConstant.FLEXIBLE_ARENT_ALLOWED_AS_MINIMUM_IN_MINMAX)
    })
    public void minmaxWithMinFrTest() throws IOException, InterruptedException {
        runTest("minmaxWithMinFrTest");
    }

    @Test
    public void minmaxWithMaxFrTest() throws IOException, InterruptedException {
        runTest("minmaxWithMaxFrTest");
    }

    @Test
    public void minMaxWithIndefiniteMinTest() throws IOException, InterruptedException {
        runTest("minMaxWithIndefiniteMinTest");
    }

    @Test
    public void pointZeroFlexTest() throws IOException, InterruptedException {
        runTest("pointZeroFlexTest");
    }

    @Test
    public void pointZeroFlexTest2() throws IOException, InterruptedException {
        runTest("pointZeroFlexTest2");
    }

    @Test
    public void pointZeroFlexTest3() throws IOException, InterruptedException {
        runTest("pointZeroFlexTest3");
    }

    @Test
    public void pointZeroFlexTest4() throws IOException, InterruptedException {
        runTest("pointZeroFlexTest4");
    }

    @Test
    public void pointZeroFlexTest5() throws IOException, InterruptedException {
        runTest("pointZeroFlexTest5");
    }

    @Test
    public void pointZeroFlexTest6() throws IOException, InterruptedException {
        runTest("pointZeroFlexTest6");
    }

    @Test
    public void spanOnlyFrTest() throws IOException, InterruptedException {
        runTest("spanOnlyFrTest");
    }

    @Test
    public void autoFitOnIntrinsicAreaWithLargeBorderTest() throws IOException, InterruptedException {
        runTest("autoFitOnIntrinsicAreaWithLargeBorderTest");
    }

    @Test
    public void autoFitWithLargeBorderTest() throws IOException, InterruptedException {
        runTest("autoFitWithLargeBorderTest");
    }

    @Test
    public void autoFitOnIntrinsicAreaWithLargeMarginPaddingTest() throws IOException, InterruptedException {
        runTest("autoFitOnIntrinsicAreaWithLargeMarginPaddingTest");
    }

    @Test
    public void autoRepeatOnIntrinsicAreaTest() throws IOException, InterruptedException {
        runTest("autoRepeatOnIntrinsicAreaTest");
    }

    @LogMessages(messages = @LogMessage(messageTemplate
            = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CANNOT_BE_COMBINED_WITH_INDEFINITE_SIZES))
    @Test
    public void autoRepeatWithIntrinsicArgumentTest() throws IOException, InterruptedException {
        runTest("autoRepeatWithIntrinsicArgumentTest");
    }

    @LogMessages(messages = @LogMessage(messageTemplate
            = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CAN_BE_USED_ONLY_ONCE))
    @Test
    public void twoAutoRepeatsTest() throws IOException, InterruptedException {
        runTest("twoAutoRepeatsTest");
    }

    @Test
    public void autoFillRepeatWithFlexMinMaxTest() throws IOException, InterruptedException {
        runTest("autoFillRepeatWithFlexMinMaxTest");
    }

    @Test
    public void autoFitRepeatWithFlexMinMaxTest() throws IOException, InterruptedException {
        runTest("autoFitRepeatWithFlexMinMaxTest");
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_WAS_NOT_RECOGNISED, logLevel = LogLevelConstants.WARN)
    })
    @Test
    public void repeatInsideMinMaxTest() throws IOException, InterruptedException {
        runTest("repeatInsideMinMaxTest");
    }

    @LogMessages(messages = @LogMessage(messageTemplate
            = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CANNOT_BE_COMBINED_WITH_INDEFINITE_SIZES))
    @Test
    public void autoRepeatWithFitContentTest() throws IOException, InterruptedException {
        runTest("autoRepeatWithFitContentTest");
    }

    @Test
    public void fixedRepeatWithFitContentTest() throws IOException, InterruptedException {
        runTest("fixedRepeatWithFitContentTest");
    }

    @Test
    public void fixedRepeatWithMinMaxContentTest() throws IOException, InterruptedException {
        runTest("fixedRepeatWithMinMaxContentTest");
    }

    @LogMessages(messages = @LogMessage(messageTemplate
            = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CANNOT_BE_COMBINED_WITH_INDEFINITE_SIZES))
    @Test
    public void autoRepeatWithLeadingMaxContentTest() throws IOException, InterruptedException {
        runTest("autoRepeatWithLeadingMaxContentTest");
    }

    @Test
    public void autoFitWithGapsTest() throws IOException, InterruptedException {
        runTest("autoFitWithGapsTest");
    }

    @Test
    public void rowColumnShorthandSimpleTest() throws IOException, InterruptedException {
        runTest("rowColumnShorthandSimpleTest");
    }

    @Test
    public void gridShorthandColumnAutoFlowTest() throws IOException, InterruptedException {
        runTest("gridShorthandColumnAutoFlowTest");
    }

    @Test
    public void gridShorthandRowAutoFlowTest() throws IOException, InterruptedException {
        runTest("gridShorthandRowAutoFlowTest");
    }

    @Test
    public void shrankTemplateAfterAutoFitTest() throws IOException, InterruptedException {
        runTest("shrankTemplateAfterAutoFitTest");
    }

    @Test
    public void minHeightTest() throws IOException, InterruptedException {
        runTest("minHeightTest");
    }

    @Test
    public void minHeightFlexRowsTest() throws IOException, InterruptedException {
        runTest("minHeightFlexRowsTest");
    }

    @Test
    // TODO DEVSIX-8426 Fix working with min\max-height\width on grid container
    public void maxHeightTest() throws IOException, InterruptedException {
        runTest("maxHeightTest");
    }

    @Test
    // TODO DEVSIX-8426 Fix working with min\max-height\width on grid container
    public void maxHeightFlexRowsTest() throws IOException, InterruptedException {
        runTest("maxHeightFlexRowsTest");
    }

    @Test
    public void maxHeightFlexRowsTest2() throws IOException, InterruptedException {
        runTest("maxHeightFlexRowsTest2");
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION,
                    logLevel = LogLevelConstants.WARN),
            @LogMessage(messageTemplate = IoLogMessageConstant.UNKNOWN_COLOR_FORMAT_MUST_BE_RGB_OR_RRGGBB,
                    logLevel = LogLevelConstants.ERROR, count = 11)})
    @Test
    public void divNestingTest() throws IOException, InterruptedException {
        runTest("divNestingTest");
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.SUBGRID_VALUE_IS_NOT_SUPPORTED, logLevel = LogLevelConstants.WARN),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_WAS_NOT_RECOGNISED, logLevel = LogLevelConstants.WARN)
    })
    @Test
    public void subgridTest() throws IOException, InterruptedException {
        runTest("subgridTest");
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_WAS_NOT_RECOGNISED, logLevel = LogLevelConstants.WARN)
    })
    @Test
    public void invalidTemplateColumns() throws IOException, InterruptedException {
        runTest("invalidTemplateColumns");
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_WAS_NOT_RECOGNISED, logLevel = LogLevelConstants.WARN)
    })
    @Test
    public void invalidTemplateRows() throws IOException, InterruptedException {
        runTest("invalidTemplateRows");
    }

    @Test
    public void gridSplitPaddingMarginBorderTest() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest");
    }

    @Test
    public void gridSplitPaddingMarginBorderTest2() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest2");
    }

    @Test
    public void gridSplitPaddingMarginBorderTest3() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest3");
    }

    @Test
    public void gridSplitPaddingMarginBorderTest4() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest4");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, logLevel = LogLevelConstants.WARN)
    })
    public void gridSplitPaddingMarginBorderTest5() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest5");
    }

    @Test
    public void gridSplitPaddingMarginBorderTest6() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest6");
    }

    @Test
    public void gridSplitPaddingMarginBorderTest7() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest7");
    }

    @Test
    public void gridSplitPaddingMarginBorderTest8() throws IOException, InterruptedException {
        runTest("gridSplitPaddingMarginBorderTest8");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
