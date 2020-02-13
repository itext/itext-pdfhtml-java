package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class WidowsTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/WidowsTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/WidowsTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void widows5LinesTest() throws IOException, InterruptedException {
        runTest("widows5LinesTest");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.WIDOWS_CONSTRAINT_VIOLATED))
    public void widows5LinesViolationTest() throws IOException, InterruptedException {
        runTest("widows5LinesViolationTest");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName, sourceFolder, destinationFolder);
    }

}
