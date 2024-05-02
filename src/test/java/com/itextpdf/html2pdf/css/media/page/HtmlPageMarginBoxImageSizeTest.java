/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
package com.itextpdf.html2pdf.css.media.page;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class HtmlPageMarginBoxImageSizeTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/media/page/HtmlPageMarginBoxImageSizeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/media/page/HtmlPageMarginBoxImageSizeTest/";

    @BeforeAll
    public static void initDestinationFolder() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void bigImagesInTopAndBottomPageMarginsTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "topAndBottomPageMargins.pdf";
        String cmpPdf = sourceFolder + "cmp_topAndBottomPageMargins.pdf";
        String htmlSource = sourceFolder + "topAndBottomPageMargins.html";

        HtmlConverter.convertToPdf(new File(htmlSource), new File(outPdf));

        Assertions.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff_"));
    }

    @Test
    public void bigImagesInRightAndLeftPageMarginsTest() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "leftAndRightPageMargins.pdf";
        String cmpPdf = sourceFolder + "cmp_leftAndRightPageMargins.pdf";
        String htmlSource = sourceFolder + "leftAndRightPageMargins.html";

        HtmlConverter.convertToPdf(new File(htmlSource), new File(outPdf));

        Assertions.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff_"));
    }
}
