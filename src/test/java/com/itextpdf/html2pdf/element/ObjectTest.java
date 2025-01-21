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
    public void relativeSizeSvg3_2_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_2_2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_3", sourceFolder, destinationFolder);
    }
    @Test
    public void relativeSizeSvg3_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_4", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_5", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_6", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_7Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_7", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_8Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_8", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_9Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_9", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_10", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_11", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_12", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_13", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_14", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvg3_15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvg3_15", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_2", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_3", sourceFolder, destinationFolder);
    }
    @Test
    public void fixedImgRelativeSizeSvg3_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_4", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_5", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_6", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_7Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_7", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_8Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_8", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_9Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_9", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_10", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_11", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_12", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_13", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_14", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedImgRelativeSizeSvg3_15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedImgRelativeSizeSvg3_15", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_3", sourceFolder, destinationFolder);
    }
    @Test
    public void relativeHeightImgRelativeSizeSvg3_4Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_4", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_5Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_5", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_6Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_6", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_7Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_7", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_8Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_8", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_9Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_9", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_10", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_11", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_12", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_13", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_14", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeHeightImgRelativeSizeSvg3_15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeHeightImgRelativeSizeSvg3_15", sourceFolder, destinationFolder);
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

    //TODO DEVSIX-1316 fix incorrect min max width
    @Test
    public void relativeSizeSvgInlineBlock2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock2", sourceFolder, destinationFolder);
    }

    //TODO DEVSIX-1316 fix incorrect min max width
    @Test
    public void relativeSizeSvgInlineBlock2_2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock2_2", sourceFolder, destinationFolder);
    }

    //TODO DEVSIX-1316 fix incorrect min max width
    @Test
    public void relativeSizeSvgInlineBlock3() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock3", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInlineBlock4() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock4", sourceFolder, destinationFolder);
    }

    //TODO DEVSIX-1316 fix incorrect min max width
    @Test
    public void relativeSizeSvgInlineBlock5() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInlineBlock5", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInTable1() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable1", sourceFolder, destinationFolder);
    }

    //TODO DEVSIX-7003, DEVSIX-1316 fix image with relative size in the table
    @Test
    public void relativeSizeSvgInTable2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable2", sourceFolder, destinationFolder);
    }

    //TODO DEVSIX-7003, DEVSIX-1316 fix image with relative size in the table
    @Test
    public void relativeSizeSvgInTable3() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable3", sourceFolder, destinationFolder);
    }

    //TODO DEVSIX-7003, DEVSIX-1316 fix image with relative size in the table
    @Test
    public void relativeSizeSvgInTable3_2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInTable3_2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInFixedObject() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInFixedObject", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInFixedObject2() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInFixedObject2", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgInRelativeObject() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgInRelativeObject", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void giantSvgInRelativeObject() throws IOException, InterruptedException {
        convertToPdfAndCompare("giantSvgInRelativeObject", sourceFolder, destinationFolder);
    }

    @Test
    public void invalidSizeSvgInRelativeObject() throws IOException, InterruptedException {
        convertToPdfAndCompare("invalidSizeSvgInRelativeObject", sourceFolder, destinationFolder);
    }

    @Test
    public void fixedSizeSvgInRelativeObject() throws IOException, InterruptedException {
        convertToPdfAndCompare("fixedSizeSvgInRelativeObject", sourceFolder, destinationFolder);
    }

    @Test
    public void relativeSizeSvgFixedInlineBlock() throws IOException, InterruptedException {
        convertToPdfAndCompare("relativeSizeSvgFixedInlineBlock", sourceFolder, destinationFolder);
    }
}
