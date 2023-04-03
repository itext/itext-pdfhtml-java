/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class AlignAttributeTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute/AlignAttributeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/AlignAttributeTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void alignImgTest01() throws IOException, InterruptedException {
        // vertical-alignment values top, middle and bottom are not currently supported for inline-block elements and images
        convertToPdfAndCompare("alignImgTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void imgAttrAlignLeftReadOrderPdfTest() throws IOException, InterruptedException {
        String pdfDestinationFile = destinationFolder + "imgAttrAlignLeftReadOrderPdf.pdf";
        String htmlSource = sourceFolder + "imgAttrAlignLeftReadOrderPdf.html";
        String cmpPdfDestinationFile = sourceFolder + "cmp_imgAttrAlignLeftReadOrderPdf.pdf";

        FileOutputStream fileOutputStream = new FileOutputStream(pdfDestinationFile);
        WriterProperties writerProperties = new WriterProperties();
        writerProperties.addXmpMetadata();

        PdfWriter pdfWriter = new PdfWriter(fileOutputStream, writerProperties);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.getCatalog().setLang(new PdfString("en-US"));
        pdfDocument.setTagged();

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri(sourceFolder);

        FileInputStream fileInputStream = new FileInputStream(htmlSource);
        HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProperties);

        Assert.assertNull(new CompareTool()
                .compareByContent(pdfDestinationFile, cmpPdfDestinationFile, destinationFolder));
    }
}
