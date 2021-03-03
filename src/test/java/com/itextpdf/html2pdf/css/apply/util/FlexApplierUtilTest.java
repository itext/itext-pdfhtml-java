/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.AlignmentPropertyValue;
import com.itextpdf.layout.property.JustifyContent;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class FlexApplierUtilTest extends ExtendedITextTest {

    private static final float EPS = 1e-6f;

    @Test
    public void applyFlexGrowTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_GROW, "20.568");
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Float flexGrow = element.<Float>getProperty(Property.FLEX_GROW);
        Assert.assertEquals(20.568f, (float) flexGrow, EPS);
    }

    @Test
    public void applyFlexShrinkTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_SHRINK, "182.1932");
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Float flexShrink = element.<Float>getProperty(Property.FLEX_SHRINK);
        Assert.assertEquals(182.1932f, (float) flexShrink, EPS);
    }

    @Test
    public void applyFlexBasisNullTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_BASIS, null);
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Assert.assertNull(element.<UnitValue>getProperty(Property.FLEX_BASIS));
    }

    @Test
    public void applyFlexBasisNullWidthTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_BASIS, null);
        cssProps.put(CssConstants.WIDTH, "20.45pt");
        cssProps.put(CssConstants.FONT_SIZE, "0");
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Assert.assertEquals(UnitValue.createPointValue(20.45f),
                element.<UnitValue>getProperty(Property.FLEX_BASIS));
    }

    @Test
    public void applyFlexBasisAutoTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_BASIS, CssConstants.AUTO);
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Assert.assertNull(element.<UnitValue>getProperty(Property.FLEX_BASIS));
    }

    @Test
    public void applyFlexBasisAutoWidthTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_BASIS, CssConstants.AUTO);
        cssProps.put(CssConstants.WIDTH, "20.45pt");
        cssProps.put(CssConstants.FONT_SIZE, "0");
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Assert.assertEquals(UnitValue.createPointValue(20.45f),
                element.<UnitValue>getProperty(Property.FLEX_BASIS));
    }

    @Test
    public void applyFlexBasisContentWidthTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_BASIS, CssConstants.CONTENT);
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Assert.assertFalse(element.hasProperty(Property.FLEX_BASIS));
        Assert.assertNull(element.<UnitValue>getProperty(Property.FLEX_BASIS));
    }

    @Test
    public void applyFlexBasisAbsoluteValueTest() {
        ProcessorContext context = new ProcessorContext(new ConverterProperties());
        Map<String, String> cssProps = new HashMap<>();
        cssProps.put(CssConstants.FLEX_BASIS, "20.45pt");
        cssProps.put(CssConstants.FONT_SIZE, "0");
        IElement element = new Div();
        FlexApplierUtil.applyFlexItemProperties(cssProps, context, element);
        Assert.assertEquals(UnitValue.createPointValue(20.45f),
                element.<UnitValue>getProperty(Property.FLEX_BASIS));
        Assert.assertEquals(UnitValue.createPointValue(20.45f), element.<UnitValue>getProperty(Property.FLEX_BASIS));
    }


    @Test
    public void applyAlignItemsTest() {
        String[] alignItemsStrings = {
                CssConstants.START,
                CssConstants.END,
                CssConstants.CENTER,
                CssConstants.FLEX_START,
                CssConstants.FLEX_END,
                CssConstants.SELF_START,
                CssConstants.SELF_END,
                CssConstants.BASELINE,
                CssConstants.STRETCH,
                CssConstants.NORMAL
        };
        AlignmentPropertyValue[] alignItemsValues = {
                AlignmentPropertyValue.START,
                AlignmentPropertyValue.END,
                AlignmentPropertyValue.CENTER,
                AlignmentPropertyValue.FLEX_START,
                AlignmentPropertyValue.FLEX_END,
                AlignmentPropertyValue.SELF_START,
                AlignmentPropertyValue.SELF_END,
                AlignmentPropertyValue.BASELINE,
                AlignmentPropertyValue.STRETCH,
                AlignmentPropertyValue.NORMAL
        };
        for (int i = 0; i < alignItemsStrings.length; ++i) {
            Map<String, String> cssProps = new HashMap<>();
            cssProps.put(CssConstants.ALIGN_ITEMS, alignItemsStrings[i]);
            IElement element = new Div();
            FlexApplierUtil.applyFlexContainerProperties(cssProps, element);
            Assert.assertEquals(alignItemsValues[i], (AlignmentPropertyValue) element.<AlignmentPropertyValue>getProperty(Property.ALIGN_ITEMS));
        }
    }

    @Test
    public void applyJustifyContentTest() {
        String[] justifyContentStrings = {
                CssConstants.START,
                CssConstants.END,
                CssConstants.CENTER,
                CssConstants.FLEX_START,
                CssConstants.FLEX_END,
                CssConstants.SELF_START,
                CssConstants.SELF_END,
                CssConstants.LEFT,
                CssConstants.RIGHT,
                CssConstants.NORMAL
        };
        JustifyContent[] justifyContentValues = {
                JustifyContent.START,
                JustifyContent.END,
                JustifyContent.CENTER,
                JustifyContent.FLEX_START,
                JustifyContent.FLEX_END,
                JustifyContent.SELF_START,
                JustifyContent.SELF_END,
                JustifyContent.LEFT,
                JustifyContent.RIGHT,
                JustifyContent.NORMAL
        };
        for (int i = 0; i < justifyContentStrings.length; ++i) {
            Map<String, String> cssProps = new HashMap<>();
            cssProps.put(CssConstants.JUSTIFY_CONTENT, justifyContentStrings[i]);
            IElement element = new Div();
            FlexApplierUtil.applyFlexContainerProperties(cssProps, element);
            Assert.assertEquals(justifyContentValues[i], (JustifyContent) element.<JustifyContent>getProperty(Property.JUSTIFY_CONTENT));
        }
    }
}
