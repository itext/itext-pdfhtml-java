package org.jsoup.nodes;

import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class AttributeTest {
    @Test public void html() {
        Attribute attr = new Attribute("key", "value &");
        assertEquals("key=\"value &amp;\"", attr.html());
        assertEquals(attr.html(), attr.toString());
    }

    @Test public void testWithSupplementaryCharacterInAttributeKeyAndValue() {
        String s = new String(Character.toChars(135361));
        Attribute attr = new Attribute(s, "A" + s + "B");
        assertEquals(s + "=\"A" + s + "B\"", attr.html());
        assertEquals(attr.html(), attr.toString());
    }
}
