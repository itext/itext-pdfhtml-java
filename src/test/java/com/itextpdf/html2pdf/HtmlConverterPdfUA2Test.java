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
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
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
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import com.itextpdf.test.pdfa.VeraPdfValidator;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class HtmlConverterPdfUA2Test extends ExtendedITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterPdfUA2Test/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterPdfUA2Test/";

    @BeforeClass
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
        FontProvider fontProvider = new DefaultFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        /* TODO: DEVSIX-5700 - Links created from html2pdf are not ua-2 compliant
         * One verapdf error is generated here:
         * 1. clause="8.5.1", Real content that does not possess the semantics of text objects and does not have
         *    an alternate textual representation is not enclosed within Figure or Formula structure elements.
         */
        compareAndCheckCompliance(destinationPdf, cmpPdf, false);
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
        FontProvider fontProvider = new DefaultFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        /* TODO: DEVSIX-5700 - Links created from html2pdf are not ua-2 compliant
         * One verapdf error is generated here:
         * 1. clause="8.5.1", Real content that does not possess the semantics of text objects and does not have
         *    an alternate textual representation is not enclosed within Figure or Formula structure elements.
         */
        compareAndCheckCompliance(destinationPdf, cmpPdf, false);
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
        FontProvider fontProvider = new DefaultFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setBaseUri(SOURCE_FOLDER);
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        /* TODO: DEVSIX-5700 - Links created from html2pdf are not ua-2 compliant
         * One verapdf error is generated here:
         * 1. clause="8.5.1", Real content that does not possess the semantics of text objects and does not have
         *    an alternate textual representation is not enclosed within Figure or Formula structure elements.
         */
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
        FontProvider fontProvider = new DefaultFontProvider(false, true, false);
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setOutlineHandler(OutlineHandler.createStandardHandler());
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfDocument, converterProperties);

        compareAndCheckCompliance(destinationPdf, cmpPdf, true);
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
            Assert.assertNull(new VeraPdfValidator().validate(destinationPdf));
        } else {
            Assert.assertNotNull(new VeraPdfValidator().validate(destinationPdf));
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, DESTINATION_FOLDER,
                "diff_simple_"));
    }
}
