package com.itextpdf.html2pdf.attach.impl.layout.form.element;

import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.renderer.SelectFieldListBoxRenderer;
import com.itextpdf.layout.renderer.IRenderer;

/**
 * A field that represents a control for selecting one or several of the provided options.
 */
public class ListBoxField extends AbstractSelectField {

    /**
     * Creates a new list box field.
     *
     * @param size the size of the list box, which will define the height of visible properties, shall be greater than zero
     * @param allowMultipleSelection a boolean flag that defines whether multiple options are allowed to be selected at once
     * @param id the id
     */
    public ListBoxField(String id, int size, boolean allowMultipleSelection) {
        super(id);
        setProperty(Html2PdfProperty.FORM_FIELD_SIZE, size);
        setProperty(Html2PdfProperty.FORM_FIELD_MULTIPLE, allowMultipleSelection);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.layout.form.element.FormField#getDefaultProperty(int)
     */
    @Override
    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case Html2PdfProperty.FORM_FIELD_MULTIPLE:
                return (T1) (Object) false;
            case Html2PdfProperty.FORM_FIELD_SIZE:
                return (T1) (Object) 4;
            default:
                return super.<T1>getDefaultProperty(property);
        }
    }

    @Override
    protected IRenderer makeNewRenderer() {
        return new SelectFieldListBoxRenderer(this);
    }
}
