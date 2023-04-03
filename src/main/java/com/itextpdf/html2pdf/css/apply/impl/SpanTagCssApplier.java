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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.forms.form.element.IFormField;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FloatApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.HyphenationApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.MarginApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OpacityApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OutlineApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PaddingApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PositionApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.FloatPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for Span elements.
 */
public class SpanTagCssApplier implements ICssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.ICssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        SpanTagWorker spanTagWorker = (SpanTagWorker) tagWorker;
        Map<String, String> cssStyles = stylesContainer.getStyles();
        for (IPropertyContainer child : spanTagWorker.getOwnLeafElements()) {
            // Workaround for form fields so that SpanTagCssApplier does not apply its font-size to the child.
            // Form fields have their own CSS applier
            if (!(child instanceof IFormField)) {
                applyChildElementStyles(child, cssStyles, context, stylesContainer);
            }
        }

        VerticalAlignmentApplierUtil.applyVerticalAlignmentForInlines(cssStyles, context, stylesContainer,
                spanTagWorker.getAllElements());

        if (cssStyles.containsKey(CssConstants.OPACITY)) {
            for (IPropertyContainer elem : spanTagWorker.getAllElements()) {
                if (elem instanceof Text && !elem.hasProperty(Property.OPACITY)) {
                    OpacityApplierUtil.applyOpacity(cssStyles, context, elem);
                }
            }
        }

        String floatVal = cssStyles.get(CssConstants.FLOAT);
        if (floatVal != null && !CssConstants.NONE.equals(floatVal)) {
            for (IPropertyContainer elem : spanTagWorker.getAllElements()) {
                FloatPropertyValue kidFloatVal = elem.<FloatPropertyValue>getProperty(Property.FLOAT);
                if (kidFloatVal == null || FloatPropertyValue.NONE.equals(kidFloatVal)) {
                    FloatApplierUtil.applyFloating(cssStyles, context, elem);
                }
            }
        }

        if (spanTagWorker.getAllElements() != null) {
            for (IPropertyContainer child : spanTagWorker.getAllElements()) {
                FloatPropertyValue kidFloatVal = child.<FloatPropertyValue>getProperty(Property.FLOAT);
                if (child instanceof Text && !child.hasOwnProperty(Property.BACKGROUND)
                        && (kidFloatVal == null || FloatPropertyValue.NONE.equals(kidFloatVal))) {
                    BackgroundApplierUtil.applyBackground(cssStyles, context, child);
                }
            }
        }
    }

    /**
     * Applies styles to child elements.
     *
     * @param element the element
     * @param css the CSS mapping
     * @param context the processor context
     * @param stylesContainer the styles container
     */
    protected void applyChildElementStyles(IPropertyContainer element, Map<String, String> css, ProcessorContext context, IStylesContainer stylesContainer) {
        FontStyleApplierUtil.applyFontStyles(css, context, stylesContainer, element);
        BackgroundApplierUtil.applyBackground(css, context, element);
        BorderStyleApplierUtil.applyBorders(css, context, element);
        OutlineApplierUtil.applyOutlines(css, context, element);
        HyphenationApplierUtil.applyHyphenation(css, context, stylesContainer, element);
        MarginApplierUtil.applyMargins(css, context, element);
        PositionApplierUtil.applyPosition(css, context, element);
        FloatApplierUtil.applyFloating(css, context, element);
        PaddingApplierUtil.applyPaddings(css, context, element);
    }

}
