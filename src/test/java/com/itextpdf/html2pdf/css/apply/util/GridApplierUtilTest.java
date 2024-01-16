package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Tag("UnitTest")
public class GridApplierUtilTest {
    @Test
    public void applyColumnStartTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, "2");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_COLUMN_START);
        Assertions.assertEquals(2, columnStart);
    }

    @Test
    public void applyColumnEndTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_END, "4");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_COLUMN_END);
        Assertions.assertEquals(4, columnStart);
    }

    @Test
    public void applyRowStartTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_ROW_START, "3");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_ROW_START);
        Assertions.assertEquals(3, columnStart);
    }

    @Test
    public void applyRowEndTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_ROW_END, "11");
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_ROW_END);
        Assertions.assertEquals(11, columnStart);
    }

    @Test
    public void applyInvalidColumnStartTest() {
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.GRID_COLUMN_START, CssConstants.AUTO);
        IElement element = new Div();
        GridApplierUtil.applyGridItemProperties(cssProps, element);
        Integer columnStart = element.<Integer>getProperty(Property.GRID_COLUMN_START);
        Assertions.assertNull(columnStart);
    }
}
