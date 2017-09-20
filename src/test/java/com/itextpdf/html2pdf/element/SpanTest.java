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
public class SpanTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/SpanTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/SpanTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void spanTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest01.html"), new File(destinationFolder + "spanTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest01.pdf", sourceFolder + "cmp_spanTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void spanTest02() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest02.html"), new File(destinationFolder + "spanTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest02.pdf", sourceFolder + "cmp_spanTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void spanTest03() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest03.html"), new File(destinationFolder + "spanTest03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest03.pdf", sourceFolder + "cmp_spanTest03.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void spanTest04() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest04.html"), new File(destinationFolder + "spanTest04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest04.pdf", sourceFolder + "cmp_spanTest04.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void spanTest05() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest05.html"), new File(destinationFolder + "spanTest05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest05.pdf", sourceFolder + "cmp_spanTest05.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void spanTest06() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest06.html"), new File(destinationFolder + "spanTest06.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest06.pdf", sourceFolder + "cmp_spanTest06.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void spanTest07() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest07.html"), new File(destinationFolder + "spanTest07.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest07.pdf", sourceFolder + "cmp_spanTest07.pdf", destinationFolder, "diff07_"));
    }

    @Test
    // TODO DEVSIX-1438
    public void spanTest08() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest08.html"), new File(destinationFolder + "spanTest08.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest08.pdf", sourceFolder + "cmp_spanTest08.pdf", destinationFolder, "diff08_"));
    }

    @Test
    public void spanTest09() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest09.html"), new File(destinationFolder + "spanTest09.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest09.pdf", sourceFolder + "cmp_spanTest09.pdf", destinationFolder, "diff09_"));
    }

    @Test
    public void spanTest10() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest10.html"), new File(destinationFolder + "spanTest10.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest10.pdf", sourceFolder + "cmp_spanTest10.pdf", destinationFolder, "diff10_"));
    }

    @Test
    public void spanTest11() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "spanTest11.html"), new File(destinationFolder + "spanTest11.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "spanTest11.pdf", sourceFolder + "cmp_spanTest11.pdf", destinationFolder, "diff11_"));
    }

}
