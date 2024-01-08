/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
    Authors: Apryse Software.

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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.styledxmlparser.node.IAttributes;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class TextDecorationApplierUtilTest extends ExtendedITextTest {

    private static IElementNode createNewNode(IElementNode parent, String color, String line, String decorationStyle) {
        IElementNode node = new TextDecorationTestElementNode(parent);
        if (color != null) {
            node.getStyles().put(CssConstants.TEXT_DECORATION_COLOR, color);
        }
        if (line != null) {
            node.getStyles().put(CssConstants.TEXT_DECORATION_LINE, line);
        }
        if (decorationStyle != null) {
            node.getStyles().put(CssConstants.TEXT_DECORATION_STYLE, decorationStyle);
        }
        return node;
    }

    @Test
    public void textDecorationHasNoParentShouldNotAlterStyles() {
        final String color = "red";
        final String line = "line-through";
        final String style = "solid";
        IElementNode node = createNewNode(null, color, line, style);
        TextDecorationApplierUtil.propagateTextDecorationProperties(node);

        Assert.assertEquals(color, node.getStyles().get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(line, node.getStyles().get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(style, node.getStyles().get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void parentNodeHasNoStyleMapDoesNothing() {
        IElementNode parent = new TextDecorationTestElementNode(null);
        parent.setStyles(null);
        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";

        TextDecorationApplierUtil.propagateTextDecorationProperties(parent);

        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();
        Assert.assertEquals(colorChild1, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(lineChild1, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(styleChild1, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));

    }

    @Test
    public void parentNoStyleAtAllDoesNotImpactChild() {
        IElementNode parent = createNewNode(null, null, null, null);

        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";

        TextDecorationApplierUtil.propagateTextDecorationProperties(parent);
        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();
        Assert.assertEquals(colorChild1, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(lineChild1, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(styleChild1, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void parentOnlyHasColorChildStylesShouldBeMerged() {
        IElementNode parent = createNewNode(null, "red", null, null);

        TextDecorationApplierUtil.propagateTextDecorationProperties(parent);

        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";

        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();

        // we only expect yellow because the line is ignored
        String expectedColorChild = "yellow";
        String expectedLineChild = "line-under";
        String expectedStyleChild = "solid";

        Assert.assertEquals(expectedColorChild, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineChild, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleChild, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void parentOnlyHasLineOnlyChildStylesShouldBeMerged() {
        IElementNode parent = createNewNode(null, null, "line-through", null);
        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";

        TextDecorationApplierUtil.propagateTextDecorationProperties(parent);

        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();

        String expectedColorChild = "currentcolor yellow";
        String expectedLineChild = "line-through line-under";
        String expectedStyleChild = "solid solid";

        Assert.assertEquals(expectedColorChild, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineChild, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleChild, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void parentOnlyHasDecorationOnlyChildStylesShouldBeMerged() {
        IElementNode parent = createNewNode(null, null, null, "solid");
        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";

        TextDecorationApplierUtil.propagateTextDecorationProperties(parent);
        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();

        //first is ignored because no line style is defined
        String expectedColorChild = "yellow";
        String expectedLineChild = "line-under";
        String expectedStyleChild = "solid";

        Assert.assertEquals(expectedColorChild, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineChild, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleChild, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void textDecorationShouldBeAppliedToChild() {
        final String colorParent = "red";
        final String lineParent = "line-through";
        final String styleParent = "solid";

        IElementNode parent = createNewNode(null, colorParent, lineParent, styleParent);

        TextDecorationApplierUtil.propagateTextDecorationProperties(parent);

        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";

        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();

        String expectedColorChild = "red yellow";
        String expectedLineChild = "line-through line-under";
        String expectedStyleChild = "solid solid";
        Assert.assertEquals(expectedColorChild, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineChild, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleChild, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void textDecorationShouldBeAppliedToChildWithoutDuplicates() {
        final String colorParent = "red";
        final String lineParent = "line-through";
        final String styleParent = "solid";

        IElementNode parent = createNewNode(null, colorParent, lineParent, styleParent);

        IElementNode child1 = createNewNode(parent, colorParent, lineParent, styleParent);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();

        Assert.assertEquals(colorParent, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(lineParent, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(styleParent, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void textDecorationOneColor2StylesShouldBeAppliedToChild() {
        final String colorParent = "red";
        final String lineParent = "line-through line-over";
        final String styleParent = "solid";

        IElementNode parent = createNewNode(null, colorParent, lineParent, styleParent);

        TextDecorationApplierUtil.propagateTextDecorationProperties(parent);
        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";

        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> childStyles = child1.getStyles();

        String expectedColorChild = "red red yellow";
        String expectedLineChild = "line-through line-over line-under";
        String expectedStyleChild = "solid solid solid";
        Assert.assertEquals(expectedColorChild, childStyles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineChild, childStyles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleChild, childStyles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void textDecorationShouldBeAppliedToChildAndSubChild() {
        final String colorParent = "red";
        final String lineParent = "line-through";
        final String styleParent = "solid";
        IElementNode parent = createNewNode(null, colorParent, lineParent, styleParent);

        final String colorChild1 = "yellow";
        final String lineChild1 = "line-under";
        final String styleChild1 = "solid";
        IElementNode child1 = createNewNode(parent, colorChild1, lineChild1, styleChild1);

        parent.addChild(child1);

        final String colorSubChild1 = "pink";
        final String lineSubChild1 = "line-over";
        final String styleSubChild1 = "solid";
        IElementNode subChild1 = createNewNode(child1, colorSubChild1, lineSubChild1, styleSubChild1);
        child1.addChild(subChild1);

        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> child1Styles = child1.getStyles();

        String expectedColorChild = "red yellow";
        String expectedLineChild = "line-through line-under";
        String expectedStyleChild = "solid solid";
        Assert.assertEquals(expectedColorChild, child1Styles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineChild, child1Styles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleChild, child1Styles.get(CssConstants.TEXT_DECORATION_STYLE));

        TextDecorationApplierUtil.propagateTextDecorationProperties(subChild1);

        child1Styles = subChild1.getStyles();
        String expectedColorSubChild = "red yellow pink";
        String expectedLineSubChild = "line-through line-under line-over";
        String expectedStyleSubChild = "solid solid solid";
        Assert.assertEquals(expectedColorSubChild, child1Styles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineSubChild, child1Styles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleSubChild, child1Styles.get(CssConstants.TEXT_DECORATION_STYLE));
    }

    @Test
    public void textDecorationShouldBeAppliedToChildAndSubChildWhenSecondChildDoesntHaveAttributes() {
        final String colorParent = "red";
        final String lineParent = "line-through";
        final String styleParent = "solid";
        IElementNode parent = createNewNode(null, colorParent, lineParent, styleParent);
        IElementNode child1 = createNewNode(parent, null, null, null);
        parent.addChild(child1);
        TextDecorationApplierUtil.propagateTextDecorationProperties(child1);
        Map<String, String> child1Styles = child1.getStyles();

        String expectedColorChild = "red";
        String expectedLineChild = "line-through";
        String expectedStyleChild = "solid";
        Assert.assertEquals(expectedColorChild, child1Styles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineChild, child1Styles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleChild, child1Styles.get(CssConstants.TEXT_DECORATION_STYLE));

        final String colorSubChild1 = "pink";
        final String lineSubChild1 = "line-over";
        final String styleSubChild1 = "solid";
        IElementNode subChild1 = createNewNode(child1, colorSubChild1, lineSubChild1, styleSubChild1);
        child1.addChild(subChild1);

        TextDecorationApplierUtil.propagateTextDecorationProperties(subChild1);

        child1Styles = subChild1.getStyles();
        String expectedColorSubChild = "red pink";
        String expectedLineSubChild = "line-through line-over";
        String expectedStyleSubChild = "solid solid";
        Assert.assertEquals(expectedColorSubChild, child1Styles.get(CssConstants.TEXT_DECORATION_COLOR));
        Assert.assertEquals(expectedLineSubChild, child1Styles.get(CssConstants.TEXT_DECORATION_LINE));
        Assert.assertEquals(expectedStyleSubChild, child1Styles.get(CssConstants.TEXT_DECORATION_STYLE));
    }


    static class TextDecorationTestElementNode implements IElementNode {
        private final INode parent;

        TextDecorationTestElementNode(INode parent) {
            this.parent = parent;
        }

        @Override
        public String name() {
            return "testnode";
        }

        @Override
        public IAttributes getAttributes() {
            return null;
        }

        @Override
        public String getAttribute(String key) {
            return null;
        }

        @Override
        public List<Map<String, String>> getAdditionalHtmlStyles() {
            return null;
        }

        @Override
        public void addAdditionalHtmlStyles(Map<String, String> styles) {

        }

        @Override
        public String getLang() {
            return "en";
        }

        @Override
        public List<INode> childNodes() {
            return children;
        }

        List<INode> children = new ArrayList<>();

        @Override
        public void addChild(INode node) {
            children.add(node);
        }

        @Override
        public INode parentNode() {
            return parent;
        }

        @Override
        public void setStyles(Map<String, String> stringStringMap) {

        }

        HashMap<String, String> styles = new HashMap<>();

        @Override
        public Map<String, String> getStyles() {
            return styles;
        }
    }
}
