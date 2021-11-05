package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class HeightTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/attribute/HeightTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/attribute/HeightTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-2554 Html2Pdf: Image with negative height is missed in output pdf
    public void negativeHeightValueInImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("negativeHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
