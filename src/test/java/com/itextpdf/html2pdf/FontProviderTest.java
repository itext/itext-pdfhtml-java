package com.itextpdf.html2pdf;

import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

// Actually the results are invalid because there is no pdfCalligraph.
// But we'd like to test how Free Sans works for a specific scripts.
public class FontProviderTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/FontProviderTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/FontProviderTest/";

    private static final String TYPOGRAPHY_WARNING = "Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = TYPOGRAPHY_WARNING, count = 14)
    })
    public void hebrewTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hebrew.html"), new File(destinationFolder + "hebrew.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hebrew.pdf", sourceFolder + "cmp_hebrew.pdf", destinationFolder, "diffHebrew_"));
    }

    @Test
    public void devanagariTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "devanagari.html"), new File(destinationFolder + "devanagari.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "devanagari.pdf", sourceFolder + "cmp_devanagari.pdf", destinationFolder, "diffDevanagari_"));
    }

}
