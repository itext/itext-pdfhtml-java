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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class HTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/HTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/HTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void h1Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest01.html"), new File(destinationFolder + "hTest01.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest01.pdf", sourceFolder + "cmp_hTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void h2Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest02.html"), new File(destinationFolder + "hTest02.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest02.pdf", sourceFolder + "cmp_hTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void h3Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest03.html"), new File(destinationFolder + "hTest03.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest03.pdf", sourceFolder + "cmp_hTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void h4Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest04.html"), new File(destinationFolder + "hTest04.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest04.pdf", sourceFolder + "cmp_hTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void h5Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest05.html"), new File(destinationFolder + "hTest05.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest05.pdf", sourceFolder + "cmp_hTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void hTagRoleTest() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "hTest06.pdf"));
        pdfDocument.setTagged();
        HtmlConverter.convertToPdf(new FileInputStream(sourceFolder + "hTest06.html"), pdfDocument, new ConverterProperties());
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest06.pdf", sourceFolder + "cmp_hTest06.pdf", destinationFolder, "diff06_"));
    }

}
