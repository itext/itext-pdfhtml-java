/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Category(IntegrationTest.class)
public class SpanTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/SpanTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/SpanTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    private void testWithSuffix(String testIndex) throws IOException, InterruptedException {
        String htmlFile = sourceFolder + MessageFormatUtil.format("spanTest{0}.html", testIndex);
        String pdfFile = destinationFolder + MessageFormatUtil.format("spanTest{0}.pdf", testIndex);
        String cmpFile = sourceFolder + MessageFormatUtil.format("cmp_spanTest{0}.pdf", testIndex);
        String diff = MessageFormatUtil.format("diff{0}_", testIndex);
        HtmlConverter.convertToPdf(new File(htmlFile), new File(pdfFile));
        Assert.assertNull(new CompareTool().compareByContent(pdfFile, cmpFile, destinationFolder, diff));
    }

    private void test(String testName, boolean tagged) throws IOException, InterruptedException {
        String htmlFile = sourceFolder + testName + ".html";
        String pdfFile = destinationFolder + testName + ".pdf";
        String cmpFile = sourceFolder + MessageFormatUtil.format("cmp_{0}.pdf", testName);
        String diff = MessageFormatUtil.format("diff_{0}_", testName);
        if (tagged) {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
            pdfDocument.setTagged();
            try (FileInputStream fileInputStream = new FileInputStream(htmlFile)) {
                HtmlConverter.convertToPdf(fileInputStream, pdfDocument);
            }
        } else {
            HtmlConverter.convertToPdf(new File(htmlFile), new File(pdfFile));
        }
        Assert.assertNull(new CompareTool().compareByContent(pdfFile, cmpFile, destinationFolder, diff));
    }

    private void test(String testName) throws IOException, InterruptedException {
        test(testName, false);
    }

    @Test
    public void spanTest01() throws IOException, InterruptedException {
        testWithSuffix("01");
    }

    @Test
    public void spanTest02() throws IOException, InterruptedException {
        testWithSuffix("02");
    }

    @Test
    public void spanTest03() throws IOException, InterruptedException {
        testWithSuffix("03");
    }

    @Test
    public void spanTest04() throws IOException, InterruptedException {
        testWithSuffix("04");
    }

    @Test
    public void spanTest05() throws IOException, InterruptedException {
        testWithSuffix("05");
    }

    @Test
    public void spanTest06() throws IOException, InterruptedException {
        testWithSuffix("06");
    }

    @Test
    public void spanTest07() throws IOException, InterruptedException {
        testWithSuffix("07");
    }

    @Test
    // TODO DEVSIX-1438
    public void spanTest08() throws IOException, InterruptedException {
        testWithSuffix("08");
    }

    @Test
    public void spanTest09() throws IOException, InterruptedException {
        testWithSuffix("09");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES, logLevel = LogLevelConstants.WARN)
    })
    public void spanTest10() throws IOException, InterruptedException {
        testWithSuffix("10");
    }

    @Test
    public void spanTest11() throws IOException, InterruptedException {
        testWithSuffix("11");
    }

    @Test
    public void spanTest12() throws IOException, InterruptedException {
        testWithSuffix("12");
    }

    @Test
    public void spanTest13() throws IOException, InterruptedException {
        testWithSuffix("13");
    }

    @Test
    public void spanInsideSpanWithBackgroundTest() throws IOException, InterruptedException {
        test("spanInsideSpanWithBackground");
    }

    @Test
    public void spanWithLeftFloatInsideSpanWithBackgroundTest() throws IOException, InterruptedException {
        test("spanWithLeftFloatInsideSpanWithBackground");
    }

    @Test
    public void spanWithFloatsInsideSpanWithBackgroundAndFloatTest() throws IOException, InterruptedException {
        test("spanWithFloatsInsideSpanWithBackgroundAndFloat");
    }

    @Test
    public void commonNestedSpanTest() throws IOException, InterruptedException {
        test("commonNestedSpanTest");
    }

    // TODO: update cmp files during DEVSIX-2510
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES, count = 4, logLevel = LogLevelConstants.WARN)
    })
    public void spanTestNestedBlock() throws IOException, InterruptedException {
        test("spanTestNestedBlock");
    }

    // TODO: update cmp files during DEVSIX-2510
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES, count = 4, logLevel = LogLevelConstants.WARN)
    })
    public void spanTestNestedInlineBlock() throws IOException, InterruptedException {
        test("spanTestNestedInlineBlock");
    }

    @Test
    public void spanWithDisplayBlockInsideSpanParagraphTest() throws IOException, InterruptedException {
        test("spanWithDisplayBlockInsideSpanParagraphTest", true);
    }

    @Test
    public void spanWithBackgroundImageTest() throws IOException, InterruptedException {
        test("spanWithBackgroundImageTest");
    }
}
