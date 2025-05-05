package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class BrTagArtifactTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/BrTagArtifactTest/";
    public static final String DESTINATION_FOLDER = "./target/test/resources/com/itextpdf/html2pdf/css/BrTagArtifactTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void simpleBrTagArtifactTest()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("brTagArtifact", SOURCE_FOLDER, DESTINATION_FOLDER, true);
    }

    @Test
    public void brTagArtifactDoubleTagTest()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("brTagArtifactDoubleTag", SOURCE_FOLDER, DESTINATION_FOLDER, true);
    }

    @Test
    public void brTagSimpleTextFieldTest()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("brTagSimpleTextField", SOURCE_FOLDER, DESTINATION_FOLDER, true);
    }

    @Test
    public void brTagSimpleTableTest()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("brTagSimpleTable", SOURCE_FOLDER, DESTINATION_FOLDER, true);
    }

    @Test
    public void brTagSimpleLabelTest()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("brTagSimpleLabel", SOURCE_FOLDER, DESTINATION_FOLDER, true);
    }
}
