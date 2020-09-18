/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
public class BackgroundRepeatTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundRepeatTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/BackgroundRepeatTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgRepeatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBckgRepeat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgNoRepeatTest() throws InterruptedException, IOException {
        convertToPdfAndCompare("imageBckgNoRepeat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgRoundTest() throws InterruptedException, IOException {
        convertToPdfAndCompare("imageBckgRound", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgSpaceTest() throws InterruptedException, IOException {
        convertToPdfAndCompare("imageBckgSpace", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgRepeatXTest() throws InterruptedException, IOException {
        convertToPdfAndCompare("imageBckgRepeatX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgRepeatYTest() throws InterruptedException, IOException {
        convertToPdfAndCompare("imageBckgRepeatY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void linearGradientBckgRepeatTest() throws InterruptedException, IOException {
        convertToPdfAndCompare("linearGradientBckgRepeat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void linearGradientBckgNoRepeatTest() throws InterruptedException, IOException {
        convertToPdfAndCompare("linearGradientBckgNoRepeat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void linearGradientBckgRoundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("linearGradientBckgRound", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-1708 update cmp file
    public void linearGradientBckgSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("linearGradientBckgSpace", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void linearGradientBckgRepeatXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("linearGradientBckgRepeatX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void linearGradientBckgRepeatYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("linearGradientBckgRepeatY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgRepeatAndSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBckgRepeatAndSpace", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgRoundAndSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBckgRoundAndSpace", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRepeatAndBckgPositionXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRepeatAndBckgPositionX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRepeatAndBckgPositionYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRepeatAndBckgPositionY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRoundSpaceAndBckgPositionXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRoundSpaceAndBckgPositionX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgSpaceRoundAndBckgPositionYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgSpaceRoundAndBckgPositionY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRepeatXAndBckgPositionYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRepeatXAndBckgPositionY", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRepeatYAndBckgPositionXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRepeatYAndBckgPositionX", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRoundAndBckgPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRoundAndBckgPosition", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRoundAndNegativeBckgPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRoundAndNegativeBckgPosition", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgSpaceAndBckgPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgSpaceAndBckgPosition", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgSpaceAndBckgPositionPageSeparationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgSpaceAndBckgPositionPageSeparation", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgSpaceAndNegativeBckgPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgSpaceAndNegativeBckgPosition", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRoundRemainsLessHalfOfImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRoundRemainsLessHalfOfImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRoundRemainsMoreHalfOfImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRoundRemainsMoreHalfOfImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void bckgRoundCompressAndStretchImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bckgRoundCompressAndStretchImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgRoundBckgSizeLessThanImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBckgRoundBckgSizeLessThanImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4370 update cmp file
    public void imageBckgSpaceBckgSizeLessThanImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBckgSpaceBckgSizeLessThanImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
