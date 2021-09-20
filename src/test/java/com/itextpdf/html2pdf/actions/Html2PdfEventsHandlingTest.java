/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.actions;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.actions.events.PdfHtmlProductEvent;
import com.itextpdf.html2pdf.actions.events.PdfHtmlTestProductEvent;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.EventManager;
import com.itextpdf.commons.actions.IBaseEvent;
import com.itextpdf.commons.actions.IBaseEventHandler;
import com.itextpdf.commons.actions.ProductNameConstant;
import com.itextpdf.commons.actions.confirmations.ConfirmEvent;
import com.itextpdf.commons.actions.confirmations.ConfirmedEventWrapper;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;
import com.itextpdf.commons.actions.processors.DefaultITextProductEventProcessor;
import com.itextpdf.commons.actions.producer.ProducerBuilder;
import com.itextpdf.commons.actions.sequence.SequenceId;
import com.itextpdf.commons.actions.contexts.IMetaInfo;
import com.itextpdf.kernel.actions.events.ITextCoreProductEvent;
import com.itextpdf.kernel.logs.KernelLogMessageConstant;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class Html2PdfEventsHandlingTest extends ExtendedITextTest {
    private static final TestConfigurationEvent CONFIGURATION_ACCESS = new TestConfigurationEvent();
    private static StoreEventsHandler handler;

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/actions/Html2PdfEventsHandlingTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/actions/Html2PdfEventsHandlingTest/";

    @Before
    public void setUpHandler() {
        handler = new StoreEventsHandler();
        EventManager.getInstance().register(handler);
    }

    @After
    public void resetHandler() {
        EventManager.getInstance().unregister(handler);
        handler = null;
    }

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void twoDifferentElementsToOneDocumentTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SequenceId docSequenceId;
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos));
                Document document = new Document(pdfDocument)) {
            docSequenceId = pdfDocument.getDocumentIdWrapper();
            String firstHtml = "<p>Hello world first!</p>";
            List<IElement> lstFirst = HtmlConverter.convertToElements(firstHtml);

            addElementsToDocument(document, lstFirst);

            String secondHtml = "<p>Hello world second!</p>";
            List<IElement> lstSecond = HtmlConverter.convertToElements(secondHtml);

            addElementsToDocument(document, lstSecond);
        }

        List<AbstractProductProcessITextEvent> events = CONFIGURATION_ACCESS.getPublicEvents(docSequenceId);
        // Confirmed 3 events, but only 2 events (1 core + 1 pdfHtml) will be reported because
        // ReportingHandler don't report similar events for one sequenceId
        Assert.assertEquals(3, events.size());

        Assert.assertTrue(events.get(0) instanceof ConfirmedEventWrapper);
        ConfirmedEventWrapper confirmedEventWrapper = (ConfirmedEventWrapper) events.get(0);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof ITextCoreProductEvent);
        Assert.assertEquals(ITextCoreProductEvent.PROCESS_PDF, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(1) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(1);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(2) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(2);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())))) {
            String expectedProdLine = createExpectedProducerLine(
                    new ConfirmedEventWrapper[] {getCoreEvent(), getPdfHtmlEvent()});
            Assert.assertEquals(expectedProdLine, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    @Test
    public void setOfElementsToOneDocumentTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SequenceId docSequenceId;
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos));
                Document document = new Document(pdfDocument)) {
            docSequenceId = pdfDocument.getDocumentIdWrapper();
            String html = "<p>Hello world first!</p><span>Some text</span><p>Some second text</p>";
            List<IElement> lstFirst = HtmlConverter.convertToElements(html);
            Assert.assertEquals(3, lstFirst.size());

            addElementsToDocument(document, lstFirst);
        }

        List<AbstractProductProcessITextEvent> events = CONFIGURATION_ACCESS.getPublicEvents(docSequenceId);
        Assert.assertEquals(2, events.size());

        Assert.assertTrue(events.get(0) instanceof ConfirmedEventWrapper);
        ConfirmedEventWrapper confirmedEventWrapper = (ConfirmedEventWrapper) events.get(0);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof ITextCoreProductEvent);
        Assert.assertEquals(ITextCoreProductEvent.PROCESS_PDF, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(1) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(1);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())))) {
            String expectedProdLine = createExpectedProducerLine(
                    new ConfirmedEventWrapper[] {getCoreEvent(), getPdfHtmlEvent()});
            Assert.assertEquals(expectedProdLine, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    @Test
    public void convertHtmlToDocumentTest() throws IOException {
        String outFileName = "helloWorld_to_doc.pdf";
        SequenceId docId;
        try (Document document = HtmlConverter.convertToDocument(SOURCE_FOLDER + "helloWorld.html",
                new PdfWriter(DESTINATION_FOLDER + outFileName))) {
            docId = document.getPdfDocument().getDocumentIdWrapper();
        }

        List<AbstractProductProcessITextEvent> events = CONFIGURATION_ACCESS.getPublicEvents(docId);
        Assert.assertEquals(2, events.size());

        Assert.assertTrue(events.get(0) instanceof ConfirmedEventWrapper);
        ConfirmedEventWrapper confirmedEventWrapper = (ConfirmedEventWrapper) events.get(0);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof ITextCoreProductEvent);
        Assert.assertEquals(ITextCoreProductEvent.PROCESS_PDF, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(1) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(1);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(DESTINATION_FOLDER + outFileName))) {
            String expectedProdLine = createExpectedProducerLine(
                    new ConfirmedEventWrapper[] {getCoreEvent(), getPdfHtmlEvent()});
            Assert.assertEquals(expectedProdLine, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    @Test
    public void convertHtmlToDocAndAddElementsToDocTest() throws IOException {
        String outFileName = "helloWorld_to_doc_add_elem.pdf";
        SequenceId docId;
        try (Document document = HtmlConverter.convertToDocument(SOURCE_FOLDER + "helloWorld.html",
                new PdfWriter(DESTINATION_FOLDER + outFileName))) {
            docId = document.getPdfDocument().getDocumentIdWrapper();

            String html = "<p>Hello world first!</p><span>Some text</span><p>Some second text</p>";
            List<IElement> lstFirst = HtmlConverter.convertToElements(html);
            Assert.assertEquals(3, lstFirst.size());

            addElementsToDocument(document, lstFirst);
        }

        List<AbstractProductProcessITextEvent> events = CONFIGURATION_ACCESS.getPublicEvents(docId);
        // Confirmed 3 events, but only 2 events (1 core + 1 pdfHtml) will be reported because
        // ReportingHandler don't report similar events for one sequenceId
        Assert.assertEquals(3, events.size());

        Assert.assertTrue(events.get(0) instanceof ConfirmedEventWrapper);
        ConfirmedEventWrapper confirmedEventWrapper = (ConfirmedEventWrapper) events.get(0);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof ITextCoreProductEvent);
        Assert.assertEquals(ITextCoreProductEvent.PROCESS_PDF, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(1) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(1);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(2) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(2);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(DESTINATION_FOLDER + outFileName))) {
            String expectedProdLine = createExpectedProducerLine(
                    new ConfirmedEventWrapper[] {getCoreEvent(), getPdfHtmlEvent()});
            Assert.assertEquals(expectedProdLine, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    @Test
    public void convertHtmlToPdfTest() throws IOException {
        String outFileName = "helloWorld_to_pdf.pdf";
        HtmlConverter.convertToPdf(SOURCE_FOLDER + "helloWorld.html",
                new PdfWriter(DESTINATION_FOLDER + outFileName));

        List<ConfirmEvent> events = handler.getEvents();
        Assert.assertEquals(1, events.size());

        AbstractProductProcessITextEvent event = events.get(0).getConfirmedEvent();
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, event.getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(DESTINATION_FOLDER + outFileName))) {
            String expectedProdLine = createExpectedProducerLine(new ConfirmedEventWrapper[] {getPdfHtmlEvent()});
            Assert.assertEquals(expectedProdLine, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    @Test
    public void convertHtmlToPdfWithExistPdfTest() throws IOException {
        String outFileName = "helloWorld_to_pdf.pdf";
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + outFileName))) {
            HtmlConverter.convertToPdf(SOURCE_FOLDER + "helloWorld.html", pdfDocument, new ConverterProperties());
        }

        List<ConfirmEvent> events = handler.getEvents();
        Assert.assertEquals(2, events.size());

        AbstractProductProcessITextEvent event = events.get(0).getConfirmedEvent();
        Assert.assertEquals(ITextCoreProductEvent.PROCESS_PDF, event.getEventType());

        event = events.get(1).getConfirmedEvent();
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, event.getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(DESTINATION_FOLDER + outFileName))) {
            String expectedProdLine = createExpectedProducerLine(
                    new ConfirmedEventWrapper[] {getCoreEvent(), getPdfHtmlEvent()});
            Assert.assertEquals(expectedProdLine, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    @Test
    public void nestedElementToDocumentTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SequenceId docSequenceId;
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos));
                Document document = new Document(pdfDocument)) {
            docSequenceId = pdfDocument.getDocumentIdWrapper();
            String firstHtml = "<p>Hello world first!</p>";
            Paragraph pWithId = (Paragraph) HtmlConverter.convertToElements(firstHtml).get(0);

            Paragraph pWithoutId = new Paragraph("");
            pWithoutId.add(pWithId);
            Div divWithoutId = new Div();
            divWithoutId.add(pWithoutId);

            document.add(divWithoutId);
        }

        List<AbstractProductProcessITextEvent> events = CONFIGURATION_ACCESS.getPublicEvents(docSequenceId);

        Assert.assertEquals(2, events.size());

        Assert.assertTrue(events.get(0) instanceof ConfirmedEventWrapper);
        ConfirmedEventWrapper confirmedEventWrapper = (ConfirmedEventWrapper) events.get(0);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof ITextCoreProductEvent);
        Assert.assertEquals(ITextCoreProductEvent.PROCESS_PDF, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(1) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(1);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())))) {
            String expectedProdLine = createExpectedProducerLine(
                    new ConfirmedEventWrapper[] {getCoreEvent(), getPdfHtmlEvent()});
            Assert.assertEquals(expectedProdLine, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = KernelLogMessageConstant.UNCONFIRMED_EVENT)})
    public void unreportedCoreEventTest() throws Exception {
        String outFileName = DESTINATION_FOLDER + "unreportedCoreEvent.pdf";
        String html = "<html><head></head>" +
                "<body style=\"font-size:12.0pt; font-family:Arial\">" +
                "<p>Text in paragraph</p>" +
                "</body></html>";

        try (PdfDocument document = new PdfDocument(new PdfWriter(outFileName),
                new DocumentProperties().setEventCountingMetaInfo(new HtmlTestMetaInfo()))) {
            document.addNewPage();

            HtmlConverter.convertToDocument(html, document, new ConverterProperties());

            ITextCoreProductEvent coreEvent = ITextCoreProductEvent.createProcessPdfEvent(
                    document.getDocumentIdWrapper(), null, EventConfirmationType.ON_DEMAND);
            EventManager.getInstance().onEvent(coreEvent);
        }

        String expectedProdLine = createExpectedProducerLine(new ConfirmedEventWrapper[] {getPdfHtmlEvent()});
        validatePdfProducerLine(outFileName, expectedProdLine);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = KernelLogMessageConstant.UNCONFIRMED_EVENT)})
    public void unreportedPdfHtmlEventTest() throws Exception {
        String outFileName = DESTINATION_FOLDER + "unreportedPdfHtmlEvent.pdf";

        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName))) {
            pdfDocument.addNewPage();

            PdfHtmlTestProductEvent event = new PdfHtmlTestProductEvent(
                    pdfDocument.getDocumentIdWrapper(), "event data", EventConfirmationType.ON_DEMAND);

            EventManager.getInstance().onEvent(event);
        }

        String expectedProdLine = createExpectedProducerLine(new ConfirmedEventWrapper[] {getCoreEvent()});
        validatePdfProducerLine(outFileName, expectedProdLine);
    }

    private static class HtmlTestMetaInfo implements IMetaInfo {}

    private void validatePdfProducerLine(String filePath, String expected) throws IOException {
        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(filePath))) {
            Assert.assertEquals(expected, pdfDocument.getDocumentInfo().getProducer());
        }
    }

    private static String createExpectedProducerLine(ConfirmedEventWrapper[] expectedEvents) {
        List<ConfirmedEventWrapper> listEvents = Arrays.asList(expectedEvents);
        return ProducerBuilder.modifyProducer(listEvents, null);
    }

    private static ConfirmedEventWrapper getPdfHtmlEvent() {
        DefaultITextProductEventProcessor processor = new DefaultITextProductEventProcessor(
                ProductNameConstant.PDF_HTML);
        return new ConfirmedEventWrapper(
                PdfHtmlProductEvent.createConvertHtmlEvent(new SequenceId(), null),
                processor.getUsageType(),
                processor.getProducer());
    }

    private static ConfirmedEventWrapper getCoreEvent() {
        DefaultITextProductEventProcessor processor = new DefaultITextProductEventProcessor(
                ProductNameConstant.ITEXT_CORE);
        return new ConfirmedEventWrapper(
                ITextCoreProductEvent.createProcessPdfEvent(new SequenceId(), null, EventConfirmationType.ON_CLOSE),
                processor.getUsageType(),
                processor.getProducer());
    }

    private static void addElementsToDocument(Document document, List<IElement> elements) {
        for (IElement elem : elements) {
            if (elem instanceof IBlockElement) {
                document.add((IBlockElement) elem);
            } else if (elem instanceof Image) {
                document.add((Image) elem);
            } else if (elem instanceof AreaBreak) {
                document.add((AreaBreak) elem);
            } else {
                Assert.fail(
                        "The #convertToElements method gave element which is unsupported as root element, it's unexpected.");
            }
        }
    }

    private static class StoreEventsHandler implements IBaseEventHandler {
        private List<ConfirmEvent> events = new ArrayList<>();

        public List<ConfirmEvent> getEvents() {
            return events;
        }

        @Override
        public void onEvent(IBaseEvent event) {
            if (event instanceof ConfirmEvent) {
                events.add((ConfirmEvent) event);
            }
        }
    }
}
