/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class PseudoElementsTest extends ExtendedITextTest {
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/PseudoElementsTest/";
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/PseudoElementsTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void beforeAfterPseudoTest01() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest01");
    }

    @Test
    public void beforeAfterPseudoTest02() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest02");
    }

    @Test
    public void beforeAfterPseudoTest03() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest03");
    }

    @Test
    public void beforeAfterPseudoTest04() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest04");
    }

    @Test
    public void beforeAfterPseudoTest05() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest05");
    }

    @Test
    public void beforeAfterPseudoTest06() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest06");
    }

    @Test
    public void beforeAfterPseudoTest07() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest07");
    }

    @Test
    public void beforeAfterPseudoTest10() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest10");
    }

    @Test
    public void beforeAfterPseudoTest11() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest11");
    }

    @Test
    public void beforeAfterPseudoTest12() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest12");
    }

    @Test
    public void beforeAfterPseudoTest13() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest13");
    }

    @Test
    public void beforeAfterPseudoTest14() throws IOException, InterruptedException {
        runTest("beforeAfterPseudoTest14");
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo01() throws IOException, InterruptedException {
        runTest("collapsingMarginsBeforeAfterPseudo01");
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo02() throws IOException, InterruptedException {
        runTest("collapsingMarginsBeforeAfterPseudo02");
    }

    @Test
    //TODO: incorrect behaviour because of trimmed non-breakable space
    public void collapsingMarginsBeforeAfterPseudo03() throws IOException, InterruptedException {
        runTest("collapsingMarginsBeforeAfterPseudo03");
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo04() throws IOException, InterruptedException {
        runTest("collapsingMarginsBeforeAfterPseudo04");
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo05() throws IOException, InterruptedException {
        runTest("collapsingMarginsBeforeAfterPseudo05");
    }

    @Test
    public void collapsingMarginsBeforeAfterPseudo06() throws IOException, InterruptedException {
        runTest("collapsingMarginsBeforeAfterPseudo06");
    }

    @Test
    public void escapedStringTest01() throws IOException, InterruptedException {
        runTest("escapedStringTest01");
    }

    @Test
    public void escapedStringTest02() throws IOException, InterruptedException {
        runTest("escapedStringTest02");
    }

    @Test
    public void escapedStringTest03() throws IOException, InterruptedException {
        runTest("escapedStringTest03");
    }

    @Test
    public void escapedStringTest04() throws IOException, InterruptedException {
        runTest("escapedStringTest04");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.CONTENT_PROPERTY_INVALID, count = 5))
    public void attrTest01() throws IOException, InterruptedException {
        runTest("attrTest01");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.CONTENT_PROPERTY_INVALID, count = 3))
    public void attrTest02() throws IOException, InterruptedException {
        runTest("attrTest02");
    }


    @Test
    public void emptyStillShownPseudoTest01() throws IOException, InterruptedException {
        runTest("emptyStillShownPseudoTest01");
    }

    @Test
    public void emptyStillShownPseudoTest02() throws IOException, InterruptedException {
        // TODO inline elements with absolute positioning are not supported at the moment
        runTest("emptyStillShownPseudoTest02");
    }

    @Test
    public void emptyStillShownPseudoTest03() throws IOException, InterruptedException {
        // TODO inline elements with absolute positioning are not supported at the moment
        runTest("emptyStillShownPseudoTest03");
    }

    @Test
    public void emptyStillShownPseudoTest04() throws IOException, InterruptedException {
        // TODO inline elements with absolute positioning are not supported at the moment
        runTest("emptyStillShownPseudoTest04");
    }

    @Test
    public void emptyStillShownPseudoTest05() throws IOException, InterruptedException {
        runTest("emptyStillShownPseudoTest05");
    }

    @Test
    public void emptyStillShownPseudoTest06() throws IOException, InterruptedException {
        runTest("emptyStillShownPseudoTest06");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES),
    })
    public void emptyStillShownPseudoTest07() throws IOException, InterruptedException {
        runTest("emptyStillShownPseudoTest07");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.RECTANGLE_HAS_NEGATIVE_OR_ZERO_SIZES),
    })
    public void emptyStillShownPseudoTest08() throws IOException, InterruptedException {
        runTest("emptyStillShownPseudoTest08");
    }

    @Test
    public void emptyStillShownPseudoTest09() throws IOException, InterruptedException {
        runTest("emptyStillShownPseudoTest09");
    }

    @Test
    public void pseudoDisplayTable01Test() throws IOException, InterruptedException {
        runTest("pseudoDisplayTable01");
    }

    @Test
    public void pseudoDisplayTable02Test() throws IOException, InterruptedException {
        runTest("pseudoDisplayTable02");
    }

    private void runTest(String name) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath));
        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }
}
