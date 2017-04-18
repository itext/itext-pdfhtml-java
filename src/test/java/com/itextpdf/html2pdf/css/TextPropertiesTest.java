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
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void textAlign01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "textAlignTest01.html"), new File(destinationFolder + "textAlignTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "textAlignTest01.pdf", sourceFolder + "cmp_textAlignTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.TEXT_DECORATION_BLINK_NOT_SUPPORTED)})
    public void textDecoration01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "textDecorationTest01.html"), new File(destinationFolder + "textDecorationTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "textDecorationTest01.pdf", sourceFolder + "cmp_textDecorationTest01.pdf", destinationFolder, "diff02_"));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED)})
    public void textIndent01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "textIndentTest01.html"), new File(destinationFolder + "textIndentTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "textIndentTest01.pdf", sourceFolder + "cmp_textIndentTest01.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void letterSpacing01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "letterSpacingTest01.html"), new File(destinationFolder + "letterSpacingTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "letterSpacingTest01.pdf", sourceFolder + "cmp_letterSpacingTest01.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void wordSpacing01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "wordSpacingTest01.html"), new File(destinationFolder + "wordSpacingTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "wordSpacingTest01.pdf", sourceFolder + "cmp_wordSpacingTest01.pdf", destinationFolder, "diff05_"));
    }

    @Test
    public void lineHeight01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "lineHeightTest01.html"), new File(destinationFolder + "lineHeightTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "lineHeightTest01.pdf", sourceFolder + "cmp_lineHeightTest01.pdf", destinationFolder, "diff06_"));
    }

    @Test
    public void direction01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "directionTest01.html"), new File(destinationFolder + "directionTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "directionTest01.pdf", sourceFolder + "cmp_directionTest01.pdf", destinationFolder, "diff07_"));
    }

    @Test
    public void whiteSpace01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "whiteSpaceTest01.html"), new File(destinationFolder + "whiteSpaceTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "whiteSpaceTest01.pdf", sourceFolder + "cmp_whiteSpaceTest01.pdf", destinationFolder, "diff08_"));
    }

    @Test
    public void textTransform01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "textTransformTest01.html"), new File(destinationFolder + "textTransformTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "textTransformTest01.pdf", sourceFolder + "cmp_textTransformTest01.pdf", destinationFolder, "diff09_"));
    }

    @Test
    public void whiteSpace02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "whiteSpaceTest02.html"), new File(destinationFolder + "whiteSpaceTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "whiteSpaceTest02.pdf", sourceFolder + "cmp_whiteSpaceTest02.pdf", destinationFolder, "diff10_"));
    }
}
