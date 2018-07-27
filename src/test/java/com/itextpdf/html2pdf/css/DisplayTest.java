/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
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
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class DisplayTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/DisplayTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/DisplayTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void displayTable01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_table01.html"), new File(destinationFolder + "display_table01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table01.pdf", sourceFolder + "cmp_display_table01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH)
    })
    public void displayTable02Test() throws IOException, InterruptedException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + "display_table02.pdf"));
        pdfDoc.setDefaultPageSize(new PageSize(1500, 842));
        HtmlConverter.convertToPdf(new FileInputStream(sourceFolder + "display_table02.html"), pdfDoc);
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table02.pdf", sourceFolder + "cmp_display_table02.pdf", destinationFolder, "diff20_"));
    }

    @Test
    public void displayTable03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_table03.html"), new File(destinationFolder + "display_table03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table03.pdf", sourceFolder + "cmp_display_table03.pdf", destinationFolder, "diff21_"));
    }

    @Test
    public void displayTable04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_table04.html"), new File(destinationFolder + "display_table04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table04.pdf", sourceFolder + "cmp_display_table04.pdf", destinationFolder, "diff22_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH)
    })
    public void displayTable05Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_table05.html"), new File(destinationFolder + "display_table05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table05.pdf", sourceFolder + "cmp_display_table05.pdf", destinationFolder, "diff23_"));
    }

    @Test
    public void displayTable06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_table06.html"), new File(destinationFolder + "display_table06.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table06.pdf", sourceFolder + "cmp_display_table06.pdf", destinationFolder, "diff24_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH),
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 1)
    })
    public void displayTable07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_table07.html"), new File(destinationFolder + "display_table07.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table07.pdf", sourceFolder + "cmp_display_table07.pdf", destinationFolder, "diff24_"));
    }

    @Test
    public void displayTable08Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_table08.html"), new File(destinationFolder + "display_table08.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_table08.pdf", sourceFolder + "cmp_display_table08.pdf", destinationFolder, "diff25_"));
    }
    @Test
    public void displayInline01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline01.html"), new File(destinationFolder + "display_inline01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline01.pdf", sourceFolder + "cmp_display_inline01.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void displayInlineBlock01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block01.html"), new File(destinationFolder + "display_inline-block01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block01.pdf", sourceFolder + "cmp_display_inline-block01.pdf", destinationFolder, "diff03_"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.INLINE_BLOCK_ELEMENT_WILL_BE_CLIPPED)})
    public void displayInlineBlock02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block02.html"), new File(destinationFolder + "display_inline-block02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block02.pdf", sourceFolder + "cmp_display_inline-block02.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void displayInlineBlock03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block03.html"), new File(destinationFolder + "display_inline-block03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block03.pdf", sourceFolder + "cmp_display_inline-block03.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void displayInlineBlock04Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block04.html"), new File(destinationFolder + "display_inline-block04.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block04.pdf", sourceFolder + "cmp_display_inline-block04.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void displayInlineBlock05Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block05.html"), new File(destinationFolder + "display_inline-block05.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block05.pdf", sourceFolder + "cmp_display_inline-block05.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void displayInlineBlock06Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block06.html"), new File(destinationFolder + "display_inline-block06.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block06.pdf", sourceFolder + "cmp_display_inline-block06.pdf", destinationFolder, "diff08_"));
    }

    @Test
    public void displayInlineBlock07Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block07.html"), new File(destinationFolder + "display_inline-block07.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block07.pdf", sourceFolder + "cmp_display_inline-block07.pdf", destinationFolder, "diff09_"));
    }

    @Test
    public void displayInlineBlock08Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block08.html"), new File(destinationFolder + "display_inline-block08.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block08.pdf", sourceFolder + "cmp_display_inline-block08.pdf", destinationFolder, "diff10_"));
    }

    @Test
    public void displayInlineBlock09Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block09.html"), new File(destinationFolder + "display_inline-block09.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block09.pdf", sourceFolder + "cmp_display_inline-block09.pdf", destinationFolder, "diff11_"));
    }

    @Test
    public void displayInlineBlock10Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block10.html"), new File(destinationFolder + "display_inline-block10.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block10.pdf", sourceFolder + "cmp_display_inline-block10.pdf", destinationFolder, "diff12_"));
    }

    @Test
    public void displayInlineBlock11Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block11.html"), new File(destinationFolder + "display_inline-block11.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block11.pdf", sourceFolder + "cmp_display_inline-block11.pdf", destinationFolder, "diff13_"));
    }

    @Test
    public void displayInlineBlock12Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block12.html"), new File(destinationFolder + "display_inline-block12.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block12.pdf", sourceFolder + "cmp_display_inline-block12.pdf", destinationFolder, "diff14_"));
    }

    @Test
    public void displayInlineBlock13Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block13.html"), new File(destinationFolder + "display_inline-block13.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block13.pdf", sourceFolder + "cmp_display_inline-block13.pdf", destinationFolder, "diff15_"));
    }

    @Test
    public void displayInlineBlock14Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block14.html"), new File(destinationFolder + "display_inline-block14.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block14.pdf", sourceFolder + "cmp_display_inline-block14.pdf", destinationFolder, "diff16_"));
    }

    @Test
    public void displayInlineBlock15Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block15.html"), new File(destinationFolder + "display_inline-block15.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block15.pdf", sourceFolder + "cmp_display_inline-block15.pdf", destinationFolder, "diff17_"));
    }

    @Test
    public void displayInlineBlock16Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block16.html"), new File(destinationFolder + "display_inline-block16.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block16.pdf", sourceFolder + "cmp_display_inline-block16.pdf", destinationFolder, "diff18_"));
    }

    @Test
    public void displayInlineBlock17Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "display_inline-block17.html"), new File(destinationFolder + "display_inline-block17.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "display_inline-block17.pdf", sourceFolder + "cmp_display_inline-block17.pdf", destinationFolder, "diff19_"));
    }

}
