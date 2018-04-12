package com.itextpdf.html2pdf.css.selector.item;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.ICustomElementNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import java.util.List;

class CssPseudoClassFirstChildSelectorItem extends CssPseudoClassChildSelectorItem {
    private static final CssPseudoClassFirstChildSelectorItem instance = new CssPseudoClassFirstChildSelectorItem();

    private CssPseudoClassFirstChildSelectorItem() {
        super(CssConstants.FIRST_CHILD);
    }

    public static CssPseudoClassFirstChildSelectorItem getInstance() {
        return instance;
    }

    @Override
    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || node instanceof ICustomElementNode || node instanceof IDocumentNode) {
            return false;
        }
        List<INode> children = getAllSiblings(node);
        return !children.isEmpty() && node.equals(children.get(0));
    }
}
