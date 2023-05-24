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
public class ColumnCountTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/ColumnCountTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/ColumnCountTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicArticleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicArticleTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicDivTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicDivWithImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicDivWithImageTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicPTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicPTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicFormTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicFormTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicUlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicUlTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicOlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicOlTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicTableTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicSectionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicSectionTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicDivMultiPageDocumentsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicDivMultiPageTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicFormMultiPageDocumentsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicFormMultiPageTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicDisplayPropertyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicDisplayPropertyTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicDisplayPropertyWithNestedColumnsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicDisplayPropertyWithNestedColumnsTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicFloatPropertyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicFloatPropertyTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertBasicFlexPropertyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("basicFlexPropertyTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
