package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OverflowWrapTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/OverflowWrapTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/OverflowWrapTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    // TODO: update cmp file after implementing DEVSIX-1438
    public void overflowWrapCommonScenarioTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowWrapCommonScenario.html"),
                new File(destinationFolder + "overflowWrapCommonScenario.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "overflowWrapCommonScenario.pdf",
                sourceFolder + "cmp_overflowWrapCommonScenario.pdf", destinationFolder));
    }

    @Test
    // TODO: update cmp file after implementing DEVSIX-1438
    public void overflowXOverflowWrapTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowXOverflowWrap.html"),
                new File(destinationFolder + "overflowXOverflowWrap.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "overflowXOverflowWrap.pdf",
                sourceFolder + "cmp_overflowXOverflowWrap.pdf", destinationFolder));
    }

    @Test
    // TODO: update cmp file after implementing DEVSIX-1438
    public void whiteSpaceAndOverflowWrapTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "whiteSpaceAndOverflowWrap.html"),
                new File(destinationFolder + "whiteSpaceAndOverflowWrap.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "whiteSpaceAndOverflowWrap.pdf",
                sourceFolder + "cmp_whiteSpaceAndOverflowWrap.pdf", destinationFolder));
    }

    @Test
    // TODO: update cmp file after implementing DEVSIX-1438
    public void overflowWrapAndFloatTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowWrapAndFloat.html"),
                new File(destinationFolder + "overflowWrapAndFloat.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "overflowWrapAndFloat.pdf",
                        sourceFolder + "cmp_overflowWrapAndFloat.pdf", destinationFolder));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant
            .TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 3)})
    // TODO: update cmp file after implementing DEVSIX-1438
    public void overflowWrapTableScenarioTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowWrapTableScenario.html"),
                new File(destinationFolder + "overflowWrapTableScenario.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "overflowWrapTableScenario.pdf",
                        sourceFolder + "cmp_overflowWrapTableScenario.pdf", destinationFolder));
    }
}
