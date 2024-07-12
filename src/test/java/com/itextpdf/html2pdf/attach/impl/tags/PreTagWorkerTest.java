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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag("UnitTest")
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
            Assertions.assertTrue(child instanceof Text);
            Assertions.assertEquals("\u200Dcontent", ((Text) child).getText());
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
            Assertions.assertTrue(child instanceof Text);
            Assertions.assertEquals("\u200D\n\u200Dcontent", ((Text) child).getText());
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
            Assertions.assertTrue(child instanceof Text);
            if (i == 0) {
                Assertions.assertEquals("\u200Dcontent", ((Text) child).getText());
            } else {
                Assertions.assertEquals("\u200D\r\n\u200Dcontent", ((Text) child).getText());
            }
        }
    }
}
