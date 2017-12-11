package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class FloatAndAlignmentTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FloatAndAlignmentTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FloatAndAlignmentTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void SingleBlockSingleParagraphRight() throws IOException, InterruptedException {
        /* this test shows different combinations of float values blocks and  paragraph align RIGHT within div container
        */
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        //TODO: update cmp file after ticket DEVSIX-1268 fix (Float property...)
        runTest("SingleBlockSingleParagraphRight", "diffRight01_");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void SingleBlockSingleParagraphLeft() throws IOException, InterruptedException {
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        //TODO: update cmp file after ticket DEVSIX-1268 fix (Float property...)
        runTest("SingleBlockSingleParagraphLeft", "diffLeft01_");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void SingleBlockSingleParagraphJustify() throws IOException, InterruptedException {
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        //TODO: update cmp file after ticket DEVSIX-1268 fix (Float property...)
        runTest("SingleBlockSingleParagraphJustify", "diffJust01_");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void SingleBlockSingleParagraphCenter() throws IOException, InterruptedException {
        //TODO: update test after ticket DEVSIX-1720  fix (WARN Invalid css property declaration: float: initial)
        //TODO: update cmp file after ticket DEVSIX-1268 fix (Float property...)
        runTest("SingleBlockSingleParagraphCenter", "diffCent01_");
    }

    @Test
    public void SeveralBlocksSingleParagraph() throws IOException, InterruptedException {
        /* this test shows different combinations of 3 float values blocks and 1 paragraph aligns within div container
        */
        //TODO: update cmp file after ticket DEVSIX-1268 fix (Float property...)
        runTest("SeveralBlocksSingleParagraph", "diffSev01_");
    }

    @Test
    public void BlocksInsideParagraph() throws IOException, InterruptedException {
        /* this test shows different combinations of 3 float values blocks and 1 paragraph aligns within div container
        * now it points not only incorrect alignment vs float positioning, but also incorrect float area
        */
        //TODO: update cmp file after ticket DEVSIX-1268 fix (Float property...)
        //TODO: update after DEVSIX-1437 fix (Fix edge cases for floats splitting)
        runTest("BlocksInsideParagraph", "diffInside01_");
    }

    private void runTest(String testName, String diff) throws IOException, InterruptedException {
        String htmlName = sourceFolder + testName + ".html";
        String outFileName = destinationFolder + testName + ".pdf";
        String cmpFileName = sourceFolder + "cmp_" + testName + ".pdf";
        HtmlConverter.convertToPdf(new File(htmlName), new File(outFileName));
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, diff));
    }
}
