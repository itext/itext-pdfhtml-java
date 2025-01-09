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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.utils.CompareTool;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class MetaTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/MetaTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/MetaTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void meta01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "metaTest01.html"), new File(DESTINATION_FOLDER + "metaTest01.pdf"));
        PdfDocumentInfo pdfDocInfo = new PdfDocument(new PdfReader(DESTINATION_FOLDER + "metaTest01.pdf")).getDocumentInfo();
        CompareTool compareTool = new CompareTool();
        Assertions.assertNull(compareTool.compareByContent(DESTINATION_FOLDER + "metaTest01.pdf", SOURCE_FOLDER + "cmp_metaTest01.pdf",
                DESTINATION_FOLDER, "diff01_"));
        Assertions.assertNull(compareTool.compareDocumentInfo(DESTINATION_FOLDER + "metaTest01.pdf", SOURCE_FOLDER + "cmp_metaTest01.pdf"));
        Assertions.assertEquals(pdfDocInfo.getMoreInfo("test"), "the test content");
    }

    @Test
    // In this test we also check that it's not possible to override description name content
    // (which iText converts to pdf's Subject content) with Subject name content
    public void meta02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "metaTest02.html"), new File(DESTINATION_FOLDER + "metaTest02.pdf"));
        PdfDocumentInfo pdfDocInfo = new PdfDocument(new PdfReader(DESTINATION_FOLDER + "metaTest02.pdf")).getDocumentInfo();
        CompareTool compareTool = new CompareTool();
        Assertions.assertNull(compareTool.compareByContent(DESTINATION_FOLDER + "metaTest02.pdf", SOURCE_FOLDER + "cmp_metaTest02.pdf",
                DESTINATION_FOLDER, "diff02_"));
        Assertions.assertNull(compareTool.compareDocumentInfo(DESTINATION_FOLDER + "metaTest02.pdf", SOURCE_FOLDER + "cmp_metaTest02.pdf"));
        Assertions.assertEquals(pdfDocInfo.getAuthor(), "Bruno Lowagie");
        Assertions.assertEquals(pdfDocInfo.getKeywords(), "metadata, keywords, test");
        Assertions.assertEquals(pdfDocInfo.getSubject(), "This is the description of the page");
        Assertions.assertEquals(pdfDocInfo.getMoreInfo("generator"), "Eugenerator Onegenerator");
        Assertions.assertEquals(pdfDocInfo.getMoreInfo("subject"), "Trying to break iText and write pdf's Subject with subject instead of description name");
    }

    @Test
    public void meta03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "metaTest03.html"), new File(DESTINATION_FOLDER + "metaTest03.pdf"));
        CompareTool compareTool = new CompareTool();
        Assertions.assertNull(compareTool.compareByContent(DESTINATION_FOLDER + "metaTest03.pdf", SOURCE_FOLDER + "cmp_metaTest03.pdf",
                DESTINATION_FOLDER, "diff03_"));
        Assertions.assertNull(compareTool.compareDocumentInfo(DESTINATION_FOLDER + "metaTest03.pdf", SOURCE_FOLDER + "cmp_metaTest03.pdf"));
    }

    @Test
    public void metaApplicationNameTest() throws IOException, InterruptedException {
        String srcHtml = SOURCE_FOLDER + "metaApplicationName.html";
        String outPdf = DESTINATION_FOLDER + "metaApplicationName.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_metaApplicationName.pdf";
        HtmlConverter.convertToPdf(new File(srcHtml), new File(outPdf));
        PdfDocumentInfo pdfDocInfo = new PdfDocument(new PdfReader(outPdf)).getDocumentInfo();
        CompareTool compareTool = new CompareTool();
        Assertions.assertNull(compareTool.compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER, "metaAppName_"));
        Assertions.assertEquals("iText", pdfDocInfo.getCreator());
    }
}
