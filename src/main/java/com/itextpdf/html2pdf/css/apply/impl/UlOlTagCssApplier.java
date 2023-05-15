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
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.ListStyleApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.ColumnContainer;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.properties.BaseDirection;
import com.itextpdf.layout.properties.ListSymbolPosition;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for Ul en Ol elements.
 */
public class UlOlTagCssApplier extends BlockCssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.impl.BlockCssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        if (!(tagWorker.getElementResult() instanceof List || tagWorker.getElementResult() instanceof ColumnContainer)) {
            return;
        }
        Map<String, String> css = stylesContainer.getStyles();

        IPropertyContainer list = tagWorker.getElementResult();

        if (CssConstants.INSIDE.equals(css.get(CssConstants.LIST_STYLE_POSITION))) {
            list.setProperty(Property.LIST_SYMBOL_POSITION, ListSymbolPosition.INSIDE);
        } else {
            list.setProperty(Property.LIST_SYMBOL_POSITION, ListSymbolPosition.OUTSIDE);
        }

        ListStyleApplierUtil.applyListStyleTypeProperty(stylesContainer, css, context, list);
        ListStyleApplierUtil.applyListStyleImageProperty(css, context, list);
        ColumnCssApplierUtil.applyColumnCount(css, context, list);

        super.apply(context, stylesContainer, tagWorker);

        // process the padding considering the direction
        boolean isRtl = BaseDirection.RIGHT_TO_LEFT.equals(list.<BaseDirection>getProperty(Property.BASE_DIRECTION));
        if ((isRtl && !list.hasProperty(Property.PADDING_RIGHT)) || (!isRtl && !list.hasProperty(Property.PADDING_LEFT))) {
            float em = CssDimensionParsingUtils.parseAbsoluteLength(css.get(CssConstants.FONT_SIZE));
            float rem = context.getCssContext().getRootFontSize();
            UnitValue startPadding = CssDimensionParsingUtils
                    .parseLengthValueToPt(css.get(CssConstants.PADDING_INLINE_START), em, rem);
            list.setProperty(isRtl ? Property.PADDING_RIGHT : Property.PADDING_LEFT, startPadding);
        }
    }
}
