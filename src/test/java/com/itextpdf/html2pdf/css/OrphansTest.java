package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OrphansTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/OrphansTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/OrphansTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }


    @Test
    public void orphans5LinesTest() throws IOException, InterruptedException {
        runTest("orphans5LinesTest");
    }

    @Test
    public void orphans5LinesOverflowTest() throws IOException, InterruptedException {
        runTest("orphans5LinesOverflowTest");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName, sourceFolder, destinationFolder);
    }
}
