package org.jsoup.nodes;

import com.itextpdf.test.annotations.type.UnitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Tests for Attributes.
 *
 * @author Jonathan Hedley
 */
@Category(UnitTest.class)
public class AttributesTest {
    @Test public void html() {
        Attributes a = new Attributes();
        a.put("Tot", "a&p");
        a.put("Hello", "There");
        a.put("data-name", "Jsoup");

        assertEquals(3, a.size());
        assertTrue(a.hasKey("tot"));
        assertTrue(a.hasKey("Hello"));
        assertTrue(a.hasKey("data-name"));
        assertEquals(1, a.dataset().size());
        assertEquals("Jsoup", a.dataset().get("name"));
        assertEquals("a&p", a.get("tot"));

        assertEquals(" tot=\"a&amp;p\" hello=\"There\" data-name=\"Jsoup\"", a.html());
        assertEquals(a.html(), a.toString());
    }

}
