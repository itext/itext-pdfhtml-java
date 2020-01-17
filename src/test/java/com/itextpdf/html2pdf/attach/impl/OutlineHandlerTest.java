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
