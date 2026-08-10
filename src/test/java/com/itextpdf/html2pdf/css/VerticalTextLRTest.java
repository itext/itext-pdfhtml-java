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
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextLRTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalTextLRTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/VerticalTextLRTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10183 fixed width ignored
    public void vertLrAbsolutePositioningTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrAbsolutePositioning", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 Border and background sizing in flex container
    public void vertLrBackgroundDecorationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrBackgroundDecoration", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertLrBasicTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrBasic", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // Difference with browser due to tag "cite" not being supported
    public void vertLrBlockquoteTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrBlockquote", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 flex box borders
    public void vertLrCjkTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrCjkText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 paragraph positioning in flex container
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    public void vertLrComboComplexTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrComboComplex", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 horizontal positioning of paragraph content is wrong
    public void vertLrComboFlexMixedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrComboFlexMixed", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    public void vertLrComboSpacingDecorationOverflowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrComboSpacingDecorationOverflow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10180 Strike-through positioning is off.
    public void vertLrComboWideDecoratedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrComboWideDecorated", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 flex box borders div positioning
    public void vertLrCssColumnsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrCssColumns", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 Occupied area for flex container is incorrect.
    public void vertLrFlexMinMaxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrFlexMinMax", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // Floating paragraph positioning and sizing is not supported with vertical writing.
    public void vertLrFloatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrFloat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 header border in flex container too narrow
    public void vertLrHeadingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrHeadings", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10188 inline svg color is not working
    public void vertLrImageInlineBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrImageInlineBlock", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 last paragraph too small in flex container
    @LogMessages(
            messages = {@LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT, count = 4)}
    )
    public void vertLrLetterSpacingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrLetterSpacing", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 flex borders misaligned
    @LogMessages(
            messages = {@LogMessage(messageTemplate = IoLogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE),
                    @LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT, count = 8)}
    )
    public void vertLrLineHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrLineHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10186 Lists with vertical writing.
    @LogMessages(
            messages = {@LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT, count = 4),
                    @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED)}
    )
    public void vertLrListsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrLists", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 When height > Page length run over pages before going to next line
    public void vertLrLongContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrLongContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 borders incorrect when page overflows
    public void vertLrLongTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrLongText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // Symbol height calculations differ slightly depending on the font in use.
    public void vertLrMixedFontsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrMixedFonts", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 paragraph positioning in flex container + border sizing
    public void vertLrNoSoftWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrNoSoftWrap", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 paragraph positioning in flex container
    @LogMessages(
            messages = {@LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT, count = 9),
                    @LogMessage(messageTemplate = IoLogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE, count = 3)}
    )
    public void vertLrOverflowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrOverflow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertLrSpacingRatioTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrSpacingRatio", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10183 Cells vertically oversized
    public void vertLrTableCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrTableCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    //TODO DEVSIX-10168 paragraph border in flex container too narrow
    public void vertLrTextAlignTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrTextAlign", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10167 text-combine-upright all not supported
    //TODO DEVSIX-10168 Border sizing and positioning in flex container
    public void vertLrTextCombineUprightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrTextCombineUpright", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    //TODO DEVSIX-10168 paragraph border in flex container too narrow
    public void vertLrTextDecorationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrTextDecoration", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 paragraph border in flex container too narrow
    public void vertLrUnderlinePositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrUnderlinePosition", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    public void vertLrVerticalAlignTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrVerticalAlign", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertLrWideMulticolumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrWideMulticolumn", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 last paragraph too small in flex container
    public void vertLrWordSpacingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrWordSpacing", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(
            messages = {@LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT, logLevel = LogLevelConstants.WARN, count = 9),
            @LogMessage(messageTemplate = LayoutLogMessageConstant.FLEX_ITEM_LAYOUT_RESULT_IS_NOT_FULL, logLevel = LogLevelConstants.ERROR, count = 1)}
    )
    // Vertical text with extreme values doesn't work normal, but it doesn't throw or results in infinite loop, which is
    // good enough already.
    public void vertLrZeroNegativeDimensionsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertLrZeroNegativeDimensions", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(
            messages = {@LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT, count = 7)}
    )
    // TODO DEVSIX-10176 Text-orientation sideways and mixed are not supported.
    public void mixedUprightSidewaysTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("mixedUprightSideways", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void occupiedAreaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("occupiedArea", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    //TODO DEVSIX-10180 Support text rise in html mode for vertical text
    public void inlineBlockAndTextRiseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("inline_block_and_text_rise", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
