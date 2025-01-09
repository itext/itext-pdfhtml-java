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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultHtmlProcessor;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.tags.HtmlTagWorker;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.styledxmlparser.IXmlParser;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupHtmlParser;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.FileInputStream;
import java.io.IOException;

@Tag("IntegrationTest")
public class TargetCounterTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/TargetCounterTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/TargetCounterTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void targetCounterPageUrlNameTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterPageUrlName");
    }

    @Test
    public void targetCounterPageUrlIdTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterPageUrlId");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.EXCEEDED_THE_MAXIMUM_NUMBER_OF_RELAYOUTS))
    public void targetCounterManyRelayoutsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterManyRelayouts");
    }

    @Test
    public void targetCounterPageBigElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterPageBigElement");
    }

    @Test
    public void targetCounterPageAllTagsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterPageAllTags");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CANNOT_RESOLVE_TARGET_COUNTER_VALUE, count = 2))
    public void targetCounterNotExistingTargetTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterNotExistingTarget");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void pageTargetCounterTestWithLogMessageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pageTargetCounterTestWithLogMessage");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    // There should be only one log message here, but we have two because we resolve css styles twice.
    public void nonPageTargetCounterTestWithLogMessageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nonPageTargetCounterTestWithLogMessage");
    }

    @Test
    public void targetCounterSeveralCountersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterSeveralCounters");
    }

    @Test
    public void targetCounterIDNotExistTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterIDNotExist");
    }

    @Test
    public void targetCounterNotCounterElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterNotCounterElement");
    }

    @Test
    public void targetCounterNestedCountersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterNestedCounters");
    }

    @Test
    public void targetCounterUnusualStylesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterUnusualStyles");
    }

    @Test
    public void targetCountersNestedCountersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCountersNestedCounters");
    }

    @Test
    // TODO DEVSIX-4789 Armenian and Georgian symbols are not drawn, but there is no log message.
    public void targetCounterNotDefaultStyleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterNotDefaultStyle");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.EXCEEDED_THE_MAXIMUM_NUMBER_OF_RELAYOUTS))
    public void targetCounterCannotBeResolvedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("targetCounterCannotBeResolved");
    }


    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CUSTOM_RENDERER_IS_SET_FOR_HTML_DOCUMENT))
    public void customRendererAndPageTargetCounterTest() throws IOException, InterruptedException {
        convertToPdfWithCustomRendererAndCompare("customRendererAndPageTargetCounter");
    }

    @Test
    public void customRendererAndNonPageTargetCounterTest() throws IOException, InterruptedException {
        convertToPdfWithCustomRendererAndCompare("customRendererAndNonPageTargetCounter");
    }

    private void convertToPdfWithCustomRendererAndCompare(String name) throws IOException, InterruptedException {
        ConverterProperties properties = new ConverterProperties().setTagWorkerFactory(new DefaultTagWorkerFactory() {
            @Override
            public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
                if (TagConstants.HTML.equals(tag.name())) {
                    return new ITagWorker() {
                        private HtmlTagWorker htmlTagWorker = new HtmlTagWorker(tag, context);

                        @Override
                        public void processEnd(IElementNode element, ProcessorContext context) {
                            htmlTagWorker.processEnd(element, context);
                        }

                        @Override
                        public boolean processContent(String content, ProcessorContext context) {
                            return htmlTagWorker.processContent(content, context);
                        }

                        @Override
                        public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
                            return htmlTagWorker.processTagChild(childTagWorker, context);
                        }

                        @Override
                        public IPropertyContainer getElementResult() {
                            Document document = (Document) htmlTagWorker.getElementResult();
                            document.setRenderer(new DocumentRenderer(document));
                            return document;
                        }
                    };
                }
                return null;
            }
        });
        DefaultHtmlProcessor processor = new DefaultHtmlProcessor(properties);

        IXmlParser parser = new JsoupHtmlParser();
        String outPdfPath = destinationFolder + name + ".pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outPdfPath));
        IDocumentNode doc = parser.parse(new FileInputStream(sourceFolder + name + ".html"),
                properties.getCharset());
        Document document = processor.processDocument(doc, pdfDocument);
        document.close();

        Assertions.assertNull(new CompareTool().compareByContent(outPdfPath,
                sourceFolder + "cmp_" + name + ".pdf", destinationFolder));
    }

    private void convertToPdfAndCompare(String name) throws IOException, InterruptedException {
        convertToPdfAndCompare(name, sourceFolder, destinationFolder);
    }
}
