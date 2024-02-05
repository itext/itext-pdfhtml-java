package com.itextpdf.html2pdf.css.grid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class GridTemplateColumnTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridTemplateColumnTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridTemplateColumnTest/";


    //TODO DEVSIX-3340 change cmp files when GRID LAYOUT is supported

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void templateColumnBordersTest() throws IOException, InterruptedException {
        runTest("template-cols-borders");
    }

    @Test
    public void templateColumnStartEndTest() throws IOException, InterruptedException {
        runTest("template-cols-column-start-end");
    }

    @Test
    public void templateColumnWidthUnitsTest() throws IOException, InterruptedException {
        runTest("template-cols-different-width-units");
    }

    @Test
    public void templateColumnFitContentTest() throws IOException, InterruptedException {
        runTest("template-cols-fit-content");
    }

    @Test
    public void templateColumnFitContentAutoTest() throws IOException, InterruptedException {
        runTest("template-cols-fit-content-auto");
    }

    @Test
    public void templateColumnFrTest() throws IOException, InterruptedException {
        runTest("template-cols-fr");
    }

    @Test
    public void templateColumnGridGapTest() throws IOException, InterruptedException {
        runTest("template-cols-grid-gap");
    }

    @Test
    public void templateColumnHeightWidthTest() throws IOException, InterruptedException {
        runTest("template-cols-height-width");
    }

    @Test
    public void templateColumnMarginTest() throws IOException, InterruptedException {
        runTest("template-cols-margin");
    }

    @Test
    public void templateColumnMinMaxTest() throws IOException, InterruptedException {
        runTest("template-cols-minmax");
    }

    @Test
    public void templateColumnMixedTest() throws IOException, InterruptedException {
        runTest("template-cols-mixed");
    }

    @Test
    public void templateColumnMultiPageTest() throws IOException, InterruptedException {
        runTest("template-cols-enormous-size");
    }

    @Test
    public void templateColumnNestedTest() throws IOException, InterruptedException {
        runTest("template-cols-nested");
    }

    @Test
    public void templateColumnPaddingTest() throws IOException, InterruptedException {
        runTest("template-cols-padding");
    }

    @Test
    public void templateColumnRepeatTest() throws IOException, InterruptedException {
        runTest("template-cols-repeat");
    }

    @Test
    public void templateColumnRepeatMinMaxTest() throws IOException, InterruptedException {
        runTest("template-cols-repeat-minmax");
    }

    @Test
    public void templateColumnColumnGapTest() throws IOException, InterruptedException {
        runTest("template-cols-column-gap");
    }

    @Test
    public void templateColumnBasicTest() throws IOException, InterruptedException {
        runTest("template-cols-without-other-props");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
