/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
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

import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.IFormField;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.renderer.BlockRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LineRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract {@link BlockRenderer} for form fields with text content.
 */
public abstract class AbstractTextFieldRenderer extends AbstractFormFieldRenderer {

    /**
     * The font to be used for the text.
     */
    protected PdfFont font;

    /**
     * Creates a new {@link AbstractTextFieldRenderer} instance.
     *
     * @param modelElement the model element
     */
    AbstractTextFieldRenderer(IFormField modelElement) {
        super(modelElement);
    }

    /**
     * Creates a paragraph renderer.
     *
     * @param defaultValue the default value
     * @return the renderer
     */
    IRenderer createParagraphRenderer(String defaultValue) {
        if (defaultValue.trim().isEmpty()) {
            // TODO DEVSIX-1491: change to 'defaultValue = "\u00A0"' after trimming of non-breakable spaces is fixed;
            defaultValue = "\u00B7";
        }
        Paragraph paragraph = new Paragraph(defaultValue).setMargin(0);
        Leading leading = this.<Leading>getProperty(Property.LEADING);
        if (leading != null) {
            paragraph.setProperty(Property.LEADING, leading);
        }
        return paragraph.createRendererSubTree();
    }

    /**
     * Adjust number of content lines.
     *
     * @param lines       the lines that need to be rendered
     * @param bBox        the bounding box
     * @param linesNumber the number of lines
     */
    void adjustNumberOfContentLines(List<LineRenderer> lines, Rectangle bBox, int linesNumber) {
        float averageLineHeight = bBox.getHeight() / lines.size();
        if (lines.size() != linesNumber) {
            float actualHeight = averageLineHeight * linesNumber;
            bBox.moveUp(bBox.getHeight() - actualHeight);
            bBox.setHeight(actualHeight);
        }
        if (lines.size() > linesNumber) {
            List<LineRenderer> subList = new ArrayList<>(lines.subList(0, linesNumber));
            lines.clear();
            lines.addAll(subList);
        }
    }

    /**
     * Applies the default field properties.
     *
     * @param inputField the input field
     */
    void applyDefaultFieldProperties(PdfFormField inputField) {
        inputField.getWidgets().get(0).setHighlightMode(PdfAnnotation.HIGHLIGHT_NONE);
        inputField.setBorderWidth(0);
        TransparentColor color = getPropertyAsTransparentColor(Property.FONT_COLOR);
        if (color != null) {
            inputField.setColor(color.getColor());
        }
    }

    /**
     * Updates the font.
     *
     * @param renderer the renderer
     */
    void updatePdfFont(ParagraphRenderer renderer) {
        Object retrievedFont;
        if (renderer != null) {
            List<LineRenderer> lines = renderer.getLines();
            if (lines != null) {
                for (LineRenderer line : lines) {
                    for (IRenderer child : line.getChildRenderers()) {
                        retrievedFont = child.<PdfFont>getProperty(Property.FONT);
                        if (retrievedFont instanceof PdfFont) {
                            font = (PdfFont) retrievedFont;
                            return;
                        }
                    }
                }
            }
        }
        retrievedFont = renderer.<PdfFont>getProperty(Property.FONT);
        if (retrievedFont instanceof PdfFont) {
            font = (PdfFont) retrievedFont;
        }
    }
}
