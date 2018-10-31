package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;

public class WidthDimensionContainer extends DimensionContainer {

    public WidthDimensionContainer(CssContextNode node, float maxWidth, ProcessorContext context) {
        String width = node.getStyles().get(CssConstants.WIDTH);
        if (width != null && !width.equals("auto")) {
            dimension = parseDimension(node, width, maxWidth);
        }
        minDimension = getMinWidth(node, maxWidth);
        maxDimension = getMaxWidth(node, maxWidth);
        minContentDimension = PageContextProcessor.getMinContentWidth((PageMarginBoxContextNode) node, context);
        maxContentDimension = PageContextProcessor.getMaxContentWidth((PageMarginBoxContextNode) node, context);
    }

    float getMinWidth(CssContextNode node, float maxAvailableWidth) {
        String content = node.getStyles().get(CssConstants.MIN_WIDTH);
        if (content == null) {
            return 0;
        }
        content = content.toLowerCase().trim();
        if (content.equals("inherit")) {
            if (node.parentNode() instanceof CssContextNode) {
                return getMinWidth((CssContextNode) node.parentNode(), maxAvailableWidth);
            }
            return 0;
        }
        return parseDimension(node, content, maxAvailableWidth);
    }

    float getMaxWidth(CssContextNode node, float maxAvailableWidth) {
        String content = node.getStyles().get(CssConstants.MAX_WIDTH);
        if (content == null) {
            return Float.MAX_VALUE;
        }
        content = content.toLowerCase().trim();
        if (content.equals("inherit")) {
            if (node.parentNode() instanceof CssContextNode) {
                return getMaxWidth((CssContextNode) node.parentNode(), maxAvailableWidth);
            }
            return Float.MAX_VALUE;
        }
        float dim = parseDimension(node, content, maxAvailableWidth);
        if (dim == 0) {
            return Float.MAX_VALUE;
        }
        return dim;
    }

}
