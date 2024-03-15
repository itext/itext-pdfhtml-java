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
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class DirectionTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/DirectionTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/DirectionTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 2, logLevel =
                    LogLevelConstants.WARN)
    })
    public void simpleLtrDocTest() throws IOException {
        Assert.assertTrue(getTextFromDocument(convertToHtmlDocument("SimpleLtrDoc"),
                1).contains("123456789."));
    }

    @Test
    public void simpleLtrElementDocTest() throws IOException {
        Assert.assertTrue(getTextFromDocument(convertToHtmlDocument("SimpleLtrElementDoc"),
                1).contains("123456789."));
    }

    //TODO DEVSIX-1920: RTL ignored. Change test after fix
    @Test
    public void simpleRtlElementDocTest() throws IOException {
        Assert.assertFalse(getTextFromDocument(convertToHtmlDocument("SimpleRtlElementDoc"),
                1).contains(".Right to left text"));
    }

    //TODO DEVSIX-2437 : Change test after fix
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 4, logLevel =
                    LogLevelConstants.WARN)
    })
    public void ltrInRtlDocTest() throws IOException {
        Assert.assertFalse(getTextFromDocument(convertToHtmlDocument("LtrInRtlDoc"),
                1).contains("!Right to left text"));
    }

    //TODO DEVSIX-2437 : Change test after fix
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 4, logLevel =
                    LogLevelConstants.WARN)
    })
    public void rtlInLtrDocTest() throws IOException {
        Assert.assertFalse(getTextFromDocument(convertToHtmlDocument("RtlInLtrDoc"),
                1).contains("!Right to left text"));
    }

    //TODO DEVSIX-3069: Change test after fix
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 34, logLevel =
                    LogLevelConstants.WARN),
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH,
                    count = 1, logLevel =
                    LogLevelConstants.WARN)
    })
    public void bigTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("TooLargeTable", SOURCE_FOLDER, DESTINATION_FOLDER);
    }


    private PdfDocument convertToHtmlDocument(String fileName) throws IOException {
        String sourceHtml = SOURCE_FOLDER + fileName + ".html";
        String destPdf = DESTINATION_FOLDER + fileName + ".pdf";
        HtmlConverter.convertToPdf(new File(sourceHtml), new File(destPdf));
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(sourceHtml) + "\n"
                + "Out pdf: "+ UrlUtil.getNormalizedFileUriString(destPdf));
        return new PdfDocument(new PdfReader(destPdf));
    }

    private String getTextFromDocument(PdfDocument document, int pageNum) {
        return PdfTextExtractor.getTextFromPage(document.getPage(pageNum));
    }
}
