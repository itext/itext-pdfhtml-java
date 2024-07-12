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
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.test.ExtendedITextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

@Tag("IntegrationTest")
//TODO(DEVSIX-1034): serif, sans-serif font families are not supported
//TODO(DEVSIX-1036): cursive, fantasy, system-ui font-families are not supported
public class FontSelectorGenericFamiliesTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FontSelectorGenericFamiliesTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FontSelectorGenericFamiliesTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void standardFontsTest() throws IOException, InterruptedException {
        runTest("standardFonts", new DefaultFontProvider(true, false, false));
    }

    @Test
    public void embeddedFontsTest() throws IOException, InterruptedException {
        runTest("embeddedFonts", new DefaultFontProvider(false, true, false));
    }

    public void runTest(String testName, FontProvider fontProvider) throws IOException, InterruptedException {
        String outPdf = destinationFolder + testName + ".pdf";
        String cmpPdf = sourceFolder + "cmp_" + testName + ".pdf";
        String srcHtml = sourceFolder + "genericFontFamilies.html";
        ConverterProperties properties = new ConverterProperties().setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new File(srcHtml), new File(outPdf), properties);
        Assertions.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff_" + testName + "_"));
    }
}
