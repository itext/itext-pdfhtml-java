package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;

public class HeightDimensionContainer extends DimensionContainer {
    HeightDimensionContainer(CssContextNode pmbcNode, float width, float maxHeight, ProcessorContext context) {
        String height = pmbcNode.getStyles().get(CssConstants.HEIGHT);
        if (height != null && !height.equals("auto")) {
            dimension = parseDimension(pmbcNode, height, maxHeight);
        }
        minDimension = getMinHeight(pmbcNode, maxHeight);
        maxDimension = getMaxHeight(pmbcNode, maxHeight);
        minContentDimension = PageContextProcessor.getMinContentHeight((PageMarginBoxContextNode) pmbcNode, width, maxHeight, context);
        maxContentDimension = PageContextProcessor.getMaxContentHeight((PageMarginBoxContextNode) pmbcNode, width, maxHeight, context);
    }

    float getMinHeight(CssContextNode node, float maxAvailableHeight) {
        String content = node.getStyles().get(CssConstants.MIN_HEIGHT);
        if (content == null) {
            return 0;
        }
        content = content.toLowerCase().trim();
        if (content.equals("inherit")) {
            if (node.parentNode() instanceof CssContextNode) {
                return getMinHeight((CssContextNode) node.parentNode(), maxAvailableHeight);
            }
            return 0;
        }
        return parseDimension(node, content, maxAvailableHeight);
    }

    float getMaxHeight(CssContextNode node, float maxAvailableHeight) {
        String content = node.getStyles().get(CssConstants.MAX_HEIGHT);
        if (content == null) {
            return Float.MAX_VALUE;
        }
        content = content.toLowerCase().trim();
        if (content.equals("inherit")) {
            if (node.parentNode() instanceof CssContextNode) {
                return getMaxHeight((CssContextNode) node.parentNode(), maxAvailableHeight);
            }
            return Float.MAX_VALUE;
        }
        float dim = parseDimension(node, content, maxAvailableHeight);
        if (dim == 0) {
            return Float.MAX_VALUE;
        }
        return dim;
    }
}
