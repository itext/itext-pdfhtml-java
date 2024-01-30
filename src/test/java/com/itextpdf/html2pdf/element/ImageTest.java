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
package com.itextpdf.html2pdf.element;


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
public class ImageTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/ImageTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/ImageTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void imagesInBodyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagesInBody", SOURCE_FOLDER, DESTINATION_FOLDER);
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

}
