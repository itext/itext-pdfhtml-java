package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Category(UnitTest.class)
public class PreTagWorkerTest extends ExtendedITextTest {

    @Test
    public void processContentWithoutRNTest() {
        IElementNode elementNode = new JsoupElementNode(new Element("not empty"));
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.PRE);
        elementNode.setStyles(styles);
        PreTagWorker preTagWorker = new PreTagWorker(elementNode, null);

        preTagWorker.processContent("content", null);
        preTagWorker.processContent("content", null);
        preTagWorker.processContent("content", null);
        preTagWorker.postProcessInlineGroup();

        Div div = (Div) preTagWorker.getElementResult();
        List<IElement> children = ((Paragraph) div.getChildren().get(0)).getChildren();
        for (IElement child : children) {
            Assert.assertTrue(child instanceof Text);
            Assert.assertEquals("\u200Dcontent", ((Text) child).getText());
        }
    }

    @Test
    public void processContentWithNTest() {
        IElementNode elementNode = new JsoupElementNode(new Element("not empty"));
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.PRE);
        elementNode.setStyles(styles);
        PreTagWorker preTagWorker = new PreTagWorker(elementNode, null);

        preTagWorker.processContent("\ncontent", null);
        preTagWorker.processContent("\ncontent", null);
        preTagWorker.processContent("\ncontent", null);
        preTagWorker.postProcessInlineGroup();

        Div div = (Div) preTagWorker.getElementResult();
        List<IElement> children = ((Paragraph) div.getChildren().get(0)).getChildren();
        for (IElement child : children) {
            Assert.assertTrue(child instanceof Text);
            Assert.assertEquals("\u200D\n\u200Dcontent", ((Text) child).getText());
        }
    }

    @Test
    public void processContentWithRNTest() {
        IElementNode elementNode = new JsoupElementNode(new Element("not empty"));
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.PRE);
        elementNode.setStyles(styles);
        PreTagWorker preTagWorker = new PreTagWorker(elementNode, null);

        preTagWorker.processContent("\r\ncontent", null);
        preTagWorker.processContent("\r\ncontent", null);
        preTagWorker.processContent("\r\ncontent", null);
        preTagWorker.postProcessInlineGroup();

        Div div = (Div) preTagWorker.getElementResult();
        List<IElement> children = ((Paragraph) div.getChildren().get(0)).getChildren();
        for (int i = 0; i < children.size(); i++) {
            IElement child = children.get(i);
            Assert.assertTrue(child instanceof Text);
            if (i == 0) {
                Assert.assertEquals("\u200Dcontent", ((Text) child).getText());
            } else {
                Assert.assertEquals("\u200D\r\n\u200Dcontent", ((Text) child).getText());
            }
        }
    }
}
