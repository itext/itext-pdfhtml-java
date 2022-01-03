/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class QuotesTest extends ExtendedHtmlConversionITextTest {
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/QuotesTest/";
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/QuotesTest/";

    @BeforeClass
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
