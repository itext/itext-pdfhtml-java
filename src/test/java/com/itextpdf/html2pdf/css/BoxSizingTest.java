/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BoxSizingTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BoxSizingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BoxSizingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void boxSizingCellContentTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellContentTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellContentTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellContentTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellContentImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellContentImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellTest01() throws IOException, InterruptedException {
        // TODO: DEVSIX-5468 update cmp file after fixing
        convertToPdfAndCompare("boxSizingCellTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellTest03() throws IOException, InterruptedException {
        // TODO: DEVSIX-5468 update cmp file after fixing
        convertToPdfAndCompare("boxSizingCellTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFloat01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFloat01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFloat02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFloat02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingRelativeWidth01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingRelativeWidth01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingRelativeWidth02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingRelativeWidth02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingRelativeWidth03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingRelativeWidth03Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDiv01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingDiv01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDivWithImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingDivWithImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDiv03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingDiv03Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDiv04Test() throws IOException, InterruptedException {
        // Inner div still doesn't fit, because it's height is increased every time page split occurs by margins
        // borders padding. Thus, if parent height was manually fixed to include child with fixed height and if
        // page split occurs - child might not fit.
        convertToPdfAndCompare("boxSizingDiv04Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingPara01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingPara01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingParaWithImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingParaWithImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingPara03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingPara03Test", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 2))
    public void boxSizingTable01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTableWithImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTableWithImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable04Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable05Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable06Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingMinMaxHeight01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingMinMaxHeight01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingInlineBlock01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingInlineBlock01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingInlineBlock02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingInlineBlock02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFormTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFormTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFormTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFormTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFormTest03() throws IOException, InterruptedException {
        // At least in chrome, borders of buttons are always included to width and height (just as with border-box)
        convertToPdfAndCompare("boxSizingFormTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingLiTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingLiTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingLiTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingLiTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingImage", sourceFolder, destinationFolder);
    }
}
