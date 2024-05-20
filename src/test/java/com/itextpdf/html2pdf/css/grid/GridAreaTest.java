package com.itextpdf.html2pdf.css.grid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class GridAreaTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridAreaTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridAreaTest/";


    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void basicGridArea1Test() throws IOException, InterruptedException {
        runTest("basicGridArea1");
    }

    @Test
    public void basicGridArea2Test() throws IOException, InterruptedException {
        runTest("basicGridArea2");
    }

    @Test
    public void templateAreasBasicTest() throws IOException, InterruptedException {
        runTest("templateAreasBasic");
    }

    @Test
    public void templateAreasInvalidNameTest() throws IOException, InterruptedException {
        runTest("templateAreasInvalidName");
    }

    @Test
    public void templateAreasWithDotsTest() throws IOException, InterruptedException {
        runTest("templateAreasWithDots");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_AREAS_IS_INVALID))
    public void invalidTemplateAreasTest() throws IOException, InterruptedException {
        runTest("invalidTemplateAreas");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }
}
