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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

@Tag("IntegrationTest")
public class HrTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/HrTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/HrTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
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
    public void hrTest13() throws IOException, InterruptedException {
        //box-shadow property is not supported in iText
        runHrTest("13");
    }

    @Test
    public void hrTest14() throws IOException, InterruptedException {
        runHrTest("14");
    }

    @Test
    public void hrTest15() throws IOException, InterruptedException {
        runHrTest("15");
    }

    @Test
    public void hrTest16() throws IOException, InterruptedException {
        runHrTest("16");
    }

    @Test
    public void hrTest17() throws IOException, InterruptedException {
        runHrTest("17");
    }

    @Test
    public void hrTest18() throws IOException, InterruptedException {
        runHrTest("18");
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
        String htmlPath = sourceFolder + "hrTest" + id + ".html";
        String outPdfPath = destinationFolder + "hrTest" + id + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_hrTest" + id + ".pdf";
        String diff = "diff" + id + "_";
        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdfPath));
        Assertions.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff));
    }
}
