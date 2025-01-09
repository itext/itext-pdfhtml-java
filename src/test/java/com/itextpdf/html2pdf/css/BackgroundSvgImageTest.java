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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class BackgroundSvgImageTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundSvgImageTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/BackgroundSvgImageTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

// ------------------- Tests with [percent percent] not-repeated background with different SVGs

    @Test
    public void n30prc_50prc_percent_width_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("30prc_50prc_percent_width_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

// ------------------- Tests with [100px 100px] not-repeated background with different SVGs (different preserveAspectRatio values)

    @Test
    public void n100px_100px_viewbox_prRatio_1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_prRatio_1", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_prRatio_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_prRatio_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_prRatio_3Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_prRatio_3", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

// ------------------- Tests with [600px auto] repeated background with different SVGs (a few different cases)

    @Test
    public void repeat_abs_auto_svg_abs_width_abs_height_viewboxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("repeat_abs_auto_svg_abs_width_abs_height_viewbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void repeat_abs_auto_svg_abs_width_abs_height_viewbox_2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("repeat_abs_auto_svg_abs_width_abs_height_viewbox_2", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void repeat_abs_auto_svg_abs_width_no_height_no_viewboxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("repeat_abs_auto_svg_abs_width_no_height_no_viewbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void repeat_abs_auto_svg_abs_width_no_height_viewboxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("repeat_abs_auto_svg_abs_width_no_height_viewbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void repeat_abs_auto_svg_no_width_abs_height_viewboxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("repeat_abs_auto_svg_no_width_abs_height_viewbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void repeat_abs_auto_svg_no_width_no_height_no_viewboxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("repeat_abs_auto_svg_no_width_no_height_no_viewbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void repeat_abs_auto_svg_no_width_no_height_viewboxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("repeat_abs_auto_svg_no_width_no_height_viewbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

// ------------------- Tests with [auto auto] not-repeated background with different SVGs (all 32 combinations)

    @Test
    public void auto_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_width_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_width_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_width_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_width_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void auto_viewbox_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("auto_viewbox_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

// ------------------- Tests with [100px auto] not-repeated background with different SVGs (all 32 combinations)

    @Test
    public void n100px_auto_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_width_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_width_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_width_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_width_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_auto_viewbox_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_auto_viewbox_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

// ------------------- Tests with [100px 100px] not-repeated background with different SVGs (all 32 combinations)

    @Test
    public void n100px_100px_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_width_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_width_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_width_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_width_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void n100px_100px_viewbox_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("100px_100px_viewbox_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

// ------------------- Tests with [contain] not-repeated background with different SVGs (all 32 combinations)

    @Test
    public void contain_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_width_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_width_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_width_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_width_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void contain_viewbox_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("contain_viewbox_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

// ------------------- Tests with [cover] not-repeated background with different SVGs (all 32 combinations)

    @Test
    public void cover_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_width_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_width_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_width_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_width_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_fixed_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_fixed_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_fixed_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_fixed_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_fixed_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_fixed_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_fixed_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_fixed_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_nothing_are_setTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_nothing_are_set", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_nothing_are_set_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_nothing_are_set_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_heightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_height", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_height_fixed_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_height_fixed_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_height_fixed_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_height_fixed_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_height_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_height_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_height_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_height_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_height_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_height_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_widthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_width", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void cover_viewbox_percent_width_prRatio_noneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("cover_viewbox_percent_width_prRatio_none", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
