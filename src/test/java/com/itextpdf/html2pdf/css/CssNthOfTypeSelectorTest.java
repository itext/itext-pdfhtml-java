/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;


@Tag("IntegrationTest")
public class CssNthOfTypeSelectorTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssNthOfTypeSelectorTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssNthOfTypeSelectorTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void nthOfTypeEvenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resourceNthOfTypeEvenTest", sourceFolder, destinationFolder);
    }

    @Test
    public void nthOfTypeExpressionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resourceNthOfTypeExpressionTest", sourceFolder, destinationFolder);
    }

    @Test
    public void nthOfTypeNegativeExpressionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resourceNthOfTypeNegativeExpressionTest", sourceFolder, destinationFolder);
    }

    @Test
    public void nthOfTypeIntegerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resourceNthOfTypeIntegerTest", sourceFolder, destinationFolder);
    }

    @Test
    public void firstOfTypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resourceFirstOfTypeTest", sourceFolder, destinationFolder);
    }

    @Test
    public void lastOfTypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resourceLastOfTypeTest", sourceFolder, destinationFolder);
    }

    @Test
    public void notLastOfTypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resourceNotLastOfTypeTest", sourceFolder, destinationFolder);
    }
}
