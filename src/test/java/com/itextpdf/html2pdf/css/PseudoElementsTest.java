/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class PseudoElementsTest extends ExtendedHtmlConversionITextTest {
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/PseudoElementsTest/";
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/PseudoElementsTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void pseudoContentWithWidthAndHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoContentWithWidthAndHeightTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pseudoContentWithPercentWidthAndHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoContentWithPercentWidthAndHeightTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pseudoContentDisplayNoneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoContentDisplayNoneTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pseudoContentWithAutoWidthAndHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoContentWithAutoWidthAndHeightTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest07() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest07", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest08() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest08", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2))
    public void beforeAfterPseudoTest09() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest09", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest10() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest11() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest12() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest12", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest13() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest13", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void beforeAfterPseudoTest14() throws IOException, InterruptedException {
        convertToPdfAndCompare("beforeAfterPseudoTest14", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo01() throws IOException, InterruptedException {
        convertToPdfAndCompare("collapsingMarginsBeforeAfterPseudo01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo02() throws IOException, InterruptedException {
        convertToPdfAndCompare("collapsingMarginsBeforeAfterPseudo02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo03() throws IOException, InterruptedException {
        convertToPdfAndCompare("collapsingMarginsBeforeAfterPseudo03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo04() throws IOException, InterruptedException {
        convertToPdfAndCompare("collapsingMarginsBeforeAfterPseudo04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo05() throws IOException, InterruptedException {
        convertToPdfAndCompare("collapsingMarginsBeforeAfterPseudo05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo06() throws IOException, InterruptedException {
        convertToPdfAndCompare("collapsingMarginsBeforeAfterPseudo06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void escapedStringTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("escapedStringTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void escapedStringTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("escapedStringTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void escapedStringTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("escapedStringTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void escapedStringTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("escapedStringTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void escapedStringTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("escapedStringTest05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CONTENT_PROPERTY_INVALID, count = 5))
    public void attrTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("attrTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CONTENT_PROPERTY_INVALID, count = 3))
    public void attrTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("attrTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }


    @Test
    public void emptyStillShownPseudoTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-1393 position property is not supported for inline elements.
    public void emptyStillShownPseudoTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void emptyStillShownPseudoTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-1393 position property is not supported for inline elements.
    public void emptyStillShownPseudoTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-1393 position property is not supported for inline elements.
    public void emptyStillShownPseudoTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void emptyStillShownPseudoTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void emptyStillShownPseudoTest07() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest07", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void emptyStillShownPseudoTest08() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest08", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void emptyStillShownPseudoTest09() throws IOException, InterruptedException {
        convertToPdfAndCompare("emptyStillShownPseudoTest09", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pseudoDisplayTable01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoDisplayTable01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void pseudoDisplayTable02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoDisplayTable02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imgPseudoBeforeDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgPseudoBeforeDiv", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imgPseudoBeforeDivDisplayBlockTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgPseudoBeforeDivDisplayBlock", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER)
    })
    public void imgPseudoBeforeImgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgPseudoBeforeImg", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imgPseudoBeforeWithTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgPseudoBeforeWithText", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imgPseudoBeforeInSeveralDivsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgPseudoBeforeInSeveralDivs", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void imgPseudoWithPageRuleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgPseudoWithPageRule", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void nonNormalizedAfterBeforeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nonNormalizedAfterBefore", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO: update cmp file after DEVSIX-6192 will be fixed
    public void pseudoElementsWithMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("pseudoElementsWithMargin", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
