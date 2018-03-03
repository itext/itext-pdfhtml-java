package com.itextpdf.html2pdf.attach.impl.layout.form.renderer;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.ButtonContainer;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.IFormField;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.BlockRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link AbstractOneLineTextFieldRenderer} implementation for buttons with kids.
 * @deprecated Will be renamed to {@code ButtonRenderer} in next major release.
 */
@Deprecated
public class ButtonContainerRenderer extends BlockRenderer {

    private static final float DEFAULT_FONT_SIZE = 12f;

    public ButtonContainerRenderer(ButtonContainer modelElement) {
        super(modelElement);
    }

    @Override
    public void draw(DrawContext drawContext) {
        super.draw(drawContext);
        if (!isFlatten()) {
            String value = getDefaultValue();
            String name = getModelId();
            UnitValue fontSize = (UnitValue) this.getPropertyAsUnitValue(Property.FONT_SIZE);
            if (!fontSize.isPointValue()) {
                fontSize = UnitValue.createPointValue(DEFAULT_FONT_SIZE);
            }
            PdfDocument doc = drawContext.getDocument();
            Rectangle area = getOccupiedArea().getBBox().clone();
            applyMargins(area, false);
            PdfPage page = doc.getPage(occupiedArea.getPageNumber());
            PdfButtonFormField button = PdfFormField.createPushButton(doc, area, name, value, doc.getDefaultFont(), fontSize.getValue());
            button.getWidgets().get(0).setHighlightMode(PdfAnnotation.HIGHLIGHT_NONE);
            button.setBorderWidth(0);
            button.setBackgroundColor(null);
            TransparentColor color = getPropertyAsTransparentColor(Property.FONT_COLOR);
            if (color != null) {
                button.setColor(color.getColor());
            }
            PdfAcroForm forms = PdfAcroForm.getAcroForm(doc, true);
            //Add fields only if it isn't already added. This can happen on split.
            if (forms.getField(name) == null) {
                forms.addField(button, page);
            }
        }
    }

    @Override
    protected Float getLastYLineRecursively() {
        return super.getFirstYLineRecursively();
    }

    @Override
    public IRenderer getNextRenderer() {
        return new ButtonContainerRenderer((ButtonContainer) modelElement);
    }

    //NOTE: Duplicates methods from AbstractFormFieldRenderer should be changed in next major version

    /**
     * Gets the model id.
     *
     * @return the model id
     */
    protected String getModelId() {
        return ((IFormField) getModelElement()).getId();
    }

    /**
     * Checks if form fields need to be flattened.
     *
     * @return true, if fields need to be flattened
     */
    public boolean isFlatten() {
        Boolean flatten = getPropertyAsBoolean(Html2PdfProperty.FORM_FIELD_FLATTEN);
        return flatten != null ? (boolean) flatten : (boolean) modelElement.<Boolean>getDefaultProperty(Html2PdfProperty.FORM_FIELD_FLATTEN);
    }

    /**
     * Gets the default value of the form field.
     *
     * @return the default value of the form field
     */
    public String getDefaultValue() {
        String defaultValue = this.<String>getProperty(Html2PdfProperty.FORM_FIELD_VALUE);
        return defaultValue != null ? defaultValue : modelElement.<String>getDefaultProperty(Html2PdfProperty.FORM_FIELD_VALUE);
    }
}
