/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class BackgroundTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BackgroundTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void backgroundSizeTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "backgroundsize01.html"),
                new File(destinationFolder + "backgroundsize01.pdf"));
        Assertions.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "backgroundsize01.pdf", sourceFolder + "cmp_backgroundsize01.pdf",
                        destinationFolder));
    }

    @Test
    public void backgroundAttachmentMarginRoot1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundAttachmentMarginRoot1", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundAttachmentMarginRoot2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundAttachmentMarginRoot2", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4445 support display: contents
    public void backgroundColorBodyDisplayContentsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorBodyDisplayContents", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMarginHtmlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMarginHtml", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4426 support rotateZ() - remove log message after fixing
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)})
    public void backgroundTransformedRootTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundTransformedRoot", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4448 support will-change CSS property
    public void backgroundWillChangeRootTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundWillChangeRoot", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSoloImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_solo_image", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageWithoutFixedWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageWithoutFixedSize", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageCoverSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageCoverSize", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.ONLY_THE_LAST_BACKGROUND_CAN_INCLUDE_BACKGROUND_COLOR)
    })
    public void backgroundImageAndColorNotLastTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_image_and_color_not_last", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void backgroundImageAndColorsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_image_and_colors", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSoloImageWithNoRepeatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_solo_image_with_no_repeat", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSoloImageWithNoRepeatAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_solo_image_with_no_repeat_and_color", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_multi_image", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundTransparentAndNotTransparentImagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundTransparentAndNotTransparentImages", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundTwoTransparentImagesAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundTwoTransparentImagesAndColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientPosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientWithPercentagePositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientWithPercentagePosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionWithoutYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionWithoutY", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionX", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionY", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionXYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionXY", sourceFolder, destinationFolder);
    }

    @Test
    // html works inappropriate in chrome.
    public void backgroundComplicatedPositionXYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundComplicatedPositionXY", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionXAndPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionXAndPosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionAndPositionXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionAndPositionX", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionInheritAndInitialTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionInheritAndInitial", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandOnlyImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandOnlyImage", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandImageRepeatAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandImageRepeatAndColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandTwoImageWithRepeatAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandTwoImageWithRepeatAndColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandThreeImagesWithOneRepeatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandThreeImagesWithOneRepeat", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandAndPropertyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandAndProperty", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPropertyAndShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPropertyAndShorthand", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSvgTest() throws IOException, InterruptedException {
        String testName = "backgroundSvgTest";
        HtmlConverter.convertToPdf(new File(sourceFolder + testName + ".xht"),
                new File(destinationFolder + testName + ".pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf",
                sourceFolder + "cmp_" + testName + ".pdf", destinationFolder));
    }

    @Test
    public void multipleBackgroundRepeatMissedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multipleBackgroundRepeatMissed", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOrigin", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginGradient", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginShorthand", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginLatentImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginLatentImage", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginRepeatXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginRepeatX", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginLatentImageRepeatXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginLatentImageRepeatX", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginPositionOnePercentageValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginPositionOnePercentageValue", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginSizeCoverTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginSizeCover", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginRepeatSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginRepeatSpace", sourceFolder, destinationFolder);
    }
    @Test
    public void clipOriginRepeatRoundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginRepeatRound", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgImageInSimpleDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgImageInSimpleDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgImageRepeatInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgImageRepeatInDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgImageNoRepeatInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgImageNoRepeatInDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgImageRepeatYInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgImageRepeatYInDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgImageRepeatXInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgImageRepeatXInDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgBase64Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgBase64", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgShorthandThreeSizedImagesRepeatPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgShorthandThreeSizedImagesRepeatPosition", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgPositionInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgPositionInDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void bckgPositionInDivDiffValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgPositionInDivDiffValues", sourceFolder, destinationFolder);
    }

    @Test
    public void svgBase64Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("svgBase64", sourceFolder, destinationFolder);
    }
}
