/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.attach;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.impl.DefaultHtmlProcessor;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.styledxmlparser.node.IDocumentNode;

import java.util.List;

/**
 * Helper class to add parsed HTML content to an existing iText document,
 * or to parse HTML to a list of iText elements.
 */
public class Attacher {

    /**
     * Instantiates a new {@link Attacher} instance.
     */
    private Attacher() {
    }

    /**
     * Attaches the HTML content stored in a document node to
     * an existing PDF document, using specific converter properties,
     * and returning an iText {@link Document} object.
     *
     * @param documentNode the document node with the HTML
     * @param pdfDocument the {@link PdfDocument} instance
     * @param converterProperties the {@link ConverterProperties} instance
     * @return an iText {@link Document} object
     */
    public static Document attach(IDocumentNode documentNode, PdfDocument pdfDocument, ConverterProperties converterProperties) {
        IHtmlProcessor processor = new DefaultHtmlProcessor(converterProperties);
        return processor.processDocument(documentNode, pdfDocument);
    }

    /**
     * Attaches the HTML content stored in a document node to
     * a list of {@link IElement} objects.
     *
     * @param documentNode the document node with the HTML
     * @param converterProperties the {@link ConverterProperties} instance
     * @return the list of {@link IElement} objects
     */
    public static List<com.itextpdf.layout.element.IElement> attach(IDocumentNode documentNode, ConverterProperties converterProperties) {
        IHtmlProcessor processor = new DefaultHtmlProcessor(converterProperties);
        return processor.processElements(documentNode);
    }

}
