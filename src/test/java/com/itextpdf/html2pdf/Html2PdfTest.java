/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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
package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class Html2PdfTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/Html2PdfTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/Html2PdfTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void helloWorldParagraphTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_table", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloMalformedDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_malformed", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphJunkSpacesDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_junk_spaces", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphNestedInTableDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_nested_in_table", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphWithSpansDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_with_span", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloDivDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_div", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void aBlockInPTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("aBlockInPTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void base64svgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_base64svg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate =
                    StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI, count = 1),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 1)})
    public void htmlObjectIncorrectBase64Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_incorrectBase64svg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: update after DEVSIX-1346
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT,
                    count = 1),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2),
    })
    public void htmlObjectAltTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_altText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER,
                    count = 1),})
    public void htmlObjectNestedObjectTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_nestedTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void htmlImgBase64SVGTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgTag_base64svg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
