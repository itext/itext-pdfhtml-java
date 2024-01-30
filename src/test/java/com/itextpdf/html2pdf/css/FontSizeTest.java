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
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FontSizeTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FontSizeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FontSizeTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void fontSize01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fontSizeTest01", sourceFolder, destinationFolder);}

    @Test
    public void fontSize02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fontSizeTest02", sourceFolder, destinationFolder);}

    @Test
    public void fontAbsoluteKeywords() throws IOException, InterruptedException {
        convertToPdfAndCompare("fontAbsoluteKeywords", sourceFolder, destinationFolder);
    }

    @Test
    public void fontRelativeKeywords() throws IOException, InterruptedException {
        convertToPdfAndCompare("fontRelativeKeywords", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void spacesInFontSizeValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spacesInFontSizeValueTest", sourceFolder, destinationFolder);
    }

    @Test
    public void defaultFontDiffFontSizeSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("defaultFontDiffFontSizeSpan", sourceFolder, destinationFolder);
    }
}
