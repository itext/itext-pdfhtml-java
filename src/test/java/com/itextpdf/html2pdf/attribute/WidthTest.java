package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class WidthTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/attribute/WidthTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/attribute/WidthTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-2554 Html2Pdf: Image with negative width is missed in output pdf
    public void negativeWidthValueInImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("negativeWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
