/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
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

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.resolve.func.counter.CounterDigitsGlyphStyle;
import com.itextpdf.html2pdf.css.resolve.func.counter.CssCounterManager;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageCountElementNode;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageTargetCountElementNode;
import com.itextpdf.html2pdf.html.HtmlUtils;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.page.PageMarginRunningElementNode;
import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
import com.itextpdf.styledxmlparser.css.resolve.CssQuotes;
import com.itextpdf.styledxmlparser.css.util.CssGradientUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.css.util.EscapeGroup;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.node.ITextNode;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(CssContentPropertyResolver.class);

    private static final EscapeGroup[] ALLOWED_ESCAPE_CHARACTERS = {
            new EscapeGroup('\''), new EscapeGroup('\"'),
    };
    private static final int COUNTERS_MIN_PARAMS_SIZE = 2;
    private static final int COUNTER_MIN_PARAMS_SIZE = 1;
    private static final int TARGET_COUNTERS_MIN_PARAMS_SIZE = 3;
    private static final int TARGET_COUNTER_MIN_PARAMS_SIZE = 2;

    /**
     * Resolves content.
     *
     * @param styles           the styles map
     * @param contentContainer the content container
     * @param context          the CSS context
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
                continue;
            }
            if (token.getValue().startsWith(CssConstants.COUNTERS + "(")) {
                String paramsStr = token.getValue().substring(CssConstants.COUNTERS.length() + 1, token.getValue().length() - 1);
                final List<String> params = CssUtils.splitString(paramsStr, ',', ALLOWED_ESCAPE_CHARACTERS);
                if (params.size() < COUNTERS_MIN_PARAMS_SIZE) {
                    return errorFallback(contentStr);
                }
                // Counters are denoted by case-sensitive identifiers
                String counterName = params.get(0).trim();
                String counterSeparationStr = params.get(1).trim();
                counterSeparationStr = counterSeparationStr.substring(1, counterSeparationStr.length() - 1);
                final CounterDigitsGlyphStyle listStyleType = HtmlUtils.convertStringCounterGlyphStyleToEnum(
                        params.size() > COUNTERS_MIN_PARAMS_SIZE ? params.get(COUNTERS_MIN_PARAMS_SIZE).trim() : null);
                CssCounterManager counterManager = context.getCounterManager();
                INode scope = contentContainer;
                if (CssConstants.PAGE.equals(counterName)) {
                    result.add(new PageCountElementNode(false, contentContainer).setDigitsGlyphStyle(listStyleType));
                } else if (CssConstants.PAGES.equals(counterName)) {
                    result.add(new PageCountElementNode(true, contentContainer).setDigitsGlyphStyle(listStyleType));
                } else {
                    final String resolvedCounter =
                            counterManager.resolveCounters(counterName, counterSeparationStr, listStyleType);
                    result.add(new ContentTextNode(scope, resolvedCounter));
                }
            } else if (token.getValue().startsWith(CssConstants.COUNTER + "(")) {
                final String paramsStr = token.getValue()
                        .substring(CssConstants.COUNTER.length() + 1, token.getValue().length() - 1);
                final List<String> params = CssUtils.splitString(paramsStr, ',', ALLOWED_ESCAPE_CHARACTERS);
                if (params.size() < COUNTER_MIN_PARAMS_SIZE) {
                    return errorFallback(contentStr);
                }
                // Counters are denoted by case-sensitive identifiers
                String counterName = params.get(0).trim();
                final CounterDigitsGlyphStyle listStyleType = HtmlUtils.convertStringCounterGlyphStyleToEnum(
                        params.size() > COUNTER_MIN_PARAMS_SIZE ? params.get(COUNTER_MIN_PARAMS_SIZE).trim() : null);
                CssCounterManager counterManager = context.getCounterManager();
                INode scope = contentContainer;
                if (CssConstants.PAGE.equals(counterName)) {
                    result.add(new PageCountElementNode(false, contentContainer)
                            .setDigitsGlyphStyle(listStyleType));
                } else if (CssConstants.PAGES.equals(counterName)) {
                    result.add(new PageCountElementNode(true, contentContainer)
                            .setDigitsGlyphStyle(listStyleType));
                } else {
                    final String resolvedCounter = counterManager.resolveCounter(counterName, listStyleType);
                    result.add(new ContentTextNode(scope, resolvedCounter));
                }
            } else if (token.getValue().startsWith(CssConstants.TARGET_COUNTER + "(")) {
                String paramsStr = token.getValue()
                        .substring(CssConstants.TARGET_COUNTER.length() + 1, token.getValue().length() - 1);
                List<String> params = CssUtils.splitString(paramsStr, ',', ALLOWED_ESCAPE_CHARACTERS);
                if (params.size() < TARGET_COUNTER_MIN_PARAMS_SIZE) {
                    return errorFallback(contentStr);
                }
                final String target = params.get(0).startsWith(CommonCssConstants.ATTRIBUTE + "(") ? CssUtils
                        .extractAttributeValue(params.get(0), (IElementNode) contentContainer.parentNode())
                        : CssUtils.extractUrl(params.get(0));
                if (target == null) {
                    return errorFallback(contentStr);
                }
                final String counterName = params.get(1).trim();
                final CounterDigitsGlyphStyle listStyleType = HtmlUtils.convertStringCounterGlyphStyleToEnum(
                        params.size() > TARGET_COUNTER_MIN_PARAMS_SIZE ?
                        params.get(TARGET_COUNTER_MIN_PARAMS_SIZE).trim() : null);
                if (CssConstants.PAGE.equals(counterName)) {
                    result.add(new PageTargetCountElementNode(contentContainer, target)
                            .setDigitsGlyphStyle(listStyleType));
                } else if (CssConstants.PAGES.equals(counterName)) {
                    result.add(new PageCountElementNode(true, contentContainer)
                            .setDigitsGlyphStyle(listStyleType));
                } else {
                    final String counter = context.getCounterManager().resolveTargetCounter
                            (target.replace("'", "").replace("#", ""), counterName, listStyleType);
                    final ContentTextNode node = new ContentTextNode(contentContainer, counter == null ? "0" : counter);
                    result.add(node);
                }
            } else if (token.getValue().startsWith(CssConstants.TARGET_COUNTERS + "(")) {
                final String paramsStr = token.getValue()
                        .substring(CssConstants.TARGET_COUNTERS.length() + 1, token.getValue().length() - 1);
                final List<String> params = CssUtils.splitString(paramsStr, ',', ALLOWED_ESCAPE_CHARACTERS);
                if (params.size() < TARGET_COUNTERS_MIN_PARAMS_SIZE) {
                    return errorFallback(contentStr);
                }
                final String target = params.get(0).startsWith(CommonCssConstants.ATTRIBUTE + "(") ? CssUtils
                        .extractAttributeValue(params.get(0), (IElementNode) contentContainer.parentNode())
                        : CssUtils.extractUrl(params.get(0));
                if (target == null) {
                    return errorFallback(contentStr);
                }
                final String counterName = params.get(1).trim();
                String counterSeparator = params.get(2).trim();
                counterSeparator = counterSeparator.substring(1, counterSeparator.length() - 1);
                final CounterDigitsGlyphStyle listStyleType = HtmlUtils.convertStringCounterGlyphStyleToEnum(
                        params.size() > TARGET_COUNTERS_MIN_PARAMS_SIZE ?
                        params.get(TARGET_COUNTERS_MIN_PARAMS_SIZE).trim() : null);
                if (CssConstants.PAGE.equals(counterName)) {
                    result.add(new PageTargetCountElementNode(contentContainer, target)
                            .setDigitsGlyphStyle(listStyleType));
                } else if (CssConstants.PAGES.equals(counterName)) {
                    result.add(new PageCountElementNode(true, contentContainer)
                            .setDigitsGlyphStyle(listStyleType));
                } else {
                    final String counters = context.getCounterManager().resolveTargetCounters
                            (target.replace(",", "").replace("#", ""), counterName, counterSeparator, listStyleType);
                    final ContentTextNode node =
                            new ContentTextNode(contentContainer, counters == null ? "0" : counters);
                    result.add(node);
                }
            } else if (token.getValue().startsWith("url(")) {
                Map<String, String> attributes = new HashMap<>();
                attributes.put(AttributeConstants.SRC, CssUtils.extractUrl(token.getValue()));
                attributes.put(AttributeConstants.STYLE, CssConstants.DISPLAY + ":" + CssConstants.INLINE_BLOCK);
                result.add(new CssContentElementNode(contentContainer, TagConstants.IMG, attributes));
            } else if (CssGradientUtil.isCssLinearGradientValue(token.getValue())) {
                Map<String, String> attributes = new HashMap<>();
                attributes.put(AttributeConstants.STYLE, CssConstants.BACKGROUND_IMAGE + ":" + token.getValue() + ";"
                        + CssConstants.HEIGHT + ":" + CssConstants.INHERIT + ";"
                        + CssConstants.WIDTH + ":" + CssConstants.INHERIT + ";");
                result.add(new CssContentElementNode(contentContainer, TagConstants.DIV, attributes));
            } else if (token.getValue().startsWith(CommonCssConstants.ATTRIBUTE + "(")
                    && contentContainer instanceof CssPseudoElementNode) {
                String value = CssUtils
                        .extractAttributeValue(token.getValue(), (IElementNode) contentContainer.parentNode());
                if (value == null) {
                    return errorFallback(contentStr);
                }
                result.add(new ContentTextNode(contentContainer, value));
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
        return result;
    }

    /**
     * Resolves content in case of errors.
     *
     * @param contentStr the content
     * @return the resulting list of {@link INode} instances
     */
    private static List<INode> errorFallback(String contentStr) {
        int logMessageParameterMaxLength = 100;
        if (contentStr.length() > logMessageParameterMaxLength) {
            contentStr = contentStr.substring(0, logMessageParameterMaxLength) + ".....";
        }

        LOGGER.error(MessageFormatUtil.format(Html2PdfLogMessageConstant.CONTENT_PROPERTY_INVALID, contentStr));
        return null;
    }

    /**
     * {@link ITextNode} implementation for content text.
     */
    private static class ContentTextNode implements ITextNode {

        /**
         * The parent.
         */
        private final INode parent;

        /**
         * The content.
         */
        private String content;

        /**
         * Creates a new {@link ContentTextNode} instance.
         *
         * @param parent  the parent
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
