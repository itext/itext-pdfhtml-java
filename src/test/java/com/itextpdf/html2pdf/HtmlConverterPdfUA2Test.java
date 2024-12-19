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

import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.pdfa.VeraPdfValidator;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class HtmlConverterPdfUA2Test extends ExtendedITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterPdfUA2Test/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterPdfUA2Test/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void simpleLinkTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "simpleLink.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_simpleLink.pdf";
        String destinationPdf = DESTINATION_FOLDER + "simpleLink.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf, new WriterProperties().setPdfVersion(
                PdfVersion.PDF_2_0)));
        createSimplePdfUA2Document(pdfDocument);

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf, true);
    }

    @Test
    public void backwardLinkTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "backwardLink.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_backwardLink.pdf";
        String destinationPdf = DESTINATION_FOLDER + "backwardLink.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf, new WriterProperties().setPdfVersion(
                PdfVersion.PDF_2_0)));
        createSimplePdfUA2Document(pdfDocument);

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf, true);
    }

    @Test
    public void imageLinkTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "imageLink.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_imageLink.pdf";
        String destinationPdf = DESTINATION_FOLDER + "imageLink.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf, new WriterProperties().setPdfVersion(
                PdfVersion.PDF_2_0)));
        createSimplePdfUA2Document(pdfDocument);

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setBaseUri(SOURCE_FOLDER);
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        // The VeraPDF check fails probably to its internal bug. It checks that /ActualText != null, but the
        // pdf/ua-2 documentation states the following:
        // 8.5.1 General
        // Real content that does not possess the semantics of text objects and does not have an alternate
        // textual representation shall be enclosed within Figure structure elements in accordance with
        // ISO 32000-2:2020, 14.8.4.8.5
        // So probably VeraPDF should've checked for /Alt instead of /ActualText
        compareAndCheckCompliance(destinationPdf, cmpPdf, false);
    }

    @Test
    public void simpleOutlineTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "simpleOutline.html";
        String destinationPdf = DESTINATION_FOLDER + "simpleOutline.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_simpleOutline.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf, new WriterProperties().setPdfVersion(
                PdfVersion.PDF_2_0)));
        createSimplePdfUA2Document(pdfDocument);

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setOutlineHandler(OutlineHandler.createStandardHandler());
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf, true);
    }

    @Test
    // TODO DEVSIX-8707 Handle html2pdf pdfua conversion handle missing glyphs
    // TODO DEVSIX-8706 Incorrect tagging structure when using one span with glyph that doesn't have a mapping in the font
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.ATTEMPT_TO_CREATE_A_TAG_FOR_FINISHED_HINT)})
    public void unsupportedGlyphTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "unsupportedGlyph.html";
        String destinationPdf = DESTINATION_FOLDER + "unsupportedGlyph.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_unsupportedGlyph.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf, new WriterProperties().setPdfVersion(
                PdfVersion.PDF_2_0)));
        createSimplePdfUA2Document(pdfDocument);

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setOutlineHandler(OutlineHandler.createStandardHandler());
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf, false);
    }

    private void createSimplePdfUA2Document(PdfDocument pdfDocument) throws IOException, XMPException {
        byte[] bytes = Files.readAllBytes(Paths.get(SOURCE_FOLDER + "simplePdfUA2.xmp"));
        XMPMeta xmpMeta = XMPMetaFactory.parse(new ByteArrayInputStream(bytes));
        pdfDocument.setXmpMetadata(xmpMeta);
        pdfDocument.setTagged();
        pdfDocument.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        pdfDocument.getCatalog().setLang(new PdfString("en-US"));
        PdfDocumentInfo info = pdfDocument.getDocumentInfo();
        info.setTitle("PdfUA2 Title");
    }

    private static void compareAndCheckCompliance(String destinationPdf, String cmpPdf, boolean isExpectedOk)
            throws IOException, InterruptedException {
        if (isExpectedOk) {
            Assertions.assertNull(new VeraPdfValidator().validate(destinationPdf));
        } else {
            new VeraPdfValidator().validateFailure(destinationPdf);
        }
        Assertions.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, DESTINATION_FOLDER,
                "diff_simple_"));
    }
}
