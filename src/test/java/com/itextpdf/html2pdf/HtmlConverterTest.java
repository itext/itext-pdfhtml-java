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
package com.itextpdf.html2pdf;

import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.pdfa.exceptions.PdfAConformanceException;
import com.itextpdf.pdfa.exceptions.PdfaExceptionMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.test.pdfa.VeraPdfValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(IntegrationTest.class)
public class HtmlConverterTest extends ExtendedITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterTest/";
    public static final String RESOURCES_SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/fonts/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterTest/";

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertToPdfA3USimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_3U);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA3USimpleFromStringTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_3U);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        HtmlConverter.convertToPdf("<html>\n" +
                "<head><title>Test</title></head>\n" +
                "<body >\n" +
                "<form>\n" +
                "    <p>Hello world!</p>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>", new PdfWriter(destinationPdf), converterProperties);

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA4LinearGradientTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "gradient.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA4LinGradient.pdf";
        String destinationPdf = DESTINATION_FOLDER + "pdfA4LinGradient.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_4);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new FileOutputStream(destinationPdf), converterProperties);
        }
        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA3ASimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_a.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_a.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_3U);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA3UWithCustomFontProviderTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_custom_font.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_custom_font.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_3U);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(RESOURCES_SOURCE_FOLDER + "NotoSans-Regular.ttf");
        converterProperties.setFontProvider(fontProvider);
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA4SimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simpleA4.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simpleA4.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_4);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new FileOutputStream(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfAWithProvidingPdADocumentInstanceTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_doc.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_doc.pdf";

        PdfWriter writer = new PdfWriter(destinationPdf,
                new WriterProperties()
                        .setPdfVersion(PdfVersion.PDF_2_0));
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument);
        }

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfAWithoutIcmProfileTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String destinationPdf = DESTINATION_FOLDER + "simple_without_icm.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_4);

        Exception e = Assert.assertThrows(PdfAConformanceException.class, () ->
                HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), new FileOutputStream(destinationPdf), converterProperties));
        Assert.assertEquals(MessageFormatUtil.format(PdfaExceptionMessageConstant.DEVICEGRAY_SHALL_ONLY_BE_USED_IF_CURRENT_PDFA_OUTPUT_INTENT_OR_DEFAULTGRAY_IN_USAGE_CONTEXT),
                e.getMessage());

    }

    @Test
    public void convertToPdfAWithProvidingPdADocumentAndCustomFontProviderTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_doc_custom_font.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_doc_custom_font.pdf";

        ConverterProperties properties = new ConverterProperties();
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(RESOURCES_SOURCE_FOLDER + "NotoSans-Regular.ttf");
        properties.setFontProvider(fontProvider);
        PdfWriter writer = new PdfWriter(destinationPdf,
                new WriterProperties()
                        .setPdfVersion(PdfVersion.PDF_2_0));
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, properties);
        }

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfAWithProvidingPdADocumentInstanceWithDefinedConformanceTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_doc.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_doc.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_4);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        PdfWriter writer = new PdfWriter(destinationPdf,
                new WriterProperties()
                        .setPdfVersion(PdfVersion.PDF_2_0));
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProperties);
        }

        compareAndCheckCompliance(sourceHtml, destinationPdf, cmpPdf);
    }

    @Test
    public void cannotConvertHtmlToDocumentInReadingModeTest() throws IOException {
        junitExpectedException.expect(Html2PdfException.class);
        junitExpectedException.expectMessage(Html2PdfException.PDF_DOCUMENT_SHOULD_BE_IN_WRITING_MODE);

        PdfDocument pdfDocument = createTempDoc();
        ConverterProperties properties = new ConverterProperties();
        Document document = HtmlConverter.convertToDocument("", pdfDocument, properties);
    }

    private static PdfDocument createTempDoc() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        pdfDocument.addNewPage();
        pdfDocument.close();
        pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(outputStream.toByteArray())));
        return pdfDocument;
    }

    private static void compareAndCheckCompliance(String sourceHtml, String destinationPdf, String cmpPdf) throws IOException, InterruptedException {
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(sourceHtml) + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, DESTINATION_FOLDER,
                "diff_simple_"));

        VeraPdfValidator veraPdfValidator = new VeraPdfValidator();
        Assert.assertNull(veraPdfValidator.validate(destinationPdf));
    }

}
