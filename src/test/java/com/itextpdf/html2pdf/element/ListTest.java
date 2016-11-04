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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.media.MediaType;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.kernel.Version;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.test.ITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Category(IntegrationTest.class)
// TODO extend from ExtendedITextTest and therefore check logging
public class ListTest extends ITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ListTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ListTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void listTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest01.html"), new File(destinationFolder + "listTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest01.pdf", sourceFolder + "cmp_listTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void listTest02() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest02.html"), new File(destinationFolder + "listTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest02.pdf", sourceFolder + "cmp_listTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void listTest03() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest03.html"), new File(destinationFolder + "listTest03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest03.pdf", sourceFolder + "cmp_listTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void listTest04() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest04.html"), new File(destinationFolder + "listTest04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest04.pdf", sourceFolder + "cmp_listTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void listTest05() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest05.html"), new File(destinationFolder + "listTest05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest05.pdf", sourceFolder + "cmp_listTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void listTest06() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest06.html"), new File(destinationFolder + "listTest06.pdf"), new MediaDeviceDescription(MediaType.PRINT));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest06.pdf", sourceFolder + "cmp_listTest06.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void listTest07() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest07.html"), new File(destinationFolder + "listTest07.pdf"), new MediaDeviceDescription(MediaType.PRINT));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest07.pdf", sourceFolder + "cmp_listTest07.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void listTest08() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "listTest08.html"), new File(destinationFolder + "listTest08.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listTest08.pdf", sourceFolder + "cmp_listTest08.pdf", destinationFolder, "diff08_"));
    }

    @Test
    @Ignore("Conversion to Pdf/A for lists not supported. DEVSIX-917")
    public void listToPdfaTest() throws IOException, InterruptedException {
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");
        PdfADocument pdfADocument = new PdfADocument(new PdfWriter(destinationFolder + "listToPdfa.pdf"), PdfAConformanceLevel.PDF_A_1B, new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is));
        HtmlConverter.convertToPdf(new FileInputStream(sourceFolder + "listToPdfa.html"), pdfADocument, "", new MediaDeviceDescription(MediaType.PRINT));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listToPdfa.pdf", sourceFolder + "cmp_listToPdfa.pdf", destinationFolder, "diff99_"));
    }

}
