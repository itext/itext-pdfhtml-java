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
import com.itextpdf.forms.util.DrawingUtil;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.Radio;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;


/**
 * The {@link AbstractOneLineTextFieldRenderer} implementation for radio buttons.
 */
public class RadioRenderer extends AbstractFormFieldRenderer {

    private static final Color DEFAULT_CHECKED_COLOR = ColorConstants.BLACK;
    private static final Color DEFAULT_COLOR = ColorConstants.LIGHT_GRAY;
    private static final float DEFAULT_SIZE = 8.25f; // 11px
    private static final HorizontalAlignment DEFAULT_HORIZONTAL_ALIGNMENT = HorizontalAlignment.CENTER;
    private static final VerticalAlignment DEFAULT_VERTICAL_ALIGNMENT = VerticalAlignment.MIDDLE;

    /**
     * Creates a new {@link RadioRenderer} instance.
     *
     * @param modelElement the model element
     */
    public RadioRenderer(Radio modelElement) {
        super(modelElement);
        setProperty(Property.VERTICAL_ALIGNMENT, VerticalAlignment.MIDDLE);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.IRenderer#getNextRenderer()
     */
    @Override
    public IRenderer getNextRenderer() {
        return new RadioRenderer((Radio) modelElement);
    }

    @Override
    protected IRenderer createFlatRenderer() {
        UnitValue heightUV = getPropertyAsUnitValue(Property.HEIGHT);
        UnitValue widthUV = getPropertyAsUnitValue(Property.WIDTH);
        float height = null == heightUV ? DEFAULT_SIZE : heightUV.getValue();
        float width = null == widthUV ? DEFAULT_SIZE : widthUV.getValue();
        float size = Math.min(height, width);
        Paragraph paragraph = new Paragraph()
                .setWidth(size)
                .setHeight(size)
                .setHorizontalAlignment(DEFAULT_HORIZONTAL_ALIGNMENT)
                .setVerticalAlignment(DEFAULT_VERTICAL_ALIGNMENT);
        return new FlatParagraphRenderer(paragraph);
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
        this.setProperty(Property.BACKGROUND, null);
    }

    /**
     * Defines whether the radio is checked or not.
     *
     * @return the default value of the radio field
     */
    public boolean isBoxChecked() {
        return null != this.<Object>getProperty(Html2PdfProperty.FORM_FIELD_CHECKED);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.layout.form.renderer.AbstractFormFieldRenderer#applyAcroField(com.itextpdf.layout.renderer.DrawContext)
     */
    @Override
    protected void applyAcroField(DrawContext drawContext) {
        PdfDocument doc = drawContext.getDocument();
        Rectangle area = flatRenderer.getOccupiedArea().getBBox().clone();
        PdfPage page = doc.getPage(occupiedArea.getPageNumber());
        String groupName = this.<String>getProperty(Html2PdfProperty.FORM_FIELD_VALUE);
        PdfButtonFormField radioGroup = (PdfButtonFormField) PdfAcroForm.getAcroForm(doc, true).getField(groupName);
        if (null == radioGroup) {
            radioGroup = PdfFormField.createRadioGroup(doc, groupName, "on");
        }
        if (isBoxChecked()) {
            radioGroup.setValue(getModelId());
        }
        PdfButtonFormField field = (PdfButtonFormField) PdfFormField.createRadioButton(doc, area, radioGroup, getModelId());
        field.setCheckType(PdfFormField.TYPE_CIRCLE);
        PdfAcroForm.getAcroForm(doc, true).addField(radioGroup, page);
    }

    @Override
    protected boolean isLayoutBasedOnFlatRenderer() {
        return false;
    }

    private class FlatParagraphRenderer extends ParagraphRenderer {

        public FlatParagraphRenderer(Paragraph modelElement) {
            super(modelElement);
        }

        @Override
        public void drawChildren(DrawContext drawContext) {
            PdfCanvas canvas = drawContext.getCanvas();
            Rectangle rectangle = flatRenderer.getOccupiedArea().getBBox();
            float radius = (float) Math.min(rectangle.getWidth(), rectangle.getHeight()) / 2;
            canvas.saveState();
            canvas.setFillColor(DEFAULT_COLOR);
            DrawingUtil.drawCircle(canvas, rectangle.getLeft() + radius, rectangle.getBottom() + radius, radius);
            if (isBoxChecked()) {
                canvas.setFillColor(DEFAULT_CHECKED_COLOR);
                DrawingUtil.drawCircle(canvas, rectangle.getLeft() + radius, rectangle.getBottom() + radius, radius / 2);
            }
            canvas.restoreState();
        }
    }
}
