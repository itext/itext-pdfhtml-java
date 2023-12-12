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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.LinkHelper;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.resolver.resource.UriResolver;

import java.net.MalformedURLException;

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
            String base = context.getBaseUri();
            if (base != null) {
                UriResolver uriResolver = new UriResolver(base);
                if (!(url.startsWith("#") && uriResolver.isLocalBaseUri()))
                    try {
                        String resolvedUri = uriResolver.resolveAgainstBaseUri(url).toExternalForm();
                        if (!url.endsWith("/") && resolvedUri.endsWith("/"))
                            resolvedUri = resolvedUri.substring(0, resolvedUri.length() - 1);
                        if (!resolvedUri.startsWith("file:"))
                            url = resolvedUri;
                    } catch (MalformedURLException exception) {
                    }
            }
            ((Div) getElementResult()).getAccessibilityProperties().setRole(StandardRoles.LINK);
            LinkHelper.applyLinkAnnotation(getElementResult(), url, context);
        }

        if (getElementResult() != null) {
            String name = element.getAttribute(AttributeConstants.NAME);
            getElementResult().setProperty(Property.DESTINATION, name);
        }
    }
}
