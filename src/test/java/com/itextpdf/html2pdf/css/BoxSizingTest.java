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
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BoxSizingTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BoxSizingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BoxSizingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void boxSizingCellContentTest01() throws IOException, InterruptedException {
        runTest("boxSizingCellContentTest01");
    }

    @Test
    public void boxSizingCellContentTest02() throws IOException, InterruptedException {
        runTest("boxSizingCellContentTest02");
    }

    @Test
    public void boxSizingCellTest01() throws IOException, InterruptedException {
        // TODO Result of processing of this html is different from what browsers show.
        // Height of cells is always border-box-like at least if DOCTYPE "html" is not specified.
        // See also boxSizingCellTest03.
        runTest("boxSizingCellTest01");
    }

    @Test
    public void boxSizingCellTest02() throws IOException, InterruptedException {
        runTest("boxSizingCellTest02");
    }

    @Test
    public void boxSizingCellTest03() throws IOException, InterruptedException {
        // This test is exactly the same as boxSizingCellTest01, except DOCTYPE "html" is used:
        // cells height is different in browsers depending on box-sizing.

        // TODO: we don't include half of the borders in height calculation when border-box is set
        // because we apply borders on table level. However, this seems to be not very important for heights,
        // height will only be bigger and it's not that crucial in comparison to width calculations.
        runTest("boxSizingCellTest03");
    }

    @Test
    public void boxSizingFloat01Test() throws IOException, InterruptedException {
        runTest("boxSizingFloat01Test");
    }

    @Test
    public void boxSizingFloat02Test() throws IOException, InterruptedException {
        runTest("boxSizingFloat02Test");
    }

    @Test
    public void boxSizingRelativeWidth01Test() throws IOException, InterruptedException {
        runTest("boxSizingRelativeWidth01Test");
    }

    @Test
    public void boxSizingRelativeWidth02Test() throws IOException, InterruptedException {
        runTest("boxSizingRelativeWidth02Test");
    }

    @Test
    public void boxSizingRelativeWidth03Test() throws IOException, InterruptedException {
        runTest("boxSizingRelativeWidth03Test");
    }

    @Test
    public void boxSizingDiv01Test() throws IOException, InterruptedException {
        runTest("boxSizingDiv01Test");
    }

    @Test
    public void boxSizingDiv03Test() throws IOException, InterruptedException {
        runTest("boxSizingDiv03Test");
    }

    @Test
    public void boxSizingDiv04Test() throws IOException, InterruptedException {
        // TODO inner div still doesn't fit, because it's height is increased every time page split occurs by margins borders padding
        // Thus, if parent height was manually fixed to include child with fixed height and if page split occurs - child might not fit.
        runTest("boxSizingDiv04Test");
    }

    @Test
    public void boxSizingPara01Test() throws IOException, InterruptedException {
        runTest("boxSizingPara01Test");
    }

    @Test
    public void boxSizingPara03Test() throws IOException, InterruptedException {
        runTest("boxSizingPara03Test");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 2))
    public void boxSizingTable01Test() throws IOException, InterruptedException {
        runTest("boxSizingTable01Test");
    }

    @Test
    public void boxSizingTable02Test() throws IOException, InterruptedException {
        runTest("boxSizingTable02Test");
    }

    @Test
    public void boxSizingTable04Test() throws IOException, InterruptedException {
        runTest("boxSizingTable04Test");
    }

    @Test
    public void boxSizingTable05Test() throws IOException, InterruptedException {
        runTest("boxSizingTable05Test");
    }

    @Test
    public void boxSizingTable06Test() throws IOException, InterruptedException {
        runTest("boxSizingTable06Test");
    }

    @Test
    public void boxSizingMinMaxHeight01Test() throws IOException, InterruptedException {
        runTest("boxSizingMinMaxHeight01Test");
    }

    @Test
    public void boxSizingInlineBlock01Test() throws IOException, InterruptedException {
        runTest("boxSizingInlineBlock01Test");
    }

    @Test
    public void boxSizingInlineBlock02Test() throws IOException, InterruptedException {
        runTest("boxSizingInlineBlock02Test");
    }

    @Test
    public void boxSizingFormTest01() throws IOException, InterruptedException {
        runTest("boxSizingFormTest01");
    }

    @Test
    public void boxSizingFormTest02() throws IOException, InterruptedException {
        runTest("boxSizingFormTest02"); // TODO we don't apply height to textarea yet
    }

    @Test
    public void boxSizingFormTest03() throws IOException, InterruptedException {
        runTest("boxSizingFormTest03"); // TODO at least in chrome, borders of buttons are always included to width and height (just as with border-box)
    }

    @Test
    public void boxSizingLiTest01() throws IOException, InterruptedException {
        runTest("boxSizingLiTest01");
    }

    @Test
    public void boxSizingLiTest02() throws IOException, InterruptedException {
        runTest("boxSizingLiTest02");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        String htmlName = sourceFolder + testName + ".html";
        String outFileName = destinationFolder + testName + ".pdf";
        String cmpFileName = sourceFolder + "cmp_" + testName + ".pdf";
        HtmlConverter.convertToPdf(new File(htmlName), new File(outFileName));
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff_" + testName + "_"));
    }
}
