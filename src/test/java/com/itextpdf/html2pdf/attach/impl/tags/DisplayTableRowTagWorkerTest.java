/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

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
