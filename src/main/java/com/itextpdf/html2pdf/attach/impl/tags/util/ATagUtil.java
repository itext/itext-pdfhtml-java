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
package com.itextpdf.html2pdf.attach.impl.tags.util;

import com.itextpdf.styledxmlparser.resolver.resource.UriResolver;

import java.net.MalformedURLException;

/**
 * Utility class to work with the data in A tag.
 */
public final class ATagUtil {

    private ATagUtil() {
        //Empty constructor
    }

    /**
     * Resolves a link in A tag.
     *
     * @param anchorLink the link in A tag
     * @param baseUrl the base URL
     *
     * @return the resolved link
     */
    public static String resolveAnchorLink(String anchorLink, String baseUrl) {
        if (baseUrl != null) {
            UriResolver uriResolver = new UriResolver(baseUrl);
            if (!anchorLink.startsWith("#")) {
                try {
                    String resolvedUri = uriResolver.resolveAgainstBaseUri(anchorLink).toExternalForm();
                    if (!anchorLink.endsWith("/") && resolvedUri.endsWith("/")) {
                        resolvedUri = resolvedUri.substring(0, resolvedUri.length() - 1);
                    }
                    if (!resolvedUri.startsWith("file:")) {
                        return resolvedUri;
                    }
                } catch (MalformedURLException exception) {
                }
            }
        }
        return anchorLink;
    }
}
