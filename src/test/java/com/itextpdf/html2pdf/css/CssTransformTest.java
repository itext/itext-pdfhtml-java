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
public class CssTransformTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/CssTransformTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/CssTransformTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformMatrixTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformMatrix", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformRotateTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformRotate", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslate", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateXHugeValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateXHugeValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformTranslateYHugeValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformTranslateYHugeValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformSkewTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformSkew", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformSkewXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformSkewX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformSkewYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformSkewY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformScaleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformScale", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformScaleXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformScaleX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformScaleYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformScaleY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cssTransformCombinationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformCombination", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2862 layout: improve block's TRANSFORM processing
    public void cssTransformCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cssTransformCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
