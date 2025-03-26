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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.util.CounterProcessorUtil;
import com.itextpdf.html2pdf.css.util.CssStyleSheetAnalyzer;
import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.util.DecimalFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssFontFaceRule;
import com.itextpdf.styledxmlparser.css.CssRuleSet;
import com.itextpdf.styledxmlparser.css.CssStatement;
import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.css.media.CssMediaRule;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.css.parse.CssRuleSetParser;
import com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
import com.itextpdf.styledxmlparser.css.resolve.AbstractCssContext;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.css.resolve.CssInheritance;
import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssTypesValidationUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IDataNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.styledxmlparser.util.CssVariableUtil;
import com.itextpdf.styledxmlparser.util.StyleUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of the {@link ICssResolver} interface.
 */
public class DefaultCssResolver implements ICssResolver {

    /**
     * Css inheritance checker
     */
    private static final Set<IStyleInheritance> INHERITANCE_RULES = Collections.unmodifiableSet(new HashSet<>(
            Collections.singletonList((IStyleInheritance) new CssInheritance())));


    /**
     * The CSS style sheet.
     */
    private CssStyleSheet cssStyleSheet;

    /**
     * The device description.
     */
    private MediaDeviceDescription deviceDescription;

    /**
     * The list of fonts.
     */
    private List<CssFontFaceRule> fonts = new ArrayList<>();

    /**
     * Creates a new {@link DefaultCssResolver} instance.
     *
     * @param treeRoot               the root node
     * @param mediaDeviceDescription the media device description
     * @param resourceResolver       the resource resolver
     */
    public DefaultCssResolver(INode treeRoot, MediaDeviceDescription mediaDeviceDescription, ResourceResolver resourceResolver) {
        this.deviceDescription = mediaDeviceDescription;
        collectCssDeclarations(treeRoot, resourceResolver, null);
        collectFonts();
    }

    /**
     * Creates a new {@link DefaultCssResolver} instance.
     *
     * @param treeRoot the root node
     * @param context  the processor context
     */
    public DefaultCssResolver(INode treeRoot, ProcessorContext context) {
        this.deviceDescription = context.getDeviceDescription();
        collectCssDeclarations(treeRoot, context.getResourceResolver(), context.getCssContext());
        collectFonts();
    }

    /**
     * Gets the list of fonts.
     *
     * @return the list of {@link CssFontFaceRule} instances
     */
    public List<CssFontFaceRule> getFonts() {
        return fonts;
    }

    /**
     * Resolves content and counter(s) styles of a node given the passed context.
     *
     * @param node the node
     * @param context the CSS context (RootFontSize, etc.)
     */
    public void resolveContentAndCountersStyles(INode node, CssContext context) {
        final Map<String, String> elementStyles = resolveElementsStyles(node);
        CounterProcessorUtil.processCounters(elementStyles, context);
        resolveContentProperty(elementStyles, node, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> resolveStyles(INode element, AbstractCssContext context) {
        if (context instanceof CssContext) {
            return resolveStyles(element, (CssContext) context);
        }
        throw new Html2PdfException("custom AbstractCssContext implementations are not supported yet");
    }

    /**
     * Gets the CSS style sheet.
     *
     * @return the CSS style sheet
     */
    public CssStyleSheet getCssStyleSheet() {
        return cssStyleSheet;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.resolve.ICssResolver#resolveStyles(com.itextpdf.html2pdf.html.node.INode, com.itextpdf.html2pdf.css.resolve.CssContext)
     */
    private Map<String, String> resolveStyles(INode element, CssContext context) {
        Map<String, String> elementStyles = resolveElementsStyles(element);

        if (CssConstants.CURRENTCOLOR.equals(elementStyles.get(CssConstants.COLOR))) {
            // css-color-3/#currentcolor:
            // If the ‘currentColor’ keyword is set on the ‘color’ property itself, it is treated as ‘color: inherit’.
            elementStyles.put(CssConstants.COLOR, CssConstants.INHERIT);
        }

        String parentFontSizeStr = null;
        if (element.parentNode() instanceof IStylesContainer) {
            IStylesContainer parentNode = (IStylesContainer) element.parentNode();
            Map<String, String> parentStyles = parentNode.getStyles();

            if (parentStyles == null && !(element.parentNode() instanceof IDocumentNode)) {
                Logger logger = LoggerFactory.getLogger(DefaultCssResolver.class);
                logger.error(Html2PdfLogMessageConstant.ERROR_RESOLVING_PARENT_STYLES);
            }

            if (parentStyles != null) {
                for (Map.Entry<String, String> entry : parentStyles.entrySet()) {
                    elementStyles = StyleUtil
                            .mergeParentStyleDeclaration(elementStyles, entry.getKey(), entry.getValue(), parentStyles.get(
                            CommonCssConstants.FONT_SIZE), INHERITANCE_RULES);

                    // If the parent has display: flex, the flex item is blockified
                    // no matter what display value is set for it (except 'none' and 'grid' values).
                    // See CSS Flexible Box Layout Module Level 1,
                    // W3C Candidate Recommendation, 19 November 2018: 4. Flex Items.
                    final String currentElementDisplay = elementStyles.get(CssConstants.DISPLAY);
                    if (isFlexItem(entry, currentElementDisplay) &&
                            !CommonCssConstants.NONE.equals(currentElementDisplay) &&
                            !CommonCssConstants.GRID.equals(currentElementDisplay)) {
                        elementStyles.put(CssConstants.DISPLAY, CssConstants.BLOCK);
                    }
                }
                parentFontSizeStr = parentStyles.get(CssConstants.FONT_SIZE);
            }
        }

        CssVariableUtil.resolveCssVariables(elementStyles);

        String elementFontSize = elementStyles.get(CssConstants.FONT_SIZE);
        if (CssTypesValidationUtils.isRelativeValue(elementFontSize) || CssConstants.LARGER.equals(elementFontSize)
                || CssConstants.SMALLER.equals(elementFontSize)) {
            float baseFontSize;
            if (CssTypesValidationUtils.isRemValue(elementFontSize)) {
                baseFontSize = context.getRootFontSize();
            } else if (parentFontSizeStr == null) {
                baseFontSize = CssDimensionParsingUtils.parseAbsoluteFontSize(CssDefaults.getDefaultValue(CssConstants.FONT_SIZE));
            } else {
                baseFontSize = CssDimensionParsingUtils.parseAbsoluteLength(parentFontSizeStr);
            }
            float absoluteFontSize = CssDimensionParsingUtils.parseRelativeFontSize(elementFontSize, baseFontSize);
            // Format to 4 decimal places to prevent differences between Java and C#
            elementStyles.put(CssConstants.FONT_SIZE, DecimalFormatUtil.formatNumber(absoluteFontSize, "0.####") + CssConstants.PT);
        } else {
            elementStyles.put(CssConstants.FONT_SIZE, Float.toString(CssDimensionParsingUtils.parseAbsoluteFontSize(elementFontSize)) + CssConstants.PT);
        }

        // Update root font size
        if (element instanceof IElementNode && TagConstants.HTML.equals(((IElementNode) element).name())) {
            context.setRootFontSize(elementStyles.get(CssConstants.FONT_SIZE));
        }
        context.setCurrentFontSize(CssDimensionParsingUtils.parseAbsoluteFontSize(elementStyles.get(CssConstants.FONT_SIZE)));

        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, String> entry : elementStyles.entrySet()) {
            if (CssConstants.INITIAL.equals(entry.getValue())
                    || CssConstants.INHERIT.equals(entry.getValue())) { // if "inherit" is not resolved till now, parents don't have it
                keys.add(entry.getKey());
            }
        }
        for (String key : keys) {
            elementStyles.put(key, CssDefaults.getDefaultValue(key));
        }

        // This is needed for correct resolving of content property, so doing it right here
        CounterProcessorUtil.processCounters(elementStyles, context);
        resolveContentProperty(elementStyles, element, context);

        return elementStyles;
    }

    private Map<String, String> resolveElementsStyles(INode element) {
        List<CssRuleSet> ruleSets = new ArrayList<>();
        ruleSets.add(new CssRuleSet(null, UserAgentCss.getStyles(element)));
        if (element instanceof IElementNode) {
            ruleSets.add(new CssRuleSet(null, HtmlStylesToCssConverter.convert((IElementNode) element)));
        }
        ruleSets.addAll(cssStyleSheet.getCssRuleSets(element, deviceDescription));
        if (element instanceof IElementNode) {
            String styleAttribute = ((IElementNode) element).getAttribute(AttributeConstants.STYLE);
            if (styleAttribute != null) {
                ruleSets.add(new CssRuleSet(null, CssRuleSetParser.parsePropertyDeclarations(styleAttribute)));
            }
        }
        return CssStyleSheet.extractStylesFromRuleSets(ruleSets);
    }

    /**
     * Resolves a content property.
     *
     * @param styles           the styles map
     * @param contentContainer the content container
     * @param context          the CSS context
     */
    private void resolveContentProperty(Map<String, String> styles, INode contentContainer, CssContext context) {
        if (contentContainer instanceof CssPseudoElementNode || contentContainer instanceof PageMarginBoxContextNode) {
            List<INode> resolvedContent = CssContentPropertyResolver.resolveContent(styles, contentContainer, context);
            if (resolvedContent != null) {
                for (INode child : resolvedContent) {
                    contentContainer.addChild(child);
                }
            }
        }
        if (contentContainer instanceof IElementNode) {
            context.getCounterManager().addTargetCounterIfRequired((IElementNode) contentContainer);
            context.getCounterManager().addTargetCountersIfRequired((IElementNode) contentContainer);
        }
    }

    /**
     * Collects CSS declarationss.
     *
     * @param rootNode         the root node
     * @param resourceResolver the resource resolver
     * @param cssContext       the CSS context
     */
    private void collectCssDeclarations(INode rootNode, ResourceResolver resourceResolver, CssContext cssContext) {
        cssStyleSheet = new CssStyleSheet();
        LinkedList<INode> q = new LinkedList<>();
        q.add(rootNode);
        while (!q.isEmpty()) {
            INode currentNode = q.pop();
            if (currentNode instanceof IElementNode) {
                IElementNode element = (IElementNode) currentNode;
                if (TagConstants.STYLE.equals(element.name())) {
                    if (!element.childNodes().isEmpty() && element.childNodes().get(0) instanceof IDataNode) {
                        String styleData = ((IDataNode) element.childNodes().get(0)).getWholeData();
                        CssStyleSheet styleSheet  = CssStyleSheetParser.parse(styleData, resourceResolver.getBaseUri());
                        styleSheet = wrapStyleSheetInMediaQueryIfNecessary(element, styleSheet);
                        cssStyleSheet.appendCssStyleSheet(styleSheet);
                    }
                } else if (CssUtils.isStyleSheetLink(element)) {
                    String styleSheetUri = element.getAttribute(AttributeConstants.HREF);
                    try (InputStream stream = resourceResolver.retrieveResourceAsInputStream(styleSheetUri)) {
                        if (stream != null) {
                            String baseUri = resourceResolver.resolveAgainstBaseUri(styleSheetUri).toExternalForm();
                            CssStyleSheet styleSheet = CssStyleSheetParser.parse(stream, baseUri);
                            styleSheet = wrapStyleSheetInMediaQueryIfNecessary(element, styleSheet);
                            cssStyleSheet.appendCssStyleSheet(styleSheet);
                        }
                    } catch (Exception exc) {
                        Logger logger = LoggerFactory.getLogger(DefaultCssResolver.class);
                        logger.error(Html2PdfLogMessageConstant.UNABLE_TO_PROCESS_EXTERNAL_CSS_FILE, exc);
                    }
                }
            }

            for (INode child : currentNode.childNodes()) {
                if (child instanceof IElementNode) {
                    q.add(child);
                }
            }
        }
        enablePagesCounterIfMentioned(cssStyleSheet, cssContext);
        enableNonPageTargetCounterIfMentioned(cssStyleSheet, cssContext);
    }

    private static boolean isFlexItem(Entry<String, String> parentEntry, String currentElementDisplay) {
        return CssConstants.DISPLAY.equals(parentEntry.getKey())
                && CssConstants.FLEX.equals(parentEntry.getValue())
                && !CssConstants.FLEX.equals(currentElementDisplay);
    }

    /**
     * Check if a non-page(s) target-counter(s) is mentioned and enables it.
     *
     * @param styleSheet the stylesheet to analyze
     * @param cssContext the CSS context
     */
    private static void enableNonPageTargetCounterIfMentioned(CssStyleSheet styleSheet, CssContext cssContext) {
        if (CssStyleSheetAnalyzer.checkNonPagesTargetCounterPresence(styleSheet)) {
            cssContext.setNonPagesTargetCounterPresent(true);
        }
    }

    /**
     * Check if a pages counter is mentioned and enables it.
     *
     * @param styleSheet the stylesheet to analyze
     * @param cssContext the CSS context
     */
    private static void enablePagesCounterIfMentioned(CssStyleSheet styleSheet, CssContext cssContext) {
        // The presence of counter(pages) means that theoretically relayout may be needed.
        // We don't know it yet because that selector might not even be used, but
        // when we know it for sure, it's too late because the Document is created right in the start.
        if (CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet)) {
            cssContext.setPagesCounterPresent(true);
        }
    }

    /**
     * Wraps a {@link CssMediaRule} into the style sheet if the head child element has a media attribute.
     *
     * @param headChildElement the head child element
     * @param styleSheet       the style sheet
     * @return the css style sheet
     */
    private CssStyleSheet wrapStyleSheetInMediaQueryIfNecessary(IElementNode headChildElement, CssStyleSheet styleSheet) {
        String mediaAttribute = headChildElement.getAttribute(AttributeConstants.MEDIA);
        if (mediaAttribute != null && mediaAttribute.length() > 0) {
            CssMediaRule mediaRule = new CssMediaRule(mediaAttribute);
            mediaRule.addStatementsToBody(styleSheet.getStatements());
            styleSheet = new CssStyleSheet();
            styleSheet.addStatement(mediaRule);
        }
        return styleSheet;
    }

    /**
     * Collects fonts from the style sheet.
     */
    private void collectFonts() {
        for (CssStatement cssStatement : cssStyleSheet.getStatements()) {
            collectFonts(cssStatement);
        }
    }

    /**
     * Collects fonts from a {@link CssStatement}.
     *
     * @param cssStatement the CSS statement
     */
    private void collectFonts(CssStatement cssStatement) {
        if (cssStatement instanceof CssFontFaceRule) {
            fonts.add((CssFontFaceRule) cssStatement);
        } else if (cssStatement instanceof CssMediaRule && ((CssMediaRule) cssStatement).matchMediaDevice(deviceDescription)) {
            for (CssStatement cssSubStatement : ((CssMediaRule) cssStatement).getStatements()) {
                collectFonts(cssSubStatement);
            }
        }
    }
}
