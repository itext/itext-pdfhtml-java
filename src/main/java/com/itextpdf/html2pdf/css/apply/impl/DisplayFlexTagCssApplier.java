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
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.FlexApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

/**
 * {@link ICssApplier} implementation for elements with display flex.
 */
public class DisplayFlexTagCssApplier extends BlockCssApplier {
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        super.apply(context, stylesContainer, tagWorker);
        final IPropertyContainer container = tagWorker.getElementResult();
        if (container != null) {
            FlexApplierUtil.applyFlexContainerProperties(stylesContainer.getStyles(), container);
            //TODO DEVSIX-5087 remove these lines when working on a ticket
            container.deleteOwnProperty(Property.FLOAT);
            container.deleteOwnProperty(Property.CLEAR);
            container.deleteOwnProperty(Property.OVERFLOW_X);
            container.deleteOwnProperty(Property.OVERFLOW_Y);
        }
        ColumnCssApplierUtil.applyColumnCount(stylesContainer.getStyles(), context, container);
    }
}
