package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageTargetCountElementNode;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.layout.property.Property;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class LinkHelperTest extends ExtendedITextTest {

    @Test
    public void createDestinationDestinationTest() {
        ITagWorker worker = new DivTagWorker(new PageTargetCountElementNode(null, ""), null);
        Attributes attributes = new Attributes();
        attributes.put(AttributeConstants.ID, "some_id");
        attributes.put(AttributeConstants.HREF, "#some_id");
        JsoupElementNode elementNode = new JsoupElementNode(new Element(Tag.valueOf("a"), "", attributes));
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        context.getLinkContext().scanForIds(elementNode);
        LinkHelper.createDestination(worker, elementNode, context);
        Assert.assertEquals("some_id", worker.getElementResult().<String>getProperty(Property.DESTINATION));
    }

    @Test
    public void createDestinationIDTest() {
        ITagWorker worker = new DivTagWorker(new PageTargetCountElementNode(null, ""), null);
        Attributes attributes = new Attributes();
        attributes.put(AttributeConstants.ID, "some_id");
        JsoupElementNode elementNode = new JsoupElementNode(new Element(Tag.valueOf("a"), "", attributes));
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        LinkHelper.createDestination(worker, elementNode, context);
        Assert.assertEquals("some_id", worker.getElementResult().<String>getProperty(Property.ID));
    }
}
