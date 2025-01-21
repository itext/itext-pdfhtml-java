/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.element;


import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class ImageTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/ImageTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/ImageTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void imagesInBodyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagesInBody", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void relativeImageInStaticContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeImageInStaticContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imagesWithWideBorders() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagesWithWideBorders", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imagesWithWideMargins() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagesWithWideMargins", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2467
    public void imagesWithWidePaddings() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagesWithWidePaddings", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2467
    public void imagesWithWidePaddingsBordersMargins() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagesWithWidePaddingsBordersMargins", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void checkImageBorderRadius() throws IOException, InterruptedException {
        convertToPdfAndCompare("checkImageBorderRadius", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imageFileDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallWidthImagePlacement", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imageUrlDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageUrlDocument", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imageWithIncorrectBase64Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageWithIncorrectBase64", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-6190 pdfHtml: Embedded image from html doesn't present in out pdf
    public void embeddedImageBase64Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("embeddedImageBase64", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imageBase64DifferentFormatsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBase64DifferentFormats", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void smallImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallImageTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO: DEVSIX-2485
    public void imageInSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageInSpan", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void caseSensitiveBase64DataInCssNormalizationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("caseSensitiveBase64DataInCssNormalization", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void inlineBlockImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineBlockImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    /**
     * Important: the name of the resource in this test is "base64.svg".
     * This is done deliberately and tests for a bug that was present before -
     * image was only fetched as base64 value and not as resource link
     */
    @Test
    public void svgExternalResourceCornerCaseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("svgExternalResourceCornerCase", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imageAltTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageAltText", SOURCE_FOLDER, DESTINATION_FOLDER, true);
    }

    @Test
    // Android-Conversion-Ignore-Test (TODO DEVSIX-6459 fix the SecurityException(Permission denied) from UrlUtil method)
    public void imageUrlExternalDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("externalUrlImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 3))
    //To see the result in html, just increase the size
    public void sourceMediaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("sourceMedia", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void resolutionInfoStructOf8bimHeaderImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resolutionInfoStructOf8bimHeaderImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void htmlImgBase64SVGTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgTag_base64svg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg1_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg1_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg1_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg1_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_5", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg1_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_6", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_5", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_6", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2_7Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_7", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2_8Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_8", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg2_9Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_9", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_2_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_2_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    public void relativeSizeSvg3_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_5", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8870 improve support of relative sized SVG in img HTML elements
    @Test
    public void relativeSizeSvg3_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_6", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_7Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_7", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_8Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_8", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_9Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_9", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_12", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_13", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_14", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg3_15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_15", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_5", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    //TODO DEVSIX-8870 improve support of relative sized SVG in img HTML elements
    @Test
    public void fixedImgRelativeSizeSvg3_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_6", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_7Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_7", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_8Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_8", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_9Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_9", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_12", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_13", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_14", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void fixedImgRelativeSizeSvg3_15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_15", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    public void relativeHeightImgRelativeSizeSvg3_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_5", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8870 improve support of relative sized SVG in img HTML elements
    @Test
    public void relativeHeightImgRelativeSizeSvg3_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_6", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_7Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_7", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_8Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_8", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_9Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_9", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_12", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_13", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_14", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_15", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void relativeSizeSvg4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void relativeSizeSvg4_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg4_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO DEVSIX-8869 Percent height is not resolved in fixed size container
    @Test
    public void relativeSizeSvg4_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg4_4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg5_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg5_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvg6_1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg6_1", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-1316 make percent width doesn't affect elements min max width
    public void inlineRelativeImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineRelativeImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-1316 make percent width doesn't affect elements min max width
    // TODO DEVSIX-7003 Problem with layouting image with relative size in the table
    public void tableRelativeImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableRelativeImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeImageInRelativeContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeImageInRelativeContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageWithSizeAndDivContent1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageWithSizeAndDivContent1", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageWithSizeAndDivContent2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageWithSizeAndDivContent2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageWithSizeAndDivContent3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageWithSizeAndDivContent3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageWithSizeAndDivContent4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageWithSizeAndDivContent4", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvgInFixedImg() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInFixedImg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvgInFixedImg2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInFixedImg2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvgInRelativeImg() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInRelativeImg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvgInRelativeImgWithCustomViewbox2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInRelativeImgWithCustomViewbox2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvgInRelativeImgWithCustomViewbox() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInRelativeImgWithCustomViewbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }


    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void giantSvgInRelativeImg() throws IOException, InterruptedException {
        convertToPdfAndCompare("giantSvgInRelativeImg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void invalidSizeSvgInRelativeImg() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalidSizeSvgInRelativeImg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void fixedSizeSvgInRelativeImg() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedSizeSvgInRelativeImg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSizeSvgFixedInlineBlock() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgFixedInlineBlock", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSvgInSeveralImages() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSvgInSeveralImages", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void relativeSvgInImgAndBackground() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSvgInImgAndBackground", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void percentHeightImgContainer() throws IOException, InterruptedException {
        convertToPdfAndCompare("percentHeightImgContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    public void percentHeightImgContainer2() throws IOException, InterruptedException {
        convertToPdfAndCompare("percentHeightImgContainer2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
