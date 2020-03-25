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
package com.itextpdf.html2pdf.css.media.page.max_dimension;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class PageMarginBoxMaxDimensionTest extends ExtendedHtmlConversionITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/max_dimension/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/max_dimension/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    // Top margin box tests
    @Test
    public void topOnlyMaxLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMaxLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyMaxRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMaxRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topOnlyMaxCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topOnlyMaxCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxCenterAndRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxCenterAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxLeftAndCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxLeftAndCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxLeftAndRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxLeftAndRight", sourceFolder, destinationFolder);
    }

    @Test
    public void topMaxLeftAndMaxCenterAndMaxRight() throws IOException, InterruptedException {
        convertToPdfAndCompare("topMaxLeftAndMaxCenterAndMaxRight", sourceFolder, destinationFolder);
    }

    //Left margin box tests
    @Test
    public void leftOnlyMaxTopTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMaxTop", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyMaxCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMaxCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftOnlyMaxBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftOnlyMaxBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMaxCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMaxCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void leftTopAndMaxBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftTopAndMaxBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMaxCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMaxCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void leftMaxTopAndMaxCenterAndBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("leftMaxTopAndMaxCenterAndBottom", sourceFolder, destinationFolder);
    }

    @Test
    public void pageMarginFont() throws IOException, InterruptedException {
        convertToPdfAndCompare("pageMarginFont", sourceFolder, destinationFolder);
    }
}
