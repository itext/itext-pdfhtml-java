/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BackgroundOriginTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundOriginTest/";

    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BackgroundOriginTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void backgroundColorContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientPaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientPaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImagePaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImagePaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageContentBoxCleanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageContentBoxClean", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageNoBorderTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageNoBorder", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageNoPaddingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageNoPaddings", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageBorderTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageBorderTop", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImagePaddingLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImagePaddingLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void linearGradientDiffBordersBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("linearGradientDiffBordersBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void linearGradientDiffPaddingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("linearGradientDiffPaddings", sourceFolder, destinationFolder);
    }

    @Test
    public void imagePositionBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagePositionBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void imagePositionPaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagePositionPaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void imagePositionContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagePositionContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void imagePositionNumberValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imagePositionNumberValues", sourceFolder, destinationFolder);
    }
    @Test
    public void imageRepeatXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageRepeatX", sourceFolder, destinationFolder);
    }

}
