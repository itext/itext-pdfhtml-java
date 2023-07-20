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
public class FlexColumnReverseTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FlexColumnReverseTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FlexColumnReverseTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void ColumnReverseAlignIItemsCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignIItemsCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsCenterJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsCenterJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsCenterJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsCenterJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsCenterJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsCenterJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsCenterJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsCenterJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsCenterJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsCenterJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsEndJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsEndJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsEndJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsEndJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsEndJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsEndJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsEndJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsEndJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsEndJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsEndJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexEndJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexEndJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexEndJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexEndJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexEndJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexEndJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexEndJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexEndJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexEndJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexEndJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexStartJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexStartJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexStartJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexStartJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexStartJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexStartJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexStartJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexStartJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsFlexStartJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsFlexStartJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsStartJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsStartJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsStartJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsStartJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsStartJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsStartJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsStartJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsStartJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseAlignItemsStartJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseAlignItemsStartJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseJustifyContentCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseJustifyContentCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseJustifyContentEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseJustifyContentEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseJustifyContentFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseJustifyContentFlexEnd", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseJustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseJustifyContentFlexStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseJustifyContentStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseJustifyContentStart", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseJustifyContentStartMaxSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseJustifyContentStartMaxSize", sourceFolder, destinationFolder);
    }

    @Test
    public void ColumnReverseJustifyContentStartMinSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("FlexDirColumnReverseJustifyContentStartMinSize", sourceFolder, destinationFolder);
    }
}
