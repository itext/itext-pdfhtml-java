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
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class AbsolutePositionTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/AbsolutePositionTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/AbsolutePositionTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void absolutePosition01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest01.html"), new File(destinationFolder + "absolutePositionTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest01.pdf", sourceFolder + "cmp_absolutePositionTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    @Ignore("DEVSIX-1616: Absolute position for elements that break across pages is not supported")
    public void absolutePosition02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest02.html"), new File(destinationFolder + "absolutePositionTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest02.pdf", sourceFolder + "cmp_absolutePositionTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES)})
    public void absolutePosition03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest03.html"), new File(destinationFolder + "absolutePositionTest03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest03.pdf", sourceFolder + "cmp_absolutePositionTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void absolutePosition04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest04.html"), new File(destinationFolder + "absolutePositionTest04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest04.pdf", sourceFolder + "cmp_absolutePositionTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void absolutePosition05Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest05.html"), new File(destinationFolder + "absolutePositionTest05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest05.pdf", sourceFolder + "cmp_absolutePositionTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void absolutePosition06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest06.html"), new File(destinationFolder + "absolutePositionTest06.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest06.pdf", sourceFolder + "cmp_absolutePositionTest06.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void absolutePosition07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest07.html"), new File(destinationFolder + "absolutePositionTest07.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest07.pdf", sourceFolder + "cmp_absolutePositionTest07.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void absolutePosition08Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest08.html"), new File(destinationFolder + "absolutePositionTest08.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest08.pdf", sourceFolder + "cmp_absolutePositionTest08.pdf", destinationFolder, "diff08_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, count = 1),
    })
    public void absolutePosition09Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest09.html"), new File(destinationFolder + "absolutePositionTest09.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest09.pdf", sourceFolder + "cmp_absolutePositionTest09.pdf", destinationFolder, "diff09_"));
    }

    @Test
    public void absolutePosition10Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest10.html"), new File(destinationFolder + "absolutePositionTest10.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest10.pdf", sourceFolder + "cmp_absolutePositionTest10.pdf", destinationFolder, "diff10_"));
    }

    @Test
    public void absolutePosition11Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest11.html"), new File(destinationFolder + "absolutePositionTest11.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest11.pdf", sourceFolder + "cmp_absolutePositionTest11.pdf", destinationFolder, "diff11_"));
    }

    @Test
    public void absolutePosition12Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest12.html"), new File(destinationFolder + "absolutePositionTest12.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest12.pdf", sourceFolder + "cmp_absolutePositionTest12.pdf", destinationFolder, "diff12_"));
    }

    @Test
    public void absolutePosition13Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest13.html"), new File(destinationFolder + "absolutePositionTest13.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest13.pdf", sourceFolder + "cmp_absolutePositionTest13.pdf", destinationFolder, "diff13_"));
    }

    @Test
    public void absolutePosition14Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest14.html"), new File(destinationFolder + "absolutePositionTest14.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest14.pdf", sourceFolder + "cmp_absolutePositionTest14.pdf", destinationFolder, "diff14_"));
    }

    @Test
    public void absolutePosition15Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest15.html"), new File(destinationFolder + "absolutePositionTest15.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest15.pdf", sourceFolder + "cmp_absolutePositionTest15.pdf", destinationFolder, "diff15_"));
    }

    @Test
    public void absolutePositionTest16() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest16.html"), new File(destinationFolder + "absolutePositionTest16.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest16.pdf", sourceFolder + "cmp_absolutePositionTest16.pdf", destinationFolder, "diff16_"));
    }

    @Test
    public void absolutePositionTest17() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest17.html"), new File(destinationFolder + "absolutePositionTest17.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest17.pdf", sourceFolder + "cmp_absolutePositionTest17.pdf", destinationFolder, "diff17_"));
    }

    @Ignore("DEVSIX-1818")
    @Test
    public void absolutePositionTest18() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "absolutePositionTest18.html"), new File(destinationFolder + "absolutePositionTest18.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "absolutePositionTest18.pdf", sourceFolder + "cmp_absolutePositionTest18.pdf", destinationFolder, "diff18_"));
    }
}
