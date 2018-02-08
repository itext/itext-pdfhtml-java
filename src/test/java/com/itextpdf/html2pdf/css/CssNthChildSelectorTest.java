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
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(IntegrationTest.class)
public class CssNthChildSelectorTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssNthChildSelectorTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssNthChildSelectorTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void nthChildEvenTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceNthChildEvenTest.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceNthChildEvenTest.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceNthChildEvenTest.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffNthChildEven_"));
    }

    @Test
    public void nthChildExpressionTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceNthChildExpressionTest.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceNthChildExpressionTest.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceNthChildExpressionTest.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffNthChildExpression_"));
    }

    @Test
    public void nthOfTypeNegativeExpressionTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceNthOfTypeNegativeExpressionTest.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceNthOfTypeNegativeExpressionTest.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceNthOfTypeNegativeExpressionTest.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffNthOfTypeNegativeExpression_"));
    }

    @Test
    public void nthChildIntegerTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceNthChildIntegerTest.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceNthChildIntegerTest.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceNthChildIntegerTest.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffNthChildInteger_"));
    }

    @Test
    public void firstChildTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceFirstChildTest.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceFirstChildTest.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceFirstChildTest.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffFirstChild_"));
    }

    @Test
    public void lastChildTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceLastChildTest.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceLastChildTest.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceLastChildTest.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffLastChild_"));
    }

    @Test
    public void notExpressionChildTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceNotExpressionChildTest.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceNotExpressionChildTest.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceNotExpressionChildTest.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffNotExpChild_"));
    }
}
