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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.IFormField;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.HyphenationApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.MarginApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OpacityApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PaddingApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OutlineApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PositionApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FloatApplierUtil;
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Property;

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
            // Form fields have their own CSS applier // TODO remove when form fields are not leaf elements anymore
            if (!(child instanceof IFormField)) {
                applyChildElementStyles(child, cssStyles, context, stylesContainer);
            }
        }
        VerticalAlignmentApplierUtil.applyVerticalAlignmentForInlines(cssStyles, context, stylesContainer, spanTagWorker.getAllElements());
        if (cssStyles.containsKey(CssConstants.OPACITY)) {
            for (IPropertyContainer elem : spanTagWorker.getAllElements()) {
                if (elem instanceof Text && !elem.hasProperty(Property.OPACITY)) {
                    OpacityApplierUtil.applyOpacity(cssStyles, context, elem);
                }
            }
        }

        // TODO as for now spans are flattened, let's at least make kids of floating spans floating too
        String floatVal = cssStyles.get(CssConstants.FLOAT);
        if (floatVal != null && !CssConstants.NONE.equals(floatVal)) {
            for (IPropertyContainer elem : spanTagWorker.getAllElements()) {
                FloatPropertyValue kidFloatVal = elem.<FloatPropertyValue>getProperty(Property.FLOAT);
                if (kidFloatVal == null || FloatPropertyValue.NONE.equals(kidFloatVal)) {
                    FloatApplierUtil.applyFloating(cssStyles, context, elem);
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
    private void applyChildElementStyles(IPropertyContainer element, Map<String, String> css, ProcessorContext context, IStylesContainer stylesContainer) {
        FontStyleApplierUtil.applyFontStyles(css, context, stylesContainer, element);
        //TODO: Background-applying currently doesn't work in html way for spans inside other spans.
        BackgroundApplierUtil.applyBackground(css, context, element);
        //TODO: Border-applying currently doesn't work in html way for spans inside other spans.
        BorderStyleApplierUtil.applyBorders(css, context, element);
        OutlineApplierUtil.applyOutlines(css, context, element);
        HyphenationApplierUtil.applyHyphenation(css, context, stylesContainer, element);
        //TODO: Margins-applying currently doesn't work in html way for spans inside other spans. (see SpanTest#spanTest07)
        MarginApplierUtil.applyMargins(css, context, element);
        PositionApplierUtil.applyPosition(css, context, element);
        FloatApplierUtil.applyFloating(css, context, element);
        PaddingApplierUtil.applyPaddings(css, context, element);
    }

}
