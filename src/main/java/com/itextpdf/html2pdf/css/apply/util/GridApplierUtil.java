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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import java.util.Map;

/**
 * Utilities class to apply css grid properties and styles.
 */
public final class GridApplierUtil {
    private GridApplierUtil() {
        // empty constructor
    }

    /**
     * Applies grid properties to an element.
     *
     * @param cssProps the CSS properties
     * @param element  the element
     */
    public static void applyGridItemProperties(Map<String, String> cssProps, IPropertyContainer element) {

        Integer columnStart = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_COLUMN_START));
        if (columnStart != null) {
            element.setProperty(Property.GRID_COLUMN_START, columnStart);
        }

        Integer columnEnd = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_COLUMN_END));
        if (columnEnd != null) {
            element.setProperty(Property.GRID_COLUMN_END, columnEnd);
        }

        Integer rowStart = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_ROW_START));
        if (rowStart != null) {
            element.setProperty(Property.GRID_ROW_START, rowStart);
        }

        Integer rowEnd = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_ROW_END));
        if (rowEnd != null) {
            element.setProperty(Property.GRID_ROW_END, rowEnd);
        }
    }
}
