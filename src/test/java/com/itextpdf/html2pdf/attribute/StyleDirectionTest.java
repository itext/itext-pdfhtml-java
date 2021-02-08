package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class StyleDirectionTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute"
            + "/StyleDirectionTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/StyleDirectionTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    // TODO DEVSIX-5034 Incorrect direction of dot
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 8),
            @LogMessage(messageTemplate = LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH,
                    count = 1)
    })
    public void rtlDirectionOfTableElementsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rtlDirectionOfTableElements", sourceFolder, destinationFolder);
    }
}
