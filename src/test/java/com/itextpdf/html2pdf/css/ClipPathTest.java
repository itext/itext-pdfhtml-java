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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class ClipPathTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/ClipPathTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/ClipPathTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2946 Support clip-path CSS property
    public void simplePolygonAbsolutePointsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simplePolygonAbsolutePointsTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2946 Support clip-path CSS property
    public void simplePolygonPercentPointsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simplePolygonPercentPointsTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
