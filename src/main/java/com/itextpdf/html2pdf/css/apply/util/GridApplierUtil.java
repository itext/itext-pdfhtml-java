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

import com.itextpdf.commons.datastructures.Tuple2;
import com.itextpdf.commons.utils.MapUtil;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IAbstractElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.grid.AutoRepeatValue;
import com.itextpdf.layout.properties.grid.AutoValue;
import com.itextpdf.layout.properties.grid.BreadthValue;
import com.itextpdf.layout.properties.grid.FitContentValue;
import com.itextpdf.layout.properties.grid.FixedRepeatValue;
import com.itextpdf.layout.properties.grid.FlexValue;
import com.itextpdf.layout.properties.grid.GridFlow;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class to apply css grid properties and styles.
 */
public final class GridApplierUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GridApplierUtil.class);

    private static final Pattern SPAN_PLACEMENT = Pattern.compile("^span\\s+(.+)$");

    /**
     * Property map which maps property order in grid-area css prop to layout property
     */
    private static final Map<Integer, Integer> propsMap = new HashMap<>();

    /**
     * Property map which maps property order in grid-area css prop to grid span property
     */
    private static final Map<Integer, Integer> spansMap = new HashMap<>();
    static {
        propsMap.put(0, Property.GRID_ROW_START);
        propsMap.put(1, Property.GRID_COLUMN_START);
        propsMap.put(2, Property.GRID_ROW_END);
        propsMap.put(3, Property.GRID_COLUMN_END);

        spansMap.put(0, Property.GRID_ROW_SPAN);
        spansMap.put(1, Property.GRID_COLUMN_SPAN);
        spansMap.put(2, Property.GRID_ROW_SPAN);
        spansMap.put(3, Property.GRID_COLUMN_SPAN);
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

        applyGridArea(cssProps, element);

        applyGridItemPlacement(cssProps.get(CssConstants.GRID_COLUMN_END), element, Property.GRID_COLUMN_END, Property.GRID_COLUMN_SPAN);
        applyGridItemPlacement(cssProps.get(CssConstants.GRID_COLUMN_START), element, Property.GRID_COLUMN_START, Property.GRID_COLUMN_SPAN);
        applyGridItemPlacement(cssProps.get(CssConstants.GRID_ROW_END), element, Property.GRID_ROW_END, Property.GRID_ROW_SPAN);
        applyGridItemPlacement(cssProps.get(CssConstants.GRID_ROW_START), element, Property.GRID_ROW_START, Property.GRID_ROW_SPAN);
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

        NamedAreas namedAreas = applyNamedAreas(cssProps.get(CssConstants.GRID_TEMPLATE_AREAS), container);

        applyTemplate(cssProps.get(CssConstants.GRID_TEMPLATE_COLUMNS), container, Property.GRID_TEMPLATE_COLUMNS,
                emValue, remValue, namedAreas);
        applyTemplate(cssProps.get(CssConstants.GRID_TEMPLATE_ROWS), container, Property.GRID_TEMPLATE_ROWS,
                emValue, remValue, namedAreas);

        applyAuto(cssProps.get(CssConstants.GRID_AUTO_ROWS), container, Property.GRID_AUTO_ROWS, emValue, remValue);
        applyAuto(cssProps.get(CssConstants.GRID_AUTO_COLUMNS), container, Property.GRID_AUTO_COLUMNS, emValue, remValue);
        applyFlow(cssProps.get(CssConstants.GRID_AUTO_FLOW), container);

        applyGap(container, emValue, remValue, cssProps.get(CssConstants.COLUMN_GAP), Property.COLUMN_GAP);
        applyGap(container, emValue, remValue, cssProps.get(CssConstants.GRID_COLUMN_GAP), Property.COLUMN_GAP);
        applyGap(container, emValue, remValue, cssProps.get(CssConstants.ROW_GAP), Property.ROW_GAP);
        applyGap(container, emValue, remValue, cssProps.get(CssConstants.GRID_ROW_GAP), Property.ROW_GAP);
    }

    private static void applyGap(IPropertyContainer container, float emValue, float remValue, String gap, int property) {
        final UnitValue gapValue = CssDimensionParsingUtils.parseLengthValueToPt(gap, emValue, remValue);
        if (gapValue != null) {
            container.setProperty(property, gapValue.getValue());
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

    private static NamedAreas applyNamedAreas(String gridTemplateAreas, IPropertyContainer container) {
        if (gridTemplateAreas == null || CommonCssConstants.NONE.equals(gridTemplateAreas)) {
            return null;
        }

        NamedAreas namedAreas = parseGridTemplateAreas(gridTemplateAreas);
        List<IElement> children = ((IAbstractElement) container).getChildren();
        for (IElement child : children) {
            // Area name can be only in GRID_ROW_START
            Object propValue = child.<Object>getProperty(Property.GRID_ROW_START);
            if (propValue instanceof String) {
                // It will override all props by integers if area name is found
                namedAreas.setPlaceToElement((String) propValue, child);
            }
        }

        return namedAreas;
    }

    private static void applyTemplate(String templateStr, IPropertyContainer container, int property,
                                      float emValue, float remValue, NamedAreas namedAreas) {
        if (templateStr != null && templateStr.contains(CssConstants.SUBGRID)) {
            LOGGER.warn(Html2PdfLogMessageConstant.SUBGRID_VALUE_IS_NOT_SUPPORTED);
        }

        Map<String, List<Integer>> lineNumbersPerName = new HashMap<>();
        int namedAreaLength = 0;
        final boolean applyColumns = property == Property.GRID_TEMPLATE_COLUMNS;
        if (namedAreas != null) {
            if (applyColumns) {
                lineNumbersPerName = namedAreas.getNamedColumnNumbers();
                namedAreaLength = namedAreas.getColumnsCount();
            } else {
                lineNumbersPerName = namedAreas.getNamedRowNumbers();
                namedAreaLength = namedAreas.getRowsCount();
            }
        }
        final List<TemplateValue> templateResult = new ArrayList<>();
        int currentLine = 1;
        if (templateStr != null) {
            final List<String> templateStrArray = CssUtils.extractShorthandProperties(templateStr).get(0);
            for (String str : templateStrArray) {
                TemplateValue value = parseTemplateValue(str, emValue, remValue, lineNumbersPerName, currentLine);
                if (value != null) {
                    templateResult.add(value);
                    if (value instanceof FixedRepeatValue) {
                        currentLine += ((FixedRepeatValue) value).getRepeatCount() *
                                ((FixedRepeatValue) value).getValues().size();
                    } else {
                        ++currentLine;
                    }
                }
            }
            if (templateResult.isEmpty()) {
                LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.GRID_TEMPLATE_WAS_NOT_RECOGNISED,
                        applyColumns ? "columns" : "rows"));
            } else {
                container.setProperty(property, templateResult);
            }
        }

        // Now process all children to apply line names
        int startProperty;
        int endProperty;
        int spanProperty;
        if (applyColumns) {
            startProperty = Property.GRID_COLUMN_START;
            endProperty = Property.GRID_COLUMN_END;
            spanProperty = Property.GRID_COLUMN_SPAN;
        } else {
            startProperty = Property.GRID_ROW_START;
            endProperty = Property.GRID_ROW_END;
            spanProperty = Property.GRID_ROW_SPAN;
        }

        List<IElement> children = ((IAbstractElement) container).getChildren();
        for (IElement child : children) {
            substituteLinename(lineNumbersPerName, startProperty, child,
                    Math.max(namedAreaLength + 1, currentLine), "-start");
            substituteLinename(lineNumbersPerName, endProperty, child,
                    Math.max(namedAreaLength + 1, currentLine), "-end");
            substituteLinenameInSpan(lineNumbersPerName, startProperty, endProperty, spanProperty, child,
                    Math.max(namedAreaLength + 1, currentLine));
        }
    }

    private static void substituteLinenameInSpan(Map<String, List<Integer>> lineNumbersPerName,
                                                 int startProperty, int endProperty, int spanProperty,
                                                 IElement child, int lastLineNumber) {
        Object propValue = child.<Object>getProperty(spanProperty);
        if (!(propValue instanceof String)) {
            // It means it's null or we processed it earlier
            return;
        }
        child.deleteOwnProperty(spanProperty);

        // Here we need one of grid-row/column-start or grid-row/column-end
        // as otherwise the property doesn't have sense
        // And we know that there can't be both start and end at this point
        Integer startPoint = child.<Integer>getProperty(startProperty);
        Integer endPoint = child.<Integer>getProperty(endProperty);
        if (startPoint == null && endPoint == null) {
            return;
        }

        Tuple2<Integer, String> parsedValue = parseStringValue((String) propValue);
        final int distance = parsedValue.getFirst();
        final String strValue = parsedValue.getSecond();

        List<Integer> lineNumbers = lineNumbersPerName.get(strValue);
        if (lineNumbers == null || distance <= 0 || strValue == null) {
            return;
        }

        // We should span by X linenames back or forth starting from current position
        final int direction = startPoint != null ? 1 : -1;
        final int startPosition = startPoint != null ? startPoint.intValue() : endPoint.intValue();

        // linenumbers are sorted, let's find current position in the array
        int start = -1;
        int correction = -direction;
        for (Integer lineNumber : lineNumbers) {
            ++start;
            if (startPosition <= lineNumber) {
                if (startPosition == lineNumber) {
                    correction = 0;
                }
                break;
            }
        }

        int spanIdx = start + distance * direction + correction;
        if (spanIdx < 0) {
            // Going negative is not supported
            return;
        }
        int endPosition;
        if (spanIdx > lineNumbers.size() - 1) {
            // Increase grid
            endPosition = lastLineNumber + spanIdx - (lineNumbers.size() - 1);
        } else {
            endPosition = lineNumbers.get(spanIdx);
        }

        if (direction == 1) {
            child.setProperty(endProperty, endPosition);
        } else {
            child.setProperty(startProperty, endPosition);
        }
    }

    private static void substituteLinename(Map<String, List<Integer>> lineNumbersPerName, int property,
                                           IElement child, int lastLineNumber, String alternateLineNameSuffix) {
        Object propValue = child.<Object>getProperty(property);
        if (!(propValue instanceof String)) {
            // It means it's null or we processed it earlier
            return;
        }
        child.deleteOwnProperty(property);

        Tuple2<Integer, String> parsedValue = parseStringValue((String) propValue);
        int idx = parsedValue.getFirst();
        String strValue = parsedValue.getSecond();
        if (idx == 0 || strValue == null) {
            return;
        }

        List<Integer> lineNumbers = lineNumbersPerName.get(strValue);
        if (lineNumbers == null) {
            lineNumbers = lineNumbersPerName.get(strValue + alternateLineNameSuffix);
        }
        if (lineNumbers == null) {
            return;
        }

        if (idx > lineNumbers.size()) {
            // Increase grid
            // We should also go to negative in a similar manner
            // but currently we don't support negative columns/rows
            child.setProperty(property, lastLineNumber + idx - lineNumbers.size());
            return;
        }

        if (Math.abs(idx) > lineNumbers.size()) {
            // The case when it's too negative
            LOGGER.error(Html2PdfLogMessageConstant.ADDING_GRID_LINES_TO_THE_LEFT_OR_TOP_IS_NOT_SUPPORTED);
            return;
        }
        if (idx < 0) {
            idx = lineNumbers.size() + idx + 1;
        }
        child.setProperty(property, lineNumbers.get(idx - 1));
    }

    private static Tuple2<Integer, String> parseStringValue(String strPropValue) {
        String[] propValues = strPropValue.split("\\s+");
        int idx = 1;
        String strValue = null;
        if (propValues.length == 1) {
            strValue = propValues[0];
        } else if (propValues.length == 2) {
            // Here we have two options
            // grid-row-start: 1 a and grid-row-start a 1
            final Integer i0 = CssDimensionParsingUtils.parseInteger(propValues[0]);
            final Integer i1 = CssDimensionParsingUtils.parseInteger(propValues[1]);
            final Integer i = i0 != null ? i0 : i1;
            if (i != null) {
                idx = i.intValue();
            }
            strValue = i0 != null ? propValues[1] : propValues[0];
        }

        return new Tuple2<Integer, String>(idx, strValue);
    }

    private static TemplateValue parseTemplateValue(String str, float emValue, float remValue) {
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
        if (determineFunction(str, CssConstants.MINMAX)) {
            return parseMinMax(str, emValue, remValue);
        }
        return null;
    }

    private static TemplateValue parseTemplateValue(String str, float emValue, float remValue,
            Map<String, List<Integer>> lineNumbersPerName, int currentLine) {

        if (str == null) {
            return null;
        }

        if (str.startsWith("[") && str.endsWith("]")) {
            // It's a linename
            String strStripped = str.substring(1, str.length() - 1);
            String[] linenames = strStripped.trim().split("\\s+");
            for (String linename : linenames) {
                if (!lineNumbersPerName.containsKey(linename)) {
                    lineNumbersPerName.put(linename, new ArrayList<>(1));
                }
                lineNumbersPerName.get(linename).add(currentLine);
            }

            return null;
        }

        if (determineFunction(str, CommonCssConstants.REPEAT)) {
            return parseRepeat(str, emValue, remValue, lineNumbersPerName, currentLine);
        }

        return parseTemplateValue(str, emValue, remValue);
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

    private static TemplateValue parseRepeat(String str, float emValue, float remValue,
                                             Map<String, List<Integer>> lineNumbersPerName, int currentLine) {
        List<GridValue> repeatList = new ArrayList<>();
        int repeatTypeEndIndex = str.indexOf(',');
        if (repeatTypeEndIndex < 0) {
            return null;
        }
        String repeatType = str.substring(CommonCssConstants.REPEAT.length() + 1, repeatTypeEndIndex).trim();
        Integer repeatCount = CssDimensionParsingUtils.parseInteger(repeatType);

        List<String> repeatStr = CssUtils.extractShorthandProperties(
                str.substring(repeatTypeEndIndex + 1, str.length() - 1)).get(0);
        Map<String, List<Integer>> repeatLineNumbersPerName = new HashMap<>();
        for (String strValue : repeatStr) {
            TemplateValue value = parseTemplateValue(strValue, emValue, remValue,
                    repeatLineNumbersPerName, currentLine);
            if (value instanceof GridValue) {
                repeatList.add((GridValue) value);
                ++currentLine;
            }
        }

        // Now multiply line numbers for repeats
        if (repeatCount != null && repeatCount.intValue() > 1) {
            for (List<Integer> repeatLineNumbers : repeatLineNumbersPerName.values()) {
                List<Integer> extraLineNumbers = new ArrayList<>();
                for (int i = 1; i < repeatCount.intValue(); ++i) {
                    for (Integer lineNumber : repeatLineNumbers) {
                        final int extraLineNumber = lineNumber.intValue() + repeatList.size() * i;
                        if (!extraLineNumbers.contains(extraLineNumber)) {
                            extraLineNumbers.add(extraLineNumber);
                        }
                    }
                }
                repeatLineNumbers.removeAll(extraLineNumbers);
                repeatLineNumbers.addAll(extraLineNumbers);
            }
        }

        // Now merge with common lineNumbersPerName
        MapUtil.merge(lineNumbersPerName, repeatLineNumbersPerName, (List<Integer> dest, List<Integer> source) -> {
            dest.removeAll(source);
            dest.addAll(source);
            return dest;
        });

        if (repeatCount != null) {
            return new FixedRepeatValue(repeatCount.intValue(), repeatList);
        } else if (CssConstants.AUTO_FILL.equals(repeatType)) {
            if (!lineNumbersPerName.isEmpty()) {
                LOGGER.warn(Html2PdfLogMessageConstant.LINENAMES_ARE_NOT_SUPPORTED_WITHIN_AUTO_REPEAT);
            }
            return new AutoRepeatValue(false, repeatList);
        } else if (CssConstants.AUTO_FIT.equals(repeatType)) {
            if (!lineNumbersPerName.isEmpty()) {
                LOGGER.warn(Html2PdfLogMessageConstant.LINENAMES_ARE_NOT_SUPPORTED_WITHIN_AUTO_REPEAT);
            }
            return new AutoRepeatValue(true, repeatList);
        }
        return null;
    }

    private static void applyGridArea(Map<String, String> cssProps, IPropertyContainer element) {
        if (cssProps.get(CssConstants.GRID_AREA) == null) {
            return;
        }

        String gridArea = cssProps.get(CssConstants.GRID_AREA);
        String[] gridAreaParts = gridArea.split("/");
        for(int i = 0; i < gridAreaParts.length; ++i) {
            String part = gridAreaParts[i].trim();
            if (CommonCssConstants.AUTO.equals(part)) {
                // We override already set value if any
                element.deleteOwnProperty(propsMap.get(i).intValue());
                continue;
            }

            // If it's an area name from grid-template-areas, it will go into GRID_ROW_START
            applyGridItemPlacement(part, element, propsMap.get(i).intValue(), spansMap.get(i).intValue());
        }
    }

    private static void applyGridItemPlacement(String value, IPropertyContainer element,
                                               int property, int spanProperty) {
        if (value == null) {
            return;
        }

        Integer intValue = CssDimensionParsingUtils.parseInteger(value);
        if (intValue != null) {
            // grid-row-start: 2
            element.setProperty(property, intValue);
            return;
        }

        Matcher matcher = SPAN_PLACEMENT.matcher(value.trim());
        if (matcher.matches()) {
            Integer spanValue = CssDimensionParsingUtils.parseInteger(matcher.group(1));
            if (spanValue != null) {
                // grid-row-start: span 2
                element.setProperty(spanProperty, spanValue);
            } else {
                // grid-row-start: span linename or grid-row-start: span linename 2
                // Later on we will convert linename to number or remove
                element.setProperty(spanProperty, matcher.group(1).trim());
            }

            return;
        }

        // grid-row-start: linename
        // Later on we will convert linename to number or remove
        element.setProperty(property, value.trim());
    }

    private static NamedAreas parseGridTemplateAreas(String templateAreas) {
        NamedAreas res = new NamedAreas();

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

        return res;
    }

    private final static class NamedAreas {
        private static final String DOT_PLACEHOLDER = ".";
        private static final String AREA_START_SUFFIX = "-start";
        private static final String AREA_END_SUFFIX = "-end";
        private final Map<String, Placement> areas = new HashMap<>();
        private int rowsCount = 0;
        private int columnsCount = 0;

        NamedAreas() {
            // Empty constructor
        }

        public void addName(String name, int row, int column) {
            // Dot has a special meaning saying this area is not named and grid-template-areas doesn't work for it
            // Numbers are also not allowed
            if (DOT_PLACEHOLDER.equals(name) || CssDimensionParsingUtils.parseInteger(name) != null) {
                return;
            }

            Placement placement = areas.get(name);
            if (placement == null) {
                areas.put(name, new Placement(row, row, column, column));
            } else {
                placement.increaseSpansTill(row, column);
            }

            rowsCount = Math.max(rowsCount, row);
            columnsCount = Math.max(columnsCount, column);
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

        public Map<String, List<Integer>> getNamedRowNumbers() {
            Map<String, List<Integer>> namedNumbers = new HashMap<>(areas.size() * 2);
            for (Map.Entry<String, Placement> area : areas.entrySet()) {
                namedNumbers.put(area.getKey() + AREA_START_SUFFIX,
                        new ArrayList<>(Arrays.asList(area.getValue().getRowStart())));
                namedNumbers.put(area.getKey() + AREA_END_SUFFIX,
                        new ArrayList<>(Arrays.asList(area.getValue().getRowEnd() + 1)));
            }

            return namedNumbers;
        }

        public Map<String, List<Integer>> getNamedColumnNumbers() {
            Map<String, List<Integer>> namedNumbers = new HashMap<>();
            for (Map.Entry<String, Placement> area : areas.entrySet()) {
                namedNumbers.put(area.getKey() + AREA_START_SUFFIX,
                        new ArrayList<>(Arrays.asList(area.getValue().getColumnStart())));
                namedNumbers.put(area.getKey() + AREA_END_SUFFIX,
                        new ArrayList<>(Arrays.asList(area.getValue().getColumnEnd() + 1)));
            }

            return namedNumbers;
        }

        public int getRowsCount() {
            return rowsCount;
        }

        public int getColumnsCount() {
            return columnsCount;
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
                valid = column == columnStart;
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
