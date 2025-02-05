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
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.RangeBuilder;
import com.itextpdf.layout.font.selectorstrategy.BestMatchFontSelectorStrategy.BestMatchFontSelectorStrategyFactory;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.ExtendedITextTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class FontRangeTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FontRangeTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FontRangeTest/";
    public static final String FONTS_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/fonts/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void fontCharRangeTest() throws IOException {
        char glyph = '\u00B6';
        String HTML = "<!DOCTYPE html><html lang=en><head></head><body>Hello" + glyph + "World</body></html>";
        String font = FONTS_FOLDER + "Bokor-Regular.ttf";
        String dest = DESTINATION_FOLDER + "fontRangeTest.pdf";

        FontProvider fontProvider = new BasicFontProvider(false, false, false);
        FontProgram fontProgram = FontProgramFactory.createFont(font);
        fontProvider.setFontSelectorStrategyFactory(new BestMatchFontSelectorStrategyFactory());
        fontProvider.addFont(fontProgram);
        fontProvider.addFont(StandardFonts.HELVETICA, PdfEncodings.WINANSI, new RangeBuilder((int)glyph).create());

        ConverterProperties properties = new ConverterProperties();
        properties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(HTML, new PdfWriter(dest),properties);

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(dest));
        String contentStream = new String(pdfDocument.getPage(1).getFirstContentStream().getBytes(), StandardCharsets.UTF_8);

        //Currently we will find only one mention of our first font in the contentstream.
        //Expected we would find it twice. /F1  for hello - /F2 for the glyph - /F1 again for world.
        int count = contentStream.split("/F1").length -1;
        Assertions.assertEquals(2, count, "The result does not find the expected number of occurrences.");
    }
}
