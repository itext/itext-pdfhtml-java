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
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.pdfa.PdfAConformanceLogMessageConstant;
import com.itextpdf.pdfa.PdfADocument;
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
import java.io.InputStream;

@Category(IntegrationTest.class)
public class LinkTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/LinkTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/LinkTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void linkTest01() throws IOException, InterruptedException {
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "linkTest01.html").getPath() + "\n");
        PdfDocument outDoc = new PdfDocument(new PdfWriter(destinationFolder + "linkTest01.pdf"));
        outDoc.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest01.html")) {
            HtmlConverter.convertToPdf(fileInputStream, outDoc);
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest01.pdf", sourceFolder + "cmp_linkTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void linkTest02() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest02.html"), new File(destinationFolder + "linkTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest02.pdf", sourceFolder + "cmp_linkTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void linkTest03() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest03.html"), new File(destinationFolder + "linkTest03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest03.pdf", sourceFolder + "cmp_linkTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void linkTest04() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest04.html"), new File(destinationFolder + "linkTest04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest04.pdf", sourceFolder + "cmp_linkTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void linkTest05() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest05.html"), new File(destinationFolder + "linkTest05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest05.pdf", sourceFolder + "cmp_linkTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void linkTest06() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest06.html"), new File(destinationFolder + "linkTest06.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest06.pdf", sourceFolder + "cmp_linkTest06.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void linkTest07() throws IOException, InterruptedException {
        PdfDocument outDoc = new PdfDocument(new PdfWriter(destinationFolder + "linkTest07.pdf"));
        outDoc.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest07.html")) {
            HtmlConverter.convertToPdf(fileInputStream, outDoc);
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest07.pdf", sourceFolder + "cmp_linkTest07.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void linkTest09() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "linkTest09.pdf"));
        pdfDocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest09.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest09.pdf", sourceFolder + "cmp_linkTest09.pdf", destinationFolder, "diff09_"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = PdfAConformanceLogMessageConstant.CATALOG_SHOULD_CONTAIN_LANG_ENTRY)})
    public void linkTest10ToPdfa() throws IOException, InterruptedException {
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");
        PdfADocument pdfADocument = new PdfADocument(new PdfWriter(destinationFolder + "linkTest10.pdf"), PdfAConformanceLevel.PDF_A_2A, new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is));
        pdfADocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest10.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfADocument);
        }

        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest10.pdf", sourceFolder + "cmp_linkTest10.pdf", destinationFolder, "diff10_"));
    }

    @Test
    public void linkTest11() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "linkTest11.pdf"));
        pdfDocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest11.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest11.pdf", sourceFolder + "cmp_linkTest11.pdf", destinationFolder, "diff11_"));
    }

    @Test
    public void linkTest12() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "linkTest12.pdf"));
        pdfDocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest12.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setBaseUri("https://en.wikipedia.org/wiki/"));
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest12.pdf", sourceFolder + "cmp_linkTest12.pdf", destinationFolder, "diff12_"));
    }
}
