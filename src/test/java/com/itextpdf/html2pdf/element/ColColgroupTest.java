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


import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class ColColgroupTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ColColgroupTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ColColgroupTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simpleBackgroundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleBackgroundTest", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-2090. This property with cols works only in firefox and only with collapse value.
    public void simpleVisibilityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleVisibilityTest", sourceFolder, destinationFolder);
    }

    @Test
    public void simpleTdColspanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleTdColspanTest", sourceFolder, destinationFolder);
    }

    @Test
    public void simpleTdRowspanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleTdRowspanTest", sourceFolder, destinationFolder);
    }

    @Test
    public void simpleTdColspanRowspanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleTdColspanRowspanTest", sourceFolder, destinationFolder);
    }

    @Test
    public void complexColspanRowspanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("complexColspanRowspanTest", sourceFolder, destinationFolder);
    }

    @Test
    public void simpleWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleWidthTest", sourceFolder, destinationFolder);
    }

    @Test
    public void widthColOverridedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("widthColOverridedTest", sourceFolder, destinationFolder);
    }

    @Test
    //In this test we use FireFox behavior that treat <colgroup> and <col> tags equally and don't override colgroup's width value with smaller one in case of width set on <td>
    public void widthColgroupOverridedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("widthColgroupOverridedTest", sourceFolder, destinationFolder);
    }
}
