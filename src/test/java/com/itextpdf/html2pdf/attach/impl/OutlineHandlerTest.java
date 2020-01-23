/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.PTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OutlineHandlerTest extends ExtendedHtmlConversionITextTest {

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
        Assert.assertEquals("pdfHTML-iText-outline-pdfHTML-iText-outline-1", pdfStringDest.toUnicodeString());
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
        Assert.assertEquals("prefix-prefix-1", pdfStringDest.toUnicodeString());
    }
}
