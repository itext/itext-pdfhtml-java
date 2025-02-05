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
package com.itextpdf.html2pdf.css;

import com.itextpdf.forms.logs.FormsLogMessageConstants;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class DisplayTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/DisplayTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/DisplayTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void displayTable01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayTable01ATest() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table01A", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH)
    })
    public void displayTable02Test() throws IOException, InterruptedException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + "display_table02.pdf"));
        pdfDoc.setDefaultPageSize(new PageSize(1500, 842));
        HtmlConverter.convertToPdf(new FileInputStream(SOURCE_FOLDER + "display_table02.html"), pdfDoc);
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "display_table02.pdf",
                SOURCE_FOLDER + "cmp_display_table02.pdf",
                DESTINATION_FOLDER, "diff20_"));
    }

    @Test
    public void displayTable03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-2445
    public void displayTable04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH)
    })
    public void displayTable05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH)
    })
    public void displayTable05aTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table05a", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayTable06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 1)
    })
    public void displayTable07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table07", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayTable08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table08", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH),
            @LogMessage(messageTemplate = IoLogMessageConstant.SUM_OF_TABLE_COLUMNS_IS_GREATER_THAN_100),
    })
    public void displayTable09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table09", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayTable10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH),
    })
    @Test
    public void displayTable11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_table11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: update after DEVSIX-2445 fix
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 6),
            @LogMessage(messageTemplate = FormsLogMessageConstants.DUPLICATE_EXPORT_VALUE, count = 2)
    })
    public void displayBlockInsideParagraphTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayBlockInsideParagraph", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-6163 Image is converted in outPdf as inline element when display: block is set
    public void displayBlockInsideImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayBlockInsideImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInline01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.INLINE_BLOCK_ELEMENT_WILL_BE_CLIPPED)})
    public void displayInlineBlock02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block07", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block08", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block09", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block12", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block13", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block14", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block15", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock16Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block16", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock17Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block17", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlock18Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block18", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockAndWidthInDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockAndWidthInDiv", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockJustified01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block_justified01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockJustified02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("display_inline-block_justified02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockYLineTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockYLineTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockYLineTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockYLineTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockYLineTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockYLineTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockYLineTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockYLineTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockYLineTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockYLineTest05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayInlineBlockYLineTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockYLineTest06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO Fix cmp after DEVSIX-3429 is implemented. The checkbox should become aligned with the baseline of the text
    public void displayInlineBlockYLineTest07() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayInlineBlockYLineTest07", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayNoneImportant01() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayNoneImportant01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayNoneImportant02() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayNoneImportant02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayNoneImportant03() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayNoneImportant03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayDivInlineWithStyle() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayDivInlineWithStyle", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION
            , count = 7))
    public void inlineBlockInsideTableCellTest() throws IOException, InterruptedException {

        // IO setup
        PdfWriter writer = new PdfWriter(new File(DESTINATION_FOLDER + "inlineBlockInsideTableCellTest.pdf"));
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(new PageSize(1000f, 1450f));

        // properties
        ConverterProperties props = new ConverterProperties();
        props.setBaseUri(SOURCE_FOLDER);

        HtmlConverter.convertToPdf(new FileInputStream(SOURCE_FOLDER + "inlineBlockInsideTableCellTest.html"),
                pdfDocument, props);
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "inlineBlockInsideTableCellTest.pdf",
                SOURCE_FOLDER
                        + "cmp_inlineBlockInsideTableCell.pdf", DESTINATION_FOLDER,
                "diffinlineBlockInsideTableCellTest_"));
    }

    @Test
    public void displayValuesInsideImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayValuesInsideImage", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
