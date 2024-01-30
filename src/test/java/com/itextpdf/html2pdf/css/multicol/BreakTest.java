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
package com.itextpdf.html2pdf.css.multicol;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BreakTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/multicol/BreakTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/multicol/BreakTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAutoTest() throws IOException, InterruptedException {
        runTest("breakBeforeAutoTest");
    }

    @Test
    public void convertPageBreakBeforeAutoTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeAutoTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAutoInsideColTest() throws IOException, InterruptedException {
        runTest("breakBeforeAutoInsideColTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAlwaysTest() throws IOException, InterruptedException {
        runTest("breakBeforeAlwaysTest");
    }

    @Test
    public void convertPageBreakBeforeAlwaysTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeAlwaysTest");
    }

    //TODO: DEVSIX-7740 Support page-break inside multicol container
    @Test
    public void convertPageBreakBeforeInnerElementAlwaysTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeInnerElementAlwaysTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void convertPageBreakBeforeInnerElementDivAlwaysTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeInnerElementDivAlwaysTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAvoidTest() throws IOException, InterruptedException {
        runTest("breakBeforeAvoidTest");
    }

    @Test
    public void convertPageBreakBeforeAvoidTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeAvoidTest");
    }
    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAvoidInsideColTest() throws IOException, InterruptedException {
        runTest("breakBeforeAvoidInsideColTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAllTest() throws IOException, InterruptedException {
        runTest("breakBeforeAllTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAvoidPageTest() throws IOException, InterruptedException {
        runTest("breakBeforeAvoidPageTest");
    }

    @Test
    public void convertPageBreakBeforeAvoidPageTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeAvoidPageTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAvoidPageInsideColumnTest() throws IOException, InterruptedException {
        runTest("breakBeforeAvoidPageInsideColumnTest");
    }

    @Test
    public void convertPageBreakBeforeAvoidInsideColumnTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeAvoidInsideColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforePageTest() throws IOException, InterruptedException {
        runTest("breakBeforePageTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforePageInsideColumnTest() throws IOException, InterruptedException {
        runTest("breakBeforePageInsideColumnTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void convertPageBreakBeforePageInsideColumnTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforePageInsideColumnTest");
    }

    @Test
    public void convertPageBreakBeforePageColumnTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforePageColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeLeftTest() throws IOException, InterruptedException {
        runTest("breakBeforeLeftTest");
    }

    @Test
    public void convertPageBreakBeforeLeftTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeLeftTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeRightTest() throws IOException, InterruptedException {
        runTest("breakBeforeRightTest");
    }

    @Test
    public void convertPageBreakBeforeRightTest() throws IOException, InterruptedException {
        runTest("pageBreakBeforeRightTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeAvoidColumnTest() throws IOException, InterruptedException {
        runTest("breakBeforeAvoidColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakBeforeColumnTest() throws IOException, InterruptedException {
        runTest("breakBeforeColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAutoTest() throws IOException, InterruptedException {
        runTest("breakAfterAutoTest");
    }

    @Test
    public void convertPageBreakAfterAutoTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterAutoTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAutoInsideColTest() throws IOException, InterruptedException {
        runTest("breakAfterAutoInsideColTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAlwaysTest() throws IOException, InterruptedException {
        runTest("breakAfterAlwaysTest");
    }

    @Test
    public void convertPageBreakAfterAlwaysTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterAlwaysTest");
    }

    //TODO: DEVSIX-7740 Support page-break inside multicol container
    @Test
    public void convertPageBreakAfterInnerElementAlwaysTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterInnerElementAlwaysTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void convertPageBreakAfterInnerElementDivAlwaysTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterInnerElementDivAlwaysTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAvoidTest() throws IOException, InterruptedException {
        runTest("breakAfterAvoidTest");
    }

    @Test
    public void convertPageBreakAfterAvoidTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterAvoidTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAvoidInsideColTest() throws IOException, InterruptedException {
        runTest("breakAfterAvoidInsideColTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAllTest() throws IOException, InterruptedException {
        runTest("breakAfterAllTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAvoidPageTest() throws IOException, InterruptedException {
        runTest("breakAfterAvoidPageTest");
    }

    @Test
    public void convertPageBreakAfterAvoidPageTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterAvoidPageTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAvoidPageInsideColumnTest() throws IOException, InterruptedException {
        runTest("breakAfterAvoidPageInsideColumnTest");
    }

    @Test
    public void convertPageBreakAfterAvoidInsideColumnTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterAvoidInsideColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterPageTest() throws IOException, InterruptedException {
        runTest("breakAfterPageTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterPageInsideColumnTest() throws IOException, InterruptedException {
        runTest("breakAfterPageInsideColumnTest");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void convertPageBreakAfterPageInsideColumnTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterPageInsideColumnTest");
    }

    @Test
    public void convertPageBreakAfterPageColumnTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterPageColumnTest");
    }
    
    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterLeftTest() throws IOException, InterruptedException {
        runTest("breakAfterLeftTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertPageBreakAfterLeftTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterLeftTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterRightTest() throws IOException, InterruptedException {
        runTest("breakAfterRightTest");
    }

    @Test
    public void convertPageBreakAfterRightTest() throws IOException, InterruptedException {
        runTest("pageBreakAfterRightTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterAvoidColumnTest() throws IOException, InterruptedException {
        runTest("breakAfterAvoidColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakAfterColumnTest() throws IOException, InterruptedException {
        runTest("breakAfterColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakInsideAutoTest() throws IOException, InterruptedException {
        runTest("breakInsideAutoTest");
    }

    @Test
    public void convertPageBreakInsideAutoTest() throws IOException, InterruptedException {
        runTest("pageBreakInsideAutoTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakInsideAvoidTest() throws IOException, InterruptedException {
        runTest("breakInsideAvoidTest");
    }

    //TODO: DEVSIX-7740 Support page-break inside multicol container
    @Test
    public void convertPageBreakInsideAvoidTest() throws IOException, InterruptedException {
        runTest("pageBreakInsideAvoidTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakInsideAvoidInsideColumnTest() throws IOException, InterruptedException {
        runTest("breakInsideAvoidInsideColumnTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakInsideAvoidPageTest() throws IOException, InterruptedException {
        runTest("breakInsideAvoidPageTest");
    }

    @Test
    // TODO DEVSIX-3819 support break-after, break-before, break-inside CSS properties
    public void convertBreakInsideAvoidColumnTest() throws IOException, InterruptedException {
        runTest("breakInsideAvoidColumnTest");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        convertToPdfAndCompare(testName,
                SOURCE_FOLDER, DESTINATION_FOLDER, false,
                new ConverterProperties().setBaseUri(SOURCE_FOLDER));
    }
}
