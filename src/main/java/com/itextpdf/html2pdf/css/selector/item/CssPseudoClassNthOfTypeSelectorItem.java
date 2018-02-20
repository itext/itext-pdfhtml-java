package com.itextpdf.html2pdf.css.selector.item;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.ICustomElementNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import java.util.List;

class CssPseudoClassNthOfTypeSelectorItem extends CssPseudoClassNthSelectorItem {

    public CssPseudoClassNthOfTypeSelectorItem(String arguments) {
        super(CssConstants.NTH_OF_TYPE, arguments);
    }

    @Override
    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || node instanceof ICustomElementNode || node instanceof IDocumentNode) {
            return false;
        }
        List<INode> children = getAllSiblingsOfNodeType(node);
        return !children.isEmpty() && resolveNth(node, children);
    }
}
