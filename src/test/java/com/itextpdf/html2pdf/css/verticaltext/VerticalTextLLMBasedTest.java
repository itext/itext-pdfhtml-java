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
package com.itextpdf.html2pdf.css.verticaltext;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;


import java.io.IOException;

import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class VerticalTextLLMBasedTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/verticaltext/VerticalTextLLMBasedTest/";
    public static final String DEST_FOLDER = "./target/test/com/itextpdf/html2pdf/css/verticaltext/VerticalTextLLMBasedTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DEST_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseKinsokuLineBreakingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseKinsokuLineBreaking", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseHangingPunctuationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseHangingPunctuation", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseCjkLatinAutospaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseCjkLatinAutospace", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapanesePunctuationSpacingPaltTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapanesePunctuationSpacingPalt", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseWidowsOrphansPaginationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseWidowsOrphansPagination", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseUnbreakableAlphanumericSequenceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseUnbreakableAlphanumericSequence", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 6)})
    public void verticalJapaneseMonoRubyVsGroupRubyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseMonoRubyVsGroupRuby", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalKoreanWordBreakKeepAllTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalKoreanWordBreakKeepAll", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 12)})
    public void verticalChineseBopomofoRubyPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalChineseBopomofoRubyPosition", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalChineseEmphasisMarkPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalChineseEmphasisMarkPosition", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseConsecutivePunctuationCompressionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseConsecutivePunctuationCompression", SOURCE_FOLDER, DEST_FOLDER, false);
    }
}
