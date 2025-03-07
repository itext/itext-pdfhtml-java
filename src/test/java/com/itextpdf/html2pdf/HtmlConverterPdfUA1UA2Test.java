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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfUAConformance;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.pdfua.PdfUAConfig;
import com.itextpdf.pdfua.PdfUADocument;
import com.itextpdf.pdfua.exceptions.PdfUAConformanceException;
import com.itextpdf.pdfua.exceptions.PdfUAExceptionMessageConstants;
import com.itextpdf.pdfua.logs.PdfUALogMessageConstants;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.pdfa.VeraPdfValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

@Tag("IntegrationTest")
public class HtmlConverterPdfUA1UA2Test extends ExtendedITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf" +
            "/HtmlConverterPdfUA1UA2Test/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterPdfUA1UA2Test/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void simpleLinkTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simpleLink.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_simpleLinkUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_simpleLinkUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "simpleLinkUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "simpleLinkUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void backwardLinkTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "backwardLink.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_backwardLinkUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_backwardLinkUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "backwardLinkUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "backwardLinkUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void imageLinkTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "imageLink.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_imageLinkUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_imageLinkUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "imageLinkUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "imageLinkUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void externalLinkTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "externalLink.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_externalLinkUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_externalLinkUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "externalLinkUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "externalLinkUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void simpleOutlineTest() throws IOException, InterruptedException {
        String sourceHtmlUa1 = SOURCE_FOLDER + "simpleOutlineUa1.html";
        String sourceHtmlUa2 = SOURCE_FOLDER + "simpleOutlineUa2.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_simpleOutlineUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_simpleOutlineUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "simpleOutlineUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "simpleOutlineUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtmlUa1, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtmlUa2, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void unsupportedGlyphTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "unsupportedGlyph.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_unsupportedGlyphUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_unsupportedGlyphUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "unsupportedGlyphUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "unsupportedGlyphUa2.pdf";

        String expectedUa1Message = MessageFormatUtil.format(
                PdfUAExceptionMessageConstants.GLYPH_IS_NOT_DEFINED_OR_WITHOUT_UNICODE, '中');

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, false, expectedUa1Message);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    public void emptyElementsTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "emptyElements.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_emptyElementsUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_emptyElementsUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "emptyElementsUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "emptyElementsUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void boxSizingInlineBlockTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "boxSizingInlineBlock.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_boxSizingInlineBlockUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_boxSizingInlineBlockUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "boxSizingInlineBlockUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "boxSizingInlineBlockUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void divInButtonTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "divInButton.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_divInButtonUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_divInButtonUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "divInButtonUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "divInButtonUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void headingInButtonTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "headingInButton.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_headingInButtonUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_headingInButtonUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "headingInButtonUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "headingInButtonUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    // TODO DEVSIX-8864 PDF 2.0: Destination in GoTo action is not a structure destination
    public void pageBreakAfterAvoidTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "pageBreakAfterAvoid.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_pageBreakAfterAvoidUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_pageBreakAfterAvoidUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "pageBreakAfterAvoidUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "pageBreakAfterAvoidUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8864 PDF 2.0: Destination in GoTo action is not a structure destination
    public void linkWithPageBreakBeforeTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "linkWithPageBreakBefore.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_linkWithPageBreakBeforeUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_linkWithPageBreakBeforeUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "linkWithPageBreakBeforeUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "linkWithPageBreakBeforeUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
   public void emptyHtmlTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "emptyHtml.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_emptyHtmlUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_emptyHtmlUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "emptyHtmlUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "emptyHtmlUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml,destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void inputWithTitleTagTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "inputWithTitleTag.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_inputWithTitleTagUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_inputWithTitleTagUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "inputWithTitleTagUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "inputWithTitleTagUa2.pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setCreateAcroForm(true);

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, converterProperties, true,
                null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, converterProperties, true);
    }

    @Test
    // TODO DEVSIX-8883 content is not tagged as real content or tagged as artifact after conversion
    public void svgBase64Test() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "svgBase64.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_svgBase64Ua1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_svgBase64Ua2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "svgBase64Ua1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "svgBase64Ua2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, false, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, false);
    }

    @Test
    // TODO DEVSIX-8883 content is not tagged as real content or tagged as artifact after conversion
    public void pngInDivStyleTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "pngInDivStyle.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_pngInDivStyleUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_pngInDivStyleUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "pngInDivStyleUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "pngInDivStyleUa2.pdf";

        // Investigate why VeraPdf doesn't complain about the missing tag.
        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }


    @Test
    public void svgAlternativeDescription() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "svgSimpleAlternateDescription.html";
        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_svgSimpleAlternateDescriptionUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_svgSimpleAlternateDescriptionUa2.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "svgSimpleAlternateDescriptionUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "svgSimpleAlternateDescriptionUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = PdfUALogMessageConstants.PAGE_FLUSHING_DISABLED, count = 2)
    })
    public void extensiveRepairTaggingStructRepairTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "tagStructureFixes.html";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_tagStructureFixes.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "tagStructureFixesUA2.pdf";

        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_tagStructureFixesUA1.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "tagStructureFixesUA1.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void inputFieldsUA2Test() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "input.html";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_input.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "inputUA2.pdf";

        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_inputUA1.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "inputUA1.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = PdfUALogMessageConstants.PAGE_FLUSHING_DISABLED, count = 2)
    })
    public void tableUa2Test() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "table.html";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_table.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "tableUA2.pdf";

        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_tableUA1.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "tableUA1.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void complexParagraphStructure() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "complexParagraphStructure.html";

        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_complexParagraphStructureUA1.pdf";
        String destinationPdfUa1 = DESTINATION_FOLDER + "complexParagraphStructureUA1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_complexParagraphStructure.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "complexParagraphStructure.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
    }

    @Test
    public void emptyTableDataCellTest() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "emptyTableDataCell.html";

        String cmpPdfUa1 = SOURCE_FOLDER + "cmp_emptyTableDataCellUa1.pdf";
        String cmpPdfUa2 = SOURCE_FOLDER + "cmp_emptyTableDataCellUa2.pdf";

        String destinationPdfUa1 = DESTINATION_FOLDER + "emptyTableDataCellUa1.pdf";
        String destinationPdfUa2 = DESTINATION_FOLDER + "emptyTableDataCellUa2.pdf";

        convertToUa1AndCheckCompliance(sourceHtml, destinationPdfUa1, cmpPdfUa1, true, null);
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdfUa2, cmpPdfUa2, true);
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
                                                boolean isExpectedOk, String expectedErrorMessage)
            throws IOException, InterruptedException {
        convertToUa1AndCheckCompliance(sourceHtml, destinationPdf, cmpPdf, new ConverterProperties(), isExpectedOk,
                expectedErrorMessage);
    }

    private void convertToUa2AndCheckCompliance(String sourceHtml, String destinationPdf, String cmpPdf,
                                                boolean isExpectedOk)
            throws IOException, InterruptedException {
        convertToUa2AndCheckCompliance(sourceHtml, destinationPdf, cmpPdf, new ConverterProperties(), isExpectedOk);
    }

    private void convertToUa1AndCheckCompliance(String sourceHtml, String destinationPdf, String cmpPdf,
                                                ConverterProperties converterProperties, boolean isExpectedOk,
                                                String expectedErrorMessage) throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfUADocument(new PdfWriter(destinationPdf),
                new PdfUAConfig(PdfUAConformance.PDF_UA_1, "simple doc", "eng"));

        ConverterProperties converterPropertiesCopy;
        if (converterProperties == null) {
            converterPropertiesCopy = new ConverterProperties();
        } else {
            converterPropertiesCopy = new ConverterProperties(converterProperties);
        }

        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterPropertiesCopy.setFontProvider(fontProvider);
        converterPropertiesCopy.setBaseUri(SOURCE_FOLDER);
        converterPropertiesCopy.setOutlineHandler(OutlineHandler.createStandardHandler());

        if (expectedErrorMessage != null) {
            Exception e = Assertions.assertThrows(PdfUAConformanceException.class,
                    () -> HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument,
                            converterPropertiesCopy));
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        } else {
            HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterPropertiesCopy);
            compareAndCheckCompliance(destinationPdf, cmpPdf, isExpectedOk);
        }
    }

    private void convertToUa2AndCheckCompliance(String sourceHtml, String destinationPdf, String cmpPdf,
                                                ConverterProperties converterProperties, boolean isExpectedOk)
            throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfUADocument(new PdfWriter(destinationPdf, new WriterProperties().setPdfVersion(
                PdfVersion.PDF_2_0)), new PdfUAConfig(PdfUAConformance.PDF_UA_2, "simple doc", "en-US"));

        ConverterProperties converterPropertiesCopy;
        if (converterProperties == null) {
            converterPropertiesCopy = new ConverterProperties();
        } else {
            converterPropertiesCopy = new ConverterProperties(converterProperties);
        }

        FontProvider fontProvider = new BasicFontProvider(false, true, false);
        converterPropertiesCopy.setFontProvider(fontProvider);
        converterPropertiesCopy.setBaseUri(SOURCE_FOLDER);
        converterPropertiesCopy.setOutlineHandler(OutlineHandler.createStandardHandler());
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterPropertiesCopy);

        compareAndCheckCompliance(destinationPdf, cmpPdf, isExpectedOk);
    }
}
