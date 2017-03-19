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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssRuleName;
import com.itextpdf.html2pdf.css.page.PageContextNode;
import com.itextpdf.html2pdf.css.page.PageMarginBoxContextNode;
import com.itextpdf.html2pdf.css.resolve.CssContext;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.html.node.INode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class PageContextProperties {
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

    private PageContextNode pageContextNode;
    private List<PageMarginBoxContextNode> pageMarginBoxes;

    private PageContextProperties(PageContextNode pageProps, List<PageMarginBoxContextNode> pagesMarginBoxes) {
        this.pageContextNode = pageProps;
        this.pageMarginBoxes = pagesMarginBoxes;
    }

    public static PageContextProperties resolve(INode rootNode, ICssResolver cssResolver, CssContext context, String... pageClasses) {
        PageContextNode pageProps = getResolvedPageClassNode(rootNode, cssResolver, context, pageClasses);
        List<PageMarginBoxContextNode> pagesMarginBoxes = getResolvedMarginBoxes(pageProps, cssResolver, context);
        return new PageContextProperties(pageProps, pagesMarginBoxes);
    }

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

    private static PageContextNode getResolvedPageClassNode(INode rootNode, ICssResolver cssResolver, CssContext context, String... pageClasses) {
        PageContextNode pagesClassNode = new PageContextNode(rootNode);
        for (String pageClass : pageClasses) {
            pagesClassNode.addPageClass(pageClass);
        }
        Map<String, String> pageClassStyles = cssResolver.resolveStyles(pagesClassNode, context);
        pagesClassNode.setStyles(pageClassStyles);

        return pagesClassNode;
    }

    public PageContextNode getResolvedPageContextNode() {
        return pageContextNode;
    }

    public List<PageMarginBoxContextNode> getResolvedPageMarginBoxes() {
        return pageMarginBoxes;
    }
}
