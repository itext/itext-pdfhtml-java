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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ListStyleImageLinearGradientTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/ListStyleImageLinearGradientTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/ListStyleImageLinearGradientTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void linearGradientInListStyleTest() throws IOException, InterruptedException {
        runTest("linearGradientInListStyle");
    }

    @Test
    public void linearGradientTypeTest() throws IOException, InterruptedException {
        runTest("linearGradientType");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.INVALID_GRADIENT_DECLARATION, count = 3)})
    public void invalidLinearGradientTypeTest() throws IOException, InterruptedException {
        runTest("invalidLinearGradientType");
    }

    @Test
    public void repeatingLinearGradientTypeTest() throws IOException, InterruptedException {
        runTest("repeatingLinearGradientType");
    }

    @Test
    public void linearGradientWithEmRemValuesTest() throws IOException, InterruptedException {
        runTest("linearGradientWithEmRemValues");
    }

    @Test
    public void differentLinearGradientsInElementsTest() throws IOException, InterruptedException {
        runTest("differentLinearGradientsInElements");
    }

    @Test
    public void linearGradientInDifferentElementsTest() throws IOException, InterruptedException {
        runTest("linearGradientInDifferentElements");
    }

    @Test
    public void linearGradientDifferentFontSizeTest() throws IOException, InterruptedException {
        runTest("linearGradientDifferentFontSize");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + testName + ".html"),
                new File(DESTINATION_FOLDER + testName + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + testName + ".pdf",
                SOURCE_FOLDER + "cmp_" + testName + ".pdf", DESTINATION_FOLDER, "diff_" + testName));
    }
}
