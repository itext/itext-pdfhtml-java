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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class ObjectTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ObjectTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ObjectTest/";


    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void base64svgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_base64svg", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate =
                    StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI, count = 1),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 1)})
    public void htmlObjectIncorrectBase64Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_incorrectBase64svg", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: update after DEVSIX-1346
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT,
                    count = 1),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2),
    })
    public void htmlObjectAltTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_altText", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER,
                    count = 1),})
    public void htmlObjectNestedObjectTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_nestedTag", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg1_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg1_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_4", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg1_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg1_5", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg2_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg2_3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg4", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg4_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg4_3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg5_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg5_3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInlineBlock1() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock1", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInlineBlock2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInlineBlock2_2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock2_2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInlineBlock3() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInlineBlock4() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock4", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInlineBlock5() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock5", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInTable1() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable1", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInTable2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInTable3() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInTable3_2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable3_2", sourceFolder, destinationFolder);
    }
}
