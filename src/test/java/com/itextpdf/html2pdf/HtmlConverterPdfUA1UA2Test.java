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
import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfAConformance;
import com.itextpdf.kernel.pdf.PdfUAConformance;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.pdfua.exceptions.PdfUAConformanceException;
import com.itextpdf.pdfua.exceptions.PdfUAExceptionMessageConstants;
import com.itextpdf.pdfua.logs.PdfUALogMessageConstants;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.pdfa.VeraPdfValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    public static Object[] conformanceLevels() {
        return new Object[]{PdfUAConformance.PDF_UA_1, PdfUAConformance.PDF_UA_2};
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void simpleLinkTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "simpleLink.html";
        String cmpFile = SOURCE_FOLDER + "cmp_simpleLinkUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "simpleLinkUa" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }


    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void backwardLinkTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "backwardLink.html";
        String cmpFile = SOURCE_FOLDER + "cmp_backwardLinkUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "backwardLinkUa" + conformance.getPart() + ".pdf";

        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void imageLinkTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "imageLink.html";
        String cmpFile = SOURCE_FOLDER + "cmp_imageLinkUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "imageLinkUa" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void externalLinkTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "externalLink.html";
        String cmpPdf = SOURCE_FOLDER + "cmp_externalLinkUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "externalLinkUa" + conformance.getPart() + ".pdf";

        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpPdf, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void simpleOutlineTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        if (conformance == PdfUAConformance.PDF_UA_1) {
            String sourceHtmlUa1 = SOURCE_FOLDER + "simpleOutlineUa1.html";
            String cmpPdfUa1 = SOURCE_FOLDER + "cmp_simpleOutlineUa1.pdf";
            String destinationPdfUa1 = DESTINATION_FOLDER + "simpleOutlineUa1.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtmlUa1, destinationPdfUa1, cmpPdfUa1, null, true, null);
        }
        if (conformance == PdfUAConformance.PDF_UA_2) {
            String sourceHtmlUa2 = SOURCE_FOLDER + "simpleOutlineUa2.html";
            String cmpPdfUa2 = SOURCE_FOLDER + "cmp_simpleOutlineUa2.pdf";
            String destinationPdfUa2 = DESTINATION_FOLDER + "simpleOutlineUa2.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtmlUa2, destinationPdfUa2, cmpPdfUa2, null, true, null);
        }
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void unsupportedGlyphTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "unsupportedGlyph.html";
        String expectedUaMessage = MessageFormatUtil.format(
                PdfUAExceptionMessageConstants.GLYPH_IS_NOT_DEFINED_OR_WITHOUT_UNICODE, '中');


        if (conformance == PdfUAConformance.PDF_UA_1) {
            String cmpPdfUa1 = SOURCE_FOLDER + "cmp_unsupportedGlyphUa1.pdf";
            String destinationPdfUa1 = DESTINATION_FOLDER + "unsupportedGlyphUa1.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa1, cmpPdfUa1, null, false,
                    expectedUaMessage);
        }
        if (conformance == PdfUAConformance.PDF_UA_2) {
            String cmpPdfUa2 = SOURCE_FOLDER + "cmp_unsupportedGlyphUa2.pdf";
            String destinationPdfUa2 = DESTINATION_FOLDER + "unsupportedGlyphUa2.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa2, cmpPdfUa2, null, false,
                    expectedUaMessage);
        }
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void emptyElementsTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "emptyElements.html";
        String cmpFile = SOURCE_FOLDER + "cmp_emptyElementsUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "emptyElementsUa" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void boxSizingInlineBlockTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "boxSizingInlineBlock.html";
        String cmpFile = SOURCE_FOLDER + "cmp_boxSizingInlineBlockUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "boxSizingInlineBlockUa" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void divInButtonTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "divInButton.html";
        String cmpFile = SOURCE_FOLDER + "cmp_divInButtonUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "divInButtonUa" + conformance.getPart() + ".pdf";

        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void headingInButtonTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "headingInButton.html";
        String cmpFile = SOURCE_FOLDER + "cmp_headingInButtonUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "headingInButtonUa" + conformance.getPart() + ".pdf";

        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void paragraphsInHeadingsTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "paragraphsInHeadings.html";
        String cmpFile = SOURCE_FOLDER + "cmp_paragraphsInHeadingsUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "paragraphsInHeadingsUa" + conformance.getPart() + ".pdf";

        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    // TODO DEVSIX-8864 PDF 2.0: Destination in GoTo action is not a structure destination
    public void pageBreakAfterAvoidTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "pageBreakAfterAvoid.html";

        if (conformance == PdfUAConformance.PDF_UA_1) {
            String cmpPdfUa1 = SOURCE_FOLDER + "cmp_pageBreakAfterAvoidUa1.pdf";
            String destinationPdfUa1 = DESTINATION_FOLDER + "pageBreakAfterAvoidUa1.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa1, cmpPdfUa1, null, true, null);
        }

        if (conformance == PdfUAConformance.PDF_UA_2) {
            String cmpPdfUa2 = SOURCE_FOLDER + "cmp_pageBreakAfterAvoidUa2.pdf";
            String destinationPdfUa2 = DESTINATION_FOLDER + "pageBreakAfterAvoidUa2.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa2, cmpPdfUa2, null, false, null);
        }
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    // TODO DEVSIX-8864 PDF 2.0: Destination in GoTo action is not a structure destination
    public void linkWithPageBreakBeforeTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "linkWithPageBreakBefore.html";
        if (conformance == PdfUAConformance.PDF_UA_1) {
            String cmpPdfUa1 = SOURCE_FOLDER + "cmp_linkWithPageBreakBeforeUa1.pdf";
            String destinationPdfUa1 = DESTINATION_FOLDER + "linkWithPageBreakBeforeUa1.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa1, cmpPdfUa1, null, true, null);
        }
        if (conformance == PdfUAConformance.PDF_UA_2) {
            String cmpPdfUa2 = SOURCE_FOLDER + "cmp_linkWithPageBreakBeforeUa2.pdf";
            String destinationPdfUa2 = DESTINATION_FOLDER + "linkWithPageBreakBeforeUa2.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa2, cmpPdfUa2, null, false, null);
        }

    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void emptyHtmlTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "emptyHtml.html";
        String cmpFile = SOURCE_FOLDER + "cmp_emptyHtmlUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "emptyHtmlUa" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void inputWithTitleTagTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "inputWithTitleTag.html";
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setCreateAcroForm(true);


        if (conformance == PdfUAConformance.PDF_UA_1) {
            String cmpPdfUa1 = SOURCE_FOLDER + "cmp_inputWithTitleTagUa1.pdf";
            String destinationPdfUa1 = DESTINATION_FOLDER + "inputWithTitleTagUa1.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa1, cmpPdfUa1, converterProperties,
                    true, null);
        }
        if (conformance == PdfUAConformance.PDF_UA_2) {
            String cmpPdfUa2 = SOURCE_FOLDER + "cmp_inputWithTitleTagUa2.pdf";
            String destinationPdfUa2 = DESTINATION_FOLDER + "inputWithTitleTagUa2.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa2, cmpPdfUa2, converterProperties,
                    true, null);
        }
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    // TODO DEVSIX-8883 content is not tagged as real content or tagged as artifact after conversion
    public void svgBase64Test(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "svgBase64.html";

        if (conformance == PdfUAConformance.PDF_UA_1) {
            String cmpPdfUa1 = SOURCE_FOLDER + "cmp_svgBase64Ua1.pdf";
            String destinationPdfUa1 = DESTINATION_FOLDER + "svgBase64Ua1.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa1, cmpPdfUa1, null, false, null);
        }

        if (conformance == PdfUAConformance.PDF_UA_2) {
            String cmpPdfUa2 = SOURCE_FOLDER + "cmp_svgBase64Ua2.pdf";
            String destinationPdfUa2 = DESTINATION_FOLDER + "svgBase64Ua2.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa2, cmpPdfUa2, null, false, null);
        }
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    // TODO DEVSIX-8883 content is not tagged as real content or tagged as artifact after conversion
    public void pngInDivStyleTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        // Investigate why VeraPdf doesn't complain about the missing tag.
        String sourceHtml = SOURCE_FOLDER + "pngInDivStyle.html";

        if (conformance == PdfUAConformance.PDF_UA_1) {
            String cmpPdfUa1 = SOURCE_FOLDER + "cmp_pngInDivStyleUa1.pdf";
            String destinationPdfUa1 = DESTINATION_FOLDER + "pngInDivStyleUa1.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa1, cmpPdfUa1, null, true, null);
        }
        if (conformance == PdfUAConformance.PDF_UA_2) {
            String cmpPdfUa2 = SOURCE_FOLDER + "cmp_pngInDivStyleUa2.pdf";
            String destinationPdfUa2 = DESTINATION_FOLDER + "pngInDivStyleUa2.pdf";
            convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdfUa2, cmpPdfUa2, null, true, null);
        }
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void svgAlternativeDescription(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "svgSimpleAlternateDescription.html";
        String cmpFile = SOURCE_FOLDER + "cmp_svgSimpleAlternateDescriptionUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "svgSimpleAlternateDescriptionUa" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    @LogMessages(messages = {@LogMessage(messageTemplate = PdfUALogMessageConstants.PAGE_FLUSHING_DISABLED, count = 1)})
    public void extensiveRepairTaggingStructRepairTest(PdfUAConformance conformance)
            throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "tagStructureFixes.html";
        String cmpFile = SOURCE_FOLDER + "cmp_tagStructureFixesUA" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "tagStructureFixesUA" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void inputFieldsUA2Test(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "input.html";
        String cmpFile = SOURCE_FOLDER + "cmp_inputUA" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "inputUA" + conformance.getPart() + ".pdf";

        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    @LogMessages(messages = {@LogMessage(messageTemplate = PdfUALogMessageConstants.PAGE_FLUSHING_DISABLED, count = 1)})
    public void tableUa2Test(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "table.html";
        String cmpFile = SOURCE_FOLDER + "cmp_tableUA" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "tableUA" + conformance.getPart() + ".pdf";

        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void complexParagraphStructure(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "complexParagraphStructure.html";
        String cmpFile = SOURCE_FOLDER + "cmp_complexParagraphStructureUA" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "complexParagraphStructureUA" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpFile, null, true, null);
    }

    @ParameterizedTest
    @MethodSource("conformanceLevels")
    public void emptyTableDataCellTest(PdfUAConformance conformance) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + "emptyTableDataCell.html";

        String cmpPdf = SOURCE_FOLDER + "cmp_emptyTableDataCellUa" + conformance.getPart() + ".pdf";
        String destinationPdf = DESTINATION_FOLDER + "emptyTableDataCellUa" + conformance.getPart() + ".pdf";
        convertToUaAndCheckCompliance(conformance, sourceHtml, destinationPdf, cmpPdf, null, true, null);
    }

    @Test
    public void duplicateConformanceLevelAAndUAThrows() {
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfUAConformance(PdfUAConformance.PDF_UA_1);
        converterProperties.setPdfAConformance(PdfAConformance.PDF_A_4);
        PdfWriter dummy = new PdfWriter(new ByteArrayOutputStream());
        Exception e = Assertions.assertThrows(Html2PdfException.class, () -> {
            HtmlConverter.convertToPdf("<h1>Let's gooooo</h1>", dummy, converterProperties);
        });
        Assertions.assertEquals(Html2PdfLogMessageConstant.PDF_A_AND_PDF_UA_CONFORMANCE_CANNOT_BE_USED_TOGETHER,
                e.getMessage());
    }

    private void convertToUaAndCheckCompliance(PdfUAConformance conformance, String sourceHtml, String destinationPdf,
                                               String cmpPdf, ConverterProperties converterProperties,
                                               boolean isExpectedOk, String expectedErrorMessage)
            throws IOException, InterruptedException {

        if (converterProperties == null) {
            converterProperties = new ConverterProperties();
        }
        converterProperties.setPdfUAConformance(conformance);
        converterProperties.setBaseUri(SOURCE_FOLDER);


        WriterProperties writerProperties = new WriterProperties();
        if (conformance == PdfUAConformance.PDF_UA_2) {
            writerProperties.setPdfVersion(PdfVersion.PDF_2_0);
        }
        FileInputStream fileInputStream = new FileInputStream(sourceHtml);
        try (PdfWriter pdfWriter = new PdfWriter(destinationPdf, writerProperties)) {
            if (expectedErrorMessage == null) {
                HtmlConverter.convertToPdf(fileInputStream, pdfWriter, converterProperties);
                compareAndCheckCompliance(destinationPdf, cmpPdf, isExpectedOk);
                return;
            }
            ConverterProperties finalConverterProperties = converterProperties;
            Exception e = Assertions.assertThrows(PdfUAConformanceException.class, () -> {
                HtmlConverter.convertToPdf(fileInputStream, pdfWriter, finalConverterProperties);
            });
            Assertions.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    private static void compareAndCheckCompliance(String destinationPdf, String cmpPdf, boolean isExpectedOk)
            throws IOException, InterruptedException {
        if (isExpectedOk) {
            Assertions.assertNull(new VeraPdfValidator().validate(destinationPdf));
        } else {
            new VeraPdfValidator().validateFailure(destinationPdf);
        }
        Assertions.assertNull(
                new CompareTool().compareByContent(destinationPdf, cmpPdf, DESTINATION_FOLDER, "diff_simple_"));
        Assertions.assertNull(new CompareTool().compareXmp(destinationPdf, cmpPdf, true));
    }

}
