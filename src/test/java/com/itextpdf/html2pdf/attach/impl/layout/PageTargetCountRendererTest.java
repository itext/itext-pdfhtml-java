package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class PageTargetCountRendererTest extends ExtendedITextTest {

    @Test
    public void getNextRendererTest() {
        PageTargetCountRenderer renderer = new PageTargetCountRenderer(new PageTargetCountElement("target"));
        Assert.assertEquals(renderer, renderer.getNextRenderer());
    }
}
