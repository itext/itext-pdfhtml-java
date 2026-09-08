/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 Apryse Group NV
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
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class RadialGradientTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/RadialGradientTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/RadialGradientTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void backgroundRadialGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // TODO DEVSIX-4136 alpha is not supported
    @Test
    public void backgroundRadialGradientWithSlashInColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-radial-gradient-with-slash-in-color", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundRadialGradientWithTransformTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-radial-gradient-with-transform", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageRadialGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 5,
                    logLevel = LogLevelConstants.WARN)
    })
    public void backgroundImageRadialGradientWithMetricsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-metrics-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageRadialGradientWithOffsetsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-offsets-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundRepeatingRadialGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-repeating-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageRepeatingRadialGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-repeating-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 5,
                    logLevel = LogLevelConstants.WARN)
    })
    public void backgroundImageRepeatingRadialGradientWithMetricsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-metrics-repeating-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageRepeatingRadialGradientWithOffsetsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-offsets-repeating-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // Pdf differs from html, same as backgroundImageLinearGradientInBodyTest
    @Test
    public void backgroundImageRadialGradientInBodyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageRadialGradientInBody", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // Pdf differs from html but backgroundImageLinearGradientInSeveralElementsTest is the same
    @Test
    public void backgroundImageRadialGradientInSeveralElementsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageRadialGradientInSeveralElements", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageRadialGradientInSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageRadialGradientInSpan", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageRadialGradientInTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageRadialGradientInTable", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundImageRadialGradientInButtonTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageRadialGradientInButton", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void radialGradientExtentKeywordsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-extent-keywords", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void radialGradientCircleOneLengthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-circle-one-length", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // Nothing is renderer in browsers but https://www.w3.org/TR/css-images-4/#radial-size allows it
    @Test
    public void radialGradientCirclePercentageSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-circle-percentage-size", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void radialGradientEllipsePercentageSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-ellipse-percentage-size", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void radialGradientPercentagePositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-percentage-position", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void radialGradientOffsetPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-offset-position", SOURCE_FOLDER, DESTINATION_FOLDER);
    }


    @Test
    public void radialGradientExplicitSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-explicit-size", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void radialGradientShapeGrammarTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-shape", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 2,
                    logLevel = LogLevelConstants.WARN)
    })
    public void radialGradientSizeGrammarTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-size", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 2,
                    logLevel = LogLevelConstants.WARN),
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION,
                    count = 2, logLevel = LogLevelConstants.WARN)
    })
    public void radialGradientPositionGrammarTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-position", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN),
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void radialGradientInterpolationGrammarTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-interpolation", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // TODO DEVSIX-4136 update cmp when gradient opacity is supported
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 4,
                    logLevel = LogLevelConstants.WARN)
    })
    public void radialGradientColorStopListGrammarTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("radial-gradient-color-stop-list", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listStyleImageRadialGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("list-style-image-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listStyleShorthandRadialGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("list-style-shorthand-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidRadialGradientKeywordTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalid-radial-gradient-keyword", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidColorWithThreeOffsetsValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalid-color-with-three-offsets-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidColorOffsetValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalid-color-offset-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidMultipleHintsInARowValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalid-multiple-hints-in-a-row-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidMultipleHintsInARowWithoutCommaValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalid-multiple-hints-in-a-row-without-comma-radial-gradient", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidFirstElementIsAHintValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalid-first-element-is-a-hint-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidLastElementIsAHintValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalid-last-element-is-a-hint-radial-gradient", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidRadialGradientCircleTwoSizesTest() throws IOException {
        convertHtmlWithGradient("radial-gradient(circle 100px 200px, red, blue)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidRadialGradientEllipseSingleSizeTest() throws IOException {
        convertHtmlWithGradient("radial-gradient(ellipse 100px, red, blue)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidRadialGradientAtWithoutPositionTest() throws IOException {
        convertHtmlWithGradient("radial-gradient(at, red, blue)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidRadialGradientMultipleAtTest() throws IOException {
        convertHtmlWithGradient("radial-gradient(circle at center at top, red, blue)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidRadialGradientNegativeRadiusTest() throws IOException {
        convertHtmlWithGradient("radial-gradient(-50px, red, blue)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION,
                    logLevel = LogLevelConstants.WARN)
    })
    public void invalidRadialGradientNoStopsTest() throws IOException {
        convertHtmlWithGradient("radial-gradient(circle)");
    }

    private static void convertHtmlWithGradient(String gradient) throws IOException {
        String html = "<div style=\"background-image: " + gradient + "; width: 500pt; height: 100pt;\"></div>";
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(html, outputStream);
        }
    }
}
