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
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.itextpdf.test.ITextTest.createOrClearDestinationFolder;

@Category(IntegrationTest.class)
public class VerticalAlignmentTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalAlignmentTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/VerticalAlignmentTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void verticalAlignmentTest01() throws IOException, InterruptedException {
        // TODO 'top' and 'bottom' values are not supported for now
        runTest("verticalAlignmentTest01");
    }
    
    @Test
    public void verticalAlignmentTest02() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest02");
    }
    
    @Test
    public void verticalAlignmentTest03() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest03");
    }
    
    @Test
    public void verticalAlignmentTest05() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest05");
    }
    
    @Test
    public void verticalAlignmentTest06() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest06");
    }
    
    @Test
    public void verticalAlignmentTest07() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest07");
    }
    
    @Test
    public void verticalAlignmentTest08() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest08");
    }
    
    @Test
    public void verticalAlignmentTest09() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest09");
    }
    
    @Test
    public void verticalAlignmentTest10() throws IOException, InterruptedException {
        // TODO interesting thing is that vertical alignment increases line height if needed, however itext doesn't in this case 
        runTest("verticalAlignmentTest10");
    }
    
    @Test
    public void verticalAlignmentTest11() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest11");
    }
    
    @Test
    public void verticalAlignmentTest12() throws IOException, InterruptedException {
        runTest("verticalAlignmentTest12");
    }
    
    @Test
    public void verticalAlignmentCellTest01() throws IOException, InterruptedException {
        runTest("verticalAlignmentCellTest01");
    }
    
    @Test
    public void verticalAlignmentCellTest02() throws IOException, InterruptedException {
        runTest("verticalAlignmentCellTest02");
    }
    
    @Test
    public void verticalAlignmentCellTest03() throws IOException, InterruptedException {
        runTest("verticalAlignmentCellTest03");
    }
    
    @Test
    public void vAlignAttributeCellTest01() throws IOException, InterruptedException {
        runTest("vAlignAttributeCellTest01");
    }
    
    @Test
    public void verticalAlignOnNestedInlines01() throws IOException, InterruptedException {
        runTest("verticalAlignOnNestedInlines01");
    }
    
    @Test
    public void verticalAlignOnNestedInlines02() throws IOException, InterruptedException {
        runTest("verticalAlignOnNestedInlines02");
    }
    
    private void runTest(String testName) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + testName + ".html"), new File(destinationFolder + testName + ".pdf"));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + testName + ".html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf", sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, "diff_" + testName + "_"));
    }
}
