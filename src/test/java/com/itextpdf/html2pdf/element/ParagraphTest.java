/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.element;

import com.itextpdf.forms.logs.FormsLogMessageConstants;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
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
public class ParagraphTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/ParagraphTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/ParagraphTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void paragraphTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphTest01.pdf",
                SOURCE_FOLDER + "cmp_paragraphTest01.pdf", DESTINATION_FOLDER, "diff01_"));
    }

    @Test
    public void paragraphWithBordersTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithBordersTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphWithBordersTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithBordersTest01.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithBordersTest01.pdf", DESTINATION_FOLDER, "diff02_"));
    }

    @Test
    public void paragraphWithMarginsTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithMarginsTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphWithMarginsTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithMarginsTest01.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithMarginsTest01.pdf", DESTINATION_FOLDER, "diff03_"));
    }

    @Test
    public void paragraphWithPaddingTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithPaddingTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphWithPaddingTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithPaddingTest01.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithPaddingTest01.pdf", DESTINATION_FOLDER, "diff04_"));
    }

    @Test
    public void paragraphWithFontAttributesTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithFontAttributesTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphWithFontAttributesTest01.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithFontAttributesTest01.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithFontAttributesTest01.pdf", DESTINATION_FOLDER, "diff05_"));
    }

    @Test
    public void paragraphWithNonBreakableSpaceTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithNonBreakableSpaceTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphWithNonBreakableSpaceTest01.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithNonBreakableSpaceTest01.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithNonBreakableSpaceTest01.pdf", DESTINATION_FOLDER, "diff06_"));
    }

    @Test
    public void paragraphWithNonBreakableSpaceTest02() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithNonBreakableSpaceTest02.html"),
                new File(DESTINATION_FOLDER + "paragraphWithNonBreakableSpaceTest02.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithNonBreakableSpaceTest02.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithNonBreakableSpaceTest02.pdf", DESTINATION_FOLDER, "diff07_"));
    }

    @Test
    public void paragraphWithNonBreakableSpaceTest03() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithNonBreakableSpaceTest03.html"),
                new File(DESTINATION_FOLDER + "paragraphWithNonBreakableSpaceTest03.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithNonBreakableSpaceTest03.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithNonBreakableSpaceTest03.pdf", DESTINATION_FOLDER, "diff08_"));
    }

    @Test
    public void paragraphInTablePercentTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphInTablePercentTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphInTablePercentTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphInTablePercentTest01.pdf",
                SOURCE_FOLDER + "cmp_paragraphInTablePercentTest01.pdf", DESTINATION_FOLDER, "diff09_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = FormsLogMessageConstants.DUPLICATE_EXPORT_VALUE, count = 2),
    })
    public void paragraphWithButtonInputLabelSelectTextareaTest() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2445 fix
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithButtonInputLabelSelectTextareaTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithButtonInputLabelSelectTextareaTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + "paragraphWithButtonInputLabelSelectTextareaTest.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithButtonInputLabelSelectTextareaTest.pdf", DESTINATION_FOLDER,
                "diff11_"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 2)})
    @Test
    public void paragraphWithBdoBrImgMapQSubSupTest() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2445 fix
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithBdoBrImgMapQSubSupTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithBdoBrImgMapQSubSupTest.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithBdoBrImgMapQSubSupTest.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithBdoBrImgMapQSubSupTest.pdf", DESTINATION_FOLDER, "diff12_"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 2)})
    @Test
    public void paragraphWithAbbrAcronymCireCodeDfnEmKbdSampVarTest() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2445 fix
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithAbbrAcronymCireCodeDfnEmKbdSampVarTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithAbbrAcronymCireCodeDfnEmKbdSampVarTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + "paragraphWithAbbrAcronymCireCodeDfnEmKbdSampVarTest.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithAbbrAcronymCireCodeDfnEmKbdSampVarTest.pdf", DESTINATION_FOLDER,
                "diff13_"));
    }

    @Test
    public void paragraphWithAParagraphSpanDivTest() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2445 fix
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithAParagraphSpanDivTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithAParagraphSpanDivTest.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithAParagraphSpanDivTest.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithAParagraphSpanDivTest.pdf", DESTINATION_FOLDER, "diff14_"));
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 2)})
    @Test
    public void paragraphWithBBigISmallTtStrongTest() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2445 fix
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithBBigISmallTtStrongTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithBBigISmallTtStrongTest.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithBBigISmallTtStrongTest.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithBBigISmallTtStrongTest.pdf", DESTINATION_FOLDER, "diff15_"));
    }

    @Test
    public void paragraphWithPDisplayTableTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithPDisplayTableTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithPDisplayTableTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithPDisplayTableTest.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithPDisplayTableTest.pdf", DESTINATION_FOLDER, "diff15_"));
    }

    @Test
    public void paragraphWithDifferentSpansTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithDifferentSpansTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithDifferentSpansTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithDifferentSpansTest.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithDifferentSpansTest.pdf", DESTINATION_FOLDER, "diff15_"));
    }

    @Test
    public void paragraphWithDifferentBlocksAndDisplaysTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithDifferentBlocksAndDisplaysTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithDifferentBlocksAndDisplaysTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + "paragraphWithDifferentBlocksAndDisplaysTest.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithDifferentBlocksAndDisplaysTest.pdf", DESTINATION_FOLDER, "diff15_"));
    }

    @Test
    public void paragraphWithLabelSpanDisplayBlockTest() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2619 fix
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithLabelSpanDisplayBlockTest.html"),
                new File(DESTINATION_FOLDER + "paragraphWithLabelSpanDisplayBlockTest.pdf"));
        Assert.assertNull(
                new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithLabelSpanDisplayBlockTest.pdf",
                        SOURCE_FOLDER + "cmp_paragraphWithLabelSpanDisplayBlockTest.pdf", DESTINATION_FOLDER, "diff15_"));
    }

    @Test
    public void paragraphWithImageTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithImageTest01.html"),
                new File(DESTINATION_FOLDER + "paragraphWithImageTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithImageTest01.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithImageTest01.pdf", DESTINATION_FOLDER,
                "diff_paragraphWithImageTest01_"));
    }

    @Test
    public void paragraphWithImageTest01RTL() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "paragraphWithImageTest01RTL.html"),
                new File(DESTINATION_FOLDER + "paragraphWithImageTest01RTL.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "paragraphWithImageTest01RTL.pdf",
                SOURCE_FOLDER + "cmp_paragraphWithImageTest01RTL.pdf", DESTINATION_FOLDER,
                "diff_paragraphWithImageTest01_"));
    }

    @Test
    public void helloWorldParagraphTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_table", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphJunkSpacesDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_junk_spaces", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphNestedInTableDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_nested_in_table", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void helloParagraphWithSpansDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hello_paragraph_with_span", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void aBlockInPTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("aBlockInPTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

}
