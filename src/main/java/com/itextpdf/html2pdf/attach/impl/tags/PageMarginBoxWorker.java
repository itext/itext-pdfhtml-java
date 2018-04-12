package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.tagging.IAccessibleElement;

/**
 * TagWorker class for the page margin box.
 */
public class PageMarginBoxWorker extends DivTagWorker {

    private Div wrappedElementResult;

    /**
     * Creates a new {@link PageMarginBoxWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public PageMarginBoxWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        super.processEnd(element, context);

        getElementResult().setProperty(Property.COLLAPSING_MARGINS, true);
        if (super.getElementResult() instanceof IBlockElement) {
            wrappedElementResult = new Div().add((IBlockElement) super.getElementResult());
            wrappedElementResult.setProperty(Property.COLLAPSING_MARGINS, false);
        }

        if (getElementResult() instanceof IAccessibleElement) {
            ((IAccessibleElement) getElementResult()).getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);
        }
    }

    @Override
    public IPropertyContainer getElementResult() {
        return wrappedElementResult == null ? super.getElementResult() : wrappedElementResult;
    }
}
