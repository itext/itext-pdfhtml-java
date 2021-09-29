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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BoxSizingTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BoxSizingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BoxSizingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void boxSizingCellContentTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellContentTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellContentTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellContentTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellContentImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellContentImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellTest01() throws IOException, InterruptedException {
        // TODO: DEVSIX-5468 update cmp file after fixing
        convertToPdfAndCompare("boxSizingCellTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingCellTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingCellTest03() throws IOException, InterruptedException {
        // TODO: DEVSIX-5468 update cmp file after fixing
        convertToPdfAndCompare("boxSizingCellTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFloat01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFloat01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFloat02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFloat02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingRelativeWidth01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingRelativeWidth01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingRelativeWidth02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingRelativeWidth02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingRelativeWidth03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingRelativeWidth03Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDiv01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingDiv01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDivWithImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingDivWithImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDiv03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingDiv03Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingDiv04Test() throws IOException, InterruptedException {
        // Inner div still doesn't fit, because it's height is increased every time page split occurs by margins
        // borders padding. Thus, if parent height was manually fixed to include child with fixed height and if
        // page split occurs - child might not fit.
        convertToPdfAndCompare("boxSizingDiv04Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingPara01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingPara01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingParaWithImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingParaWithImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingPara03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingPara03Test", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 2))
    public void boxSizingTable01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTableWithImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTableWithImg", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable04Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable05Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingTable06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingTable06Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingMinMaxHeight01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingMinMaxHeight01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingInlineBlock01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingInlineBlock01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingInlineBlock02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingInlineBlock02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFormTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFormTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFormTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingFormTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingFormTest03() throws IOException, InterruptedException {
        // At least in chrome, borders of buttons are always included to width and height (just as with border-box)
        convertToPdfAndCompare("boxSizingFormTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingLiTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingLiTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingLiTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingLiTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void boxSizingImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("boxSizingImage", sourceFolder, destinationFolder);
    }
}
