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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class CounterTest extends ExtendedHtmlConversionITextTest{

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/CounterTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/CounterTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void counter01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("counter01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pageCounter01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pageCounter02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pageCounter03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pageCounter04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pageCounterSpacesInDeclarationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter_spaces_in_declaration", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 2, logLevel = LogLevelConstants.WARN)
    })
    public void pageCounterAndKeepTogetherTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pageCounterAndKeepTogetherTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO fix cmp after DEVSIX-5509 is done; currently total page count is incorrect
    public void pageCounterWithTrimmedLastPageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter_with_trimmed_last_page", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
