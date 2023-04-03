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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class CssContentPropertyResolverIntegrationTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/CssContentPropertyResolverIntegrationTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/CssContentPropertyResolverIntegrationTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void checkSimpleAttrFunctionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("checkSimpleAttrFunctionTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void checkAttrFunctionWithTypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("checkAttrFunctionWithTypeTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
