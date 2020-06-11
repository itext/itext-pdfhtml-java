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
public class ListStyleImageLinearGradientTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/ListStyleImageLinearGradientTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/ListStyleImageLinearGradientTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    //TODO: Update cmp-file and remove logmessage when DEVSIX-4138 will be implemented
    public void linearGradientInListStyleTest() throws IOException, InterruptedException {
        runTest("linearGradientInListStyle");
    }

    @Test
    //TODO: Update cmp-file and remove logmessage when DEVSIX-4138 will be implemented
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 3)})
    public void linearGradientTypeTest() throws IOException, InterruptedException {
        runTest("linearGradientType");
    }

    @Test
    //TODO: Update cmp-file and remove logmessage when DEVSIX-4138 will be implemented
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 3)})
    public void repeatingLinearGradientTypeTest() throws IOException, InterruptedException {
        runTest("repeatingLinearGradientType");
    }

    @Test
    //TODO: Update cmp-file and remove logmessage when DEVSIX-4138 will be implemented
    public void linearGradientWithEmRemValuesTest() throws IOException, InterruptedException {
        runTest("linearGradientWithEmRemValues");
    }

    @Test
    //TODO: Update cmp-file and remove logmessage when DEVSIX-4138 will be implemented
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 5)})
    public void differentLinearGradientsInElementsTest() throws IOException, InterruptedException {
        runTest("differentLinearGradientsInElements");
    }

    @Test
    //TODO: Update cmp-file and remove logmessage when DEVSIX-4138 will be implemented
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 5)})
    public void linearGradientInDifferentElementsTest() throws IOException, InterruptedException {
        runTest("linearGradientInDifferentElements");
    }

    @Test
    //TODO: Update cmp-file and remove logmessage when DEVSIX-4138 will be implemented
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 3)})
    public void linearGradientDifferentFontSizeTest() throws IOException, InterruptedException {
        runTest("linearGradientDifferentFontSize");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + testName + ".html"),
                new File(DESTINATION_FOLDER + testName + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + testName + ".pdf",
                SOURCE_FOLDER + "cmp_" + testName + ".pdf", DESTINATION_FOLDER, "diff_" + testName));
    }
}
