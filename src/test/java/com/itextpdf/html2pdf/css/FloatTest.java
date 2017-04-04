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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class FloatTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FloatTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FloatTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void float01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float01Test.html"), new File(destinationFolder + "float01Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float01Test.pdf", sourceFolder + "cmp_float01Test.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void float02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float02Test.html"), new File(destinationFolder + "float02Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float02Test.pdf", sourceFolder + "cmp_float02Test.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void float03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float03Test.html"), new File(destinationFolder + "float03Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float03Test.pdf", sourceFolder + "cmp_float03Test.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void float04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float04Test.html"), new File(destinationFolder + "float04Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float04Test.pdf", sourceFolder + "cmp_float04Test.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void float05Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float05Test.html"), new File(destinationFolder + "float05Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float05Test.pdf", sourceFolder + "cmp_float05Test.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void float06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float06Test.html"), new File(destinationFolder + "float06Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float06Test.pdf", sourceFolder + "cmp_float06Test.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void float07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float07Test.html"), new File(destinationFolder + "float07Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float07Test.pdf", sourceFolder + "cmp_float07Test.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void float08Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float08Test.html"), new File(destinationFolder + "float08Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float08Test.pdf", sourceFolder + "cmp_float08Test.pdf", destinationFolder, "diff08_"));
    }

    @Test
    public void float09Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float09Test.html"), new File(destinationFolder + "float09Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float09Test.pdf", sourceFolder + "cmp_float09Test.pdf", destinationFolder, "diff09_"));
    }

    @Test
    public void float10Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float10Test.html"), new File(destinationFolder + "float10Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float10Test.pdf", sourceFolder + "cmp_float10Test.pdf", destinationFolder, "diff10_"));
    }

    @Test
    public void float11Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float11Test.html"), new File(destinationFolder + "float11Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float11Test.pdf", sourceFolder + "cmp_float11Test.pdf", destinationFolder, "diff11_"));
    }

    @Test
    public void float12Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float12Test.html"), new File(destinationFolder + "float12Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float12Test.pdf", sourceFolder + "cmp_float12Test.pdf", destinationFolder, "diff12_"));
    }

    @Test
    public void float13Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float13Test.html"), new File(destinationFolder + "float13Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float13Test.pdf", sourceFolder + "cmp_float13Test.pdf", destinationFolder, "diff13_"));
    }

    @Test
    @Ignore("In this test css property overflow: hidden is ignored by iText. This leads to invalid results. Perhaps, one day it will be fixed")
    public void float14Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float14Test.html"), new File(destinationFolder + "float14Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float14Test.pdf", sourceFolder + "cmp_float14Test.pdf", destinationFolder, "diff14_"));
    }

    @Test
    public void float15Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float15Test.html"), new File(destinationFolder + "float15Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float15Test.pdf", sourceFolder + "cmp_float15Test.pdf", destinationFolder, "diff15_"));
    }

    @Test
    public void float16Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float16Test.html"), new File(destinationFolder + "float16Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float16Test.pdf", sourceFolder + "cmp_float16Test.pdf", destinationFolder, "diff16_"));
    }

    @Test
    public void float17Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float17Test.html"), new File(destinationFolder + "float17Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float17Test.pdf", sourceFolder + "cmp_float17Test.pdf", destinationFolder, "diff17_"));
    }

    @Test
    public void float18Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float18Test.html"), new File(destinationFolder + "float18Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float18Test.pdf", sourceFolder + "cmp_float18Test.pdf", destinationFolder, "diff18_"));
    }

    @Test
    public void float19Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float19Test.html"), new File(destinationFolder + "float19Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float19Test.pdf", sourceFolder + "cmp_float19Test.pdf", destinationFolder, "diff19_"));
    }

    @Test
    public void float20Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float20Test.html"), new File(destinationFolder + "float20Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float20Test.pdf", sourceFolder + "cmp_float20Test.pdf", destinationFolder, "diff20_"));
    }

    @Test
    public void float21Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "float21Test.html"), new File(destinationFolder + "float21Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "float21Test.pdf", sourceFolder + "cmp_float21Test.pdf", destinationFolder, "diff21_"));
    }
}
