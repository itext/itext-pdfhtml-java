/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
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
package com.itextpdf.html2pdf.css.media.page;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class PageMarginBoxIntegrationTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/PageMarginBoxIntegrationTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/PageMarginBoxIntegrationTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void headerFooterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerFooter", sourceFolder, destinationFolder);
    }

    @Test
    public void pageWidthContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("css-page-width-content", sourceFolder, destinationFolder);
    }

    @Test
    public void pageWidthElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("css-page-width-element", sourceFolder, destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topLeftAndCenter() throws IOException, InterruptedException {
        convertToPdfAndCompare("topLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topLeftAndCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topLeftAndCenterAndRight", sourceFolder, destinationFolder);
    }

    //Bottom margin box tests
    @Test
    public void bottomOnlyLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomOnlyLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomOnlyRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomOnlyRight", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomLeftAndCenter() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void bottomLeftAndCenterAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomLeftAndCenterAndRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndCenterAndBottom", sourceFolder, destinationFolder);
    }

    //Right margin box tests
    @Test
    public void rightOnlyTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightOnlyTop", sourceFolder, destinationFolder);
    }

    @Test
    public void rightOnlyCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightOnlyCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void rightOnlyBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightOnlyBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void rightTopAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightTopAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void rightTopAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightTopAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void rightCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void rightTopAndCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rightTopAndCenterAndBottom", sourceFolder, destinationFolder);
    }

    //Edge-case test
    @Test
    public void largeAutoLeftRegularCenterTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoLeftRegularCenterTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeAutoLeftTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeAutoLeftTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeAutoCenterRegularSidesTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoCenterRegularSidesTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedLeftRegularCenterTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedLeftRegularCenterTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeFixedLeftTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeFixedLeftTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void smallFixedLeftTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallFixedLeftTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedCenterRegularSidesTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedCenterRegularSidesTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeAutoTopRegularCenterLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoTopRegularCenterLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeAutoTopLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeAutoTopLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeAutoCenterRegularSidesLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeAutoCenterRegularSidesLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedTopRegularCenterLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedTopRegularCenterLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeFixedTopLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeFixedTopLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void smallFixedTopLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallFixedTopLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedCenterRegularSidesLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedCenterRegularSidesLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedAllLeftRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedAllLeftRightTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedAllTopBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedAllTopBottomTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedSidesAutoMiddleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedSidesAutoMiddleTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedSidesFixedMiddleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedSidesFixedMiddleTest", sourceFolder, destinationFolder);
    }

    @Test
    public void largeFixedSidesNoMiddleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("largeFixedSidesNoMiddleTest", sourceFolder, destinationFolder);
    }

    @Test
    public void allFixedWithMBPTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("allFixedWithMBPTest", sourceFolder, destinationFolder);
    }

    @Test
    public void percentageVerticalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("percentageVerticalTest", sourceFolder, destinationFolder);
    }

    @Test
    public void cornerPrecisionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cornerPrecisionTest", sourceFolder, destinationFolder);
    }

    @Test
    public void negativeMarginsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("negativeMarginsTest", sourceFolder, destinationFolder);
    }

    @Test
    public void hugeImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("hugeImageTest", sourceFolder, destinationFolder);
    }
}
