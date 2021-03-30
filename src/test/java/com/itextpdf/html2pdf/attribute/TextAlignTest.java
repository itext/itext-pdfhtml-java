/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class TextAlignTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute/TextAlignTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/TextAlignTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void textAlignLeftTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignLeft", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignRightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignRight", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignCenter", sourceFolder, destinationFolder);
    }

    @Test
    public void textAlignJustifyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textAlignJustify", sourceFolder, destinationFolder);
    }
}
