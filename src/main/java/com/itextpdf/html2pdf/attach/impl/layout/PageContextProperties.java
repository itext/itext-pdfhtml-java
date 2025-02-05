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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.resolve.CssContext;
import com.itextpdf.styledxmlparser.css.CssRuleName;
import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.css.page.PageContextNode;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.node.INode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Properties class for the {@link PageContextProcessor}.
 */
class PageContextProperties {

    /**
     * List containing possible names for page margin boxes.
     */
    private static final List<String> pageMarginBoxNames = Arrays.asList(
            CssRuleName.TOP_LEFT_CORNER,
            CssRuleName.TOP_LEFT,
            CssRuleName.TOP_CENTER,
            CssRuleName.TOP_RIGHT,
            CssRuleName.TOP_RIGHT_CORNER,
            CssRuleName.RIGHT_TOP,
            CssRuleName.RIGHT_MIDDLE,
            CssRuleName.RIGHT_BOTTOM,
            CssRuleName.BOTTOM_RIGHT_CORNER,
            CssRuleName.BOTTOM_RIGHT,
            CssRuleName.BOTTOM_CENTER,
            CssRuleName.BOTTOM_LEFT,
            CssRuleName.BOTTOM_LEFT_CORNER,
            CssRuleName.LEFT_BOTTOM,
            CssRuleName.LEFT_MIDDLE,
            CssRuleName.LEFT_TOP
    );

    /**
     * The page context node.
     */
    private PageContextNode pageContextNode;

    /**
     * The page margin boxes.
     */
    private List<PageMarginBoxContextNode> pageMarginBoxes;

    /**
     * Instantiates a new {@link PageContextProperties} instance.
     *
     * @param pageProps        the page context node
     * @param pagesMarginBoxes the page margin boxes
     */
    private PageContextProperties(PageContextNode pageProps, List<PageMarginBoxContextNode> pagesMarginBoxes) {
        this.pageContextNode = pageProps;
        this.pageMarginBoxes = pagesMarginBoxes;
    }

    /**
     * Resolves a node with a {@link PageContextProperties} instance as result.
     *
     * @param rootNode    the root node to resolve
     * @param cssResolver the CSS resolver
     * @param context     the CSS context
     * @param pageClasses the page classes
     * @return the {@link PageContextProperties} for a specific node
     */
    public static PageContextProperties resolve(INode rootNode, ICssResolver cssResolver, CssContext context, String... pageClasses) {
        PageContextNode pageProps = getResolvedPageClassNode(rootNode, cssResolver, context, pageClasses);
        List<PageMarginBoxContextNode> pagesMarginBoxes = getResolvedMarginBoxes(pageProps, cssResolver, context);
        return new PageContextProperties(pageProps, pagesMarginBoxes);
    }

    /**
     * Gets the resolved margin boxes.
     *
     * @param pageClassNode the page contex node
     * @param cssResolver   the CSS resolver
     * @param context       the CSS context
     * @return the resolved margin boxes
     */
    private static List<PageMarginBoxContextNode> getResolvedMarginBoxes(PageContextNode pageClassNode, ICssResolver cssResolver, CssContext context) {
        List<PageMarginBoxContextNode> resolvedMarginBoxes = new ArrayList<>();
        for (String pageMarginBoxName : pageMarginBoxNames) {
            PageMarginBoxContextNode marginBoxNode = new PageMarginBoxContextNode(pageClassNode, pageMarginBoxName);
            Map<String, String> marginBoxStyles = cssResolver.resolveStyles(marginBoxNode, context);
            if (!marginBoxNode.childNodes().isEmpty()) {
                marginBoxNode.setStyles(marginBoxStyles);
                resolvedMarginBoxes.add(marginBoxNode);
            }
            context.setQuotesDepth(0);
        }
        return resolvedMarginBoxes;
    }

    /**
     * Gets the resolved page class node.
     *
     * @param rootNode    the root node
     * @param cssResolver the CSS resolver
     * @param context     the CSS context
     * @param pageClasses the page classes
     * @return the resolved page class node
     */
    private static PageContextNode getResolvedPageClassNode(INode rootNode, ICssResolver cssResolver, CssContext context, String... pageClasses) {
        PageContextNode pagesClassNode = new PageContextNode(rootNode);
        for (String pageClass : pageClasses) {
            pagesClassNode.addPageClass(pageClass);
        }
        Map<String, String> pageClassStyles = cssResolver.resolveStyles(pagesClassNode, context);
        pagesClassNode.setStyles(pageClassStyles);

        return pagesClassNode;
    }

    /**
     * Gets the resolved page context node.
     *
     * @return the resolved page context node
     */
    PageContextNode getResolvedPageContextNode() {
        return pageContextNode;
    }

    /**
     * Gets the resolved page margin boxes.
     *
     * @return the resolved page margin boxes
     */
    List<PageMarginBoxContextNode> getResolvedPageMarginBoxes() {
        return pageMarginBoxes;
    }
}
