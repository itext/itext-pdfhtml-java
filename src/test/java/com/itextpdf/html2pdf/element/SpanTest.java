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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Tag("IntegrationTest")
public class SpanTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/SpanTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/SpanTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    private void testWithSuffix(String testIndex) throws IOException, InterruptedException {
        String htmlFile = sourceFolder + MessageFormatUtil.format("spanTest{0}.html", testIndex);
        String pdfFile = destinationFolder + MessageFormatUtil.format("spanTest{0}.pdf", testIndex);
        String cmpFile = sourceFolder + MessageFormatUtil.format("cmp_spanTest{0}.pdf", testIndex);
        String diff = MessageFormatUtil.format("diff{0}_", testIndex);
        HtmlConverter.convertToPdf(new File(htmlFile), new File(pdfFile));
        Assertions.assertNull(new CompareTool().compareByContent(pdfFile, cmpFile, destinationFolder, diff));
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
        Assertions.assertNull(new CompareTool().compareByContent(pdfFile, cmpFile, destinationFolder, diff));
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
    //TODO DEVSIX-2485: Margins-applying currently doesn't work in html way for spans inside other spans.
    public void spanTest07() throws IOException, InterruptedException {
        testWithSuffix("07");
    }

    @Test
    public void spanTest08() throws IOException, InterruptedException {
        testWithSuffix("08");
    }

    @Test
    public void spanTest09() throws IOException, InterruptedException {
        testWithSuffix("09");
    }

    @Test
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
    public void spanTestNestedBlock() throws IOException, InterruptedException {
        test("spanTestNestedBlock");
    }

    // TODO: update cmp files during DEVSIX-2510
    @Test
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

    @Test
    //TODO DEVSIX-2485: Border-applying currently doesn't work in html way for spans inside other spans.
    public void spanBorderDottedInsideSolidSpanTest() throws IOException, InterruptedException {
        test("spanBorderDottedInsideSolidSpan");
    }

    @Test
    //TODO DEVSIX-2485: Border-applying currently doesn't work in html way for spans inside other spans.
    public void spanBorderNoneInsideDoubleSpanTest() throws IOException, InterruptedException {
        test("spanBorderNoneInsideDoubleSpan");
    }

    @Test
    //TODO DEVSIX-2485: Border-applying currently doesn't work in html way for spans inside other spans.
    public void spanBorderMixedInsideSolidSpanTest() throws IOException, InterruptedException {
        test("spanBorderMixedInsideSolidSpan");
    }


    @Test
    //TODO DEVSIX-2485: Margins-applying currently doesn't work in html way for spans inside other spans.
    public void spanMarginRightInsideSpanTest() throws IOException, InterruptedException {
        test("spanMarginRightInsideSpan");
    }

    @Test
    //TODO DEVSIX-2485: Margins-applying currently doesn't work in html way for spans inside other spans.
    public void spanMarginLeftInsideSpanTest() throws IOException, InterruptedException {
        test("spanMarginLeftInsideSpanTest");
    }

    @Test
    //TODO DEVSIX-2485: Margins-applying currently doesn't work in html way for spans inside other spans.
    public void spanMarginLeftInsideRightSpanTest() throws IOException, InterruptedException {
        test("spanMarginLeftInsideRightSpan");
    }
}
