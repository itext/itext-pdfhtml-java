/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class PageBreakTest extends ExtendedHtmlConversionITextTest {

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();    //Member of testing class. Add if it isn't there.

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/PageBreakTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/PageBreakTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-4521: test currently results in endless loop")
    public void breakInsideAndBreakAfterTest() throws IOException, InterruptedException {
        runTest("breakInsideAndBreakAfter");
    }

    @Test
    public void pageBreakAfter01Test() throws IOException, InterruptedException {
        runTest("page-break-after01");
    }

    @Test
    public void pageBreakAfter02Test() throws IOException, InterruptedException {
        runTest("page-break-after02");
    }

    @Test
    public void pageBreakAfter03Test() throws IOException, InterruptedException {
        runTest("page-break-after03");
    }

    @Test
    public void pageBreakAfter04Test() throws IOException, InterruptedException {
        runTest("page-break-after04");
    }

    @Test
    public void pageBreakAfter05Test() throws IOException, InterruptedException {
        runTest("page-break-after05");
    }

    @Test
    public void pageBreakBefore01Test() throws IOException, InterruptedException {
        runTest("page-break-before01");
    }

    @Test
    public void pageBreakBefore02Test() throws IOException, InterruptedException {
        runTest("page-break-before02");
    }

    @Test
    public void pageBreakBefore03Test() throws IOException, InterruptedException {
        runTest("page-break-before03");
    }

    @Test
    public void pageBreakBefore04Test() throws IOException, InterruptedException {
        runTest("page-break-before04");
    }

    @Test
    public void pageBreakBeforeAfter01Test() throws IOException, InterruptedException {
        runTest("page-break-before-after01");
    }

    @Test
    public void pageBreakBeforeAfter02Test() throws IOException, InterruptedException {
        runTest("page-break-before-after02");
    }

    @Test
    public void pageBreakAfterTable01Test() throws IOException, InterruptedException {
        runTest("page-break-after-table01");
    }

    @Test
    public void pageBreakBeforeTable01Test() throws IOException, InterruptedException {
        runTest("page-break-before-table01");
    }

    @Test
    public void pageBreakInsideAvoidInParaTest() throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidInPara");
    }

    @Test
    public void pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerClearBothTest()
            throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerClearBothTest");
    }

    @Test
    public void pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerClearBothWithTextTest()
            throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerClearBothWithTextTest");
    }

    @Test
    // TODO: DEVSIX-4720 short text div invalid layout on page break
    public void pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerDivWithShortTextTest()
            throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerDivWithShortTextTest");
    }

    @Test
    public void pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerDivsWithShortAndLongTextsTest()
            throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidWithFloatsWidth100PercentAndInnerDivsWithShortAndLongTextsTest");
    }

    @Test
    // TODO: DEVSIX-4720 short text div invalid layout on page break
    // TODO: DEVSIX-1270 simple text layout to the left of the left float
    public void pageBreakInsideAvoidWithFloatsWidth80PercentAndInnerDivWithShortTextTest()
            throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidWithFloatsWidth80PercentAndInnerDivWithShortTextTest");
    }

    @Test
    // TODO: DEVSIX-1270 simple text layout to the left of the left float
    public void pageBreakInsideAvoidWithFloatsWidth80PercentAndInnerDivsWithShortAndLongTextsTest()
            throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidWithFloatsWidth80PercentAndInnerDivsWithShortAndLongTextsTest");
    }

    @Test
    public void pageBreakAlwaysInsidePageBreakAvoidTest()
            throws IOException, InterruptedException {
        runTest("pageBreakAlwaysInsidePageBreakAvoidTest");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.CLIP_ELEMENT)})
    /* Test will fail after fix in DEVSIX-2024 */
    public void pageBreakInConstrainedDivTest() throws IOException, InterruptedException {
        junitExpectedException.expect(UnsupportedOperationException.class);
        runTest("pageBreakInConstrainedDivTest");
    }

    private void runTest(String name) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), new ConverterProperties().setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT)));
        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }

}
