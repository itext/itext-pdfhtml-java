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
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

@Tag("IntegrationTest")
public class HrTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/HrTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/HrTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void hrHelloTest() throws IOException, InterruptedException {
        runHrTest("00");
    }

    @Test
    public void hrTest01() throws IOException, InterruptedException {
        runHrTest("01");
    }

    @Test
    public void hrTest02() throws IOException, InterruptedException {
        runHrTest("02");
    }

    @Test
    public void hrTest03() throws IOException, InterruptedException {
        runHrTest("03");
    }

    @Test
    public void hrTest04() throws IOException, InterruptedException {
        runHrTest("04");
    }

    @Test
    public void hrTest05() throws IOException, InterruptedException {
        runHrTest("05");
    }

    @Test
    public void hrTest06() throws IOException, InterruptedException {
        runHrTest("06");
    }

    @Test
    public void hrTest07() throws IOException, InterruptedException {
        runHrTest("07");
    }

    //It is expected that in the resulting PDF and firefox the border on the right is visible,
    //but not in Chrome. This is simply because the border in Chrome has the same color as the BG.
    @Test
    public void hrTest08() throws IOException, InterruptedException {
        runHrTest("08");
    }

    @Test
    public void hrTest09() throws IOException, InterruptedException {
        runHrTest("09");
    }

    @Test
    public void hrTest10() throws IOException, InterruptedException {
        runHrTest("10");
    }

    @Test
    public void hrTest11() throws IOException, InterruptedException {
        runHrTest("11");
    }

    @Test
    public void hrTest12() throws IOException, InterruptedException {
        runHrTest("12");
    }

    @Test
    //TODO DEVSIX-4384: support box-shadow
    public void hrTest13() throws IOException, InterruptedException {
        runHrTest("13");
    }

    @Test
    public void hrTest14() throws IOException, InterruptedException {
        runHrTest("14");
    }

    @Test
    //TODO DEVSIX-8856: HR tag should have overflow: hidden by default
    public void hrTest15() throws IOException, InterruptedException {
        runHrTest("15");
    }

    @Test
    //TODO DEVSIX-8856: HR tag should have overflow: hidden by default
    public void hrTest15WihtOverflow() throws IOException, InterruptedException {
        runHrTest("15WithOverflow");
    }

    @Test
    //TODO DEVSIX-8856: HR tag should have overflow: hidden by default
    public void hrTest16() throws IOException, InterruptedException {
        runHrTest("16");
    }

    @Test
    //TODO DEVSIX-8856: HR tag should have overflow: hidden by default
    public void hrTest17() throws IOException, InterruptedException {
        runHrTest("17");
    }

    @Test
    //TODO DEVSIX-8856: HR tag should have overflow: hidden by default
    //TODO DEVSIX-4400: overflow: hidden is not working with border-radius
    public void hrTest18() throws IOException, InterruptedException {
        runHrTest("18");
    }

    @Test
    //TODO DEVSIX-8856: HR tag should have overflow: hidden by default
    //TODO DEVSIX-4400: overflow: hidden is not working with border-radius
    public void hrTest18WithOverflow() throws IOException, InterruptedException {
        runHrTest("18WithOverflow");
    }

    @Test
    public void hrTest19() throws IOException, InterruptedException {
        runHrTest("19");
    }

    @Test
    public void hrTest20() throws IOException, InterruptedException {
        runHrTest("20");
    }

    @Test
    public void hrTest21() throws IOException, InterruptedException {
        runHrTest("21");
    }

    private void runHrTest(String id) throws IOException, InterruptedException {
        convertToPdfAndCompare("hrTest" + id, SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
