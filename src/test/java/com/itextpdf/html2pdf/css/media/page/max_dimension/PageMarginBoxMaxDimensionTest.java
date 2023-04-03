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
package com.itextpdf.html2pdf.css.media.page.max_dimension;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class PageMarginBoxMaxDimensionTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/max_dimension/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/max_dimension/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyMaxLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMaxLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyMaxRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMaxRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyMaxCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMaxCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxCenterAndRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxLeftAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxLeftAndMaxCenterAndMaxRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxLeftAndMaxCenterAndMaxRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyMaxTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMaxTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyMaxCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMaxCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyMaxBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMaxBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMaxCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMaxCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMaxBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMaxBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMaxCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMaxCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMaxTopAndMaxCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMaxTopAndMaxCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void pageMarginFont() throws IOException, InterruptedException {
        convertToPdfAndCompare("pageMarginFont", sourceFolder, destinationFolder);
    }
}
