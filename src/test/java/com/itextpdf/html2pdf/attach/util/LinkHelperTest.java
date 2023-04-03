/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageTargetCountElementNode;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.layout.properties.Property;
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
