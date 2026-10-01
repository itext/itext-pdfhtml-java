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
public class VerticalTextCommonExamplesTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/verticaltext/VerticalTextCommonExamplesTest/";
    public static final String DEST_FOLDER = "./target/test/com/itextpdf/html2pdf/css/verticaltext/VerticalTextCommonExamplesTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DEST_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 32)})
    public void verticalJapaneseNovelExcerptWithRubyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseNovelExcerptWithRuby", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 26)})
    public void verticalJapaneseNovelWagahaiTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseNovelWagahai", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 24)})
    public void verticalJapaneseNovelInlineFeaturesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseNovelInlineFeatures", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseHaikuTanzakuTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseHaikuTanzaku", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalChineseTangPoemTraditionalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalChineseTangPoemTraditional", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalChineseSpringFestivalCoupletsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalChineseSpringFestivalCouplets", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseCertificateOfAppreciationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseCertificateOfAppreciation", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 4)})
    public void verticalJapaneseBusinessCardTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseBusinessCard", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalKoreanSijoPoemsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalKoreanSijoPoems", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 3)})
    public void verticalJapaneseRestaurantMenuTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseRestaurantMenu", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2)})
    public void verticalJapaneseNewspaperMultiColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseNewspaperMultiColumn", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 108)})
    public void verticalJapaneseAozoraKumoNoItoTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseAozoraKumoNoIto", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 4)})
    public void verticalJapaneseRecipeOrderedListTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseRecipeOrderedList", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 4)})
    public void verticalJapaneseSchoolNewsletterTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseSchoolNewsletterTable", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 3)})
    public void verticalJapaneseBlogPostWithFigureTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseBlogPostWithFigure", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    //Mixed baseline / upright
    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2)})
    public void verticalChineseNewsWithLatinInitialismsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalChineseNewsWithLatinInitialisms", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT, count = 2)})
    public void verticalJapaneseMultiPageDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseMultiPageDocument", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseTcyLatinAbbreviationAndDigitsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseTcyLatinAbbreviationAndDigits", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2)})
    public void verticalJapaneseTcyDigitsCountVariantsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseTcyDigitsCountVariants", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseTcyLongRunCompressionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseTcyLongRunCompression", SOURCE_FOLDER, DEST_FOLDER, false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)})
    public void verticalJapaneseTcyGeneratedContentFootnotesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalJapaneseTcyGeneratedContentFootnotes", SOURCE_FOLDER, DEST_FOLDER, false);
    }
}
