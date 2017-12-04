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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfName;
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
public class MetaTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/MetaTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/MetaTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void meta01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "metaTest01.html"), new File(destinationFolder + "metaTest01.pdf"));
        PdfDocumentInfo pdfDocInfo = new PdfDocument(new PdfReader(destinationFolder + "metaTest01.pdf")).getDocumentInfo();
        CompareTool compareTool = new CompareTool();
        Assert.assertNull(compareTool.compareDocumentInfo(destinationFolder + "metaTest01.pdf", sourceFolder + "cmp_metaTest01.pdf"));
        Assert.assertNull(compareTool.compareByContent(destinationFolder + "metaTest01.pdf", sourceFolder + "cmp_metaTest01.pdf", destinationFolder, "diff01_"));
        Assert.assertEquals(pdfDocInfo.getMoreInfo("test"), "the test content");
    }

    @Test
    // In this test we also check that it's not possible to override description name content
    // (which iText converts to pdf's Subject content) with Subject name content
    public void meta02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "metaTest02.html"), new File(destinationFolder + "metaTest02.pdf"));
        PdfDocumentInfo pdfDocInfo = new PdfDocument(new PdfReader(destinationFolder + "metaTest02.pdf")).getDocumentInfo();
        CompareTool compareTool = new CompareTool();
        Assert.assertNull(compareTool.compareDocumentInfo(destinationFolder + "metaTest02.pdf", sourceFolder + "cmp_metaTest02.pdf"));
        Assert.assertNull(compareTool.compareByContent(destinationFolder + "metaTest02.pdf", sourceFolder + "cmp_metaTest02.pdf", destinationFolder, "diff02_"));
        Assert.assertEquals(pdfDocInfo.getAuthor(), "Bruno Lowagie");
        Assert.assertEquals(pdfDocInfo.getKeywords(), "metadata, keywords, test");
        Assert.assertEquals(pdfDocInfo.getSubject(), "This is the description of the page");
        Assert.assertEquals(pdfDocInfo.getMoreInfo("generator"), "Eugenerator Onegenerator");
        Assert.assertEquals(pdfDocInfo.getMoreInfo("subject"), "Trying to break iText and write pdf's Subject with subject instead of description name");
    }

    @Test
    public void meta03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "metaTest03.html"), new File(destinationFolder + "metaTest03.pdf"));
        CompareTool compareTool = new CompareTool();
        Assert.assertNull(compareTool.compareDocumentInfo(destinationFolder + "metaTest03.pdf", sourceFolder + "cmp_metaTest03.pdf"));
        Assert.assertNull(compareTool.compareByContent(destinationFolder + "metaTest03.pdf", sourceFolder + "cmp_metaTest03.pdf", destinationFolder, "diff03_"));
    }
}
