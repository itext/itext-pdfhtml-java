/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

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
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.test.ExtendedITextTest;
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
public class WordBreakTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/WordBreakTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/WordBreakTest/";
    public static final String fontsFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CJKFonts/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void wordBreakCommonScenarioTest() throws IOException, InterruptedException {
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.addFont(fontsFolder + "NotoSansCJKjp-Regular.otf");

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(fontProvider);

        HtmlConverter.convertToPdf(new File(sourceFolder + "wordBreakCommonScenario.html"),
                new File(destinationFolder + "wordBreakCommonScenario.pdf"), converterProperties);
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "wordBreakCommonScenario.pdf",
                sourceFolder + "cmp_wordBreakCommonScenario.pdf", destinationFolder));
    }

    @Test
    public void overflowXWordBreakTest() throws IOException, InterruptedException {
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.addFont(fontsFolder + "NotoSansCJKjp-Regular.otf");

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(fontProvider);

        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowXWordBreak.html"),
                new File(destinationFolder + "overflowXWordBreak.pdf"), converterProperties);
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "overflowXWordBreak.pdf",
                sourceFolder + "cmp_overflowXWordBreak.pdf", destinationFolder));
    }

    @Test
    public void whiteSpaceAndWordBreakTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "whiteSpaceAndWordBreak.html"),
                new File(destinationFolder + "whiteSpaceAndWordBreak.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "whiteSpaceAndWordBreak.pdf",
                sourceFolder + "cmp_whiteSpaceAndWordBreak.pdf", destinationFolder));
    }

    @Test
    public void wordBreakMidNumbersTest() throws IOException, InterruptedException {
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.addFont(fontsFolder + "NotoSansCJKjp-Regular.otf");

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(fontProvider);

        HtmlConverter.convertToPdf(new File(sourceFolder + "wordBreakMidNumbers.html"),
                new File(destinationFolder + "wordBreakMidNumbers.pdf"), converterProperties);
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "wordBreakMidNumbers.pdf",
                sourceFolder + "cmp_wordBreakMidNumbers.pdf", destinationFolder));
    }

    @Test
    public void wordBreakMidPunctuationTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "wordBreakMidPunctuation.html"),
                new File(destinationFolder + "wordBreakMidPunctuation.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "wordBreakMidPunctuation.pdf",
                sourceFolder + "cmp_wordBreakMidPunctuation.pdf", destinationFolder));
    }

    @Test
    public void wordBreakAllAndFloatTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "wordBreakAllAndFloat.html"),
                new File(destinationFolder + "wordBreakAllAndFloat.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "wordBreakAllAndFloat.pdf",
                sourceFolder + "cmp_wordBreakAllAndFloat.pdf", destinationFolder));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant
            .TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 3)})
    public void wordBreakTableScenarioTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "wordBreakTableScenario.html"),
                new File(destinationFolder + "wordBreakTableScenario.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "wordBreakTableScenario.pdf",
                sourceFolder + "cmp_wordBreakTableScenario.pdf", destinationFolder));
    }
}
