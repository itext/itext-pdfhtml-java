package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.renderer.FlexContainerRenderer;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FloatAndFlexTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FloatAndFlexTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FloatAndFlexTest/";

    @Test
    //TODO DEVSIX-5087 remove this test when working on the ticket
    public void floatAtFlexContainerTest() throws IOException {
        String name = "floatAtFlexContainer";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertFalse(flexContainer.hasProperty(Property.FLOAT));
        Assert.assertFalse(flexContainer.hasProperty(Property.CLEAR));
    }

    @Test
    public void clearAtFlexItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clearAtFlexItem", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexContainerHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexContainerHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void floatAtFlexItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatAtFlexItem", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void floatAtFlexItemNestedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatAtFlexItemNested", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
