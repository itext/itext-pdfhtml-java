package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class PageSizeParserTest extends ExtendedITextTest {

    private static final double EPS = 1e-9;

    @Test
    public void simpleA4Test() {
        PageSize expected = PageSize.A4;
        PageSize actual = PageSizeParser.fetchPageSize("a4", 10, 10, PageSize.A0);
        assertSizesAreSame(expected, actual);
    }

    @Test
    public void simpleLedgerTest() {
        PageSize expected = PageSize.LEDGER;
        PageSize actual = PageSizeParser.fetchPageSize("ledger", 10, 10, PageSize.A0);
        assertSizesAreSame(expected, actual);
    }

    @Test
    public void ledgerLandscapeIsSameAsLedgerTest() {
        PageSize expected = PageSize.LEDGER;
        PageSize actual = PageSizeParser.fetchPageSize("ledger landscape", 10, 10, PageSize.A0);
        assertSizesAreSame(expected, actual);
    }

    @Test
    public void ledgerPortraitIsRotatedLedgerTest() {
        PageSize expected = PageSize.LEDGER.rotate();
        PageSize actual = PageSizeParser.fetchPageSize("ledger portrait", 10, 10, PageSize.A0);
        assertSizesAreSame(expected, actual);
    }

    private void assertSizesAreSame(PageSize a, PageSize b) {
        Assert.assertEquals(a.getWidth(), b.getWidth(), EPS);
        Assert.assertEquals(a.getHeight(), b.getHeight(), EPS);
    }

}
