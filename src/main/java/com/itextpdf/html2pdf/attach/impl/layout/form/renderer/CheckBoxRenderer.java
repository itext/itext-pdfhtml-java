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
package com.itextpdf.html2pdf.attach.impl.layout.form.renderer;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.CheckBox;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import org.slf4j.LoggerFactory;

/**
 * The {@link AbstractOneLineTextFieldRenderer} implementation for checkboxes.
 */
public class CheckBoxRenderer extends AbstractOneLineTextFieldRenderer {

    public static final Color CHECKBOX_BORDER_COLOR = ColorConstants.DARK_GRAY;
    private static final float CHECKBOX_BORDER_WIDTH = 0.75f; // 1px
    private static final float CHECKBOX_FONT_SIZE = 6f; // enough to fit the area
    private static final float CHECKBOX_HEIGHT = 8.25f; // 11px
    private static final float CHECKBOX_WIDTH = 8.25f; // 11px

    /**
     * Creates a new {@link CheckBoxRenderer} instance.
     *
     * @param modelElement the model element
     */
    public CheckBoxRenderer(CheckBox modelElement) {
        super(modelElement);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.IRenderer#getNextRenderer()
     */
    @Override
    public IRenderer getNextRenderer() {
        return new CheckBoxRenderer((CheckBox) modelElement);
    }

    @Override
    protected IRenderer createFlatRenderer() {
        return createParagraphRenderer(isBoxChecked() ? "\u2713" : "\u00A0");
    }

    @Override
    protected void adjustFieldLayout() {
        throw new RuntimeException("adjustFieldLayout() is deprecated and shouldn't be used. Override adjustFieldLayout(LayoutContext) instead");
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.layout.form.renderer.AbstractFormFieldRenderer#adjustFieldLayout()
     */
    @Override
    protected void adjustFieldLayout(LayoutContext layoutContext) {
        updatePdfFont((ParagraphRenderer) flatRenderer);
        if (null == font) {
            LoggerFactory.getLogger(getClass()).error(MessageFormatUtil.format(LogMessageConstant.ERROR_WHILE_LAYOUT_OF_FORM_FIELD_WITH_TYPE, "checkbox input"));
            setProperty(Html2PdfProperty.FORM_FIELD_FLATTEN, true);
        }
    }

    /**
     * Defines whether the box is checked or not.
     *
     * @return the default value of the checkbox field
     */
    public boolean isBoxChecked() {
        return null != this.<Object>getProperty(Html2PdfProperty.FORM_FIELD_CHECKED);
    }


    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.layout.form.renderer.AbstractFormFieldRenderer#applyAcroField(com.itextpdf.layout.renderer.DrawContext)
     */
    @Override
    protected void applyAcroField(DrawContext drawContext) {
        String name = getModelId();
        PdfDocument doc = drawContext.getDocument();
        Rectangle area = flatRenderer.getOccupiedArea().getBBox().clone();
        PdfPage page = doc.getPage(occupiedArea.getPageNumber());
        PdfButtonFormField checkBox = PdfFormField.createCheckBox(doc, area, name, isBoxChecked() ? "Yes" : "Off");
        PdfAcroForm.getAcroForm(doc, true).addField(checkBox, page);
    }

    /**
     * Creates a paragraph renderer.
     *
     * @param defaultValue the default value
     * @return the renderer
     */
    IRenderer createParagraphRenderer(String defaultValue) {
        Paragraph paragraph = new Paragraph(defaultValue)
                .setWidth(CHECKBOX_WIDTH)
                .setHeight(CHECKBOX_HEIGHT)
                .setBorder(new SolidBorder(CHECKBOX_BORDER_COLOR, CHECKBOX_BORDER_WIDTH))
                .setFontSize(CHECKBOX_FONT_SIZE)
                .setTextAlignment(TextAlignment.CENTER);
        Leading leading = this.<Leading>getProperty(Property.LEADING);
        if (leading != null) {
            paragraph.setProperty(Property.LEADING, leading);
        }
        return paragraph.createRendererSubTree();
    }
}

