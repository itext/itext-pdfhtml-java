package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
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

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class BrTagWorkerTest extends ExtendedITextTest {

    @Test
    public void langAttrInBrForTaggedPdfTest() {
        Attributes attributes = new Attributes();
        attributes.put(AttributeConstants.LANG, "en");
        Element element = new Element(Tag.valueOf(TagConstants.BR), TagConstants.BR, attributes);
        JsoupElementNode node = new JsoupElementNode(element);
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.FONT_FAMILY, "sans-serif");
        node.setStyles(styles);

        ProcessorContext processorContext = new ProcessorContext(new ConverterProperties());

        BrTagWorker tagWorker = new BrTagWorker(node, processorContext);
        IPropertyContainer propertyContainer = tagWorker.getElementResult();

        Assert.assertTrue(propertyContainer instanceof IAccessibleElement);
        String lang = ((IAccessibleElement) propertyContainer).getAccessibilityProperties().getLanguage();
        Assert.assertEquals("en", lang);
    }
}
