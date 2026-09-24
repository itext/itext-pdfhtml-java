/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 Apryse Group NV
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
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Document;
import com.itextpdf.test.ExtendedITextTest;

import java.io.ByteArrayInputStream;
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

    @Test
    public void trimLastPageWithTrailingBlankPageTest() {
        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        try (Document document = HtmlConverter.convertToDocument(
                "<html><body><div style='page-break-after: always'>text</div></body></html>",
                new PdfWriter(new ByteArrayOutputStream()), converterProperties)) {
            HtmlDocumentRenderer documentRenderer = (HtmlDocumentRenderer) document.getRenderer();
            document.getPdfDocument().addNewPage();

            Assertions.assertEquals(2, document.getPdfDocument().getNumberOfPages());
            Assertions.assertEquals(1, documentRenderer.simulateTrimLastPage());
        }
    }

    @Test
    public void relayoutProcessesWaitingElementTest() throws Exception {
        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Document document = HtmlConverter.convertToDocument(
                "<html><body><span>first</span><span>second</span></body></html>",
                new PdfWriter(outputStream), converterProperties)) {
            document.relayout();
        }

        try (PdfDocument resultDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(outputStream.toByteArray())))) {
            Assertions.assertEquals(1, resultDocument.getNumberOfPages());
            String pageText = PdfTextExtractor.getTextFromPage(resultDocument.getPage(1));
            Assertions.assertTrue(pageText.contains("first"));
            Assertions.assertTrue(pageText.contains("second"));
        }
    }

    @Test
    public void relayoutDoesNotLeaveWrongEventHandlersHtmlDocumentRendererTest() {
        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        ThrowOnTooManyGetPagePdfDocument pdfDocument =
                new ThrowOnTooManyGetPagePdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new GetPageProbeOnEndPageEventHandler());
        Document document = HtmlConverter.convertToDocument("<html><head><style>@page { @bottom-center { content: counter(page); } }"
                        + "</style></head><body><span>first</span><span>second</span></body></html>",
                pdfDocument, converterProperties);
        try {

            document.relayout();
            
            pdfDocument.resetGetPageCalls();
            pdfDocument.setMaxGetPageCalls(7);
            Assertions.assertDoesNotThrow(() -> document.close());
            Assertions.assertEquals(7, pdfDocument.getPageCalls());
        } finally {
            if (!pdfDocument.isClosed()) {
                document.close();
            }
        }
    }

    @Test
    public void removeEventHandlersBeforeRelayoutTest() {
        CountRemoveEventHandlerPdfDocument pdfDocument =
                new CountRemoveEventHandlerPdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        try (Document document = new Document(pdfDocument)) {
            HtmlDocumentRenderer documentRenderer = new HtmlDocumentRenderer(document, false);
            document.setRenderer(documentRenderer);
            pdfDocument.resetRemoveEventHandlerCalls();

            documentRenderer.removeEventHandlersForRelayout();

            Assertions.assertEquals(2, pdfDocument.getRemoveEventHandlerCalls());
        }
    }

    private static final class ThrowOnTooManyGetPagePdfDocument extends PdfDocument {
        private int pageCalls = 0;
        private int maxGetPageCalls = Integer.MAX_VALUE;

        public ThrowOnTooManyGetPagePdfDocument(PdfWriter writer) {
            super(writer);
        }

        @Override
        public PdfPage getPage(int pageNum) {
            ++pageCalls;
            if (pageCalls > maxGetPageCalls) {
                throw new IllegalStateException("getPage(int) called too many times: " + pageCalls
                        + " (max " + maxGetPageCalls + ")");
            }
            return super.getPage(pageNum);
        }

        public void resetGetPageCalls() {
            pageCalls = 0;
        }

        public void setMaxGetPageCalls(int maxGetPageCalls) {
            this.maxGetPageCalls = maxGetPageCalls;
        }

        public int getPageCalls() {
            return pageCalls;
        }
    }

    private static final class CountRemoveEventHandlerPdfDocument extends PdfDocument {
        private int removeEventHandlerCalls = 0;

        public CountRemoveEventHandlerPdfDocument(PdfWriter writer) {
            super(writer);
        }

        @Override
        public void removeEventHandler(AbstractPdfDocumentEventHandler handler) {
            ++removeEventHandlerCalls;
            super.removeEventHandler(handler);
        }

        public void resetRemoveEventHandlerCalls() {
            removeEventHandlerCalls = 0;
        }

        public int getRemoveEventHandlerCalls() {
            return removeEventHandlerCalls;
        }
    }

    private static final class GetPageProbeOnEndPageEventHandler extends AbstractPdfDocumentEventHandler {
        @Override
        public void onAcceptedEvent(AbstractPdfDocumentEvent event) {
            if (event instanceof PdfDocumentEvent) {
                PdfDocumentEvent pageEvent = (PdfDocumentEvent) event;
                int pageNumber = event.getDocument().getPageNumber(pageEvent.getPage());
                event.getDocument().getPage(pageNumber);
            }
        }
    }
}
