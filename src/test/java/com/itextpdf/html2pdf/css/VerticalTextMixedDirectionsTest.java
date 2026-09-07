package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextMixedDirectionsTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalTextMixedDirectionsTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/VerticalTextMixedDirectionsTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextWithLineBreaksTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextWithLineBreaksTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextNoEnoughHorizontalSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextNoEnoughHorizontalSpaceTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextWithPageBreakTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextWithPageBreakTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalParagraphMixedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalParagraphMixedTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalParagraphMixedWithLineBreaksTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalParagraphMixedWithLineBreaksTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelTwoLinesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelTwoLinesTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelPageBreakTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelPageBreakTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelLongTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelLongTextTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalParagraphWithHorizontalTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalParagraphWithHorizontalTextTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
