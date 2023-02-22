package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class HtmlStylesToCssConverterTest extends ExtendedITextTest {
    @Test
    public void trimSemicolonsForWidthAttributeTest() {
        final Element element = new Element(Tag.valueOf(TagConstants.IMG), "");
        element.attr("width", "53%;;;");
        JsoupElementNode node = new JsoupElementNode(element);
        List<CssDeclaration> cssDeclarations = HtmlStylesToCssConverter.convert(node);
        assertCssDeclarationListWithOneElement(cssDeclarations, "width", "53%");
    }

    @Test
    public void withoutSemicolonsWidthAttributeTest() {
        final Element element = new Element(Tag.valueOf(TagConstants.IMG), "");
        element.attr("width", "52%");
        JsoupElementNode node = new JsoupElementNode(element);
        List<CssDeclaration> cssDeclarations = HtmlStylesToCssConverter.convert(node);
        assertCssDeclarationListWithOneElement(cssDeclarations, "width", "52%");
    }

    @Test
    public void trimSemicolonsForHeightAttributeTest() {
        final Element element = new Element(Tag.valueOf(TagConstants.IMG), "");
        element.attr("height", "51%;");
        JsoupElementNode node = new JsoupElementNode(element);
        List<CssDeclaration> cssDeclarations = HtmlStylesToCssConverter.convert(node);
        assertCssDeclarationListWithOneElement(cssDeclarations, "height", "51%");
    }

    @Test
    public void withoutSemicolonsHeightAttributeTest() {
        final Element element = new Element(Tag.valueOf(TagConstants.IMG), "");
        element.attr("height", "50%");
        JsoupElementNode node = new JsoupElementNode(element);
        List<CssDeclaration> cssDeclarations = HtmlStylesToCssConverter.convert(node);
        assertCssDeclarationListWithOneElement(cssDeclarations, "height", "50%");
    }

    private static void assertCssDeclarationListWithOneElement(List<CssDeclaration> cssDeclarations, String prop, String exp) {
        Assert.assertEquals(1, cssDeclarations.size());
        Assert.assertEquals(prop, cssDeclarations.get(0).getProperty());
        Assert.assertEquals(exp, cssDeclarations.get(0).getExpression());
    }
}
