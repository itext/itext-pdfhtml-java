/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css.media;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.CssDeclaration;
import com.itextpdf.html2pdf.css.CssStyleSheet;
import com.itextpdf.html2pdf.css.parse.CssStyleSheetParser;
import com.itextpdf.html2pdf.html.IHtmlParser;
import com.itextpdf.html2pdf.html.impl.jsoup.JsoupHtmlParser;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupDocumentNode;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.io.util.StreamUtil;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
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
        IHtmlParser htmlParser = new JsoupHtmlParser();
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
        IHtmlParser htmlParser = new JsoupHtmlParser();
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
        IHtmlParser htmlParser = new JsoupHtmlParser();
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
        IHtmlParser htmlParser = new JsoupHtmlParser();
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
        IHtmlParser htmlParser = new JsoupHtmlParser();
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
        IHtmlParser htmlParser = new JsoupHtmlParser();
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
