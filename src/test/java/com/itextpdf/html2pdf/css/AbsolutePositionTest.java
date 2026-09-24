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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class AbsolutePositionTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/AbsolutePositionTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/AbsolutePositionTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest07", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest08", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED),
    })
    public void absolutePosition09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest09", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest12", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest13", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest14", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePosition15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest15", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePositionTest16() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest16", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePositionTest17() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest17", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Disabled("DEVSIX-1818")
    @Test
    public void absolutePositionTest18() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest18", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosNoTopBottomTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosNoTopBottomTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePositionSplitPagesBeforeTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionSplitPagesBeforeText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePositionSplitPagesAfterTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionSplitPagesAfterText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absolutePositionSplitPagesAfterTextInImageBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionSplitPagesAfterTextInImageBlock", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosTopOnlyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosTopOnly", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosLeftOnlyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosLeftOnly", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosBottomOnlyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosBottomOnly", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosRightOnlyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosRightOnly", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosRightBottomOnlyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosRightBottomOnly", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosTopRightCornerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosTopRightCorner", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosBottomLeftCornerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosBottomLeftCorner", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosNoOffsetsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosNoOffsets", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosLeftRightOverconstrainedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosLeftRightOverconstrained", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosMinMaxWidthClashTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosMinMaxWidthClash", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosInsetShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosInsetShorthand", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosCenteringMarginAutoTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosCenteringMarginAuto", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, count = 4))
    public void absPosPercentageAutoHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosPercentageAutoHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosZeroNegativeDimensionsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosZeroNegativeDimensions", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosExtremeOffsetsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosExtremeOffsets", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosExtremelyOversizedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosExtremelyOversized", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosRtlLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosRtlLeftRight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosRtlLeftRightBothTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosRtlLeftRightBoth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)
    })
    public void absPosWritingModeVerticalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosWritingModeVertical", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absoluteInsideRelativeInsideAbsoluteTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absoluteInsideRelativeInsideAbsolute", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absoluteNestedFourLevelsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absoluteNestedFourLevels", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absoluteInsideStaticAncestorChainTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absoluteInsideStaticAncestorChain", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, count = 3))
    public void absPosOnImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosOnImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED),
            @LogMessage(messageTemplate = IoLogMessageConstant.FONT_PROPERTY_MUST_BE_PDF_FONT_OBJECT, count = 2)
    })
    public void absPosOnInlineTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosOnInlineText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosOnListTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosOnList", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosOnTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosOnTable", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED))
    public void absPosOnFormControlsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosOnFormControls", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, count = 2))
    public void absPosComboFlexNestedMissingAxisTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosComboFlexNestedMissingAxis", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT),
    })
    public void absPosComboRtlWritingModeNestedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosComboRtlWritingModeNested", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosComboStaticChainOversizedMinWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosComboStaticChainOversizedMinWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void absPosContainerSpansAcrossMultiplePagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("absPosContainerSpansAcrossMultiplePages", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
