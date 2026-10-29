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

import java.io.IOException;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class SpanCssFeaturesTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/SpanCssFeaturesTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/SpanCssFeaturesTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void spanBackgroundGradientParentAndChildTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBackgroundGradientParentAndChild", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanBackgroundTranslucentOverlapTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBackgroundTranslucentOverlap", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanBorderCurrentColorPerSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBorderCurrentColorPerSpan", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanBorderRadiusSizesParentAndChildTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBorderRadiusSizesParentAndChild", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanBorderStylesPerSideMixTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBorderStylesPerSideMix", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanBrInsideNestedBorderedSpansTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBrInsideNestedBorderedSpans", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 2)
    })
    public void spanBreakOpportunitiesAtSpanEdgesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBreakOpportunitiesAtSpanEdges", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanCascadeSpecificityParentAndChildTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanCascadeSpecificityParentAndChild", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION),
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.DEFAULT_VALUE_OF_CSS_PROPERTY_UNKNOWN)
    })
    public void spanCssWideKeywordsOnNonInheritedPropertiesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanCssWideKeywordsOnNonInheritedProperties", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationAcrossChildPaddingAndBorderTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationAcrossChildPaddingAndBorder", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationLargerChildFontSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationLargerChildFontSize", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationOnBlockChildOfInlineParentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationOnBlockChildOfInlineParent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationPropagationCannotBeUndoneTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationPropagationCannotBeUndone", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationStyleAndThicknessPerLevelTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationStyleAndThicknessPerLevel", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationThicknessAndOffsetInEmTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationThicknessAndOffsetInEm", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationUnderlineOffsetAndPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationUnderlineOffsetAndPosition", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationWithRelativeAndRaisedChildTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationWithRelativeAndRaisedChild", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDecorationWithSpacingAndWhitespaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDecorationWithSpacingAndWhitespace", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDisplayBlockNestedMarginCollapseTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDisplayBlockNestedMarginCollapse", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDisplayContentsOnSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDisplayContentsOnSpan", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDisplayFlexInBorderedInlineParentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDisplayFlexInBorderedInlineParent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDisplayFlexWrapAndGapsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDisplayFlexWrapAndGaps", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDisplayInlineBlockChildMarginsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDisplayInlineBlockChildMargins", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanDisplayInlineFlexBaselineAlignmentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanDisplayInlineFlexBaselineAlignment", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanEmptySpanOccupiesInlineAreaTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanEmptySpanOccupiesInlineArea", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanFontSizeRelativeCompoundingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanFontSizeRelativeCompounding", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanInheritedAndNonInheritedPropertyMixTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanInheritedAndNonInheritedPropertyMix", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanLetterWordSpacingComputedValueInheritedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanLetterWordSpacingComputedValueInherited", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanLineBoxHeightMixedChildrenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanLineBoxHeightMixedChildren", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanMarginCollapseBarriersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanMarginCollapseBarriers", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanOneSidedPaddingAndBorderMixTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanOneSidedPaddingAndBorderMix", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanOpacityMultipliesAndIsNotInheritedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanOpacityMultipliesAndIsNotInherited", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.MARGIN_VALUE_IN_PERCENT_NOT_SUPPORTED, count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED, count = 8)
    })
    public void spanPercentAndEmPaddingResolutionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanPercentAndEmPaddingResolution", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanPositionRelativeNestedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanPositionRelativeNested", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanTextShadowInheritedAndOverriddenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanTextShadowInheritedAndOverridden", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanTextTransformInheritedAndOverriddenTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanTextTransformInheritedAndOverridden", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanVerticalAlignKeywordsAndNestingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanVerticalAlignKeywordsAndNesting", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanVerticalPaddingBorderDoNotAffectLineBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanVerticalPaddingBorderDoNotAffectLineBox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanVisibilityParentAndChildTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanVisibilityParentAndChild", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanWrappedParentWithMarginsAndPaddingFragmentsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanWrappedParentWithMarginsAndPaddingFragments", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
