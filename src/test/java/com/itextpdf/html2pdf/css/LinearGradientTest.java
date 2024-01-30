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
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class LinearGradientTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/LinearGradientTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/LinearGradientTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void backgroundLinearGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-linear-gradient", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundLinearGradientWithTransformTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-linear-gradient-with-transform", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-linear-gradient", sourceFolder, destinationFolder);
    }

    // TODO: DEVSIX-3595 update cmp_ after fix and remove log message expectation
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 3, logLevel = LogLevelConstants.WARN)
    })
    public void backgroundImageLinearGradientWithAnglesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-angles-linear-gradient", sourceFolder, destinationFolder);
    }

    // TODO: DEVSIX-3596 update cmp_ after fix and remove log message expectation
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 5, logLevel = LogLevelConstants.WARN)
    })
    public void backgroundImageLinearGradientWithMetricsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-metrics-linear-gradient", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientWithOffsetsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-offsets-linear-gradient", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundRepeatingLinearGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-repeating-linear-gradient", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageRepeatingLinearGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-repeating-linear-gradient", sourceFolder, destinationFolder);
    }

    // TODO: DEVSIX-3595 update cmp_ after fix and remove log message expectation
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 3, logLevel = LogLevelConstants.WARN)
    })
    public void backgroundImageRepeatingLinearGradientWithAnglesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-angles-repeating-linear-gradient", sourceFolder, destinationFolder);
    }

    // TODO: DEVSIX-3596 update cmp_ after fix and remove log message expectation
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 5, logLevel = LogLevelConstants.WARN)
    })
    public void backgroundImageRepeatingLinearGradientWithMetricsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-metrics-repeating-linear-gradient", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageRepeatingLinearGradientWithOffsetsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background-image-offsets-repeating-linear-gradient", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidFirstArgumentTest() throws IOException {
        convertHtmlWithGradient(
                "linear-gradient(not-angle-or-color, orange 100pt, red 150pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidToSideTest0() throws IOException {
        convertHtmlWithGradient("linear-gradient(to , orange 100pt, red 150pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidToSideTest1() throws IOException {
        convertHtmlWithGradient("linear-gradient(to, orange 100pt, red 150pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidToSideTest2() throws IOException {
        convertHtmlWithGradient("linear-gradient(to left left, orange 100pt, red 150pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidToSideTest3() throws IOException {
        convertHtmlWithGradient("linear-gradient(to bottom top, orange 100pt, red 150pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidToSideTest4() throws IOException {
        convertHtmlWithGradient("linear-gradient(to left right, orange 100pt, red 150pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidToSideTest5() throws IOException {
        convertHtmlWithGradient(
                "linear-gradient(to top right right, orange 100pt, red 150pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidColorWithThreeOffsetsValueTest() throws IOException {
        convertHtmlWithGradient("linear-gradient(red, orange 20pt 30pt 100pt, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidColorOffsetValueTest() throws IOException {
        convertHtmlWithGradient("linear-gradient(red, orange 20, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidMultipleHintsInARowValueTest() throws IOException {
        convertHtmlWithGradient("linear-gradient(red, orange, 20%, 30%, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidMultipleHintsInARowWithoutCommaValueTest() throws IOException {
        convertHtmlWithGradient("linear-gradient(red, orange, 20% 30%, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidFirstElementIsAHintValueTest() throws IOException {
        convertHtmlWithGradient("linear-gradient(5%, red, orange, 30%, green 200pt, blue 250pt)");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel = LogLevelConstants.WARN)
    })
    public void invalidLastElementIsAHintValueTest() throws IOException {
        convertHtmlWithGradient("linear-gradient(red, orange, 30%, green 200pt, blue 250pt, 120%)");
    }

    @Test
    public void backgroundImageLinearGradientInBodyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInBody", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInParagraphTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInParagraph", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInHeaderTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInHeader", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInAbbrTest() throws IOException, InterruptedException {
        // TODO DEVSIX-4617 process abbr tag with "text-decoration: underline dotted" CSS styles
        convertToPdfAndCompare("backgroundImageLinearGradientInAbbr", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInListItemsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInListItems", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInSeveralElementsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInSeveralElements", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInSpan", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInTable", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInCiteTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInCite", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInCaptionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInCaption", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInBlockquoteTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInBlockquote", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInLinkTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInLink", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInLableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInLabel", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageLinearGradientInButtonTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInButton", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-4174 update cmp after fix
    public void backgroundImageLinearGradientInFormsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageLinearGradientInForms", sourceFolder, destinationFolder);
    }

    private void convertHtmlWithGradient(String gradientString) throws IOException {
        String html = "<!DOCTYPE html><html lang=\"en\">"
                + "<head><meta charset=\"UTF-8\"></head><body><div style=\"background-image: "
                + gradientString + ";\"></div></body></html>";
        try (OutputStream os = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(html, os);
        }
    }
}
