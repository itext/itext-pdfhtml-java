package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.PageCountElement;
import com.itextpdf.html2pdf.attach.impl.layout.PageCountType;
import com.itextpdf.html2pdf.attach.impl.layout.PageTargetCountElement;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageCountElementNode;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageTargetCountElementNode;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class PageCountWorkerTest extends ExtendedITextTest {

    @Test
    public void pageTargetCountElementNodeTest() {
        final String target = "target";
        PageCountWorker worker = new PageCountWorker(new PageTargetCountElementNode(null, target), null);
        IPropertyContainer container = worker.getElementResult();
        Assert.assertTrue(container instanceof PageTargetCountElement);
        Assert.assertEquals(target, ((PageTargetCountElement) container).getTarget());
    }

    @Test
    public void pageCountElementNodeTest() {
        PageCountWorker worker = new PageCountWorker(new PageCountElementNode(false, null), null);
        IPropertyContainer container = worker.getElementResult();
        Assert.assertTrue(container instanceof PageCountElement);
        Assert.assertEquals(PageCountType.CURRENT_PAGE_NUMBER, container.<PageCountType>getProperty(Html2PdfProperty.PAGE_COUNT_TYPE));
    }

    @Test
    public void pagesCountElementNodeTest() {
        PageCountWorker worker = new PageCountWorker(new PageCountElementNode(true, null), null);
        IPropertyContainer container = worker.getElementResult();
        Assert.assertTrue(container instanceof PageCountElement);
        Assert.assertEquals(PageCountType.TOTAL_PAGE_COUNT, container.<PageCountType>getProperty(Html2PdfProperty.PAGE_COUNT_TYPE));
    }
}
