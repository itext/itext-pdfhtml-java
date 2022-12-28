/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

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
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BackgroundBlendModeTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundBlendModeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BackgroundBlendModeTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void backgroundBlendModeColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeColorBurnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeColorBurn", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeColorDodgeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeColorDodge", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeDarkenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeDarken", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeDifferenceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeDifference", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeExclusionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeExclusion", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeHardLightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeHardLight", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeHueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeHue", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeLightenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeLighten", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeLuminosityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeLuminosity", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeMultiplyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeMultiply", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeNormalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeNormal", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeOverlayTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeOverlay", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeSaturationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeSaturation", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeScreenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeScreen", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeSoftLightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeSoftLight", sourceFolder, destinationFolder);
    }

    @Test
    public void oneImageTwoBackgroundBlendModesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("oneImageTwoBackgroundBlendModes", sourceFolder, destinationFolder);
    }

    @Test
    public void twoImagesOneBackgroundBlendModeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("twoImagesOneBackgroundBlendMode", sourceFolder, destinationFolder);
    }

    @Test
    public void imageAndGradientWithBackgroundBlendModeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageAndGradientWithBackgroundBlendMode", sourceFolder, destinationFolder);
    }

    @Test
    public void placementOrderOfLayersAndBackgroundBlendModeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("placementOrderOfLayersAndBackgroundBlendMode", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeTwoGradientsSaturationLuminosityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeTwoGradientsSaturationLuminosity", sourceFolder, destinationFolder);
    }

    @Test
    public void threeBackgroundBlendModesLastNormalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("threeBackgroundBlendModesLastNormal", sourceFolder, destinationFolder);
    }

    @Test
    public void threeBackgroundBlendModesSecondNormalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("threeBackgroundBlendModesSecondNormal", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundBlendModeLetterCaseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundBlendModeLetterCase", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION,
                    logLevel = LogLevelConstants.WARN, count = 3)})
    public void invalidBackgroundBlendModeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalidBackgroundBlendMode", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-blend-mode-two-gradients-darken-lighten", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientTest2() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-blend-mode-two-gradients-normal-darken", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidBackgroundBlendModeValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-blend-mode-invalid", sourceFolder, destinationFolder);
    }
}
