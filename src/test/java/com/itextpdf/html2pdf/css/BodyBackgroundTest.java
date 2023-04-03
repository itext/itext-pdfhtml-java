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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
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
