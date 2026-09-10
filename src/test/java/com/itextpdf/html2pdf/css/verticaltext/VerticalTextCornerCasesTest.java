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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextCornerCasesTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/verticaltext/VerticalTextCornerCasesTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/verticaltext/VerticalTextCornerCasesTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerCombiningMarkAloneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerCombiningMarkAlone", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerExtremeEverythingAtOnceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerExtremeEverythingAtOnce", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerGeneratedContentPseudoElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerGeneratedContentPseudoElement", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerHugeBorderRadiusTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerHugeBorderRadius", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerHugeFontTinyBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerHugeFontTinyBox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerHugeLineHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerHugeLineHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerHugeMarginAllSidesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerHugeMarginAllSides", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerHugeMarginOneSideAsymmetricTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerHugeMarginOneSideAsymmetric", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerHugeNegativeMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerHugeNegativeMargin", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerExtremeMarginPageEdgeOverflowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerExtremeMarginPageEdgeOverflow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerHugePaddingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerHugePadding", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerIdeographicSpaceAloneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerIdeographicSpaceAlone", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerMultiPageBackgroundColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageBackgroundColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerMultiPageBackgroundColorDecorationCloneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageBackgroundColorDecorationClone", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerMultiPageBackgroundColorDecorationSliceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageBackgroundColorDecorationSlice", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerMultiPageBorderColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageBorderColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-4384 box-shadow is not supported
    public void vertCornerMultiPageBoxShadowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageBoxShadow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerMultiPageBreakInsideAvoidTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageBreakInsideAvoid", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.WIDOWS_CONSTRAINT_VIOLATED)})
    public void vertCornerMultiPageOrphansWidowsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageOrphansWidows", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerMultiPageOutlineTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageOutline", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerMultiPageUnbreakableWordWithBackgroundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerMultiPageUnbreakableWordWithBackground", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerNearInvisibleOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerNearInvisibleOpacity", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerPerfectFitSingleCharacterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerPerfectFitSingleCharacter", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate =
            Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2)
    })
    public void vertCornerRootElementVerticalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerRootElementVertical", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerSingleCharacterHugeLetterSpacingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerSingleCharacterHugeLetterSpacing", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerSingleSpaceCharacterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerSingleSpaceCharacter", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerTinyFontSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerTinyFontSize", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    public void vertCornerTinyLineHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerTinyLineHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerUnusualAbsoluteUnitsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerUnusualAbsoluteUnits", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertCornerZeroFontSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertCornerZeroFontSize", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

}
