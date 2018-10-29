package com.itextpdf.html2pdf.css.media.page.min_dimension;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class PageMarginBoxMinDimensionTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/min_dimension/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/min_dimension/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyMinLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMinLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void topMinCenterAndRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    @Ignore("TODO DEVSIX-2389: Change test files after decision")
    public void topMinLeftAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topMinLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topMinLeftAndMinCenterAndMinRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinLeftAndMinCenterAndMinRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyMinTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMinTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMinCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMinCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMinBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMinBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMinCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMinCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMinTopAndMinCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMinTopAndMinCenterAndBottom", sourceFolder, destinationFolder);
    }
}
