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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.exceptions.TagWorkerInitializationException;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerMapping.ITagWorkerCreator;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(UnitTest.class)
public class DefaultTagWorkerFactoryTest extends ExtendedITextTest {

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Test
    public void cannotGetTagWorkerForCustomTagViaReflection() {
        ITagWorker tagWorker = new TestTagWorkerFactory().getTagWorker(new JsoupElementNode(new Element(Tag.valueOf("custom-tag"), "")),
                new ProcessorContext(new ConverterProperties()));
        Assert.assertEquals(TestClass.class, tagWorker.getClass());
    }
}

class TestTagWorkerFactory extends DefaultTagWorkerFactory {
    public TestTagWorkerFactory() {
        getDefaultMapping().putMapping("custom-tag", (lhs, rhs) -> new TestClass());
    }
}

class TestClass implements ITagWorker {
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {

    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return false;
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return false;
    }

    @Override
    public IPropertyContainer getElementResult() {
        return null;
    }

}
