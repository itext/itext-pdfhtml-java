/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class HTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/HTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/HTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void h1Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest01.html"), new File(destinationFolder + "hTest01.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest01.pdf", sourceFolder + "cmp_hTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void h2Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest02.html"), new File(destinationFolder + "hTest02.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest02.pdf", sourceFolder + "cmp_hTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void h3Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest03.html"), new File(destinationFolder + "hTest03.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest03.pdf", sourceFolder + "cmp_hTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void h4Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest04.html"), new File(destinationFolder + "hTest04.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest04.pdf", sourceFolder + "cmp_hTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void h5Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hTest05.html"), new File(destinationFolder + "hTest05.pdf"),
                new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest05.pdf", sourceFolder + "cmp_hTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void hTagRoleTest() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "hTest06.pdf"));
        pdfDocument.setTagged();
        HtmlConverter.convertToPdf(new FileInputStream(sourceFolder + "hTest06.html"), pdfDocument, new ConverterProperties());
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hTest06.pdf", sourceFolder + "cmp_hTest06.pdf", destinationFolder, "diff06_"));
    }

}
