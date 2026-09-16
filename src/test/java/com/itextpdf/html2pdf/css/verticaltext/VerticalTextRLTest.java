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
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextRLTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/verticaltext/VerticalTextRLTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/verticaltext/VerticalTextRLTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-10200 Consider text elements with different writing-mode as inline-blocks
    public void vertRlBasicTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlBasic", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlPageSplitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlPageSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void directionRtlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("directionRtl", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void innerTextVerticalRlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("innerTextVerticalRl", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void severalInnerTextVerticalRlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("severalInnerTextVerticalRl", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED)
    })
    public void vertRlAbsolutePositioningTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlAbsolutePositioning", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlBackgroundDecorationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlBackgroundDecoration", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)
    })
    public void vertRlBlockquoteTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlBlockquote", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlCjkTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlCjkText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlComboComplexTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlComboComplex", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)
    })
    public void vertRlComboFlexMixedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlComboFlexMixed", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlComboSpacingDecorationOverflowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlComboSpacingDecorationOverflow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlComboWideDecoratedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlComboWideDecorated", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlCssColumnsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlCssColumns", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlFlexMinMaxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlFlexMinMax", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.UNSUPPORTED_PROPERTY,
                    count = 2)
    })
    // TODO DEVSIX-10168 Improve min-max width calculations for vertical text
    public void vertRlFloatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlFloat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)
    })
    public void vertRlHeadingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlHeadings", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT,
                    count = 2)
    })
    public void vertRlImageInlineBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlImageInlineBlock", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlLetterSpacingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlLetterSpacing", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlLineHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlLineHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT,
                    count = 2)
    })
    public void vertRlListsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlLists", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlLongContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlLongContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlLongContainerWithWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlLongContainerWithWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlLongTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlLongText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlMixedFontsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlMixedFonts", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlNoSoftWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlNoSoftWrap", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlOverflowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlOverflow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlSpacingRatioTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlSpacingRatio", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)
    })
    public void vertRlTableCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlTableCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // Start and end text-align is not supported, justify is a bit different.
    public void vertRlTextAlignTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlTextAlign", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlTextCombineUprightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlTextCombineUpright", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlTextDecorationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlTextDecoration", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlUnderlinePositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlUnderlinePosition", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlVerticalAlignTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlVerticalAlign", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void nestedSpansVerticalAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedSpansVerticalAlignment", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlWideMulticolumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlWideMulticolumn", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertRlWordSpacingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlWordSpacing", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA),
            @LogMessage(messageTemplate = IoLogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE)
    })
    public void vertRlZeroNegativeDimensionsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertRlZeroNegativeDimensions", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalLrPlusRTLDirectionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalLrPlusRTLDirection", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
