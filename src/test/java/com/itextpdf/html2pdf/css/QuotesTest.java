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

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class QuotesTest extends ExtendedHtmlConversionITextTest {
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/QuotesTest/";
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/QuotesTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void depthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("depthTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void depthTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("depthTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void depthTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("depthTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void escapedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("escapedTest", sourceFolder, destinationFolder);
    }

    @Test
    public void noQuoteTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("noQuoteTest", sourceFolder, destinationFolder);
    }

    @Test
    public void valuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("valuesTest", sourceFolder, destinationFolder);
    }

    @Test
    //attr() is not supported in quotes property in browsers
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.QUOTES_PROPERTY_INVALID),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void attrTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("attrTest", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.QUOTES_PROPERTY_INVALID, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2)
    })
    public void errorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("errorTest", sourceFolder, destinationFolder);
    }
}
