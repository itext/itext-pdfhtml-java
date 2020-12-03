package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.exceptions.CssApplierInitializationException;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

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
        String tag = "custom-tag";
        String className = "com.itextpdf.html2pdf.css.apply.impl.TestClass";

        junitExpectedException.expect(CssApplierInitializationException.class);
        junitExpectedException.expectMessage(MessageFormatUtil
                .format(CssApplierInitializationException.REFLECTION_FAILED, className, tag));

        new TestCssApplierFactory().getCssApplier(new JsoupElementNode(new Element(Tag.valueOf("custom-tag"), "")));
    }
}

class TestCssApplierFactory extends DefaultCssApplierFactory {
    public TestCssApplierFactory() {
        defaultMapping.putMapping("custom-tag", TestClass.class);
    }
}

class TestClass {
}
