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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class BackgroundClipTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundClipTest/";

    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BackgroundClipTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void backgroundColorBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorContentBox1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorContentBox1", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorContentBoxMarginsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorContentBoxMargins", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorDefaultTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorDefault", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorPaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorPaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageColorContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageColorContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageColorDefaultTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageColorDefault", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageDiffBordersPaddingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageDiffBordersPaddings", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageDefaultTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageDefault", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImagePaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImagePaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImage", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImageColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageColor2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImageColor2", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageColor3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImageColor3", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorMultipleClipTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorMultipleClip", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4525 border-radius works incorrectly with background-clip
    public void backgroundColorBorderRadiusTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorBorderRadius", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4525 border-radius works incorrectly with background-clip
    public void backgroundImageBorderRadiusTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageBorderRadius", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientPaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientPaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorComma", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorNoCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorNoComma", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorNoneCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorNoneComma", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorNoneNoCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorNoneNoComma", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImagePositionContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImagePositionContentBox", sourceFolder, destinationFolder);
    }
}
