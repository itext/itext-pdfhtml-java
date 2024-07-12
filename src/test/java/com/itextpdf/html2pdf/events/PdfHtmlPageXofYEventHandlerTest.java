/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
package com.itextpdf.html2pdf.events;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.test.ExtendedITextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Tag("IntegrationTest")
public class PdfHtmlPageXofYEventHandlerTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/events/PdfHtmlPageXofYEventHandlerTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/events/PdfHtmlPageXofYEventHandlerTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void pageXofYHtmlTest() throws IOException, InterruptedException {
        String filename = "pageXofY";
        String src = sourceFolder + filename + ".html";
        String dest = destinationFolder + filename + ".pdf";
        String cmp = sourceFolder + "cmp_" + filename + ".pdf";
        new PdfHtmlPageXofYEventHandlerTest().parseWithFooter(src, dest, sourceFolder);
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(src) + "\n");
        Assertions.assertNull(new CompareTool().compareByContent(dest, cmp, destinationFolder, "diff_XofY_"));

    }

    public void parseWithFooter(String htmlSource, String pdfDest, String resoureLoc) throws IOException {
        //Create Document
        PdfWriter writer = new PdfWriter(pdfDest);
        PdfDocument pdfDocument = new PdfDocument(writer);
        //Create event-handlers
        PageXofY footerHandler = new PageXofY(pdfDocument);
        //Assign event-handlers
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);
        //Convert
        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(resoureLoc);
        Document doc = HtmlConverter.convertToDocument(new FileInputStream(new File(htmlSource).getAbsolutePath()), pdfDocument, converterProperties);
        //Write the total number of pages to the placeholder
        doc.flush();
        footerHandler.writeTotal(pdfDocument);
        doc.close();
    }

    //page X of Y
    protected class PageXofY implements IEventHandler {
        protected PdfFormXObject placeholder;
        protected float side = 20;
        protected float x = 300;
        protected float y = 25;
        protected float space = 4.5f;
        protected float descent = 3;

        public PageXofY(PdfDocument pdf) {
            placeholder =
                    new PdfFormXObject(new Rectangle(0, 0, side, side));
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdf.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pageSize);
            Paragraph p = new Paragraph()
                    .add("Page ").add(String.valueOf(pageNumber)).add(" of");
            canvas.showTextAligned(p, x, y, TextAlignment.RIGHT);
            pdfCanvas.addXObjectAt(placeholder, x + space, y - descent);
            canvas.close();
        }

        public void writeTotal(PdfDocument pdf) {
            Canvas canvas = new Canvas(placeholder, pdf);
            canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()),
                    0, descent, TextAlignment.LEFT);
        }
    }


}
