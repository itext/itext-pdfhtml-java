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
package com.itextpdf.html2pdf.element;

import com.itextpdf.forms.logs.FormsLogMessageConstants;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BrTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/BrTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/BrTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void br01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "brTest01.html"), new File(destinationFolder + "brTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "brTest01.pdf", sourceFolder + "cmp_brTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void br02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "brTest02.html"), new File(destinationFolder + "brTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "brTest02.pdf", sourceFolder + "cmp_brTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    @Ignore("DEVSIX-1655")
    public void br03Test() throws IOException, InterruptedException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + "brTest03.pdf"));
        pdfDoc.setDefaultPageSize(new PageSize(72, 72));
        HtmlConverter.convertToPdf(new FileInputStream(sourceFolder + "brTest03.html"), pdfDoc, new ConverterProperties());
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "brTest03.pdf", sourceFolder + "cmp_brTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = FormsLogMessageConstants.DUPLICATE_EXPORT_VALUE, count = 1)
    })
    // TODO DEVSIX-2092
    public void brInsideDifferentTagsTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "brInsideDifferentTagsTest01.html"), new File(destinationFolder + "brInsideDifferentTagsTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "brInsideDifferentTagsTest01.pdf", sourceFolder + "cmp_brInsideDifferentTagsTest01.pdf", destinationFolder, "diff04_"));
    }

    @Test
    // TODO DEVSIX-6070 <br> tag create too much space with a small font size
    public void brSmallFontSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("brSmallFontSize", sourceFolder, destinationFolder);
    }

    @Test
    public void brClearNoneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("brClearNone", sourceFolder, destinationFolder);
    }
}
