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
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

@Tag("IntegrationTest")
public class ListCssTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/ListCSSTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/ListCSSTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void listCSSStartTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "orderedList.html"), new File(DESTINATION_FOLDER + "orderedList01.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "orderedList01.pdf", SOURCE_FOLDER + "cmp_orderedList01.pdf", DESTINATION_FOLDER, "diff02_"));
    }

    @Test
    public void lowercaseATypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("aType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void uppercaseATypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("aAType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void lowercaseITypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("iType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void uppercaseITypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("iIType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void digitTypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("1Type", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO: DEVSIX-6128 NullPointerException when trying to convert html with non-existing ol type.
    @Test
    public void unsupportedType() {
        Assertions.assertThrows(NullPointerException.class,
                () -> convertToPdfAndCompare("unsupportedType", SOURCE_FOLDER, DESTINATION_FOLDER));
    }

    @Test
    public void horizontalDescriptionListTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("horizontalDescriptionList", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
