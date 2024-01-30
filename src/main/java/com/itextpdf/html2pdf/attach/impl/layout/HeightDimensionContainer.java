/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
