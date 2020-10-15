package com.itextpdf.html2pdf.css.resolve.func.counter;

import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class PageTargetCountElementNodeTest extends ExtendedITextTest {

    @Test
    public void constructorTest() {
        String target = "tarGet";
        PageTargetCountElementNode node = new PageTargetCountElementNode(null, target);
        Assert.assertEquals("_e0d00a6_page-counter", node.name());
        Assert.assertEquals(target, node.getTarget());
        Assert.assertNull(node.parentNode());
    }
}
