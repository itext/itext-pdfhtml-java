/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalAlignmentTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalAlignmentTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/VerticalAlignmentTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    // TODO: DEVSIX-3757 ('top' and 'bottom' values are not supported)
    public void verticalAlignmentTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest01", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest02", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest03", sourceFolder, destinationFolder);
    }

    @Test
    // TODO: DEVSIX-3757 ('top' and 'bottom' values are not supported)
    public void verticalAlignmentImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentImgTest", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest05", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest06", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest07() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest07", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest08() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest08", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest09() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest09", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest10() throws IOException, InterruptedException {
        // TODO DEVSIX-3757 interesting thing is that vertical alignment increases line height if needed, however itext doesn't in this case
        convertToPdfAndCompare("verticalAlignmentTest10", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentTest11() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest11", sourceFolder, destinationFolder);
    }

    @Test
    public void verticalAlignmentTest12() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest12", sourceFolder, destinationFolder);
    }

    @Test
    public void verticalAlignmentTest13() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest13", sourceFolder, destinationFolder);
    }

    @Test
    public void verticalAlignmentTest14() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest14", sourceFolder, destinationFolder);
    }

    @Test
    public void verticalAlignmentTest15() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest15", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentCellTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentCellTest01", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentCellTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentCellTest02", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignmentCellTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentCellTest03", sourceFolder, destinationFolder);
    }
    
    @Test
    public void vAlignAttributeCellTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("vAlignAttributeCellTest01", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignOnNestedInlines01() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignOnNestedInlines01", sourceFolder, destinationFolder);
    }
    
    @Test
    public void verticalAlignOnNestedInlines02() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignOnNestedInlines02", sourceFolder, destinationFolder);
    }
}
