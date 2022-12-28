/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

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
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FlexAlgoTest extends ExtendedHtmlConversionITextTest {

    private static boolean s = true;

    /* To see unit tests for flex algorithm go to FlexUtilTest in layout module:
    - these test were created as unit tests for flex algo at first
    - the htmls were used to compare the widths, returned by the algo
    - we couldn't maintain such htmls, because they just are present for manual comparison, thus it
    was decided to create html2pdf tests out of them
    - the names are preserved: one can go to FlexUtilTest and see the corresponding tests, but be aware that with
    time they might change and we will not maintain such correspondance */

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FlexAlgoTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FlexAlgoTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void defaultTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("defaultTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void item1BasisGtWidthGrow0Shrink01Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("item1BasisGtWidthGrow0Shrink01Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisGrow1Shrink0MBPOnContainerTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisGrow1Shrink0MBPOnContainerTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisGrow1Shrink0MBPOnContainerNoWidthTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisGrow1Shrink0MBPOnContainerNoWidthTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void basisGtWidthGrow0Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisGtWidthGrow0Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basisMinGrow0Shrink1Item2Grow05Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisMinGrow0Shrink1Item2Grow05Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basisMinGrow0Shrink1Item2Grow2Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisMinGrow0Shrink1Item2Grow2Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basisMinGrow2Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisMinGrow2Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basisMinGrow05SumGt1Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisMinGrow05SumGt1Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basisMinGrow01SumLt1Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisMinGrow01SumLt1Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basisMinGrow0Shrink05SumGt1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisMinGrow0Shrink05SumGt1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basisMinGrow0Shrink01SumLt1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basisMinGrow0Shrink01SumLt1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basis50SumLtWidthGrow0Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basis50SumLtWidthGrow0Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basis250SumGtWidthGrow0Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basis250SumGtWidthGrow0Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow0Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow0Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink0Item2MBP30Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink0Item2MBP30Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MBP30Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MBP30Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void ltWidthGrow0Shrink1Item2MBP30Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("ltWidthGrow0Shrink1Item2MBP30Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void ltWidthGrow0Shrink1Item2MBP30JustifyContentCenterAlignItemsCenterTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("ltWidthGrow0Shrink1Item2MBP30JustifyContentCenterAlignItemsCenterTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void ltWidthGrow0Shrink1Item2MBP30JustifyContentFlexEndAlignItemsFlexEndTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("ltWidthGrow0Shrink1Item2MBP30JustifyContentFlexEndAlignItemsFlexEndTest01",
                SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void ltWidthGrow0Shrink1Item2MBP30JustifyContentFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("ltWidthGrow0Shrink1Item2MBP30JustifyContentFlexStartTest", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void ltWidthGrow0Shrink1Item2MBP30AlignItemsStretchAndNormalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("ltWidthGrow0Shrink1Item2MBP30AlignItemsStretchAndNormal", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void ltWidthGrow0Shrink0Item2MBP30JustifyContentCenterAlignItemsCenterDontFitTest()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("ltWidthGrow0Shrink0Item2MBP30JustifyContentCenterAlignItemsCenterDontFit",
                SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MuchContentTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MuchContentTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthLtBasisTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthLtBasisTest01",
                SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MaxWidthLtBasisTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MaxWidthLtBasisTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MaxWidthLtBasisTest02()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MaxWidthLtBasisTest02", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MaxWidthLtBasisTest03()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MaxWidthLtBasisTest03", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow1Shrink1Item1MinWidthGtBasisTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink1Item1MinWidthGtBasisTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void imgGtUsedWidthTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("imgGtUsedWidthTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthGtBasisTest01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthGtBasisTest01",
                SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void basis1Grow0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basis1Grow0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthGtBasisTest02()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthGtBasisTest02",
                SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthGtBasisTest03()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MuchContentSetMinWidthGtBasisTest03",
                SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumEqWidthGrow1Shrink1Item2Basis0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumEqWidthGrow1Shrink1Item2Basis0Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumEqWidthGrow1Shrink1Item2Basis0NoContentTest02()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumEqWidthGrow1Shrink1Item2Basis0NoContentTest02", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow0Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow0Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow0Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow0Shrink05Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink05Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow0Shrink01Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink01Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow0Shrink5Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink5Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow1Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow1Shrink1Item3Shrink50Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink1Item3Shrink50Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow1Shrink1Item3Shrink5Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink1Item3Shrink5Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow0Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumGtWidthGrow1Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void basis250SumGtWidthGrow0Shrink1WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("basis250SumGtWidthGrow0Shrink1WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow0Shrink1WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink1WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow0Shrink05WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink05WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow0Shrink01WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink01WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow0Shrink5WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink5WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow1Shrink1WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink1WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow1Shrink1Item3Shrink50WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink1Item3Shrink50WrapTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow1Shrink1Item3Shrink5WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink1Item3Shrink5WrapTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow0Shrink0WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow0Shrink0WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 Support flex-wrap property
    public void differentBasisSumGtWidthGrow1Shrink0WrapTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumGtWidthGrow1Shrink0WrapTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumLtWidthGrow0Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumLtWidthGrow0Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumLtWidthGrow1Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumLtWidthGrow1Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumLtWidthGrow0Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumLtWidthGrow0Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumLtWidthGrow1Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumLtWidthGrow1Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow0Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow0Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow0Shrink05Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow0Shrink05Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow0Shrink01Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow0Shrink01Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow0Shrink5Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow0Shrink5Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow1Shrink1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow1Shrink1Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow1Shrink1Item3Shrink50Test01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow1Shrink1Item3Shrink50Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow1Shrink1Item3Shrink5Test01()
            throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow1Shrink1Item3Shrink5Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow0Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow0Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumGtWidthGrow1Shrink0Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumGtWidthGrow1Shrink0Test01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisPercentSumLtWidthGrow0Shrink0Item2Grow1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisPercentSumLtWidthGrow0Shrink0Item2Grow1Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow0Shrink0Item2Grow1Test01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow0Shrink0Item2Grow1Test01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }

    @Test
    public void differentBasisSumLtWidthGrow1Shrink1Item2MarginsTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentBasisSumLtWidthGrow1Shrink1Item2MarginsTest01", SOURCE_FOLDER,
                DESTINATION_FOLDER);
    }
}
