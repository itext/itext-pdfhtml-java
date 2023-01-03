/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
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

    private ProcessorContext context;

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
                PdfFormXObject xObject = processAsSvg(stream, context, null);
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
            PdfFormXObject xObject = processAsSvg(stream, context, null);
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
        if (context.getPdfDocument() != null) {
            SvgProcessingUtil processingUtil = new SvgProcessingUtil(context.getResourceResolver());
            return processingUtil.createXObjectFromProcessingResult(res, context.getPdfDocument());
        } else {
            return null;
        }
    }
}
