/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
    Authors: iText Software.

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
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class VerticalAlignmentInlineBlockTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalAlignmentInlineBlockTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/VerticalAlignmentInlineBlockTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void checkBaselineAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("baseline", sourceFolder, destinationFolder);
    }

    @Test
    public void checkBaselineAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("baseline-wrap", sourceFolder, destinationFolder);
    }

    @Test
    public void checkBottomAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottom", sourceFolder, destinationFolder);
    }

    @Test
    public void checkBottomAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bottom-wrap", sourceFolder, destinationFolder);
    }

    @Test
    public void checkMiddleAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("middle", sourceFolder, destinationFolder);
    }

    @Test
    public void checkMiddleAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("middle-wrap", sourceFolder, destinationFolder);
    }
    @Test
    public void checkTopAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("top", sourceFolder, destinationFolder);
    }

    @Test
    public void checkTopAlignmentWitWrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("top-wrap", sourceFolder, destinationFolder);
    }
    @Test
    public void checkMixedAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("mixed", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void checkMixedAlignment2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("mixed2", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void checkMixedAlignment3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("mixed3", sourceFolder, destinationFolder);
    }
    @Test
    public void checkCustomerExampleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("customerExample", sourceFolder, destinationFolder);
    }

    @Test
    public void checkSingleImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("singleimage", sourceFolder, destinationFolder);
    }

    @Test
    public void checkElementsInDivAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("ElementsInDiv", sourceFolder, destinationFolder);
    }

    @Test
    public void checkSpanAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("span", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void checkStyledElementsAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("styleAlignment", sourceFolder, destinationFolder);
    }

    @Test
    public void checkUnorderedListAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("unorderedList", sourceFolder, destinationFolder);
    }

    @Test
    public void orderedListAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("orderedList", sourceFolder, destinationFolder);
    }

    @Test
    public void tableAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("table", sourceFolder, destinationFolder);
    }

    @Test
    public void buttonAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("button", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void formAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("form", sourceFolder, destinationFolder);
    }

    @Test
    public void headerAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("header", sourceFolder, destinationFolder);
    }

    @Test
    public void paragraphAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraph", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3757 Update reference doc if the result matched the expected result
    public void allStylesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("AllStyles", sourceFolder, destinationFolder);
    }
}
