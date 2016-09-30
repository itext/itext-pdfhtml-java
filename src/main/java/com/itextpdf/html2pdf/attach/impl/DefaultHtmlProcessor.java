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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.attach.IContentProcessor;
import com.itextpdf.html2pdf.attach.IElementProcessor;
import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.ICSSResolver;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElement;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DefaultHtmlProcessor implements IHtmlProcessor {

    private ProcessorContext context;
    private ICSSResolver resolver;
    private INode root;
    private IElementProcessor elementProcessor;
    private IContentProcessor contentProcessor;
    private List<IPropertyContainer> roots;

    public DefaultHtmlProcessor(INode node, ICSSResolver cssResolver) {
        this.resolver = cssResolver;
        this.root = node;
    }

    @Override
    public List<com.itextpdf.layout.element.IElement> processElements() {
        elementProcessor = new DefaultElementProcessor();
        contentProcessor = new DefaultContentProcessor();
        context = new ProcessorContext();
        roots = new ArrayList<>();
        root = findBodyNode(root);
        visit(root);
        elementProcessor = null;
        contentProcessor = null;
        context = null;
        List<com.itextpdf.layout.element.IElement> elements = new ArrayList<>();
        for (IPropertyContainer propertyContainer : roots) {
            if (propertyContainer instanceof com.itextpdf.layout.element.IElement) {
                elements.add((com.itextpdf.layout.element.IElement) propertyContainer);
            }
        }
        roots = null;
        return elements;
    }

    @Override
    public Document processDocument(PdfDocument pdfDocument) {
        elementProcessor = new DefaultElementProcessor();
        contentProcessor = new DefaultContentProcessor();
        context = new ProcessorContext(pdfDocument);
        roots = new ArrayList<>();
        visit(root);
        elementProcessor = null;
        contentProcessor = null;
        context = null;
        Document doc = (Document) roots.get(0);
        roots = null;
        return doc;
    }

    private void visit(INode node) {
        if (node instanceof IElement) {
            IPropertyContainer element = elementProcessor.processElementStart((IElement) node, context);

            for (INode childNode : node.childNodes()) {
                visit(childNode);
            }

            elementProcessor.processElementEnd((IElement) node, context, element);

            if (element != null && context.getState().empty()) {
                roots.add(element);
            }
        } else if (node instanceof ITextNode) {
            contentProcessor.processContent(((ITextNode) node).wholeText(), context);
        }
    }

    private INode findBodyNode(INode node) {
        Queue<INode> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            INode currentNode = q.poll();
            if (currentNode instanceof IElement && ((IElement) currentNode).name().equals(TagConstants.BODY)) {
                return currentNode;
            }
            for (INode child : currentNode.childNodes()) {
                if (child instanceof IElement) {
                    q.add(child);
                }
            }
        }
        return null;
    }

}
