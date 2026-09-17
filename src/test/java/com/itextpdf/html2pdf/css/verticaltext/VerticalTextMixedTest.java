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
package com.itextpdf.html2pdf.css.verticaltext;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextMixedTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/verticaltext/VerticalTextMixedTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/verticaltext/VerticalTextMixedTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedDirectionRtlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedDirectionRtl", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedAdjacentNoGapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedAdjacentNoGap", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedDirectionInParagraphTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedDirectionInParagraph", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedMultipleLinesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedMultipleLines", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedSingleLineForcedSplitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedSingleLineForcedSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedSingleLineNoSplitTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedSingleLineNoSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedWithHorizontalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedWithHorizontal", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedWritingModeAndDirectionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedWritingModeAndDirection", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedWritingModeLrInRlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedWritingModeLrInRl", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMixedWritingModeRlInLrTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMixedWritingModeRlInLr", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
