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

package com.itextpdf.html2pdf.events;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class PdfHtmlAcroformDocumentEventTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/events/PdfHtmlAcroformDocumentEventTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/events/PdfHtmlAcroformDocumentEventTest/";

    @BeforeClass
    public static void initDestinationFolder() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void endPageEventWithFieldTest() throws IOException, InterruptedException {
        String name = "endPageEventWithFieldTest";
        String pdfOutput = destinationFolder + name + ".pdf";
        String pdfComparison = sourceFolder + "cmp_" + name + ".pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setCreateAcroForm(true);
        PdfDocument pdfDocument = addEventHandlersToPdfDocument(pdfOutput, converterProperties);

        String htmlString = "<html><head><title>pdfHtml header and footer example</title></head><body><span>test</span>"
                + "<div style='page-break-before:always;'></div><span>test</span></body></html>";

        HtmlConverter.convertToPdf(htmlString, pdfDocument, new ConverterProperties());

        pdfDocument.close();

        Assert.assertNull(new CompareTool().compareByContent(pdfOutput, pdfComparison, destinationFolder));
    }

    private PdfDocument addEventHandlersToPdfDocument(String pdfOutput, ConverterProperties converterProperties)
            throws IOException {
        FileOutputStream pdfStream = new FileOutputStream(pdfOutput);

        final List<IElement> elements = HtmlConverter
                .convertToElements("<input type='text' name='h' value='test header field value here'/>",
                        converterProperties);
        final List<IElement> footer = HtmlConverter
                .convertToElements("<input type='text' name='h' value='test footer field value here'/>",
                        converterProperties);
        PdfWriter writer = new PdfWriter(pdfStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        IEventHandler handler = new IEventHandler() {
            @Override
            public void handleEvent(Event event) {
                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                PdfPage page = docEvent.getPage();
                PdfCanvas pdfCanvas = new PdfCanvas(page);
                Rectangle pageSize = page.getPageSize();
                Canvas canvas = new Canvas(pdfCanvas, pageSize);
                Paragraph headerP = new Paragraph();
                for (IElement elem : elements) {
                    if (elem instanceof IBlockElement) {
                        headerP.add((IBlockElement) elem);
                    } else if (elem instanceof Image) {
                        headerP.add((Image) elem);
                    }
                }
                Paragraph footerP = new Paragraph();
                for (IElement elem : footer) {
                    if (elem instanceof IBlockElement) {
                        footerP.add((IBlockElement) elem);
                    } else if (elem instanceof Image) {
                        footerP.add((Image) elem);
                    }
                }
                canvas.showTextAligned(headerP, pageSize.getWidth() / 2, pageSize.getTop() - 30, TextAlignment.LEFT);
                canvas.showTextAligned(footerP, 0, 0, TextAlignment.LEFT);
            }
        };

        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, handler);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, handler);

        return pdfDocument;
    }
}
