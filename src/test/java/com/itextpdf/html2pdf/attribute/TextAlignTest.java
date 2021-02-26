package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class TextAlignTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute/TextAlignTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/TextAlignTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void textAlignLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignRight", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignJustifyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignJustify", sourceFolder, destinationFolder);
    }
}
