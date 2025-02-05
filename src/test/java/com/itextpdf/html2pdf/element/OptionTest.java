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
package com.itextpdf.html2pdf.element;

import com.itextpdf.forms.logs.FormsLogMessageConstants;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class OptionTest extends ExtendedHtmlConversionITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/OptionTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/OptionTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void optionBasicTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionBasicTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionBasicTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionBasicTest02", sourceFolder, destinationFolder);
    }


    @Test
    public void optionEmptyTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionEmptyTest01", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = FormsLogMessageConstants.DUPLICATE_EXPORT_VALUE, count = 1)
    })
    public void optionLabelValueTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionLabelValueTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionStylesTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionStylesTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionStylesTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionStylesTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optionHeightTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionHeightTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionWidthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionWidthTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionOverflowTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionOverflowTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionOverflowTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionOverflowTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void optionPseudoTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionPseudoTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void optionPseudoTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("optionPseudoTest02", sourceFolder, destinationFolder);
    }
}
