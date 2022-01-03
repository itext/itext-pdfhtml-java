/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.exceptions.CssApplierInitializationException;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(UnitTest.class)
public class DefaultCssApplierFactoryTest extends ExtendedITextTest {

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Test
    public void cannotGetCssApplierForCustomTagViaReflection() {
        ICssApplier cssApplier = new TestCssApplierFactory().getCssApplier(new JsoupElementNode(new Element(Tag.valueOf("custom-tag"), "")));
        Assert.assertEquals(TestClass.class, cssApplier.getClass());
    }
}

class TestCssApplierFactory extends DefaultCssApplierFactory {
    public TestCssApplierFactory() {
        getDefaultMapping().putMapping("custom-tag", () -> new TestClass());
    }
}

class TestClass implements ICssApplier {
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {

    }
}
