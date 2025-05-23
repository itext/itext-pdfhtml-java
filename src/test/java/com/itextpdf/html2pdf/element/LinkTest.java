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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfAConformance;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.pdfa.logs.PdfAConformanceLogMessageConstant; // Android-Conversion-Skip-Line (TODO DEVSIX-7372 investigate why a few tests related to PdfA in iTextCore and PdfHtml were cut)
import com.itextpdf.pdfa.PdfADocument; // Android-Conversion-Skip-Line (TODO DEVSIX-7372 investigate why a few tests related to PdfA in iTextCore and PdfHtml were cut)
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Tag("IntegrationTest")
public class LinkTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/LinkTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/LinkTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void linkTest01() throws IOException, InterruptedException {
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(sourceFolder + "linkTest01.html") + "\n");
        PdfDocument outDoc = new PdfDocument(new PdfWriter(destinationFolder + "linkTest01.pdf"));
        outDoc.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest01.html")) {
            HtmlConverter.convertToPdf(fileInputStream, outDoc);
        }
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest01.pdf", sourceFolder + "cmp_linkTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void linkTest02() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest02.html"), new File(destinationFolder + "linkTest02.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest02.pdf", sourceFolder + "cmp_linkTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void linkTest03() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest03.html"), new File(destinationFolder + "linkTest03.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest03.pdf", sourceFolder + "cmp_linkTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void linkTest04() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest04.html"), new File(destinationFolder + "linkTest04.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest04.pdf", sourceFolder + "cmp_linkTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void linkTest05() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest05.html"), new File(destinationFolder + "linkTest05.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest05.pdf", sourceFolder + "cmp_linkTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void linkTest06() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "linkTest06.html"), new File(destinationFolder + "linkTest06.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest06.pdf", sourceFolder + "cmp_linkTest06.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void linkTest07() throws IOException, InterruptedException {
        PdfDocument outDoc = new PdfDocument(new PdfWriter(destinationFolder + "linkTest07.pdf"));
        outDoc.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest07.html")) {
            HtmlConverter.convertToPdf(fileInputStream, outDoc);
        }
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest07.pdf", sourceFolder + "cmp_linkTest07.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void linkTest08() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "linkTest08.pdf"));
        pdfDocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest08.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest08.pdf", sourceFolder + "cmp_linkTest08.pdf", destinationFolder, "diff08_"));
    }

    @Test
    public void linkTest09() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "linkTest09.pdf"));
        pdfDocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest09.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest09.pdf", sourceFolder + "cmp_linkTest09.pdf", destinationFolder, "diff09_"));
    }

    // Android-Conversion-Skip-Block-Start (TODO DEVSIX-7372 investigate why a few tests related to PdfA in iTextCore and PdfHtml were cut)
    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = PdfAConformanceLogMessageConstant.CATALOG_SHOULD_CONTAIN_LANG_ENTRY)})
    public void linkTest10ToPdfa() throws IOException, InterruptedException {
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");
        PdfADocument pdfADocument = new PdfADocument(new PdfWriter(destinationFolder + "linkTest10.pdf"), PdfAConformance.PDF_A_2A, new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is));
        pdfADocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest10.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfADocument);
        }

        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest10.pdf", sourceFolder + "cmp_linkTest10.pdf", destinationFolder, "diff10_"));
    }
    // Android-Conversion-Skip-Block-End

    @Test
    public void linkTest11() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "linkTest11.pdf"));
        pdfDocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest11.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest11.pdf", sourceFolder + "cmp_linkTest11.pdf", destinationFolder, "diff11_"));
    }

    @Test
    public void linkTest12() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "linkTest12.pdf"));
        pdfDocument.setTagged();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "linkTest12.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setBaseUri("https://en.wikipedia.org/wiki/"));
        }
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "linkTest12.pdf", sourceFolder + "cmp_linkTest12.pdf", destinationFolder, "diff12_"));
    }

    @Test
    public void anchorLinkToSpanTest01() throws IOException, InterruptedException {
        String fileName = "anchorLinkToSpanTest01";
        HtmlConverter.convertToPdf(new File(sourceFolder + fileName + ".html"), new File(destinationFolder + fileName + ".pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + fileName + ".pdf", sourceFolder + "cmp_" + fileName + ".pdf", destinationFolder));
    }

    @Test
    public void simpleLinkTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "simpleLink.pdf";
        String cmpPdf = sourceFolder + "cmp_simpleLink.pdf";

        HtmlConverter.convertToPdf(new File(sourceFolder + "simpleLink.html"), new File(outPdf));

        Assertions.assertNull(new CompareTool()
                .compareByContent(outPdf, cmpPdf, destinationFolder, "diff09_"));
    }
}
