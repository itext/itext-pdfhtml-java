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
package com.itextpdf.html2pdf;

import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfUAConformance;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.pdfua.PdfUAConfig;
import com.itextpdf.pdfua.PdfUADocument;
import com.itextpdf.pdfua.exceptions.PdfUAConformanceException;
import com.itextpdf.pdfua.exceptions.PdfUAExceptionMessageConstants;
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
public class HtmlConverterPdfUA1UA2Test extends ExtendedITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterPdfUA1UA2Test/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterPdfUA1UA2Test/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-8679 Apply links alternate description according to UA standard
    public void simpleLinkTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "simpleLink.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_simpleLinkUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_simpleLinkUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "simpleLink.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "simpleLink.pdf";

        String expectedUa1Message = MessageFormatUtil.format(
                PdfUAExceptionMessageConstants.ANNOTATION_OF_TYPE_0_SHOULD_HAVE_CONTENTS_OR_ALT_KEY,
                PdfName.Link.getValue());

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, false, expectedUa1Message);
        // Expected valid UA-2 document because PDF/UA-2 does not require Contents in Link annotations
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    // TODO DEVSIX-8679 Apply links alternate description according to UA standard
    public void backwardLinkTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "backwardLink.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_backwardLinkUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_backwardLinkUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "backwardLinkUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "backwardLinkUa2.pdf";

        String expectedUa1Message = MessageFormatUtil.format(
                PdfUAExceptionMessageConstants.ANNOTATION_OF_TYPE_0_SHOULD_HAVE_CONTENTS_OR_ALT_KEY,
                PdfName.Link.getValue());

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, false, expectedUa1Message);
        // Expected valid UA-2 document because PDF/UA-2 does not require Contents in Link annotations
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    // TODO DEVSIX-8679 Apply links alternate description according to UA standard
    public void imageLinkTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "imageLink.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_imageLinkUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_imageLinkUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "imageLinkUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "imageLinkUa2.pdf";

        String expectedUa1Message = MessageFormatUtil.format(
                PdfUAExceptionMessageConstants.ANNOTATION_OF_TYPE_0_SHOULD_HAVE_CONTENTS_OR_ALT_KEY,
                PdfName.Link.getValue());

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, false, expectedUa1Message);
        // Expected valid UA-2 document because PDF/UA-2 does not require Contents in Link annotations
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    // TODO DEVSIX-8476 PDF 2.0 doesn't allow P tag be a child of H tag
    public void simpleOutlineTest() throws IOException, InterruptedException, XMPException {
        String sourceHtmlUa1 = SOURCE_FOLDER + "simpleOutlineUa1.html";
        String sourceHtmlUa2 = SOURCE_FOLDER + "simpleOutlineUa2.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_simpleOutlineUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_simpleOutlineUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "simpleOutlineUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "simpleOutlineUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtmlUa1,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtmlUa2, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8706 Incorrect tagging structure when using one span with glyph that doesn't have a mapping in the font
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.ATTEMPT_TO_CREATE_A_TAG_FOR_FINISHED_HINT)})
    public void unsupportedGlyphTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "unsupportedGlyph.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_unsupportedGlyphUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_unsupportedGlyphUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "unsupportedGlyphUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "unsupportedGlyphUa2.pdf";

        String expectedUa1Message = MessageFormatUtil.format(
                PdfUAExceptionMessageConstants.GLYPH_IS_NOT_DEFINED_OR_WITHOUT_UNICODE, '中');

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, false, expectedUa1Message);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8862 PDF 2.0 does not allow DIV, P tags to be children of the P tag
    public void emptyElementsTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "emptyElements.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_emptyElementsUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_emptyElementsUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "emptyElementsUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "emptyElementsUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8862 PDF 2.0 does not allow DIV, P tags to be children of the P tag
    public void boxSizingInlineBlockTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "boxSizingInlineBlock.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_boxSizingInlineBlockUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_boxSizingInlineBlockUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "boxSizingInlineBlockUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "boxSizingInlineBlockUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8863 PDF 2.0 does not allow P, Hn tags to be children of the Form tag
    public void divInButtonTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "divInButton.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_divInButtonUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_divInButtonUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "divInButtonUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "divInButtonUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8863 PDF 2.0 does not allow P, Hn tags to be children of the Form tag
    // TODO DEVSIX-8476 PDF 2.0 doesn't allow P tag be a child of H tag
    public void headingInButtonTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "headingInButton.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_headingInButtonUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_headingInButtonUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "headingInButtonUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "headingInButtonUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8864 PDF 2.0: Destination in GoTo action is not a structure destination
    public void pageBreakAfterAvoidTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "pageBreakAfterAvoid.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_pageBreakAfterAvoidUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_pageBreakAfterAvoidUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "pageBreakAfterAvoidUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "pageBreakAfterAvoidUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8864 PDF 2.0: Destination in GoTo action is not a structure destination
    // TODO DEVSIX-8476 PDF 2.0 doesn't allow P tag be a child of H tag
    // TODO DEVSIX-8679 Apply links alternate description according to UA standard
    public void linkWithPageBreakBeforeTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "linkWithPageBreakBefore.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_linkWithPageBreakBeforeUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_linkWithPageBreakBeforeUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "linkWithPageBreakBeforeUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "linkWithPageBreakBeforeUa2.pdf";

        String expectedUa1Message = MessageFormatUtil.format(
                PdfUAExceptionMessageConstants.ANNOTATION_OF_TYPE_0_SHOULD_HAVE_CONTENTS_OR_ALT_KEY,
                PdfName.Link.getValue());

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, false, expectedUa1Message);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8865 PDF document does not contain Document tag if it does not contain any content
    public void emptyHtmlTest() throws IOException, InterruptedException, XMPException {
        String sourceHtml = SOURCE_FOLDER + "emptyHtml.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_emptyHtmlUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_emptyHtmlUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "emptyHtmlUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "emptyHtmlUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
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

    private void convertToUa1AndCheckCompliance(String sourceHtml, String destinationPdf, String cmpPdf,
            boolean isExpectedOk, String expectedErrorMessage) throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfUADocument(new PdfWriter(destinationPdf),
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "simple doc", "eng"));

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setBaseUri(SOURCE_FOLDER);
        converterProperties.setOutlineHandler(OutlineHandler.createStandardHandler());

        if (expectedErrorMessage != null) {
            Exception e = Assertions.assertThrows(PdfUAConformanceException.class,
                    () -> HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument,
                            converterProperties));
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        } else {
            HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);
            compareAndCheckCompliance(destinationPdf, cmpPdf, isExpectedOk);
        }
    }

    private void convertToUa2AndCheckCompliance(String sourceHtml, String destinationPdf, String cmpPdf,
            boolean isExpectedOk) throws IOException, XMPException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf, new WriterProperties().setPdfVersion(
                PdfVersion.PDF_2_0)));
        createSimplePdfUA2Document(pdfDocument);

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setBaseUri(SOURCE_FOLDER);
        converterProperties.setOutlineHandler(OutlineHandler.createStandardHandler());
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf, isExpectedOk);
    }
}
