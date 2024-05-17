/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link ICssApplier} implementation for elements with display grid.
 */
public class DisplayGridTagCssApplier extends BlockCssApplier {

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        super.apply(context, stylesContainer, tagWorker);
        final IPropertyContainer container = tagWorker.getElementResult();
        if (container != null) {
            Map<String, String> cssProps = stylesContainer.getStyles();

            final float emValue = CssDimensionParsingUtils.parseAbsoluteFontSize(cssProps.get(CssConstants.FONT_SIZE));
            final float remValue = context.getCssContext().getRootFontSize();

            final String templateColumnsStr = cssProps.get(CssConstants.GRID_TEMPLATE_COLUMNS);
            parseAndSetTemplate(templateColumnsStr, container, Property.GRID_TEMPLATE_COLUMNS, emValue, remValue);

            final String templateRowsStr = cssProps.get(CssConstants.GRID_TEMPLATE_ROWS);
            parseAndSetTemplate(templateRowsStr, container, Property.GRID_TEMPLATE_ROWS, emValue, remValue);

            final String autoRows = cssProps.get(CssConstants.GRID_AUTO_ROWS);
            final UnitValue autoRowsUnit = CssDimensionParsingUtils.parseLengthValueToPt(autoRows, emValue, remValue);
            if (autoRowsUnit != null) {
                container.setProperty(Property.GRID_AUTO_ROWS, autoRowsUnit);
            }

            final String autoColumns = cssProps.get(CssConstants.GRID_AUTO_COLUMNS);
            final UnitValue autoColumnsUnit = CssDimensionParsingUtils.parseLengthValueToPt(autoColumns, emValue, remValue);
            if (autoColumnsUnit != null) {
                container.setProperty(Property.GRID_AUTO_COLUMNS, autoColumnsUnit);
            }

            final UnitValue columnGap = CssDimensionParsingUtils.parseLengthValueToPt(cssProps.get(CssConstants.COLUMN_GAP),
                    emValue, remValue);
            if (columnGap != null) {
                container.setProperty(Property.COLUMN_GAP, columnGap.getValue());
            }
            final UnitValue rowGap = CssDimensionParsingUtils.parseLengthValueToPt(cssProps.get(CssConstants.ROW_GAP),
                    emValue, remValue);
            if (rowGap != null) {
                container.setProperty(Property.ROW_GAP, rowGap.getValue());
            }
        }
        MultiColumnCssApplierUtil.applyMultiCol(stylesContainer.getStyles(), context, container);
    }

    private static void parseAndSetTemplate(String templateStr, IPropertyContainer container, int property, float emValue, float remValue) {
        if (templateStr != null) {
            final List<String> templateStrArray = CssUtils.extractShorthandProperties(templateStr).get(0);
            final List<UnitValue> templateResult = new ArrayList<>();
            for (String s : templateStrArray) {
                final UnitValue trackUnit = CssDimensionParsingUtils.parseLengthValueToPt(s, emValue, remValue);
                if (trackUnit != null) {
                    templateResult.add(trackUnit);
                }
            }
            if (!templateResult.isEmpty()) {
                container.setProperty(property, templateResult);
            }
        }
    }
}
