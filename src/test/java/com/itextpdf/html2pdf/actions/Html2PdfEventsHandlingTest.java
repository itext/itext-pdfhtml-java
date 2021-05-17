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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.actions.events.PdfHtmlProductEvent;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.actions.EventManager;
import com.itextpdf.kernel.actions.events.AbstractProductProcessITextEvent;
import com.itextpdf.kernel.actions.events.ConfirmedEventWrapper;
import com.itextpdf.kernel.actions.events.LinkDocumentIdEvent;
import com.itextpdf.kernel.actions.sequence.AbstractIdentifiableElement;
import com.itextpdf.kernel.actions.sequence.SequenceId;
import com.itextpdf.kernel.actions.sequence.SequenceIdManager;
import com.itextpdf.kernel.counter.event.ITextCoreEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class Html2PdfEventsHandlingTest extends ExtendedITextTest {
    private static final TestConfigurationEvent CONFIGURATION_ACCESS = new TestConfigurationEvent();

    @Test
    public void twoSetOfElementsToOneDocumentTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SequenceId docSequenceId;
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos));
                Document document = new Document(pdfDocument)) {
            docSequenceId = pdfDocument.getDocumentIdWrapper();
            String firstHtml = "<p>Hello world first!</p>";
            List<IElement> lstFirst = HtmlConverter.convertToElements(firstHtml);

            addElementsToDocument(document, lstFirst);
            AbstractIdentifiableElement identifiableElement = (AbstractIdentifiableElement) lstFirst.get(0);
            // TODO DEVSIX-5304 remove LinkDocumentIdEvent after adding support linking in the scope of layout module
            EventManager.getInstance().onEvent(new LinkDocumentIdEvent(pdfDocument,
                    SequenceIdManager.getSequenceId(identifiableElement)));

            String secondHtml = "<p>Hello world second!</p>";
            List<IElement> lstSecond = HtmlConverter.convertToElements(secondHtml);

            addElementsToDocument(document, lstSecond);
            identifiableElement = (AbstractIdentifiableElement) lstSecond.get(0);
            // TODO DEVSIX-5304 remove LinkDocumentIdEvent after adding support linking in the scope of layout module
            EventManager.getInstance().onEvent(new LinkDocumentIdEvent(pdfDocument,
                    SequenceIdManager.getSequenceId(identifiableElement)));
        }


        List<AbstractProductProcessITextEvent> events = CONFIGURATION_ACCESS.getPublicEvents(docSequenceId);
        Assert.assertEquals(3, events.size());

        Assert.assertTrue(events.get(0) instanceof ConfirmedEventWrapper);
        ConfirmedEventWrapper confirmedEventWrapper = (ConfirmedEventWrapper) events.get(0);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof ITextCoreEvent);
        Assert.assertEquals(ITextCoreEvent.PROCESS_PDF, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(1) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(1);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        Assert.assertTrue(events.get(2) instanceof ConfirmedEventWrapper);
        confirmedEventWrapper = (ConfirmedEventWrapper) events.get(2);
        Assert.assertTrue(confirmedEventWrapper.getEvent() instanceof PdfHtmlProductEvent);
        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, confirmedEventWrapper.getEvent().getEventType());

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())))){
            String producerLine = pdfDocument.getDocumentInfo().getProducer();
            // TODO DEVSIX-5304 improve producer line check to check via some template
            Assert.assertNotEquals(-1, producerLine.indexOf("pdfHTML"));
            Assert.assertEquals(producerLine.indexOf("pdfHTML"), producerLine.lastIndexOf("pdfHTML"));
            Assert.assertNotEquals(-1, producerLine.indexOf("Core"));
        }
    }

    private static void addElementsToDocument(Document document, List<IElement> elements) {
        for (IElement elem : elements) {
            if (elem instanceof IBlockElement) {
                document.add((IBlockElement)elem);
            } else if (elem instanceof Image) {
                document.add((Image)elem);
            } else if (elem instanceof AreaBreak) {
                document.add((AreaBreak) elem);
            } else {
                Assert.fail("The #convertToElements method gave element which is unsupported as root element, it's unexpected.");
            }
        }
    }
}
