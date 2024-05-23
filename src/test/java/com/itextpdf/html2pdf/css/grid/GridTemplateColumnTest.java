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
package com.itextpdf.html2pdf.css.grid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class GridTemplateColumnTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridTemplateColumnTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridTemplateColumnTest/";


    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void templateColumnBordersTest() throws IOException, InterruptedException {
        runTest("template-cols-borders");
    }

    @Test
    public void autoRowFixedTest() throws IOException, InterruptedException {
        runTest("auto-cols-fixed");
    }

    @Test
    public void templateColumnStartEndTest() throws IOException, InterruptedException {
        runTest("template-cols-column-start-end");
    }

    @Test
    public void templateColumnWidthUnitsTest() throws IOException, InterruptedException {
        runTest("template-cols-different-width-units");
    }

    @Test
    // TODO DEVSIX-8383 (assign to the ticket about minmax&repeat&fit-content support)
    public void templateColumnFitContentTest() throws IOException, InterruptedException {
        runTest("template-cols-fit-content");
    }

    @Test
    // TODO DEVSIX-8383 (assign to the ticket about minmax&repeat&fit-content support)
    public void templateColumnFitContentAutoTest() throws IOException, InterruptedException {
        runTest("template-cols-fit-content-auto");
    }

    @Test
    public void templateColumnFrTest() throws IOException, InterruptedException {
        runTest("template-cols-fr");
    }

    @Test
    public void templateColumnGridGapTest() throws IOException, InterruptedException {
        runTest("template-cols-grid-gap");
    }

    @Test
    public void templateColumnHeightWidthTest() throws IOException, InterruptedException {
        runTest("template-cols-height-width");
    }

    @Test
    public void templateColumnMarginTest() throws IOException, InterruptedException {
        runTest("template-cols-margin");
    }

    @Test
    // TODO DEVSIX-8383 (assign to the ticket about minmax&repeat&fit-content support)
    public void templateColumnMinMaxTest() throws IOException, InterruptedException {
        runTest("template-cols-minmax");
    }

    @Test
    public void templateColumnMixedTest() throws IOException, InterruptedException {
        runTest("template-cols-mixed");
    }

    @Test
    public void templateColumnMultiPageTest() throws IOException, InterruptedException {
        runTest("template-cols-enormous-size");
    }

    @Test
    public void templateColumnNestedTest() throws IOException, InterruptedException {
        runTest("template-cols-nested");
    }

    @Test
    public void templateColumnPaddingTest() throws IOException, InterruptedException {
        runTest("template-cols-padding");
    }

    @Test
    // TODO DEVSIX-8383 (assign to the ticket about minmax&repeat&fit-content support)
    public void templateColumnRepeatTest() throws IOException, InterruptedException {
        runTest("template-cols-repeat");
    }

    @Test
    // TODO DEVSIX-8383 (assign to the ticket about minmax&repeat&fit-content support)
    public void templateColumnRepeatMinMaxTest() throws IOException, InterruptedException {
        runTest("template-cols-repeat-minmax");
    }

    @Test
    public void templateColumnColumnGapTest() throws IOException, InterruptedException {
        runTest("template-cols-column-gap");
    }

    @Test
    public void templateColumnBasicTest() throws IOException, InterruptedException {
        runTest("template-cols-without-other-props");
    }

    @Test
    public void templateColumnBasicTest2() throws IOException, InterruptedException {
        runTest("template-cols-without-other-props-2");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }
}
