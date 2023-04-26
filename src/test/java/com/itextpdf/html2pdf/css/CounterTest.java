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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class CounterTest extends ExtendedHtmlConversionITextTest{

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CounterTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CounterTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void counter01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("counter01", sourceFolder, destinationFolder);
    }

    @Test
    public void pageCounter01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter01", sourceFolder, destinationFolder);
    }

    @Test
    public void pageCounter02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter02", sourceFolder, destinationFolder);
    }

    @Test
    public void pageCounter03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter03", sourceFolder, destinationFolder);
    }

    @Test
    public void pageCounter04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter04", sourceFolder, destinationFolder);
    }

    @Test
    public void pageCounterSpacesInDeclarationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter_spaces_in_declaration", sourceFolder, destinationFolder);
    }

    @Test
    // TODO fix cmp after DEVSIX-5509 is done; currently total page count is incorrect
    public void pageCounterWithTrimmedLastPageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("page_counter_with_trimmed_last_page", sourceFolder, destinationFolder);
    }
}
