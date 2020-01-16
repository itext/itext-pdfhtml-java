/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.AbstractSelectField;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.ComboBoxField;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.ListBoxField;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;

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
        String lang = element.getAttribute(AttributeConstants.LANG);
        selectElement.setProperty(Html2PdfProperty.FORM_ACCESSIBILITY_LANGUAGE, lang);
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
