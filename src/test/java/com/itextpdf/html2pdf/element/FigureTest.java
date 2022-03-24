/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
    Authors: iText Software.

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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class FigureTest extends ExtendedITextTest {
    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/FigureTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/FigureTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void figureFileDocumentTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hello_figure_file.html"), new File(destinationFolder + "hello_figure_file.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hello_figure_file.pdf", sourceFolder + "cmp_hello_figure_file.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void smallFigureTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "smallFigureTest.html"), new File(destinationFolder + "smallFigureTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "smallFigureTest.pdf", sourceFolder + "cmp_smallFigureTest.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void figureInSpanTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "figureInSpan.html"), new File(destinationFolder + "figureInSpan.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "figureInSpan.pdf", sourceFolder + "cmp_figureInSpan.pdf", destinationFolder, "diff04_"));
    }
}
