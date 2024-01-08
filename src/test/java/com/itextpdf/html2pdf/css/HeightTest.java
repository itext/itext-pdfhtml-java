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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class HeightTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/HeightTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/HeightTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void heightTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest05", sourceFolder, destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-1007")
    public void heightTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest06", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest07() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest07", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest08() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest08", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest09() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest09", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest10() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest10", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest11() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest11", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest12() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest12", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest13() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest13", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest14() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest14", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest15() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest15", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest16() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest16", sourceFolder, destinationFolder);
    }

    @Test
    public void heightTest17() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightTest17", sourceFolder, destinationFolder);
    }

    @Test
    public void heightWithCollapsingMarginsTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightWithCollapsingMarginsTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void heightWithCollapsingMarginsTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightWithCollapsingMarginsTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void heightWithCollapsingMarginsTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightWithCollapsingMarginsTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void heightWithCollapsingMarginsTest04() throws IOException, InterruptedException {
        // second paragraph should not be drawn in pdf, as it doesn't fit with it's margins
        convertToPdfAndCompare("heightWithCollapsingMarginsTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void heightWithCollapsingMarginsTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightWithCollapsingMarginsTest05", sourceFolder, destinationFolder);
    }

    @Test
    public void heightLargerThanMinHeight01() throws IOException, InterruptedException {
        // TODO DEVSIX-1895: height differs from the browser rendering due to incorrect resolving of max-height/height properties
        convertToPdfAndCompare("heightLargerThanMinHeight01", sourceFolder, destinationFolder);
    }

    @Test
    public void heightLesserThanMaxHeight01() throws IOException, InterruptedException {
        // TODO DEVSIX-1895: height differs from the browser rendering due to incorrect resolving of max-height/height properties
        convertToPdfAndCompare("heightLesserThanMaxHeight01", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-6078 print log message about invalid height
    public void heightNumberWithoutUnitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("heightNumberWithoutUnit", sourceFolder, destinationFolder);
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    @Test
    public void emptyTableOnCustomPageSizedDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyTableOnCustomPageSizedDocument", sourceFolder, destinationFolder);
    }
}
