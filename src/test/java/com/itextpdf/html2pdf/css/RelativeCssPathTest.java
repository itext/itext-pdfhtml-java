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

import com.itextpdf.html2pdf.ConverterProperties;
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
public class RelativeCssPathTest extends ExtendedITextTest {

    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/RelativeCssPathTest/";
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/RelativeCssPathTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void relativeCssPath01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "css_relative.html"), new File(destinationFolder + "css_relative.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "css_relative.pdf", sourceFolder + "cmp_css_relative.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void relativeCssPath02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "css_relative_base64.html"), new File(destinationFolder + "css_relative_base64.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "css_relative_base64.pdf", sourceFolder + "cmp_css_relative_base64.pdf", destinationFolder, "diff02_"));
    }

    @Test
    public void relativeImports01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "root/html/test.html"), new File(destinationFolder + "relativeImportsTest.pdf"), new ConverterProperties().setBaseUri(sourceFolder + "root/html/"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "relativeImportsTest.pdf", sourceFolder + "cmp_relativeImportsTest.pdf", destinationFolder));
    }
}
