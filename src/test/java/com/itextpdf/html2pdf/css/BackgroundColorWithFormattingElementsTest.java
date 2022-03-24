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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BackgroundColorWithFormattingElementsTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/BackgroundColorWithFormattingElementsTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/BackgroundColorWithFormattingElementsTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void strongTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("strongTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void bTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void iTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("iTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void emTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("emTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void markTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("markTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void smallTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void delTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("delTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void insTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("insTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void subTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("subTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void supTagTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("supTag", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
