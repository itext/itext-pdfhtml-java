/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FlexIntrinsicAspectRatioTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FlexIntrinsicAspectRatioTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FlexIntrinsicAspectRatioTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void autoFixedHeightFixedWidthIndefiniteContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("autoFixedHeightFixedWidthIndefiniteContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    public void autoFixedHeightUnfixedWidthDefiniteContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("autoFixedHeightUnfixedWidthDefiniteContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    public void contentFixedHeightFixedWidthIndefiniteContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contentFixedHeightFixedWidthIndefiniteContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    public void contentFixedHeightUnfixedWidthIndefiniteContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contentFixedHeightUnfixedWidthIndefiniteContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    public void contentUnfixedHeightUnfixedWidthDefiniteContainerStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contentUnfixedHeightUnfixedWidthDefiniteContainerStart", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    // Both firefox and chrome work incorrectly in this case.
    // Paragraph https://www.w3.org/TR/css-flexbox-1/#algo-stretch from the specification explicitly says,
    // that stretch does not affect the main size of the flex item, even if it has an intrinsic aspect ratio.
    public void contentUnfixedHeightUnfixedWidthDefiniteContainerStretchTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contentUnfixedHeightUnfixedWidthDefiniteContainerStretch", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // Firefox works incorrectly in this case
    public void autoPercentageWidthHeightContainerMinHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("autoPercentageWidthHeightContainerMinHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-5265 Inline svg images don't work correctly if they are flex-items.
    public void inlineSvgImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineSvgImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void externalSvgImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("externalSvgImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
