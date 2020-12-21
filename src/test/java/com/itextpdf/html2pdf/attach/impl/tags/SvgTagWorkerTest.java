package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class SvgTagWorkerTest extends ExtendedITextTest {
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_PROCESS_SVG_ELEMENT, logLevel = LogLevelConstants.ERROR)
    })
    public void noSvgRootTest() {
        Element element = new Element(Tag.valueOf(TagConstants.FIGURE), TagConstants.FIGURE);
        IElementNode elementNode = new JsoupElementNode(element);

        ConverterProperties properties = new ConverterProperties();
        ProcessorContext context = new ProcessorContext(properties);

        SvgTagWorker svgTagWorker = new SvgTagWorker(elementNode, context);
        Assert.assertNull(svgTagWorker.getElementResult());
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_PROCESS_SVG_ELEMENT, logLevel = LogLevelConstants.ERROR)
    })
    public void nullElementTest() {
        ConverterProperties properties = new ConverterProperties();
        ProcessorContext context = new ProcessorContext(properties);

        SvgTagWorker svgTagWorker = new SvgTagWorker(null, context);
        Assert.assertNull(svgTagWorker.getElementResult());
    }
}
