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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ImagesDpiTest extends ExtendedITextTest {
    private static final String SRC = "./src/test/resources/com/itextpdf/html2pdf/css/ImagesDpiTest/";
    private static final String DEST = "./target/test/com/itextpdf/html2pdf/css/ImagesDpiTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DEST);
    }

    @Test
    public void imagesDpiSimpleTest() throws IOException, InterruptedException {
        runTest("imagesDpi");
    }

    @Test
    public void imagesDpiFixedSizeTest() throws IOException, InterruptedException {
        runTest("imagesDpiFixedSize");
    }

    @Test
    public void imagesDpiFixedWidthTest() throws IOException, InterruptedException {
        runTest("imagesDpiFixedWidth");
    }

    @Test
    public void imagesDpiFixedHeightTest() throws IOException, InterruptedException {
        runTest("imagesDpiFixedHeight");
    }

    @Test
    public void imagesDpiFixedBlockSizeTest() throws IOException, InterruptedException {
        runTest("imagesDpiFixedBlockSize");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SRC + testName + ".html"),
                new File(DEST + testName + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DEST + testName + ".pdf",
                SRC + "cmp_" + testName + ".pdf", DEST));
    }
}
