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
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class VerticalAlignmentTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalAlignmentTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/VerticalAlignmentTest/";

    @BeforeClass
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
        // TODO interesting thing is that vertical alignment increases line height if needed, however itext doesn't in this case 
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
    @Ignore("DEVSIX-1750")
    public void verticalAlignmentTest13() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest13", sourceFolder, destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-1750")
    public void verticalAlignmentTest14() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalAlignmentTest14", sourceFolder, destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-1750")
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
