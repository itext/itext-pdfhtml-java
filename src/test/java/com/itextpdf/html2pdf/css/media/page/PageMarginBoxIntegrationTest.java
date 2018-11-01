package com.itextpdf.html2pdf.css.media.page;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class PageMarginBoxIntegrationTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/PageMarginBoxIntegrationTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/PageMarginBoxIntegrationTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void headerFooterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerFooter", sourceFolder, destinationFolder);
    }

    @Test
    public void pageWidthContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("css-page-width-content", sourceFolder, destinationFolder);
    }

    @Test
    public void pageWidthElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("css-page-width-element", sourceFolder, destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topLeftAndCenter() throws IOException, InterruptedException {
        convertToPdfAndCompare("topLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topLeftAndCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topLeftAndCenterAndRight", sourceFolder, destinationFolder);
    }

    //Bottom margin box tests
    @Test
    public void bottomOnlyLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomOnlyLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomOnlyRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomOnlyRight", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomLeftAndCenter() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomLeftAndCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomLeftAndCenterAndRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndCenterAndBottom", sourceFolder, destinationFolder);
    }

    //Right margin box tests
    @Test
    public void rightOnlyTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightOnlyTop", sourceFolder, destinationFolder);
    }

    @Test
    public void rightOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void rightOnlyBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightOnlyBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void rightTopAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightTopAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void rightTopAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightTopAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void rightCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void rightTopAndCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightTopAndCenterAndBottom", sourceFolder, destinationFolder);
    }

    //Edge-case test
    // TODO testing here
    @Test
    public void largeAutoLeftRegularCenterTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoLeftRegularCenterTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeAutoLeftTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeAutoLeftTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeAutoCenterRegularSidesTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoCenterRegularSidesTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedLeftRegularCenterTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedLeftRegularCenterTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeFixedLeftTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeFixedLeftTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void smallFixedLeftTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallFixedLeftTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedCenterRegularSidesTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedCenterRegularSidesTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeAutoTopRegularCenterLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoTopRegularCenterLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeAutoTopLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeAutoTopLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeAutoCenterRegularSidesLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoCenterRegularSidesLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedTopRegularCenterLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedTopRegularCenterLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeFixedTopLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeFixedTopLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void smallFixedTopLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallFixedTopLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES, count = 4))
    public void largeFixedCenterRegularSidesLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedCenterRegularSidesLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedAllLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedAllLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedAllTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedAllTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedSidesAutoMiddleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedSidesAutoMiddleTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedSidesFixedMiddleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedSidesFixedMiddleTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedSidesNoMiddleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedSidesNoMiddleTest", sourceFolder, destinationFolder);
    }

    @Test
    public void twoPagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("twoPagesTest", sourceFolder, destinationFolder);
    }
}
