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
import com.itextpdf.io.util.UrlUtil;
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
public class ColColgroupTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ColColgroupTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ColColgroupTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simpleBackgroundTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "simpleBackgroundTest.html"), new File(destinationFolder + "simpleBackgroundTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "simpleBackgroundTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simpleBackgroundTest.pdf", sourceFolder + "cmp_simpleBackgroundTest.pdf", destinationFolder, "diff_simpleBackgroundTest_"));
    }

    @Test
    public void simpleTdColspanTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "simpleTdColspanTest.html"), new File(destinationFolder + "simpleTdColspanTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "simpleTdColspanTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simpleTdColspanTest.pdf", sourceFolder + "cmp_simpleTdColspanTest.pdf", destinationFolder, "diff_simpleTdColspanTest_"));
    }

    @Test
    public void simpleTdRowspanTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "simpleTdRowspanTest.html"), new File(destinationFolder + "simpleTdRowspanTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "simpleTdRowspanTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simpleTdRowspanTest.pdf", sourceFolder + "cmp_simpleTdRowspanTest.pdf", destinationFolder, "diff_simpleTdRowspanTest_"));
    }

    @Test
    public void simpleTdColspanRowspanTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "simpleTdColspanRowspanTest.html"), new File(destinationFolder + "simpleTdColspanRowspanTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "simpleTdColspanRowspanTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simpleTdColspanRowspanTest.pdf", sourceFolder + "cmp_simpleTdColspanRowspanTest.pdf", destinationFolder, "diff_simpleTdColspanRowspanTest_"));
    }

    @Test
    public void complexColspanRowspanTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "complexColspanRowspanTest.html"), new File(destinationFolder + "complexColspanRowspanTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "complexColspanRowspanTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "complexColspanRowspanTest.pdf", sourceFolder + "cmp_complexColspanRowspanTest.pdf", destinationFolder, "diff_complexColspanRowspanTest_"));
    }

    @Test
    public void simpleWidthTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "simpleWidthTest.html"), new File(destinationFolder + "simpleWidthTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "simpleWidthTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simpleWidthTest.pdf", sourceFolder + "cmp_simpleWidthTest.pdf", destinationFolder, "diff_simpleWidthTest_"));
    }

    @Test
    public void widthColOverridedTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "widthColOverridedTest.html"), new File(destinationFolder + "widthColOverridedTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "widthColOverridedTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "widthColOverridedTest.pdf", sourceFolder + "cmp_widthColOverridedTest.pdf", destinationFolder, "diff_widthColOverridedTest_"));
    }

    @Test
    //In this test we use FireFox behavior that treat <colgroup> and <col> tags equally and don't override colgroup's width value with smaller one in case of width set on <td>
    public void widthColgroupOverridedTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "widthColgroupOverridedTest.html"), new File(destinationFolder + "widthColgroupOverridedTest.pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + "widthColgroupOverridedTest.html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "widthColgroupOverridedTest.pdf", sourceFolder + "cmp_widthColgroupOverridedTest.pdf", destinationFolder, "diff_widthColgroupOverridedTest_"));
    }
}
