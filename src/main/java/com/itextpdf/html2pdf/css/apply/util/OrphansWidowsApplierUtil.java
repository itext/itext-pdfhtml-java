/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
    Authors: iText Software.

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

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.ParagraphOrphansControl;
import com.itextpdf.layout.properties.ParagraphWidowsControl;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import java.util.Map;

/**
 * Utilities class to apply orphans and widows properties.
 */
public class OrphansWidowsApplierUtil {

    public static final int MAX_LINES_TO_MOVE = 2;
    public static final boolean OVERFLOW_PARAGRAPH_ON_VIOLATION = false;

    /**
     * Creates a {@link OrphansWidowsApplierUtil} instance.
     */
    private OrphansWidowsApplierUtil() {

    }

    /**
     * Applies orphans and widows properties to an element.
     *
     * @param cssProps the CSS properties
     * @param element  to which the property will be applied.
     */
    public static void applyOrphansAndWidows(Map<String, String> cssProps, IPropertyContainer element) {
        if (cssProps != null) {
            if (cssProps.containsKey(CssConstants.WIDOWS)) {
                Integer minWidows = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.WIDOWS));
                if (minWidows != null && minWidows > 0) {
                    element.setProperty(Property.WIDOWS_CONTROL,
                            new ParagraphWidowsControl(minWidows, MAX_LINES_TO_MOVE, OVERFLOW_PARAGRAPH_ON_VIOLATION));
                }
            }

            if (cssProps.containsKey(CssConstants.ORPHANS)) {
                Integer minOrphans = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.ORPHANS));
                if (minOrphans != null && minOrphans > 0) {
                    element.setProperty(Property.ORPHANS_CONTROL,
                            new ParagraphOrphansControl(minOrphans));
                }
            }
        }
    }
}
