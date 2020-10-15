package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class PageTargetCountElementTest extends ExtendedITextTest {

    @Test
    public void constructorTest() {
        PageTargetCountElement element = new PageTargetCountElement("'aadad''''adad#####'''aaa");
        Assert.assertEquals("aadadadadaaa", element.getTarget());
    }

    @Test
    public void makeNewRendererTest() {
        PageTargetCountElement element = new PageTargetCountElement("'#target'");
        IRenderer renderer = element.getRenderer();
        Assert.assertTrue(renderer instanceof PageTargetCountRenderer);
    }
}
