package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.AbstractSelectField;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.ComboBoxField;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.ListBoxField;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;

/**
 * TagWorker class for the {@code select} element.
 */
public class SelectTagWorker implements ITagWorker, IDisplayAware {

    /** The form element. */
    private AbstractSelectField selectElement;

    /** The display. */
    private String display;

    /**
     * Creates a new {@link SelectTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public SelectTagWorker(IElementNode element, ProcessorContext context) {
        String name = context.getFormFieldNameResolver().resolveFormName(element.getAttribute(AttributeConstants.NAME));

        boolean multipleAttr = element.getAttribute(AttributeConstants.MULTIPLE) != null;
        Integer sizeAttr = CssUtils.parseInteger(element.getAttribute(AttributeConstants.SIZE));
        int size = getSelectSize(sizeAttr, multipleAttr);

        if (size > 1 || multipleAttr) {
            selectElement = new ListBoxField(name, size, multipleAttr);
        } else {
            selectElement = new ComboBoxField(name);
        }
        selectElement.setProperty(Html2PdfProperty.FORM_FIELD_FLATTEN, !context.isCreateAcroForm());
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {

    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return content == null || content.trim().isEmpty();
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        if (childTagWorker instanceof OptionTagWorker || childTagWorker instanceof OptGroupTagWorker) {
            if (childTagWorker.getElementResult() instanceof IBlockElement) {
                selectElement.addOption((IBlockElement) childTagWorker.getElementResult());
                return true;
            }
        }
        return false;
    }

    @Override
    public IPropertyContainer getElementResult() {
        return selectElement;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    private int getSelectSize(Integer size, boolean multiple) {
        if (size != null && size > 0) {
            return (int) size;
        }

        return multiple ? 4 : 1;
    }
}
