package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.styledxmlparser.css.CssContextNode;

class HeightDimensionContainer extends DimensionContainer {

    private static final float infHeight = 1e6f;;

    HeightDimensionContainer(CssContextNode pmbcNode, float width, float maxHeight, IRenderer renderer, float additionalWidthFix) {
        String height = pmbcNode.getStyles().get(CssConstants.HEIGHT);
        if (height != null && !height.equals(CssConstants.AUTO)) {
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
