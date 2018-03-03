package com.itextpdf.html2pdf.attach.impl.layout.form.element;

import com.itextpdf.html2pdf.attach.impl.layout.form.renderer.SelectFieldComboBoxRenderer;
import com.itextpdf.layout.renderer.IRenderer;

/**
 * A field that represents a control for selecting one of the provided options.
 */
public class ComboBoxField extends AbstractSelectField {

    /**
     * Creates a new select field box.
     *
     * @param id the id
     */
    public ComboBoxField(String id) {
        super(id);
    }

    @Override
    protected IRenderer makeNewRenderer() {
        return new SelectFieldComboBoxRenderer(this);
    }
}
