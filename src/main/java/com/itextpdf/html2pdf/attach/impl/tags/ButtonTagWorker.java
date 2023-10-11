/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.forms.form.FormProperty;
import com.itextpdf.forms.form.element.Button;
import com.itextpdf.forms.form.element.IFormField;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for a button element.
 */
public class ButtonTagWorker extends DivTagWorker {

    /** The Constant DEFAULT_BUTTON_NAME. */
    private static final String DEFAULT_BUTTON_NAME = "Button";

    /** The button. */
    private IFormField formField;

    /** The lang attribute value. */
    private String lang;

    private StringBuilder fallbackContent = new StringBuilder();

    private String name;

    private boolean flatten;

    private boolean hasChildren = false;

    private final PdfAConformanceLevel pdfAConformanceLevel;

    /**
     * Creates a new {@link ButtonTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public ButtonTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
        String name = element.getAttribute(AttributeConstants.ID);
        if (name == null) {
            name = DEFAULT_BUTTON_NAME;
        }
        this.name = context.getFormFieldNameResolver().resolveFormName(name);
        flatten = !context.isCreateAcroForm();
        pdfAConformanceLevel = context.getPdfDocument().getConformanceLevel();
        lang = element.getAttribute(AttributeConstants.LANG);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        fallbackContent.append(content);
        return super.processContent(content, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        hasChildren = true;
        return super.processTagChild(childTagWorker, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        if (formField == null) {
            if (hasChildren) {
                Button button = new Button(name);
                button.setProperty(FormProperty.FORM_ACCESSIBILITY_LANGUAGE, lang);
                Div div = (Div) super.getElementResult();
                for (IElement element : div.getChildren()) {
                    if (element instanceof IAccessibleElement) {
                        AccessiblePropHelper.trySetLangAttribute((IAccessibleElement) element, lang);
                    }
                    if (element instanceof IBlockElement) {
                        button.add((IBlockElement) element);
                    } else if (element instanceof Image) {
                        button.add((Image) element);
                    }
                }
                div.getChildren().clear();
                formField = button;
            } else {
                Button inputButton = new Button(name);
                inputButton.setProperty(FormProperty.FORM_ACCESSIBILITY_LANGUAGE, lang);
                inputButton.setValue(fallbackContent.toString().trim());
                formField = inputButton;
            }
        }
        formField.setProperty(FormProperty.FORM_FIELD_FLATTEN, flatten);
        formField.setProperty(FormProperty.FORM_CONFORMANCE_LEVEL, pdfAConformanceLevel);
        return formField;
    }
}
