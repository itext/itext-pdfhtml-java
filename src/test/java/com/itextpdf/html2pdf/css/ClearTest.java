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

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ClearTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/ClearTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/ClearTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void clear02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear02Test.html"), new File(destinationFolder + "clear02Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear02Test.pdf", sourceFolder + "cmp_clear02Test.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void clear03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear03Test.html"), new File(destinationFolder + "clear03Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear03Test.pdf", sourceFolder + "cmp_clear03Test.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void clear04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear04Test.html"), new File(destinationFolder + "clear04Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear04Test.pdf", sourceFolder + "cmp_clear04Test.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void clear06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear06Test.html"), new File(destinationFolder + "clear06Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear06Test.pdf", sourceFolder + "cmp_clear06Test.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void clear07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear07Test.html"), new File(destinationFolder + "clear07Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear07Test.pdf", sourceFolder + "cmp_clear07Test.pdf", destinationFolder, "diff07_"));
    }

    @Test
    @Ignore("DEVSIX-1269")
    public void clear08Test() throws IOException, InterruptedException {
        // TODO behaving differently from browser in some cases of selfcollapsing margins
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear08Test.html"), new File(destinationFolder + "clear08Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear08Test.pdf", sourceFolder + "cmp_clear08Test.pdf", destinationFolder, "diff08_"));
    }

    @Test
    public void clear09Test() throws IOException, InterruptedException {
        // TODO behaving differently from browser in some cases of selfcollapsing margins
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear09Test.html"), new File(destinationFolder + "clear09Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear09Test.pdf", sourceFolder + "cmp_clear09Test.pdf", destinationFolder, "diff09_"));
    }

    @Test
    public void clear10Test() throws IOException, InterruptedException {
        // TODO behaving differently from browser in some cases of selfcollapsing margins
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear10Test.html"), new File(destinationFolder + "clear10Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear10Test.pdf", sourceFolder + "cmp_clear10Test.pdf", destinationFolder, "dif10_"));
    }

    @Test
    public void clear11Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "clear11Test.html"), new File(destinationFolder + "clear11Test.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "clear11Test.pdf", sourceFolder + "cmp_clear11Test.pdf", destinationFolder, "dif11_"));
    }

    private void runTest(String testName, String diff) throws IOException, InterruptedException {
        String htmlName = sourceFolder + testName + ".html";
        String outFileName = destinationFolder + testName + ".pdf";
        String cmpFileName = sourceFolder + "cmp_" + testName + ".pdf";
        HtmlConverter.convertToPdf(new File(htmlName), new File(outFileName));
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, diff));
    }

}
