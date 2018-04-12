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
import com.itextpdf.io.util.UrlUtil;
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
public class HeightTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/HeightTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/HeightTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void heightTest01() throws IOException, InterruptedException {
        String testName = "heightTest01";
        String diffPrefix = "diff01_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest02() throws IOException, InterruptedException {
        String testName = "heightTest02";
        String diffPrefix = "diff02_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest03() throws IOException, InterruptedException {
        String testName = "heightTest03";
        String diffPrefix = "diff03_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest04() throws IOException, InterruptedException {
        String testName = "heightTest04";
        String diffPrefix = "diff04_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest05() throws IOException, InterruptedException {
        String testName = "heightTest05";
        String diffPrefix = "diff05_";

        runTest(testName, diffPrefix);
    }

    @Test
    @Ignore("DEVSIX-1007")
    public void heightTest06() throws IOException, InterruptedException {
        String testName = "heightTest06";
        String diffPrefix = "diff06_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest07() throws IOException, InterruptedException {
        String testName = "heightTest07";
        String diffPrefix = "diff07_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest08() throws IOException, InterruptedException {
        String testName = "heightTest08";
        String diffPrefix = "diff08_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest09() throws IOException, InterruptedException {
        String testName = "heightTest09";
        String diffPrefix = "diff09_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest10() throws IOException, InterruptedException {
        String testName = "heightTest10";
        String diffPrefix = "diff10_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest11() throws IOException, InterruptedException {
        String testName = "heightTest11";
        String diffPrefix = "diff11_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest12() throws IOException, InterruptedException {
        String testName = "heightTest12";
        String diffPrefix = "diff12_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest13() throws IOException, InterruptedException{
        String testName = "heightTest13";
        String diffPrefix = "diff13_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest14() throws IOException, InterruptedException{
        String testName = "heightTest14";
        String diffPrefix = "diff14_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest15() throws IOException, InterruptedException{
        String testName = "heightTest15";
        String diffPrefix = "diff15_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest16() throws IOException, InterruptedException{
        String testName = "heightTest16";
        String diffPrefix = "diff16_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightTest17() throws IOException, InterruptedException{
        String testName = "heightTest17";
        String diffPrefix = "diff17_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightWithCollapsingMarginsTest01() throws IOException, InterruptedException {
        String testName = "heightWithCollapsingMarginsTest01";
        String diffPrefix = "diffMargins01_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightWithCollapsingMarginsTest03() throws IOException, InterruptedException {
        String testName = "heightWithCollapsingMarginsTest03";
        String diffPrefix = "diffMargins03_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightWithCollapsingMarginsTest04() throws IOException, InterruptedException {
        String testName = "heightWithCollapsingMarginsTest04";
        String diffPrefix = "diffMargins04_";

        // second paragraph should not be drawn in pdf, as it doesn't fit with it's margins

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightLargerThanMinHeight01() throws IOException, InterruptedException {
        // TODO DEVSIX-1895: height differs from the browser rendering due to incorrect resolving of max-height/height properties
        String testName = "heightLargerThanMinHeight01";
        String diffPrefix = "diffLargerMin01_";

        runTest(testName, diffPrefix);
    }

    @Test
    public void heightLesserThanMaxHeight01() throws IOException, InterruptedException {
        // TODO DEVSIX-1895: height differs from the browser rendering due to incorrect resolving of max-height/height properties
        String testName = "heightLesserThanMaxHeight01";
        String diffPrefix = "diffLessMax01_";

        runTest(testName, diffPrefix);
    }

    public void runTest(String testName, String diffPrefix) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + testName + ".html"), new File(destinationFolder + testName + ".pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + testName + ".html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf", sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, diffPrefix));
    }
}
