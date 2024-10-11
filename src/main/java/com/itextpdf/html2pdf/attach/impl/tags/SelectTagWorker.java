/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
import com.itextpdf.forms.form.element.AbstractSelectField;
import com.itextpdf.forms.form.element.ComboBoxField;
import com.itextpdf.forms.form.element.ListBoxField;
import com.itextpdf.forms.form.element.SelectFieldItem;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TagWorker class for the {@code select} element.
 */
public class SelectTagWorker implements ITagWorker, IDisplayAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectTagWorker.class);
    /**
     * The form element.
     */
    private AbstractSelectField selectElement;

    /**
     * The display.
     */
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
        Integer sizeAttr = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.SIZE));
        int size = getSelectSize(sizeAttr, multipleAttr);

        if (size > 1 || multipleAttr) {
            selectElement = new ListBoxField(name, size, multipleAttr);

            // Remove some properties which are set in ListBoxField constructor
            selectElement.deleteOwnProperty(Property.PADDING_LEFT);
            selectElement.deleteOwnProperty(Property.PADDING_RIGHT);
            selectElement.deleteOwnProperty(Property.PADDING_TOP);
            selectElement.deleteOwnProperty(Property.PADDING_BOTTOM);
        } else {
            selectElement = new ComboBoxField(name);
        }
        String lang = element.getAttribute(AttributeConstants.LANG);
        selectElement.getAccessibilityProperties().setLanguage(lang);
        selectElement.setProperty(FormProperty.FORM_FIELD_FLATTEN, !context.isCreateAcroForm());
        selectElement.setProperty(FormProperty.FORM_CONFORMANCE_LEVEL, context.getConformance());
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
                IBlockElement blockElement = (IBlockElement) childTagWorker.getElementResult();
                String label = blockElement.<String>getProperty(FormProperty.FORM_FIELD_LABEL);
                SelectFieldItem item = new SelectFieldItem(label, blockElement);
                selectElement.addOption(item);

                Boolean isFlattenFromProperty = selectElement.<Boolean>getProperty(FormProperty.FORM_FIELD_FLATTEN);
                if (childTagWorker instanceof OptGroupTagWorker && !Boolean.TRUE.equals(isFlattenFromProperty)) {
                    LOGGER.warn(Html2PdfLogMessageConstant.OPTGROUP_NOT_SUPPORTED_IN_INTERACTIVE_SELECT);
                }

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
