package com.itextpdf.html2pdf.attach;

import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * {@link IOutlineMarkExtractor} interface is used to control what part of element will be a mark
 * witch will be used to create outline in {@link com.itextpdf.html2pdf.attach.impl.OutlineHandler}
 */
public interface IOutlineMarkExtractor {
    /**
     * Get element mark.
     *
     * @param element the element
     * @return returns string mark of the element
     */
    String getMark(IElementNode element);
}
