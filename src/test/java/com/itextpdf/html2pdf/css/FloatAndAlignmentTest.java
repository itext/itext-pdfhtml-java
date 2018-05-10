package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class FloatAndAlignmentTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FloatAndAlignmentTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FloatAndAlignmentTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void singleBlockSingleParagraphRight() throws IOException, InterruptedException {
        /* this test shows different combinations of float values blocks and  paragraph align RIGHT within div container
        */
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        convertToPdfAndCompare("singleBlockSingleParagraphRight", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void singleBlockSingleParagraphLeft() throws IOException, InterruptedException {
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        convertToPdfAndCompare("singleBlockSingleParagraphLeft",sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void singleBlockSingleParagraphJustify() throws IOException, InterruptedException {
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        convertToPdfAndCompare("singleBlockSingleParagraphJustify", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void singleBlockSingleParagraphCenter() throws IOException, InterruptedException {
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        convertToPdfAndCompare("singleBlockSingleParagraphCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void severalBlocksSingleParagraph() throws IOException, InterruptedException {
        /* this test shows different combinations of 3 float values blocks and 1 paragraph aligns within div container
        */
        convertToPdfAndCompare("severalBlocksSingleParagraph", sourceFolder, destinationFolder);
    }

    @Test
    public void blocksInsideParagraph() throws IOException, InterruptedException {
        /* this test shows different combinations of 3 float values blocks and 1 paragraph aligns within div container
        * now it points not only incorrect alignment vs float positioning, but also incorrect float area
        */
        convertToPdfAndCompare("blocksInsideParagraph", sourceFolder, destinationFolder);
    }

    @Test
    public void inlineBlocksInsideParagraph() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineBlocksInsideParagraph", sourceFolder, destinationFolder);
    }

    @Test
    public void inlineFloatsWithTextAlignmentTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineFloatsWithTextAlignmentTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void inlineFloatsWithTextAlignmentTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineFloatsWithTextAlignmentTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void inlineFloatsWithTextAlignmentTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineFloatsWithTextAlignmentTest03", sourceFolder, destinationFolder);
    }
}
