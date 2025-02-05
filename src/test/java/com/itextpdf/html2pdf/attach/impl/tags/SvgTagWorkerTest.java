/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("UnitTest")
public class SvgTagWorkerTest extends ExtendedITextTest {

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_PROCESS_SVG_ELEMENT, logLevel = LogLevelConstants.ERROR)
    })
    public void noSvgRootTest() {
        Element element = new Element(Tag.valueOf(TagConstants.FIGURE), TagConstants.FIGURE);
        IElementNode elementNode = new JsoupElementNode(element);

        ConverterProperties properties = new ConverterProperties();
        ProcessorContext context = new ProcessorContext(properties);

        SvgTagWorker svgTagWorker = new SvgTagWorker(elementNode, context);
        Assertions.assertNull(svgTagWorker.getElementResult());
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_PROCESS_SVG_ELEMENT, logLevel = LogLevelConstants.ERROR)
    })
    public void nullElementTest() {
        ConverterProperties properties = new ConverterProperties();
        ProcessorContext context = new ProcessorContext(properties);

        SvgTagWorker svgTagWorker = new SvgTagWorker(null, context);
        Assertions.assertNull(svgTagWorker.getElementResult());
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_PROCESS_SVG_ELEMENT, count = 1)})
    public void unableToProcessSvgImageTest() {
        IElementNode elementNode = new JsoupElementNode(
                new Element(Tag.valueOf(TagConstants.FIGURE), TagConstants.FIGURE));
        ConverterProperties properties = new ConverterProperties();
        ProcessorContext context = new ProcessorContext(properties);
        SvgTagWorker tagWorker = new SvgTagWorker(elementNode, context);
        Assertions.assertNull(tagWorker.getElementResult());
    }
}
