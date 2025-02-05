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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.OverflowPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.html2pdf.css.CssConstants;

import java.util.Map;

/**
 * Utilities class to apply overflow.
 */
public class OverflowApplierUtil {

    /**
     * Creates a new <code>OverflowApplierUtil</code> instance.
     */
    private OverflowApplierUtil() {
    }

    /**
     * Applies overflow to an element.
     *
     * @param cssProps the CSS properties
     * @param element  the element
     */
    public static void applyOverflow(Map<String, String> cssProps, IPropertyContainer element) {
        String overflow = null != cssProps && CssConstants.OVERFLOW_VALUES.contains(cssProps.get(CssConstants.OVERFLOW)) ? cssProps.get(CssConstants.OVERFLOW) : null;

        String overflowX = null != cssProps && CssConstants.OVERFLOW_VALUES.contains(cssProps.get(CssConstants.OVERFLOW_X)) ? cssProps.get(CssConstants.OVERFLOW_X) : overflow;
        if (CssConstants.HIDDEN.equals(overflowX) || CssConstants.AUTO.equals(overflowX) || CssConstants.SCROLL.equals(overflowX)) {
            element.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.HIDDEN);
        } else {
            element.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.VISIBLE);
        }

        String overflowY = null != cssProps && CssConstants.OVERFLOW_VALUES.contains(cssProps.get(CssConstants.OVERFLOW_Y)) ? cssProps.get(CssConstants.OVERFLOW_Y) : overflow;
        if (CssConstants.HIDDEN.equals(overflowY) || CssConstants.AUTO.equals(overflowY) || CssConstants.SCROLL.equals(overflowY)) {
            element.setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.HIDDEN);
        } else {
            element.setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.VISIBLE);
        }
    }
}
