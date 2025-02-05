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
package com.itextpdf.html2pdf.css.grid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.layout.exceptions.LayoutExceptionMessageConstant;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class GridItemPlacementTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridItemPlacementTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridItemPlacementTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void colStartEnd1Test() throws IOException, InterruptedException {
        runTest("colStartEnd1");
    }

    @Test
    public void colStartEnd2Test() throws IOException, InterruptedException {
        runTest("colStartEnd2");
    }

    @Test
    public void colStartEnd3Test() throws IOException, InterruptedException {
        runTest("colStartEnd3");
    }

    @Test
    public void colStartEnd4Test() throws IOException, InterruptedException {
        runTest("colStartEnd4");
    }

    @Test
    public void colStartEnd5Test() throws IOException, InterruptedException {
        runTest("colStartEnd5");
    }

    @Test
    public void fewCellsPlacement1Test() throws IOException, InterruptedException {
        runTest("fewCellsPlacement1");
    }

    @Test
    public void fewCellsPlacement2Test() throws IOException, InterruptedException {
        runTest("fewCellsPlacement2");
    }

    @Test
    public void fewCellsPlacement3Test() throws IOException, InterruptedException {
        runTest("fewCellsPlacement3");
    }

    @Test
    public void fewCellsPlacement4Test() throws IOException, InterruptedException {
        runTest("fewCellsPlacement4");
    }

    @Test
    public void fewCellsPlacement5Test() throws IOException, InterruptedException {
        runTest("fewCellsPlacement5");
    }

    @Test
    public void fewCellsPlacement6Test() throws IOException, InterruptedException {
        runTest("fewCellsPlacement6");
    }

    @Test
    public void fewCellsPlacement7Test() throws IOException, InterruptedException {
        runTest("fewCellsPlacement7");
    }

    @Test
    public void rowStartEnd1Test() throws IOException, InterruptedException {
        runTest("rowStartEnd1");
    }

    @Test
    public void rowStartEnd2Test() throws IOException, InterruptedException {
        runTest("rowStartEnd2");
    }

    @Test
    public void rowStartEnd3Test() throws IOException, InterruptedException {
        runTest("rowStartEnd3");
    }

    @Test
    public void rowStartEnd4Test() throws IOException, InterruptedException {
        runTest("rowStartEnd4");
    }

    @Test
    public void twoColumnSpans1Test() throws IOException, InterruptedException {
        runTest("twoColumnSpans1");
    }

    @Test
    public void twoColumnSpans2Test() throws IOException, InterruptedException {
        runTest("twoColumnSpans2");
    }

    @Test
    public void twoColumnSpans3Test() throws IOException, InterruptedException {
        runTest("twoColumnSpans3");
    }

    @Test
    public void twoRowSpans1Test() throws IOException, InterruptedException {
        runTest("twoRowSpans1");
    }

    @Test
    public void twoRowSpans2Test() throws IOException, InterruptedException {
        runTest("twoRowSpans2");
    }

    @Test
    public void twoRowSpans3Test() throws IOException, InterruptedException {
        runTest("twoRowSpans3");
    }

    @Test
    public void spanToNegativeStartTest() throws IOException, InterruptedException {
        runTest("spanToNegativeStartTest");
    }

    @Test
    public void spanToNegativeStartWithExplicitTemplatesTest() throws IOException, InterruptedException {
        runTest("spanToNegativeStartWithExplicitTemplatesTest");
    }

    @Test
    public void spanToNegativeStartWithoutTemplatesTest() throws IOException, InterruptedException {
        runTest("spanToNegativeStartWithoutTemplatesTest");
    }

    @Test
    public void spanToNegativeStartWithoutTemplatesTest2() throws IOException, InterruptedException {
        runTest("spanToNegativeStartWithoutTemplatesTest2");
    }

    @Test
    public void spanToNegativeStartWithSingleTemplateTest() throws IOException, InterruptedException {
        runTest("spanToNegativeStartWithSingleTemplateTest");
    }

    @Test
    public void columnSpanExpandsStartToNegativeTest() throws IOException, InterruptedException {
        runTest("columnSpanExpandsStartToNegativeTest");
    }

    @Test
    public void negativeIndexOutOfTemplateTest() throws IOException, InterruptedException {
        runTest("negativeIndexOutOfTemplateTest");
    }

    @Test
    public void negativeIndexWithImplicitLinesTest() throws IOException, InterruptedException {
        runTest("negativeIndexWithImplicitLinesTest");
    }

    @Test
    public void negativeIndexWithoutTemplateTest() throws IOException, InterruptedException {
        runTest("negativeIndexWithoutTemplateTest");
    }

    @Test
    public void negativeIndexShorthandTest() throws IOException, InterruptedException {
        runTest("negativeIndexShorthandTest");
    }

    @Test
    public void negativeAndPositiveIndexShorthandTest() throws IOException, InterruptedException {
        runTest("negativeAndPositiveIndexShorthandTest");
    }

    @Test
    public void spanToNegativeIndexWithoutTemplateTest() throws IOException, InterruptedException {
        runTest("spanToNegativeIndexWithoutTemplateTest");
    }

    @Test
    public void noTemplate1Test() throws IOException, InterruptedException {
        runTest("noTemplate1");
    }

    @Test
    public void noTemplate2Test() throws IOException, InterruptedException {
        runTest("noTemplate2");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName, SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
