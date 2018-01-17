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
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssDeclaration;
import com.itextpdf.html2pdf.css.CssFontFaceRule;
import com.itextpdf.html2pdf.css.CssStatement;
import com.itextpdf.html2pdf.css.CssStyleSheet;
import com.itextpdf.html2pdf.css.apply.util.CounterProcessorUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.media.CssMediaRule;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.parse.CssRuleSetParser;
import com.itextpdf.html2pdf.css.parse.CssStyleSheetParser;
import com.itextpdf.html2pdf.css.pseudo.CssPseudoElementNode;
import com.itextpdf.html2pdf.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.ShorthandResolverFactory;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.css.validate.CssDeclarationValidationMaster;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.HtmlUtils;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IDataNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.html2pdf.resolver.resource.ResourceResolver;
import com.itextpdf.io.util.DecimalFormatUtil;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.io.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Default implementation of the {@link ICssResolver} interface.
 */
public class DefaultCssResolver implements ICssResolver {

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

    private static final List<String> fontSizeDependentPercentage = new ArrayList<String>(2);

    static {
        fontSizeDependentPercentage.add(CssConstants.FONT_SIZE);
        fontSizeDependentPercentage.add(CssConstants.LINE_HEIGHT);
    }

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

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.resolve.ICssResolver#resolveStyles(com.itextpdf.html2pdf.html.node.INode, com.itextpdf.html2pdf.css.resolve.CssContext)
     */
    @Override
    public Map<String, String> resolveStyles(INode element, CssContext context) {
        List<CssDeclaration> nodeCssDeclarations = UserAgentCss.getStyles(element);
        if (element instanceof IElementNode) {
            nodeCssDeclarations.addAll(HtmlStylesToCssConverter.convert((IElementNode) element));
        }
        nodeCssDeclarations.addAll(cssStyleSheet.getCssDeclarations(element, deviceDescription));

        if (element instanceof IElementNode) {
            String styleAttribute = ((IElementNode) element).getAttribute(AttributeConstants.STYLE);
            if (styleAttribute != null) {
                nodeCssDeclarations.addAll(CssRuleSetParser.parsePropertyDeclarations(styleAttribute));
            }
        }

        Map<String, String> elementStyles = cssDeclarationsToMap(nodeCssDeclarations);

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
                logger.error(LogMessageConstant.ERROR_RESOLVING_PARENT_STYLES);
            }

            if (parentStyles != null) {
                for (Map.Entry<String, String> entry : parentStyles.entrySet()) {
                    mergeParentCssDeclaration(elementStyles, entry.getKey(), entry.getValue(), parentStyles);
                }
                parentFontSizeStr = parentStyles.get(CssConstants.FONT_SIZE);
            }
        }

        String elementFontSize = elementStyles.get(CssConstants.FONT_SIZE);
        if (CssUtils.isRelativeValue(elementFontSize) || CssConstants.LARGER.equals(elementFontSize) || CssConstants.SMALLER.equals(elementFontSize)) {
            float baseFontSize;
            if (CssUtils.isRemValue(elementFontSize)) {
                baseFontSize = context.getRootFontSize();
            } else {
                if (parentFontSizeStr == null) {
                    baseFontSize = FontStyleApplierUtil.parseAbsoluteFontSize(CssDefaults.getDefaultValue(CssConstants.FONT_SIZE));
                } else {
                    baseFontSize = CssUtils.parseAbsoluteLength(parentFontSizeStr);
                }
            }
            float absoluteFontSize = FontStyleApplierUtil.parseRelativeFontSize(elementFontSize, baseFontSize);
            // Format to 4 decimal places to prevent differences between Java and C#
            elementStyles.put(CssConstants.FONT_SIZE, DecimalFormatUtil.formatNumber(absoluteFontSize, "0.####") + CssConstants.PT);
        } else {
            elementStyles.put(CssConstants.FONT_SIZE, Float.toString(FontStyleApplierUtil.parseAbsoluteFontSize(elementFontSize)) + CssConstants.PT);
        }

        // Update root font size
        if (element instanceof IElementNode && TagConstants.HTML.equals(((IElementNode) element).name())) {
            context.setRootFontSize(elementStyles.get(CssConstants.FONT_SIZE));
        }

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
        CounterProcessorUtil.processCounters(elementStyles, context, element);
        resolveContentProperty(elementStyles, element, context);

        return elementStyles;
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
    }

    /**
     * Converts a list of {@link CssDeclaration} instances to a map consisting of {@link String} key-value pairs.
     *
     * @param nodeCssDeclarations the node css declarations
     * @return the map
     */
    private Map<String, String> cssDeclarationsToMap(List<CssDeclaration> nodeCssDeclarations) {
        Map<String, String> stylesMap = new HashMap<>();
        for (CssDeclaration cssDeclaration : nodeCssDeclarations) {
            IShorthandResolver shorthandResolver = ShorthandResolverFactory.getShorthandResolver(cssDeclaration.getProperty());
            if (shorthandResolver == null) {
                putDeclarationInMapIfValid(stylesMap, cssDeclaration);
            } else {
                List<CssDeclaration> resolvedShorthandProps = shorthandResolver.resolveShorthand(cssDeclaration.getExpression());
                for (CssDeclaration resolvedProp : resolvedShorthandProps) {
                    putDeclarationInMapIfValid(stylesMap, resolvedProp);
                }
            }
        }
        return stylesMap;
    }

    /**
     * Adds a CSS declaration to a styles map if the CSS declaration is valid.
     *
     * @param stylesMap      the styles map
     * @param cssDeclaration the CSS declaration
     */
    private void putDeclarationInMapIfValid(Map<String, String> stylesMap, CssDeclaration cssDeclaration) {
        if (CssDeclarationValidationMaster.checkDeclaration(cssDeclaration)) {
            stylesMap.put(cssDeclaration.getProperty(), cssDeclaration.getExpression());
        } else {
            Logger logger = LoggerFactory.getLogger(DefaultCssResolver.class);
            logger.warn(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, cssDeclaration));
        }
    }

    /**
     * Collects CSS declarationss.
     *
     * @param rootNode         the root node
     * @param resourceResolver the resource resolver
     * @param cssContext       the CSS context
     * @return the node (always null in this case)
     */
    private INode collectCssDeclarations(INode rootNode, ResourceResolver resourceResolver, CssContext cssContext) {
        cssStyleSheet = new CssStyleSheet();
        LinkedList<INode> q = new LinkedList<>();
        q.add(rootNode);
        while (!q.isEmpty()) {
            INode currentNode = q.getFirst();
            q.removeFirst();
            if (currentNode instanceof IElementNode) {
                IElementNode headChildElement = (IElementNode) currentNode;
                if (headChildElement.name().equals(TagConstants.STYLE)) {
                    if (currentNode.childNodes().size() > 0 && currentNode.childNodes().get(0) instanceof IDataNode) {
                        String styleData = ((IDataNode) currentNode.childNodes().get(0)).getWholeData();
                        checkIfPagesCounterMentioned(styleData, cssContext);
                        CssStyleSheet styleSheet = CssStyleSheetParser.parse(styleData);
                        styleSheet = wrapStyleSheetInMediaQueryIfNecessary(headChildElement, styleSheet);
                        cssStyleSheet.appendCssStyleSheet(styleSheet);
                    }
                } else if (HtmlUtils.isStyleSheetLink(headChildElement)) {
                    String styleSheetUri = headChildElement.getAttribute(AttributeConstants.HREF);
                    try {
                        InputStream stream = resourceResolver.retrieveStyleSheet(styleSheetUri);
                        byte[] bytes = StreamUtil.inputStreamToArray(stream);
                        checkIfPagesCounterMentioned(new String(bytes), cssContext);
                        CssStyleSheet styleSheet = CssStyleSheetParser.parse(new ByteArrayInputStream(bytes), resourceResolver.resolveAgainstBaseUri(styleSheetUri).toExternalForm());
                        styleSheet = wrapStyleSheetInMediaQueryIfNecessary(headChildElement, styleSheet);
                        cssStyleSheet.appendCssStyleSheet(styleSheet);
                    } catch (Exception exc) {
                        Logger logger = LoggerFactory.getLogger(DefaultCssResolver.class);
                        logger.error(LogMessageConstant.UNABLE_TO_PROCESS_EXTERNAL_CSS_FILE, exc);
                    }
                }
            }

            for (INode child : currentNode.childNodes()) {
                if (child instanceof IElementNode) {
                    q.add(child);
                }
            }
        }
        return null;
    }

    /**
     * Check if a pages counter is mentioned.
     *
     * @param cssContents the CSS contents
     * @param cssContext  the CSS context
     */
    private void checkIfPagesCounterMentioned(String cssContents, CssContext cssContext) {
        // TODO more efficient (avoid searching in text string) and precise (e.g. skip spaces) check during the parsing.
        if (cssContents.contains("counter(pages)") || cssContents.contains("counters(pages")) {
            // The presence of counter(pages) means that theoretically relayout may be needed.
            // We don't know it yet because that selector might not even be used, but
            // when we know it for sure, it's too late because the Document is created right in the start.
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
     * Merge parent CSS declarations.
     *
     * @param styles          the styles map
     * @param cssProperty     the CSS property
     * @param parentPropValue the parent properties value
     */
    private void mergeParentCssDeclaration(Map<String, String> styles, String cssProperty, String parentPropValue, Map<String, String> parentStyles) {
        String childPropValue = styles.get(cssProperty);
        if ((childPropValue == null && CssInheritance.isInheritable(cssProperty)) || CssConstants.INHERIT.equals(childPropValue)) {
            if (valueIsOfMeasurement(parentPropValue, CssConstants.EM) || valueIsOfMeasurement(parentPropValue, CssConstants.EX) ||
                    valueIsOfMeasurement(parentPropValue, CssConstants.PERCENTAGE) && fontSizeDependentPercentage.contains(cssProperty)) {
                float absoluteParentFontSize = CssUtils.parseAbsoluteLength(parentStyles.get(CssConstants.FONT_SIZE));
                // Format to 4 decimal places to prevent differences between Java and C#
                styles.put(cssProperty, DecimalFormatUtil.formatNumber(CssUtils.parseRelativeValue(parentPropValue, absoluteParentFontSize),
                        "0.####") + CssConstants.PT);
            } else
                styles.put(cssProperty, parentPropValue);
        } else if (CssConstants.TEXT_DECORATION.equals(cssProperty) && !CssConstants.INLINE_BLOCK.equals(styles.get(CssConstants.DISPLAY))) {
            // TODO Note! This property is formally not inherited, but the browsers behave very similar to inheritance here.
                        /* Text decorations on inline boxes are drawn across the entire element,
                            going across any descendant elements without paying any attention to their presence. */
            // Also, when, for example, parent element has text-decoration:underline, and the child text-decoration:overline,
            // then the text in the child will be both overline and underline. This is why the declarations are merged
            // See TextDecorationTest#textDecoration01Test
            styles.put(cssProperty, CssPropertyMerger.mergeTextDecoration(childPropValue, parentPropValue));
        }
    }

    private static boolean valueIsOfMeasurement(String value, String measurement) {
        if (value == null)
            return false;
        if (value.endsWith(measurement) && CssUtils.isNumericValue(value.substring(0, value.length() - measurement.length()).trim()))
            return true;
        return false;
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
