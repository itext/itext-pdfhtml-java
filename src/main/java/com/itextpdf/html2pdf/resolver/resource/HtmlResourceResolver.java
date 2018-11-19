package com.itextpdf.html2pdf.resolver.resource;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.util.SvgProcessingUtil;
import com.itextpdf.io.codec.Base64;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Extends {@link ResourceResolver} to also support SVG images
 */
public class HtmlResourceResolver extends ResourceResolver {

    private static final String SVG_BASE64_PREFIX = "data:image/svg+xml";

    private ProcessorContext context;

    /**
     * Creates {@link HtmlResourceResolver} instance. If {@code baseUri} is a string that represents an absolute URI with any schema
     * except "file" - resources url values will be resolved exactly as "new URL(baseUrl, uriString)". Otherwise base URI
     * will be handled as path in local file system.
     * <p>
     * If empty string or relative URI string is passed as base URI, then it will be resolved against current working
     * directory of this application instance.
     * </p>
     *
     * @param baseUri base URI against which all relative resource URIs will be resolved.
     * @param context {@link ProcessorContext} instance for the current HTML to PDF conversion process
     */
    public HtmlResourceResolver(String baseUri, ProcessorContext context) {
        super(baseUri);
        this.context = context;
    }

    @Override
    protected PdfXObject tryResolveBase64ImageSource(String src) {
        String fixedSrc = src.replaceAll("\\s", "");
        if (fixedSrc.startsWith(SVG_BASE64_PREFIX)) {
            fixedSrc = fixedSrc.substring(fixedSrc.indexOf(BASE64IDENTIFIER) + 7);
            try {
                PdfFormXObject xObject = processAsSvg(new ByteArrayInputStream(Base64.decode(fixedSrc)), context);
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
            try (InputStream is = url.openStream()) {
                return processAsSvg(is, context);
            }
        }
    }

    private PdfFormXObject processAsSvg(InputStream stream, ProcessorContext context) throws IOException {
        SvgProcessingUtil processingUtil = new SvgProcessingUtil();
        SvgConverterProperties svgConverterProperties = new SvgConverterProperties();
        svgConverterProperties.setBaseUri(context.getBaseUri())
                .setFontProvider(context.getFontProvider())
                .setMediaDeviceDescription(context.getDeviceDescription());
        ISvgProcessorResult res = SvgConverter.parseAndProcess(stream, svgConverterProperties);
        if (context.getPdfDocument() != null) {
            return processingUtil.createXObjectFromProcessingResult(res, context.getPdfDocument());
        } else {
            return null;
        }
    }

}
