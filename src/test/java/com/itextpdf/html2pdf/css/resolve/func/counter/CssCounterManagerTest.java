package com.itextpdf.html2pdf.css.resolve.func.counter;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
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
        Assert.assertEquals("1", manager.resolveCounters("counter1", ",", null));
        Assert.assertEquals("2", manager.resolveCounters("counter2", ",", null));

        IElementNode node1 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node1);
        manager.resetCounter("counter1", 3);
        manager.resetCounter("counter2", 4);
        Assert.assertEquals("1,3", manager.resolveCounters("counter1", ",", null));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", null));

        IElementNode node2 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node2);
        manager.resetCounter("counter1", 5);
        Assert.assertEquals("1,3,5", manager.resolveCounters("counter1", ",", null));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", null));

        IElementNode node3 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node3);
        Assert.assertEquals("1,3,5", manager.resolveCounters("counter1", ",", null));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", null));

        manager.popEveryCounterFromCounters(node3);
        Assert.assertEquals("1,3,5", manager.resolveCounters("counter1", ",", null));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", null));

        manager.popEveryCounterFromCounters(node2);
        Assert.assertEquals("1,3", manager.resolveCounters("counter1", ",", null));
        Assert.assertEquals("2,4", manager.resolveCounters("counter2", ",", null));

        manager.popEveryCounterFromCounters(node1);
        Assert.assertEquals("1", manager.resolveCounters("counter1", ",", null));
        Assert.assertEquals("2", manager.resolveCounters("counter2", ",", null));

        manager.popEveryCounterFromCounters(node1);
    }

    @Test
    public void resolveTargetCounterTest() {
        CssCounterManager manager = new CssCounterManager();

        manager.resetCounter("counter");
        manager.incrementCounter("counter", 5);

        Assert.assertNull(manager.resolveTargetCounter("id", "counter", null));
        Assert.assertNull(manager.resolveTargetCounter("id", "counter", null));

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

        Assert.assertEquals("5", manager.resolveTargetCounter("id", "counter", null));
        Assert.assertNull(manager.resolveTargetCounter("id", "counter2", null));

        manager.incrementCounter("counter2", 10);
        manager.addTargetCounterIfRequired(node);

        Assert.assertEquals("10", manager.resolveTargetCounter("id", "counter2", null));
    }

    @Test
    public void resolveTargetCountersTest() {
        CssCounterManager manager = new CssCounterManager();

        manager.resetCounter("counter");
        manager.incrementCounter("counter", 5);

        Assert.assertNull(manager.resolveTargetCounters("id", "counter", ".", null));
        Assert.assertNull(manager.resolveTargetCounters("id", "counter", ".", null));

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

        Assert.assertEquals("5", manager.resolveTargetCounters("id", "counter", ".", null));
        Assert.assertNull(manager.resolveTargetCounters("id", "counter2", ".", null));

        manager.incrementCounter("counter2", 10);
        manager.addTargetCountersIfRequired(node);

        Assert.assertEquals("10", manager.resolveTargetCounters("id", "counter2", ".", null));

        manager.pushEveryCounterToCounters(node);
        manager.resetCounter("counter2", 7);
        manager.addTargetCountersIfRequired(node);

        Assert.assertEquals("5", manager.resolveTargetCounters("id", "counter", ".", null));
        Assert.assertEquals("10.7", manager.resolveTargetCounters("id", "counter2", ".", null));
    }

    @Test
    public void addTargetCounterIfRequiredTest() {
        CssCounterManager manager = new CssCounterManager();

        Assert.assertNull(manager.resolveTargetCounter("id1", "counter", null));

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

        Assert.assertEquals("0", manager.resolveTargetCounter("id1", "counter", null));
        Assert.assertNull(manager.resolveTargetCounter("id2", "counter", null));

        manager.resetCounter("counter");
        manager.incrementCounter("counter");
        manager.addTargetCounterIfRequired(node1);

        Assert.assertEquals("1", manager.resolveTargetCounter("id1", "counter", null));
    }

    @Test
    public void resolveCounterTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter1", 1);

        Assert.assertEquals("1", manager.resolveCounter("counter1", null));
        Assert.assertEquals("0", manager.resolveCounter("counter2", null));

        IElementNode node = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node);

        manager.resetCounter("counter2", 1);
        manager.incrementCounter("counter1", 2);

        Assert.assertEquals("3", manager.resolveCounter("counter1", null));
        Assert.assertEquals("1", manager.resolveCounter("counter2", null));
    }

    @Test
    public void resolveCountersTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter1", 1);

        Assert.assertEquals("1", manager.resolveCounters("counter1", ";", null));
        Assert.assertEquals("0", manager.resolveCounters("counter2", "::", null));

        IElementNode node1 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node1);
        manager.resetCounter("counter2", 1);
        manager.incrementCounter("counter1", 2);

        Assert.assertEquals("3", manager.resolveCounters("counter1", ";", null));
        Assert.assertEquals("1", manager.resolveCounters("counter2", "::", null));

        IElementNode node2 = new CssPseudoElementNode(null, "name");
        manager.pushEveryCounterToCounters(node2);
        manager.resetCounter("counter1", 2);
        manager.resetCounter("counter1", 30);
        manager.resetCounter("counter2", 10);

        Assert.assertEquals("3;30", manager.resolveCounters("counter1", ";", null));
        Assert.assertEquals("1::10", manager.resolveCounters("counter2", "::", null));
    }

    @Test
    public void resetCounterTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter");
        Assert.assertEquals("0", manager.resolveCounter("counter", null));

        manager.resetCounter("counter", 101);
        Assert.assertEquals("101", manager.resolveCounter("counter", null));

        manager.resetCounter("counter", -5);
        Assert.assertEquals("-5", manager.resolveCounter("counter", null));
    }

    @Test
    public void incrementCounterTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter");
        manager.incrementCounter("counter");
        Assert.assertEquals("1", manager.resolveCounter("counter", null));

        manager.incrementCounter("counter", 101);
        Assert.assertEquals("102", manager.resolveCounter("counter", null));

        manager.incrementCounter("counter", -3);
        Assert.assertEquals("99", manager.resolveCounter("counter", null));

        manager.incrementCounter("counter", -101);
        Assert.assertEquals("-2", manager.resolveCounter("counter", null));
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
        Assert.assertEquals("III", manager.resolveCounter("counter", CssConstants.UPPER_ROMAN));
        manager.resetCounter("counter", 2);
        Assert.assertEquals("III.II", manager.resolveCounters("counter", ".", CssConstants.UPPER_ROMAN));

        manager.resolveTargetCounter("id", "counter", CssConstants.UPPER_ROMAN);
        manager.resolveTargetCounters("id", "counter", ".", CssConstants.UPPER_ROMAN);

        manager.addTargetCounterIfRequired(node);
        manager.addTargetCountersIfRequired(node);

        Assert.assertEquals("II", manager.resolveTargetCounter("id", "counter", CssConstants.UPPER_ROMAN));
        Assert.assertEquals("III.II", manager.resolveTargetCounters("id", "counter", ".", CssConstants.UPPER_ROMAN));
    }

    @Test
    public void convertCounterToSymbolTest() {
        CssCounterManager manager = new CssCounterManager();
        manager.resetCounter("counter", 3);
        Assert.assertEquals("3", manager.resolveCounter("counter", null));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.NONE));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", CssConstants.DISC));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", CssConstants.SQUARE));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", CssConstants.CIRCLE));
        Assert.assertEquals("C", manager.resolveCounter("counter", CssConstants.UPPER_ALPHA));
        Assert.assertEquals("C", manager.resolveCounter("counter", CssConstants.UPPER_LATIN));
        Assert.assertEquals("c", manager.resolveCounter("counter", CssConstants.LOWER_ALPHA));
        Assert.assertEquals("c", manager.resolveCounter("counter", CssConstants.LOWER_LATIN));
        Assert.assertEquals("\u03b3", manager.resolveCounter("counter", CssConstants.LOWER_GREEK));
        Assert.assertEquals("iii", manager.resolveCounter("counter", CssConstants.LOWER_ROMAN));
        Assert.assertEquals("III", manager.resolveCounter("counter", CssConstants.UPPER_ROMAN));
        Assert.assertEquals("03", manager.resolveCounter("counter", CssConstants.DECIMAL_LEADING_ZERO));
        Assert.assertEquals("\u10D2", manager.resolveCounter("counter", CssConstants.GEORGIAN));
        Assert.assertEquals("\u0533", manager.resolveCounter("counter", CssConstants.ARMENIAN));
        Assert.assertEquals("3", manager.resolveCounter("counter", "some_script"));

        manager.resetCounter("counter", 0);
        Assert.assertEquals("0", manager.resolveCounter("counter", null));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.NONE));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", CssConstants.DISC));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", CssConstants.SQUARE));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", CssConstants.CIRCLE));
        Assert.assertEquals("0", manager.resolveCounter("counter", CssConstants.UPPER_ALPHA));
        Assert.assertEquals("0", manager.resolveCounter("counter", CssConstants.UPPER_LATIN));
        Assert.assertEquals("0", manager.resolveCounter("counter", CssConstants.LOWER_ALPHA));
        Assert.assertEquals("0", manager.resolveCounter("counter", CssConstants.LOWER_LATIN));
        Assert.assertEquals("0", manager.resolveCounter("counter", CssConstants.LOWER_GREEK));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.LOWER_ROMAN));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.UPPER_ROMAN));
        Assert.assertEquals("00", manager.resolveCounter("counter", CssConstants.DECIMAL_LEADING_ZERO));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.GEORGIAN));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.ARMENIAN));
        Assert.assertEquals("0", manager.resolveCounter("counter", "some_script"));

        manager.resetCounter("counter", -3);
        Assert.assertEquals("-3", manager.resolveCounter("counter", null));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.NONE));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", CssConstants.DISC));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", CssConstants.SQUARE));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", CssConstants.CIRCLE));
        Assert.assertEquals("-3", manager.resolveCounter("counter", CssConstants.UPPER_ALPHA));
        Assert.assertEquals("-3", manager.resolveCounter("counter", CssConstants.UPPER_LATIN));
        Assert.assertEquals("-3", manager.resolveCounter("counter", CssConstants.LOWER_ALPHA));
        Assert.assertEquals("-3", manager.resolveCounter("counter", CssConstants.LOWER_LATIN));
        Assert.assertEquals("-3", manager.resolveCounter("counter", CssConstants.LOWER_GREEK));
        Assert.assertEquals("-iii", manager.resolveCounter("counter", CssConstants.LOWER_ROMAN));
        Assert.assertEquals("-III", manager.resolveCounter("counter", CssConstants.UPPER_ROMAN));
        Assert.assertEquals("0-3", manager.resolveCounter("counter", CssConstants.DECIMAL_LEADING_ZERO));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.GEORGIAN));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.ARMENIAN));
        Assert.assertEquals("-3", manager.resolveCounter("counter", "some_script"));

        manager.resetCounter("counter", 5000);
        Assert.assertEquals("5000", manager.resolveCounter("counter", null));
        Assert.assertEquals("", manager.resolveCounter("counter", CssConstants.NONE));
        Assert.assertEquals("\u2022", manager.resolveCounter("counter", CssConstants.DISC));
        Assert.assertEquals("\u25a0", manager.resolveCounter("counter", CssConstants.SQUARE));
        Assert.assertEquals("\u25e6", manager.resolveCounter("counter", CssConstants.CIRCLE));
        Assert.assertEquals("GJH", manager.resolveCounter("counter", CssConstants.UPPER_ALPHA));
        Assert.assertEquals("GJH", manager.resolveCounter("counter", CssConstants.UPPER_LATIN));
        Assert.assertEquals("gjh", manager.resolveCounter("counter", CssConstants.LOWER_ALPHA));
        Assert.assertEquals("gjh", manager.resolveCounter("counter", CssConstants.LOWER_LATIN));
        Assert.assertEquals("\u03b8\u03c0\u03b8", manager.resolveCounter("counter", CssConstants.LOWER_GREEK));
        Assert.assertEquals("5000", manager.resolveCounter("counter", CssConstants.LOWER_ROMAN));
        Assert.assertEquals("5000", manager.resolveCounter("counter", CssConstants.UPPER_ROMAN));
        Assert.assertEquals("5000", manager.resolveCounter("counter", CssConstants.DECIMAL_LEADING_ZERO));
        Assert.assertEquals("\u10ed", manager.resolveCounter("counter", CssConstants.GEORGIAN));
        Assert.assertEquals("\u0550", manager.resolveCounter("counter", CssConstants.ARMENIAN));
        Assert.assertEquals("5000", manager.resolveCounter("counter", "some_script"));
    }
}
