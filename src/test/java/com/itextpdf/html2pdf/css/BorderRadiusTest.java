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
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class BorderRadiusTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BorderRadiusTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BorderRadiusTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void borderRadius01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest05", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest06", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest07", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest08", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest09", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest10", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest11", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12ImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12Image", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12ATest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12A", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius12BTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest12B", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest13", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest14", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest15", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius16Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest16", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius17Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest17", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadius18Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusTest18", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadiusInlineElementTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderRadiusInlineElementTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void imageBorderRadiusTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageBorderRadiusTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadiusInlineSpanElementTest01() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2018, DEVSIX-1191 closing
        convertToPdfAndCompare("borderRadiusInlineSpanElementTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void borderRadiusInlineDivElementTest01() throws IOException, InterruptedException {
        //TODO: update after DEVSIX-2018, DEVSIX-1191 closing
        convertToPdfAndCompare("borderRadiusInlineDivElementTest01", sourceFolder, destinationFolder);
    }
}
