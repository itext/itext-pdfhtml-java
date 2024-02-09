package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.attach.IOutlineMarkExtractor;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * {@link ClassOutlineMarkExtractor} class is used to get class of element as a mark for {@link OutlineHandler}
 */
public class ClassOutlineMarkExtractor implements IOutlineMarkExtractor {
    @Override
    public String getMark(IElementNode element) {
        return element.getAttribute("class");
    }
}
