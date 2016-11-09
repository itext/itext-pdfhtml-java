package org.jsoup.nodes;

import com.itextpdf.test.annotations.type.UnitTest;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Tests for Element (DOM stuff mostly).
 *
 * @author Jonathan Hedley
 */
@Category(UnitTest.class)
public class ElementExceptionTest {
    @Test(expected = IllegalArgumentException.class) public void testThrowsOnAddNullText() {
        Document doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        Element div = doc.getElementById("1");
        div.appendText(null);
    }

    @Test(expected = IllegalArgumentException.class)  public void testThrowsOnPrependNullText() {
        Document doc = Jsoup.parse("<div id=1><p>Hello</p></div>");
        Element div = doc.getElementById("1");
        div.prependText(null);
    }
}
