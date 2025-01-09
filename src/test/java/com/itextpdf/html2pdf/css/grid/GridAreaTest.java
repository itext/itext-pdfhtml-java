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
public class GridAreaTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridAreaTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridAreaTest/";


    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void basicGridArea1Test() throws IOException, InterruptedException {
        runTest("basicGridArea1");
    }

    @Test
    public void basicGridArea2Test() throws IOException, InterruptedException {
        runTest("basicGridArea2");
    }

    @Test
    public void templateAreasBasicTest() throws IOException, InterruptedException {
        runTest("templateAreasBasic");
    }

    @Test
    public void templateAreasShorthandBasicTest() throws IOException, InterruptedException {
        runTest("templateAreasShorthandBasic");
    }

    @Test
    public void templateAreasShorthandAdvancedTest() throws IOException, InterruptedException {
        runTest("templateAreasShorthandAdvanced");
    }

    @Test
    public void gridShorthandAdvancedTest() throws IOException, InterruptedException {
        runTest("gridShorthandAdvanced");
    }

    @Test
    public void templateShorthandWithoutLineNamesTest() throws IOException, InterruptedException {
        runTest("templateShorthandWithoutLineNames");
    }

    @Test
    public void templateAreasInvalidNameTest() throws IOException, InterruptedException {
        runTest("templateAreasInvalidName");
    }

    @Test
    public void templateAreasWithDotsTest() throws IOException, InterruptedException {
        runTest("templateAreasWithDots");
    }

    @Test
    public void templateAreasSwitchedPlacesTest() throws IOException, InterruptedException {
        runTest("grid-area-switched-places");
    }

    @Test
    public void differentRowSpanTest() throws IOException, InterruptedException {
        runTest("differentRowSpanTest");
    }

    @Test
    public void borderBoxTest() throws IOException, InterruptedException {
        runTest("borderBoxTest");
    }

    @Test
    public void borderBoxTest2() throws IOException, InterruptedException {
        runTest("borderBoxTest2");
    }

    @Test
    public void differentRowSpanOnSplitTest() throws IOException, InterruptedException {
        runTest("differentRowSpanOnSplitTest");
    }

    @Test
    public void differentRowSpanOnSplitTest2() throws IOException, InterruptedException {
        runTest("differentRowSpanOnSplitTest2");
    }

    @Test
    public void differentRowSpanWithGaps50OnSplitTest() throws IOException, InterruptedException {
        runTest("differentRowSpanWithGaps50OnSplitTest");
    }

    @Test
    public void differentRowSpanWithGaps100OnSplitTest() throws IOException, InterruptedException {
        runTest("differentRowSpanWithGaps100OnSplitTest");
    }

    @Test
    public void splitOn2ndRowGapTest() throws IOException, InterruptedException {
        runTest("splitOn2ndRowGapTest");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.GRID_TEMPLATE_AREAS_IS_INVALID))
    public void invalidTemplateAreasTest() throws IOException, InterruptedException {
        runTest("invalidTemplateAreas");
    }

    @Test
    public void templateAreasStartAutoTest() throws IOException, InterruptedException {
        runTest("templateAreasStartAuto");
    }

    @Test
    public void templateAreasStartTest() throws IOException, InterruptedException {
        // Here browser result seems strange. We specified only row starts but it somehow applies to column starts also.
        // I'd expect the same result as for templateAreasStartAutoTest
        runTest("templateAreasStart");
    }

    @Test
    public void templateAreasStartEndTest() throws IOException, InterruptedException {
        runTest("templateAreasStartEnd");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
