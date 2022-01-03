/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.ContextMappingHelper;
import com.itextpdf.html2pdf.util.SvgProcessingUtil;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.exceptions.SvgExceptionMessageConstant;
import com.itextpdf.svg.processors.ISvgConverterProperties;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.SvgTagSvgNodeRenderer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// TODO: DEVSIX-5968 Add new tests in HtmlResourceResolverTest
@Category(IntegrationTest.class)
public class HtmlResourceResolverTest extends ExtendedITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/resolver/resource/HtmlResourceResolverTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/resolver/resource/HtmlResourceResolverTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void resourceResolverHtmlWithSvgTest01() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverHtmlWithSvgTest01.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverHtmlWithSvgTest01.pdf";

        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "resourceResolverHtmlWithSvgTest01.html"),
                new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 2)
    })
    public void resourceResolverHtmlWithSvgTest02() throws IOException, InterruptedException {
        String baseUri = SOURCE_FOLDER + "%23r%e%2525s@o%25urces/";
        String outPdf = DESTINATION_FOLDER + "resourceResolverHtmlWithSvgTest02.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverHtmlWithSvgTest02.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverHtmlWithSvgTest02.html", outPdf, cmpPdf, baseUri);
    }

    @Test
    public void resourceResolverTest07() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverTest07.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverTest07.pdf";

        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "resourceResolverTest07.html"), new File(outPdf));

        Assert.assertNull(new CompareTool().compareByContent(
                outPdf, cmpPdf, DESTINATION_FOLDER, "diff07_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG))
    public void resourceResolverTest07A() throws IOException, InterruptedException {
        String baseUri = SOURCE_FOLDER + "%23r%e%2525s@o%25urces/";
        String outPdf = DESTINATION_FOLDER + "resourceResolverTest07A.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverTest07A.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverTest07A.html", outPdf, cmpPdf, baseUri);
    }

    @Test
    public void resourceResolverTest07B() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverTest07B.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverTest07B.pdf";

        HtmlConverter.convertToPdf(new File(
                SOURCE_FOLDER + "#r%e%25s@o%urces/resourceResolverTest07B.html"), new File(outPdf));

        Assert.assertNull(new CompareTool().compareByContent(
                outPdf, cmpPdf, DESTINATION_FOLDER, "diff07B_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG))
    public void resourceResolverTest07C() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverTest07C.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverTest07C.pdf";

        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "#r%e%25s@o%urces/resourceResolverTest07C.html"),
                new File(outPdf), new ConverterProperties().setBaseUri(SOURCE_FOLDER + "#r%e%25s@o%urces/.."));

        Assert.assertNull(new CompareTool().compareByContent(
                outPdf, cmpPdf, DESTINATION_FOLDER, "diff07C_"));
    }

    @Test
    public void resourceResolverHtmlWithSvgTest03() throws IOException, InterruptedException {
        String baseUri = SOURCE_FOLDER + "%23r%e%2525s@o%25urces/";
        String outPdf = DESTINATION_FOLDER + "resourceResolverHtmlWithSvgTest03.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverHtmlWithSvgTest03.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverHtmlWithSvgTest03.html", outPdf, cmpPdf, baseUri);
    }

    @Test
    public void resourceResolverHtmlWithSvgTest04() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverHtmlWithSvgTest04.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverHtmlWithSvgTest04.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverHtmlWithSvgTest04.html", outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    //TODO: update after DEVSIX-2239 fix
    public void resourceResolverCssWithSvg() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverCssWithSvg.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverCssWithSvg.pdf";

        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "resourceResolverCssWithSvg.html"),
                new File(outPdf));

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2)
    })
    public void resourceResolverHtmlWithSvgDifferentLevels() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverHtmlWithSvgDifferentLevels.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverHtmlWithSvgDifferentLevels.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverHtmlWithSvgDifferentLevels.html",
                outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void attemptToProcessBySvgProcessingUtilSvgWithImageTest() {
        // TODO review this test in the scope of DEVSIX-4107
        String fileName = "svgWithImage.svg";
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        HtmlResourceResolver resourceResolver = new HtmlResourceResolver(SOURCE_FOLDER, context);

        ISvgConverterProperties svgConverterProperties = ContextMappingHelper.mapToSvgConverterProperties(context);
        ISvgProcessorResult res = SvgConverter.parseAndProcess(resourceResolver.retrieveResourceAsInputStream(fileName), svgConverterProperties);
        ISvgNodeRenderer imageRenderer = ((SvgTagSvgNodeRenderer) res.getRootRenderer()).getChildren().get(0);
        // Remove the previous result of the resource resolving in order to demonstrate that the resource will not be
        // resolved due to not setting of baseUri in the SvgProcessingUtil#createXObjectFromProcessingResult method.
        imageRenderer.setAttribute(SvgConstants.Attributes.XLINK_HREF, "doggo.jpg");

        SvgProcessingUtil processingUtil = new SvgProcessingUtil(resourceResolver);
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        PdfFormXObject pdfFormXObject = processingUtil.createXObjectFromProcessingResult(res, pdfDocument);
        PdfDictionary resources = (PdfDictionary) pdfFormXObject.getResources().getPdfObject().get(PdfName.XObject);
        PdfDictionary fm1Dict = (PdfDictionary) resources.get(new PdfName("Fm1"));
        Assert.assertFalse(((PdfDictionary) fm1Dict.get(PdfName.Resources)).containsKey(PdfName.XObject));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void attemptToProcessBySvgProcessingUtilSvgWithSvgTest() {
        // TODO review this test in the scope of DEVSIX-4107
        String fileName = "svgWithSvg.svg";
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        HtmlResourceResolver resourceResolver = new HtmlResourceResolver(SOURCE_FOLDER, context);

        ISvgConverterProperties svgConverterProperties = ContextMappingHelper.mapToSvgConverterProperties(context);
        ISvgProcessorResult res = SvgConverter
                .parseAndProcess(resourceResolver.retrieveResourceAsInputStream(fileName), svgConverterProperties);
        ISvgNodeRenderer imageRenderer = ((SvgTagSvgNodeRenderer) res.getRootRenderer()).getChildren().get(1);
        // Remove the previous result of the resource resolving in order to demonstrate that the resource will not be
        // resolved due to not setting of baseUri in the SvgProcessingUtil#createXObjectFromProcessingResult method.
        // But even if set baseUri in the SvgProcessingUtil#createXObjectFromProcessingResult method, the SVG will not
        // be processed, because in the createXObjectFromProcessingResult method we create ResourceResolver, not HtmlResourceResolver.
        imageRenderer.setAttribute(SvgConstants.Attributes.XLINK_HREF, "res\\itextpdf.com\\lines.svg");

        SvgProcessingUtil processingUtil = new SvgProcessingUtil(resourceResolver);
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        PdfFormXObject pdfFormXObject = processingUtil.createXObjectFromProcessingResult(res, pdfDocument);
        PdfDictionary resources = (PdfDictionary) pdfFormXObject.getResources().getPdfObject().get(PdfName.XObject);
        PdfDictionary fm1Dict = (PdfDictionary) resources.get(new PdfName("Fm1"));
        Assert.assertFalse(((PdfDictionary) fm1Dict.get(PdfName.Resources)).containsKey(PdfName.XObject));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void resourceResolverSvgEmbeddedSvg() throws IOException, InterruptedException {
        // TODO review this test in the scope of DEVSIX-4107
        String outPdf = DESTINATION_FOLDER + "resourceResolverSvgEmbeddedSvg.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverSvgEmbeddedSvg.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverSvgEmbeddedSvg.html", outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void resourceResolverObjectWithSvgEmbeddedSvg() throws IOException, InterruptedException {
        // TODO review this test in the scope of DEVSIX-4107
        String outPdf = DESTINATION_FOLDER + "resourceResolverObjectWithSvgEmbeddedSvg.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverObjectWithSvgEmbeddedSvg.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverObjectWithSvgEmbeddedSvg.html",
                outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    // TODO DEVSIX-1595
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG))
    public void resourceResolverTest11() throws IOException, InterruptedException {
        String baseUri = "https://en.wikipedia.org/wiki/Welsh_Corgi";
        String outPdf = DESTINATION_FOLDER + "resourceResolverTest11.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverTest11.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverTest11.html", outPdf, cmpPdf, baseUri);
    }

    @Test
    public void resourceResolverSvgWithImageInlineTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverSvgWithImageInline.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverSvgWithImageInline.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverSvgWithImageInline.html", outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    public void resourceResolverSvgWithImageBackgroundTest() throws IOException, InterruptedException {
        //Browsers do not render this
        String outPdf = DESTINATION_FOLDER + "resourceResolverSvgWithImageBackground.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverSvgWithImageBackground.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverSvgWithImageBackground.html",
                outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    public void resourceResolverSvgWithImageObjectTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverSvgWithImageObject.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverSvgWithImageObject.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverSvgWithImageObject.html", outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_DATA_URI, count = 3),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 3)
    })
    public void resourceResolverSvgDifferentFormatsTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverSvgDifferentFormats.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverSvgDifferentFormats.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverSvgDifferentFormats.html",
                outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_DATA_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)
    })
    public void resourceResolverNotValidInlineSvgTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverNotValidInlineSvg.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverNotValidInlineSvg.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverNotValidInlineSvg.html",
                outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = SvgExceptionMessageConstant.NO_ROOT),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)})
    public void resourceResolverIncorrectSyntaxTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverIncorrectSyntaxObject.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverIncorrectSyntaxObject.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverIncorrectSyntaxObject.html", outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    public void resourceResolverLinkBaseRefTest() throws IOException, InterruptedException {
        String baseUri = SOURCE_FOLDER + "img/";
        String outPdf = DESTINATION_FOLDER + "resourceResolverLinkBaseRef.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverLinkBaseRef.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverLinkBaseRef.html", outPdf, cmpPdf, baseUri);
    }

    @Test
    public void resourceResolverLinkDirectRefTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "resourceResolverLinkDirectRef.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_resourceResolverLinkDirectRef.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "resourceResolverLinkDirectRef.html",
                outPdf, cmpPdf, SOURCE_FOLDER);
    }

    @Test
    public void convertToPdfWithRelativeBaseUriTest() throws IOException, InterruptedException {
        String baseUri = SOURCE_FOLDER + "textWithStyleAndImage.html";
        String outPdf = DESTINATION_FOLDER + "convertToPdfWithRelativeBaseUriTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_convertToPdfWithRelativeBaseUriTest.pdf";

        convertHtmlFileToPdf(SOURCE_FOLDER + "textWithStyleAndImage.html", outPdf, cmpPdf,
                new ConverterProperties().setBaseUri(baseUri));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)})
    public void convertToPdfWithInvalidBaseUriTest() throws IOException, InterruptedException {
        String invalidBaseUri = "/folderInDiskRoot";
        String outPdf = DESTINATION_FOLDER + "convertToPdfWithInvalidBaseUriTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_convertToPdfWithInvalidBaseUriTest.pdf";

        convertHtmlFileToPdf(SOURCE_FOLDER + "textWithStyleAndImage.html", outPdf, cmpPdf,
                new ConverterProperties().setBaseUri(invalidBaseUri));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)})
    public void convertInputStreamToPdfWithoutBaseUriTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "convertInputStreamToPdfWithoutBaseUriTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_convertInputStreamToPdfWithoutBaseUriTest.pdf";

        convertHtmlStreamToPdf(SOURCE_FOLDER + "textWithStyleAndImage.html", outPdf, cmpPdf, null);
    }

    @Test
    public void convertFileToPdfWithoutBaseUriTest() throws IOException, InterruptedException {
        String html = SOURCE_FOLDER + "textWithStyleAndImage.html";
        String outPdf = DESTINATION_FOLDER + "convertFileToPdfWithoutBaseUriTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_convertFileToPdfWithoutBaseUriTest.pdf";

        convertHtmlFileToPdf(html, outPdf, cmpPdf, null);
    }

    @Test
    public void convertToPdfWithAbsoluteBaseUriTest() throws IOException, InterruptedException {
        String baseUri = PathUtil.getAbsolutePathToResourcesForHtmlResourceResolverTest();
        String outPdf = DESTINATION_FOLDER + "convertToPdfWithAbsoluteBaseUriTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_convertToPdfWithAbsoluteBaseUriTest.pdf";

        convertHtmlFileToPdf(SOURCE_FOLDER + "textWithStyleAndImageWithIncompletePath.html", outPdf, cmpPdf,
                new ConverterProperties().setBaseUri(baseUri));
    }

    @Test
    public void convertToPdfWithBaseUriFromUriTest() throws IOException, InterruptedException {
        String baseUri = PathUtil.getUriToResourcesForHtmlResourceResolverTest();
        String outPdf = DESTINATION_FOLDER + "convertToPdfWithBaseUriFromUriTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_convertToPdfWithBaseUriFromUriTest.pdf";

        convertHtmlFileToPdf(SOURCE_FOLDER + "textWithStyleAndImageWithIncompletePath.html", outPdf, cmpPdf,
                new ConverterProperties().setBaseUri(baseUri));
    }

    private void convertHtmlStreamToPdf(String htmlPath, String outPdf, String cmpPdf, String baseUri)
            throws IOException, InterruptedException {
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");

        try (FileInputStream fileInputStream = new FileInputStream(htmlPath);
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream,
                    new ConverterProperties().setBaseUri(baseUri));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER));
    }

    private void convertHtmlFileToPdf(String htmlPath, String outPdf, String cmpPdf, ConverterProperties converterProperties)
            throws IOException, InterruptedException {

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");

        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdf), converterProperties);
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER));
    }
}
