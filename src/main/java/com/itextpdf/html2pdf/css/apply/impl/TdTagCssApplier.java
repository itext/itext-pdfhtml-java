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
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import java.util.Map;

/**
 * {@link ICssApplier} implementation for Td elements.
 */
public class TdTagCssApplier extends BlockCssApplier {
    
    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.impl.BlockCssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker worker) {
        super.apply(context, stylesContainer, worker);

        IPropertyContainer cell = worker.getElementResult();
        if (cell != null) {
            Map<String, String> cssProps = stylesContainer.getStyles();
            VerticalAlignmentApplierUtil.applyVerticalAlignmentForCells(cssProps, context, cell);

            float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
            float rem = context.getCssContext().getRootFontSize();

            Border[] bordersArray = BorderStyleApplierUtil.getBordersArray(cssProps, em, rem);
            if (bordersArray[0] == null) {
                cell.setProperty(Property.BORDER_TOP, Border.NO_BORDER);
            }
            if (bordersArray[1] == null) {
                cell.setProperty(Property.BORDER_RIGHT, Border.NO_BORDER);
            }
            if (bordersArray[2] == null) {
                cell.setProperty(Property.BORDER_BOTTOM, Border.NO_BORDER);
            }
            if (bordersArray[3] == null) {
                cell.setProperty(Property.BORDER_LEFT, Border.NO_BORDER);
            }
        }
    }
}
