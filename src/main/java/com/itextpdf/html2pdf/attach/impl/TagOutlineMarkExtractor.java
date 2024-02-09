package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.attach.IOutlineMarkExtractor;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * {@link TagOutlineMarkExtractor} class is used to get tag of element as a mark for {@link OutlineHandler}
 */
public class TagOutlineMarkExtractor implements IOutlineMarkExtractor {

    @Override
    public String getMark(IElementNode element) {
        return element.name();
    }
}
