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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code meta} element.
 */
public class MetaTagWorker implements ITagWorker {


    /**
     * Creates a new {@link MetaTagWorker} instance.
     *
     * @param tag     the tag
     * @param context the context
     */
    public MetaTagWorker(IElementNode tag, ProcessorContext context) {
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        // Note that charset and http-equiv attributes are processed on DataUtil#parseByteData(ByteBuffer, String, String, Parser) level.
        String name = element.getAttribute(AttributeConstants.NAME);
        if (null != name) {
            name = name.toLowerCase();
            String content = element.getAttribute(AttributeConstants.CONTENT);
            // although iText do not visit head during processing html to elements
            // meta tag can by accident be presented in body section and that shouldn't cause NPE
            if (null != content && null != context.getPdfDocument()) {
                PdfDocumentInfo info = context.getPdfDocument().getDocumentInfo();
                if (AttributeConstants.AUTHOR.equals(name)) {
                    info.setAuthor(content);
                } else if (AttributeConstants.APPLICATION_NAME.equals(name)) {
                    info.setCreator(content);
                } else if (AttributeConstants.KEYWORDS.equals(name)) {
                    info.setKeywords(content);
                } else if (AttributeConstants.DESCRIPTION.equals(name)) {
                    info.setSubject(content);
                } else {
                    info.setMoreInfo(name, content);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return null;
    }
}
