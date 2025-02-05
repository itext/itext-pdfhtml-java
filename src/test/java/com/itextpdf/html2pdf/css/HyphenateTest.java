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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class HyphenateTest extends ExtendedITextTest {
    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/HyphenateTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/HyphenateTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void test01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest01.html"), new File(destinationFolder + "hyphenateTest01.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest01.pdf", sourceFolder + "cmp_hyphenateTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void test02() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest02.html"), new File(destinationFolder + "hyphenateTest02.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest02.pdf", sourceFolder + "cmp_hyphenateTest02.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void test03() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest03.html"), new File(destinationFolder + "hyphenateTest03.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest03.pdf", sourceFolder + "cmp_hyphenateTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void test04() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest04.html"), new File(destinationFolder + "hyphenateTest04.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest04.pdf", sourceFolder + "cmp_hyphenateTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void test05() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest05.html"), new File(destinationFolder + "hyphenateTest05.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest05.pdf", sourceFolder + "cmp_hyphenateTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void test06() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest06.html"), new File(destinationFolder + "hyphenateTest06.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest06.pdf", sourceFolder + "cmp_hyphenateTest06.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void test07Ru() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest07Ru.html"), new File(destinationFolder + "hyphenateTest07Ru.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest07Ru.pdf", sourceFolder + "cmp_hyphenateTest07Ru.pdf", destinationFolder, "diff07Ru_"));
    }

    @Test
    public void test08De() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest08De.html"), new File(destinationFolder + "hyphenateTest08De.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest08De.pdf", sourceFolder + "cmp_hyphenateTest08De.pdf", destinationFolder, "diff08De_"));
    }

    @Test
    public void test09NonBreakingHyphen() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hyphenateTest09NonBreakingHyphen.html"), new File(destinationFolder + "hyphenateTest09NonBreakingHyphen.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hyphenateTest09NonBreakingHyphen.pdf", sourceFolder + "cmp_hyphenateTest09NonBreakingHyphen.pdf", destinationFolder, "diff09NonBreakingHyphen_"));
    }
}
