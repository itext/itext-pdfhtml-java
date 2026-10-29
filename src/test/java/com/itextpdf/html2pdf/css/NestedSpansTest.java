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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class NestedSpansTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/NestedSpansTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/NestedSpansTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void nestedSpansDifferentNestingLevelsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedSpansDifferentNestingLevels", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void nestedSpansDifferentNestingLevelsNoFloatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedSpansDifferentNestingLevelsNoFloat", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void nestedSpansPropertiesLevelsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedSpansPropertiesLevels", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentPaddingOverrideTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentPaddingOverrideTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentCornerRadiusSplitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentCornerRadiusSplitTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentBorderSidePreserveTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentBorderSidePreserveTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentMarginPaddingCombineOverrideTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentMarginPaddingCombineOverrideTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanParentDifferentTopBottomMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanParentDifferentTopBottomMarginTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void childSpanMarginDoubledTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("childSpanMarginDoubled", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void parentPaddingIncreasesChildBackgroundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("parentPaddingIncreasesChildBackgroundTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
