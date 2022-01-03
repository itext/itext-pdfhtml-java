/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ClearTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/ClearTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/ClearTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void clear02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear02Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear03Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear04Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear06Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear07Test", sourceFolder, destinationFolder);
    }

    @Test
    // TODO: DEVSIX-1269, DEVSIX-5474 update cmp file after fixing issues
    public void clear08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear08Test", sourceFolder, destinationFolder);
    }

    @Test
    // TODO: DEVSIX-5474 update cmp file after fixing
    public void clear09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear09Test", sourceFolder, destinationFolder);
    }

    @Test
    // TODO: DEVSIX-5474 update cmp file after fixing
    public void clear10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear10Test", sourceFolder, destinationFolder);
    }

    @Test
    public void clear11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("clear11Test", sourceFolder, destinationFolder);
    }

    @Test
    public void imageFloatParagraphClearTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageFloatParagraphClear", sourceFolder, destinationFolder);
    }

    @Test
    public void clearInTableWithImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clearInTableWithImage", sourceFolder, destinationFolder);
    }

    @Test
    public void imgFloatAmongParaWithClearPropTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgFloatAmongParaWithClearProp", sourceFolder, destinationFolder);
    }

    @Test
    public void imgFloatAmongParaWithSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgFloatAmongParaWithSpan", sourceFolder, destinationFolder);
    }

    @Test
    public void paraFloatLeftImgClearLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paraFloatLeftImgClearLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void paraFloatImgClearAndDisplayBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paraFloatImgClearAndDisplayBlock", sourceFolder, destinationFolder);
    }

    @Test
    public void paraFloatImgWideBorderClearAndDisplayBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paraFloatImgWideBorderClearAndDisplayBlock", sourceFolder, destinationFolder);
    }

    @Test
    public void imgFloatWideBorderAmongParaWithClearTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgFloatWideBorderAmongParaWithClear", sourceFolder, destinationFolder);
    }

    @Test
    public void imgWideBorderFloatAmongParaWithSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgWideBorderFloatAmongParaWithSpan", sourceFolder, destinationFolder);
    }

    @Test
    public void imgWideBorderClearAndDisplayBlockParaFloatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgWideBorderClearAndDisplayBlockParaFloat", sourceFolder, destinationFolder);
    }
}
