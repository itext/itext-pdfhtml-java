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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
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
public class BodyBackgroundTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BodyBackgroundTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BodyBackgroundTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void bodyWithBorderTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyBorder", sourceFolder, destinationFolder);
    }

    @Test
    public void bodyWithHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyHeight", sourceFolder, destinationFolder);
    }

    @Test
    public void bodyWithMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyMargin", sourceFolder, destinationFolder);
    }

    @Test
    public void bodyWithMarginNegativeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyMarginNegative", sourceFolder, destinationFolder);
    }

    @Test
    public void firstElementHasMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("firstElementHasMargin", sourceFolder, destinationFolder);
    }

    @Test
    public void marginNegativeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("marginNegative", sourceFolder, destinationFolder);
    }

    @Test
    public void multiplePageContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiplePageContent", sourceFolder, destinationFolder);
    }

    @Test
    public void positionAbsoluteTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("positionAbsolute", sourceFolder, destinationFolder);
    }

    @Test
    public void positionFixedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("positionFixed", sourceFolder, destinationFolder);
    }

    @Test
    //todo DEVSIX-4732 html borders
    public void bordersAndBackgroundsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bordersAndBackgrounds", sourceFolder, destinationFolder);
    }

    @Test
    //todo DEVSIX-4732 html borders
    public void bordersAndBackgroundsWithPagePropertiesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bordersAndBackgroundsWithPageProperties", sourceFolder, destinationFolder);
    }

    @Test
    //todo DEVSIX-4732 html borders
    public void bordersAndBackgroundsWithPageMarksTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bordersAndBackgroundsWithPageMarks", sourceFolder, destinationFolder);
    }

    @Test
    public void floatRightElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatRightElement", sourceFolder, destinationFolder);
    }

    @Test
    public void transformRotateElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("transformRotateElement", sourceFolder, destinationFolder);
    }

    @Test
    public void displayNoneElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayNoneElement", sourceFolder, destinationFolder);
    }

    @Test
    public void overflowWithFixedHeightElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("overflowWithFixedHeightElement", sourceFolder, destinationFolder);
    }

    @Test
    public void visibilityHiddenElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visibilityHiddenElement", sourceFolder, destinationFolder);
    }

    @Test
    public void pagePropertyWithBackgroundImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pagePropertyBackgroundImage", sourceFolder, destinationFolder);
    }
}
