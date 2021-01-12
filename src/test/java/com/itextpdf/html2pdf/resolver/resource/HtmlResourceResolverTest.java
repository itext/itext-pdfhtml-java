/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.utils.CompareTool;
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

@Category(IntegrationTest.class)
public class HtmlResourceResolverTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/resolver/resource/HtmlResourceResolverTest/";

    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/resolver/resource/HtmlResourceResolverTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void resourceResolverHtmlWithSvgTest01() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest01.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest01.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverHtmlWithSvgTest01.html"),
                new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 2)
    })
    public void resourceResolverHtmlWithSvgTest02() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "%23r%e%2525s@o%25urces/";

        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest02.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest02.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverHtmlWithSvgTest02.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void resourceResolverTest07() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest07.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverTest07.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest07A() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "%23r%e%2525s@o%25urces/";

        String outPdf = destinationFolder + "resourceResolverTest07A.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07A.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest07A.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07A_"));
    }

    @Test
    public void resourceResolverTest07B() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest07B.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07B.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "#r%e%25s@o%urces/resourceResolverTest07B.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07B_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest07C() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest07C.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07C.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "#r%e%25s@o%urces/resourceResolverTest07C.html"), new File(outPdf),
                new ConverterProperties().setBaseUri(sourceFolder + "#r%e%25s@o%urces/.."));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07C_"));
    }

    @Test
    public void resourceResolverHtmlWithSvgTest03() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "%23r%e%2525s@o%25urces/";

        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest03.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest03.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverHtmlWithSvgTest03.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void resourceResolverHtmlWithSvgTest04() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest04.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest04.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverHtmlWithSvgTest04.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    //TODO: update after DEVSIX-2239 fix
    public void resourceResolverCssWithSvg() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverCssWithSvg.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverCssWithSvg.pdf";

        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverCssWithSvg.html"),
                new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser
                    .LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser
                    .LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2)
    })
    public void resourceResolverHtmlWithSvgDifferentLevels() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgDifferentLevels.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgDifferentLevels.pdf";

        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverHtmlWithSvgDifferentLevels.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(sourceFolder));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void attemptToProcessBySvgProcessingUtilSvgWithImageTest() {
        // TODO review this test in the scope of DEVSIX-4107
        String fileName = "svgWithImage.svg";
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        HtmlResourceResolver resourceResolver = new HtmlResourceResolver(sourceFolder, context);

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
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void attemptToProcessBySvgProcessingUtilSvgWithSvgTest() {
        // TODO review this test in the scope of DEVSIX-4107
        String fileName = "svgWithSvg.svg";
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        HtmlResourceResolver resourceResolver = new HtmlResourceResolver(sourceFolder, context);

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
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void resourceResolverSvgEmbeddedSvg() throws IOException, InterruptedException {
        // TODO review this test in the scope of DEVSIX-4107
        String baseUri = sourceFolder;

        String outPdf = destinationFolder + "resourceResolverSvgEmbeddedSvg.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgEmbeddedSvg.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverSvgEmbeddedSvg.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffEmbeddedSvg_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void resourceResolverObjectWithSvgEmbeddedSvg() throws IOException, InterruptedException {
        // TODO review this test in the scope of DEVSIX-4107
        String baseUri = sourceFolder;

        String outPdf = destinationFolder + "resourceResolverObjectWithSvgEmbeddedSvg.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverObjectWithSvgEmbeddedSvg.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverObjectWithSvgEmbeddedSvg.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffObjectWithSvg_"));
    }

    @Test
    // TODO DEVSIX-1595
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG))
    public void resourceResolverTest11() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest11.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest11.pdf";

        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest11.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream,
                    new ConverterProperties().setBaseUri("https://en.wikipedia.org/wiki/Welsh_Corgi"));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void resourceResolverSvgWithImageInlineTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverSvgWithImageInline.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgWithImageInline.pdf";

        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverSvgWithImageInline.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(sourceFolder));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void resourceResolverSvgWithImageBackgroundTest() throws IOException, InterruptedException {
        //Browsers do not render this
        String outPdf = destinationFolder + "resourceResolverSvgWithImageBackground.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgWithImageBackground.pdf";

        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverSvgWithImageBackground.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(sourceFolder));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void resourceResolverSvgWithImageObjectTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverSvgWithImageObject.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgWithImageObject.pdf";

        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverSvgWithImageObject.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream,
                            new ConverterProperties().setBaseUri(sourceFolder));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_DATA_URI, count = 3),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 3)
    })
    public void resourceResolverSvgDifferentFormatsTest() throws IOException, InterruptedException {
        String html = sourceFolder + "resourceResolverSvgDifferentFormats.html";
        String outPdf = destinationFolder + "resourceResolverSvgDifferentFormats.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgDifferentFormats.pdf";
        try (
                FileInputStream htmlInput = new FileInputStream(html);
                FileOutputStream pdfOutput = new FileOutputStream(outPdf)
        ) {
            HtmlConverter.convertToPdf(htmlInput, pdfOutput, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffCorruptedSvg_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_DATA_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)
    })
    public void resourceResolverNotValidInlineSvgTest() throws IOException, InterruptedException {
        String html = sourceFolder + "resourceResolverNotValidInlineSvg.html";
        String outPdf = destinationFolder + "resourceResolverNotValidInlineSvg.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverNotValidInlineSvg.pdf";
        try (
                FileInputStream htmlInput = new FileInputStream(html);
                FileOutputStream pdfOutput = new FileOutputStream(outPdf)
        ) {
            HtmlConverter.convertToPdf(htmlInput, pdfOutput, new ConverterProperties().setBaseUri(sourceFolder));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffCorruptedSvg_"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = SvgExceptionMessageConstant.NO_ROOT),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)})
    public void resourceResolverIncorrectSyntaxTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverIncorrectSyntaxObject.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverIncorrectSyntaxObject.pdf";

        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverIncorrectSyntaxObject.html");
                FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(sourceFolder));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void resourceResolverLinkBaseRefTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverLinkBaseRef.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverLinkBaseRef.pdf";
        String baseUri = sourceFolder + "img/";

        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverLinkBaseRef.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void resourceResolverLinkDirectRefTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverLinkDirectRef.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverLinkDirectRef.pdf";

        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverLinkDirectRef.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter
                    .convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(sourceFolder));
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }


    // TODO test with absolute http links for resources?
    // TODO test with http base URI?
}
