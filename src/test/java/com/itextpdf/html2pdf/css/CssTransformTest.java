package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class CssTransformTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/CssTransformTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/CssTransformTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformMatrixTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformMatrix", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformRotateTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformRotate", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslate", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateXHugeValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateXHugeValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateYHugeValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateYHugeValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformSkewTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformSkew", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformSkewXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformSkewX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformSkewYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformSkewY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformScaleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformScale", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformScaleXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformScaleX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformScaleYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformScaleY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformCombinationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformCombination", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2862 layout: improve block's TRANSFORM processing
    public void cssTransformCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
