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
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ClearTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/ClearTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/ClearTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void clear02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear03Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear04Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear06Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear07Test", sourceFolder, destinationFolder);
    }

    @Test
    // TODO: DEVSIX-1269, DEVSIX-5474 update cmp file after fixing issues
    public void clear08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear08Test", sourceFolder, destinationFolder);
    }

    @Test
    // TODO: DEVSIX-5474 update cmp file after fixing
    public void clear09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear09Test", sourceFolder, destinationFolder);
    }

    @Test
    // TODO: DEVSIX-5474 update cmp file after fixing
    public void clear10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear10Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear11Test", sourceFolder, destinationFolder);
    }

    @Test
    public void imageFloatParagraphClearTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageFloatParagraphClear", sourceFolder, destinationFolder);
    }

    @Test
    public void clearInTableWithImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clearInTableWithImage", sourceFolder, destinationFolder);
    }

    @Test
    public void imgFloatAmongParaWithClearPropTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgFloatAmongParaWithClearProp", sourceFolder, destinationFolder);
    }

    @Test
    public void imgFloatAmongParaWithSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgFloatAmongParaWithSpan", sourceFolder, destinationFolder);
    }

    @Test
    public void paraFloatLeftImgClearLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paraFloatLeftImgClearLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void paraFloatImgClearAndDisplayBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paraFloatImgClearAndDisplayBlock", sourceFolder, destinationFolder);
    }

    @Test
    public void paraFloatImgWideBorderClearAndDisplayBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paraFloatImgWideBorderClearAndDisplayBlock", sourceFolder, destinationFolder);
    }

    @Test
    public void imgFloatWideBorderAmongParaWithClearTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgFloatWideBorderAmongParaWithClear", sourceFolder, destinationFolder);
    }

    @Test
    public void imgWideBorderFloatAmongParaWithSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgWideBorderFloatAmongParaWithSpan", sourceFolder, destinationFolder);
    }

    @Test
    public void imgWideBorderClearAndDisplayBlockParaFloatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgWideBorderClearAndDisplayBlockParaFloat", sourceFolder, destinationFolder);
    }

    @Test
    public void floatsPositioningOutsideDivWithClearTest() throws IOException, InterruptedException {
        // TODO DEVSIX-7602 Css right and left clear property is processed incorrectly if floats "intersect" by x
        convertToPdfAndCompare("floatsPositioningOutsideDivWithClear", sourceFolder, destinationFolder);
    }

    @Test
    public void wideFloatsPositioningOutsideDivWithClearTest() throws IOException, InterruptedException {
        // TODO DEVSIX-7602 Css right and left clear property is processed incorrectly for wide floats
        convertToPdfAndCompare("wideFloatsPositioningOutsideDivWithClear", sourceFolder, destinationFolder);
    }

    @Test
    public void floatsWithClearInsideFlexElementTest() throws IOException, InterruptedException {
        // TODO DEVSIX-7602 Flex element width is incorrect in case clear property is applied to floats inside it
        convertToPdfAndCompare("floatsWithClearInsideFlexElement", sourceFolder, destinationFolder);
    }
}
