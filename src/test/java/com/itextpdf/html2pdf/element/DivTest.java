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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class DivTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/DivTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/DivTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void divTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("divTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("divTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("divTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("divTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divInTablePercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divInTablePercent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divInTableDataCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divInTableDataCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divColumnCountTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divColumnCount", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    public void helloDivDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_div", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

}
