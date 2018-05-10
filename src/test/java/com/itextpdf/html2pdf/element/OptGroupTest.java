package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OptGroupTest extends ExtendedHtmlConversionITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/OptGroupTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/OptGroupTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void optGroupBasicTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupBasicTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupBasicTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupBasicTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupEmptyTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupEmptyTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupNestedTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupNestedTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupNestedTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupNestedTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupNoSelectTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupNoSelectTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupStylesTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupStylesTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupHeightTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupHeightTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupWidthTest01() throws IOException, InterruptedException {
        // TODO DEVSIX-1896 Support "nowrap" value of "white-space" css property value
        convertToPdfAndCompare("optGroupWidthTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupOverflowTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupOverflowTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupOverflowTest02() throws IOException, InterruptedException {
        // TODO DEVSIX-1896 Support "nowrap" value of "white-space" css property value
        convertToPdfAndCompare("optGroupOverflowTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optGroupPseudoTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optGroupPseudoTest01", sourceFolder, destinationFolder);
    }
}
