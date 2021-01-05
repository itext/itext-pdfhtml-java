/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.LogMessageConstant;
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
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.TEXT_DECORATION_BLINK_NOT_SUPPORTED)})
    public void textDecorationTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest01", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void letterSpacingTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("letterSpacingTest01", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void letterSpacingWithInvalidValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("letterSpacingWithInvalidValues", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
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
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH))
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
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH))
    public void whiteSpaceNowrapTableCellTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTableCellTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void whiteSpaceNowrapTableCellTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("whiteSpaceNowrapTableCellTest02", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH))
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
