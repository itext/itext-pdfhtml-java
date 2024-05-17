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
