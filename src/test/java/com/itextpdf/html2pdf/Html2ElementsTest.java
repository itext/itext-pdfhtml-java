/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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

import com.itextpdf.commons.actions.EventManager;
import com.itextpdf.commons.actions.IEvent;
import com.itextpdf.commons.actions.IEventHandler;
import com.itextpdf.commons.actions.sequence.AbstractIdentifiableElement;
import com.itextpdf.commons.actions.sequence.SequenceId;
import com.itextpdf.commons.actions.sequence.SequenceIdManager;
import com.itextpdf.html2pdf.actions.events.PdfHtmlProductEvent;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.exceptions.KernelExceptionMessageConstant;
import com.itextpdf.kernel.exceptions.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IAbstractElement;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.Leading;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class Html2ElementsTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/Html2ElementsTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/Html2ElementsTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void htmlToElementsTest01() {
        String html = "<p>Hello world!</p>";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 1);
        Assert.assertTrue(lst.get(0) instanceof Paragraph);
        Paragraph p = (Paragraph) lst.get(0);
        Assert.assertEquals("Hello world!", ((Text) p.getChildren().get(0)).getText());
        Assert.assertEquals(12f, p.<UnitValue>getProperty(Property.FONT_SIZE).getValue(), 1e-10);
    }

    @Test
    public void htmlToElementsTest02() {
        String html = "<table style=\"font-size: 2em\"><tr><td>123</td><td><456></td></tr><tr><td>Long cell</td></tr></table>";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 1);
        Assert.assertTrue(lst.get(0) instanceof Table);
        Table t = (Table) lst.get(0);
        Assert.assertEquals(2, t.getNumberOfRows());
        Assert.assertEquals("123", ((Text) (((Paragraph) t.getCell(0, 0).getChildren().get(0)).getChildren().get(0))).getText());
        Assert.assertEquals(24f, t.<UnitValue>getProperty(Property.FONT_SIZE).getValue(), 1e-10);
    }

    @Test
    public void htmlToElementsTest03() {
        String html = "<p>Hello world!</p><table><tr><td>123</td><td><456></td></tr><tr><td>Long cell</td></tr></table><p>Hello world!</p>";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 3);
        Assert.assertTrue(lst.get(0) instanceof Paragraph);
        Assert.assertTrue(lst.get(1) instanceof Table);
        Assert.assertTrue(lst.get(2) instanceof Paragraph);
        Assert.assertEquals("Hello world!", ((Text) ((Paragraph) lst.get(0)).getChildren().get(0)).getText());
        Assert.assertEquals("123", ((Text) (((Paragraph) ((Table) lst.get(1)).getCell(0, 0).getChildren().get(0)).getChildren().get(0))).getText());
    }

    @Test
    // Handles malformed html
    public void htmlToElementsTest04() {
        String html = "<p>Hello world!<table><td>123";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 2);
        Assert.assertTrue(lst.get(0) instanceof Paragraph);
        Assert.assertTrue(lst.get(1) instanceof Table);
        Assert.assertEquals("Hello world!", ((Text) ((Paragraph) lst.get(0)).getChildren().get(0)).getText());
        Assert.assertEquals("123", ((Text) (((Paragraph) ((Table) lst.get(1)).getCell(0, 0).getChildren().get(0)).getChildren().get(0))).getText());
    }

    @Test
    @LogMessages(messages = {})
    public void htmlToElementsTest05() {
        String html = "123";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 1);
    }

    @Test
    @LogMessages(messages = {})
    public void htmlElementsTest06() {
        String html = "<html>Lorem<p>Ipsum</p>Dolor<p>Sit</p></html>";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 4);
        for (int i = 0; i < lst.size(); i++)
            Assert.assertTrue(lst.get(i) instanceof Paragraph);
    }

    @Test
    @LogMessages(messages = {})
    public void htmlElementsTest07() {
        String html = "<html>Lorem<span>Dolor</span><p>Ipsum</p><p>Sit</p></html>";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 3);
        for (int i = 0; i < lst.size(); i++)
            Assert.assertTrue(lst.get(i) instanceof Paragraph);
    }

    @Test
    // this test checks whether iText fails to process meta tag inside body section or not
    public void htmlToElementsTest08() {
        String html = "<html><p>Hello world!</p><meta name=\"author\" content=\"Bruno\"><table><tr><td>123</td><td><456></td></tr><tr><td>Long cell</td></tr></table><p>Hello world!</p></html>";
        HtmlConverter.convertToElements(html);
    }

    @Test
    //Test OutlineHandler exception throwing
    public void htmlToElementsTest09() {
        /*
            Outlines require a PdfDocument, and OutlineHandler is based around its availability
            Any convert to elements workflow of course doesn't have a PdfDocument.
            Instead of throwing an NPE when trying it, the OutlineHandler will check for the existence of a pdfDocument
            If no PdfDocument is found, the handler will do nothing silently
         */
        String html = "<html><p>Hello world!</p><meta name=\"author\" content=\"Bruno\"><table><tr><td>123</td><td><456></td></tr><tr><td>Long cell</td></tr></table><p>Hello world!</p></html>";
        ConverterProperties props = new ConverterProperties();
        OutlineHandler outlineHandler = new OutlineHandler();
        outlineHandler.putTagPriorityMapping("h1", 1);
        outlineHandler.putTagPriorityMapping("h3", 2);
        outlineHandler.putTagPriorityMapping("p", 3);
        props.setOutlineHandler(outlineHandler);

        HtmlConverter.convertToElements(html);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI, count = 1),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)
    })
    public void htmlObjectMalformedUrlTest() {
        String html = "<object data ='htt://as' type='image/svg+xml'></object>";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertTrue(lst.size() == 0);
    }

    @Test
    public void htmlToElementsVsHtmlToPdfTest() throws IOException, InterruptedException {
        String src = sourceFolder + "basic.html";
        String outConvertToPdf = destinationFolder + "basicCovertToPdfResult.pdf";
        String outConvertToElements = destinationFolder + "basicCovertToElementsResult.pdf";
        HtmlConverter.convertToPdf(new File(src), new File(outConvertToPdf));

        List<IElement> elements = HtmlConverter.convertToElements(new FileInputStream(src));
        Document document = new Document(new PdfDocument(new PdfWriter(outConvertToElements)));

        // In order to collapse margins between the direct children of root element
        // it's required to manually enable collapsing on root element. This is because siblings
        // margins collapsing is controlled by the parent element.
        // This leads to the difference between pure convertToPdf/Document and convertToElements methods.
        document.setProperty(Property.COLLAPSING_MARGINS, true);

        for (IElement elem : elements) {
            if (elem instanceof IBlockElement) {
                document.add((IBlockElement) elem);
            } else if (elem instanceof Image) {
                document.add((Image) elem);
            } else if (elem instanceof AreaBreak) {
                document.add((AreaBreak) elem);
            } else {
                Assert.fail("The #convertToElements method gave element which is unsupported as root element, it's unexpected.");
            }
        }
        document.close();

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(src) + "\n");

        Assert.assertNull(new CompareTool().compareByContent(outConvertToElements, outConvertToPdf, destinationFolder));
    }

    @Test
    public void bodyFontFamilyTest() {
        String html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<body style=\"font-family: monospace\">\n"
                + "This text is directly in body and should be monospaced.\n"
                + "<p>This text is in paragraph and should be monospaced.</p>\n"
                + "</body>\n"
                + "</html>";
        List<IElement> elements = HtmlConverter.convertToElements(html);

        Assert.assertEquals(2, elements.size());
        IElement anonymousParagraph = elements.get(0);

        Assert.assertArrayEquals(new String[]{"monospace"}, anonymousParagraph.<String[]>getProperty(Property.FONT));

        IElement normalParagraph = elements.get(1);
        Assert.assertArrayEquals(new String[]{"monospace"}, normalParagraph.<String[]>getProperty(Property.FONT));
    }

    @Test
    public void leadingInDefaultRenderingModeTest() {
        String html = "This text is directly in body. It should have the same default LEADING property as everything else.\n"
                + "<p>This text is in paragraph.</p>";
        List<IElement> elements = HtmlConverter.convertToElements(html);

        Assert.assertEquals(2, elements.size());
        IElement anonymousParagraph = elements.get(0);

        // TODO DEVSIX-3873 anonymous paragraph inherited styles should be applied in general way
        Assert.assertNull(anonymousParagraph.<Leading>getProperty(Property.LEADING));

        IElement normalParagraph = elements.get(1);
        Assert.assertEquals(new Leading(Leading.MULTIPLIED, 1.2f), normalParagraph.<Leading>getProperty(Property.LEADING));
    }

    @Test
    public void eventGenerationTest() {
        StoreEventsHandler handler = new StoreEventsHandler();
        try {
            EventManager.getInstance().register(handler);
            String html = "<table><tr><td>123</td><td><456></td></tr><tr><td>789</td></tr></table><p>Hello world!</p>";
            List<IElement> elements = HtmlConverter.convertToElements(html);

            Assert.assertEquals(1, handler.getEvents().size());
            Assert.assertTrue(handler.getEvents().get(0) instanceof PdfHtmlProductEvent);

            SequenceId expectedSequenceId = ((PdfHtmlProductEvent) handler.getEvents().get(0)).getSequenceId();
            int validationsCount = validateSequenceIds(expectedSequenceId, elements);
            // Table                                     1
            //      Cell -> Paragraph -> Text [123]      3
            //      Cell -> Paragraph -> Text [456]      3
            //      Cell -> Paragraph -> Text [789]      3
            // Paragraph -> Text [Hello world!]          2
            //--------------------------------------------
            //                                          12
            Assert.assertEquals(12, validationsCount);
        } finally {
            EventManager.getInstance().unregister(handler);
        }
    }

    @Test
    public void convertToElementsAndCreateTwoDocumentsTest() {
        String html = "This text is directly in body. It should have the same default LEADING property as everything else.\n"
                + "<p>This text is in paragraph.</p>";
        List<IElement> iElementList = HtmlConverter.convertToElements(html);

        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
             Document document = new Document(pdfDocument)) {
            addElementsToDocument(document, iElementList);
        }

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        Document document = new Document(pdfDocument);
        addElementsToDocument(document, iElementList);

        // TODO DEVSIX-5753 error should not be thrown here
        Exception e = Assert.assertThrows(PdfException.class, () -> document.close());
        Assert.assertEquals(KernelExceptionMessageConstant.PDF_INDIRECT_OBJECT_BELONGS_TO_OTHER_PDF_DOCUMENT, e.getMessage());
    }

    @Test
    public void htmlToElementsSvgTest() throws IOException, InterruptedException {
        String html = "<svg height=\"100\" width=\"100\">"
                + "<circle cx=\"50\" cy=\"50\" r=\"40\" stroke=\"black\" stroke-width=\"3\" fill=\"red\" />"
                + "</svg>";
        String cmpPdf = sourceFolder + "cmp_htmlToElementsSvg.pdf";
        String outPdf = destinationFolder + "htmlToElementsSvg.pdf";
        List<IElement> lst = HtmlConverter.convertToElements(html);
        Assert.assertEquals(1, lst.size());
        try (Document document = new Document(new PdfDocument(new PdfWriter(outPdf)))) {
            for (IElement element : lst) {
                document.add((Image) element);
            }
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void htmlToElementsSvgInTheTableTest() throws IOException, InterruptedException {
        String html =
                "<table style=\"border: 1pt solid black\">\n" +
                        "  <tr>\n" +
                        "     <td>\n" +
                        "        <svg height=\"100\" width=\"100\">\n" +
                        "          <circle cx=\"50\" cy=\"50\" r=\"40\" stroke=\"black\" stroke-width=\"3\" fill=\"red\" />\n" +
                        "        </svg>\n" +
                        "     </td>\n" +
                        "     <td>\n" +
                        "        test\n" +
                        "     </td>\n" +
                        "  </tr>\n" +
                        "</table>";
        String cmpPdf = sourceFolder + "cmp_htmlToElementsSvgInTheTable.pdf";
        String outPdf = destinationFolder + "htmlToElementsSvgInTheTable.pdf";

        List<IElement> elements = HtmlConverter.convertToElements(html);
        try (Document document = new Document(new PdfDocument(new PdfWriter(outPdf)))) {
            for (IElement element : elements) {
                document.add((IBlockElement) element);
            }
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void htmlToElementsSvgImgTest() throws IOException, InterruptedException {
        String html = "<img src=\"lines.svg\" height=\"500\" width=\"500\"/>";
        String cmpPdf = sourceFolder + "cmp_htmlToElementsSvgImg.pdf";
        String outPdf = destinationFolder + "htmlToElementsSvgImg.pdf";

        List<IElement> elements = HtmlConverter.convertToElements(html,
                new ConverterProperties().setBaseUri(sourceFolder));
        try (Document document = new Document(new PdfDocument(new PdfWriter(outPdf)))) {
            for (IElement element : elements) {
                document.add((IBlockElement) element);
            }
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void htmlToElementsSvgObjectTest() throws IOException, InterruptedException {
        String html = "<object data ='lines.svg' type='image/svg+xml'></object>";
        String cmpPdf = sourceFolder + "cmp_htmlToElementsSvgObject.pdf";
        String outPdf = destinationFolder + "htmlToElementsSvgObject.pdf";

        List<IElement> elements = HtmlConverter.convertToElements(html,
                new ConverterProperties().setBaseUri(sourceFolder));
        try (Document document = new Document(new PdfDocument(new PdfWriter(outPdf)))) {
            for (IElement element : elements) {
                document.add((Image) element);
            }
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
    }

    @Test
    public void htmlToElementsFormTest() throws IOException, InterruptedException {
        FileInputStream htmlFile = new FileInputStream(sourceFolder + "formelements.html");
        String cmpPdf = sourceFolder + "cmp_htmlToElementsForms.pdf";
        String outPdf = destinationFolder + "htmlToElementsForms.pdf";

        List<IElement> elements = HtmlConverter.convertToElements(htmlFile,
                new ConverterProperties().setBaseUri(sourceFolder));
        try (Document document = new Document(new PdfDocument(new PdfWriter(outPdf)))) {
            for (IElement element : elements) {
                document.add((IBlockElement) element);
            }
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder));
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

    private static int validateSequenceIds(SequenceId expectedSequenceId, List<IElement> elements) {
        int validationCount = 0;
        for (IElement element : elements) {
            Assert.assertTrue(element instanceof AbstractIdentifiableElement);
            Assert.assertTrue(element instanceof IAbstractElement);
            Assert.assertEquals(expectedSequenceId, SequenceIdManager.getSequenceId((AbstractIdentifiableElement) element));
            validationCount += 1;
            validationCount += validateSequenceIds(expectedSequenceId, ((IAbstractElement) element).getChildren());
        }
        return validationCount;
    }

    private static class StoreEventsHandler implements IEventHandler {

        private List<IEvent> events = new ArrayList<>();

        public List<IEvent> getEvents() {
            return events;
        }

        @Override
        public void onEvent(IEvent event) {
            events.add(event);
        }
    }
}
