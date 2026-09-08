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
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class VerticalTextMinMaxWidthTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/VerticalTextMinMaxWidthTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/VerticalTextMinMaxWidthTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableAutoLayoutTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableAutoLayout", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableExplicitCellMinMaxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableExplicitCellMinMax", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableColgroupWidthsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableColgroupWidths", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableFixedLayoutTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableFixedLayout", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableDisplayTableCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableDisplayTableCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableColspanVerticalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableColspanVertical", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableRowspanVerticalTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableRowspanVertical", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableMultipleVerticalCellsSameRowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableMultipleVerticalCellsSameRow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableAllVerticalCellsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableAllVerticalCells", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableNowrapCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableNowrapCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexNestedInTableCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexNestedInTableCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableNestedInTableCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableNestedInTableCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexBasisAutoMinWidthAutoTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexBasisAutoMinWidthAuto", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexMinWidthZeroOverrideTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexMinWidthZeroOverride", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexShrinkBelowContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexShrinkBelowContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexGrowWithMaxWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexGrowWithMaxWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexBasisZeroPercentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexBasisZeroPercent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexProportionalGrowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexProportionalGrow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexStretchCrossAxisTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexStretchCrossAxis", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.FONT_PROPERTY_MUST_BE_PDF_FONT_OBJECT)})
    public void vertMinMaxFlexColumnDirectionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexColumnDirection", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexWrapMultipleItemsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexWrapMultipleItems", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexNestedContainersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexNestedContainers", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxFlexLogicalBlockSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxFlexLogicalBlockSize", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxTableNestedInFlexItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxTableNestedInFlexItem", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockWrapperAutoWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockWrapperAutoWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockWrapperExplicitMinMaxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockWrapperExplicitMinMax", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockElementItselfTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockElementItself", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockPercentageWidthDefiniteParentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockPercentageWidthDefiniteParent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockDifferentFontSizesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockDifferentFontSizes", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockMultipleSideBySideTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockMultipleSideBySide", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockInTableCellTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockInTableCell", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockOverflowHiddenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockOverflowHidden", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxInlineBlockDoubleNestedWrappersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxInlineBlockDoubleNestedWrappers", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxGridAutoTracksTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxGridAutoTracks", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxGridMinmaxFunctionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxGridMinmaxFunction", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxGridMinWidthZeroTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxGridMinWidthZero", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxGridNestedInGridItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxGridNestedInGridItem", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBoxSizingContentBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBoxSizingContentBox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBoxSizingBorderBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBoxSizingBorderBox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakLogicalSizeInTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakLogicalSizeInTable", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakTableConflictingColumnWidthsAcrossRowsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakTableConflictingColumnWidthsAcrossRows", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void vertMinMaxBreakTableFixedTinyWidthUnbreakableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakTableFixedTinyWidthUnbreakable", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void vertMinMaxBreakTableZeroWidthCellOverflowHiddenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakTableZeroWidthCellOverflowHidden", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakTableEverythingConflictsAtOnceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakTableEverythingConflictsAtOnce", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakZeroWidthFlexBasisTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakZeroWidthFlexBasis", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakUnbreakableMinContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakUnbreakableMinContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakFlexShrinkZeroOverflowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakFlexShrinkZeroOverflow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakFlexMinWidthOverridesShrinkTargetTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakFlexMinWidthOverridesShrinkTarget", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)})
    public void vertMinMaxBreakFlexInvalidNegativeBasisTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakFlexInvalidNegativeBasis", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakFlexNegativeMarginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakFlexNegativeMargin", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakPercentageOnIndefiniteAncestorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakPercentageOnIndefiniteAncestor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakNestedConflictingConstraintsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakNestedConflictingConstraints", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakInlineBlockZeroWidthNowrapAncestorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakInlineBlockZeroWidthNowrapAncestor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakInlineBlockDoubleNowrapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakInlineBlockDoubleNowrap", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakInlineBlockNegativePaddingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakInlineBlockNegativePadding", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakInlineBlockFloatOverridesDisplayTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakInlineBlockFloatOverridesDisplay", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakGridMinmaxZeroToFrTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakGridMinmaxZeroToFr", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakGridAutoFillMinmaxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakGridAutoFillMinmax", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakGridMinmaxInvertedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakGridMinmaxInverted", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakConflictingMinGreaterThanMaxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakConflictingMinGreaterThanMax", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakMinExceedsContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakMinExceedsContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakEmptyContentWithConstraintsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakEmptyContentWithConstraints", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakExtremeLargeMinWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakExtremeLargeMinWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void vertMinMaxBreakExtremeTinyMaxWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("vertMinMaxBreakExtremeTinyMaxWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
