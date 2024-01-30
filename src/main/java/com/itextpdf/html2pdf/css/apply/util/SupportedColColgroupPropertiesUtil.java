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
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utilities class to get widths and mapping related to columns and column groups
 * as stated in paragraph 17.3 of https://www.w3.org/TR/CSS21/tables.html#q4.
 */
public class SupportedColColgroupPropertiesUtil {
    
    /**
     * These inheritable properties should be transferred from &lt;colgroup&gt;
     * to &lt;col&gt; and then to &lt;td&gt; or &lt;th&gt;.
     */
    private static final Set<String> CELL_CSS_PROPERTIES = Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList(
            CssConstants.BACKGROUND_COLOR, CssConstants.BACKGROUND_IMAGE, CssConstants.BACKGROUND_POSITION_X,
            CssConstants.BACKGROUND_POSITION_Y, CssConstants.BACKGROUND_SIZE, CssConstants.BACKGROUND_REPEAT,
            CssConstants.BACKGROUND_ORIGIN, CssConstants.BACKGROUND_CLIP, CssConstants.BACKGROUND_ATTACHMENT)));

    /** These properties don't need to be transferred from &lt;colgroup&gt; to &lt;col&gt;. */
    /*TODO DEVSIX-2090 visibility doesn't work on "chrome" or "safari" and it works on "firefox" but the results differ,
      The supported values are 'collapse' and 'visible'. The expected behaviour for 'collapse' is not to render those cols
      (the table layout should change ann the width should be diminished), and to clip cells that are spaned to none-collapsed one.
      The state of the content in clipped cells is not specified*/
    private static final Set<String> OWN_CSS_PROPERTIES = Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList(
            CssConstants.BORDER_BOTTOM_COLOR, CssConstants.BORDER_BOTTOM_STYLE, CssConstants.BORDER_BOTTOM_WIDTH,
            CssConstants.BORDER_LEFT_COLOR, CssConstants.BORDER_LEFT_STYLE, CssConstants.BORDER_LEFT_WIDTH,
            CssConstants.BORDER_RIGHT_COLOR, CssConstants.BORDER_RIGHT_STYLE, CssConstants.BORDER_RIGHT_WIDTH,
            CssConstants.BORDER_TOP_COLOR, CssConstants.BORDER_TOP_STYLE, CssConstants.BORDER_TOP_WIDTH,
            CssConstants.VISIBILITY)));

    /**
     * Gets the width.
     *
     * @param resolvedCssProps the resolved CSS properties
     * @param context the processor context
     * @return the width
     */
    //The Width is a special case, casue it should be transferred from <colgroup> to <col> but it not applied to <td> or <th>
    public static UnitValue getWidth(Map<String, String> resolvedCssProps, ProcessorContext context) {
        float em = CssDimensionParsingUtils.parseAbsoluteLength(resolvedCssProps.get(CssConstants.FONT_SIZE));
        String width = resolvedCssProps.get(CssConstants.WIDTH);
        return width != null ? CssDimensionParsingUtils.parseLengthValueToPt(width, em, context.getCssContext().getRootFontSize()) : null;
    }

    /**
     * Gets the cell properties.
     *
     * @param resolvedCssProps the resolved CSS properties
     * @return the cell properties
     */
    public static Map<String, String> getCellProperties(Map<String, String> resolvedCssProps) {
        return getFilteredMap(resolvedCssProps, CELL_CSS_PROPERTIES);
    }

    /**
     * Gets the own properties.
     *
     * @param resolvedCssProps the resolved css props
     * @return the own properties
     */
    public static Map<String, String> getOwnProperties(Map<String, String> resolvedCssProps) {
        return getFilteredMap(resolvedCssProps, OWN_CSS_PROPERTIES);
    }

    /**
     * Filters a given map so that it only contains supported keys.
     *
     * @param map the map
     * @param supportedKeys the supported keys
     * @return the filtered map
     */
    private static Map<String, String> getFilteredMap(Map<String, String> map, Set<String> supportedKeys) {
        Map<String, String> result = new HashMap<>();
        if (map != null) {
            for (String key : supportedKeys) {
                String value = map.get(key);
                if (value != null) {
                    result.put(key, value);
                }
            }
        }
        return result.size() > 0 ? result : null;
    }
}
