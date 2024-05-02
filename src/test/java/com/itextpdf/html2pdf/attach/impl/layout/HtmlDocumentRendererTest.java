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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.test.ExtendedITextTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("UnitTest")
public class HtmlDocumentRendererTest extends ExtendedITextTest {

    @Test
    public void shouldAttemptTrimLastPageTest() {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        Document document = new Document(pdfDocument);
        HtmlDocumentRenderer documentRenderer = new HtmlDocumentRenderer(document, false);
        document.setRenderer(documentRenderer);
        pdfDocument.addNewPage();

        Assertions.assertEquals(1, pdfDocument.getNumberOfPages());
        // For one-page documents it does not make sense to attempt to trim last page
        Assertions.assertFalse(documentRenderer.shouldAttemptTrimLastPage());

        pdfDocument.addNewPage();
        Assertions.assertEquals(2, pdfDocument.getNumberOfPages());
        // If there are more than one page, we try to trim last page
        Assertions.assertTrue(documentRenderer.shouldAttemptTrimLastPage());
    }

    @Test
    public void trimLastPageIfNecessaryTest() {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        Document document = new Document(pdfDocument);
        HtmlDocumentRenderer documentRenderer = new HtmlDocumentRenderer(document, false);
        document.setRenderer(documentRenderer);
        pdfDocument.addNewPage();
        pdfDocument.addNewPage();
        new PdfCanvas(pdfDocument.getLastPage()).moveTo(10, 10).lineTo(20, 20).stroke();
        pdfDocument.addNewPage();

        Assertions.assertEquals(3, pdfDocument.getNumberOfPages());
        documentRenderer.trimLastPageIfNecessary();
        Assertions.assertEquals(2, pdfDocument.getNumberOfPages());
        documentRenderer.trimLastPageIfNecessary();
        Assertions.assertEquals(2, pdfDocument.getNumberOfPages());
    }

    @Test
    public void estimatedNumberOfPagesInNextRendererEmptyDocumentTest() {
        Document document = HtmlConverter.convertToDocument("<html></html>",
                new PdfWriter(new ByteArrayOutputStream()));
        HtmlDocumentRenderer documentRenderer = (HtmlDocumentRenderer) document.getRenderer();

        HtmlDocumentRenderer nextRenderer = (HtmlDocumentRenderer) documentRenderer.getNextRenderer();
        Assertions.assertEquals(0, nextRenderer.getEstimatedNumberOfPages());
    }

    @Test
    public void estimatedNumberOfPagesInNextRendererDocumentWithTextChunkTest() {
        Document document = HtmlConverter.convertToDocument("<html>text</html>",
                new PdfWriter(new ByteArrayOutputStream()));
        HtmlDocumentRenderer documentRenderer = (HtmlDocumentRenderer) document.getRenderer();

        HtmlDocumentRenderer nextRenderer = (HtmlDocumentRenderer) documentRenderer.getNextRenderer();
        Assertions.assertEquals(1, nextRenderer.getEstimatedNumberOfPages());
    }
}
