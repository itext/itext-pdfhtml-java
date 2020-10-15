package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageTargetCountElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Category(UnitTest.class)
public class CssContentPropertyResolverTest extends ExtendedITextTest {

    @Test
    public void resolveContentTargetCounterEnabledTest() {
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.CONTENT, "target-counter(url('#some_target'), page)");
        CssContext context = new CssContext();
        context.setTargetCounterEnabled(true);
        List<INode> result = CssContentPropertyResolver.resolveContent(styles, null, context);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.get(0) instanceof PageTargetCountElementNode);
        Assert.assertEquals("#some_target", ((PageTargetCountElementNode) result.get(0)).getTarget());
    }

    @Test
    // TODO DEVSIX-2995 This code should be correctly parsed.
    public void resolveContentTargetCounterNotPageTest() {
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.CONTENT, "target-counter(url('#some_target'), some_counter)");
        CssContext context = new CssContext();
        context.setTargetCounterEnabled(true);
        List<INode> result = CssContentPropertyResolver.resolveContent(styles, null, context);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.CONTENT_PROPERTY_INVALID))
    public void resolveContentTargetCounterDisabledTest() {
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.CONTENT, "target-counter(url('#some_target'), some_counter)");
        CssContext context = new CssContext();
        context.setTargetCounterEnabled(false);
        List<INode> result = CssContentPropertyResolver.resolveContent(styles, null, context);
        Assert.assertNull(result);
    }
}
