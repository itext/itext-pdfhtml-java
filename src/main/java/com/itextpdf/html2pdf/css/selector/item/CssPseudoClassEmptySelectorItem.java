package com.itextpdf.html2pdf.css.selector.item;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.ICustomElementNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
import java.util.List;

class CssPseudoClassEmptySelectorItem extends CssPseudoClassSelectorItem {
    private static final CssPseudoClassEmptySelectorItem instance = new CssPseudoClassEmptySelectorItem();

    private CssPseudoClassEmptySelectorItem() {
        super(CssConstants.EMPTY);
    }

    public static CssPseudoClassEmptySelectorItem getInstance() {
        return instance;
    }

    @Override
    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || node instanceof ICustomElementNode || node instanceof IDocumentNode) {
            return false;
        }
        if (node.childNodes().isEmpty()) {
            return true;
        }
        for (INode childNode : node.childNodes()) {
            if (!(childNode instanceof ITextNode) || !((ITextNode) childNode).wholeText().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
