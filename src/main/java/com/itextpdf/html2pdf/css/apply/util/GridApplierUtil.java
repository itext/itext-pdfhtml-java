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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class to apply css grid properties and styles.
 */
public final class GridApplierUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GridApplierUtil.class);

    private static final Map<String, NamedAreas> namedAreasCache = new ConcurrentHashMap<>();
    private static final int NAMED_AREAS_CACHE_CAPACITY = 10;

    /**
     * Property map which maps property order in grid-area css prop to layout property
     */
    private final static Map<Integer, Integer> propsMap = new HashMap<>();
    static {
        propsMap.put(0, Property.GRID_ROW_START);
        propsMap.put(1, Property.GRID_COLUMN_START);
        propsMap.put(2, Property.GRID_ROW_END);
        propsMap.put(3, Property.GRID_COLUMN_END);
    }

    private GridApplierUtil() {
        // empty constructor
    }

    /**
     * Applies grid properties to an element.
     *
     * @param cssProps the CSS properties
     * @param stylesContainer the styles container
     * @param element  the element
     */
    public static void applyGridItemProperties(Map<String, String> cssProps, IStylesContainer stylesContainer,
            IPropertyContainer element) {
        if (!(stylesContainer instanceof JsoupElementNode)
                || !(((JsoupElementNode) stylesContainer).parentNode() instanceof JsoupElementNode)) {
            return;
        }
        final Map<String, String> parentStyles = ((JsoupElementNode) ((JsoupElementNode) stylesContainer)
                .parentNode()).getStyles();
        if (!CssConstants.GRID.equals(parentStyles.get(CssConstants.DISPLAY))) {
            // Not a grid - return
            return;
        }

        // Let's parse grid-template-areas here on child level as we need it here
        String gridTemplateAreas = parentStyles.get(CssConstants.GRID_TEMPLATE_AREAS);
        NamedAreas namedAreas = null;
        if (gridTemplateAreas != null && !CommonCssConstants.NONE.equals(gridTemplateAreas)) {
            namedAreas = parseGridTemplateAreas(gridTemplateAreas);
        }

        for (Map.Entry<String, String> entry : cssProps.entrySet()) {
            if (CssConstants.GRID_AREA.equals(entry.getKey())) {
                String gridArea = entry.getValue();
                String[] gridAreaParts = gridArea.split("/");
                for(int i = 0; i < gridAreaParts.length; ++i) {
                    String part = gridAreaParts[i].trim();
                    if (CommonCssConstants.AUTO.equals(part)) {
                        // We override already set value if any
                        element.deleteOwnProperty(propsMap.get(i).intValue());
                        continue;
                    }
                    Integer partInt = CssDimensionParsingUtils.parseInteger(part);
                    if (partInt != null) {
                        element.setProperty(propsMap.get(i).intValue(), partInt);
                    } else if (namedAreas != null && i == 0) {
                        // We are interested in the 1st element in grid area for now
                        // so let's even break immediately
                        namedAreas.setPlaceToElement(part, element);
                        break;
                    }
                }
            }

            if (CssConstants.GRID_COLUMN_START.equals(entry.getKey())) {
                Integer columnStart = CssDimensionParsingUtils.parseInteger(entry.getValue());
                if (columnStart != null) {
                    element.setProperty(Property.GRID_COLUMN_START, columnStart);
                }
            }

            if (CssConstants.GRID_COLUMN_END.equals(entry.getKey())) {
                Integer columnStart = CssDimensionParsingUtils.parseInteger(entry.getValue());
                if (columnStart != null) {
                    element.setProperty(Property.GRID_COLUMN_END, columnStart);
                }
            }

            if (CssConstants.GRID_ROW_START.equals(entry.getKey())) {
                Integer columnStart = CssDimensionParsingUtils.parseInteger(entry.getValue());
                if (columnStart != null) {
                    element.setProperty(Property.GRID_ROW_START, columnStart);
                }
            }

            if (CssConstants.GRID_ROW_END.equals(entry.getKey())) {
                Integer columnStart = CssDimensionParsingUtils.parseInteger(entry.getValue());
                if (columnStart != null) {
                    element.setProperty(Property.GRID_ROW_END, columnStart);
                }
            }
        }
    }

    /**
     * Applies properties to a grid container.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyGridContainerProperties(Map<String, String> cssProps, ProcessorContext context,
            IPropertyContainer element) {
        final float emValue = CssDimensionParsingUtils.parseAbsoluteFontSize(cssProps.get(CommonCssConstants.FONT_SIZE));
        final float remValue = context.getCssContext().getRootFontSize();

        final String templateColumnsStr = cssProps.get(CssConstants.GRID_TEMPLATE_COLUMNS);
        parseAndSetTemplate(templateColumnsStr, element, Property.GRID_TEMPLATE_COLUMNS, emValue, remValue);

        final String templateRowsStr = cssProps.get(CssConstants.GRID_TEMPLATE_ROWS);
        parseAndSetTemplate(templateRowsStr, element, Property.GRID_TEMPLATE_ROWS, emValue, remValue);

        final String autoRows = cssProps.get(CssConstants.GRID_AUTO_ROWS);
        final UnitValue autoRowsUnit = CssDimensionParsingUtils.parseLengthValueToPt(autoRows, emValue, remValue);
        if (autoRowsUnit != null) {
            element.setProperty(Property.GRID_AUTO_ROWS, autoRowsUnit);
        }

        final String autoColumns = cssProps.get(CssConstants.GRID_AUTO_COLUMNS);
        final UnitValue autoColumnsUnit = CssDimensionParsingUtils.parseLengthValueToPt(autoColumns, emValue, remValue);
        if (autoColumnsUnit != null) {
            element.setProperty(Property.GRID_AUTO_COLUMNS, autoColumnsUnit);
        }

        final UnitValue columnGap = CssDimensionParsingUtils.parseLengthValueToPt(cssProps.get(CommonCssConstants.COLUMN_GAP),
                emValue, remValue);
        if (columnGap != null) {
            element.setProperty(Property.COLUMN_GAP, columnGap.getValue());
        }
        final UnitValue rowGap = CssDimensionParsingUtils.parseLengthValueToPt(cssProps.get(CommonCssConstants.ROW_GAP),
                emValue, remValue);
        if (rowGap != null) {
            element.setProperty(Property.ROW_GAP, rowGap.getValue());
        }
    }

    private static void parseAndSetTemplate(String templateStr, IPropertyContainer container, int property,
            float emValue, float remValue) {
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

    private static NamedAreas parseGridTemplateAreas(String templateAreas) {
        NamedAreas res = namedAreasCache.get(templateAreas);
        if (res != null) {
            return res;
        }
        res = new NamedAreas();

        String[] rows = templateAreas.split("[\\\"|']");
        int rowIdx = 0;
        for(String row : rows) {
            String rowTrimmed = row.trim();
            if (rowTrimmed.isEmpty()) {
                continue;
            }
            ++rowIdx;
            int columnIdx = 0;
            String[] names = rowTrimmed.split("\\s+");
            for(String name : names) {
                if (name.isEmpty()) {
                    continue;
                }
                ++columnIdx;

                res.addName(name, rowIdx, columnIdx);
            }
        }

        if (namedAreasCache.size() >= NAMED_AREAS_CACHE_CAPACITY) {
            namedAreasCache.clear();
        }
        namedAreasCache.put(templateAreas, res);
        return res;
    }

    private final static class NamedAreas {
        private final static String DOT_PLACEHOLDER = ".";
        private final Map<String, Placement> areas = new HashMap<>();

        NamedAreas() {
            // Empty constructor
        }

        public void addName(String name, int row, int column) {
            // It has a special meaning saying this area is not named and grid-template-areas doesn't work for it
            if (DOT_PLACEHOLDER.equals(name)) {
                return;
            }
            Placement placement = areas.get(name);
            if (placement == null) {
                areas.put(name, new Placement(row, row, column, column));
            } else {
                placement.increaseSpansTill(row, column);
            }
        }

        public void setPlaceToElement(String name, IPropertyContainer element) {
            Placement placement = areas.get(name);
            if (placement == null) {
                return;
            }

            element.setProperty(Property.GRID_ROW_START, placement.getRowStart());
            element.setProperty(Property.GRID_ROW_END, placement.getRowEnd() + 1);
            element.setProperty(Property.GRID_COLUMN_START, placement.getColumnStart());
            element.setProperty(Property.GRID_COLUMN_END, placement.getColumnEnd() + 1);
        }
    }

    private final static class Placement {

        // 1-based indexes.
        private int rowStart;
        private int rowEnd;
        private int columnStart;
        private int columnEnd;

        public Placement(int rowStart, int rowEnd, int columnStart, int columnEnd) {
            this.rowStart = rowStart;
            this.rowEnd = rowEnd;
            this.columnStart = columnStart;
            this.columnEnd = columnEnd;
        }

        public void increaseSpansTill(int row, int column) {
            boolean valid = false;
            if (row == rowEnd + 1) {
                valid = column == columnEnd;
            } else if (column == columnEnd + 1) {
                valid = row == rowEnd;
            } else {
                // valid stays false
            }
            if (!valid) {
                LOGGER.error(Html2PdfLogMessageConstant.GRID_TEMPLATE_AREAS_IS_INVALID);
                return;
            }
            rowEnd = row;
            columnEnd = column;
        }

        public int getRowStart() {
            return rowStart;
        }

        public int getRowEnd() {
            return rowEnd;
        }

        public int getColumnStart() {
            return columnStart;
        }

        public int getColumnEnd() {
            return columnEnd;
        }
    }
}
