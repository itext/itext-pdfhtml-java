/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 Apryse Group NV
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

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class VerticalTextAreaTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalTextAreaTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/VerticalTextAreaTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void divTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("div", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divs", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 Inline mode positioning
    //TODO DEVSIX-10168 Grid mode sizing
    //TODO DEVSIX-10168 Table mode sizing
    public void divDisplayModesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divDisplayModes", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("p", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void psTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("ps", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void psInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("psInDiv", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 positioning problems like with inline mode
    public void spansInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spansInDiv", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO minor borders should not be closed on bottom and top for line splited spans
    public void spansInDiv2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("spansInDiv2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // Height of body is ignored in horizontal and vertical modes.
    public void bodyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("body", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 paragraphs are not wide enough
    public void flexPsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexPs", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-10168 Sizing of divs and flex container
    public void flexDivsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexDivs", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexDivs2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexDivs2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
