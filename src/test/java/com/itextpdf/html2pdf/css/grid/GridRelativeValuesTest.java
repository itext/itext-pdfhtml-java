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
package com.itextpdf.html2pdf.css.grid;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.layout.exceptions.LayoutExceptionMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class GridRelativeValuesTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/grid/GridRelativeValuesTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/grid/GridRelativeValuesTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void bothAxis1Test() throws IOException, InterruptedException {
        runTest("bothAxis1");
    }

    @Test
    public void bothAxis2Test() throws IOException, InterruptedException {
        runTest("bothAxis2");
    }

    @Test
    public void bothAxis3Test() throws IOException, InterruptedException {
        runTest("bothAxis3");
    }

    @Test
    public void bothAxis4Test() throws IOException, InterruptedException {
        runTest("bothAxis4");
    }

    @Test
    public void bothAxis5Test() throws IOException, InterruptedException {
        runTest("bothAxis5");
    }

    @Test
    public void bothAxis6Test() throws IOException, InterruptedException {
        runTest("bothAxis6");
    }

    @Test
    public void bothAxis7Test() throws IOException, InterruptedException {
        runTest("bothAxis7");
    }

    @Test
    public void bothAxis8Test() throws IOException, InterruptedException {
        runTest("bothAxis8");
    }

    @Test
    public void bothAxis9Test() throws IOException, InterruptedException {
        runTest("bothAxis9");
    }

    @Test
    public void bothAxis10Test() throws IOException, InterruptedException {
        runTest("bothAxis10");
    }

    @Test
    public void bothAxis11Test() throws IOException, InterruptedException {
        runTest("bothAxis11");
    }

    @Test
    public void bothAxis12Test() throws IOException, InterruptedException {
        runTest("bothAxis12");
    }

    @Test
    public void bothAxis13Test() throws IOException, InterruptedException {
        runTest("bothAxis13");
    }

    @Test
    public void bothAxis14Test() throws IOException, InterruptedException {
        runTest("bothAxis14");
    }

    @Test
    public void bothAxis15Test() throws IOException, InterruptedException {
        runTest("bothAxis15");
    }

    @Test
    public void bothAxis16Test() throws IOException, InterruptedException {
        runTest("bothAxis16");
    }

    @Test
    public void bothAxis17Test() throws IOException, InterruptedException {
        runTest("bothAxis17");
    }

    @Test
    public void bothAxis18Test() throws IOException, InterruptedException {
        runTest("bothAxis18");
    }

    @Test
    public void bothAxis19Test() throws IOException, InterruptedException {
        runTest("bothAxis19");
    }

    @Test
    public void bothAxis20Test() throws IOException, InterruptedException {
        runTest("bothAxis20");
    }

    @Test
    public void bothAxis21Test() throws IOException, InterruptedException {
        runTest("bothAxis21");
    }

    @Test
    public void bothAxisOnlyFr1Test() throws IOException, InterruptedException {
        runTest("bothAxisOnlyFr1");
    }

    @Test
    public void bothAxisOnlyFr2Test() throws IOException, InterruptedException {
        runTest("bothAxisOnlyFr2");
    }

    @Test
    public void bothAxisOnlyFr3Test() throws IOException, InterruptedException {
        runTest("bothAxisOnlyFr3");
    }

    @Test
    public void bothAxisOnlyFr4Test() throws IOException, InterruptedException {
        runTest("bothAxisOnlyFr4");
    }

    @Test
    public void bothAxisOnlyFr5Test() throws IOException, InterruptedException {
        runTest("bothAxisOnlyFr5");
    }

    @Test
    public void bothAxisOnlyFr6Test() throws IOException, InterruptedException {
        runTest("bothAxisOnlyFr6");
    }

    @Test
    public void colAxis1Test() throws IOException, InterruptedException {
        runTest("colAxis1");
    }

    @Test
    public void colAxis2Test() throws IOException, InterruptedException {
        runTest("colAxis2");
    }

    @Test
    public void colAxis3Test() throws IOException, InterruptedException {
        runTest("colAxis3");
    }

    @Test
    public void colAxis4Test() throws IOException, InterruptedException {
        runTest("colAxis4");
    }

    @Test
    public void colAxis5Test() throws IOException, InterruptedException {
        runTest("colAxis5");
    }

    @Test
    public void colAxis6Test() throws IOException, InterruptedException {
        runTest("colAxis6");
    }

    @Test
    public void colAxis7Test() throws IOException, InterruptedException {
        runTest("colAxis7");
    }

    @Test
    public void colAxis8Test() throws IOException, InterruptedException {
        runTest("colAxis8");
    }

    @Test
    public void colAxis9Test() throws IOException, InterruptedException {
        runTest("colAxis9");
    }

    @Test
    public void colAxis10Test() throws IOException, InterruptedException {
        runTest("colAxis10");
    }

    @Test
    public void colAxis11Test() throws IOException, InterruptedException {
        runTest("colAxis11");
    }

    @Test
    public void rowAxis1Test() throws IOException, InterruptedException {
        runTest("rowAxis1");
    }

    @Test
    public void rowAxis2Test() throws IOException, InterruptedException {
        runTest("rowAxis2");
    }

    @Test
    public void rowAxis3Test() throws IOException, InterruptedException {
        runTest("rowAxis3");
    }

    @Test
    public void rowAxis4Test() throws IOException, InterruptedException {
        runTest("rowAxis4");
    }

    @Test
    public void rowAxis5Test() throws IOException, InterruptedException {
        runTest("rowAxis5");
    }

    @Test
    public void rowAxis6Test() throws IOException, InterruptedException {
        runTest("rowAxis6");
    }

    @Test
    public void rowAxis7Test() throws IOException, InterruptedException {
        runTest("rowAxis7");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CANNOT_BE_COMBINED_WITH_INDEFINITE_SIZES, count = 2)
    })
    public void minmaxAutoRepeat1Test() throws IOException, InterruptedException {
        runTest("minmaxAutoRepeat1");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CANNOT_BE_COMBINED_WITH_INDEFINITE_SIZES, count = 2)
    })
    public void minmaxAutoRepeat2Test() throws IOException, InterruptedException {
        runTest("minmaxAutoRepeat2");
    }

    @Test
    public void minmaxFitContent1Test() throws IOException, InterruptedException {
        runTest("minmaxFitContent1");
    }

    @Test
    public void minmaxFitContent2Test() throws IOException, InterruptedException {
        runTest("minmaxFitContent2");
    }

    @Test
    public void minmaxFitContentAutoRepeat1Test() throws IOException, InterruptedException {
        runTest("minmaxFitContentAutoRepeat1");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutExceptionMessageConstant.GRID_AUTO_REPEAT_CANNOT_BE_COMBINED_WITH_INDEFINITE_SIZES)
    })
    public void minmaxFitContentAutoRepeat2Test() throws IOException, InterruptedException {
        runTest("minmaxFitContentAutoRepeat2");
    }

    @Test
    public void minmaxWithBothAxisSpan1Test() throws IOException, InterruptedException {
        runTest("minmaxWithBothAxisSpan1");
    }

    @Test
    public void minmaxWithBothAxisSpan2Test() throws IOException, InterruptedException {
        runTest("minmaxWithBothAxisSpan2");
    }

    @Test
    public void minmaxWithBothAxisSpan3Test() throws IOException, InterruptedException {
        runTest("minmaxWithBothAxisSpan3");
    }

    @Test
    public void minmaxWithContentAndFrTest() throws IOException, InterruptedException {
        runTest("minmaxWithContentAndFr");
    }

    @Test
    public void minmaxWithSpan1Test() throws IOException, InterruptedException {
        runTest("minmaxWithSpan1");
    }

    @Test
    public void minmaxWithSpan2Test() throws IOException, InterruptedException {
        runTest("minmaxWithSpan2");
    }

    @Test
    public void minmaxWithSpan3Test() throws IOException, InterruptedException {
        runTest("minmaxWithSpan3");
    }

    @Test
    public void minmaxWithSpan4Test() throws IOException, InterruptedException {
        runTest("minmaxWithSpan4");
    }


    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName, SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
