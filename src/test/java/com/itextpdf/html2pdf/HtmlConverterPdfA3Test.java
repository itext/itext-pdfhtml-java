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
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfAConformance;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.pdfa.exceptions.PdfAConformanceException;
import com.itextpdf.pdfa.exceptions.PdfaExceptionMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.itextpdf.html2pdf.HtmlConverterTest.compareAndCheckCompliance;

@Tag("IntegrationTest")
public class HtmlConverterPdfA3Test extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterPdfA3Test/";
    public static final String RESOURCES_SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/fonts/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterPdfA3Test/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertToPdfA3USimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA3USimpleFromStringTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        HtmlConverter.convertToPdf("<html>\n" +
                "<head><title>Test</title></head>\n" +
                "<body >\n" +
                "<form>\n" +
                "    <p>Hello world!</p>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>", new PdfWriter(destinationPdf), converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA3ASimpleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_a.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_a.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    //TODO DEVSIX-4201 adapt test when property is added
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2,
                    logLevel = LogLevelConstants.WARN)
    })
    public void convertToPdfA3ColorsTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "colors.html";
        String destinationPdf = DESTINATION_FOLDER + "pdfA3ColorTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA3ColorTest.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new FileOutputStream(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA3UWithCustomFontProviderTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simple.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simple_custom_font.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simple_custom_font.pdf";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
        converterProperties.setDocumentOutputIntent(
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        DefaultFontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addFont(RESOURCES_SOURCE_FOLDER + "NotoSans-Regular.ttf");
        converterProperties.setFontProvider(fontProvider);
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new PdfWriter(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    //TODO DEVSIX-7925 - Adapt cmp file after img filter is supported
    @Test
    public void convertToPdfA3TaggedTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "mixedContent.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA3TaggedTest.pdf";
        String destinationPdf = DESTINATION_FOLDER + "pdfA3TaggedTest.pdf";
        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);
        PdfADocument pdfADocument = new PdfADocument(new PdfWriter(destinationPdf), PdfAConformance.PDF_A_3U,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                        new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        pdfADocument.setTagged();
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfADocument, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }

    @Test
    public void convertToPdfA3UnreferencedGlyphsTest() throws IOException {
        String destinationPdf = DESTINATION_FOLDER + "pdfA3FontTest.pdf";
        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body >\n" +
                "<p>أميرة</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
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
    public void convertToPdfA3ArabicFontTest() throws IOException, InterruptedException {
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA3ArabicFontTest.pdf";
        String destinationPdf = DESTINATION_FOLDER + "pdfA3ArabicFontTest.pdf";
        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body >\n" +
                "<p>أميرة</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
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
    public void convertToPdfA3UnreferencedEmojiTest() throws IOException {
        String destinationPdf = DESTINATION_FOLDER + "pdfA3UnrefEmojiTest.pdf";
        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body >\n" +
                "<p>\uD83D\uDE09</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
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
    public void convertToPdfA3EmojiTest() throws IOException, InterruptedException {
        String destinationPdf = DESTINATION_FOLDER + "pdfA3EmojiTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_pdfA3EmojiTest.pdf";

        String html = "<html>\n" +
                "<head>"
                + "<title>Test</title></head>\n" +
                "<body>\n" +
                "<p>\uD83D\uDE09</p>" +
                "</body>\n" +
                "</html>";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_3U);
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
}
