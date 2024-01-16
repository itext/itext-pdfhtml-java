package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.layout.element.GridContainer;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * {@link ITagWorker} implementation for elements with {@code display: grid}.
 */
public class DisplayGridTagWorker extends DivTagWorker {
    public DisplayGridTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context, new GridContainer());
    }
}
