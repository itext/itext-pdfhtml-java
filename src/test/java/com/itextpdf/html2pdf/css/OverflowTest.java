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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OverflowTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/OverflowTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/OverflowTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void divOverflowXOverflowYHiddenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divOverflowXOverflowYHidden", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphOverflowVisibleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphOverflowVisible", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divOverflowHiddenParagraphOverflowVisibleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divOverflowHiddenParagraphOverflowVisible", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divOverflowXOverflowYVisibleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divOverflowXOverflowYVisible", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void tableOverflowXOverflowYVisibleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableOverflowXOverflowYVisible", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-5208 Overflown content should be placed over the content of backgrounds and borders of other elements
    public void paragraphOverflowXOverflowYVisibleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphOverflowXOverflowYVisible", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-5208 Overflown content should be placed over the content of backgrounds and borders of other elements
    public void ulOverflowXOverflowYVisibleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("ulOverflowXOverflowYVisible", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void tableOverflowHiddenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableOverflowHidden", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divOverflowScrollTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divOverflowScroll", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divOverflowAutoTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divOverflowAuto", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-5211 Overflown due to 'overflow: visible' content should be wrapped to the next page
    public void overflowVisibleContentShouldBeSplitBetweenPagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("overflowVisibleContentShouldBeSplitBetweenPages", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void overflowAndAlignment01() throws IOException, InterruptedException {
        convertToPdfAndCompare("overflowAndAlignment01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void overflowAndAlignment02() throws IOException, InterruptedException {
        convertToPdfAndCompare("overflowAndAlignment02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayOverflowAutoScroll() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayOverflowAutoScroll", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-5212 CSS parsing: implement correct handling of css tokens with escaped code point
    public void overflowYVisibleOverflowXAllValuesTest() throws IOException, InterruptedException {
        runTest("overflowYVisibleOverflowXAllValues", new PageSize(1200, 1400));
    }

    @Test
    //TODO DEVSIX-5212 CSS parsing: implement correct handling of css tokens with escaped code point
    public void overflowYHiddenOverflowXAllValuesTest() throws IOException, InterruptedException {
        runTest("overflowYHiddenOverflowXAllValues", new PageSize(1200, 1400));
    }

    @Test
    //TODO DEVSIX-5212 CSS parsing: implement correct handling of css tokens with escaped code point
    public void overflowYScrollOverflowXAllValuesTest() throws IOException, InterruptedException {
        runTest("overflowYScrollOverflowXAllValues", new PageSize(1200, 1400));
    }

    @Test
    //TODO DEVSIX-5212 CSS parsing: implement correct handling of css tokens with escaped code point
    public void overflowYAutoOverflowXAllValues() throws IOException, InterruptedException {
        runTest("overflowYAutoOverflowXAllValues", new PageSize(1200, 1400));
    }

    private void runTest(String testName, PageSize pageSize) throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + testName + ".pdf"));
        if (null != pageSize) {
            pdfDocument.setDefaultPageSize(pageSize);
        }
        HtmlConverter.convertToPdf(new FileInputStream(SOURCE_FOLDER + testName + ".html"), pdfDocument,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(SOURCE_FOLDER + testName + ".html") + "\n");
        Assert.assertNull(new CompareTool()
                .compareByContent(DESTINATION_FOLDER + testName + ".pdf", SOURCE_FOLDER + "cmp_" + testName + ".pdf",
                        DESTINATION_FOLDER, "diff_" + testName));
    }
}
