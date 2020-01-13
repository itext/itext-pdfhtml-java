package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.Leading;
import com.itextpdf.layout.property.Property;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class LineHeightTest {

    @Test
    public void defaultLineHeightTest() throws IOException {
        List<IElement> elements = HtmlConverter.convertToElements("<p>Lorem Ipsum</p>");
        Assert.assertEquals(1.2f, elements.get(0).<Leading>getProperty(Property.LEADING).getValue(), 1e-10);
    }
}
