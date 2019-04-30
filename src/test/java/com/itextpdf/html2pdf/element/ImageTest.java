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
package com.itextpdf.html2pdf.element;


import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class ImageTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ImageTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ImageTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void imagesInBodyTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "imagesInBody.html"), new File(destinationFolder + "imagesInBody.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "imagesInBody.pdf", sourceFolder + "cmp_imagesInBody.pdf", destinationFolder, "diff18_"));
    }

    @Test
    public void imagesWithWideBorders() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "imagesWithWideBorders.html"), new File(destinationFolder + "imagesWithWideBorders.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "imagesWithWideBorders.pdf", sourceFolder + "cmp_imagesWithWideBorders.pdf", destinationFolder));
    }

    @Test
    public void imagesWithWideMargins() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "imagesWithWideMargins.html"), new File(destinationFolder + "imagesWithWideMargins.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "imagesWithWideMargins.pdf", sourceFolder + "cmp_imagesWithWideMargins.pdf", destinationFolder));
    }

    @Test
    // TODO DEVSIX-2467
    public void imagesWithWidePaddings() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "imagesWithWidePaddings.html"), new File(destinationFolder + "imagesWithWidePaddings.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "imagesWithWidePaddings.pdf", sourceFolder + "cmp_imagesWithWidePaddings.pdf", destinationFolder));
    }

    @Test
    // TODO DEVSIX-2467
    public void imagesWithWidePaddingsBordersMargins() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "imagesWithWidePaddingsBordersMargins.html"), new File(destinationFolder + "imagesWithWidePaddingsBordersMargins.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "imagesWithWidePaddingsBordersMargins.pdf", sourceFolder + "cmp_imagesWithWidePaddingsBordersMargins.pdf", destinationFolder));
    }

}
