package com.itextpdf.html2pdf.css.selector.item;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.parse.CssSelectorParser;
import com.itextpdf.html2pdf.css.selector.ICssSelector;
import com.itextpdf.html2pdf.html.node.ICustomElementNode;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import java.util.List;

class CssPseudoClassNotSelectorItem extends CssPseudoClassSelectorItem {
    private ICssSelector argumentsSelector;

    CssPseudoClassNotSelectorItem(ICssSelector argumentsSelector) {
        super(CssConstants.NOT, argumentsSelector.toString());
        this.argumentsSelector = argumentsSelector;
    }

    public List<ICssSelectorItem> getArgumentsSelector() {
        return CssSelectorParser.parseSelectorItems(arguments);
    }

    @Override
    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || node instanceof ICustomElementNode || node instanceof IDocumentNode) {
            return false;
        }
        return !argumentsSelector.matches(node);
    }
}
