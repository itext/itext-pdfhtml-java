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
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class GridTemplateElementPropertyContainerTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridTemplateElementPropertyContainerTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridTemplateElementPropertyContainerTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void backgroundItemsOnTopOfBackgroundGridTest() throws IOException, InterruptedException {
        runTest("backgroundItemsOnTopOfBackgroundGrid");
    }


    @Test
    public void paddingAllSidesTest() throws IOException, InterruptedException {
        runTest("paddingAll");
    }

    @Test
    public void paddingAllSidesEmptyDivTest() throws IOException, InterruptedException {
        runTest("padding_all_with_empty_div");
    }

    @Test
    @LogMessages(messages = {
            @com.itextpdf.test.annotations.LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1)
    })
    public void paddingAllToBigForWidthTest() throws IOException, InterruptedException {
        runTest("paddingAllToBigForWidth");
    }

    @Test
    @Ignore("DEVSIX-TODO")
    public void paddingOverflowX() throws IOException, InterruptedException {
        runTest("padding_overflow_x");
    }

    @Test
    public void basicBorderTest() throws IOException, InterruptedException {
        runTest("basic_border");
    }


    @Test
    public void borderBigTest() throws IOException, InterruptedException {
        runTest("border_big");
    }

    @Test
    public void boderWithOverflowXTest() throws IOException, InterruptedException {
        runTest("border_big_with_overflow_x");
    }

    @Test
    public void marginAllTest() throws IOException, InterruptedException {
        runTest("margin_all");
    }

    @Test
    public void marginAllEmptyTest() throws IOException, InterruptedException {
        runTest("margin_all_empty");
    }

    @Test
    public void marginAllWithEmptyDIV() throws IOException, InterruptedException {
        runTest("margin_all_with_empty_div");
    }


    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER).setCssGridEnabled(true));
    }

}
