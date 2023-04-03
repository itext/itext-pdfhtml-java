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
package com.itextpdf.html2pdf.css.media;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.StreamUtil;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.IXmlParser;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupHtmlParser;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupDocumentNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Category(UnitTest.class)
public class MediaRuleTest extends ExtendedITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/MediaRuleTest/";

    @BeforeClass
    public static void beforeClass() {
    }

    @Test
    public void test01() throws IOException {
        String htmlFileName = sourceFolder + "html01.html";
        String cssFileName = sourceFolder + "css01.css";
        IXmlParser htmlParser = new JsoupHtmlParser();
        IDocumentNode document = htmlParser.parse(new FileInputStream(htmlFileName), "UTF-8");
        CssStyleSheet css = CssStyleSheetParser.parse(new FileInputStream(cssFileName));
        MediaDeviceDescription deviceDescription = new MediaDeviceDescription(MediaType.PRINT);
        IElementNode element = new JsoupElementNode(((JsoupDocumentNode)document).getDocument().getElementsByTag("p").first());
        List<CssDeclaration> declarations = css.getCssDeclarations(element, deviceDescription);
        Assert.assertEquals(3, declarations.size());
        Assert.assertEquals("font-weight: bold", declarations.get(0).toString());
        Assert.assertEquals("color: red", declarations.get(1).toString());
        Assert.assertEquals("font-size: 20pt", declarations.get(2).toString());
    }

    @Test
    public void test02() throws IOException {
        String htmlFileName = sourceFolder + "html02.html";
        String cssFileName = sourceFolder + "css02.css";
        IXmlParser htmlParser = new JsoupHtmlParser();
        IDocumentNode document = htmlParser.parse(new FileInputStream(htmlFileName), "UTF-8");
        CssStyleSheet css = CssStyleSheetParser.parse(new FileInputStream(cssFileName));
        IElementNode element = new JsoupElementNode(((JsoupDocumentNode)document).getDocument().getElementsByTag("p").first());

        MediaDeviceDescription deviceDescription1 = new MediaDeviceDescription(MediaType.PRINT);
        deviceDescription1.setWidth(525);

        MediaDeviceDescription deviceDescription2 = new MediaDeviceDescription(MediaType.HANDHELD);
        deviceDescription2.setOrientation("landscape");

        List<CssDeclaration> declarations1 = css.getCssDeclarations(element, deviceDescription1);
        List<CssDeclaration> declarations2 = css.getCssDeclarations(element, deviceDescription2);

        Assert.assertTrue(declarations1.equals(declarations2));

        Assert.assertEquals(1, declarations1.size());
        Assert.assertEquals("font-weight: bold", declarations1.get(0).toString());
    }

    @Test
    public void test03() throws IOException {
        String htmlFileName = sourceFolder + "html03.html";
        String cssFileName = sourceFolder + "css03.css";
        IXmlParser htmlParser = new JsoupHtmlParser();
        IDocumentNode document = htmlParser.parse(new FileInputStream(htmlFileName), "UTF-8");
        CssStyleSheet css = CssStyleSheetParser.parse(new FileInputStream(cssFileName));
        MediaDeviceDescription deviceDescription = new MediaDeviceDescription(MediaType.PRINT);
        deviceDescription.setResolution(300);
        IElementNode element = new JsoupElementNode(((JsoupDocumentNode)document).getDocument().getElementsByTag("p").first());
        List<CssDeclaration> declarations = css.getCssDeclarations(element, deviceDescription);
        Assert.assertEquals(1, declarations.size());
        Assert.assertEquals("color: black", declarations.get(0).toString());
    }

    @Test
    public void test04() throws IOException {
        String htmlFileName = sourceFolder + "html04.html";
        String cssFileName = sourceFolder + "css04.css";
        IXmlParser htmlParser = new JsoupHtmlParser();
        IDocumentNode document = htmlParser.parse(new FileInputStream(htmlFileName), "UTF-8");
        CssStyleSheet css = CssStyleSheetParser.parse(new FileInputStream(cssFileName));

        MediaDeviceDescription deviceDescription = new MediaDeviceDescription(MediaType.PRINT).setColorIndex(256);

        IElementNode element = new JsoupElementNode(((JsoupDocumentNode)document).getDocument().getElementsByTag("p").first());
        List<CssDeclaration> declarations = css.getCssDeclarations(element, deviceDescription);
        Assert.assertEquals(2, declarations.size());
        Assert.assertEquals("color: red", declarations.get(0).toString());
        Assert.assertEquals("font-size: 20em", declarations.get(1).toString());
    }

    @Test
    public void test05() throws IOException {
        String htmlFileName = sourceFolder + "html05.html";
        String cssFileName = sourceFolder + "css05.css";
        IXmlParser htmlParser = new JsoupHtmlParser();
        IDocumentNode document = htmlParser.parse(new FileInputStream(htmlFileName), "UTF-8");
        CssStyleSheet css = CssStyleSheetParser.parse(new FileInputStream(cssFileName));
        IElementNode element = new JsoupElementNode(((JsoupDocumentNode)document).getDocument().getElementsByTag("p").first());

        MediaDeviceDescription deviceDescription1 = new MediaDeviceDescription(MediaType.PRINT).
                setWidth(300).setHeight(301);

        MediaDeviceDescription deviceDescription2 = new MediaDeviceDescription(MediaType.SCREEN).
                setWidth(400).setHeight(400);

        List<CssDeclaration> declarations1 = css.getCssDeclarations(element, deviceDescription1);
        List<CssDeclaration> declarations2 = css.getCssDeclarations(element, deviceDescription2);

        Assert.assertEquals(0, declarations1.size());

        Assert.assertEquals(1, declarations2.size());
        Assert.assertEquals("color: red", declarations2.get(0).toString());
    }

    @Test
    public void test06() throws IOException {
        String htmlFileName = sourceFolder + "html06.html";
        String cssFileName = sourceFolder + "css06.css";
        IXmlParser htmlParser = new JsoupHtmlParser();
        IDocumentNode document = htmlParser.parse(new FileInputStream(htmlFileName), "UTF-8");
        CssStyleSheet css = CssStyleSheetParser.parse(new FileInputStream(cssFileName));
        IElementNode element = new JsoupElementNode(((JsoupDocumentNode)document).getDocument().getElementsByTag("p").first());

        MediaDeviceDescription deviceDescription1 = new MediaDeviceDescription(MediaType.PRINT).
                setBitsPerComponent(2);

        MediaDeviceDescription deviceDescription2 = new MediaDeviceDescription(MediaType.HANDHELD).
                setBitsPerComponent(2);

        MediaDeviceDescription deviceDescription3 = new MediaDeviceDescription(MediaType.SCREEN).
                setBitsPerComponent(1);

        List<CssDeclaration> declarations1 = css.getCssDeclarations(element, deviceDescription1);
        List<CssDeclaration> declarations2 = css.getCssDeclarations(element, deviceDescription2);
        List<CssDeclaration> declarations3 = css.getCssDeclarations(element, deviceDescription3);

        Assert.assertTrue(declarations1.equals(declarations2));
        Assert.assertEquals(0, declarations3.size());

        Assert.assertEquals(1, declarations1.size());
        Assert.assertEquals("color: red", declarations1.get(0).toString());
    }

    @Test
    public void test07() throws IOException {
        String htmlFileName = sourceFolder + "html07.html";
        byte[] bytes = StreamUtil.inputStreamToArray(new FileInputStream(htmlFileName));
        String html = new String(bytes);
        MediaDeviceDescription printDevice = new MediaDeviceDescription(MediaType.PRINT);
        MediaDeviceDescription screenDevice = new MediaDeviceDescription(MediaType.SCREEN).setWidth(1000);

        List<IElement> printElements = HtmlConverter.convertToElements(html, new ConverterProperties().setMediaDeviceDescription(printDevice).setBaseUri(sourceFolder));
        List<IElement> screenElements = HtmlConverter.convertToElements(html, new ConverterProperties().setMediaDeviceDescription(screenDevice).setBaseUri(sourceFolder));

        Assert.assertEquals(12f, printElements.get(0).<UnitValue>getProperty(Property.FONT_SIZE).getValue(), 1e-10f);

        Assert.assertEquals(20f, screenElements.get(0).<UnitValue>getProperty(Property.FONT_SIZE).getValue(), 1e-10f);
    }


}
