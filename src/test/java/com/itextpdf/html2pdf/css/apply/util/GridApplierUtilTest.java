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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.GridValue;
import com.itextpdf.layout.properties.Property;
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
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, createStylesContainer(), element);
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
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_START));
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
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
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
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, CommonCssConstants.NONE);
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertNull(element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridTemplateAreas1Test() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, "somename1");
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "\"somename1 Somename1\" ' somename1     Somename1' ' somename1     Somename1'");
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
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
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "\"somename1 Somename1\" ' somename1     Somename1' ' somename1     Somename1'");
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
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
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "\"somename1 Somename1\" ' somename1     Somename1' ' somename1     Somename1'");
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
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
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'a b' 'b a'");
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
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
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'a b a' 'a b a'");
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_ROW_START));
        Assertions.assertEquals(1, element.<Integer>getProperty(Property.GRID_COLUMN_START));
        Assertions.assertEquals(3, element.<Integer>getProperty(Property.GRID_ROW_END));
        Assertions.assertEquals(2, element.<Integer>getProperty(Property.GRID_COLUMN_END));
    }

    @Test
    public void applyGridTemplateAreasWithDotsTest() {
        Map<String, String> cssProps = new LinkedHashMap<>();
        cssProps.put(CssConstants.GRID_AREA, ".");
        IElement element = new Div();
        IElementNode stylesContainer = createStylesContainer();
        Map<String, String> parentStyles = ((JsoupElementNode) stylesContainer.parentNode()).getStyles();
        parentStyles.put(CssConstants.GRID_TEMPLATE_AREAS, "'. . a' '. . a'");
        GridApplierUtil.applyGridItemProperties(cssProps, stylesContainer, element);
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
        Assertions.assertEquals(8.25, element.<GridValue>getProperty(Property.GRID_AUTO_COLUMNS).getAbsoluteValue(), 0.00001);
        Assertions.assertNull(element.<GridValue>getProperty(Property.GRID_AUTO_ROWS));
    }

    @Test
    public void containerTemplateValuesTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_TEMPLATE_COLUMNS, "min-content 1.5fr auto 2fr 100px 20%");
        cssProps.put(CssConstants.GRID_TEMPLATE_ROWS, "10px 20pt 3em 5rem");
        IElement element = new Div();
        GridApplierUtil.applyGridContainerProperties(cssProps, element, new ProcessorContext(new ConverterProperties()));
        List<GridValue> actualColValues = element.<List<GridValue>>getProperty(Property.GRID_TEMPLATE_COLUMNS);
        Assertions.assertEquals(1, actualColValues.size());
        Assertions.assertEquals(75, actualColValues.get(0).getAbsoluteValue());
        List<GridValue> actualRowValues = element.<List<GridValue>>getProperty(Property.GRID_TEMPLATE_ROWS);
        Assertions.assertEquals(4, actualRowValues.size());
        Assertions.assertEquals(7.5f, actualRowValues.get(0).getAbsoluteValue());
        Assertions.assertEquals(20, actualRowValues.get(1).getAbsoluteValue());
        Assertions.assertEquals(0, actualRowValues.get(2).getAbsoluteValue());
        Assertions.assertEquals(60, actualRowValues.get(3).getAbsoluteValue());
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
