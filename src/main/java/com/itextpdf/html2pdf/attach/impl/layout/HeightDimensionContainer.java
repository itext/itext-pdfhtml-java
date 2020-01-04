/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.styledxmlparser.css.CssContextNode;

class HeightDimensionContainer extends DimensionContainer {

    private static final float infHeight = 1e6f;;

    HeightDimensionContainer(CssContextNode pmbcNode, float width, float maxHeight, IRenderer renderer, float additionalWidthFix) {
        String height = pmbcNode.getStyles().get(CssConstants.HEIGHT);
        if (height != null && !CssConstants.AUTO.equals(height)) {
            dimension = parseDimension(pmbcNode, height, maxHeight, additionalWidthFix);
        }
        minDimension = getMinHeight(pmbcNode, maxHeight, additionalWidthFix);
        maxDimension = getMaxHeight(pmbcNode, maxHeight, additionalWidthFix);

        if (!isAutoDimension()) {
            maxContentDimension = dimension;
            maxContentDimension = dimension;
        } else {
            LayoutArea layoutArea = new LayoutArea(1, new Rectangle(0, 0, width, infHeight));
            LayoutContext minimalContext = new LayoutContext(layoutArea);

            LayoutResult quickLayout = renderer.layout(minimalContext);
            if (quickLayout.getStatus() != LayoutResult.NOTHING) {
                maxContentDimension = quickLayout.getOccupiedArea().getBBox().getHeight();
                minContentDimension = maxContentDimension;
            }
        }
    }

    private float getMinHeight(CssContextNode node, float maxAvailableHeight, float additionalWidthFix) {
        String content = node.getStyles().get(CssConstants.MIN_HEIGHT);
        if (content == null) {
            return 0;
        }
        return parseDimension(node, content, maxAvailableHeight, additionalWidthFix);
    }

    private float getMaxHeight(CssContextNode node, float maxAvailableHeight, float additionalWidthFix) {
        String content = node.getStyles().get(CssConstants.MAX_HEIGHT);
        if (content == null) {
            return Float.MAX_VALUE;
        }
        float dim = parseDimension(node, content, maxAvailableHeight, additionalWidthFix);
        if (dim == 0) {
            return Float.MAX_VALUE;
        }
        return dim;
    }
}
