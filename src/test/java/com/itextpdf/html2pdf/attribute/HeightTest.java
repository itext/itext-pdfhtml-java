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
package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class HeightTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/attribute/HeightTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/attribute/HeightTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-2554 Html2Pdf: Image with negative height is missed in output pdf
    public void negativeHeightValueInImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("negativeHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
