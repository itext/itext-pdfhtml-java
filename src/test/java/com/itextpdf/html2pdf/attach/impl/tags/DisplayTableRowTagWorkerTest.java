package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
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
public class DisplayTableRowTagWorkerTest extends ExtendedITextTest {

    @Test
    public void langAttrInDisplayTableRowForTaggedPdfTest() {
        Attributes attributes = new Attributes();
        attributes.put(AttributeConstants.LANG, "en");
        Element element = new Element(Tag.valueOf(TagConstants.DIV), TagConstants.DIV, attributes);
        JsoupElementNode node = new JsoupElementNode(element);
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.NORMAL);
        styles.put(CssConstants.TEXT_TRANSFORM, CssConstants.LOWERCASE);
        styles.put(CssConstants.DISPLAY, CssConstants.TABLE_ROW);
        node.setStyles(styles);

        ProcessorContext processorContext = new ProcessorContext(new ConverterProperties());
        DisplayTableRowTagWorker tagWorker = new DisplayTableRowTagWorker(node, processorContext);

        Element childElement = new Element(Tag.valueOf(TagConstants.TD), TagConstants.TD);
        JsoupElementNode childNode = new JsoupElementNode(childElement);
        styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.NORMAL);
        styles.put(CssConstants.TEXT_TRANSFORM, CssConstants.LOWERCASE);
        styles.put(CssConstants.DISPLAY, CssConstants.TABLE_CELL);
        childNode.setStyles(styles);

        TdTagWorker childTagWorker = new TdTagWorker(childNode, processorContext);
        tagWorker.processTagChild(childTagWorker, processorContext);
        IPropertyContainer propertyContainer = tagWorker.getElementResult();
        Assert.assertTrue(propertyContainer instanceof Table);
        Cell cell = ((Table) propertyContainer).getCell(0, 0);

        Assert.assertNotNull(cell);
        Assert.assertEquals("en", cell.getAccessibilityProperties().getLanguage());
    }
}
