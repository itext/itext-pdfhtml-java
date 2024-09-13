/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
package com.itextpdf.html2pdf;

import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.kernel.pdf.PdfAConformance;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.pdfa.exceptions.PdfAConformanceException;
import com.itextpdf.pdfa.exceptions.PdfaExceptionMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.pdfa.VeraPdfValidator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class HtmlConverterTest extends ExtendedITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertToPdfA2SimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_2B);
        converterProperties.setDocumentOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfASimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_1B);
        converterProperties.setDocumentOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void cannotConvertHtmlToDocumentInReadingModeTest() throws IOException {
        Exception exception = Assertions.assertThrows(Html2PdfException.class, () -> {
            PdfDocument pdfDocument = createTempDoc();
            ConverterProperties properties = new ConverterProperties();
            Document document = HtmlConverter.convertToDocument("", pdfDocument, properties);
        });
        Assertions.assertEquals(Html2PdfException.PDF_DOCUMENT_SHOULD_BE_IN_WRITING_MODE, exception.getMessage());

    }

    @Test
    public void convertHtmlToDocumentIncorrectConverterPropertiesTest() throws IOException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String destinationPdf = DESTINATION_FOLDER + "simpleA4.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
        converterProperties.setDocumentOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        PdfADocument pdfDocument = new PdfADocument(new PdfWriter(destinationPdf), PdfAConformance.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        Exception e = Assertions.assertThrows(PdfAConformanceException.class, () -> {
            HtmlConverter.convertToPdf(sourceHtml, pdfDocument, converterProperties);
        });

        Assertions.assertEquals(MessageFormatUtil.format(
                        PdfaExceptionMessageConstant.THE_FILE_HEADER_SHALL_CONTAIN_RIGHT_PDF_VERSION, "2"),
                e.getMessage());
    }

    @Test
    public void convertHtmlToDocumentWithDifferentColorProfileTest() throws IOException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String destinationPdf = DESTINATION_FOLDER + "simpleA4.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4E);
        converterProperties.setDocumentOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        PdfADocument pdfDocument = new PdfADocument(new PdfWriter(destinationPdf), PdfAConformance.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "USWebUncoated.icc")));

        Exception e = Assertions.assertThrows(PdfAConformanceException.class, () -> {
            HtmlConverter.convertToPdf(sourceHtml, pdfDocument, converterProperties);
        });

        Assertions.assertEquals(MessageFormatUtil.format(
                        PdfaExceptionMessageConstant.THE_FILE_HEADER_SHALL_CONTAIN_RIGHT_PDF_VERSION, "2"),
                e.getMessage());
    }

    private static PdfDocument createTempDoc() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        pdfDocument.addNewPage();
        pdfDocument.close();
        pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(outputStream.toByteArray())));
        return pdfDocument;
    }

    protected static void compareAndCheckCompliance(String destinationPdf, String cmpPdf) throws IOException, InterruptedException {
        Assertions.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, DESTINATION_FOLDER,
                "diff_simple_"));
        VeraPdfValidator veraPdfValidator = new VeraPdfValidator();
        Assertions.assertNull(veraPdfValidator.validate(destinationPdf));
    }

}
