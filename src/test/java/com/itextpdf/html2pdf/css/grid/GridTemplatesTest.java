package com.itextpdf.html2pdf.css.grid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class GridTemplatesTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridTemplatesTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridTemplatesTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void basicColumnOneDivTest() throws IOException, InterruptedException {
        runTest("basicColumnOneDivTest");
    }

    @Test
    public void basicColumnFewDivsTest() throws IOException, InterruptedException {
        runTest("basicColumnFewDivsTest");
    }

    @Test
    // TODO DEVSIX-8324
    public void basicColumnFewDivsWithFrTest() throws IOException, InterruptedException {
        runTest("basicColumnFewDivsWithFrTest");
    }

    @Test
    public void basicColumnFewDivs2Test() throws IOException, InterruptedException {
        runTest("basicColumnFewDivs2Test");
    }

    @Test
    // TODO DEVSIX-8330
    // TODO DEVSIX-8331
    public void basicColumnMultiPageTest() throws IOException, InterruptedException {
        runTest("basicColumnMultiPageTest");
    }

    @Test
    public void basicColumnStartEndTest() throws IOException, InterruptedException {
        runTest("basicColumnStartEndTest");
    }

    @Test
    // TODO DEVSIX-8323
    public void basicColumnStartEnd2Test() throws IOException, InterruptedException {
        runTest("basicColumnStartEnd2Test");
    }

    @Test
    // We need to add a "dry run" for cell balancing without layouting to determine final grid size
    public void basicColumnStartEnd3Test() throws IOException, InterruptedException {
        runTest("basicColumnStartEnd3Test");
    }

    //--------------- without grid-templates-columns and grid-templates-rows ---------------
    @Test
    public void basicOnlyGridDisplayTest() throws IOException, InterruptedException {
        runTest("basicOnlyGridDisplayTest");
    }

    //--------------- grid-templates-rows ---------------
    @Test
    public void basicRowOneDivTest() throws IOException, InterruptedException {
        runTest("basicRowOneDivTest");
    }

    @Test
    public void basicRowFewDivsTest() throws IOException, InterruptedException {
        runTest("basicRowFewDivsTest");
    }

    @Test
    public void basicRowStartEndTest() throws IOException, InterruptedException {
        runTest("basicRowStartEndTest");
    }

    //--------------- grid-templates-columns + grid-templates-rows ---------------

    @Test
    public void basicColumnRowFewDivs1Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs1Test");
    }

    @Test
    public void basicColumnRowFewDivs2Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs2Test");
    }

    @Test
    public void basicColumnRowFewDivs3Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs3Test");
    }

    @Test
    public void basicColumnRowFewDivs4Test() throws IOException, InterruptedException {
        runTest("basicColumnRowFewDivs4Test");
    }

    @Test
    public void basicColumnRowStartEndTest() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEndTest");
    }

    @Test
    public void basicColumnRowStartEnd2Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd2Test");
    }

    @Test
    public void basicColumnRowStartEnd3Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd3Test");
    }

    @Test
    public void basicColumnRowStartEnd4Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd4Test");
    }

    @Test
    public void basicColumnRowStartEnd5Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd5Test");
    }

    //TODO DEVSIX-8325 null rows/cols not terminated, causes error in layout
    @Test
    public void basicColumnRowStartEnd6Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd6Test");
    }

    @Test
    public void basicColumnRowStartEnd7Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd7Test");
    }

    //TODO DEVSIX-8323
    @Test
    public void basicColumnRowStartEnd8Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd8Test");
    }

    @Test
    public void basicColumnRowStartEnd9Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd9Test");
    }

    @Test
    public void basicColumnRowStartEnd10Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd10Test");
    }

    @Test
    public void basicColumnRowStartEnd11Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd11Test");
    }
    @Test
    public void basicColumnRowStartEnd12Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd12Test");
    }
    @Test
    public void basicColumnRowStartEnd13Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd13Test");
    }
    @Test
    public void basicColumnRowStartEnd14Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd14Test");
    }
    @Test
    public void basicColumnRowStartEnd15Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd15Test");
    }
    @Test
    public void basicColumnRowStartEnd16Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd16Test");
    }
    @Test
    public void basicColumnRowStartEnd17Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd17Test");
    }

    @Test
    public void basicColumnRowStartEnd18Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd18Test");
    }

    @Test
    public void basicColumnRowStartEnd19Test() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEnd19Test");
    }

    @Test
    public void basicColumnRowStartEndWithInlineTextTest() throws IOException, InterruptedException {
        runTest("basicColumnRowStartEndWithInlineTextTest");
    }

    @Test
    public void basicGridAfterParagraphTest() throws IOException, InterruptedException {
        runTest("basicGridAfterParagraphTest");
    }

    @Test
    public void gridLayoutDisablingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicColumnFewDivsTest",
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
        convertToPdfAndCompare("basicColumnFewDivsWithDisabledGridLayoutTest",
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }
}
