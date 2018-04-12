package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.Property;

/**
 * TagWorker class for the {@code optgroup} element.
 */
public class OptGroupTagWorker extends DivTagWorker {

    /**
     * Creates a new {@link OptGroupTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public OptGroupTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
        String label = element.getAttribute(AttributeConstants.LABEL);
        if (label == null || label.isEmpty()) {
            label = "\u00A0";
        }
        getElementResult().setProperty(Html2PdfProperty.FORM_FIELD_LABEL, label);
        Paragraph p = new Paragraph(label).setMargin(0);
        p.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.VISIBLE);
        p.setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.VISIBLE);
        ((Div) getElementResult()).add(p);
    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return content == null || content.trim().isEmpty();
    }
}
