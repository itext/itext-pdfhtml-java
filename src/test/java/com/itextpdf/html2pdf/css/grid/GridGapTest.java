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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class GridGapTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridGapTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridGapTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void templateColsGapTest() throws IOException, InterruptedException {
        runTest("template_cols_gap");
    }

    @Test
    public void templateColsColGapTest() throws IOException, InterruptedException {
        runTest("template_cols_col_gap");
    }

    @Test
    public void template_rowsRowGapTest() throws IOException, InterruptedException {
        runTest("template_rows_row_gap");
    }

    @Test
    public void templateRowsGapTest() throws IOException, InterruptedException {
        runTest("template_rows_gap");
    }

    @Test
    public void templateRowsColsGapTest() throws IOException, InterruptedException {
        runTest("template_rows_cols_gap");
    }

    @Test
    public void templateRowsColsAutoRowsGapTest() throws IOException, InterruptedException {
        runTest("template_rows_cols_auto_rows_gap");
    }

    @Test
    public void templateRowsColsAutoColsGapTest() throws IOException, InterruptedException {
        runTest("template_rows_cols_auto_cols_gap");
    }

    @Test
    public void templateRowsColsBigCellGapTest1() throws IOException, InterruptedException {
        runTest("template_rows_cols_big_cell_gap_1");
    }

    @Test
    public void templateRowsColsBigCellGapTest2() throws IOException, InterruptedException {
        runTest("template_rows_cols_big_cell_gap_2");
    }

    @Test
    public void templateRowsColsVertCellGapTest1() throws IOException, InterruptedException {
        runTest("template_rows_cols_vert_cell_gap_1");
    }

    @Test
    public void templateRowsColsVertCellGapTest2() throws IOException, InterruptedException {
        runTest("template_rows_cols_vert_cell_gap_2");
    }

    @Test
    public void templateRowsColsHorzCellGapTest1() throws IOException, InterruptedException {
        runTest("template_rows_cols_horz_cell_gap_1");
    }

    @Test
    public void templateRowsColsHorzCellGapTest2() throws IOException, InterruptedException {
        runTest("template_rows_cols_horz_cell_gap_2");
    }

    @Test
    public void templateRowsColsFewBigCellGapTest1() throws IOException, InterruptedException {
        runTest("template_rows_cols_few_big_cell_gap_1");
    }

    @Test
    public void templateRowsColsFewBigCellGapTest2() throws IOException, InterruptedException {
        runTest("template_rows_cols_few_big_cell_gap_2");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void diffUnitsTest() throws IOException, InterruptedException {
        runTest("diff_units");
    }

    @Test
    public void floatGapValueTest() throws IOException, InterruptedException {
        runTest("float_gap_value");
    }

    @Test
    public void largeColGapValueTest() throws IOException, InterruptedException {
        runTest("large_col_gap_value");
    }

    @Test
    public void largeRowGapValueTest() throws IOException, InterruptedException {
        runTest("large_row_gap_value");
    }

    @Test
    public void largeGapValueTest() throws IOException, InterruptedException {
        runTest("large_gap_value");
    }

    @Test
    public void marginTest() throws IOException, InterruptedException {
        runTest("margin");
    }

    @Test
    public void paddingTest() throws IOException, InterruptedException {
        runTest("padding");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void negativeColGapValueTest() throws IOException, InterruptedException {
        runTest("negative_col_gap_value");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void negativeRowGapValueTest() throws IOException, InterruptedException {
        runTest("negative_row_gap_value");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void negativeGapValueTest() throws IOException, InterruptedException {
        runTest("negative_gap_value");
    }

    @Test
    public void smallValuesGapTest() throws IOException, InterruptedException {
        runTest("small_values_gap");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName, SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }
}
