package org.jsoup.parser;

import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 Tag tests.
 @author Jonathan Hedley, jonathan@hedley.net */
@Category(UnitTest.class)
public class TagExceptionTest {
    @Test(expected = IllegalArgumentException.class) public void valueOfChecksNotNull() {
        Tag.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class) public void valueOfChecksNotEmpty() {
        Tag.valueOf(" ");
    }
}
