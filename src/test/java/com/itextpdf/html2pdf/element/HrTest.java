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
import com.itextpdf.io.LogMessageConstant;
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
public class HrTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/HrTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/HrTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void hrHelloTest() throws IOException, InterruptedException {
        runHrTest("00");
    }

    @Test
    public void hrTest01() throws IOException, InterruptedException {
        runHrTest("01");
    }

    @Test
    public void hrTest02() throws IOException, InterruptedException {
        runHrTest("02");
    }

    @Test
    public void hrTest03() throws IOException, InterruptedException {
        runHrTest("03");
    }

    @Test
    public void hrTest04() throws IOException, InterruptedException {
        runHrTest("04");
    }

    @Test
    public void hrTest05() throws IOException, InterruptedException {
        runHrTest("05");
    }

    @Test
    public void hrTest06() throws IOException, InterruptedException {
        runHrTest("06");
    }

    @Test
    public void hrTest07() throws IOException, InterruptedException {
        runHrTest("07");
    }

    @Test
    public void hrTest08() throws IOException, InterruptedException {
        runHrTest("08");
    }

    @Test
    public void hrTest09() throws IOException, InterruptedException {
        runHrTest("09");
    }

    @Test
    public void hrTest10() throws IOException, InterruptedException {
        runHrTest("10");
    }

    @Test
    public void hrTest11() throws IOException, InterruptedException {
        runHrTest("11");
    }

    @Test
    public void hrTest12() throws IOException, InterruptedException {
        runHrTest("12");
    }

    @Test
    public void hrTest13() throws IOException, InterruptedException {
        //box-shadow property is not supported in iText
        runHrTest("13");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
    })
    public void hrTest14() throws IOException, InterruptedException {
        //gradient function is not supported in iText
        runHrTest("14");
    }

    @Test
    public void hrTest15() throws IOException, InterruptedException {
        runHrTest("15");
    }

    @Test
    public void hrTest16() throws IOException, InterruptedException {
        runHrTest("16");
    }

    @Test
    public void hrTest17() throws IOException, InterruptedException {
        runHrTest("17");
    }

    @Test
    public void hrTest18() throws IOException, InterruptedException {
        runHrTest("18");
    }

    @Test
    public void hrTest19() throws IOException, InterruptedException {
        runHrTest("19");
    }

    @Test
    public void hrTest20() throws IOException, InterruptedException {
        runHrTest("20");
    }

    @Test
    public void hrTest21() throws IOException, InterruptedException {
        runHrTest("21");
    }

    private void runHrTest(String id) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + "hrTest" + id + ".html";
        String outPdfPath = destinationFolder + "hrTest" + id + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_hrTest" + id + ".pdf";
        String diff = "diff" + id + "_";
        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdfPath));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff));
    }
}
