/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.css.selector.CssSelector;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class SpecificityCalculationTest extends ExtendedITextTest {

    // https://www.smashingmagazine.com/2007/07/css-specificity-things-you-should-know/
    // https://specificity.keegan.st/

    @Test
    public void test01() {
        Assert.assertEquals(0, getSpecificity("*"));
    }

    @Test
    public void test02() {
        Assert.assertEquals(1, getSpecificity("li"));
    }

    @Test
    public void test03() {
        Assert.assertEquals(2, getSpecificity("li:first-line"));
    }

    @Test
    public void test04() {
        Assert.assertEquals(2, getSpecificity("ul li"));
    }

    @Test
    public void test05() {
        Assert.assertEquals(3, getSpecificity("ul ol+li"));
    }

    @Test
    public void test06() {
        Assert.assertEquals(11, getSpecificity("h1 + *[rel=up]"));
    }

    @Test
    public void test07() {
        Assert.assertEquals(13, getSpecificity("ul ol li.red"));
    }

    @Test
    public void test08() {
        Assert.assertEquals(21, getSpecificity("li.red.level"));
    }

    @Test
    public void test09() {
        Assert.assertEquals(10, getSpecificity(".sith"));
    }

    @Test
    public void test10() {
        Assert.assertEquals(12, getSpecificity("div p.sith"));
    }

    @Test
    public void test11() {
        Assert.assertEquals(100, getSpecificity("#sith"));
    }

    @Test
    public void test12() {
        Assert.assertEquals(112, getSpecificity("body #darkside .sith p"));
    }

    @Test
    public void test13() {
        Assert.assertEquals(22, getSpecificity("li:first-child h2 .title"));
    }

    @Test
    public void test14() {
        Assert.assertEquals(121, getSpecificity("#nav .selected > a:hover"));
    }

    @Test
    public void test15() {
        Assert.assertEquals(2, getSpecificity("p:before"));
        Assert.assertEquals(2, getSpecificity("p::before"));
    }

    @Test
    public void test16() {
        Assert.assertEquals(2, getSpecificity("a::hover"));
    }

    private int getSpecificity(String selector) {
        return new CssSelector(selector).calculateSpecificity();
    }

}
