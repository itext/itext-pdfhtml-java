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

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BackgroundClipTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundClipTest/";

    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BackgroundClipTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void backgroundColorBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorContentBox1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorContentBox1", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorContentBoxMarginsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorContentBoxMargins", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorDefaultTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorDefault", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorPaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorPaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageColorContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageColorContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageColorDefaultTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageColorDefault", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageDiffBordersPaddingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageDiffBordersPaddings", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageDefaultTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageDefault", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImagePaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImagePaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImage", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImageColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageColor2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImageColor2", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageColor3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMultiImageColor3", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundColorMultipleClipTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorMultipleClip", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4525 border-radius works incorrectly with background-clip
    public void backgroundColorBorderRadiusTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorBorderRadius", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4525 border-radius works incorrectly with background-clip
    public void backgroundImageBorderRadiusTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageBorderRadius", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientBorderBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientPaddingBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientPaddingBox", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientContentBox", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorComma", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorNoCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorNoComma", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorNoneCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorNoneComma", sourceFolder, destinationFolder);
    }

    @Test
    public void multiImageColorNoneNoCommaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multiImageColorNoneNoComma", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImagePositionContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImagePositionContentBox", sourceFolder, destinationFolder);
    }
}
