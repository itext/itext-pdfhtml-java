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
package com.itextpdf.html2pdf.resolver.resource;

import com.itextpdf.commons.utils.Base64;
import com.itextpdf.commons.utils.FileUtil;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.ContextMappingHelper;
import com.itextpdf.html2pdf.util.SvgProcessingUtil;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.element.SvgImage;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * Extends {@link ResourceResolver} to also support SVG images
 */
public class HtmlResourceResolver extends ResourceResolver {

    private static final String SVG_PREFIX = "data:image/svg+xml";
    private static final Pattern SVG_IDENTIFIER_PATTERN = Pattern.compile(",[\\s]*(<svg )");

    private final ProcessorContext context;

    /**
     * Creates a new {@link HtmlResourceResolver} instance.
     * If {@code baseUri} is a string that represents an absolute URI with any schema
     * except "file" - resources url values will be resolved exactly as "new URL(baseUrl, uriString)". Otherwise base URI
     * will be handled as path in local file system.
     * <p>
     * If empty string or relative URI string is passed as base URI, then it will be resolved against current working
     * directory of this application instance.
     *
     * @param baseUri base URI against which all relative resource URIs will be resolved
     * @param context {@link ProcessorContext} instance for the current HTML to PDF conversion process
     */
    public HtmlResourceResolver(String baseUri, ProcessorContext context) {
        this(baseUri, context, null);
    }

    /**
     * Creates a new {@link HtmlResourceResolver} instance.
     * If {@code baseUri} is a string that represents an absolute URI with any schema
     * except "file" - resources url values will be resolved exactly as "new URL(baseUrl, uriString)". Otherwise base URI
     * will be handled as path in local file system.
     * <p>
     * If empty string or relative URI string is passed as base URI, then it will be resolved against current working
     * directory of this application instance.
     *
     * @param baseUri base URI against which all relative resource URIs will be resolved
     * @param context {@link ProcessorContext} instance for the current HTML to PDF conversion process
     * @param retriever the resource retriever with the help of which data from resources will be retrieved
     */
    public HtmlResourceResolver(String baseUri, ProcessorContext context, IResourceRetriever retriever) {
        super(baseUri, retriever);
        this.context = context;
    }

    @Override
    public PdfXObject retrieveImage(String src) {
        if (src != null && src.trim().startsWith(SVG_PREFIX) && SVG_IDENTIFIER_PATTERN.matcher(src).find()) {
            PdfXObject imageXObject = tryResolveSvgImageSource(src);
            if (imageXObject != null) {
                return imageXObject;
            }
        }
        return super.retrieveImage(src);
    }

    /**
     * Retrieve image as either {@link com.itextpdf.kernel.pdf.xobject.PdfImageXObject}, or {@link PdfFormXObject}.
     *
     * @param src either link to file or base64 encoded stream
     * @return PdfXObject on success, otherwise null
     */
    @Override
    protected PdfXObject tryResolveBase64ImageSource(String src) {
        String fixedSrc = src.replaceAll("\\s", "");
        if (fixedSrc.startsWith(SVG_PREFIX)) {
            fixedSrc = fixedSrc.substring(fixedSrc.indexOf(BASE64_IDENTIFIER) + BASE64_IDENTIFIER.length() + 1);
            try (ByteArrayInputStream stream = new ByteArrayInputStream(Base64.decode(fixedSrc))) {
                PdfFormXObject xObject = HtmlResourceResolver.processAsSvg(stream, context, null);
                if (xObject != null) {
                    return xObject;
                }
            } catch (Exception ignored) {
            }
        }
        return super.tryResolveBase64ImageSource(src);
    }

    @Override
    protected PdfXObject createImageByUrl(URL url) throws Exception {
        try {
            return super.createImageByUrl(url);
        } catch (Exception ignored) {
            try (InputStream is = getRetriever().getInputStreamByUrl(url)) {
                return is == null ? null : HtmlResourceResolver.processAsSvg(is, context, FileUtil.parentDirectory(url));
            }
        }
    }

    private PdfXObject tryResolveSvgImageSource(String src) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8))) {
            PdfFormXObject xObject = HtmlResourceResolver.processAsSvg(stream, context, null);
            if (xObject != null) {
                return xObject;
            }
        } catch (Exception ignored) {
            //Logs an error in a higher-level method if null is returned
        }
        return null;
    }

    private static PdfFormXObject processAsSvg(InputStream stream, ProcessorContext context, String parentDir) {
        SvgConverterProperties svgConverterProperties = ContextMappingHelper.mapToSvgConverterProperties(context);
        if (parentDir != null) {
            svgConverterProperties.setBaseUri(parentDir);
        }
        ISvgProcessorResult res = SvgConverter.parseAndProcess(stream, svgConverterProperties);
        SvgProcessingUtil processingUtil = new SvgProcessingUtil(context.getResourceResolver());
        return processingUtil.createXObjectFromProcessingResult(res, context.getPdfDocument());
    }
}
