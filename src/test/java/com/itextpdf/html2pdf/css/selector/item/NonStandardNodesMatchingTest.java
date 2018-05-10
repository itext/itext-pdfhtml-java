package com.itextpdf.html2pdf.css.selector.item;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class NonStandardNodesMatchingTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/selector/item/NonStandardNodesMatchingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/selector/item/NonStandardNodesMatchingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void pseudoElementMatchingTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoElementMatchingTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void pseudoElementMatchingTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoElementMatchingTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void documentNodeMatchingTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("documentNodeMatchingTest01", sourceFolder, destinationFolder);
    }
}
