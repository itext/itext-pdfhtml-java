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
import com.itextpdf.layout.properties.grid.AutoRepeatValue;
import com.itextpdf.layout.properties.grid.BreadthValue;
import com.itextpdf.layout.properties.grid.FixedRepeatValue;
import com.itextpdf.layout.properties.grid.GridFlow;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.grid.AutoValue;
import com.itextpdf.layout.properties.grid.FitContentValue;
import com.itextpdf.layout.properties.grid.FlexValue;
import com.itextpdf.layout.properties.grid.GridValue;
import com.itextpdf.layout.properties.grid.MaxContentValue;
import com.itextpdf.layout.properties.grid.MinContentValue;
import com.itextpdf.layout.properties.grid.MinMaxValue;
import com.itextpdf.layout.properties.grid.PercentValue;
import com.itextpdf.layout.properties.grid.PointValue;
import com.itextpdf.layout.properties.grid.TemplateValue;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class to apply css grid properties and styles.
 */
public final class GridApplierUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GridApplierUtil.class);

    private static final Pattern SPAN_PLACEMENT = Pattern.compile("^span (\\d+)$");

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
     * Applies grid properties to a grid item.
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

        if (cssProps.get(CssConstants.GRID_AREA) != null) {
            String gridArea = cssProps.get(CssConstants.GRID_AREA);
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

        applyGridItemPlacement(cssProps.get(CssConstants.GRID_COLUMN_END), element, Property.GRID_COLUMN_END, Property.GRID_COLUMN_SPAN);
        applyGridItemPlacement(cssProps.get(CssConstants.GRID_COLUMN_START), element, Property.GRID_COLUMN_START, Property.GRID_COLUMN_SPAN);
        applyGridItemPlacement(cssProps.get(CssConstants.GRID_ROW_END), element, Property.GRID_ROW_END, Property.GRID_ROW_SPAN);
        applyGridItemPlacement(cssProps.get(CssConstants.GRID_ROW_START), element, Property.GRID_ROW_START, Property.GRID_ROW_SPAN);
    }

    private static void applyGridItemPlacement(String value, IPropertyContainer element, int property, int spanProperty) {
        if (value == null) {
            return;
        }
        Integer intValue = CssDimensionParsingUtils.parseInteger(value);
        if (intValue != null) {
            element.setProperty(property, intValue);
        } else {
            Matcher matcher = SPAN_PLACEMENT.matcher(value.trim());
            if (matcher.matches()) {
                Integer spanValue = CssDimensionParsingUtils.parseInteger(matcher.group(1));
                if (spanValue != null) {
                    element.setProperty(spanProperty, spanValue);
                }
            }
        }
    }

    /**
     * Applies grid properties to a grid container.
     *
     * @param cssProps the CSS properties
     * @param container the grid container
     * @param context the context
     */
    public static void applyGridContainerProperties(Map<String, String> cssProps, IPropertyContainer container,
                                                    ProcessorContext context) {

        final float emValue = CssDimensionParsingUtils.parseAbsoluteFontSize(cssProps.get(CssConstants.FONT_SIZE));
        final float remValue = context.getCssContext().getRootFontSize();

        applyTemplate(cssProps.get(CssConstants.GRID_TEMPLATE_COLUMNS), container, Property.GRID_TEMPLATE_COLUMNS,
                emValue, remValue);
        applyTemplate(cssProps.get(CssConstants.GRID_TEMPLATE_ROWS), container, Property.GRID_TEMPLATE_ROWS,
                emValue, remValue);

        applyAuto(cssProps.get(CssConstants.GRID_AUTO_ROWS), container, Property.GRID_AUTO_ROWS, emValue, remValue);
        applyAuto(cssProps.get(CssConstants.GRID_AUTO_COLUMNS), container, Property.GRID_AUTO_COLUMNS, emValue, remValue);
        applyFlow(cssProps.get(CssConstants.GRID_AUTO_FLOW), container);

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

    private static void applyAuto(String autoStr, IPropertyContainer container, int property, float emValue, float remValue) {
        if (autoStr != null) {
            TemplateValue value = parseTemplateValue(autoStr, emValue, remValue);
            if (value != null) {
                container.setProperty(property, value);
            }
        }
    }

    private static void applyFlow(String flow, IPropertyContainer container) {
        GridFlow value = GridFlow.ROW;
        if (flow != null) {
            if (flow.contains(CommonCssConstants.COLUMN)) {
                if (flow.contains(CssConstants.DENSE)) {
                    value = GridFlow.COLUMN_DENSE;
                } else {
                    value = GridFlow.COLUMN;
                }
            } else if (flow.contains(CssConstants.DENSE)) {
                value = GridFlow.ROW_DENSE;
            }
        }
        container.setProperty(Property.GRID_FLOW, value);
    }

    private static void applyTemplate(String templateStr, IPropertyContainer container, int property, float emValue, float remValue) {
        if (templateStr != null) {
            final List<String> templateStrArray = CssUtils.extractShorthandProperties(templateStr).get(0);
            final List<TemplateValue> templateResult = new ArrayList<>();
            for (String str : templateStrArray) {
                TemplateValue value = parseTemplateValue(str, emValue, remValue);
                if (value != null) {
                    templateResult.add(value);
                }
            }
            if (!templateResult.isEmpty()) {
                container.setProperty(property, templateResult);
            }
        }
    }

    private static TemplateValue parseTemplateValue(String str, float emValue, float remValue) {
        if (str == null) {
            return null;
        }
        final UnitValue unit = CssDimensionParsingUtils.parseLengthValueToPt(str, emValue, remValue);
        if (unit != null) {
            if (unit.isPointValue()) {
                return new PointValue(unit.getValue());
            } else {
                return new PercentValue(unit.getValue());
            }
        }
        if (CommonCssConstants.MIN_CONTENT.equals(str)) {
            return MinContentValue.VALUE;
        }
        if (CommonCssConstants.MAX_CONTENT.equals(str)) {
            return MaxContentValue.VALUE;
        }
        if (CommonCssConstants.AUTO.equals(str)) {
            return AutoValue.VALUE;
        }
        final Float fr = CssDimensionParsingUtils.parseFlex(str);
        if (fr != null) {
            return new FlexValue((float) fr);
        }
        if (determineFunction(str, CommonCssConstants.FIT_CONTENT)) {
            return parseFitContent(str, emValue, remValue);
        }
        if (determineFunction(str, CommonCssConstants.REPEAT)) {
            return parseRepeat(str, emValue, remValue);
        }
        if (determineFunction(str, CssConstants.MINMAX)) {
            return parseMinMax(str, emValue, remValue);
        }
        return null;
    }

    private static FitContentValue parseFitContent(String str, float emValue, float remValue) {
        UnitValue length = CssDimensionParsingUtils.parseLengthValueToPt(
                str.substring(CommonCssConstants.FIT_CONTENT.length() + 1, str.length() - 1), emValue, remValue);
        if (length == null) {
            return null;
        }
        return new FitContentValue(length);
    }

    private static boolean determineFunction(String str, String function) {
        return str.startsWith(function)
                && str.length() > function.length() + 2;
    }

    private static TemplateValue parseMinMax(String str, float emValue, float remValue) {
        int parameterSeparator = str.indexOf(',');
        if (parameterSeparator < 0) {
            return null;
        }
        TemplateValue min = parseTemplateValue(str.substring(CssConstants.MINMAX.length() + 1, parameterSeparator).trim(), emValue, remValue);
        TemplateValue max = parseTemplateValue(str.substring(parameterSeparator + 1, str.length() - 1).trim(), emValue, remValue);
        if (!(min instanceof BreadthValue) || !(max instanceof BreadthValue)) {
            return null;
        }
        return new MinMaxValue((BreadthValue)min, (BreadthValue)max);
    }

    private static TemplateValue parseRepeat(String str, float emValue, float remValue) {
        List<GridValue> repeatList = new ArrayList<>();
        int repeatCount = -1;
        int repeatTypeEndIndex = str.indexOf(',');
        if (repeatTypeEndIndex < 0) {
            return null;
        }
        String repeatType = str.substring(CommonCssConstants.REPEAT.length() + 1, repeatTypeEndIndex).trim();
        try {
            repeatCount = Integer.parseInt(repeatType);
        } catch (NumberFormatException ex) {
            //do nothing
        }

        List<String> repeatStr = CssUtils.extractShorthandProperties(
                str.substring(repeatTypeEndIndex + 1, str.length() - 1)).get(0);
        for (String strValue : repeatStr) {
            TemplateValue value = parseTemplateValue(strValue, emValue, remValue);
            if (value instanceof GridValue) {
                repeatList.add((GridValue) value);
            } else {
                return null;
            }
        }
        if (repeatCount > 0) {
            return new FixedRepeatValue(repeatCount, repeatList);
        } else if (CssConstants.AUTO_FILL.equals(repeatType)) {
            return new AutoRepeatValue(false, repeatList);
        } else if (CssConstants.AUTO_FIT.equals(repeatType)) {
            return new AutoRepeatValue(true, repeatList);
        }
        return null;
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
