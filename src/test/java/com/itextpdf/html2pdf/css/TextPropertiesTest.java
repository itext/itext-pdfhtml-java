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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class TextPropertiesTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/TextPropertiesTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/TextPropertiesTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void textAlignTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignJustifyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignJustifyTest", sourceFolder, destinationFolder);
    }

    @Test
    public void justifiedTextWithCharSpacingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("justifiedTextWithCharSpacingTest", sourceFolder, destinationFolder);
    }

    @Test
    public void justifiedTextWithCharAndWordSpacingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("justifiedTextWithCharAndWordSpacingTest", sourceFolder, destinationFolder);
    }

    @Test
    public void justifiedTextWithCharPositiveAndWordSpacingNegativeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare(
                "justifiedTextWithCharPositiveAndWordSpacingNegativeTest",sourceFolder, destinationFolder);
    }

    @Test
    public void justifiedTextWithCharAndWordSpacingNegativeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare(
                "justifiedTextWithCharAndWordSpacingNegativeTest", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.TEXT_DECORATION_BLINK_NOT_SUPPORTED)})
    public void textDecorationTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest01", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void letterSpacingTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("letterSpacingTest01", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void letterSpacingWithInvalidValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("letterSpacingWithInvalidValues", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void wordSpacingTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("wordSpacingTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void lineHeightTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("lineHeightTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void lineHeightTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("lineHeightTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void lineHeightTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("lineHeightTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void lineHeightInHyperlinkTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("lineHeightInHyperlink", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void textTransformTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("textTransformTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void textTransform02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textTransformTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceTest03", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH))
    //TODO: fix after DEVSIX-2447. To reproduce without error, remove "white-space: pre;" (pre-wrap, pre-line)
    public void checkWhiteSpaceCss() throws IOException, InterruptedException {
        convertToPdfAndCompare("checkWhiteSpaceCss", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapBasicTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapBasicTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapBackgroundTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapBackgroundTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapShortTextTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapShortTextTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapShortTextTest02() throws IOException, InterruptedException {
        // TODO DEVSIX-2443: /r itext doesn't collapse and treats as new-line
        convertToPdfAndCompare("whiteSpaceNowrapShortTextTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapLongTextTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapLongTextTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapLongTextTest02() throws IOException, InterruptedException {
        // TODO DEVSIX-2443: /r itext doesn't collapse and treats as new-line
        convertToPdfAndCompare("whiteSpaceNowrapLongTextTest02", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH))
    public void whiteSpaceNowrapTableCellTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTableCellTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapTableCellTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTableCellTest02", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH))
    public void whiteSpaceNowrapTableCellTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTableCellTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapTextAlignTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTextAlignTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapTextAlignTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTextAlignTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapTextAlignTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTextAlignTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapTextAlignTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTextAlignTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapImageTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapImageTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapImageTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapImageTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapImageTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapImageTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapImageTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapImageTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapInlineBlockTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapInlineBlockTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapInlineBlockTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapInlineBlockTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapInlineBlockTest03() throws IOException, InterruptedException {
        // TODO DEVSIX-4600 ignores nowrap on inline elements
        convertToPdfAndCompare("whiteSpaceNowrapInlineBlockTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapSequentialTest01() throws IOException, InterruptedException {
        // TODO DEVSIX-4600 ignores nowrap on inline elements
        convertToPdfAndCompare("whiteSpaceNowrapSequentialTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapSequentialTest02() throws IOException, InterruptedException {
        // TODO DEVSIX-4600 ignores nowrap on inline elements
        convertToPdfAndCompare("whiteSpaceNowrapSequentialTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapSequentialTest03() throws IOException, InterruptedException {
        // TODO DEVSIX-4600 ignores nowrap on inline elements
        convertToPdfAndCompare("whiteSpaceNowrapSequentialTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapSequentialTest04() throws IOException, InterruptedException {
        // TODO DEVSIX-4600 ignores nowrap on inline elements
        convertToPdfAndCompare("whiteSpaceNowrapSequentialTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapSequentialTest05() throws IOException, InterruptedException {
        // TODO DEVSIX-4600 ignores nowrap on inline elements
        convertToPdfAndCompare("whiteSpaceNowrapSequentialTest05", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapSequentialTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapSequentialTest06", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapNestedTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapNestedTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest05", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest06", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest07() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest07", sourceFolder, destinationFolder);
    }

    @Test
    public void enspEmspThinspTest08() throws IOException, InterruptedException {
        // TODO DEVSIX-1442
        convertToPdfAndCompare("enspEmspThinspTest08", sourceFolder, destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-1442")
    public void enspEmspThinspTest09() throws IOException, InterruptedException {
        convertToPdfAndCompare("enspEmspThinspTest09", sourceFolder, destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-1851")
    public void wordCharSpacingJustifiedTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("wordCharSpacingJustifiedTest01", sourceFolder, destinationFolder);
    }
}
