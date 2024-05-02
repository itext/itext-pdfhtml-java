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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
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
public class BodyTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/BodyTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/BodyTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void body01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest01.html"), new File(destinationFolder + "bodyTest01.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest01.pdf", sourceFolder + "cmp_bodyTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void body02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest02.html"), new File(destinationFolder + "bodyTest02.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest02.pdf", sourceFolder + "cmp_bodyTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void body03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest03.html"), new File(destinationFolder + "bodyTest03.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest03.pdf", sourceFolder + "cmp_bodyTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void body04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest04.html"), new File(destinationFolder + "bodyTest04.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest04.pdf", sourceFolder + "cmp_bodyTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void body05Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest05.html"), new File(destinationFolder + "bodyTest05.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest05.pdf", sourceFolder + "cmp_bodyTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void body06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest06.html"), new File(destinationFolder + "bodyTest06.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest06.pdf", sourceFolder + "cmp_bodyTest06.pdf", destinationFolder, "diff06_"));
    }

    // this test is both for html and body
    @Test
    public void body07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest07.html"), new File(destinationFolder + "bodyTest07.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest07.pdf", sourceFolder + "cmp_bodyTest07.pdf", destinationFolder, "diff07_"));
    }

    // this test is both for html and body
    @Test
    public void body08Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest08.html"), new File(destinationFolder + "bodyTest08.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest08.pdf", sourceFolder + "cmp_bodyTest08.pdf", destinationFolder, "diff08_"));
    }

    // this test is both for html and body
    @Test
    public void body09Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "bodyTest09.html"), new File(destinationFolder + "bodyTest09.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "bodyTest09.pdf", sourceFolder + "cmp_bodyTest09.pdf", destinationFolder, "diff09_"));
    }
    @Test
    public void helloMalformedDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_malformed", sourceFolder, destinationFolder);
    }

}
