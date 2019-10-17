package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class FontPropertyTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FontPropertyTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FontPropertyTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    // TODO DEVSIX-3373: Fix cmp file after the bug is fixed. Currently, Helvetica font is picked up while the default one should be used
    public void fontShorthandContainingOnlyFontFamilyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("fontShorthandContainingOnlyFontFamily", sourceFolder, destinationFolder);
    }
}
