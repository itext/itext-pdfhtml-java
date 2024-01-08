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
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class BorderRadiusTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BorderRadiusTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BorderRadiusTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void borderRadius01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest05", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest06", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest07", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest08", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest09", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest10", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest11", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12ImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12Image", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12ATest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12A", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12BTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12B", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest13", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest14", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest15", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius16Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest16", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius17Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest17", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius18Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest18", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadiusInlineElementTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusInlineElementTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void imageBorderRadiusTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBorderRadiusTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadiusInlineSpanElementTest01() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2018, DEVSIX-1191 closing
        convertToPdfAndCompare("borderRadiusInlineSpanElementTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadiusInlineDivElementTest01() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2018, DEVSIX-1191 closing
        convertToPdfAndCompare("borderRadiusInlineDivElementTest01", sourceFolder, destinationFolder);
    }
}
