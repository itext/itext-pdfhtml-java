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
import com.itextpdf.html2pdf.attach.impl.layout.form.element.Button;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LineRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;

public class ButtonRenderer extends AbstractOneLineTextFieldRenderer {

    private boolean isSplit = false;

    public ButtonRenderer(Button modelElement) {
        super(modelElement);
    }

    @Override
    public IRenderer getNextRenderer() {
        return new ButtonRenderer((Button) modelElement);
    }

    @Override
    protected void adjustFieldLayout() {
        List<LineRenderer> flatLines = ((ParagraphRenderer) flatRenderer).getLines();
        updatePdfFont((ParagraphRenderer) flatRenderer);
        if (!flatLines.isEmpty() && font != null) {
            if (flatLines.size() != 1) {
                isSplit = true;
            }
            cropContentLines(flatLines);
        } else {
            LoggerFactory.getLogger(getClass()).error(MessageFormat.format(LogMessageConstant.ERROR_WHILE_LAYOUT_OF_FORM_FIELD_WITH_TYPE, "button"));
            setProperty(Html2PdfProperty.FORM_FIELD_FLATTEN, true);
            baseline = flatRenderer.getOccupiedArea().getBBox().getTop();
            flatRenderer.getOccupiedArea().getBBox().setY(baseline).setHeight(0);
        }
    }

    @Override
    protected IRenderer createFlatRenderer() {
        return createParagraphRenderer(getDefaultValue());
    }

    @Override
    protected void applyAcroField(PdfDocument doc, PdfPage page, String name, String value, float fontSize, Rectangle area) {
        PdfFormField inputField = PdfFormField.createPushButton(doc, area, name, value, font, fontSize);
        applyDefaultFieldProperties(inputField);
        PdfAcroForm.getAcroForm(doc, true).addField(inputField, page);
    }

    @Override
    protected boolean isRendererFit(float availableWidth, float availableHeight) {
        return !isSplit && super.isRendererFit(availableWidth, availableHeight);
    }
}

