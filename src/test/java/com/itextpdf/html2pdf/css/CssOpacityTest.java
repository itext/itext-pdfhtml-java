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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class CssOpacityTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssOpacityTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssOpacityTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    //TODO: DEVSIX-4679 invalid processing of opacity in kid elements
    public void innerOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("innerOpacityTest", sourceFolder, destinationFolder);
    }

    @Test
    public void nestedInSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedInSpanTest", sourceFolder, destinationFolder);
    }

    @Test
    public void spanOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanOpacity", sourceFolder, destinationFolder);
    }

    @Test
    public void imageOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageOpacity", sourceFolder, destinationFolder);
    }

    @Test
    public void pOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pOpacity", sourceFolder, destinationFolder);
    }

    @Test
    public void divOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divOpacity", sourceFolder, destinationFolder);
    }

    @Test
    public void tableOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableOpacity", sourceFolder, destinationFolder);
    }

    @Test
    public void opacityInListsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("opacityInLists", sourceFolder, destinationFolder);
    }

    @Test
    public void innerDivOpacityTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("innerDivOpacity", sourceFolder, destinationFolder);
    }
}
