package com.itextpdf.html2pdf.attach.impl.layout.form.element;

import com.itextpdf.layout.element.IBlockElement;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for fields that represents a control for selecting one or several of the provided options.
 */
public abstract class AbstractSelectField extends FormField<AbstractSelectField> {

    private List<IBlockElement> options = new ArrayList<>();

    protected AbstractSelectField(String id) {
        super(id);
    }

    /**
     * Adds a container with option(s). This might be a container for options group.
     *
     * @param optionElement a container with option(s)
     */
    public void addOption(IBlockElement optionElement) {
        options.add(optionElement);
    }

    /**
     * Gets a list of containers with option(s). Every container might be a container for options group.
     *
     * @return a list of containers with option(s)
     */
    public List<IBlockElement> getOptions() {
        return options;
    }
}
