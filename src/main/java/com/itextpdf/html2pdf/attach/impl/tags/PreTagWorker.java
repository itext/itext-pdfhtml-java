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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code pre} element.
 */
public class PreTagWorker extends DivTagWorker {

    /** Keeps track to see if any content was processed. */
    private boolean anyContentProcessed = false;

    /**
     * Creates a new {@link PreTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public PreTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        // It seems that browsers just skip first newline symbol, if any
        if (!anyContentProcessed) {
            if (content.startsWith("\r\n")) {
                content = content.substring(2);
            }
        }
        anyContentProcessed = true;
        return super.processContent(content, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        anyContentProcessed = true;
        return super.processTagChild(childTagWorker, context);
    }
}
