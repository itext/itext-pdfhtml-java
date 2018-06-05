package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.node.IAttributes;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @deprecated Remove this class in 7.2 and use {@link PageMarginBoxContextNode} instead
 *             (by making it implement {@link IElementNode}).
 */
@Deprecated
class PageMarginBoxDummyElement implements IElementNode, ICustomElementNode {

    /** The resolved styles. */
    private Map<String, String> elementResolvedStyles;

    @Override
    public String name() {
        return PageMarginBoxContextNode.PAGE_MARGIN_BOX_TAG;
    }

    @Override
    public IAttributes getAttributes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAttribute(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Map<String, String>> getAdditionalHtmlStyles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAdditionalHtmlStyles(Map<String, String> styles) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLang() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<INode> childNodes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addChild(INode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public INode parentNode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStyles(Map<String, String> stringStringMap) {
        elementResolvedStyles = stringStringMap;
    }

    @Override
    public Map<String, String> getStyles() {
        return elementResolvedStyles == null ? Collections.<String,String>emptyMap() : elementResolvedStyles;
    }
}
