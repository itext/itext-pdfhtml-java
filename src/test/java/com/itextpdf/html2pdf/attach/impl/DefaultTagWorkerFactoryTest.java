package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.exceptions.TagWorkerInitializationException;
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
public class DefaultTagWorkerFactoryTest extends ExtendedITextTest {

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Test
    public void cannotGetTagWorkerForCustomTagViaReflection() {
        String tag = "custom-tag";
        String className = "com.itextpdf.html2pdf.attach.impl.TestClass";

        junitExpectedException.expect(TagWorkerInitializationException.class);
        junitExpectedException.expectMessage(MessageFormatUtil
                .format(TagWorkerInitializationException.REFLECTION_IN_TAG_WORKER_FACTORY_IMPLEMENTATION_FAILED,
                        className, tag));

        new TestTagWorkerFactory().getTagWorker(new JsoupElementNode(new Element(Tag.valueOf("custom-tag"), "")),
                new ProcessorContext(new ConverterProperties()));
    }
}

class TestTagWorkerFactory extends DefaultTagWorkerFactory {
    public TestTagWorkerFactory() {
        defaultMapping.putMapping("custom-tag", TestClass.class);
    }
}

class TestClass {
}
