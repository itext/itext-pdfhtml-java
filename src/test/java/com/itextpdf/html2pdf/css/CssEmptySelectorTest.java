package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class CssEmptySelectorTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssEmptySelectorTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssEmptySelectorTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void cssEmptyNotEmptyNestedNodesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssEmptyNotEmptyNestedNodesTest", sourceFolder, destinationFolder);
    }
}
