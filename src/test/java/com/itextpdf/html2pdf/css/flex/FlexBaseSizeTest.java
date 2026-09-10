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
package com.itextpdf.html2pdf.css.flex;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
// TODO DEVSIX-5091 Support flex-basis: content
// TODO DEVSIX-5001 Support content, min-content, max-content as width values
public class FlexBaseSizeTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/flex/FlexBaseSizeTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/flex/FlexBaseSizeTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    // A and E – most common cases from https://www.w3.org/TR/css-flexbox-1/#algo-main-item (flex base size algorithm)

    @Test
    public void flexBasisAutoWidthNumTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoWidthNum", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisAutoHeightNumTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoHeightNum", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisAutoWidthPercentageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoWidthPercentage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisAutoHeightPercentageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoHeightPercentage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisAutoWidthContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoWidthContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisAutoHeightContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoHeightContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-5001 min-content and max-content as width are not supported
    public void flexBasisAutoWidthMinContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoWidthMinContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisAutoWidthMaxContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAutoWidthMaxContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 12))
    public void flexBasisContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 9))
    // TODO DEVSIX-5255 Support aspect-ratio property
    public void flexBasisContentAspectRatioTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentAspectRatio", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 9),
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)
    })
    // Result for image width differs from browser, although min width and flexible lengths are determined according
    // to the CSS specification algorithms. Not sure why browser behaves like this: min main size is calculated based on
    // transferred size suggestion instead of content size suggestion and main size property is ignored.
    public void flexBasisContentAspectRatioImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentAspectRatioImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 9))
    // flexBasisContentAspectRatioImageTest alternative
    public void flexBasisContentAspectRatioImageBrowserTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentAspectRatioImageBrowser", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitMaxSizesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("splitMaxSizes", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitMinSizesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("splitMinSizes", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 10))
    public void flexBasisContentIgnoreMinMaxWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentIgnoreMinMaxWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 5))
    public void flexBasisContentIgnoreMinMaxHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentIgnoreMinMaxHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisContentMaxWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentMaxWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemWithPercentageMaxWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemWithPercentageMaxWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // B from https://www.w3.org/TR/css-flexbox-1/#algo-main-item (Determine the flex base size algorithm)

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    // TODO DEVSIX-5255 Support aspect-ratio property
    public void flexBasisContentAspectRatioDefiniteCrossSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentAspectRatioDefiniteCrossSize", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    // TODO DEVSIX-5255 Support aspect-ratio property
    public void flexBasisContentAspectRatioDefiniteCrossSizeColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentAspectRatioDefiniteCrossSizeColumn", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // C from https://www.w3.org/TR/css-flexbox-1/#algo-main-item (Determine the flex base size algorithm)

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    // TODO DEVSIX-5001 min-content and max-content as width are not supported
    public void flexBasisContentInsideMinContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentInsideMinContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 4))
    // TODO DEVSIX-5001 min-content and max-content as width are not supported
    public void flexBasisContentInsideMaxContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentInsideMaxContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    // TODO DEVSIX-5001 min-content and max-content as width are not supported
    public void flexBasisContentInsideMinContentColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentInsideMinContentColumn", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages =
    @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
    // TODO DEVSIX-5001 min-content and max-content as width are not supported
    public void flexBasisContentInsideMaxContentColumnTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentInsideMaxContentColumn", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // D from https://www.w3.org/TR/css-flexbox-1/#algo-main-item (Determine the flex base size algorithm)

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.VERTICAL_WRITING_MODE_NOT_SUPPORTED_FOR_ELEMENT)
    })
    // TODO DEVSIX-5182 Support writing-mode property
    // E.g. infinite height + vertical main axe for flex container (column) + vertical-writing-mode flex item
    public void flexBasisContentOrthogonalFlowTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentOrthogonalFlow", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
