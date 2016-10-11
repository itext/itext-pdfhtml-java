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

import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.TagWorkerFactory;
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
            ITagWorker tagWorker = TagWorkerFactory.getTagWorker(((IElement) node), context);
            if (tagWorker == null) {
                logger.error("No worker found for tag " + ((IElement) node).name());
            } else {
                context.getState().push(tagWorker);
            }

            for (INode childNode : node.childNodes()) {
                visit(childNode);
            }

            if (tagWorker != null) {
                tagWorker.processEnd((IElement) node, context);
                context.getState().pop();

                ICssApplier cssApplier = CssApplierFactory.getCssApplier(((IElement) node).name());
                if (cssApplier == null) {
                    logger.error("No css applier found for tag " + ((IElement) node).name());
                } else {
                    cssApplier.apply(context, node, tagWorker);
                }

                if (!context.getState().empty()) {
                    boolean childProcessed = context.getState().top().processTagChild(tagWorker, context);
                    if (!childProcessed) {
                        logger.error(String.format( "Worker of type %s wasn't able to process %s",
                                context.getState().top().getClass().getName(), tagWorker.getClass().getName()));
                    }
                } else if (tagWorker.getElementResult() != null) {
                    roots.add(tagWorker.getElementResult());
                }
            }
        } else if (node instanceof ITextNode) {
            // TODO not exactly correct to trim like that; e.g. "<p>text<span>text</span>text</p>" and "<p>text\n<span>text</span>text</p>" produce different output in browser
            String content = trimContentAndNormalizeSpaces(((ITextNode) node).wholeText());
            if (content != null) {

                if (!context.getState().empty()) {
                    boolean contentProcessed = context.getState().top().processContent(content, context);
                    if (!contentProcessed) {
                        logger.error(String.format("Worker of type %s wasn't able to process it's text content",
                                context.getState().top().getClass().getName()));
                    }
                } else {
                    logger.error("No consumer found for content");
                }

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

    private String trimContentAndNormalizeSpaces(String content) {
        int start = 0;
        int end = content.length();
        while (start < end
                && isSpace(content.charAt(start))) {
            start++;
        }

        int firstNonSpaceCharIndex = end - 1;
        while (firstNonSpaceCharIndex >= start) {
            if (!isSpace(content.charAt(firstNonSpaceCharIndex))) {
                break;
            }

            firstNonSpaceCharIndex--;
        }
        end = firstNonSpaceCharIndex + 1;

        if (start == end) {
            return null;
        }

        String trimmed = content.substring(start, end);

        // TODO review
        // replace multiple space chars (and also single line breaks) with single space;
        String normalizedSpaceChars = trimmed.replaceAll("\\s+", " ");

        return normalizedSpaceChars;
    }

    private boolean isSpace(char ch) {
        return Character.isWhitespace(ch) || Character.isSpaceChar(ch);
    }

}
