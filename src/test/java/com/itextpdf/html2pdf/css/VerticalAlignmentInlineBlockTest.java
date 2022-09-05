package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class VerticalAlignmentInlineBlockTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalAlignmentInlineBlockTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/VerticalAlignmentInlineBlockTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void checkBaselineAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("baseline", sourceFolder, destinationFolder);
    }

    @Test
    public void checkBaselineAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("baseline-wrap", sourceFolder, destinationFolder);
    }

    @Test
    public void checkBottomAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottom", sourceFolder, destinationFolder);
    }

    @Test
    public void checkBottomAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottom-wrap", sourceFolder, destinationFolder);
    }

    @Test
    public void checkMiddleAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("middle", sourceFolder, destinationFolder);
    }

    @Test
    public void checkMiddleAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("middle-wrap", sourceFolder, destinationFolder);
    }
    @Test
    public void checkTopAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("top", sourceFolder, destinationFolder);
    }

    @Test
    public void checkTopAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("top-wrap", sourceFolder, destinationFolder);
    }
    @Test
    public void checkMixedAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("mixed", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void checkMixedAlignment2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("mixed2", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void checkMixedAlignment3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("mixed3", sourceFolder, destinationFolder);
    }
    @Test
    public void checkCustomerExampleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("customerExample", sourceFolder, destinationFolder);
    }

    @Test
    public void checkSingleImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("singleimage", sourceFolder, destinationFolder);
    }

    @Test
    public void checkElementsInDivAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("ElementsInDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void checkSpanAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("span", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void checkStyledElementsAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("styleAlignment", sourceFolder, destinationFolder);
    }

    @Test
    public void checkUnorderedListAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("unorderedList", sourceFolder, destinationFolder);
    }

    @Test
    public void orderedListAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("orderedList", sourceFolder, destinationFolder);
    }

    @Test
    public void tableAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("table", sourceFolder, destinationFolder);
    }

    @Test
    public void buttonAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("button", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void formAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("form", sourceFolder, destinationFolder);
    }

    @Test
    public void headerAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("header", sourceFolder, destinationFolder);
    }

    @Test
    public void paragraphAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraph", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void allStylesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("AllStyles", sourceFolder, destinationFolder);
    }
}
