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


import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
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
public class HtmlTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/HtmlTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/HtmlTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void html01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest01.html"), new File(destinationFolder + "htmlTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest01.pdf", sourceFolder + "cmp_htmlTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG)})
    public void html02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest02.html"), new File(destinationFolder + "htmlTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest02.pdf", sourceFolder + "cmp_htmlTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void html03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest03.html"), new File(destinationFolder + "htmlTest03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest03.pdf", sourceFolder + "cmp_htmlTest03.pdf", destinationFolder, "diff03_"));
    }

    // this test is both for html and body
    @Test
    public void html04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest04.html"), new File(destinationFolder + "htmlTest04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest04.pdf", sourceFolder + "cmp_htmlTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void html05Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest05.html"), new File(destinationFolder + "htmlTest05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest05.pdf", sourceFolder + "cmp_htmlTest05.pdf", destinationFolder, "diff05_"));
    }

    // this test is both for html and body
    @Test
    public void html06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest06.html"), new File(destinationFolder + "htmlTest06.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest06.pdf", sourceFolder + "cmp_htmlTest06.pdf", destinationFolder, "diff06_"));
    }

    // this test is both for html and body
    @Test
    public void html07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest07.html"), new File(destinationFolder + "htmlTest07.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest07.pdf", sourceFolder + "cmp_htmlTest07.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void html08Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest08.html"), new File(destinationFolder + "htmlTest08.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest08.pdf", sourceFolder + "cmp_htmlTest08.pdf", destinationFolder, "diff08_"));
    }

    //TODO replace cmp file when fixing DEVSIX-7303
    @Test
    public void html09Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "htmlTest09.html"), new File(destinationFolder + "htmlTest09.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "htmlTest09.pdf", sourceFolder + "cmp_htmlTest09.pdf", destinationFolder, "diff09_"));
    }
}
