package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class HtmlStylesToCssConverterIntegrationTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/HtmlStylesToCssConverterIntegrationTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/HtmlStylesToCssConverterIntegrationTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void objectTagWidthAndHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTagWidthAndHeightTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
