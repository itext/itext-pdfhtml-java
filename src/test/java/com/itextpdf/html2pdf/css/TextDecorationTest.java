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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.IOException;

@Tag("IntegrationTest")
public class TextDecorationTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/TextDecorationTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/TextDecorationTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void textDecoration01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecoration02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecoration03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //Text decoration property is in defaults.css for a[href], should be replaced by css.
    @Test
    public void textDecoration04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2532
    public void textDecoration05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO update after DEVSIX-4063 is closed
    public void textDecorationShorthandAllValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationShorthandAllValues", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationShorthandOneValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationShorthandOneValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO update after DEVSIX-4063 is closed
    public void textDecorationShorthandTwoValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationShorthandTwoValues", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationWithChildElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationWithChildElement", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-4201
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.HSL_COLOR_NOT_SUPPORTED)})
    public void textDecorationColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationColorWithTransparencyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColorWithTransparency", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationLineTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationLine", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationLineNoneAndUnderlineTogetherTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationLineNoneAndUnderlineTogether", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO update after DEVSIX-4063 is closed
    public void textDecorationStyleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationStyle", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO update after DEVSIX-4063 is closed
    public void shorthandAndSpecificTextDecorPropsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("shorthandAndSpecificTextDecorProps", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void combinationOfLinesInTextDecorationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("combinationOfLinesInTextDecoration", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationColorEffectOnNestedElements01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColorEffectOnNestedElements01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationColorEffectOnNestedElements02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColorEffectOnNestedElements02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationColorEffectOnNestedElements03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColorEffectOnNestedElements03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationColorEffectOnNestedElements04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColorEffectOnNestedElements04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationNoneOnNestedElementsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationNoneOnNestedElements", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationWithDisplayInlineBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationWithDisplayInlineBlock", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void textDecorationInNodeStyleAttributeVsStyleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationInNodeStyleAttributeVsStyle", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
