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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.util.ATagUtil;
import com.itextpdf.html2pdf.attach.util.LinkHelper;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for a link block.
 */
public class ABlockTagWorker extends DivTagWorker {

    /**
     * Creates a new {@link ABlockTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public ABlockTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        super.processEnd(element, context);

        String url = element.getAttribute(AttributeConstants.HREF);
        if (url != null) {
            String anchorLink = element.getAttribute(AttributeConstants.HREF);
            String baseUri = context.getBaseUri();
            String modifiedUrl = ATagUtil.resolveAnchorLink(anchorLink, baseUri);
            LinkHelper.applyLinkAnnotation(getElementResult(), modifiedUrl, context, element);
        }

        if (getElementResult() != null) {
            String name = element.getAttribute(AttributeConstants.NAME);
            getElementResult().setProperty(Property.DESTINATION, name);
        }
    }
}
