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
import com.itextpdf.forms.form.element.TextArea;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.properties.BoxSizingPropertyValue;
import com.itextpdf.layout.properties.Leading;
import com.itextpdf.layout.properties.OverflowPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code textarea} element.
 */
public class TextAreaTagWorker implements ITagWorker, IDisplayAware {

    /**
     * The Constant DEFAULT_TEXTAREA_NAME.
     */
    private static final String DEFAULT_TEXTAREA_NAME = "TextArea";

    /**
     * The text area.
     */
    private TextArea textArea;

    private String display;

    /**
     * Creates a new {@link TextAreaTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public TextAreaTagWorker(IElementNode element, ProcessorContext context) {
        String name = element.getAttribute(AttributeConstants.ID);
        if (name == null) {
            name = DEFAULT_TEXTAREA_NAME;
        }
        name = context.getFormFieldNameResolver().resolveFormName(name);
        textArea = new TextArea(name);
        Integer rows = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.ROWS));
        Integer cols = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.COLS));
        textArea.setProperty(FormProperty.FORM_FIELD_ROWS, rows);
        textArea.setProperty(FormProperty.FORM_FIELD_COLS, cols);
        textArea.setProperty(FormProperty.FORM_FIELD_FLATTEN, !context.isCreateAcroForm());
        textArea.setProperty(FormProperty.FORM_ACCESSIBILITY_LANGUAGE, element.getAttribute(AttributeConstants.LANG));

        // Default html2pdf text area appearance differs from the default one for form fields.
        // That's why we need to get rid of all properties we set by default during TextArea instance creation.
        textArea.deleteOwnProperty(Property.BOX_SIZING);
        
        String placeholder = element.getAttribute(AttributeConstants.PLACEHOLDER);
        if (null != placeholder) {
            Paragraph paragraph;
            if (placeholder.isEmpty()) {
                paragraph = new Paragraph();
            } else {
                if (placeholder.trim().isEmpty()) {
                    paragraph = new Paragraph("\u00A0");
                } else {
                    paragraph = new Paragraph(placeholder);
                }
            }
            textArea.setPlaceholder(paragraph.setMargin(0));
        }
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        if (content.startsWith("\r\n")) {
            content = content.substring(2);
        } else if (content.startsWith("\r") || content.startsWith("\n")) {
            content = content.substring(1);
        }
        textArea.setProperty(FormProperty.FORM_FIELD_VALUE, content);
        return true;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return childTagWorker instanceof PlaceholderTagWorker && null != textArea.getPlaceholder();
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return textArea;
    }

    @Override
    public String getDisplay() {
        return display;
    }
}
