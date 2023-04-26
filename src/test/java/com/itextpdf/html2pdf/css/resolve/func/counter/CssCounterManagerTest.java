/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.css.resolve.func.counter;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.HtmlUtils;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Category(UnitTest.class)
public class CssCounterManagerTest extends ExtendedITextTest {

    @Test
    public void pushPopEveryCounterToCountersTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter1", 1);
        manager.resetCounter("counter2", 2);
        Assert.assertEquals("1", manager.resolveCounters("counter1", ",", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("2", manager.resolveCounters("counter2", ",", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node1 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node1);
        manager.resetCounter("counter1", 3);
        manager.resetCounter("counter2", 4);
        Assert.assertEquals("1,3", manager.resolveCounters("counter1", ",", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node2 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node2);
        manager.resetCounter("counter1", 5);
        Assert.assertEquals("1,3,5", manager.resolveCounters("counter1", ",", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node3 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node3);
        Assert.assertEquals("1,3,5", manager.resolveCounters("counter1", ",", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", CounterDigitsGlyphStyle.DEFAULT));

        manager.popEveryCounterFromCounters(node3);
        Assert.assertEquals("1,3,5", manager.resolveCounters("counter1", ",", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", CounterDigitsGlyphStyle.DEFAULT));

        manager.popEveryCounterFromCounters(node2);
        Assert.assertEquals("1,3", manager.resolveCounters("counter1", ",", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", CounterDigitsGlyphStyle.DEFAULT));

        manager.popEveryCounterFromCounters(node1);
        Assert.assertEquals("1", manager.resolveCounters("counter1", ",", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("2", manager.resolveCounters("counter2", ",", CounterDigitsGlyphStyle.DEFAULT));

        manager.popEveryCounterFromCounters(node1);
    }

    @Test
    public void resolveTargetCounterTest() {
        CssCounterManager manager = new CssCounterManager();

        manager.resetCounter("counter");
        manager.incrementCounter("counter", 5);

        Assert.assertNull(manager.resolveTargetCounter("id", "counter", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertNull(manager.resolveTargetCounter("id", "counter", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node = new CssPseudoElementNode(null, "name") {
            @Override
            public String getAttribute(String key) {
                if (AttributeConstants.ID.equals(key)) {
                    return "id";
                }
                return null;
            }
        };

        manager.addTargetCounterIfRequired(node);

        Assert.assertEquals("5", manager.resolveTargetCounter("id", "counter", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertNull(manager.resolveTargetCounter("id", "counter2", CounterDigitsGlyphStyle.DEFAULT));

        manager.incrementCounter("counter2", 10);
        manager.addTargetCounterIfRequired(node);

        Assert.assertEquals("10", manager.resolveTargetCounter("id", "counter2", CounterDigitsGlyphStyle.DEFAULT));
    }

    @Test
    public void resolveTargetCountersTest() {
        CssCounterManager manager = new CssCounterManager();

        manager.resetCounter("counter");
        manager.incrementCounter("counter", 5);

        Assert.assertNull(manager.resolveTargetCounters("id", "counter", ".", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertNull(manager.resolveTargetCounters("id", "counter", ".", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node = new CssPseudoElementNode(null, "name") {
            @Override
            public String getAttribute(String key) {
                if (AttributeConstants.ID.equals(key)) {
                    return "id";
                }
                return null;
            }
        };

        manager.addTargetCountersIfRequired(node);

        Assert.assertEquals("5", manager.resolveTargetCounters("id", "counter", ".", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertNull(manager.resolveTargetCounters("id", "counter2", ".", CounterDigitsGlyphStyle.DEFAULT));

        manager.incrementCounter("counter2", 10);
        manager.addTargetCountersIfRequired(node);

        Assert.assertEquals("10", manager.resolveTargetCounters("id", "counter2", ".", CounterDigitsGlyphStyle.DEFAULT));

        manager.pushEveryCounterToCounters(node);
        manager.resetCounter("counter2", 7);
        manager.addTargetCountersIfRequired(node);

        Assert.assertEquals("5", manager.resolveTargetCounters("id", "counter", ".", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("10.7", manager.resolveTargetCounters("id", "counter2", ".", CounterDigitsGlyphStyle.DEFAULT));
    }

    @Test
    public void addTargetCounterIfRequiredTest() {
        CssCounterManager manager = new CssCounterManager();

        Assert.assertNull(manager.resolveTargetCounter("id1", "counter", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node1 = new CssPseudoElementNode(null, "name") {
            @Override
            public String getAttribute(String key) {
                if (AttributeConstants.ID.equals(key)) {
                    return "id1";
                }
                return null;
            }
        };
        IElementNode node2 = new CssPseudoElementNode(null, "name") {
            @Override
            public String getAttribute(String key) {
                if (AttributeConstants.ID.equals(key)) {
                    return "id2";
                }
                return null;
            }
        };
        IElementNode node3 = new CssPseudoElementNode(null, "name");

        manager.addTargetCounterIfRequired(node1);
        manager.addTargetCounterIfRequired(node2);
        manager.addTargetCounterIfRequired(node3);

        Assert.assertEquals("0", manager.resolveTargetCounter("id1", "counter", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertNull(manager.resolveTargetCounter("id2", "counter", CounterDigitsGlyphStyle.DEFAULT));

        manager.resetCounter("counter");
        manager.incrementCounter("counter");
        manager.addTargetCounterIfRequired(node1);

        Assert.assertEquals("1", manager.resolveTargetCounter("id1", "counter", CounterDigitsGlyphStyle.DEFAULT));
    }

    @Test
    public void resolveCounterTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter1", 1);

        Assert.assertEquals("1", manager.resolveCounter("counter1", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("0", manager.resolveCounter("counter2", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node);

        manager.resetCounter("counter2", 1);
        manager.incrementCounter("counter1", 2);

        Assert.assertEquals("3", manager.resolveCounter("counter1", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("1", manager.resolveCounter("counter2", CounterDigitsGlyphStyle.DEFAULT));
    }

    @Test
    public void resolveCountersTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter1", 1);

        Assert.assertEquals("1", manager.resolveCounters("counter1", ";", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("0", manager.resolveCounters("counter2", "::", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node1 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node1);
        manager.resetCounter("counter2", 1);
        manager.incrementCounter("counter1", 2);

        Assert.assertEquals("3", manager.resolveCounters("counter1", ";", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("1", manager.resolveCounters("counter2", "::", CounterDigitsGlyphStyle.DEFAULT));

        IElementNode node2 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node2);
        manager.resetCounter("counter1", 2);
        manager.resetCounter("counter1", 30);
        manager.resetCounter("counter2", 10);

        Assert.assertEquals("3;30", manager.resolveCounters("counter1", ";", CounterDigitsGlyphStyle.DEFAULT));
        Assert.assertEquals("1::10", manager.resolveCounters("counter2", "::", CounterDigitsGlyphStyle.DEFAULT));
    }

    @Test
    public void resetCounterTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter");
        Assert.assertEquals("0", manager.resolveCounter("counter", CounterDigitsGlyphStyle.DEFAULT));

        manager.resetCounter("counter", 101);
        Assert.assertEquals("101", manager.resolveCounter("counter", CounterDigitsGlyphStyle.DEFAULT));

        manager.resetCounter("counter", -5);
        Assert.assertEquals("-5", manager.resolveCounter("counter", CounterDigitsGlyphStyle.DEFAULT));
    }

    @Test
    public void incrementCounterTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter");
        manager.incrementCounter("counter");
        Assert.assertEquals("1", manager.resolveCounter("counter", CounterDigitsGlyphStyle.DEFAULT));

        manager.incrementCounter("counter", 101);
        Assert.assertEquals("102", manager.resolveCounter("counter", CounterDigitsGlyphStyle.DEFAULT));

        manager.incrementCounter("counter", -3);
        Assert.assertEquals("99", manager.resolveCounter("counter", CounterDigitsGlyphStyle.DEFAULT));

        manager.incrementCounter("counter", -101);
        Assert.assertEquals("-2", manager.resolveCounter("counter", CounterDigitsGlyphStyle.DEFAULT));
    }

    @Test
    public void resolveEveryCounterWithNotDefaultSymbolsTest() {
        CssCounterManager manager = new CssCounterManager();
        IElementNode node = new CssPseudoElementNode(null, "name") {
            @Override
            public String getAttribute(String key) {
                if (AttributeConstants.ID.equals(key)) {
                    return "id";
                }
                return null;
            }
        };
        manager.resetCounter("counter", 3);
        manager.pushEveryCounterToCounters(node);
        Assert.assertEquals("III", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));
        manager.resetCounter("counter", 2);
        Assert.assertEquals("III.II", manager.resolveCounters("counter", ".",
                HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));

        manager.resolveTargetCounter("id", "counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN));
        manager.resolveTargetCounters("id", "counter", ".", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN));

        manager.addTargetCounterIfRequired(node);
        manager.addTargetCountersIfRequired(node);

        Assert.assertEquals("II", manager.resolveTargetCounter("id", "counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));
        Assert.assertEquals("III.II", manager.resolveTargetCounters("id", "counter", ".",
                HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));
    }

    @Test
    public void convertCounterToSymbolTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter", 3);
        Assert.assertEquals("3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(null)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.NONE)));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DISC)));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.SQUARE)));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.CIRCLE)));
        Assert.assertEquals("C", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ALPHA)));
        Assert.assertEquals("C", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_LATIN)));
        Assert.assertEquals("c", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ALPHA)));
        Assert.assertEquals("c", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_LATIN)));
        Assert.assertEquals("\u03b3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_GREEK)));
        Assert.assertEquals("iii", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ROMAN)));
        Assert.assertEquals("III", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));
        Assert.assertEquals("03", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DECIMAL_LEADING_ZERO)));
        Assert.assertEquals("\u10D2", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.GEORGIAN)));
        Assert.assertEquals("\u0533", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.ARMENIAN)));
        Assert.assertEquals("3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum("some_script")));

        manager.resetCounter("counter", 0);
        Assert.assertEquals("0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(null)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.NONE)));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DISC)));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.SQUARE)));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.CIRCLE)));
        Assert.assertEquals("0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ALPHA)));
        Assert.assertEquals("0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_LATIN)));
        Assert.assertEquals("0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ALPHA)));
        Assert.assertEquals("0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_LATIN)));
        Assert.assertEquals("0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_GREEK)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ROMAN)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));
        Assert.assertEquals("00", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DECIMAL_LEADING_ZERO)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.GEORGIAN)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.ARMENIAN)));
        Assert.assertEquals("0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum("some_script")));

        manager.resetCounter("counter", -3);
        Assert.assertEquals("-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(null)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.NONE)));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DISC)));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.SQUARE)));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.CIRCLE)));
        Assert.assertEquals("-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ALPHA)));
        Assert.assertEquals("-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_LATIN)));
        Assert.assertEquals("-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ALPHA)));
        Assert.assertEquals("-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_LATIN)));
        Assert.assertEquals("-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_GREEK)));
        Assert.assertEquals("-iii", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ROMAN)));
        Assert.assertEquals("-III", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));
        Assert.assertEquals("0-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DECIMAL_LEADING_ZERO)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.GEORGIAN)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.ARMENIAN)));
        Assert.assertEquals("-3", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum("some_script")));

        manager.resetCounter("counter", 5000);
        Assert.assertEquals("5000", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(null)));
        Assert.assertEquals("", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.NONE)));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DISC)));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.SQUARE)));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.CIRCLE)));
        Assert.assertEquals("GJH", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ALPHA)));
        Assert.assertEquals("GJH", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_LATIN)));
        Assert.assertEquals("gjh", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ALPHA)));
        Assert.assertEquals("gjh", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_LATIN)));
        Assert.assertEquals("\u03b8\u03c0\u03b8", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_GREEK)));
        Assert.assertEquals("5000", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.LOWER_ROMAN)));
        Assert.assertEquals("5000", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.UPPER_ROMAN)));
        Assert.assertEquals("5000", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.DECIMAL_LEADING_ZERO)));
        Assert.assertEquals("\u10ed", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.GEORGIAN)));
        Assert.assertEquals("\u0550", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum(CssConstants.ARMENIAN)));
        Assert.assertEquals("5000", manager.resolveCounter("counter", HtmlUtils.convertStringCounterGlyphStyleToEnum("some_script")));
    }
}
