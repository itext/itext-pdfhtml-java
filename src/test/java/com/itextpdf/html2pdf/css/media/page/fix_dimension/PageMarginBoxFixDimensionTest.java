/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
package com.itextpdf.html2pdf.css.media.page.fix_dimension;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class PageMarginBoxFixDimensionTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/fix_dimension/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/fix_dimension/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyLeftFixPxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPx", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixPtTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPt", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixPercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPercent", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixInTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixIn", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixCmTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixCm", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixMmTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixMm", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixPcTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixPc", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixEmTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixEm", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyLeftFixExTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeftFixEx", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyRightFixPercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyRightFixPercent", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyCenterFixPercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyCenterFixPercent", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixCenterAndRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixLeftAndFixCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixLeftAndFixCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topFixLeftAndFixCenterAndFixRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topFixLeftAndFixCenterAndFixRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyFixTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyFixTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyFixCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyFixCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyFixBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyFixBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndFixCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndFixCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndFixBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndFixBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftFixCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftFixCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftFixTopAndFixCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftFixTopAndFixCenterAndBottom", sourceFolder, destinationFolder);
    }
}
