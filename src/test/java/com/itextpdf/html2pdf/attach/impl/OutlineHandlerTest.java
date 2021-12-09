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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.PTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OutlineHandlerTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/attach/impl/OutlineHandlerTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/attach/impl/OutlineHandlerTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void defaultDestinationPrefixTest() {
        Map<String, Integer> priorityMappings = new HashMap<>();
        priorityMappings.put("p", 1);
        OutlineHandler outlineHandler = new OutlineHandler().putAllTagPriorityMappings(priorityMappings);

        ProcessorContext context = new ProcessorContext(new ConverterProperties().setOutlineHandler(outlineHandler));
        context.reset(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())));

        IElementNode elementNode = new JsoupElementNode(new Element(Tag.valueOf(TagConstants.P), TagConstants.P));
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.NORMAL);
        styles.put(CssConstants.TEXT_TRANSFORM, CssConstants.LOWERCASE);
        // Styles are required in the constructor of the PTagWorker class
        elementNode.setStyles(styles);

        outlineHandler.addOutlineAndDestToDocument(new PTagWorker(elementNode, context), elementNode, context);

        PdfOutline pdfOutline = context.getPdfDocument().getOutlines(false).getAllChildren().get(0);
        Assert.assertEquals("p1", pdfOutline.getTitle());
        PdfString pdfStringDest = (PdfString) pdfOutline.getDestination().getPdfObject();
        Assert.assertEquals("pdfHTML-iText-outline-1", pdfStringDest.toUnicodeString());
    }

    @Test
    public void customDestinationPrefixTest() {
        Map<String, Integer> priorityMappings = new HashMap<>();
        priorityMappings.put("p", 1);
        OutlineHandler outlineHandler = new OutlineHandler().putAllTagPriorityMappings(priorityMappings);

        outlineHandler.setDestinationNamePrefix("prefix-");
        Assert.assertEquals("prefix-", outlineHandler.getDestinationNamePrefix());

        ProcessorContext context = new ProcessorContext(new ConverterProperties().setOutlineHandler(outlineHandler));
        context.reset(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())));

        IElementNode elementNode = new JsoupElementNode(new Element(Tag.valueOf(TagConstants.P), TagConstants.P));
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.NORMAL);
        styles.put(CssConstants.TEXT_TRANSFORM, CssConstants.LOWERCASE);
        // Styles are required in the constructor of the PTagWorker class
        elementNode.setStyles(styles);

        outlineHandler.addOutlineAndDestToDocument(new PTagWorker(elementNode, context), elementNode, context);

        PdfOutline pdfOutline = context.getPdfDocument().getOutlines(false).getAllChildren().get(0);
        Assert.assertEquals("p1", pdfOutline.getTitle());
        PdfString pdfStringDest = (PdfString) pdfOutline.getDestination().getPdfObject();
        Assert.assertEquals("prefix-1", pdfStringDest.toUnicodeString());
    }

    @Test
    // TODO DEVSIX-5195 fix cmp after fix is introduced
    public void defaultOutlineHandlerWithHTagHavingIdTest() throws IOException, InterruptedException {
        String inFile = SOURCE_FOLDER + "defaultOutlineHandlerWithHTagHavingIdTest.html";
        String outFile = DESTINATION_FOLDER + "defaultOutlineHandlerWithHTagHavingIdTest.pdf";
        String cmpFile = SOURCE_FOLDER + "cmp_defaultOutlineHandlerWithHTagHavingIdTest.pdf";

        OutlineHandler outlineHandler = OutlineHandler.createStandardHandler();
        HtmlConverter.convertToPdf(new File(inFile), new File(outFile),
                new ConverterProperties().setOutlineHandler(outlineHandler));
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFile, DESTINATION_FOLDER, "diff_defaultOutlineHandlerWithHTagHavingIdTest"));
    }

    @Test
    public void resetOutlineHandlerTest() throws IOException, InterruptedException {
        OutlineHandler outlineHandler = OutlineHandler.createStandardHandler();

        for (int i = 1; i <= 3; i++) {
            String srcHtml = SOURCE_FOLDER + "outlines0" + i + ".html";
            String outPdf = DESTINATION_FOLDER + "outlines0" + i + ".pdf";
            String cmpPdf = SOURCE_FOLDER + "cmp_outlines0" + i + ".pdf";

            HtmlConverter.convertToPdf(new File(srcHtml), new File(outPdf),
                    new ConverterProperties().setOutlineHandler(outlineHandler));
            Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER, "diff_" + i));
        }
    }
}
