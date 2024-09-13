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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfAConformance;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.pdfa.checker.PdfA4Checker;
import com.itextpdf.pdfa.exceptions.PdfAConformanceException;
import com.itextpdf.pdfa.exceptions.PdfaExceptionMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.itextpdf.html2pdf.HtmlConverterTest.compareAndCheckCompliance;

@Tag("IntegrationTest")
public class HtmlConverterPdfA4Test extends ExtendedITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterPdfA4Test/";
    public static final String RESOURCES_SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/fonts/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterPdfA4Test/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertToPdfA4SimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simpleA4.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simpleA4.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        FileOutputStream fOutputDest = new FileOutputStream(destinationPdf);

        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, fOutputDest, converterProperties);
        }

        fOutputDest.close();
        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    //TODO DEVSIX-4201 adapt test when property is added
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2,
                    logLevel = LogLevelConstants.WARN)
    })
    public void convertToPdfA4ColorsTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "colors.html";
        String destinationPdf = DESTINATION_FOLDER + "pdfA4ColorTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA4ColorTest.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new FileOutputStream(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA4MetaDataInvalidTest() throws IOException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String destinationPdf = DESTINATION_FOLDER + "pdfA4InvalidMetadataTest.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        converterProperties.setBaseUri("no_link");

        try (FileInputStream fOutput = new FileInputStream(sourceHtml);
                PdfWriter pdfWriter = new PdfWriter(destinationPdf);
        ) {
            Exception e = Assertions.assertThrows(PdfAConformanceException.class, () -> {
                HtmlConverter.convertToPdf(fOutput, pdfWriter, converterProperties);
            });

            Assertions.assertEquals(MessageFormatUtil.format(
                            PdfaExceptionMessageConstant.THE_FILE_HEADER_SHALL_CONTAIN_RIGHT_PDF_VERSION, "2"),
                    e.getMessage());
        }
    }

    @Test
    public void convertToPdfA4ArabicFontTest() throws IOException, InterruptedException {
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA4ArabicFontTest.pdf";
        String destinationPdf = DESTINATION_FOLDER + "pdfA4ArabicFontTest.pdf";
        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body >\n" +
                "<p>أميرة</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(RESOURCES_SOURCE_FOLDER + "NotoNaskhArabic-Regular.ttf");
        converterProperties.setFontProvider(fontProvider);
        FileOutputStream fOutput = new FileOutputStream(destinationPdf);

        HtmlConverter.convertToPdf(html, fOutput, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA4UnreferencedGlyphsTest() throws IOException {
        String destinationPdf = DESTINATION_FOLDER + "pdfA4UnrefFontTest.pdf";
        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body >\n" +
                "<p>أميرة</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(RESOURCES_SOURCE_FOLDER + "NotoSans-Regular.ttf");
        converterProperties.setFontProvider(fontProvider);
        try (FileOutputStream fOutput = new FileOutputStream(destinationPdf)) {

            Exception e = Assertions.assertThrows(PdfAConformanceException.class, () -> {
                HtmlConverter.convertToPdf(html, fOutput, converterProperties);
            });

            Assertions.assertEquals(MessageFormatUtil.format(
                            PdfaExceptionMessageConstant.EMBEDDED_FONTS_SHALL_DEFINE_ALL_REFERENCED_GLYPHS),
                    e.getMessage());
        }
    }

    @Test
    public void convertToPdfA4UnreferencedEmojiTest() throws IOException {
        String destinationPdf = DESTINATION_FOLDER + "pdfA4UnrefEmojiTest.pdf";
        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body >\n" +
                "<p>\uD83D\uDE09</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(RESOURCES_SOURCE_FOLDER + "NotoSans-Regular.ttf");
        converterProperties.setFontProvider(fontProvider);

        try (FileOutputStream fOutput = new FileOutputStream(destinationPdf)) {
            Exception e = Assertions.assertThrows(PdfAConformanceException.class, () -> {
                HtmlConverter.convertToPdf(html, fOutput, converterProperties);
            });
            Assertions.assertEquals(MessageFormatUtil.format(
                            PdfaExceptionMessageConstant.EMBEDDED_FONTS_SHALL_DEFINE_ALL_REFERENCED_GLYPHS),
                    e.getMessage());
        }
    }

    @Test
    public void convertToPdfA4EmojiTest() throws IOException, InterruptedException {
        String destinationPdf = DESTINATION_FOLDER + "pdfA4EmojiTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA4EmojiTest.pdf";

        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body>\n" +
                "<p>\uD83D\uDE09</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(RESOURCES_SOURCE_FOLDER + "NotoEmoji-Regular.ttf");
        converterProperties.setFontProvider(fontProvider);

        FileOutputStream fOutput = new FileOutputStream(destinationPdf);

        HtmlConverter.convertToPdf(html, fOutput, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfAWithProvidingPdADocumentInstanceTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_doc.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_doc.pdf";

        PdfWriter writer = new PdfWriter(destinationPdf,
                new WriterProperties()
                        .setPdfVersion(PdfVersion.PDF_2_0));
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformance.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfAWithoutIcmProfileTest() throws IOException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String destinationPdf = DESTINATION_FOLDER + "simple_without_icm.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);

        try (FileInputStream fOutputHtml = new FileInputStream(sourceHtml);
                FileOutputStream fOutputDest = new FileOutputStream(destinationPdf);
        ) {
            Exception e = Assertions.assertThrows(PdfAConformanceException.class, () ->
                    HtmlConverter.convertToPdf(fOutputHtml, fOutputDest, converterProperties));

            Assertions.assertEquals(MessageFormatUtil.format(
                            PdfaExceptionMessageConstant.DEVICEGRAY_SHALL_ONLY_BE_USED_IF_CURRENT_PDFA_OUTPUT_INTENT_OR_DEFAULTGRAY_IN_USAGE_CONTEXT),
                    e.getMessage());
        }

    }

    @Test
    public void convertToPdfAWithProvidingPdADocumentAndCustomFontProviderTest()
            throws IOException, InterruptedException {
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
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformance.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, properties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfAWithProvidingPdADocumentInstanceWithDefinedConformanceTest()
            throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_doc.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_doc.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        PdfWriter writer = new PdfWriter(destinationPdf,
                new WriterProperties()
                        .setPdfVersion(PdfVersion.PDF_2_0));
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformance.PDF_A_4E,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));

        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProperties);
        }

        try (PdfADocument pdfADocument = new PdfADocument(new PdfReader(destinationPdf),
                new PdfWriter(new ByteArrayOutputStream()))) {
            new PdfA4Checker(PdfAConformance.PDF_A_4E).checkDocument(pdfADocument.getCatalog());
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA4LinearGradientTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "gradient.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA4LinGradient.pdf";
        String destinationPdf = DESTINATION_FOLDER + "pdfA4LinGradient.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        converterProperties.setDocumentOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new FileOutputStream(destinationPdf), converterProperties);
        }
        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }
}
