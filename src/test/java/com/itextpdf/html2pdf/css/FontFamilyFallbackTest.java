/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.test.ExtendedITextTest;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class FontFamilyFallbackTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FontFamilyFallbackTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FontFamilyFallbackTest/";
    public static final String FONTSFOLDER = "./src/test/resources/com/itextpdf/html2pdf/fonts/";


    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void noJapaneseGlyphsTest() throws IOException, InterruptedException {
        String htmlPath = SOURCE_FOLDER + "glyphsNotFound.html";
        String pdfPath = DESTINATION_FOLDER + "glyphsNotFound.pdf";

        FontProgram font = FontProgramFactory.createFont(FONTSFOLDER +
                "Bokor-Regular.ttf");
        FontProgram backUpFont = FontProgramFactory.createFont(FONTSFOLDER +
                "NotoSansJP-Bold.ttf");
        FontProvider dfp = new FontProvider();
        dfp.addFont(font);
        dfp.addFont(backUpFont);

        ConverterProperties props = new ConverterProperties();
        props.setFontProvider(dfp);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), props);


        String basefontName = "";
        int fontDictionarySize = 0;
        try (PdfDocument resultPdf = new PdfDocument(new PdfReader(pdfPath))) {
            PdfDictionary resources = resultPdf.getPage(1).getResources().getPdfObject();
            PdfDictionary fontDictionary = resources.getAsDictionary(PdfName.Font);
            fontDictionarySize = fontDictionary.size();
            basefontName = fontDictionary.getAsDictionary(new PdfName("F1"))
                    .getAsName(PdfName.BaseFont).getValue();
        }

        Assertions.assertEquals(2, fontDictionarySize, "PDF contains a number of fonts different from expected.");
        Assertions.assertTrue(basefontName.contains("NotoSansJP-Bold"), "Base font name is different from expected.");
    }

    @Test
    public void mixedEnglishJapaneseTest() throws IOException, InterruptedException {
        String htmlPath = SOURCE_FOLDER + "mixedJapaneseEnglish.html";
        String pdfPath = DESTINATION_FOLDER + "mixedJapaneseEnglish.pdf";

        FontProgram font = FontProgramFactory.createFont(FONTSFOLDER +
                "Bokor-Regular.ttf");
        FontProgram backUpFont = FontProgramFactory.createFont(FONTSFOLDER +
                "NotoSansJP-Bold.ttf");
        FontProvider dfp = new FontProvider();
        dfp.addFont(font);
        dfp.addFont(backUpFont);

        ConverterProperties props = new ConverterProperties();
        props.setFontProvider(dfp);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), props);

        int fontDictionarySize = 0;
        try (PdfDocument resultPdf = new PdfDocument(new PdfReader(pdfPath))) {
            PdfDictionary resources = resultPdf.getPage(1).getResources().getPdfObject();
            PdfDictionary fontDictionary = resources.getAsDictionary(PdfName.Font);
            fontDictionarySize = fontDictionary.size();
        }

        Assertions.assertEquals(2, fontDictionarySize, "PDF contains " + fontDictionarySize + " and not the expected 2.");
    }
}
