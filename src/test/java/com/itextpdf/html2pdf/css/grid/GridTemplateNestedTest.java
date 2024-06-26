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
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class GridTemplateNestedTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid"
            + "/GridTemplateNestedTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid"
            + "/GridTemplateNestedTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }


    @Test
    public void templateNestedAreasTest() throws IOException, InterruptedException {
        runTest("grid-nested-areas");
    }

    @Test
    public void templateNestedAreasWithBorderTest() throws IOException, InterruptedException {
        runTest("grid-nested-areas-with-border");
    }

    @Disabled("DEVSIX-8423")
    @Test
    public void templateNestedArticlesTest() throws IOException, InterruptedException {
        runTest("grid-nested-articles");
    }

    @Test
    public void templateNestedFormsTest() throws IOException, InterruptedException {
        runTest("grid-nested-forms");
    }

    @Test
    public void templateNestedGridTest() throws IOException, InterruptedException {
        runTest("grid-nested-grid");
    }

    @Test
    public void templateNestedListsTest() throws IOException, InterruptedException {
        runTest("grid-nested-lists");
    }

    @Test
    public void templateNestedListsOddEvenTest() throws IOException, InterruptedException {
        runTest("grid-nested-lists-odd-even");
    }

    @Test
    public void templateNestedMixedContentTest() throws IOException, InterruptedException {
        runTest("grid-nested-mixed-content");
    }

    @Disabled("DEVSIX-8423")
    @Test
    public void templateNestedParagraphsTest() throws IOException, InterruptedException {
        runTest("grid-nested-paragraphs");
    }

    @Test
    public void templateNestedImagesTest() throws IOException, InterruptedException {
        runTest("grid-nested-images");
    }

    @Test
    public void templateNestedTableTest() throws IOException, InterruptedException {
        runTest("grid-nested-table");
    }

    @Test
    public void templateNestedTableNestedGridTest() throws IOException, InterruptedException {
        runTest("grid-nested-table-nested-grid");
    }

    @Test
    public void templateNestedTableMixedContentTest() throws IOException, InterruptedException {
        runTest("grid-nested-table-with-mixed-content");
    }

    @Disabled("DEVSIX-8423")
    @Test
    public void templateNested2LevelsWithAreasTest() throws IOException, InterruptedException {
        runTest("grid-nested-2-levels-areas");
    }

    @Disabled("DEVSIX-8423")
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT, count = 4)
    })
    @Test
    public void templateNested3LevelsFormsTest() throws IOException, InterruptedException {
        runTest("grid-nested-3-forms");
    }

    @Disabled("DEVSIX-8423")
    @Test
    public void templateNested3LevelsTest() throws IOException, InterruptedException {
        runTest("grid-nested-3-levels");
    }

    @Disabled("DEVSIX-8423")
    @Test
    public void templateNested3LevelsMultipleTest() throws IOException, InterruptedException {
        runTest("grid-nested-3-levels-multiple");
    }

    @Test
    public void templateNested3LevelsTablesTest() throws IOException, InterruptedException {
        runTest("grid-nested-3-levels-tables");
    }


    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }
}
