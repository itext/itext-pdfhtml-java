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
import com.itextpdf.html2pdf.attach.impl.tags.DisplayTableRowTagWorker;
import com.itextpdf.html2pdf.attach.wrapelement.TableRowWrapper;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OpacityApplierUtil;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for table rows.
 */
public class DisplayTableRowTagCssApplier extends BlockCssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.impl.BlockCssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        Map<String, String> cssProps = stylesContainer.getStyles();
        super.apply(context, stylesContainer, tagWorker);
        TableRowWrapper rowWrapper = ((DisplayTableRowTagWorker) tagWorker).getTableRowWrapper();
        for (Cell cell : rowWrapper.getCells()) {
            if (!cell.hasProperty(Property.BACKGROUND)) {
                BackgroundApplierUtil.applyBackground(cssProps, context, cell);
            }
            if (!cell.hasProperty(Property.OPACITY)) {
                OpacityApplierUtil.applyOpacity(cssProps, context, cell);
            }
            // TODO DEVSIX-1554
//            if (!cell.hasProperty(Property.POSITION)) {
//                PositionApplierUtil.applyPosition(cssProps, context, cell);
//            }
//            if (!cell.hasProperty(Property.TRANSFORM)) {
//                TransformationApplierUtil.applyTransformation(cssProps, context, cell);
//            }
        }
    }
}
