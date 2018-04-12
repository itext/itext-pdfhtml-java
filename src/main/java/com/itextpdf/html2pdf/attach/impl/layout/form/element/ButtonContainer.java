package com.itextpdf.html2pdf.attach.impl.layout.form.element;

import com.itextpdf.html2pdf.attach.impl.layout.form.renderer.ButtonContainerRenderer;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.IRenderer;

/**
 * Extension of the {@link FormField} class representing a button in html
 * @deprecated Will be renamed to {@code Button} in next major release
 */
@Deprecated
public class ButtonContainer extends FormField<ButtonContainer> {

    public ButtonContainer(String id) {
        super(id);
    }

    @Override
    protected IRenderer makeNewRenderer() {
        return new ButtonContainerRenderer(this);
    }


    /**
     * Adds any block element to the div's contents.
     *
     * @param element a {@link BlockElement}
     * @return this Element
     */
    public ButtonContainer add(IBlockElement element) {
        childElements.add(element);
        return this;
    }

    /**
     * Adds an image to the div's contents.
     *
     * @param element an {@link Image}
     * @return this Element
     */
    public ButtonContainer add(Image element) {
        childElements.add(element);
        return this;
    }

    @Override
    public <T1> T1 getDefaultProperty(int property) {
        if (property == Property.KEEP_TOGETHER) {
            return (T1) (Object) true;
        }
        return super.<T1>getDefaultProperty(property);
    }
}
