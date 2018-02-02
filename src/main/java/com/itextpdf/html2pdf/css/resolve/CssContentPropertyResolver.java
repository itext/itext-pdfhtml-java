/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.page.PageMarginRunningElementNode;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.parse.CssDeclarationValueTokenizer;
import com.itextpdf.html2pdf.css.pseudo.CssPseudoElementNode;
import com.itextpdf.html2pdf.css.resolve.func.counter.CssCounterManager;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageCountElementNode;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.html2pdf.html.node.ITextNode;
import com.itextpdf.io.util.MessageFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class CssContentPropertyResolver.
 */
class CssContentPropertyResolver {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(CssContentPropertyResolver.class);

    /**
     * Resolves content.
     *
     * @param styles the styles map
     * @param contentContainer the content container
     * @param context the CSS context
     * @return a list of {@link INode} instances
     */
    static List<INode> resolveContent(Map<String, String> styles, INode contentContainer, CssContext context) {
        String contentStr = styles.get(CssConstants.CONTENT);
        List<INode> result = new ArrayList<>();
        if (contentStr == null || CssConstants.NONE.equals(contentStr) || CssConstants.NORMAL.equals(contentStr)) {
            return null;
        }
        CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(contentStr);
        CssDeclarationValueTokenizer.Token token;
        CssQuotes quotes = null;
        while ((token = tokenizer.getNextValidToken()) != null) {
            if (token.isString()) {
                result.add(new ContentTextNode(contentContainer, token.getValue()));
            } else {
                if (token.getValue().startsWith(CssConstants.COUNTERS + "(")) {
                    String paramsStr = token.getValue().substring(CssConstants.COUNTERS.length() + 1, token.getValue().length() - 1);
                    String[] params = paramsStr.split(",");
                    if (params.length == 0) {
                        return errorFallback(contentStr);
                    }
                    // Counters are denoted by case-sensitive identifiers
                    String counterName = params[0].trim();
                    String counterSeparationStr = params[1].trim();
                    counterSeparationStr = counterSeparationStr.substring(1, counterSeparationStr.length() - 1);
                    String listStyleType = params.length > 2 ? params[2].trim() : null;
                    CssCounterManager counterManager = context.getCounterManager();
                    INode scope = contentContainer;
                    if (CssConstants.PAGE.equals(counterName)) {
                        result.add(new PageCountElementNode(false, contentContainer));
                    } else if (CssConstants.PAGES.equals(counterName)) {
                        result.add(new PageCountElementNode(true, contentContainer));
                    } else {
                        String resolvedCounter = counterManager.resolveCounters(counterName, counterSeparationStr, listStyleType, scope);
                        if (resolvedCounter == null) {
                            logger.error(MessageFormatUtil.format(LogMessageConstant.UNABLE_TO_RESOLVE_COUNTER, counterName));
                        } else {
                            result.add(new ContentTextNode(scope, resolvedCounter));
                        }
                    }
                } else if (token.getValue().startsWith(CssConstants.COUNTER + "(")) {
                    String paramsStr = token.getValue().substring(CssConstants.COUNTER.length() + 1, token.getValue().length() - 1);
                    String[] params = paramsStr.split(",");
                    if (params.length == 0) {
                        return errorFallback(contentStr);
                    }
                    // Counters are denoted by case-sensitive identifiers
                    String counterName = params[0].trim();
                    String listStyleType = params.length > 1 ? params[1].trim() : null;
                    CssCounterManager counterManager = context.getCounterManager();
                    INode scope = contentContainer;
                    if (CssConstants.PAGE.equals(counterName)) {
                        result.add(new PageCountElementNode(false, contentContainer));
                    } else if (CssConstants.PAGES.equals(counterName)) {
                        result.add(new PageCountElementNode(true, contentContainer));
                    } else {
                        String resolvedCounter = counterManager.resolveCounter(counterName, listStyleType, scope);
                        if (resolvedCounter == null) {
                            logger.error(MessageFormatUtil.format(LogMessageConstant.UNABLE_TO_RESOLVE_COUNTER, counterName));
                        } else {
                            result.add(new ContentTextNode(scope, resolvedCounter));
                        }
                    }
                } else if (token.getValue().startsWith("url(")) {
                    Map<String, String> attributes = new HashMap<>();
                    attributes.put(AttributeConstants.SRC, CssUtils.extractUrl(token.getValue()));
                    //TODO: probably should add user agent styles on CssContentElementNode creation, not here.
                    attributes.put(AttributeConstants.STYLE, CssConstants.DISPLAY + ":" + CssConstants.INLINE_BLOCK);
                    result.add(new CssContentElementNode(contentContainer, TagConstants.IMG, attributes));
                } else if (token.getValue().startsWith("attr(") && contentContainer instanceof CssPseudoElementNode) {
                    int endBracket = token.getValue().indexOf(')');
                    if (endBracket > 5 ) {
                        String attrName = token.getValue().substring(5, endBracket);
                        if (attrName.contains("(") || attrName.contains(" ")
                                || attrName.contains("'") || attrName.contains("\"")) {
                            return errorFallback(contentStr);
                        }
                        IElementNode element = (IElementNode) contentContainer.parentNode();
                        String value = element.getAttribute(attrName);
                        result.add(new ContentTextNode(contentContainer, value == null ? "" : value));
                    }
                } else if (token.getValue().endsWith("quote") && contentContainer instanceof IStylesContainer) {
                    if (quotes == null) {
                        quotes = CssQuotes.createQuotes(styles.get(CssConstants.QUOTES), true);
                    }
                    String value = quotes.resolveQuote(token.getValue(), context);
                    if (value == null) {
                        return errorFallback(contentStr);
                    }
                    result.add(new ContentTextNode(contentContainer, value));
                } else if (token.getValue().startsWith(CssConstants.ELEMENT + "(") && contentContainer instanceof PageMarginBoxContextNode) {
                    String paramsStr = token.getValue().substring(CssConstants.ELEMENT.length() + 1, token.getValue().length() - 1);
                    String[] params = paramsStr.split(",");
                    if (params.length == 0) {
                        return errorFallback(contentStr);
                    }
                    String name = params[0].trim();
                    String runningElementOccurrence = null;
                    if (params.length > 1) {
                        runningElementOccurrence = params[1].trim();
                    }

                    result.add(new PageMarginRunningElementNode(name, runningElementOccurrence));
                } else {
                    return errorFallback(contentStr);
                }
            }
        }
        return result;
    }

    /**
     * Resolves content in case of errors.
     *
     * @param contentStr the content
     * @return the resulting list of {@link INode} instances
     */
    private static List<INode> errorFallback(String contentStr) {
        Logger logger = LoggerFactory.getLogger(CssContentPropertyResolver.class);

        int logMessageParameterMaxLength = 100;
        if (contentStr.length() > logMessageParameterMaxLength) {
            contentStr = contentStr.substring(0, logMessageParameterMaxLength) + ".....";
        }

        logger.error(MessageFormatUtil.format(LogMessageConstant.CONTENT_PROPERTY_INVALID, contentStr));
        return null;
    }

    /**
     * {@link ITextNode} implementation for content text.
     */
    private static class ContentTextNode implements ITextNode {

        /** The parent. */
        private final INode parent;

        /** The content. */
        private String content;

        /**
         * Creates a new {@link ContentTextNode} instance.
         *
         * @param parent the parent
         * @param content the content
         */
        ContentTextNode(INode parent, String content) {
            this.parent = parent;
            this.content = content;
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.INode#childNodes()
         */
        @Override
        public List<INode> childNodes() {
            return Collections.<INode>emptyList();
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.INode#addChild(com.itextpdf.html2pdf.html.node.INode)
         */
        @Override
        public void addChild(INode node) {
            throw new UnsupportedOperationException();
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.INode#parentNode()
         */
        @Override
        public INode parentNode() {
            return parent;
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.ITextNode#wholeText()
         */
        @Override
        public String wholeText() {
            return content;
        }

    }

}
