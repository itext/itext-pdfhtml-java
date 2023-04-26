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
package com.itextpdf.html2pdf.element;

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
public class PreTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/PreTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/PreTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void pre01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "preTest01.html"), new File(destinationFolder + "preTest01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "preTest01.pdf", sourceFolder + "cmp_preTest01.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void pre02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "preTest02.html"), new File(destinationFolder + "preTest02.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "preTest02.pdf", sourceFolder + "cmp_preTest02.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void pre03Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "preTest03.html"), new File(destinationFolder + "preTest03.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "preTest03.pdf", sourceFolder + "cmp_preTest03.pdf", destinationFolder, "diff03_"));
    }

}
