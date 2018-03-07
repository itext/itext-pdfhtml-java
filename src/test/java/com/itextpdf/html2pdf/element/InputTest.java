/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
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
public class InputTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/InputTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/InputTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void input01Test() throws IOException, InterruptedException {
        runTest("inputTest01");
    }

    @Test
    public void input02Test() throws IOException, InterruptedException {
        runTest("inputTest02");
    }

    @Test
    public void input03Test() throws IOException, InterruptedException {
        runTest("inputTest03");
    }

    @Test
    public void input04Test() throws IOException, InterruptedException {
        runTest("inputTest04");
    }

    @Test
    public void input05Test() throws IOException, InterruptedException {
        runTest("inputTest05");
    }

    @Test
    @LogMessages(ignore = true, messages = {
            @LogMessage(messageTemplate = LogMessageConstant.INPUT_FIELD_DOES_NOT_FIT, count = 4),
    })
    public void input06Test() throws IOException, InterruptedException {
        String htmlPath = sourceFolder + "inputTest06.html";
        String outPdfPath = destinationFolder + "inputTest06.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + "inputTest06.pdf";
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlPath).getPath() + "\n");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outPdfPath));
        pdfDoc.setDefaultPageSize(PageSize.A8);
        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), pdfDoc, new ConverterProperties().setCreateAcroForm(true));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, "diff_inputTest06_"));
    }

    @Test
    public void input07Test() throws IOException, InterruptedException {
        // TODO DEVSIX-1777: if not explicitly specified, <input> border default value should be different from the one
        // specified in user agent css. Also user agent css should not specify default color
        // and should use 'initial' instead.
        runTest("inputTest07");
    }

    @Test
    public void input08Test() throws IOException, InterruptedException {
        runTest("inputTest08");
    }

    @Test
    public void input09Test() throws IOException, InterruptedException {
        runTest("inputTest09");
    }

    @Test
    public void input10Test() throws IOException, InterruptedException {
        runTest("inputTest10");
    }

    @Test
    public void textareaRowsHeightTest() throws IOException, InterruptedException {
        runTest("textareaRowsHeight");
    }

    @Test
    public void blockHeightTest() throws IOException, InterruptedException {
        runTest("blockHeightTest");
    }

    @Test
    public void smallPercentWidthTest() throws IOException, InterruptedException {
        runTest("smallPercentWidth");
    }

    @Test
    public void button01Test() throws IOException, InterruptedException {
        runTest("buttonTest01");
    }

    @Test
    public void button02Test() throws IOException, InterruptedException {
        runTest("buttonTest02");
    }

    @Test
    public void buttonWithDisplayBlockTest() throws IOException, InterruptedException {
        runTest("buttonWithDisplayBlock");
    }

    private void runTest(String name) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String outPdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diff = "diff_" + name + "_";
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlPath).getPath() + "\n");

        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdfPath));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff));
    }
}
