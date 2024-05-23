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
public class GridTemplateCombinedTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridTemplateCombinedTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridTemplateCombinedTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void templateCombinedBordersTest() throws IOException, InterruptedException {
        runTest("template-combined-borders");
    }

    @Test
    public void templateCombinedGridColAndRowGapTest() throws IOException, InterruptedException {
        runTest("template-combined-grid-row-col-gap");
    }

    @Test
    public void templateCombinedGridStartEndTest() throws IOException, InterruptedException {
        runTest("template-combined-grid-start-end");
    }

    @Test
    public void templateCoombinedMarginsPaddingsTest() throws IOException, InterruptedException {
        runTest("template-combined-margins-paddings");
    }

    @Test
    // TODO DEVSIX-8324
    public void templateCombinedMinMaxTest() throws IOException, InterruptedException {
        runTest("template-combined-minmax");
    }

    @Test
    // TODO DEVSIX-8324
    public void templateCombinedMixedTest() throws IOException, InterruptedException {
        runTest("template-combined-mixed");
    }

    @Test
    // TODO DEVSIX-8331
    public void templateCombinedMultiPageTest() throws IOException, InterruptedException {
        runTest("template-combined-multipage");
    }

    @Test
    public void templateCombinedNestedTest() throws IOException, InterruptedException {
        runTest("template-combined-nested");
    }

    @Test
    // TODO DEVSIX-8324
    public void templateCombinedRepeatMinMaxTest() throws IOException, InterruptedException {
        runTest("template-combined-repeat-minmax");
    }

    @Test
    public void templateCombinedRowsAutoTest() throws IOException, InterruptedException {
        runTest("template-combined-rows-auto");
    }

    @Test
    public void templateCombinedRowsFitContentAutoTest() throws IOException, InterruptedException {
        runTest("template-combined-rows-fit-content-auto");
    }
    @Test
    public void templateCombinedNoOtherPropertiesTest() throws IOException, InterruptedException {
        runTest("template-combined-without-other-props");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }
}
