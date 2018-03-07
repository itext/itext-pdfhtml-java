/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

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

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.Button;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.CheckBox;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.InputField;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.Radio;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.resolver.form.RadioCheckResolver;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TagWorker class for the {@code input} element.
 */
public class InputTagWorker implements ITagWorker, IDisplayAware {

    /**
     * The form element.
     */
    private IElement formElement;

    /**
     * The display.
     */
    private String display;

    /**
     * Creates a new {@link InputTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public InputTagWorker(IElementNode element, ProcessorContext context) {
        String inputType = element.getAttribute(AttributeConstants.TYPE);
        String value = element.getAttribute(AttributeConstants.VALUE);
        String name = context.getFormFieldNameResolver().resolveFormName(element.getAttribute(AttributeConstants.NAME));
        // Default input type is text
        if (inputType == null || AttributeConstants.TEXT.equals(inputType) || AttributeConstants.EMAIL.equals(inputType)
                || AttributeConstants.PASSWORD.equals(inputType) || AttributeConstants.NUMBER.equals(inputType)) {
            Integer size = CssUtils.parseInteger(element.getAttribute(AttributeConstants.SIZE));
            formElement = new InputField(name);
            formElement.setProperty(Html2PdfProperty.FORM_FIELD_VALUE, preprocessInputValue(value, inputType));
            formElement.setProperty(Html2PdfProperty.FORM_FIELD_SIZE, size);
            if (AttributeConstants.PASSWORD.equals(inputType)) {
                formElement.setProperty(Html2PdfProperty.FORM_FIELD_PASSWORD_FLAG, true);
            }
        } else if (AttributeConstants.SUBMIT.equals(inputType) || AttributeConstants.BUTTON.equals(inputType)) {
            formElement = new Button(name);
            formElement.setProperty(Html2PdfProperty.FORM_FIELD_VALUE, value);
        } else if (AttributeConstants.CHECKBOX.equals(inputType)) {
            formElement = new CheckBox(name);
            String checked = element.getAttribute(AttributeConstants.CHECKED);
            if (null != checked) {
                formElement.setProperty(Html2PdfProperty.FORM_FIELD_CHECKED, checked); // has attribute == is checked
            }
        } else if (AttributeConstants.RADIO.equals(inputType)) {
            formElement = new Radio(name);
            String radioGroupName = element.getAttribute(AttributeConstants.NAME);
            formElement.setProperty(Html2PdfProperty.FORM_FIELD_VALUE, radioGroupName);
            String checked = element.getAttribute(AttributeConstants.CHECKED);
            if (null != checked) {
                context.getRadioCheckResolver().checkField(radioGroupName, (Radio) formElement);
                formElement.setProperty(Html2PdfProperty.FORM_FIELD_CHECKED, checked); // has attribute == is checked
            }
        } else {
            Logger logger = LoggerFactory.getLogger(InputTagWorker.class);
            logger.error(MessageFormatUtil.format(LogMessageConstant.INPUT_TYPE_IS_NOT_SUPPORTED, inputType));
        }
        if (formElement != null) {
            formElement.setProperty(Html2PdfProperty.FORM_FIELD_FLATTEN, !context.isCreateAcroForm());
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
     * @see com.itextpdf.html2pdf.attach.impl.tags.IDisplayAware#getDisplay()
     */
    @Override
    public String getDisplay() {
        return display;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return formElement;
    }

    private static String preprocessInputValue(String value, String inputType) {
        if (AttributeConstants.NUMBER.equals(inputType) && value != null && !value.matches("[0-9.]*")) {
            value = "";
        }
        return value;
    }

}
