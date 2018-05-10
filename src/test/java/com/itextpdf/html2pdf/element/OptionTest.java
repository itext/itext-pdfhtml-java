package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OptionTest extends ExtendedHtmlConversionITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/OptionTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/OptionTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void optionBasicTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionBasicTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionBasicTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionBasicTest02", sourceFolder, destinationFolder);
    }


    @Test
    public void optionEmptyTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionEmptyTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionLabelValueTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionLabelValueTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionStylesTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionStylesTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionStylesTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionStylesTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optionHeightTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionHeightTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionWidthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionWidthTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionOverflowTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionOverflowTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionOverflowTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionOverflowTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optionPseudoTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionPseudoTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionPseudoTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionPseudoTest02", sourceFolder, destinationFolder);
    }
}
