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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("UnitTest")
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
        PageSize expected = PageSize.LEDGER.rotate();
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

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.PAGE_SIZE_VALUE_IS_INVALID, count = 1),})
    public void incorrectPageSizeNameTest() {
        PageSize expected = PageSize.LEDGER;
        PageSize actual = PageSizeParser.fetchPageSize("INCORRECT_PAGE_SIZE", 10, 10, PageSize.LEDGER);
        assertSizesAreSame(expected, actual);
    }

    private void assertSizesAreSame(PageSize a, PageSize b) {
        Assertions.assertEquals(a.getWidth(), b.getWidth(), EPS);
        Assertions.assertEquals(a.getHeight(), b.getHeight(), EPS);
    }
}
