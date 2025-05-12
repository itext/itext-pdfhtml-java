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
