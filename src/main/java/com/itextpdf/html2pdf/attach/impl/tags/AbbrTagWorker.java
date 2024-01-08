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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Text;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * Tag worker class for the {@code abbr} element.
 */
public class AbbrTagWorker extends SpanTagWorker {

    /**
     * Creates a new {@link AbbrTagWorker} instance.
     *
     * @param element the tag
     * @param context the context
     */
    public AbbrTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return super.processContent(content, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        super.processEnd(element, context);
        enrichSpan(element.getAttribute(AttributeConstants.TITLE));
    }

    /**
     * Enrich the span with accessibility features, more specifically the expansion text.
     *
     * @param expansionText the expansion text
     */
    private void enrichSpan(String expansionText) {
        for (IPropertyContainer container : getAllElements()) {
            if (container instanceof Text) {
                Text txt = (Text) container;
                if (expansionText != null) {
                    txt.getAccessibilityProperties().setExpansion(expansionText);
                    break;
                }
            }
        }
    }
}
