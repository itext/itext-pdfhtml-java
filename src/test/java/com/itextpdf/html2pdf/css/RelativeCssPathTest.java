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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class RelativeCssPathTest extends ExtendedITextTest {
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/RelativeCssPathTest/";
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/RelativeCssPathTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void relativeCssPath01Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "css_relative.html"), new File(DESTINATION_FOLDER + "css_relative.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + "css_relative.pdf", SOURCE_FOLDER + "cmp_css_relative.pdf", DESTINATION_FOLDER, "diff01_"));
    }

    @Test
    public void relativeCssPath02Test() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "css_relative_base64.html"), new File(DESTINATION_FOLDER + "css_relative_base64.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "css_relative_base64.pdf", SOURCE_FOLDER
                + "cmp_css_relative_base64.pdf", DESTINATION_FOLDER, "diff02_"));
    }

    @Test
    public void relativeImportsTest() throws IOException, InterruptedException {
        runTestFromRoot("relativeImports");
    }

    @Test
    public void externalCssLoopTest() throws IOException, InterruptedException {
        runTestFromRoot("externalCssLoop");
    }

    @Test
    public void twoExternalCssTest() throws IOException, InterruptedException {
        runTestFromRoot("twoExternalCss");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI, logLevel = LogLevelConstants.ERROR)
    })
    public void wrongNestedExternalCssTest() throws IOException, InterruptedException {
        runTestFromRoot("wrongNestedExternalCss");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.IMPORT_MUST_COME_BEFORE, logLevel = LogLevelConstants.WARN)
    })
    public void styleBeforeImportTest() throws IOException, InterruptedException {
        runTestFromRoot("styleBeforeImport");
    }

    private void runTestFromRoot(String testName) throws IOException, InterruptedException {
        String html = SOURCE_FOLDER + "root/html/" + testName + ".html";
        String pdf = DESTINATION_FOLDER + testName + ".pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_" + testName + ".pdf";

        HtmlConverter.convertToPdf(new File(html), new File(pdf), new ConverterProperties().setBaseUri(SOURCE_FOLDER + "root/html/"));
        Assertions.assertNull(new CompareTool().compareByContent(pdf, cmpPdf, DESTINATION_FOLDER));
    }
}
