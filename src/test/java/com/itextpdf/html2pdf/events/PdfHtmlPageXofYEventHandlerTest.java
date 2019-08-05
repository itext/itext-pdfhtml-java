/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
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
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Category(IntegrationTest.class)
public class PdfHtmlPageXofYEventHandlerTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/events/PdfHtmlPageXofYEventHandlerTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/events/PdfHtmlPageXofYEventHandlerTest/";

    @BeforeClass
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
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(src).getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(dest, cmp, destinationFolder, "diff_XofY_"));

    }

    public void parseWithFooter(String htmlSource, String pdfDest, String resoureLoc) throws IOException, InterruptedException {
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
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            Paragraph p = new Paragraph()
                    .add("Page ").add(String.valueOf(pageNumber)).add(" of");
            canvas.showTextAligned(p, x, y, TextAlignment.RIGHT);
            pdfCanvas.addXObject(placeholder, x + space, y - descent);
            canvas.close();
        }

        public void writeTotal(PdfDocument pdf) {
            Canvas canvas = new Canvas(placeholder, pdf);
            canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()),
                    0, descent, TextAlignment.LEFT);
        }
    }


}
