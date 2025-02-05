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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.GridContainer;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.grid.AutoRepeatValue;
import com.itextpdf.layout.properties.grid.FitContentValue;
import com.itextpdf.layout.properties.grid.FixedRepeatValue;
import com.itextpdf.layout.properties.grid.GridFlow;
import com.itextpdf.layout.properties.grid.GridValue;
import com.itextpdf.layout.properties.grid.LengthValue;
import com.itextpdf.layout.properties.grid.MinMaxValue;
import com.itextpdf.layout.properties.grid.PercentValue;
import com.itextpdf.layout.properties.grid.PointValue;
import com.itextpdf.layout.properties.grid.TemplateValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("UnitTest")
public class GridApplierUtilTest extends ExtendedITextTest {
    @Test
    public void applyColumnStartTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "2");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_COLUMN_START);
        Assertions.assertEquals(2, columnStart);
    }

    @Test
    public void applyColumnEndTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_END, "4");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_COLUMN_END);
        Assertions.assertEquals(4, columnStart);
    }

    @Test
    public void applyRowStartTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_ROW_START, "3");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_ROW_START);
        Assertions.assertEquals(3, columnStart);
    }

    @Test
    public void applyRowStartSpanTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_ROW_START, "span  3");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Integer rowSpan = element.<Integer>getProperty(Property.GRID_ROW_SPAN);
        Assertions.assertEquals(3, rowSpan);
    }

    @Test
    public void applyRowEndTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_ROW_END, "11");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_ROW_END);
        Assertions.assertEquals(11, columnStart);
    }

    @Test
    public void applyInvalidColumnStartTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, CssConstants.AUTO);
        Div element = new Div();
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        GridApplierUtil.applyGridContainerProperties(new HashMap<>(), grid, new ProcessorContext(new ConverterProperties()));

        Integer columnStart = element.<Integer>getProperty(Property.GRID_COLUMN_START);
        Assertions.assertNull(columnStart);
    }

    @Test
    public void applyGridAreaBasicTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "1 / 2 /  3  / 4");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridAreaAutoTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "auto / auto / auto  / auto");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridAreaOrderTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "1 / 2 /  3  / 4");
        cssProps.put(CssConstants.GRID_COLUMN_START, "1");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridAreaOrder2Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "1");
        cssProps.put(CssConstants.GRID_AREA, "1 / 2 /  3  / 4");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridAreaOrder3Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "auto / 2 /  3  / 4");
        cssProps.put(CssConstants.GRID_ROW_START, "1");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridAreaOrder4Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_ROW_START, "1");
        cssProps.put(CssConstants.GRID_AREA, "auto  / 2 /  3  / 4");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridPropertiesToNotGrid() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "1 / 2 /  3  / 4");
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.remove(CssConstants.DISPLAY);

        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyNoneGridTemplateAreasTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, CommonCssConstants.NONE);
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, CommonCssConstants.NONE);
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(new HashMap<>(), grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridTemplateAreas1Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "somename1");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "\"somename1 Somename1\" ' somename1     Somename1' ' somename1     Somename1'");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridTemplateAreasOrder1Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_ROW_END, "3");
        cssProps.put(CssConstants.GRID_AREA, "somename1");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "\"somename1 Somename1\" ' somename1     Somename1' ' somename1     Somename1'");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridTemplateAreasOrder2Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "somename1");
        cssProps.put(CssConstants.GRID_ROW_END, "3");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "\"somename1 Somename1\" ' somename1     Somename1' ' somename1     Somename1'");

        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridTemplateAreasInvalidNameTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "1");
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'a b' '1 1'");
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_AREAS_IS_INVALID, count = 2))
    public void applyInvalidGridTemplateAreas1Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "b");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'a b' 'b a'");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_AREAS_IS_INVALID, count = 2))
    public void applyInvalidGridTemplateAreas2Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "a");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'a b a' 'a b a'");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridTemplateAreasWithDotsTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, ".");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'. . a' '. . a'");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(new HashMap<>(), grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    // ------------------ Grid container tests ------------------
    @Test
    public void containerAutoValuesTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_AUTO_COLUMNS, "11px");
        cssProps.put(CssConstants.GRID_AUTO_ROWS, "30%");
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(8.25f, element.<LengthValue>getProperty(Property.GRID_AUTO_COLUMNS).getValue());
        Assertions.assertEquals(30.0f, element.<LengthValue>getProperty(Property.GRID_AUTO_ROWS).getValue());
    }

    @Test
    public void containerTemplateValuesTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_TEMPLATE_COLUMNS, "min-content 1.5fr auto 2fr 100px 20%");
        cssProps.put(CssConstants.GRID_TEMPLATE_ROWS, "10px 20pt 3em 5rem");
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        List<TemplateValue> actualColValues = element.<List<TemplateValue>>getProperty(Property.GRID_TEMPLATE_COLUMNS);
        Assertions.assertEquals(6, actualColValues.size());
        Assertions.assertEquals(actualColValues.get(0).getType(), GridValue.ValueType.MIN_CONTENT);
        Assertions.assertEquals(actualColValues.get(1).getType(), GridValue.ValueType.FLEX);
        Assertions.assertEquals(actualColValues.get(2).getType(), GridValue.ValueType.AUTO);
        Assertions.assertEquals(actualColValues.get(3).getType(), GridValue.ValueType.FLEX);
        Assertions.assertEquals(75.0f, ((PointValue)actualColValues.get(4)).getValue());
        Assertions.assertEquals(20.0f, ((PercentValue)actualColValues.get(5)).getValue());
        List<TemplateValue> actualRowValues = element.<List<TemplateValue>>getProperty(Property.GRID_TEMPLATE_ROWS);
        Assertions.assertEquals(4, actualRowValues.size());
        Assertions.assertEquals(7.5f, ((PointValue)actualRowValues.get(0)).getValue());
        Assertions.assertEquals(20.0f, ((PointValue)actualRowValues.get(1)).getValue());
        Assertions.assertEquals(0.0f, ((PointValue)actualRowValues.get(2)).getValue());
        Assertions.assertEquals(60.0f, ((PointValue)actualRowValues.get(3)).getValue());
    }

    @Test
    public void containerComplexTemplateValuesTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_TEMPLATE_COLUMNS,
                "minmax(min-content, 1fr) fit-content(40%) fit-content(20px) repeat(2, fit-content(200px))");
        cssProps.put(CssConstants.GRID_TEMPLATE_ROWS, "repeat(3, 100px) repeat(auto-fit, minmax(100px, auto))");
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));

        List<TemplateValue> actualColValues = element.<List<TemplateValue>>getProperty(Property.GRID_TEMPLATE_COLUMNS);
        Assertions.assertEquals(4, actualColValues.size());

        Assertions.assertEquals(GridValue.ValueType.MINMAX, actualColValues.get(0).getType());
        Assertions.assertEquals(GridValue.ValueType.MIN_CONTENT, ((MinMaxValue)actualColValues.get(0)).getMin().getType());
        Assertions.assertEquals(GridValue.ValueType.FLEX, ((MinMaxValue)actualColValues.get(0)).getMax().getType());
        Assertions.assertEquals(GridValue.ValueType.FIT_CONTENT, actualColValues.get(1).getType());
        Assertions.assertEquals(GridValue.ValueType.PERCENT, ((FitContentValue)actualColValues.get(1)).getLength().getType());
        Assertions.assertEquals(GridValue.ValueType.FIT_CONTENT, actualColValues.get(2).getType());
        Assertions.assertEquals(GridValue.ValueType.POINT, ((FitContentValue)actualColValues.get(2)).getLength().getType());
        Assertions.assertEquals(GridValue.ValueType.FIXED_REPEAT, actualColValues.get(3).getType());
        Assertions.assertEquals(GridValue.ValueType.FIT_CONTENT, ((FixedRepeatValue)actualColValues.get(3)).getValues().get(0).getType());

        List<TemplateValue> actualRowValues = element.<List<TemplateValue>>getProperty(Property.GRID_TEMPLATE_ROWS);
        Assertions.assertEquals(2, actualRowValues.size());

        Assertions.assertEquals(GridValue.ValueType.FIXED_REPEAT, actualRowValues.get(0).getType());
        Assertions.assertEquals(GridValue.ValueType.POINT, ((FixedRepeatValue)actualRowValues.get(0)).getValues().get(0).getType());
        Assertions.assertEquals(GridValue.ValueType.AUTO_REPEAT, actualRowValues.get(1).getType());
        Assertions.assertEquals(GridValue.ValueType.MINMAX, ((AutoRepeatValue)actualRowValues.get(1)).getValues().get(0).getType());
    }

    @Test
    public void containerGapValuesTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.COLUMN_GAP, "11px");
        cssProps.put(CssConstants.ROW_GAP, "30%");
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(8.25f, element.<Float>getProperty(Property.COLUMN_GAP));
        Assertions.assertEquals(30, element.<Float>getProperty(Property.ROW_GAP));
    }

    @Test
    public void containerGridGapValuesTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_GAP, "11px");
        cssProps.put(CssConstants.GRID_ROW_GAP, "30%");
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(8.25f, element.<Float>getProperty(Property.COLUMN_GAP));
        Assertions.assertEquals(30, element.<Float>getProperty(Property.ROW_GAP));
    }

    @Test
    public void columnFlowTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_AUTO_FLOW, CommonCssConstants.COLUMN);
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(GridFlow.COLUMN, element.<GridFlow>getProperty(Property.GRID_FLOW));
    }

    @Test
    public void nullFlowTest() {
        Map<String, String> cssProps = new HashMap<>();
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(GridFlow.ROW, element.<GridFlow>getProperty(Property.GRID_FLOW));
    }

    @Test
    public void denseFlowTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_AUTO_FLOW, CssConstants.DENSE);
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(GridFlow.ROW_DENSE, element.<GridFlow>getProperty(Property.GRID_FLOW));
    }

    @Test
    public void columnDenseFlowTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_AUTO_FLOW, CommonCssConstants.COLUMN + " " + CssConstants.DENSE);
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(GridFlow.COLUMN_DENSE, element.<GridFlow>getProperty(Property.GRID_FLOW));
    }

    @Test
    public void invalidFlowTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_AUTO_FLOW, "some text");
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        Assertions.assertEquals(GridFlow.ROW, element.<GridFlow>getProperty(Property.GRID_FLOW));
    }

    @Test
    public void customIndentTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, " a ");
        cssProps.put(CssConstants.GRID_COLUMN_END, "c");
        cssProps.put(CssConstants.GRID_ROW_START, " a ");
        cssProps.put(CssConstants.GRID_ROW_END, "c");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px [ b  c  d  ] 10px [e f]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "[f] 10px [ e  d  c  ] 10px [b a]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void customIndentNthTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "2 a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "3 a");
        cssProps.put(CssConstants.GRID_ROW_START, "2 c");
        cssProps.put(CssConstants.GRID_ROW_END, "d");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px [b] 10px [a] 10px [a]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "[c] 10px [c] 10px [c]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void customIndentNegativeTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "-3 a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "-1 a");
        cssProps.put(CssConstants.GRID_ROW_START, "-3 c");
        cssProps.put(CssConstants.GRID_ROW_END, "-1 c");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px [b] 10px [a] 10px [a]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "[c] 10px [c] 10px [c]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    @LogMessages(messages = @LogMessage(
            messageTemplate = Html2PdfLogMessageConstant.ADDING_GRID_LINES_TO_THE_LEFT_OR_TOP_IS_NOT_SUPPORTED))
    public void customIndentOutOfBoundsTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "5 a");
        cssProps.put(CssConstants.GRID_ROW_START, "-5 c");
        cssProps.put(CssConstants.GRID_ROW_END, "-1 c");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px [b] 10px [a] 10px [a]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "[c] 10px [c] 10px [c]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        // Null for row start as we don't support negative starts
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(6, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void customIndentOutOfBounds2Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "5 a");
        cssProps.put(CssConstants.GRID_ROW_START, "c");
        cssProps.put(CssConstants.GRID_ROW_END, "2 c");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px 10px [a] 10px [b]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "10px 10px [c] 10px [a]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(5, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(7, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void customIndentGridAreaTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "c / a 2 / c -2 / 5 a");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px [b] 10px [a] 10px [a]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "[c] 10px [c] 10px [c]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(6, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void customIndentSpan1Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "span 2 a");
        cssProps.put(CssConstants.GRID_ROW_START, "span c");
        cssProps.put(CssConstants.GRID_ROW_END, "4");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px 10px [a] 10px [b]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "10px 10px [c] 10px [a]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(5, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void customIndentSpan2Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "2 a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "span 2 a");
        cssProps.put(CssConstants.GRID_ROW_START, "span 2 c");
        cssProps.put(CssConstants.GRID_ROW_END, "4");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] 10px 10px [a] 10px [b]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "10px 10px [c] 10px [a]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        // Null for row start as we don't support negative starts
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(6, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void customIndentSpan3Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, " 2  a-a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "span  2  a-a");
        cssProps.put(CssConstants.GRID_ROW_START, "span  2  c");
        cssProps.put(CssConstants.GRID_ROW_END, "4");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a-a] 10px 10px [a-a] 10px [b-a]");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "10px 10px [c] 10px [a]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        // Null for row start as we don't support negative starts
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(6, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void gridAreaLinenamesTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "a-start");
        cssProps.put(CssConstants.GRID_COLUMN_END, "span c-end 2");
        cssProps.put(CssConstants.GRID_ROW_START, "span a-start");
        cssProps.put(CssConstants.GRID_ROW_END, "c-end -1");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'a a a b b c c c' 'a a a b b c c c' 'a a a b b c c c'");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        // Null for row start as we don't support negative starts
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(4, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(10, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void lineNamesInRepeatTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "4 a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "b 4");
        cssProps.put(CssConstants.GRID_ROW_START, "span start");
        cssProps.put(CssConstants.GRID_ROW_END, "c 2");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "[a] repeat(3, [a st] 10px 10px [a] 10px [b])");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS, "[start] repeat(2, 10px 10px [c] 10px [a])");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(6, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(6, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(11, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void lineNamesInRepeat2Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "3 a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "span nd 3");
        cssProps.put(CssConstants.GRID_ROW_START, "span c");
        cssProps.put(CssConstants.GRID_ROW_END, "a 8");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS,
                "[a] 10px repeat( 2, 10px 10px [a] 10px [b]) repeat(3, [nd] auto)");
        parentStyles.put(CssConstants.GRID_TEMPLATE_ROWS,
                "[start] 10px repeat( 5, 10px 10px [c] 10px [a]) auto [a] auto [a]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(16, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(7, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(20, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(10, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    @LogMessages(messages = @LogMessage(
            messageTemplate = Html2PdfLogMessageConstant.LINENAMES_ARE_NOT_SUPPORTED_WITHIN_AUTO_REPEAT, count = 2))
    public void lineNamesInAutoRepeatTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "a");
        cssProps.put(CssConstants.GRID_COLUMN_END, "c");
        Div element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_COLUMNS, "repeat(auto-fill, [a] 10%) [b] repeat(auto-fit, 1fr) [c]");
        GridContainer grid = new GridContainer();
        grid.add(element);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        GridApplierUtil.applyGridContainerProperties(parentStyles, grid, new ProcessorContext(new ConverterProperties()));

        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    private IElementNode createStylesContainer() {
        Element element = new Element(com.itextpdf.styledxmlparser.jsoup.parser.Tag.valueOf("div"), "");
        Element parentElement = new Element(com.itextpdf.styledxmlparser.jsoup.parser.Tag.valueOf("div"), "");
        parentElement.appendChild(element);
        IElementNode node = new JsoupElementNode(element);
        IElementNode parentNode = new JsoupElementNode(parentElement);
        parentNode.addChild(node);
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.DISPLAY, CssConstants.GRID);
        parentNode.setStyles(styles);

        return node;
    }
}
