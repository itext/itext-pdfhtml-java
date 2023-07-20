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

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FlexPagingTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FlexPagingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FlexPagingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void rowNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("row-non-paging", sourceFolder, destinationFolder);
    }


    @Test
    public void columnNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-non-paging", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-7622 change files after paging is introduced
    public void columnPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-7622 change files after paging is introduced
    public void columnPagingMultiColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-paging-multi-column", sourceFolder, destinationFolder);
    }


    @Test
    public void columnReverseNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-reverse-non-paging", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-7622 change files after paging is introduced
    public void columnReversePagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-reverse-paging", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-7622 change files after paging is introduced
    public void columnReversePagingMultiColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-reverse-paging-multi-column", sourceFolder, destinationFolder);
    }

    @Test
    public void columnWrapReverseNonPagingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("column-wrap-reverse-non-paging", sourceFolder, destinationFolder);
    }
}
