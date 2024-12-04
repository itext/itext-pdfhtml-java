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
package com.itextpdf.html2pdf;

import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

// Actually the results are invalid because there is no pdfCalligraph.
@Tag("IntegrationTest")
public class FontProviderTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/FontProviderTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/FontProviderTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 4)
    })
    public void hebrewTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "hebrew.html"), new File(DESTINATION_FOLDER + "hebrew.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "hebrew.pdf", SOURCE_FOLDER + "cmp_hebrew.pdf",
                DESTINATION_FOLDER, "diffHebrew_"));
    }

    @Test
    public void devanagariTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "devanagari.html"), new File(DESTINATION_FOLDER + "devanagari.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "devanagari.pdf", SOURCE_FOLDER + "cmp_devanagari.pdf",
                DESTINATION_FOLDER, "diffDevanagari_"));
    }

    @Test
    //For more specific tests see FontSelectorTimesFontTest in html2pdf and FontSelectorHelveticaFontTest in html2pdf-private
    public void convertStandardFonts() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "convertStandardFonts.html"), new File(DESTINATION_FOLDER + "convertStandardFonts.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "convertStandardFonts.pdf", SOURCE_FOLDER
                + "cmp_convertStandardFonts.pdf", DESTINATION_FOLDER, "difffontstand_"));
    }

    @Test
    public void notoSansMonoItalicTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "notoSansMonoItalic.html"), new File(DESTINATION_FOLDER + "notoSansMonoItalic.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "notoSansMonoItalic.pdf", SOURCE_FOLDER
                + "cmp_notoSansMonoItalic.pdf", DESTINATION_FOLDER, "diffnotoSansMonoItalic_"));

    }

    @Test
    public void notoSansMonoBoldItalicTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "notoSansMonoBoldItalic.html"), new File(
                DESTINATION_FOLDER + "notoSansMonoBoldItalic.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "notoSansMonoBoldItalic.pdf", SOURCE_FOLDER
                + "cmp_notoSansMonoBoldItalic.pdf", DESTINATION_FOLDER, "diffnotoSansMonoBoldItalic_"));

    }

    @Test
    // TODO: DEVSIX-4017 (Combination of default and pdfCalligraph fonts with italic style and '"courier new", courier,
    // monospace' family reproduces comparator exception. Update test after fixing.)
    public void comparatorErrorTest() throws IOException, InterruptedException {
        ConverterProperties properties = new ConverterProperties();

        FontProvider pro = new BasicFontProvider();
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansArabic-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansArabic-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansGurmukhi-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansGurmukhi-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansMyanmar-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansMyanmar-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansOriya-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSansOriya-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifBengali-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifBengali-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifDevanagari-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifDevanagari-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifGujarati-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifGujarati-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifHebrew-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifHebrew-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifKannada-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifKannada-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifKhmer-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifKhmer-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifMalayalam-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifMalayalam-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifMyanmar-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifMyanmar-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifTamil-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifTamil-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifTelugu-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifTelugu-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifThai-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(SOURCE_FOLDER + "NotoSerifThai-Bold.ttf"));

        properties.setFontProvider(pro);

        boolean isExceptionThrown = false;
        try {
            HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "comparatorError.html"),
                    new File(DESTINATION_FOLDER + "comparatorError.pdf"), properties);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Comparison method violates its general contract!", e.getMessage());
            isExceptionThrown = true;
        }

        if (!isExceptionThrown) {
            Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "comparatorError.pdf",
                    SOURCE_FOLDER + "cmp_comparatorError.pdf", DESTINATION_FOLDER));
        }
    }

    @Test
    public void differentFontFamiliesTest() throws IOException, InterruptedException {
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, false, false);
        fontProvider.addDirectory(SOURCE_FOLDER + "Lato_fonts");
        properties.setFontProvider(fontProvider);

        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "differentFontFamilies.html"), new File(
                DESTINATION_FOLDER + "differentFontFamilies.pdf"), properties);
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "differentFontFamilies.pdf", SOURCE_FOLDER
                + "cmp_differentFontFamilies.pdf", DESTINATION_FOLDER, "diff_"));
    }
}
