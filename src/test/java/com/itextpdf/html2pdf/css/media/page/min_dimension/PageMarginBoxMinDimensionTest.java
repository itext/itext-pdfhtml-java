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
package com.itextpdf.html2pdf.css.media.page.min_dimension;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class PageMarginBoxMinDimensionTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/min_dimension/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/min_dimension/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyMinLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMinLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void topMinCenterAndRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topMinLeftAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topMinLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topMinLeftAndMinCenterAndMinRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMinLeftAndMinCenterAndMinRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyMinTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMinTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMinCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMinCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMinBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMinBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMinCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMinCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMinTopAndMinCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMinTopAndMinCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void pageMarginFont() throws IOException, InterruptedException {
        convertToPdfAndCompare("pageMarginFont", sourceFolder, destinationFolder);
    }
}
