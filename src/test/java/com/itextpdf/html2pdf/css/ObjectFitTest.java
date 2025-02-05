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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
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
public class ObjectFitTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/ObjectFitTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/ObjectFitTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void objectFitCasesTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "objectFit_test.html"), new File(destinationFolder + "objectFit_test.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "objectFit_test.pdf", sourceFolder + "cmp_objectFit_test.pdf", destinationFolder, "diff18_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate =
            Html2PdfLogMessageConstant.UNEXPECTED_VALUE_OF_OBJECT_FIT))
    public void objectFitUnexpectedValueTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "objectFit_test_unexpected.html"),
                new File(destinationFolder + "objectFit_test_unexpected.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "objectFit_test_unexpected.pdf",
                sourceFolder + "cmp_objectFit_test_unexpected.pdf", destinationFolder, "diff18_"));
    }

}
