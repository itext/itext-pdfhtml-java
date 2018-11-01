package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.renderer.BlockRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.styledxmlparser.css.CssContextNode;

class WidthDimensionContainer extends DimensionContainer {

    public WidthDimensionContainer(CssContextNode node, float maxWidth, IRenderer renderer, float additionalWidthFix) {
        String width = node.getStyles().get(CssConstants.WIDTH);
        if (width != null && !width.equals("auto")) {
            dimension = parseDimension(node, width, maxWidth, additionalWidthFix);
        }
        minDimension = getMinWidth(node, maxWidth, additionalWidthFix);
        maxDimension = getMaxWidth(node, maxWidth, additionalWidthFix);
        MinMaxWidth minMaxWidth = null;
        if (renderer instanceof BlockRenderer) {
            minMaxWidth = ((BlockRenderer) renderer).getMinMaxWidth();
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
