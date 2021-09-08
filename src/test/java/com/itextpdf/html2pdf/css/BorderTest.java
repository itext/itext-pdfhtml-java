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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BorderTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BorderTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BorderTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void border01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border01", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 4))
    public void border02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border02", sourceFolder, destinationFolder);
    }

    @Test
    public void border03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border03", sourceFolder, destinationFolder);
    }

    @Test
    public void border04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border04", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void border05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border05", sourceFolder, destinationFolder);
    }

    @Test
    public void border06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border06", sourceFolder, destinationFolder);
    }

    @Test
    public void border07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border07", sourceFolder, destinationFolder);
    }

    @Test
    public void border08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border08", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void border09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border09", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2))
    public void border10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("border10", sourceFolder, destinationFolder);
    }

    @Test
    public void border3DTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("border3DTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void border3DTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("border3DTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void borderTransparencyTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderTransparencyTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void borderTransparencyTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderTransparencyTest02", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleOverlayingInTRTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleOverlayingInTR", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleSolidAndDoubleValueInTRTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleSolidAndDoubleValueInTR", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleSolidAndDottedValueInTRTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleSolidAndDottedValueInTR", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleSolidAndDashedValueInTRTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleSolidAndDashedValueInTR", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleInTrDifferentTypesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleInTrDifferentTypes", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleTRInsideTheadTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleTRInsideThead", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleTRInsideTbodyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleTRInsideTbody", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleTRInsideTfootTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleTRInsideTfoot", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleInsideTableElementsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleInsideTableElements", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleInTRLengthUnitsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleInTRLengthUnits", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleInTrColorValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleInTrColorValues", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE))
    public void borderStyleInTRwithTHTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleInTRwithTH", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2857 update cmp file after fix
    public void borderStyleInTRSeparateBorderCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderStyleInTRSeparateBorderCollapse", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-4119 update cmp file after fix
    public void tbodyBorderCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tbodyBorderCollapse", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-4119 update cmp file after fix
    public void theadBorderCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("theadBorderCollapse", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-4119 update cmp file after fix
    public void tfootBorderCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tfootBorderCollapse", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5914 Currently border-style: hidden works like border-style: none
    public void tableBorderStyleHiddenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableBorderStyleHidden", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5914 This test could be used as a reference while testing border-style: hidden
    public void tableBorderStyleNoneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableBorderStyleNone", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5915 border-style is not considered while collapsing: in browsers one can see,
    //  that top border of the cell below always wins the bottom border of the cell above
    public void tableBorderStyleCollapsingPriorityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableBorderStyleCollapsingPriority", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5524 Left border is drawn underneath, but should overlap top and bottom
    public void tableWithCellsOfDifferentBorderColorsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableWithCellsOfDifferentBorderColors", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5524 Left border is drawn underneath, but should overlap top and bottom
    public void cellDifferentBorderColorsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cellDifferentBorderColors", sourceFolder, destinationFolder);
    }

    @Test
    public void borderCollapseWithZeroWidthBorderTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderCollapseWithZeroWidthBorder", sourceFolder, destinationFolder);
    }

    @Test
    public void bigRowspanCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bigRowspanCollapse", sourceFolder, destinationFolder);
    }

    @Test
    public void cellBorderCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cellBorderCollapse", sourceFolder, destinationFolder);
    }

    @Test
    public void headerBodyFooterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerBodyFooter", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5524 Content should be placed over rather than under overlapped border
    public void bodyCellContentOverlapsBorder2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyCellContentOverlapsBorder2", sourceFolder, destinationFolder);
    }

    @Test
    public void bordersOfDifferentWidthsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bordersOfDifferentWidths", sourceFolder, destinationFolder);
    }

    @Test
    public void headerBodyFooterBottomBorderCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerBodyFooterBottomBorderCollapse", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5524 ?
    public void bodyCellContentOverlapsBorderTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyCellContentOverlapsBorder", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5524 Left border is drawn underneath, but should overlap top and bottom
    public void bottomBorderCellAndTableCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottomBorderCellAndTableCollapse", sourceFolder, destinationFolder);
    }

    @Test
    public void footerContentOverlapsFooterBorderTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("footerContentOverlapsFooterBorder", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-5524 min-width is not respected
    public void cellBordersDifferentWidthsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cellBordersDifferentWidths", sourceFolder, destinationFolder);
    }


    @Test
    public void cornerWidthHorizontalBorderWinsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cornerWidthHorizontalBorderWins", sourceFolder, destinationFolder);
    }

    @Test
    public void cornerWidthVerticalBorderWinsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cornerWidthVerticalBorderWins", sourceFolder, destinationFolder);
    }
}
