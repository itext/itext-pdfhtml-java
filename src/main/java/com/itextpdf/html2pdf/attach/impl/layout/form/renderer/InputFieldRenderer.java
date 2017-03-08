/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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
package com.itextpdf.html2pdf.attach.impl.layout.form.renderer;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.InputField;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LineRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;

public class InputFieldRenderer extends AbstractOneLineTextFieldRenderer {

    public InputFieldRenderer(InputField modelElement) {
        super(modelElement);
    }

    @Override
    public IRenderer getNextRenderer() {
        return new InputFieldRenderer((InputField) modelElement);
    }

    public int getSize() {
        Integer size = getPropertyAsInteger(Html2PdfProperty.FORM_FIELD_SIZE);
        return size != null ? (int) size : (int) modelElement.getDefaultProperty(Html2PdfProperty.FORM_FIELD_SIZE);
    }

    public boolean isPassword() {
        Boolean password = getPropertyAsBoolean(Html2PdfProperty.FORM_FIELD_PASSWORD_FLAG);
        return password != null ? (boolean) password : (boolean) modelElement.getDefaultProperty(Html2PdfProperty.FORM_FIELD_PASSWORD_FLAG);
    }

    @Override
    protected void adjustFieldLayout() {
        List<LineRenderer> flatLines = ((ParagraphRenderer) flatRenderer).getLines();
        Rectangle flatBBox= flatRenderer.getOccupiedArea().getBBox();
        updatePdfFont((ParagraphRenderer) flatRenderer);
        if (!flatLines.isEmpty() && font != null) {
            cropContentLines(flatLines, flatBBox);
        } else {
            LoggerFactory.getLogger(getClass()).error(MessageFormat.format(LogMessageConstant.ERROR_WHILE_LAYOUT_OF_FORM_FIELD_WITH_TYPE, "text input"));
            setProperty(Html2PdfProperty.FORM_FIELD_FLATTEN, true);
            baseline = flatBBox.getTop();
            flatBBox.setY(baseline).setHeight(0);
        }
        flatBBox.setWidth(getContentWidth().floatValue());
    }

    @Override
    protected IRenderer createFlatRenderer() {
        String defaultValue = getDefaultValue();
        boolean flatten = isFlatten();
        boolean password = isPassword();
        if (flatten && password) {
            defaultValue = obfuscatePassword(defaultValue);
        }
        return createParagraphRenderer(defaultValue);
    }

    @Override
    protected void applyAcroField(DrawContext drawContext) {
        font.setSubset(false);
        String value = getDefaultValue();
        String name = getModelId();
        float fontSize = (float) getPropertyAsFloat(Property.FONT_SIZE);
        PdfDocument doc = drawContext.getDocument();
        Rectangle area = flatRenderer.getOccupiedArea().getBBox().clone();
        PdfPage page = doc.getPage(occupiedArea.getPageNumber());
        boolean password = isPassword();
        if (password) {
            value = "";
        }
        PdfFormField inputField = PdfFormField.createText(doc, area, name, value, font, fontSize);
        applyDefaultFieldProperties(inputField);
        if (password) {
            inputField.setFieldFlag(PdfFormField.FF_PASSWORD, true);
        } else {
            inputField.setDefaultValue(new PdfString(value));
        }
        PdfAcroForm.getAcroForm(doc, true).addField(inputField, page);
    }

    @Override
    protected Float getContentWidth() {
        Float width = super.getContentWidth();
        if (width == null) {
            float fontSize = (float) getPropertyAsFloat(Property.FONT_SIZE);
            int size = getSize();
            return fontSize * (size * 0.5f + 2) + 2;
        }
        return width;
    }

    private String obfuscatePassword(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            builder.append('*');
        }
        return builder.toString();
    }
}
