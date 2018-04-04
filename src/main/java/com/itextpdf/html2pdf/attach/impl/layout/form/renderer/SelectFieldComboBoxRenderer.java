package com.itextpdf.html2pdf.attach.impl.layout.form.renderer;

import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.AbstractSelectField;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link SelectFieldComboBoxRenderer} implementation for select field renderer.
 */
public class SelectFieldComboBoxRenderer extends AbstractSelectFieldRenderer {
    private IRenderer minMaxWidthRenderer;

    /**
     * Creates a new {@link SelectFieldComboBoxRenderer} instance.
     *
     * @param modelElement the model element
     */
    public SelectFieldComboBoxRenderer(AbstractSelectField modelElement) {
        super(modelElement);
        setProperty(Property.VERTICAL_ALIGNMENT, VerticalAlignment.MIDDLE);
        setProperty(Property.OVERFLOW_X, OverflowPropertyValue.HIDDEN);
        setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.HIDDEN);
        minMaxWidthRenderer = createFlatRenderer(true);
    }

    @Override
    public IRenderer getNextRenderer() {
        return new SelectFieldComboBoxRenderer((AbstractSelectField) modelElement);
    }

    @Override
    protected MinMaxWidth getMinMaxWidth() {
        List<IRenderer> realChildRenderers = childRenderers;
        childRenderers = new ArrayList<>();
        childRenderers.add(minMaxWidthRenderer);
        MinMaxWidth minMaxWidth = super.getMinMaxWidth();
        childRenderers = realChildRenderers;
        return minMaxWidth;
    }

    @Override
    protected boolean allowLastYLineRecursiveExtraction() {
        return true;
    }

    @Override
    protected IRenderer createFlatRenderer() {
        return createFlatRenderer(false);
    }

    @Override
    protected void applyAcroField(DrawContext drawContext) {
        // TODO DEVSIX-1901
    }

    private IRenderer createFlatRenderer(boolean addAllOptionsToChildren) {
        AbstractSelectField selectField = (AbstractSelectField) modelElement;
        List<IBlockElement> options = selectField.getOptions();

        Div pseudoContainer = new Div();
        for (IBlockElement option : options) {
            pseudoContainer.add(option);
        }

        List<Paragraph> allOptions;
        IRenderer pseudoRendererSubTree = pseudoContainer.createRendererSubTree();
        if (addAllOptionsToChildren) {
            allOptions = getAllOptionsFlatElements(pseudoRendererSubTree);
        } else {
            allOptions = getSingleSelectedOptionFlatRenderer(pseudoRendererSubTree);
        }

        if (allOptions.isEmpty()) {
            allOptions.add(createComboBoxOptionFlatElement());
        }
        pseudoContainer.getChildren().clear();
        for (Paragraph option : allOptions) {
            pseudoContainer.add(option);
        }

        IRenderer rendererSubTree = pseudoContainer.createRendererSubTree();

        return rendererSubTree;
    }

    private List<Paragraph> getSingleSelectedOptionFlatRenderer(IRenderer optionsSubTree) {
        List<Paragraph> selectedOptionFlatRendererList = new ArrayList<>();
        List<IRenderer> selectedOptions = getOptionsMarkedSelected(optionsSubTree);
        IRenderer selectedOption;
        if (selectedOptions.isEmpty()) {
            selectedOption = getFirstOption(optionsSubTree);
        } else {
            selectedOption = selectedOptions.get(selectedOptions.size() - 1);
        }
        if (selectedOption != null) {
            String label = selectedOption.<String>getProperty(Html2PdfProperty.FORM_FIELD_LABEL);
            selectedOptionFlatRendererList.add(createComboBoxOptionFlatElement(label, false));
        }
        return selectedOptionFlatRendererList;
    }

    private IRenderer getFirstOption(IRenderer renderer) {
        IRenderer firstOption = null;
        for (IRenderer child : renderer.getChildRenderers()) {
            if (isOptionRenderer(child)) {
                firstOption = child;
                break;
            }
            firstOption = getFirstOption(child);
            if (firstOption != null) {
                break;
            }
        }
        return firstOption;
    }

    private List<Paragraph> getAllOptionsFlatElements(IRenderer renderer) {
        return getAllOptionsFlatElements(renderer, false);
    }

    private List<Paragraph> getAllOptionsFlatElements(IRenderer renderer, boolean isInOptGroup) {
        List<Paragraph> options = new ArrayList<>();
        for (IRenderer child : renderer.getChildRenderers()) {
            if (isOptionRenderer(child)) {
                String label = child.<String>getProperty(Html2PdfProperty.FORM_FIELD_LABEL);
                Paragraph optionFlatElement = createComboBoxOptionFlatElement(label, isInOptGroup);
                options.add(optionFlatElement);
            } else {
                options.addAll(getAllOptionsFlatElements(child, isInOptGroup || isOptGroupRenderer(child)));
            }
        }
        return options;
    }

    private static Paragraph createComboBoxOptionFlatElement() {
        return createComboBoxOptionFlatElement(null, false);
    }

    private static Paragraph createComboBoxOptionFlatElement(String label, boolean simulateOptGroupMargin) {
        Paragraph paragraph = new Paragraph().setMargin(0);
        if (simulateOptGroupMargin) {
            paragraph.add("\u200d    ");
        }

        if (label == null || label.isEmpty()) {
            label = "\u00A0";
        }

        paragraph.add(label);
        paragraph.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.VISIBLE);
        paragraph.setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.VISIBLE);
        // These constants are defined according to values in default.css.
        // At least in Chrome paddings of options in comboboxes cannot be altered through css styles.
        float leftRightPaddingVal = 2 * 0.75f;
        float bottomPaddingVal = 0.75f;
        float topPaddingVal = 0;
        paragraph.setPaddings(topPaddingVal, leftRightPaddingVal, bottomPaddingVal, leftRightPaddingVal);
        return paragraph;
    }
}
