/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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
package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.attach.Attacher;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.css.resolve.DefaultCssResolver;
import com.itextpdf.html2pdf.html.IHtmlParser;
import com.itextpdf.html2pdf.html.impl.jsoup.JsoupHtmlParser;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.kernel.Version;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class HtmlConverter {

    private HtmlConverter() {
    }

    // TODO add overloads with Charset provided
    // TODO add overloads without automatic elements flushing

    public static void convertToPdf(String html, OutputStream pdfStream) throws IOException {
        convertToPdf(html, pdfStream, "");
    }

    public static void convertToPdf(String html, OutputStream pdfStream, String baseUri) throws IOException {
        convertToPdf(html, new PdfWriter(pdfStream), baseUri);
    }

    public static void convertToPdf(String html, PdfWriter pdfWriter) throws IOException {
        convertToPdf(html, pdfWriter, "");
    }

    public static void convertToPdf(String html, PdfWriter pdfWriter, String baseUri) throws IOException {
        InputStream stream = new ByteArrayInputStream(html.getBytes());
        convertToPdf(stream, pdfWriter, baseUri);
    }

    public static void convertToPdf(File htmlFile, File pdfFile) throws IOException {
        convertToPdf(htmlFile, pdfFile, MediaDeviceDescription.createDefault());
    }

    public static void convertToPdf(File htmlFile, File pdfFile, MediaDeviceDescription deviceDescription) throws IOException {
        convertToPdf(new FileInputStream(htmlFile.getAbsolutePath()), new FileOutputStream(pdfFile.getAbsolutePath()), htmlFile.getParent() + File.separator, deviceDescription);
    }

    public static void convertToPdf(InputStream htmlStream, OutputStream pdfStream) throws IOException {
        convertToPdf(htmlStream, pdfStream, "");
    }

    public static void convertToPdf(InputStream htmlStream, OutputStream pdfStream, String baseUri) throws IOException {
        convertToPdf(htmlStream, pdfStream, baseUri, MediaDeviceDescription.createDefault());
    }

    public static void convertToPdf(InputStream htmlStream, OutputStream pdfStream, String baseUri, MediaDeviceDescription deviceDescription) throws IOException {
        convertToPdf(htmlStream, new PdfWriter(pdfStream), baseUri, deviceDescription);
    }

    public static void convertToPdf(InputStream htmlStream, PdfWriter pdfWriter) throws IOException {
        convertToPdf(htmlStream, new PdfDocument(pdfWriter));
    }

    public static void convertToPdf(InputStream htmlStream, PdfDocument pdfDocument) throws IOException {
        convertToPdf(htmlStream, pdfDocument, "");
    }

    public static void convertToPdf(InputStream htmlStream, PdfWriter pdfWriter, String baseUri) throws IOException {
        convertToPdf(htmlStream, new PdfDocument(pdfWriter), baseUri);
    }

    public static void convertToPdf(InputStream htmlStream, PdfDocument pdfDocument, String baseUri) throws IOException {
        convertToPdf(htmlStream, pdfDocument, baseUri, MediaDeviceDescription.createDefault());
    }

    public static void convertToPdf(InputStream htmlStream, PdfWriter pdfWriter, String baseUri, MediaDeviceDescription deviceDescription) throws IOException {
        convertToPdf(htmlStream, new PdfDocument(pdfWriter), baseUri, deviceDescription);
    }

    public static void convertToPdf(InputStream htmlStream, PdfDocument pdfDocument, String baseUri, MediaDeviceDescription deviceDescription) throws IOException {
        Document document = convertToDocument(htmlStream, pdfDocument, baseUri, deviceDescription);
        document.close();
    }

    public static Document convertToDocument(InputStream htmlStream, PdfWriter pdfWriter) throws IOException {
        return convertToDocument(htmlStream, pdfWriter, "");
    }

    public static Document convertToDocument(InputStream htmlStream, PdfWriter pdfWriter, String baseUri) throws IOException {
        return convertToDocument(htmlStream, pdfWriter, baseUri, MediaDeviceDescription.createDefault());
    }

    public static Document convertToDocument(InputStream htmlStream, PdfWriter pdfWriter, String baseUri, MediaDeviceDescription deviceDescription) throws IOException {
        return convertToDocument(htmlStream, new PdfDocument(pdfWriter), baseUri, deviceDescription);
    }

    public static Document convertToDocument(InputStream htmlStream, PdfDocument pdfDocument, String baseUri, MediaDeviceDescription deviceDescription) throws IOException {
        String licenseKeyClassName = "com.itextpdf.licensekey.LicenseKey";
        String licenseKeyProductClassName = "com.itextpdf.licensekey.LicenseKeyProduct";
        String licenseKeyFeatureClassName = "com.itextpdf.licensekey.LicenseKeyProductFeature";
        String checkLicenseKeyMethodName = "scheduledCheck";

        try {
            Class licenseKeyClass = Class.forName(licenseKeyClassName);
            Class licenseKeyProductClass = Class.forName(licenseKeyProductClassName);
            Class licenseKeyProductFeatureClass = Class.forName(licenseKeyFeatureClassName);

            Object licenseKeyProductFeatureArray = Array.newInstance(licenseKeyProductFeatureClass, 0);

            Class[] params = new Class[] {
                    String.class,
                    Integer.TYPE,
                    Integer.TYPE,
                    licenseKeyProductFeatureArray.getClass()
            };

            Constructor licenseKeyProductConstructor = licenseKeyProductClass.getConstructor(params);

            Object licenseKeyProductObject = licenseKeyProductConstructor.newInstance(
                    Html2PdfProductInfo.PRODUCT_NAME,
                    Html2PdfProductInfo.MAJOR_VERSION,
                    Html2PdfProductInfo.MINOR_VERSION,
                    licenseKeyProductFeatureArray
            );

            Method method = licenseKeyClass.getMethod(checkLicenseKeyMethodName, licenseKeyProductClass);
            method.invoke(null, licenseKeyProductObject);
        } catch (Exception e) {
            if ( ! Version.isAGPLVersion() ) {
                throw new RuntimeException(e.getCause());
            }
        }

        if (pdfDocument.getReader() != null) {
            throw new Html2PdfException(Html2PdfException.PdfDocumentShouldBeInWritingMode);
        }
        IHtmlParser parser = new JsoupHtmlParser();
        String detectedCharset = detectEncoding(htmlStream);
        IDocumentNode doc = parser.parse(htmlStream, detectedCharset);
        ResourceResolver resourceResolver = new ResourceResolver(baseUri);
        ICssResolver cssResolver = new DefaultCssResolver(doc, deviceDescription, resourceResolver);
        Document document = Attacher.attach(doc, cssResolver, pdfDocument, resourceResolver);
        return document;
    }

    public static List<IElement> convertToElements(String html) {
        return convertToElements(html, "");
    }

    public static List<IElement> convertToElements(String html, String baseUri) {
        return convertToElements(html, MediaDeviceDescription.createDefault(), baseUri);
    }

    public static List<IElement> convertToElements(String html, MediaDeviceDescription deviceDescription) {
        return convertToElements(html, deviceDescription, "");
    }

    public static List<IElement> convertToElements(String html, MediaDeviceDescription deviceDescription, String baseUri) {
        String licenseKeyClassName = "com.itextpdf.licensekey.LicenseKey";
        String licenseKeyProductClassName = "com.itextpdf.licensekey.LicenseKeyProduct";
        String licenseKeyFeatureClassName = "com.itextpdf.licensekey.LicenseKeyProductFeature";
        String checkLicenseKeyMethodName = "scheduledCheck";

        try {
            Class licenseKeyClass = Class.forName(licenseKeyClassName);
            Class licenseKeyProductClass = Class.forName(licenseKeyProductClassName);
            Class licenseKeyProductFeatureClass = Class.forName(licenseKeyFeatureClassName);

            Object licenseKeyProductFeatureArray = Array.newInstance(licenseKeyProductFeatureClass, 0);

            Class[] params = new Class[] {
                    String.class,
                    Integer.TYPE,
                    Integer.TYPE,
                    licenseKeyProductFeatureArray.getClass()
            };

            Constructor licenseKeyProductConstructor = licenseKeyProductClass.getConstructor(params);

            Object licenseKeyProductObject = licenseKeyProductConstructor.newInstance(
                    Html2PdfProductInfo.PRODUCT_NAME,
                    Html2PdfProductInfo.MAJOR_VERSION,
                    Html2PdfProductInfo.MINOR_VERSION,
                    licenseKeyProductFeatureArray
            );

            Method method = licenseKeyClass.getMethod(checkLicenseKeyMethodName, licenseKeyProductClass);
            method.invoke(null, licenseKeyProductObject);
        } catch (Exception e) {
            if ( ! Version.isAGPLVersion() ) {
                throw new RuntimeException(e.getCause());
            }
        }

        IHtmlParser parser = new JsoupHtmlParser();
        IDocumentNode doc = parser.parse(html);
        ResourceResolver resourceResolver = new ResourceResolver(baseUri);
        ICssResolver cssResolver = new DefaultCssResolver(doc, deviceDescription, resourceResolver);
        return Attacher.attach(doc, cssResolver, resourceResolver);
    }

    // TODO
    private static String detectEncoding(final InputStream in) {
        return "UTF-8";
    }

}
