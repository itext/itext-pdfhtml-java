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
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class FlexPagingTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FlexPagingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FlexPagingTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void rowNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("row-non-paging", sourceFolder, destinationFolder);
    }


    @Test
    public void columnNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-non-paging", sourceFolder, destinationFolder);
    }

    @Test
    public void columnPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging", sourceFolder, destinationFolder);
    }

    @Test
    public void columnPagingMultiColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-multi-column", sourceFolder, destinationFolder);
    }


    @Test
    public void columnReverseNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-reverse-non-paging", sourceFolder, destinationFolder);
    }

    @Test
    public void columnReversePagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-reverse-paging", sourceFolder, destinationFolder);
    }

    @Test
    public void columnReversePagingMultiColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-reverse-paging-multi-column", sourceFolder, destinationFolder);
    }

    @Test
    public void columnPagingLargeElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-large-element", sourceFolder, destinationFolder);
    }

    @Test
    public void columnPagingLargeElementFlexEndJustificationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-large-element-flex-end-justification", sourceFolder, destinationFolder);
    }

    @Test
    public void columnPagingLargeElementCenterJustificationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-large-element-center-justification", sourceFolder, destinationFolder);
    }

    @Test
    public void columnPagingLargeElementFixedHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-large-element-fixed-height", sourceFolder, destinationFolder);
    }

    @Test
    public void columnReversePagingLargeElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-reverse-paging-large-element", sourceFolder, destinationFolder);
    }

    @Test
    public void columnWrapReverseNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-wrap-reverse-non-paging", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT))
    public void columnPagingInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-in-div", sourceFolder, destinationFolder);
    }

    @Test
    public void columnPagingFixedHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-fixed-height", sourceFolder, destinationFolder);
    }

    @Test
    public void columnNoWrapPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-nowrap-paging", sourceFolder, destinationFolder);
    }

    @Test
    public void columnFlexShrinkPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-flex-shrink-paging", sourceFolder, destinationFolder);
    }

    @Test
    public void columnFlexGrowPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-flex-grow-paging", sourceFolder, destinationFolder);
    }

    @Test
    public void columnFlexGrowPaging2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-flex-grow-paging-2", sourceFolder, destinationFolder);
    }

    @Test
    public void tableInFlexOnSplitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("table-in-flex-on-split", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(
            messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 4))
    public void tableInFlexOnSplit2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("table-in-flex-on-split2", sourceFolder, destinationFolder);
    }

    @Test
    public void tableInFlexColumnOnSplitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("table-in-flex-column-on-split", sourceFolder, destinationFolder);
    }
}
