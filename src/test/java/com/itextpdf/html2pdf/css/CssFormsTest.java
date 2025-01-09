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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class CssFormsTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/CssFormsTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/CssFormsTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED)
    })
    public void formWithIconsTest() throws IOException, InterruptedException {
        runTest("formWithIcons");
    }

    @Test
    public void inlineCheckboxesAndRadiosTest() throws IOException, InterruptedException {
        runTest("inlineCheckboxesAndRadios");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, count = 2)
    })
    public void iconsInHorizontalFormsTest() throws IOException, InterruptedException {
        runTest("iconsInHorizontalForms");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, count = 2)
    })
    public void iconsInlineFormsTest() throws IOException, InterruptedException {
        runTest("iconsInlineForms");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED)
    })
    public void minWidthInlineFormsGroupTest() throws IOException, InterruptedException {
        runTest("minWidthInlineFormsGroup");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED)
    })
    public void fullWidthFormsGroupTest() throws IOException, InterruptedException {
        runTest("fullWidthFormsGroup");
    }

    @Test
    public void disabledSelectTest() throws IOException, InterruptedException {
        runTest("disabledSelect");
    }

    @Test
    public void disabledInputTest() throws IOException, InterruptedException {
        runTest("disabledInput");
    }

    @Test
    public void checkboxAndRadioDisabledTest() throws IOException, InterruptedException {
        runTest("checkboxAndRadioDisabled");
    }

    @Test
    public void readOnlyInputTest() throws IOException, InterruptedException {
        runTest("readOnlyInput");
    }

    @Test
    public void activeAndDisabledStateOfButtonTest() throws IOException, InterruptedException {
        runTest("activeAndDisabledStateOfButton");
    }

    @Test
    public void blockLevelButtonsTest() throws IOException, InterruptedException {
        runTest("blockLevelButtons");
    }

    @Test
    public void buttonOnElementsTest() throws IOException, InterruptedException {
        runTest("buttonOnElements");
    }

    @Test
    public void buttonSizesTest() throws IOException, InterruptedException {
        runTest("buttonSizes");
    }

    @Test
    public void styledButtonsTest() throws IOException, InterruptedException {
        runTest("styledButtons");
    }

    @Test
    public void iconsInButtonsOfDifferentSizeTest() throws IOException, InterruptedException {
        runTest("iconsInButtonsOfDifferentSize");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        String htmlName = SOURCE_FOLDER + testName + ".html";
        String outFileName = DESTINATION_FOLDER + testName + ".pdf";
        String cmpFileName = SOURCE_FOLDER + "cmp_" + testName + ".pdf";

        HtmlConverter.convertToPdf(new File(htmlName), new File(outFileName));
        printPathToConsole(htmlName, "html: ");

        Assertions.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, DESTINATION_FOLDER));
    }
}
