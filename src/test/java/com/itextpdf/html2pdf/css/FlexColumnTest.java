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
public class FlexColumnTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FlexColumnTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FlexColumnTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void ColumnAlignIItemsCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignIItemsCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsCenterJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsCenterJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsCenterJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsCenterJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsCenterJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsCenterJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsCenterJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsCenterJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsCenterJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsCenterJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsEndJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsEndJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsEndJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsEndJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsEndJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsEndJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsEndJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsEndJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsEndJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsEndJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexEndJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexEndJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexEndJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexEndJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexEndJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexEndJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexEndJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexEndJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexEndJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexEndJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexStartJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexStartJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexStartJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexStartJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexStartJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexStartJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexStartJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexStartJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsFlexStartJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsFlexStartJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsStartJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsStartJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsStartJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsStartJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsStartJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsStartJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsStartJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsStartJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnAlignItemsStartJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnAlignItemsStartJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnJustifyContentStartMaxSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnJustifyContentStartMaxSize", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnJustifyContentStartMinSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnJustifyContentStartMinSize", sourceFolder, destinationFolder);
    }
}
