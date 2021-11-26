package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BackgroundColorWithFormattingElementsTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/BackgroundColorWithFormattingElementsTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/BackgroundColorWithFormattingElementsTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void strongTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("strongTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void bTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void iTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("iTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void emTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("emTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void markTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("markTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void smallTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void delTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("delTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void insTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("insTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void subTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("subTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void supTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("supTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
