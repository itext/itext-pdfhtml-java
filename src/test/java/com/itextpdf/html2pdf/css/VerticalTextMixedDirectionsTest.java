/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 Apryse Group NV
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextMixedDirectionsTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalTextMixedDirectionsTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/VerticalTextMixedDirectionsTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-10200 Consider text elements with different writing-mode as inline-blocks,
    //  after that vertical RTL text chunks in vertical LTR paragraphs and vice versa will be fixed.
    public void paragraphMixedVerticalTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedVerticalText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-10200 Consider text elements with different writing-mode as inline-blocks
    public void paragraphMixedVerticalTextRtlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedVerticalTextRtl", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-10200 Consider text elements with different writing-mode as inline-blocks
    public void paragraphMixedVerticalTextHorizontalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedVerticalTextHorizontal", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-10200 Consider text elements with different writing-mode as inline-blocks
    public void paragraphMixedVerticalTextInlineBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedVerticalTextInlineBlock", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextWithLineBreaksTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextWithLineBreaksTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextNoEnoughHorizontalSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextNoEnoughHorizontalSpaceTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphMixedTextWithPageBreakTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphMixedTextWithPageBreakTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalParagraphMixedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalParagraphMixedTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalParagraphMixedWithLineBreaksTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalParagraphMixedWithLineBreaksTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelTwoLinesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelTwoLinesTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelPageBreakTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelPageBreakTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalWritingAtTextLevelLongTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalWritingAtTextLevelLongTextTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void verticalParagraphWithHorizontalTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("verticalParagraphWithHorizontalTextTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
