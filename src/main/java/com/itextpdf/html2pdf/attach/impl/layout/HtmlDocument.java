package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;

/**
 * This class is a wrapper on {@link Document}, which is the default root element while creating a self-sufficient PDF.
 * It contains several html-specific customizations.
 */
public class HtmlDocument extends Document {

    /**
     * Creates a html document from a {@link PdfDocument} with a manually set {@link
     * PageSize}.
     *
     * @param pdfDoc         the in-memory representation of the PDF document
     * @param pageSize       the page size
     * @param immediateFlush if true, write pages and page-related instructions
     *                       to the {@link PdfDocument} as soon as possible.
     */
    public HtmlDocument(PdfDocument pdfDoc, PageSize pageSize, boolean immediateFlush) {
        super(pdfDoc, pageSize, immediateFlush);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void relayout() {
        if (rootRenderer instanceof HtmlDocumentRenderer) {
            ((HtmlDocumentRenderer) rootRenderer).removeEventHandlers();
        }
        super.relayout();
    }
}
