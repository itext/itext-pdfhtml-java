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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssTypesValidationUtils;

import java.util.Map;

/**
 * Utility class to apply column-count values.
 */
public class ColumnCssApplierUtil {
    private ColumnCssApplierUtil() {
    }

    /**
     * Apply column-count to an element.
     *
     * @param cssProps the CSS properties
     * @param context  the Processor context
     * @param element the styles container
     */
    public static void applyColumnCount(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        if (context.isMulticolEnabled()) {
            Integer columnCount = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.COLUMN_COUNT));
            if (columnCount != null) {
                element.setProperty(Property.COLUMN_COUNT, columnCount);
            }
        }
    }
}