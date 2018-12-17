package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.property.CaptionSide;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code caption} element.
 */
public class CaptionTagWorker extends DivTagWorker {

    /**
     * Creates a new {@link CaptionTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public CaptionTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
    }

}
