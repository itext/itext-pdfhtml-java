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
package com.itextpdf.html2pdf;

import com.itextpdf.commons.actions.contexts.IMetaInfo;
import com.itextpdf.commons.utils.FileUtil;
import com.itextpdf.html2pdf.attach.Attacher;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.renderer.MetaInfoContainer;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.pdfua.PdfUAConfig;
import com.itextpdf.pdfua.PdfUADocument;
import com.itextpdf.styledxmlparser.IXmlParser;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupHtmlParser;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * The HtmlConverter is the class you will use most when converting HTML to PDF.
 * It contains a series of static methods that accept HTML as a {@link String},
 * {@link File}, or {@link InputStream}, and convert it to PDF in the
 * form of an {@link OutputStream}, {@link File}, or a series of
 * iText elements. It's also possible to write to a {@link PdfWriter} or
 * {@link PdfDocument} instance.
 */
public class HtmlConverter {
    /**
     * Instantiates a new HtmlConverter instance.
     */
    private HtmlConverter() {
    }

    /**
     * Converts a {@link String} containing HTML to an {@link OutputStream}
     * containing PDF.
     *
     * @param html      the html in the form of a {@link String}
     * @param pdfStream the PDF as an {@link OutputStream}
     */
    public static void convertToPdf(String html, OutputStream pdfStream) {
        convertToPdf(html, pdfStream, null);
    }

    /**
     * Converts a {@link String} containing HTML to an {@link OutputStream}
     * containing PDF, using specific {@link ConverterProperties}.
     *
     * @param html                the html in the form of a {@link String}
     * @param pdfStream           the PDF as an {@link OutputStream}
     * @param converterProperties a {@link ConverterProperties} instance
     */
    public static void convertToPdf(String html, OutputStream pdfStream, ConverterProperties converterProperties) {
        convertToPdf(html, new PdfWriter(pdfStream), converterProperties);
    }

    /**
     * Converts a {@link String} containing HTML to PDF by writing PDF content
     * to a {@link PdfWriter} instance.
     *
     * @param html      the html in the form of a {@link String}
     * @param pdfWriter the {@link PdfWriter} instance
     */
    public static void convertToPdf(String html, PdfWriter pdfWriter) {
        convertToPdf(html, pdfWriter, null);
    }

    /**
     * Converts a {@link String} containing HTML to PDF by writing PDF content
     * to a {@link PdfWriter} instance, using specific {@link ConverterProperties}.
     *
     * @param html                the html in the form of a {@link String}
     * @param pdfWriter           the {@link PdfWriter} instance
     * @param converterProperties a {@link ConverterProperties} instance
     */
    public static void convertToPdf(String html, PdfWriter pdfWriter, ConverterProperties converterProperties) {
        DocumentProperties documentProperties = new DocumentProperties()
                .setEventCountingMetaInfo(resolveMetaInfo(converterProperties));
        PdfDocument document = createCorrectDocument(converterProperties, documentProperties, pdfWriter);
        converterProperties = setupCustomConversionProperties(document, converterProperties);
        convertToPdf(html, document, converterProperties);
    }

    /**
     * Converts a {@link String} containing HTML to objects that
     * will be added to a {@link PdfDocument}, using specific {@link ConverterProperties}.
     *
     * @param html                the html in the form of a {@link String}
     * @param pdfDocument         the {@link PdfDocument} instance
     * @param converterProperties a {@link ConverterProperties} instance
     */
    public static void convertToPdf(String html, PdfDocument pdfDocument, ConverterProperties converterProperties) {
        final Document document = convertToDocument(html, pdfDocument, converterProperties);
        document.setProperty(Property.META_INFO, new MetaInfoContainer(resolveMetaInfo(converterProperties)));
        document.close();
    }

    /**
     * Converts HTML stored in a {@link File} to a PDF {@link File}.
     *
     * @param htmlFile the {@link File} containing the source HTML
     * @param pdfFile  the {@link File} containing the resulting PDF
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(File htmlFile, File pdfFile) throws IOException {
        convertToPdf(htmlFile, pdfFile, null);
    }

    /**
     * Converts HTML stored in a {@link File} to a PDF {@link File},
     * using specific {@link ConverterProperties}.
     *
     * @param htmlFile            the {@link File} containing the source HTML
     * @param pdfFile             the {@link File} containing the resulting PDF
     * @param converterProperties a {@link ConverterProperties} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(File htmlFile, File pdfFile, ConverterProperties converterProperties)
            throws IOException {
        if (converterProperties == null) {
            String baseUri = FileUtil.getParentDirectoryUri(htmlFile);
            converterProperties = new ConverterProperties().setBaseUri(baseUri);
        } else if (converterProperties.getBaseUri() == null) {
            String baseUri = FileUtil.getParentDirectoryUri(htmlFile);
            converterProperties = new ConverterProperties(converterProperties).setBaseUri(baseUri);
        }
        try (FileInputStream fileInputStream = new FileInputStream(htmlFile.getAbsolutePath());
             FileOutputStream fileOutputStream = new FileOutputStream(pdfFile.getAbsolutePath())) {
            convertToPdf(fileInputStream, fileOutputStream, converterProperties);
        }
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to a PDF written to
     * an {@link OutputStream}.
     *
     * @param htmlStream the {@link InputStream} with the source HTML
     * @param pdfStream  the {@link OutputStream} for the resulting PDF
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(InputStream htmlStream, OutputStream pdfStream) throws IOException {
        convertToPdf(htmlStream, pdfStream, null);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to a PDF written to
     * an {@link OutputStream}.
     *
     * @param htmlStream          the {@link InputStream} with the source HTML
     * @param pdfStream           the {@link OutputStream} for the resulting PDF
     * @param converterProperties a {@link ConverterProperties} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(InputStream htmlStream, OutputStream pdfStream,
                                    ConverterProperties converterProperties) throws IOException {
        convertToPdf(htmlStream, new PdfWriter(pdfStream), converterProperties);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to objects that
     * will be added to a {@link PdfDocument}.
     *
     * @param htmlStream  the {@link InputStream} with the source HTML
     * @param pdfDocument the {@link PdfDocument} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(InputStream htmlStream, PdfDocument pdfDocument) throws IOException {
        convertToPdf(htmlStream, pdfDocument, null);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to content that
     * will be written to a {@link PdfWriter}.
     *
     * @param htmlStream the {@link InputStream} with the source HTML
     * @param pdfWriter  the {@link PdfWriter} containing the resulting PDF
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(InputStream htmlStream, PdfWriter pdfWriter) throws IOException {
        convertToPdf(htmlStream, new PdfDocument(pdfWriter, new DocumentProperties().setEventCountingMetaInfo(
                createPdf2HtmlMetaInfo())));
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to content that
     * will be written to a {@link PdfWriter}, using specific
     * {@link ConverterProperties}.
     *
     * @param htmlStream          the {@link InputStream} with the source HTML
     * @param pdfWriter           the {@link PdfWriter} containing the resulting PDF
     * @param converterProperties a {@link ConverterProperties} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(InputStream htmlStream, PdfWriter pdfWriter,
                                    ConverterProperties converterProperties) throws IOException {
        DocumentProperties props = new DocumentProperties().setEventCountingMetaInfo(
                resolveMetaInfo(converterProperties));
        PdfDocument document = createCorrectDocument(converterProperties, props, pdfWriter);
        convertToPdf(htmlStream, document, converterProperties);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to objects that
     * will be added to a {@link PdfDocument}, using specific {@link ConverterProperties}.
     *
     * @param htmlStream          the {@link InputStream} with the source HTML
     * @param pdfDocument         the {@link PdfDocument} instance
     * @param converterProperties a {@link ConverterProperties} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void convertToPdf(InputStream htmlStream, PdfDocument pdfDocument,
                                    ConverterProperties converterProperties) throws IOException {
        converterProperties = setupCustomConversionProperties(pdfDocument, converterProperties);
        final Document document = convertToDocument(htmlStream, pdfDocument, converterProperties);
        IMetaInfo metaInfo = resolveMetaInfo(converterProperties);
        document.setProperty(Property.META_INFO, new MetaInfoContainer(metaInfo));
        document.close();
    }

    /**
     * Converts a {@link String} containing HTML to content that
     * will be written to a {@link PdfWriter}, returning a {@link Document} instance.
     *
     * @param html      the html in the form of a {@link String}
     * @param pdfWriter the {@link PdfWriter} containing the resulting PDF
     * @return a {@link Document} instance
     */
    public static Document convertToDocument(String html, PdfWriter pdfWriter) {
        return convertToDocument(html, pdfWriter, null);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to content that
     * will be written to a {@link PdfWriter}, returning a {@link Document} instance.
     *
     * @param htmlStream the {@link InputStream} with the source HTML
     * @param pdfWriter  the {@link PdfWriter} containing the resulting PDF
     * @return a {@link Document} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Document convertToDocument(InputStream htmlStream, PdfWriter pdfWriter) throws IOException {
        return convertToDocument(htmlStream, pdfWriter, null);
    }

    /**
     * Converts a {@link String} containing HTML to content that
     * will be written to a {@link PdfWriter}, using specific
     * {@link ConverterProperties}, returning a {@link Document} instance.
     *
     * @param html                the html in the form of a {@link String}
     * @param pdfWriter           the pdf writer
     * @param converterProperties a {@link ConverterProperties} instance
     * @return a {@link Document} instance
     */
    public static Document convertToDocument(String html, PdfWriter pdfWriter,
                                             ConverterProperties converterProperties) {
        return convertToDocument(html, new PdfDocument(pdfWriter), converterProperties);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to content that
     * will be written to a {@link PdfWriter}, using specific
     * {@link ConverterProperties}, returning a {@link Document} instance.
     *
     * @param htmlStream          the html stream
     * @param pdfWriter           the pdf writer
     * @param converterProperties a {@link ConverterProperties} instance
     * @return a {@link Document} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Document convertToDocument(InputStream htmlStream, PdfWriter pdfWriter,
                                             ConverterProperties converterProperties) throws IOException {
        return convertToDocument(htmlStream, new PdfDocument(pdfWriter), converterProperties);
    }

    /**
     * Converts a {@link String} containing HTML to objects that
     * will be added to a {@link PdfDocument}, using specific {@link ConverterProperties},
     * returning a {@link Document} instance.
     *
     * @param html                the html in the form of a {@link String}
     * @param pdfDocument         the {@link PdfDocument} instance
     * @param converterProperties a {@link ConverterProperties} instance
     * @return a {@link Document} instance
     */
    public static Document convertToDocument(String html, PdfDocument pdfDocument,
                                             ConverterProperties converterProperties) {
        if (pdfDocument.getReader() != null) {
            throw new Html2PdfException(Html2PdfException.PDF_DOCUMENT_SHOULD_BE_IN_WRITING_MODE);
        }
        converterProperties = setupCustomConversionProperties(pdfDocument, converterProperties);
        IXmlParser parser = new JsoupHtmlParser();
        IDocumentNode doc = parser.parse(html);
        return Attacher.attach(doc, pdfDocument, converterProperties);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to objects that
     * will be added to a {@link PdfDocument}, using specific {@link ConverterProperties},
     * returning a {@link Document} instance.
     *
     * @param htmlStream          the {@link InputStream} with the source HTML
     * @param pdfDocument         the {@link PdfDocument} instance
     * @param converterProperties a {@link ConverterProperties} instance
     * @return a {@link Document} instance
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Document convertToDocument(InputStream htmlStream, PdfDocument pdfDocument,
                                             ConverterProperties converterProperties) throws IOException {
        if (pdfDocument.getReader() != null) {
            throw new Html2PdfException(Html2PdfException.PDF_DOCUMENT_SHOULD_BE_IN_WRITING_MODE);
        }
        converterProperties = setupCustomConversionProperties(pdfDocument, converterProperties);
        IXmlParser parser = new JsoupHtmlParser();
        IDocumentNode doc = parser.parse(htmlStream, converterProperties.getCharset());
        return Attacher.attach(doc, pdfDocument, converterProperties);
    }

    /**
     * Converts a {@link String} containing HTML to a {@link List} of
     * iText objects ({@link IElement} instances).
     *
     * @param html the html in the form of a {@link String}
     * @return a list of iText building blocks
     */
    public static List<IElement> convertToElements(String html) {
        return convertToElements(html, null);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to a {@link List} of
     * iText objects ({@link IElement} instances).
     *
     * @param htmlStream the {@link InputStream} with the source HTML
     * @return a list of iText building blocks
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static List<IElement> convertToElements(InputStream htmlStream) throws IOException {
        return convertToElements(htmlStream, null);
    }

    /**
     * Converts a {@link String} containing HTML to a {@link List} of
     * iText objects ({@link IElement} instances), using specific
     * {@link ConverterProperties}.
     *
     * @param html                the html in the form of a {@link String}
     * @param converterProperties a {@link ConverterProperties} instance
     * @return a list of iText building blocks
     */
    public static List<IElement> convertToElements(String html, ConverterProperties converterProperties) {
        converterProperties = setupCustomConversionProperties(null, converterProperties);
        IXmlParser parser = new JsoupHtmlParser();
        IDocumentNode doc = parser.parse(html);
        return Attacher.attach(doc, converterProperties);
    }

    /**
     * Converts HTML obtained from an {@link InputStream} to a {@link List} of
     * iText objects ({@link IElement} instances), using specific
     * {@link ConverterProperties}.
     *
     * @param htmlStream          the {@link InputStream} with the source HTML
     * @param converterProperties a {@link ConverterProperties} instance
     * @return a list of iText building blocks
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static List<IElement> convertToElements(InputStream htmlStream, ConverterProperties converterProperties)
            throws IOException {
        converterProperties = setupCustomConversionProperties(null, converterProperties);
        IXmlParser parser = new JsoupHtmlParser();
        IDocumentNode doc = parser.parse(htmlStream, converterProperties.getCharset());
        return Attacher.attach(doc, converterProperties);
    }

    static IMetaInfo createPdf2HtmlMetaInfo() {
        return new HtmlMetaInfo();
    }

    private static PdfDocument createCorrectDocument(ConverterProperties converterProps,
                                                     DocumentProperties documentProps, PdfWriter pdfWriter) {
        if (converterProps != null) {
            if (converterProps.getPdfUaConformance() != null && converterProps.getPdfAConformance() != null) {
                throw new Html2PdfException(
                        Html2PdfLogMessageConstant.PDF_A_AND_PDF_UA_CONFORMANCE_CANNOT_BE_USED_TOGETHER);
            }
            if (converterProps.getPdfAConformance() != null) {
                return new PdfADocument(pdfWriter, converterProps.getPdfAConformance(),
                        converterProps.getDocumentOutputIntent(), documentProps);

            }
            if (converterProps.getPdfUaConformance() != null) {
                return new PdfUADocument(pdfWriter, documentProps,
                        new PdfUAConfig(converterProps.getPdfUaConformance(), "Title", "en"));
            }
        }
        return new PdfDocument(pdfWriter, documentProps);


    }

    private static IMetaInfo resolveMetaInfo(ConverterProperties converterProperties) {
        return converterProperties == null
                ? createPdf2HtmlMetaInfo()
                : converterProperties.getEventMetaInfo();
    }

    private static ConverterProperties setupCustomConversionProperties(PdfDocument document,
                                                                       ConverterProperties properties) {

        if (properties == null) {
            properties = new ConverterProperties();
        }
        final boolean needsForcedEmbeddedFonts = document instanceof PdfADocument
                || document instanceof PdfUADocument
                || properties.getPdfUaConformance() != null
                || properties.getPdfAConformance() != null;


        if (needsForcedEmbeddedFonts && properties.getFontProvider() == null) {
            final BasicFontProvider embeddedFontProvider = new BasicFontProvider(false, true, false);
            properties.setFontProvider(embeddedFontProvider);
        }
        if ((document instanceof PdfUADocument || properties.getPdfUaConformance() != null)
                && properties.getOutlineHandler() == null) {
            properties.setOutlineHandler(OutlineHandler.createStandardHandler());
        }
        if (properties.getPdfAConformance() != null && "A".equals(properties.getPdfAConformance().getLevel())) {
            document.setTagged();
        }

        return properties;
    }

    private static class HtmlMetaInfo implements IMetaInfo {
    }
}
