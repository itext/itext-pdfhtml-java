package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class NestedSpansTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/NestedSpansTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/NestedSpansTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void nestedSpansDifferentNestingLevelsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedSpansDifferentNestingLevels", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void nestedSpansDifferentNestingLevelsNoFloatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedSpansDifferentNestingLevelsNoFloat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void nestedSpansPropertiesLevelsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedSpansPropertiesLevels", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentPaddingOverrideTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentPaddingOverrideTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentCornerRadiusSplitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentCornerRadiusSplitTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentBorderSidePreserveTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentBorderSidePreserveTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentMarginPaddingCombineOverrideTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentMarginPaddingCombineOverrideTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentDifferentTopBottomMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentDifferentTopBottomMarginTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void childSpanMarginDoubledTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("childSpanMarginDoubled", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void parentPaddingIncreasesChildBackgroundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("parentPaddingIncreasesChildBackgroundTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
