package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class HtmlDocumentRendererTest {

    @Test
    public void shouldAttemptTrimLastPageTest() {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        Document document = new Document(pdfDocument);
        HtmlDocumentRenderer documentRenderer = new HtmlDocumentRenderer(document, false);
        document.setRenderer(documentRenderer);
        pdfDocument.addNewPage();

        Assert.assertEquals(1, pdfDocument.getNumberOfPages());
        // For one-page documents it does not make sense to attempt to trim last page
        Assert.assertFalse(documentRenderer.shouldAttemptTrimLastPage());

        pdfDocument.addNewPage();
        Assert.assertEquals(2, pdfDocument.getNumberOfPages());
        // If there are more than one page, we try to trim last page
        Assert.assertTrue(documentRenderer.shouldAttemptTrimLastPage());
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

        Assert.assertEquals(3, pdfDocument.getNumberOfPages());
        documentRenderer.trimLastPageIfNecessary();
        Assert.assertEquals(2, pdfDocument.getNumberOfPages());
        documentRenderer.trimLastPageIfNecessary();
        Assert.assertEquals(2, pdfDocument.getNumberOfPages());
    }
    
}
