package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.tagging.IAccessibleElement;
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
public class BodyTagWorkerTest extends ExtendedITextTest {

    @Test
    public void langAttrInBodyForTaggedPdfTest() {
        Attributes attributes = new Attributes();
        attributes.put(AttributeConstants.LANG, "en");
        Element element = new Element(Tag.valueOf(TagConstants.DIV), TagConstants.DIV, attributes);
        JsoupElementNode node = new JsoupElementNode(element);

        ConverterProperties converterProperties = new ConverterProperties();
        ProcessorContext processorContext = new ProcessorContext(converterProperties);

        BodyTagWorker tagWorker = new BodyTagWorker(node, processorContext);
        IPropertyContainer propertyContainer = tagWorker.getElementResult();

        Assert.assertTrue(propertyContainer instanceof IAccessibleElement);
        String lang = ((IAccessibleElement) propertyContainer).getAccessibilityProperties().getLanguage();
        Assert.assertEquals("en", lang);
    }
}
