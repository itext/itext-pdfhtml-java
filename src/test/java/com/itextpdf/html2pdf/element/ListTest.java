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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.events.utils.MessageFormatUtil;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(IntegrationTest.class)
public class ListTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ListTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ListTest/";

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void listTest01() throws IOException, InterruptedException {
        runTest("listTest01");
    }

    @Test
    public void listTest02() throws IOException, InterruptedException {
        runTest("listTest02");
    }

    @Test
    public void listTest03() throws IOException, InterruptedException {
        runTest("listTest03");
    }

    @Test
    public void listTest04() throws IOException, InterruptedException {
        runTest("listTest04");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.NOT_SUPPORTED_LIST_STYLE_TYPE, count = 32)})
    public void listTest05() throws IOException, InterruptedException {
        runTest("listTest05");
    }

    @Test
    public void listTest06() throws IOException, InterruptedException {
        runTest("listTest06");
    }

    @Test
    public void listTest07() throws IOException, InterruptedException {
        runTest("listTest07");
    }

    @Test
    public void listTest08() throws IOException, InterruptedException {
        runTest("listTest08");
    }

    @Test
    public void listTest09() throws IOException, InterruptedException {
        runTest("listTest09");
    }

    @Test
    public void listTest10() throws IOException, InterruptedException {
        runTest("listTest10");
    }

    @Test
    public void listTest11() throws IOException, InterruptedException {
        runTest("listTest11");
    }

    @Test
    public void listTest12() throws IOException, InterruptedException {
        runTest("listTest12");
    }

    @Test
    public void listTest13() throws IOException, InterruptedException {
        runTest("listTest13");
    }

    @Test
    public void listTest14() throws IOException, InterruptedException {
        runTest("listTest14");
    }

    @Test
    public void listTest15() throws IOException, InterruptedException {
        runTest("listTest15");
    }

    @Test
    public void listTest16() throws IOException, InterruptedException {
        runTest("listTest16");
    }

    @Test
    public void listTest17() throws IOException, InterruptedException {
        runTest("listTest17");
    }

    @Test
    public void listTest18() throws IOException, InterruptedException {
        runTest("listTest18");
    }

    @Test
    public void listTest19() throws IOException, InterruptedException {
        runTest("listTest19");
    }

    @Test
    public void listTest20() throws IOException, InterruptedException {
        runTest("listTest20");
    }

    @Test
    public void listLiValuePropertyTest() throws IOException, InterruptedException {
        runTest("listLiValuePropertyTest");
    }

    @Test
    public void listStartPropertyTest() throws IOException, InterruptedException {
        runTest("listStartPropertyTest");
    }

    @Test
    public void listItemValueTest() throws IOException, InterruptedException {
        runTest("listItemValueTest");
    }

    @Test
    public void listItemValueTest01() throws IOException, InterruptedException {
        runTest("listItemValueTest01");
    }
    @Test
    public void listItemValueTest02() throws IOException, InterruptedException {
        runTest("listItemValueTest02");
    }
    @Test
    public void descendingListTest() throws IOException, InterruptedException {
        runTest("descendingListTest");
    }

    @Test
    @Ignore("DEVSIX-2431")
    public void listItemAbsolutePositionTest() throws IOException, InterruptedException {
        runTest("list-item-absolute");
    }

    @Test
    //TODO: update after fix of DEVSIX-2537
    //http://www.timrivera.com/tests/ol-start.html
    public void checkOrderedListStartAndValue() throws IOException, InterruptedException {
        runTest("checkOrderedListStartAndValue");
    }
    @Test
    //TODO: update after fix of DEVSIX-2538
    public void checkOrderedListNestedLists() throws IOException, InterruptedException {
        junitExpectedException.expect(IllegalArgumentException.class);
        junitExpectedException.expectMessage(MessageFormatUtil.format("The parameter must be a positive integer"));
        runTest("checkOrderedListNestedLists");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 6)
    })
        //TODO: update after DEVSIX-2093, DEVSIX-2092, DEVSIX-2091 fixes
        public void listsWithInlineChildren() throws IOException, InterruptedException {
            runTest("listsWithInlineChildren");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.NOT_SUPPORTED_LIST_STYLE_TYPE, count = 32)})
    public void listToPdfaTest() throws IOException, InterruptedException {
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");
        PdfADocument pdfADocument = new PdfADocument(new PdfWriter(destinationFolder + "listToPdfa.pdf"), PdfAConformanceLevel.PDF_A_1B, new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is));
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "listToPdfa.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfADocument, new ConverterProperties()
                    .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT))
                    .setFontProvider(new DefaultFontProvider(false, true, false)));
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "listToPdfa.pdf", sourceFolder + "cmp_listToPdfa.pdf", destinationFolder, "diff99_"));
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + testName + ".html"), new File(destinationFolder + testName + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf", sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, "diff_" + testName));
    }

}
