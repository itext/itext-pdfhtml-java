package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class WidthDimensionContainerTest extends ExtendedITextTest {

    @Test
    public void minFixContentDimensionTest() {
        INode iNode = null;
        Map<String, String> styles = new HashMap<>();
        styles.put("width", "20pt");

        CssContextNode cssContextNode = new CssContextNode(iNode) {
            @Override
            public INode parentNode() {
                return super.parentNode();
            }
        };

        cssContextNode.setStyles(styles);
        Paragraph paragraph = new Paragraph("Paragraph");

        WidthDimensionContainer widthDimensionContainer = new WidthDimensionContainer(cssContextNode, 500,
                paragraph.createRendererSubTree(), 0);

        Assert.assertEquals(widthDimensionContainer.minContentDimension, 20, 0.0);
    }
}
