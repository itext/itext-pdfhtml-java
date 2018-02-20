package com.itextpdf.html2pdf.css.selector.item;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.ICustomElementNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;

class CssPseudoClassRootSelectorItem extends CssPseudoClassSelectorItem {
    private static final CssPseudoClassRootSelectorItem instance = new CssPseudoClassRootSelectorItem();

    private CssPseudoClassRootSelectorItem() {
        super(CssConstants.ROOT);
    }

    public static CssPseudoClassRootSelectorItem getInstance() {
        return instance;
    }

    @Override
    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || node instanceof ICustomElementNode || node instanceof IDocumentNode) {
            return false;
        }
        return node.parentNode() instanceof IDocumentNode;
    }
}
