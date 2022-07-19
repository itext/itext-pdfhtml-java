package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class OutlineApplierUtilTest extends ExtendedITextTest {

    @Test
    public void grooveBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "groove", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

    @Test
    public void ridgeBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "ridge", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

    @Test
    public void insetBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "inset", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

    @Test
    public void outsetBorderColorTest() {
        Border border = OutlineApplierUtil.getCertainBorder("10px", "outset", "device-cmyk(0, 81%, 81%, 30%", 12.0f,
                12.0f);
        Color expected = new DeviceCmyk(0, 81, 81, 30);
        Assert.assertEquals(expected, border.getColor());
    }

}
