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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
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
public class EmTest extends ExtendedITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/EmTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/EmTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void em01Test() throws IOException, InterruptedException {
        runTest("emTest01");
    }

    @Test
    public void emWithNotLocallyLoadedFontTest() throws IOException, InterruptedException {
        runTest("emWithNotLocallyLoadedFont");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        String htmlName = SOURCE_FOLDER + testName + ".html";
        String outFileName = DESTINATION_FOLDER + testName + ".pdf";
        String cmpFileName = SOURCE_FOLDER + "cmp_" + testName + ".pdf";

        HtmlConverter.convertToPdf(new File(htmlName), new File(outFileName));
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlName) + "\n");

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, DESTINATION_FOLDER));
    }
}
