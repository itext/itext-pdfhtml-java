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
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class TextPropertiesTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/TextPropertiesTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/TextPropertiesTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void textAlignTest01() throws IOException, InterruptedException {
        runTest("textAlignTest01");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.TEXT_DECORATION_BLINK_NOT_SUPPORTED)})
    public void textDecorationTest01() throws IOException, InterruptedException {
        runTest("textDecorationTest01");
    }

    @Test
    public void letterSpacingTest01() throws IOException, InterruptedException {
        runTest("letterSpacingTest01");
    }

    @Test
    public void wordSpacingTest01() throws IOException, InterruptedException {
        runTest("wordSpacingTest01");
    }

    @Test
    public void lineHeightTest01() throws IOException, InterruptedException {
        runTest("lineHeightTest01");
    }

    @Test
    public void lineHeightTest02() throws IOException, InterruptedException {
        runTest("lineHeightTest02");
    }

    @Test
    public void lineHeightTest03() throws IOException, InterruptedException {
        runTest("lineHeightTest03");
    }

    @Test
    public void whiteSpaceTest01() throws IOException, InterruptedException {
        runTest("whiteSpaceTest01");
    }

    @Test
    public void textTransformTest01() throws IOException, InterruptedException {
        runTest("textTransformTest01");
    }

    @Test
    public void textTransform02Test() throws IOException, InterruptedException {
        runTest("textTransformTest02");
    }

    @Test
    public void whiteSpaceTest02() throws IOException, InterruptedException {
        runTest("whiteSpaceTest02");
    }

    @Test
    public void enspEmspThinspTest01() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest01");
    }

    @Test
    public void enspEmspThinspTest02() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest02");
    }

    @Test
    public void enspEmspThinspTest03() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest03");
    }

    @Test
    public void enspEmspThinspTest04() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest04");
    }

    @Test
    public void enspEmspThinspTest05() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest05");
    }

    @Test
    public void enspEmspThinspTest06() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest06");
    }

    @Test
    public void enspEmspThinspTest07() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest07");
    }

    @Test
    public void enspEmspThinspTest08() throws IOException, InterruptedException {
        // TODO DEVSIX-1442
        runTest("enspEmspThinspTest08");
    }

    @Test
    @Ignore("DEVSIX-1442")
    public void enspEmspThinspTest09() throws IOException, InterruptedException {
        runTest("enspEmspThinspTest09");
    }

    @Test
    @Ignore("DEVSIX-1851")
    public void wordCharSpacingJustifiedTest01() throws IOException, InterruptedException {
        runTest("wordCharSpacingJustifiedTest01");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + testName + ".html"), new File(destinationFolder + testName + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf", sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, "diff_" + testName));
    }

}
