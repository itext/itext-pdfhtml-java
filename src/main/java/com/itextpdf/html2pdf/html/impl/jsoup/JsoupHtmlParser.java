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
package com.itextpdf.html2pdf.html.impl.jsoup;

import com.itextpdf.html2pdf.html.IHtmlParser;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupDocument;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupElement;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupTextNode;
import com.itextpdf.html2pdf.html.node.IDocument;
import com.itextpdf.html2pdf.html.node.INode;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupHtmlParser implements IHtmlParser {

    private static Logger logger = LoggerFactory.getLogger(JsoupHtmlParser.class);

    @Override
    public IDocument parse(InputStream htmlStream, String charset) throws IOException {
        // TODO base URI
        org.jsoup.nodes.Document doc = Jsoup.parse(htmlStream, charset, "");
        INode result = wrapJsoupHierarchy(doc);
        if (result instanceof IDocument) {
            return (IDocument) result;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public IDocument parse(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        INode result = wrapJsoupHierarchy(doc);
        if (result instanceof IDocument) {
            return (IDocument) result;
        } else {
            throw new IllegalStateException();
        }
    }

    private INode wrapJsoupHierarchy(org.jsoup.nodes.Node jsoupNode) {
        INode resultNode = null;
        if (jsoupNode instanceof org.jsoup.nodes.Document) {
            resultNode = new JsoupDocument((Document) jsoupNode) ;
        } else if (jsoupNode instanceof org.jsoup.nodes.TextNode) {
            resultNode = new JsoupTextNode((TextNode) jsoupNode);
        } else if (jsoupNode instanceof org.jsoup.nodes.Element) {
            resultNode = new JsoupElement((Element) jsoupNode);
        } else {
            logger.error(MessageFormat.format("Could not map node type: {0}", jsoupNode.getClass()));
        }

        for (org.jsoup.nodes.Node node : jsoupNode.childNodes()) {
            resultNode.addChild(wrapJsoupHierarchy(node));
        }

        return resultNode;
    }
}
