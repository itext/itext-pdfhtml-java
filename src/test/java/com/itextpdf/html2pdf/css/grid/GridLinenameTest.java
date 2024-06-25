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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import java.io.IOException;

@Tag("IntegrationTest")
public class GridLinenameTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridLinenameTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridLinenameTest/";


    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void linenamesCombinedTest() throws IOException, InterruptedException {
        runTest("linenamesCombined");
    }

    @Test
    public void linenameSpanTest() throws IOException, InterruptedException {
        runTest("linenameSpan");
    }

    @Test
    public void linenameNthTest() throws IOException, InterruptedException {
        runTest("linenameNth");
    }

    @Test
    public void duplicateLineNamesTest() throws IOException, InterruptedException {
        runTest("duplicateLineNames");
    }

    @Test
    public void linenameGridAreaTest() throws IOException, InterruptedException {
        runTest("linenameGridArea");
    }

    @Test
    public void customIndentSpanTest() throws IOException, InterruptedException {
        runTest("customIndentSpan");
    }

    @Test
    public void customIndentTrickySpanTest() throws IOException, InterruptedException {
        runTest("customIndentTrickySpan");
    }

    @Test
    public void templateAreasNamesTest() throws IOException, InterruptedException {
        runTest("templateAreasNames");
    }

    @Test
    public void linenameRepeatTest() throws IOException, InterruptedException {
        runTest("linenameRepeat");
    }

    @Test
    @LogMessages(messages = @LogMessage(
            messageTemplate = Html2PdfLogMessageConstant.LINENAMES_ARE_NOT_SUPPORTED_WITHIN_AUTO_REPEAT))
    public void linenameAutoRepeatTest() throws IOException, InterruptedException {
        runTest("linenameAutoRepeat");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }
}
