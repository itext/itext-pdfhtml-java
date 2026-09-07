/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 Apryse Group NV
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
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextCjkFontsTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalTextCjkFontsTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/VerticalTextCjkFontsTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void cjkFontMetricsComparisonTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkFontMetricsComparison", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkFontNotoSansJpTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkFontNotoSansJp", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkFontNotoSansKrTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkFontNotoSansKr", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkFontNotoSansMongolianTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkFontNotoSansMongolian", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkFontNotoSansScTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkFontNotoSansSc", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkFontNotoSansScBoldTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkFontNotoSansScBold", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkFontNotoSerifScTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkFontNotoSerifSc", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkLineBreakWordBreakTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkLineBreakWordBreak", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkMixedAllScriptsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkMixedAllScripts", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkMixedBoldRegularChineseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkMixedBoldRegularChinese", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkMixedChineseJapaneseKoreanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkMixedChineseJapaneseKorean", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkMixedMongolianChineseLatinTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkMixedMongolianChineseLatin", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkMixedSansSerifChineseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkMixedSansSerifChinese", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkMongolianEmbeddedLatinSidewaysTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkMongolianEmbeddedLatinSideways", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkPunctuationOrientationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkPunctuationOrientation", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 4)
    })
    public void cjkRubyWithFontTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkRubyWithFont", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkTextCombineUprightWithFontTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkTextCombineUprightWithFont", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkTextEmphasisMarksTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkTextEmphasisMarks", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cjkTextOrientationMixedVsUprightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cjkTextOrientationMixedVsUpright", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
