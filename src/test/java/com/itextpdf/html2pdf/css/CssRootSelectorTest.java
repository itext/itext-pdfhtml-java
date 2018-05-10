package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class CssRootSelectorTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssRootSelectorTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssRootSelectorTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void rootSelectorTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("rootSelectorTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void rootAndNotRootTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rootAndNotRootTest", sourceFolder, destinationFolder);
    }
}
