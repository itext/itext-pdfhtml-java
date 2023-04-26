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

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.BodyHtmlStylesContainer;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.MarginApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PaddingApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for Body elements.
 */
public class BodyTagCssApplier implements ICssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.ICssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        Map<String, String> cssProps = stylesContainer.getStyles();
        BodyHtmlStylesContainer styleProperty = new BodyHtmlStylesContainer();
        IPropertyContainer container = tagWorker.getElementResult();
        if (container != null) {
            BackgroundApplierUtil.applyBackground(cssProps, context, styleProperty);
            MarginApplierUtil.applyMargins(cssProps, context, styleProperty);
            PaddingApplierUtil.applyPaddings(cssProps, context, styleProperty);
            BorderStyleApplierUtil.applyBorders(cssProps, context, styleProperty);
            if (styleProperty.hasStylesToApply()) {
                container.setProperty(Html2PdfProperty.BODY_STYLING, styleProperty);
            }
            FontStyleApplierUtil.applyFontStyles(cssProps, context, stylesContainer, container);
        }
    }
}
