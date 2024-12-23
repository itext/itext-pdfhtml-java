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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class HtmlStylesToCssConverterIntegrationTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/HtmlStylesToCssConverterIntegrationTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/HtmlStylesToCssConverterIntegrationTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void objectTagWidthAndHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTagWidthAndHeightTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.URL_IS_NOT_CLOSED_IN_CSS_EXPRESSION),
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.URL_IS_EMPTY_IN_CSS_EXPRESSION),
            @LogMessage(messageTemplate =
                    StyledXmlParserLogMessageConstant.WAS_NOT_ABLE_TO_DEFINE_BACKGROUND_CSS_SHORTHAND_PROPERTIES)
    })
    @Test
    public void notClosedUrlTest() throws IOException, InterruptedException {
        final String cmpPdfPath = SOURCE_FOLDER + "cmp_notClosedUrl.pdf";
        final String outPdfPath = DESTINATION_FOLDER + "notClosedUrl.pdf";

        final String input = "PDF TEST<p></p><div\n"
                + "style=\"background:url(http://google.com/\">X</div>";

        HtmlConverter.convertToPdf(input, new PdfWriter(outPdfPath));
        Assertions.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, DESTINATION_FOLDER));
    }
}
