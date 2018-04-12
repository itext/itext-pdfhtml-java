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
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class BorderRadiusTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BorderRadiusTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BorderRadiusTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void borderRadius01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest01.html"), new File(destinationFolder + "borderRadiusTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest01.pdf", sourceFolder + "cmp_borderRadiusTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void borderRadius02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest02.html"), new File(destinationFolder + "borderRadiusTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest02.pdf", sourceFolder + "cmp_borderRadiusTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void borderRadius03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest03.html"), new File(destinationFolder + "borderRadiusTest03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest03.pdf", sourceFolder + "cmp_borderRadiusTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void borderRadius04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest04.html"), new File(destinationFolder + "borderRadiusTest04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest04.pdf", sourceFolder + "cmp_borderRadiusTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void borderRadius05Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest05.html"), new File(destinationFolder + "borderRadiusTest05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest05.pdf", sourceFolder + "cmp_borderRadiusTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void borderRadius06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest06.html"), new File(destinationFolder + "borderRadiusTest06.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest06.pdf", sourceFolder + "cmp_borderRadiusTest06.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void borderRadius07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest07.html"), new File(destinationFolder + "borderRadiusTest07.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest07.pdf", sourceFolder + "cmp_borderRadiusTest07.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void borderRadius08Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest08.html"), new File(destinationFolder + "borderRadiusTest08.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest08.pdf", sourceFolder + "cmp_borderRadiusTest08.pdf", destinationFolder, "diff08_"));
    }

    @Test
    public void borderRadius09Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest09.html"), new File(destinationFolder + "borderRadiusTest09.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest09.pdf", sourceFolder + "cmp_borderRadiusTest09.pdf", destinationFolder, "diff09_"));
    }

    @Test
    public void borderRadius10Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest10.html"), new File(destinationFolder + "borderRadiusTest10.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest10.pdf", sourceFolder + "cmp_borderRadiusTest10.pdf", destinationFolder, "diff10_"));
    }

    @Test
    public void borderRadius11Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest11.html"), new File(destinationFolder + "borderRadiusTest11.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest11.pdf", sourceFolder + "cmp_borderRadiusTest11.pdf", destinationFolder, "diff11_"));
    }

    @Test
    public void borderRadius12Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest12.html"), new File(destinationFolder + "borderRadiusTest12.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest12.pdf", sourceFolder + "cmp_borderRadiusTest12.pdf", destinationFolder, "diff12_"));
    }

    @Test
    public void borderRadius13Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest13.html"), new File(destinationFolder + "borderRadiusTest13.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest13.pdf", sourceFolder + "cmp_borderRadiusTest13.pdf", destinationFolder, "diff13_"));
    }

    @Test
    // TODO DEVSIX-1911
    public void borderRadius14Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest14.html"), new File(destinationFolder + "borderRadiusTest14.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest14.pdf", sourceFolder + "cmp_borderRadiusTest14.pdf", destinationFolder, "diff14_"));
    }

    @Test
    // TODO DEVSIX-1911
    public void borderRadius15Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest15.html"), new File(destinationFolder + "borderRadiusTest15.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest15.pdf", sourceFolder + "cmp_borderRadiusTest15.pdf", destinationFolder, "diff15_"));
    }

    @Test
    // TODO DEVSIX-1911
    public void borderRadius16Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest16.html"), new File(destinationFolder + "borderRadiusTest16.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest16.pdf", sourceFolder + "cmp_borderRadiusTest16.pdf", destinationFolder, "diff16_"));
    }

    @Test
    // TODO DEVSIX-1911
    public void borderRadius17Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "borderRadiusTest17.html"), new File(destinationFolder + "borderRadiusTest17.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "borderRadiusTest17.pdf", sourceFolder + "cmp_borderRadiusTest17.pdf", destinationFolder, "diff17_"));
    }

}
