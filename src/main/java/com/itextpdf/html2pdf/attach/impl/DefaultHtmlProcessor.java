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

import com.itextpdf.html2pdf.attach.ElementResult;
import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.ITagProcessor;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.TagProcessingResult;
import com.itextpdf.html2pdf.attach.TagProcessorFactory;
import com.itextpdf.html2pdf.css.apply.CssApplierFactory;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHtmlProcessor implements IHtmlProcessor {

    private static Logger logger = LoggerFactory.getLogger(DefaultHtmlProcessor.class);

    private ProcessorContext context;
    private ICssResolver resolver;
    private INode root;
    private List<IPropertyContainer> roots;

    public DefaultHtmlProcessor(INode node, ICssResolver cssResolver) {
        this.resolver = cssResolver;
        this.root = node;
    }

    @Override
    public List<com.itextpdf.layout.element.IElement> processElements() {
        context = new ProcessorContext(resolver);
        roots = new ArrayList<>();
        root = findBodyNode(root);
        visit(root);
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
        context = new ProcessorContext(resolver, pdfDocument);
        roots = new ArrayList<>();
        visit(root);
        context = null;
        Document doc = (Document) roots.get(0);
        roots = null;
        return doc;
    }

    private void visit(INode node) {
        if (node instanceof IElement) {
            ITagProcessor processor = TagProcessorFactory.getTagProcessor(((IElement) node).name());
            if (processor == null) {
                logger.error("No processor found for tag " + ((IElement) node).name());
            }
            TagProcessingResult result = null;
            if (processor != null) {
                result = processor.processStart((IElement) node, context);
            }

            for (INode childNode : node.childNodes()) {
                visit(childNode);
            }

            if (processor != null) {
                result = processor.processEnd((IElement) node, context, result);
            }

            ICssApplier cssApplier = CssApplierFactory.getCssApplier(((IElement) node).name());
            if (cssApplier == null) {
                logger.error("No css applier found for tag " + ((IElement) node).name());
            }

            if (cssApplier != null) {
                cssApplier.apply(context, node, result);
            }

            if (result instanceof ElementResult && context.getState().empty()) {
                roots.add(((ElementResult) result).getElement());
            }
        } else if (node instanceof ITextNode) {
            INode parent = node.parentNode();
            if (parent instanceof IElement) {
                ITagProcessor processor = TagProcessorFactory.getTagProcessor(((IElement) parent).name());
                if (processor == null) {
                    logger.error("No processor found for tag " + (((IElement) parent).name()));
                } else {
                    processor.processContent(((ITextNode) node).wholeText(), context);
                }
            } else {
                logger.error("Error adding content");
            }
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
