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
public class LabelTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/LabelTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/LabelTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void label01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("labelTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void labelDisplayBlock01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("labelDisplayBlockTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void labelDisplayBlock02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("labelDisplayBlockTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void labelBackground01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("labelBackground01Test", sourceFolder, destinationFolder);
    }

    @Test
    public void labelInlineBlockRelativeWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("labelInlineBlockRelativeWidthTest", sourceFolder, destinationFolder);
    }

}
