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
            super.relayout();
            ((HtmlDocumentRenderer) rootRenderer).processWaitingElement();
        }
    }
}
