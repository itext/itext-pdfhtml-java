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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.selectorstrategy.BestMatchFontSelectorStrategy.BestMatchFontSelectorStrategyFactory;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class FontFaceTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FontFaceTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FontFaceTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_FONT) })
    @Test
    public void emptyFontDefinitionTest() throws IOException, InterruptedException {
        runTest("emptyWebFontCssTest");
    }


    @Test
    public void droidSerifWebFontTest() throws IOException, InterruptedException {
        runTest("droidSerifWebFontTest");
    }

    @Test
    public void droidSerifLocalFontTest() throws IOException, InterruptedException {
        runTest("droidSerifLocalFontTest");
    }

    @Test
    public void droidSerifLocalLocalFontTest() throws IOException, InterruptedException {
        runTest("droidSerifLocalLocalFontTest");
    }

    @Test
    public void droidSerifLocalWithMediaFontTest() throws IOException, InterruptedException {
        runTest("droidSerifLocalWithMediaFontTest");
    }

    @Test
    public void droidSerifLocalWithMediaRuleFontTest() throws IOException, InterruptedException {
        runTest("droidSerifLocalWithMediaRuleFontTest");
    }

    @Test
    public void droidSerifLocalWithMediaRuleFontTest2() throws IOException, InterruptedException {
        runTest("droidSerifLocalWithMediaRuleFontTest2");
    }

    @Test
    public void fontSelectorTest01() throws IOException, InterruptedException {
        runTest("fontSelectorTest01");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI)})
    public void fontFaceGrammarTest() throws IOException, InterruptedException {
        runTest("fontFaceGrammarTest");
    }

    @Test
    public void droidSerifLocalWithMediaRuleFontTest3() {
        String name = "droidSerifLocalWithMediaRuleFontTest";
        String htmlPath = sourceFolder + name + ".html";
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");

        ConverterProperties converterProperties = new ConverterProperties()
                .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT))
                .setFontProvider(new FontProvider());
        String exception = null;
        try {
            HtmlConverter.convertToPdf(htmlPath, new ByteArrayOutputStream(), converterProperties);
        } catch (Exception e) {
            exception = e.getMessage();
        }
        Assertions.assertEquals(Html2PdfException.FONT_PROVIDER_CONTAINS_ZERO_FONTS, exception,
                "Font Provider with zero fonts shall fail");
    }

    @Test
    public void fontFaceWoffTest01() throws IOException, InterruptedException {
        runTest("fontFaceWoffTest01");
    }

    @Test
    public void fontFaceWoffTest02() throws IOException, InterruptedException {
        runTest("fontFaceWoffTest02");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_FONT)
    })
    public void fontFaceTtcTest() throws IOException, InterruptedException {
        runTest("fontFaceTtcTest");
    }

    @Test
    public void fontFaceWoff2SimpleTest() throws IOException, InterruptedException {
        runTest("fontFaceWoff2SimpleTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_FONT)
    })
    public void fontFaceWoff2TtcTest() throws IOException, InterruptedException {
        runTest("fontFaceWoff2TtcTest");
    }

    @Test
    //In w3c test suite this font is labeled as invalid though it correctly parsers both in browser and iText
    //See BlocksMetadataPadding001Test in io for decompression details
    public void w3cProblemTest01() throws IOException, InterruptedException {
        runTest("w3cProblemTest01");
    }

    @Test
    public void w3cProblemTest02() throws IOException, InterruptedException {
        try {
            runTest("w3cProblemTest02");
        } catch (NegativeArraySizeException e) {
            return;
        }

        Assertions.fail("In w3c test suite this font is labeled as invalid, "
                + "so the invalid negative value is expected while creating a glyph.");
    }

    @Test
    //Silently omitted, decompression should fail.
    //See HeaderFlavor001Test in io for decompression details
    public void w3cProblemTest03() throws IOException, InterruptedException {
        runTest("w3cProblemTest03");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.FONT_SUBSET_ISSUE)})
    //Silently omitted, decompression should fail. Browser loads font but don't draw glyph.
    //See HeaderFlavor002Test in io for decompression details
    public void w3cProblemTest04() throws IOException, InterruptedException {
        //NOTE, iText fails on subsetting as expected.
        runTest("w3cProblemTest04");
    }

    @Test
    //In w3c test suite this font is labeled as invalid though it correctly parsers both in browser and iText
    //See HeaderReserved001Test in io for decompression details
    public void w3cProblemTest05() throws IOException, InterruptedException {
        runTest("w3cProblemTest05");
    }

    @Test
    //In w3c test suite this font is labeled as invalid though it correctly parsers both in browser and iText
    //See TabledataHmtxTransform003Test in io for decompression details
    public void w3cProblemTest06() throws IOException, InterruptedException {
        runTest("w3cProblemTest06");
    }

    @Test
    public void w3cProblemTest07() throws IOException, InterruptedException {
        try {
            runTest("w3cProblemTest07");
        } catch (NegativeArraySizeException e) {
            return;
        }

        Assertions.fail("In w3c test suite this font is labeled as invalid, "
                + "so the invalid negative value is expected while creating a glyph.");
    }

    @Test
    public void incorrectFontNameTest01() throws IOException, InterruptedException {
        runTest("incorrectFontNameTest01");
    }

    @Test
    public void incorrectFontNameTest02() throws IOException, InterruptedException {
        runTest("incorrectFontNameTest02");
    }

    @Test
    //Checks that font used in previous two files is correct
    public void incorrectFontNameTest03() throws IOException, InterruptedException {
        runTest("incorrectFontNameTest03");
    }

    @Test
    public void incorrectFontNameTest04() throws IOException, InterruptedException {
        runTest("incorrectFontNameTest04");
    }

    @Test
    public void cannotProcessSpecifiedFontTest01() throws IOException, InterruptedException {
        runTest("cannotProcessSpecifiedFontTest01");
    }

    @Test
    @Disabled("DEVSIX-1759")
    public void fontFamilyTest01() throws IOException, InterruptedException {
        runTest("fontFamilyTest01");
    }

    @Test
    //TODO DEVSIX-2122
    public void fontFaceFontWeightTest() throws IOException, InterruptedException {
        runTest("fontFaceFontWeightTest");
    }

    @Test
    //TODO DEVSIX-2122
    public void fontFaceFontWeightWrongTest() throws IOException, InterruptedException {
        runTest("fontFaceFontWeightWrongWeightsTest");
    }

    @Test
    //TODO DEVSIX-2122
    public void fontFaceFontWeightInvalidTest() throws IOException, InterruptedException {
        runTest("fontFaceFontWeightInvalidWeightsTest");
    }

    @Test
    public void texFonts01() throws IOException, InterruptedException {
        runTest("texFonts01");
    }

    @Test
    //TODO: update/refactor after DEVSIX-2054 fix
    public void correctUrlWithNotUsedUnicodeRangeTest() throws IOException, InterruptedException {
        runTest("correctUrlWithNotUsedUnicodeRangeTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_FONT),
    })
    // TODO DEVSIX-2054
    public void doNotDownloadUnusedFontTest() throws IOException, InterruptedException {
        runTest("doNotDownloadUnusedFontTest");
    }

    @Test
    public void correctUrlWithUsedUnicodeRangeTest() throws IOException, InterruptedException {
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.setFontSelectorStrategyFactory(new BestMatchFontSelectorStrategyFactory());
        runTest("correctUrlWithUsedUnicodeRangeTest", fontProvider);
    }

    @Test
    public void correctUnicodeRangeSignificantTest() throws IOException, InterruptedException {
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.setFontSelectorStrategyFactory(new BestMatchFontSelectorStrategyFactory());
        runTest("correctUnicodeRangeSignificantTest", fontProvider);
    }

    @Test
    public void overwrittenUnicodeRangeTextInLineTest() throws IOException, InterruptedException {
        runTest("overwrittenUnicodeRangeTextInLineTest");
    }

    @Test
    public void overwrittenUnicodeRangeTextInSomeLinesTest() throws IOException, InterruptedException {
        runTest("overwrittenUnicodeRangeTextInSomeLinesTest");
    }

    @Test
    public void fontFaceWithUnicodeRangeTest() throws IOException, InterruptedException {
        runTest("fontFaceWithUnicodeRangeTest");
    }

    @Test
    public void incorrectUnicodeRangesTest() throws IOException, InterruptedException {
        runTest("incorrectUnicodeRangesTest");
    }

    @Test
    public void unusedFontWithUnicodeRangeTest() throws IOException, InterruptedException {
        runTest("unusedFontWithUnicodeRangeTest");
    }

    @Test
    //TODO DEVSIX-2114: Support bolder / Lighter font weight
    public void bolderAndLighterFontWeightTest() throws IOException, InterruptedException {
        runTest("bolderLighterFontWeightTest");
    }

    private void runTest(String name, FontProvider fontProvider) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");

        ConverterProperties converterProperties = new ConverterProperties()
                .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT))
                .setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        Assertions.assertFalse(converterProperties.getFontProvider().getFontSet().contains("droid serif"), "Temporary font was found.");
        Assertions.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }

    private void runTest(String name) throws IOException, InterruptedException {
        runTest(name, new DefaultFontProvider());
    }
}
