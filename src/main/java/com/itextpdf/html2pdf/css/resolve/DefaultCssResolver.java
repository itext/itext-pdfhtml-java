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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.ResourceResolver;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssDeclaration;
import com.itextpdf.html2pdf.css.CssRuleName;
import com.itextpdf.html2pdf.css.CssStatement;
import com.itextpdf.html2pdf.css.CssStyleSheet;
import com.itextpdf.html2pdf.css.media.CssMediaRule;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.parse.CssRuleSetParser;
import com.itextpdf.html2pdf.css.parse.CssStyleSheetParser;
import com.itextpdf.html2pdf.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.ShorthandResolverFactory;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.HtmlUtils;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IDataNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultCssResolver implements ICssResolver {

    private CssStyleSheet cssStyleSheet;
    private MediaDeviceDescription deviceDescription;

    public DefaultCssResolver(INode treeRoot, MediaDeviceDescription mediaDeviceDescription, ResourceResolver resourceResolver) {
        this.deviceDescription = mediaDeviceDescription;
        collectCssDeclarations(treeRoot, resourceResolver);
    }

    @Override
    public Map<String, String> resolveStyles(IElementNode element) {
        List<CssDeclaration> nodeCssDeclarations = HtmlStylesToCssConverter.convert(element);

        nodeCssDeclarations.addAll(cssStyleSheet.getCssDeclarations(element, deviceDescription));
        String styleAttribute = element.getAttribute(AttributeConstants.STYLE);
        if (styleAttribute != null) {
            nodeCssDeclarations.addAll(CssRuleSetParser.parsePropertyDeclarations(styleAttribute));
        }

        Map<String, String> elementStyles = cssDeclarationsToMap(nodeCssDeclarations);

        if (element.parentNode() instanceof IElementNode) {
            Map<String, String> parentStyles = ((IElementNode) element.parentNode()).getStyles();

            if (parentStyles == null && !(element.parentNode() instanceof IDocumentNode)) {
                Logger logger = LoggerFactory.getLogger(DefaultCssResolver.class);
                logger.error("Element parent styles are not resolved. Styles for current element might be incorrect.");
            }

            if (parentStyles != null) {
                for (Map.Entry<String, String> entry : parentStyles.entrySet()) {
                    String cssProperty = entry.getKey();
                    String elementPropValue = elementStyles.get(cssProperty);
                    if ((elementPropValue == null && CssInheritance.isInheritable(cssProperty)) || CssConstants.INHERIT.equals(elementPropValue)) {
                        mergeCssDeclaration(elementStyles, cssProperty, entry.getValue());
                    } else if (CssConstants.TEXT_DECORATION.equals(cssProperty)) {
                        // TODO Note! This property is formally not inherited, but the browsers behave very similar to inheritance here.
                        /* Text decorations on inline boxes are drawn across the entire element,
                            going across any descendant elements without paying any attention to their presence. */
                        // Also, when, for example, parent element has text-decoration:underline, and the child text-decoration:overline,
                        // then the text in the child will be both overline and underline. This is why the declarations are merged
                        // See TextDecorationTest#textDecoration01Test
                        mergeCssDeclaration(elementStyles, cssProperty, entry.getValue());
                    }
                }

                String elementFontSize = elementStyles.get(CssConstants.FONT_SIZE);
                String parentFontSizeStr = parentStyles.get(CssConstants.FONT_SIZE);
                if (CssUtils.isRelativeValue(elementFontSize) && parentFontSizeStr != null) {
                    float parentFontSize = CssUtils.parseAbsoluteLength(parentFontSizeStr);
                    float absoluteFontSize = CssUtils.parseRelativeValue(elementFontSize, parentFontSize);
                    mergeCssDeclaration(elementStyles, CssConstants.FONT_SIZE, absoluteFontSize + "pt");
                }
            }
        }

        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, String> entry : elementStyles.entrySet()) {
            if (CssConstants.INITIAL.equals(entry.getValue())
                    || CssConstants.INHERIT.equals(entry.getValue())) { // if "inherit" is not resolved till now, parents don't have it
                keys.add(entry.getKey());
            }
        }
        for (String key : keys) {
            mergeCssDeclaration(elementStyles, key, CssDefaults.getDefaultValue(key));
        }

        return elementStyles;
    }

    private Map<String, String> cssDeclarationsToMap(List<CssDeclaration> nodeCssDeclarations) {
        Map<String, String> stylesMap = new HashMap<>();
        for (CssDeclaration cssDeclaration : nodeCssDeclarations) {
            IShorthandResolver shorthandResolver = ShorthandResolverFactory.getShorthandResolver(cssDeclaration.getProperty());
            if (shorthandResolver == null) {
                mergeCssDeclaration(stylesMap, cssDeclaration.getProperty(), cssDeclaration.getExpression());
            } else {
                List<CssDeclaration> resolvedShorthandProps = shorthandResolver.resolveShorthand(cssDeclaration.getExpression());
                for (CssDeclaration resolvedProp : resolvedShorthandProps) {
                    mergeCssDeclaration(stylesMap, resolvedProp.getProperty(), resolvedProp.getExpression());
                }
            }
        }
        return stylesMap;
    }

    private INode collectCssDeclarations(INode rootNode, ResourceResolver resourceResolver) {
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
                        cssStyleSheet.appendCssStyleSheet(CssStyleSheetParser.parse(styleData));
                    }
                } else if (HtmlUtils.isStyleSheetLink(headChildElement)) {
                    String styleSheetUri = headChildElement.getAttribute(AttributeConstants.HREF);
                    try {
                        InputStream stream = resourceResolver.retrieveStyleSheet(styleSheetUri);
                        CssStyleSheet styleSheet = CssStyleSheetParser.parse(stream);
                        String mediaAttribute = headChildElement.getAttribute(AttributeConstants.MEDIA);
                        if (mediaAttribute != null && mediaAttribute.length() > 0) {
                            List<CssStatement> statements = styleSheet.getStatements();
                            CssMediaRule mediaRule = new CssMediaRule(CssRuleName.MEDIA, mediaAttribute);
                            mediaRule.addStatementsToBody(statements);
                            styleSheet = new CssStyleSheet();
                            styleSheet.addStatement(mediaRule);
                        }
                        cssStyleSheet.appendCssStyleSheet(styleSheet);
                    } catch (IOException exc) {
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

    private void mergeCssDeclaration(Map<String, String> styles, String cssProperty, String value) {
        if (CssConstants.TEXT_DECORATION.equals(cssProperty) && styles.containsKey(cssProperty)) {
            styles.put(cssProperty, CssPropertyMerger.mergeTextDecoration(styles.get(cssProperty), value));
        } else {
            styles.put(cssProperty, value);
        }
    }
}
