/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.renderer.BlockRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.styledxmlparser.css.CssContextNode;

class WidthDimensionContainer extends DimensionContainer {

    public WidthDimensionContainer(CssContextNode node, float maxWidth, IRenderer renderer, float additionalWidthFix) {
        String width = node.getStyles().get(CssConstants.WIDTH);
        if (width != null && !CssConstants.AUTO.equals(width)) {
            dimension = parseDimension(node, width, maxWidth, additionalWidthFix);
        }
        minDimension = getMinWidth(node, maxWidth, additionalWidthFix);
        maxDimension = getMaxWidth(node, maxWidth, additionalWidthFix);

        if (!isAutoDimension()) {

            // According to point 3 of paragraph "5.3.2.3. Handling min-width and max-width" of the specification
            // maxContentDimension and minContentDimension will always be equal
            maxContentDimension = dimension;
            minContentDimension = dimension;
        } else if (renderer instanceof BlockRenderer) {
            MinMaxWidth minMaxWidth = ((BlockRenderer) renderer).getMinMaxWidth();
            maxContentDimension = minMaxWidth.getMaxWidth();
            minContentDimension = minMaxWidth.getMinWidth();
        }
    }

    private float getMinWidth(CssContextNode node, float maxAvailableWidth, float additionalWidthFix) {
        String content = node.getStyles().get(CssConstants.MIN_WIDTH);
        if (content == null) {
            return 0;
        }
        return parseDimension(node, content, maxAvailableWidth, additionalWidthFix);
    }

    private float getMaxWidth(CssContextNode node, float maxAvailableWidth, float additionalWidthFix) {
        String content = node.getStyles().get(CssConstants.MAX_WIDTH);
        if (content == null) {
            return Float.MAX_VALUE;
        }
        float dim = parseDimension(node, content, maxAvailableWidth, additionalWidthFix);
        if (dim == 0) {
            return Float.MAX_VALUE;
        }
        return dim;
    }

}
