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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class TitleTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TitleTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TitleTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void title01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "titleTest01.html"), new File(destinationFolder + "titleTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "titleTest01.pdf", sourceFolder + "cmp_titleTest01.pdf", destinationFolder, "diff01_"));
        Assert.assertEquals("Best title!", new PdfDocument(new PdfReader(destinationFolder + "titleTest01.pdf")).getDocumentInfo().getTitle());
    }

    @Test
    public void title02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "titleTest02.html"), new File(destinationFolder + "titleTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "titleTest02.pdf", sourceFolder + "cmp_titleTest02.pdf", destinationFolder, "diff02_"));
        Assert.assertEquals("Best title!", new PdfDocument(new PdfReader(destinationFolder + "titleTest02.pdf")).getDocumentInfo().getTitle());
    }

}
