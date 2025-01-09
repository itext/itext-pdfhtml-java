/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.WidthHeightApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for linear-gradient elements in content CSS property.
 */
public class CssContentLinearGradientApplier implements ICssApplier {
    /** The default width of the div content in the points, and this will be 300 pixels. */
    private static final float DEFAULT_CONTENT_WIDTH_PT = 225;

    /** The default height of the div content in the points, and this will be 150 pixels. */
    private static final float DEFAULT_CONTENT_HEIGHT_PT = 112.5f;

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.ICssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        Map<String, String> cssProps = stylesContainer.getStyles();

        IPropertyContainer container = tagWorker.getElementResult();
        if (container != null) {
            if (container instanceof Div) {
                if (!cssProps.containsKey(CssConstants.WIDTH) || CssConstants.AUTO.equals(cssProps.get(CssConstants.WIDTH))) {
                    ((Div) container).setWidth(DEFAULT_CONTENT_WIDTH_PT);
                }
                if (!cssProps.containsKey(CssConstants.HEIGHT) || CssConstants.AUTO.equals(cssProps.get(CssConstants.HEIGHT))) {
                    ((Div) container).setHeight(DEFAULT_CONTENT_HEIGHT_PT);
                }
            }

            WidthHeightApplierUtil.applyWidthHeight(cssProps, context, container);
            BackgroundApplierUtil.applyBackground(cssProps, context, container);
        }
    }
}
