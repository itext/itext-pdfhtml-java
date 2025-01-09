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
package com.itextpdf.html2pdf.css.media.page.fix_dimension;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.IOException;

@Tag("IntegrationTest")
public class PageMarginBoxFixDimensionTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/fix_dimension/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/fix_dimension/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyLeftFixPxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPx", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixPtTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPt", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixPercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPercent", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixInTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixIn", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixCmTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixCm", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixMmTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixMm", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixPcTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPc", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixEmTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixEm", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixExTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixEx", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyRightFixPercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyRightFixPercent", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyCenterFixPercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyCenterFixPercent", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixCenterAndRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixLeftAndFixCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixLeftAndFixCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixLeftAndFixCenterAndFixRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixLeftAndFixCenterAndFixRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyFixTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyFixTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyFixCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyFixCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyFixBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyFixBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndFixCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndFixCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndFixBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndFixBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftFixCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftFixCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftFixTopAndFixCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftFixTopAndFixCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void pageMarginFont() throws IOException, InterruptedException {
        convertToPdfAndCompare("pageMarginFont", sourceFolder, destinationFolder);
    }
}
