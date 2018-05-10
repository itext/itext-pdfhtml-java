package com.itextpdf.html2pdf.attach.impl.util;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class WhiteSpaceCollapsingAndTrimmingTest extends ExtendedHtmlConversionITextTest {
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attacher/impl/WhiteSpaceCollapsingAndTrimmingTest/";
    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attacher/impl/WhiteSpaceCollapsingAndTrimmingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void emptyElementsTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyElementsTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void emptyElementsTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyElementsTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void emptyElementsTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyElementsTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void floatingInlineBlockInsideLinkTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatingInlineBlockInsideLinkTest01", sourceFolder, destinationFolder);
    }
}
