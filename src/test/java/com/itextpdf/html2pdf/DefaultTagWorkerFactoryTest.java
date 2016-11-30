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
package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.attach.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.kernel.Version;
import com.itextpdf.test.ExtendedITextTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SamuelHuylebroeck on 11/30/2016.
 */
public class DefaultTagWorkerFactoryTest extends ExtendedITextTest {


    @BeforeClass
    public static void beforeClass() {
    }

    @Test
    public void RegisterTest(){
        String tag = "dummy";
        String nameSpace = DivTagWorker.class.getName();
        String snippet = "<dummy><p>Hello</p></dummy>";

        Document doc = Jsoup.parse(snippet);
        Map<String,String> styles = new ConcurrentHashMap<>();
        styles.put(CssConstants.WHITE_SPACE, "");
        styles.put(CssConstants.TEXT_TRANSFORM,"");
        IElementNode testNode = new JsoupElementNode(doc.getElementsByTag(tag).get(0));
        testNode.setStyles(styles);
        String expected = DivTagWorker.class.getName();

        DefaultTagWorkerFactory df = new DefaultTagWorkerFactory();
        df.registerTagWorker(tag,nameSpace);

        ITagWorker tw = df.getTagWorkerInstance(testNode,null);
        Assert.assertEquals(tw.getClass().getName(),expected);
    }

    @Test
    public void OverWriteTest(){
        String tag = TagConstants.DIV;
        String nameSpace = SpanTagWorker.class.getName();
        String expected = SpanTagWorker.class.getName();

        String snippet = "<"+tag+"><p>Hello</p></"+tag+">";
        Document doc = Jsoup.parse(snippet);
        Map<String,String> styles = new ConcurrentHashMap<>();
        styles.put(CssConstants.WHITE_SPACE, "");
        styles.put(CssConstants.TEXT_TRANSFORM,"");
        IElementNode testNode = new JsoupElementNode(doc.getElementsByTag(tag).get(0));
        testNode.setStyles(styles);

        DefaultTagWorkerFactory df = new DefaultTagWorkerFactory();
        df.registerTagWorker(tag,nameSpace);

        ITagWorker tw = df.getTagWorkerInstance(testNode,null);
        Assert.assertEquals(tw.getClass().getName(),expected);

    }
    @Test
    public void RemoveTagWorkerTest(){
        String tag = TagConstants.DIV;

        String snippet = "<"+tag+"><p>Hello</p></"+tag+">";
        Document doc = Jsoup.parse(snippet);
        Map<String,String> styles = new ConcurrentHashMap<>();
        styles.put(CssConstants.WHITE_SPACE, "");
        styles.put(CssConstants.TEXT_TRANSFORM,"");
        IElementNode testNode = new JsoupElementNode(doc.getElementsByTag(tag).get(0));
        testNode.setStyles(styles);

        DefaultTagWorkerFactory df = new DefaultTagWorkerFactory();
        df.removetagWorker(tag);

        ITagWorker tw = df.getTagWorkerInstance(testNode,null);
        Assert.assertNull(tw);

    }
}
