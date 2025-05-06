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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.exceptions.PdfException;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Tag("IntegrationTest")
public class TableTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TableTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TableTest/";


    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void helloTableDocumentTest() throws IOException, InterruptedException {
        runTest("hello_table");
    }

    @Test
    public void checkBasicTableFeatures() throws IOException, InterruptedException {
        runTest("checkBasicTableFeatures");
    }

    @Test
    //TODO update after DEVSIX-2908
    public void check_th_align() throws IOException, InterruptedException {
        runTest("th_align");
    }

    @Test
    public void helloTableFixed1DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed1");
    }

    @Test
    public void helloTableFixed2DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed2");
    }

    @Test
    public void helloTableFixed3DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed3");
    }

    @Test
    public void helloTableFixed4DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed4");
    }

    @Test
    public void helloTableFixed5DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed5");
    }

    @Test  //TODO: DEVSIX-5967 Incorrect cell content layout for 'table-layout: fixed' tag.
    public void helloTableFixed6DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed6");
    }

    @Test
    public void helloTableFixed7DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed7");
    }

    @Test
    public void helloTableFixed8DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_fixed8");
    }

    @Test
    public void helloTableFixedLackOfTableWidthTest01() throws IOException, InterruptedException {
        runTest("helloTableFixedLackOfTableWidthTest01", false, new PageSize(PageSize.A3).rotate());
    }

    @Test
    public void helloTableFixedLackOfTableWidthTest01A() throws IOException, InterruptedException {
        runTest("helloTableFixedLackOfTableWidthTest01A", false, new PageSize(PageSize.A3).rotate());
    }

    @Test
    public void helloTableFixedLackOfTableWidthTest02() throws IOException, InterruptedException {
        runTest("helloTableFixedLackOfTableWidthTest02", false, new PageSize(PageSize.A3).rotate());
    }

    @Test
    public void helloTableFixedLackOfTableWidthTest02A() throws IOException, InterruptedException {
        runTest("helloTableFixedLackOfTableWidthTest02A", false, new PageSize(PageSize.A3).rotate());
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.SUM_OF_TABLE_COLUMNS_IS_GREATER_THAN_100, count = 3))
    public void helloTableFixedLackOfTableWidthTest03() throws IOException, InterruptedException {
        runTest("helloTableFixedLackOfTableWidthTest03", false, new PageSize(PageSize.A3).rotate());
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.SUM_OF_TABLE_COLUMNS_IS_GREATER_THAN_100, count = 3))
    public void helloTableFixedLackOfTableWidthTest03A() throws IOException, InterruptedException {
        runTest("helloTableFixedLackOfTableWidthTest03A", false, new PageSize(PageSize.A3).rotate());
    }

    @Test
    public void helloTableAutoDocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto");
    }

    @Test
    public void helloTableAuto2DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto2");
    }

    @Test
    public void helloTableAuto3DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto3");
    }

    @Test
    public void helloTableAuto4DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto4");
    }

    @Test //TODO: DEVSIX-5969 Incorrect text wrapping for 'table-layout: auto' tag.
    public void helloTableAuto5DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto5");
    }

    @Test
    public void helloTableAuto6DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto6");
    }

    @Test
    public void helloTableAuto7DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto7");
    }

    @Test
    public void helloTableAuto8DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto8");
    }

    @Test
    public void helloTableAuto9DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto9");
    }

    @Test
    public void helloTableAuto10DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto10");
    }

    @Test
    public void helloTableAuto11DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto11");
    }

    @Test
    public void helloTableAuto12DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto12");
    }

    @Test
    @Disabled("DEVSIX-1370")
    public void helloTableAuto13DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto13");
    }

    @Test
    public void helloTableAuto14DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto14");
    }

    @Test
    public void helloTableAuto15DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto15");
    }

    @Test
    public void helloTableAuto16DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto16");
    }

    @Test
    public void helloTableAuto17DocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_auto17");
    }

    @Test
    public void helloTableHeaderFooterDocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_header_footer");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate =  IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = IoLogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE)
    })
    //TODO update after DEVSIX-2395 and DEVSIX-2399
    public void checkHeaderFooterTaggedTables() throws IOException, InterruptedException {
        runTest("checkHeaderFooterTaggedTables");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE)})
    //TODO update after DEVSIX-2395 and DEVSIX-2399
    public void checkFloatInTdTagged() throws IOException, InterruptedException {
        runTest("checkFloatInTdTagged");
    }

    @Test
    //TODO update after DEVSIX-2399
    public void checkDisplayInTableTagged() throws IOException, InterruptedException {
        runTest("checkDisplayInTableTagged");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate =  IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 3),
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 2)
    })
    //TODO update after DEVSIX-2382
    public void checkLargeImagesInTable() throws IOException, InterruptedException {
        runTest("checkLargeImagesInTable");
    }

    @Test
    public void helloTableColspanDocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_colspan");
    }

    @Test
    public void helloTableRowspanDocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_rowspan");
    }

    @Test
    public void helloTableColspanRowspanDocumentTest() throws IOException, InterruptedException {
        runTest("hello_table_colspan_rowspan");
    }

    @Test
    public void tableCssPropsTest01() throws IOException, InterruptedException {
        runTest("tableCssPropsTest01");
    }

    @Test
    public void tableCssPropsTest02() throws IOException, InterruptedException {
        runTest("tableCssPropsTest02");
    }

    @Test
    public void tableCssPropsTest03() throws IOException, InterruptedException {
        runTest("tableCssPropsTest03");
    }

    @Test
    public void defaultTableTest() throws IOException, InterruptedException {
        runTest("defaultTable");
    }

    @Test
    public void textInTableAndRowTest() throws IOException, InterruptedException {
        runTest("textInTableAndRow");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NOT_SUPPORTED_TH_SCOPE_TYPE, count = 2))
    public void thTagTest() throws IOException, InterruptedException {
        runTest("thTag", true);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NOT_SUPPORTED_TH_SCOPE_TYPE, count = 2))
    public void theadTagTest() throws IOException, InterruptedException {
        runTest("theadTagTest", true);
    }

    @Test
    public void tfootTagTest() throws IOException, InterruptedException {
        runTest("tfootTagTest", true);
    }

    @Test
    public void brInTdTest() throws IOException, InterruptedException {
        runTest("brInTd");
    }

    @Test
    public void tableBorderAttributeTest01() throws IOException, InterruptedException {
        runTest("tableBorderAttributeTest01");
    }

    @Test
    public void tableBorderAttributeTest02() throws IOException, InterruptedException {
        runTest("tableBorderAttributeTest02");
    }

    @Test
    public void tableBorderAttributeTest03() throws IOException, InterruptedException {
        runTest("tableBorderAttributeTest03");
    }

    @Test
    public void tableBorderAttributeTest04() throws IOException, InterruptedException {
        runTest("tableBorderAttributeTest04");
    }

    @Test
    public void tableBorderAttributeTest05() throws IOException, InterruptedException {
        runTest("tableBorderAttributeTest05");
    }

    @Test
    public void tableBorderAttributeTest06() throws IOException, InterruptedException {
        runTest("tableBorderAttributeTest06");
    }

    @Test
    // This test currently does not work like in browsers. Cell heights are treated in a very special way in browsers,
    // but they are considered when deciding whether to expand the table.
    // Due to the mechanism layout currently works, we do not pass heights from html to layout for cells because otherwise
    // the content would be clipped if it does not fit, whereas the cell height should be expanded in html in this case.
    // This is the reason why we do not know on layout level if a height was set to an html cell.
    // There is a possibility to work around this problem by extending from TableRenderer for case of html tables,
    // but this problem seems really not that important and a very narrow use case for now.
    // For related ticket, see DEVSIX-1072
    public void tableCellHeightsExpansionTest01() throws IOException, InterruptedException {
        runTest("tableCellHeightsExpansion01");
    }

    @Test
    public void tableCellHeightsExpansionTest02() throws IOException, InterruptedException {
        runTest("tableCellHeightsExpansion02");
    }

    @Test
    public void tableCellHeightsExpansionTest03() throws IOException, InterruptedException {
        // Cells max-height property should not affect layout, just like in browsers.
        runTest("tableCellHeightsExpansion03");
    }

    @Test
    public void tableMaxHeightTest01() throws IOException, InterruptedException {
        runTest("tableMaxHeight01");
    }

    @Test
    public void tableMaxHeightTest02() throws IOException, InterruptedException {
        runTest("tableMaxHeight02");
    }

    @Test
    public void multipleRowsInHeade01() throws IOException, InterruptedException {
        runTest("multipleRowsInHeader01");
    }

    @Test
    public void tableCollapseColCellBoxSizingWidthDifference() throws IOException, InterruptedException {
        runTest("table_collapse_col_cell_box_sizing_width_difference");
    }

    @Test
    public void colspanInHeaderFooterTest() throws IOException, InterruptedException {
        runTest("table_header_footer_colspan");
    }

    @Test
    public void separateBorder01() throws IOException, InterruptedException {
        runTest("separateBorder01");
    }

    @Test
    public void thScopeTaggedTest() throws IOException, InterruptedException {
        runTest("thTagScopeTagged", true);
    }

    @Test
    public void thScopeTaggedDifferentTablesTest() throws IOException, InterruptedException {
        runConvertToElements("thTagScopeTaggedDifferentTables", true);
    }

    @Test
    public void thScopeNotTaggedDifferentTablesTest() throws IOException, InterruptedException {
        runConvertToElements("thTagScopeNotTaggedDifferentTables", false);
    }

    @Test
    // TODO DEVSIX-2092
    public void plainTextTest() throws IOException, InterruptedException {
        runConvertToElements("plainTextTest", false);
    }

    @Test
    public void separatedTablesWithDifferentCaptionsTest01() throws IOException, InterruptedException {
        runTest("separatedTableWithDifferentCaptionsTest01", false);
    }

    @Test
    public void collapsedTablesWithDifferentCaptionsTest01() throws IOException, InterruptedException {
        runTest("collapsedTablesWithDifferentCaptionsTest01", false);
    }

    @Test
    public void captionWithTextAlignTest01() throws IOException, InterruptedException {
        runTest("captionWithTextAlignTest01", false);
    }

    @Test
    public void wideCaptionTest01() throws IOException, InterruptedException {
        runTest("wideCaptionTest01", false);
    }

    @Test
    public void wideCaptionTest02() throws IOException, InterruptedException {
        runTest("wideCaptionTest02", false);
    }

    @Test
    public void wideTableWithCaptionTest01() throws IOException, InterruptedException {
        runTest("wideTableWithCaptionTest01", false);
    }

    @Test
    public void wideTableWithCaptionTest02() throws IOException, InterruptedException {
        runTest("wideTableWithCaptionTest02", false);
    }

    @Test
    public void captionSideTest01() throws IOException, InterruptedException {
        runTest("captionSideTest01", false);
    }

    @Test
    public void captionSideSetAsAlignTest01() throws IOException, InterruptedException {
        runTest("captionSideSetAsAlignTest01", false);
    }

    @Test
    public void tableCellMinWidthRightAlignmentTest() throws IOException, InterruptedException {
        runConvertToElements("tableCellMinWidthRightAlignmentTest", false);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.SUM_OF_TABLE_COLUMNS_IS_GREATER_THAN_100, count = 4)})
    //TODO: DEVSIX-2895 - inconsistency in table width between pdf and html
    public void tableWidthMoreThan100PercentTest() throws IOException, InterruptedException {
        runTest("tableWidthMoreThan100Percent");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED, count = 63)})
    //https://codepen.io/heypablete/pen/qdIsm
    //TODO: update after DEVSIX-1101
    public void checkResponsiveTableExample() throws IOException, InterruptedException {
        runTest("checkResponsiveTableExample");
    }

    @Test
    public void tableWithChildrenBiggerThanCellTest() throws IOException, InterruptedException {
        runTest("tableWithChildrenBiggerThanCell");
    }

    @Test
    // TODO DEVSIX-4247
    public void tableRowAndCellBackgroundColorConflictTest() throws IOException, InterruptedException {
        runTest("tableRowAndCellBackgroundColorConflictTest");
    }

    @Test
    // TODO DEVSIX-5036
    public void collapsedBorderWithWrongRowspanTableTest() {
        Assertions.assertThrows(RuntimeException.class,
                () -> runTest("collapsedBorderWithWrongRowspanTable", false, new PageSize(PageSize.A5).rotate()));
    }

    @Test
    // TODO DEVSIX-4806
    public void cellWithRowspanShouldBeConsideredWhileCalculatingColumnWidths() throws IOException, InterruptedException {
        runTest("cellWithRowspanShouldBeConsideredWhileCalculatingColumnWidths");
    }


    @Test
    public void emptyTrRowspanBorderCollapsingTest() throws IOException, InterruptedException {
        runTest("emptyTrRowspanBorderCollapsing");
    }

    @Test
    // TODO DEVSIX-6068 support empty td tag
    public void emptyTdTest() throws IOException, InterruptedException {
        runTest("emptyTd");
    }

    @Test
    public void emptyTrTest() throws IOException, InterruptedException {
        runTest("emptyTr");
    }

    @Test
    public void tagsFlushingErrorWhenConvertedFromHtmlTest() throws IOException {
        String file = sourceFolder + "tagsFlushingErrorWhenConvertedFromHtml.html";

        List<IElement> elements = HtmlConverter.convertToElements(new FileInputStream(file),
                new ConverterProperties().setCreateAcroForm(true));

        PdfDocument pdf = new PdfDocument(new PdfWriter(new FileOutputStream(
                destinationFolder + "tagsFlushingErrorWhenConvertedFromHtml.pdf")));
        pdf.setTagged();

        Document document = new Document(pdf);
        document.setMargins(10, 50, 10, 50);

        for (IElement element : elements) {
            document.add((IBlockElement) element);
        }

        Exception exception = Assertions.assertThrows(PdfException.class, () -> document.close());
        Assertions.assertEquals("Tag structure flushing failed: it might be corrupted.", exception.getMessage());
    }

    @Test
    public void imageScaleTest() throws IOException, InterruptedException {
        runTest("imageScale");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE),
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH),
    })
    // TODO This test should be considered during DEVSIX-1655. After the ticket is fixed, the cmp might get updated
    public void tableSplitAndNotInitializedAreaTest() throws IOException, InterruptedException {
        runTest("tableSplitAndNotInitializedArea");
    }

    @Test
    public void repeatFooterHeaderInComplexTableTest() throws IOException, InterruptedException {
        runTest("repeatFooterHeaderInComplexTable");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NOT_SUPPORTED_TH_SCOPE_TYPE, count = 2)
    })
    public void thTagConvertToElementTest() throws IOException, InterruptedException {
        runConvertToElements("thTagConvertToElement", false);
    }

    @Test
    public void emptyRowsConvertToElementTest() throws IOException {
        FileInputStream source = new FileInputStream(sourceFolder + "emptyRowsConvertToElement.html");

        for (IElement element : HtmlConverter.convertToElements(source)) {
            Assertions.assertTrue(element instanceof Table);
            Assertions.assertEquals(4, ((Table) element).getNumberOfRows());
        }
    }

    @Test
    public void thTagConvertToPdfTest() throws IOException, InterruptedException {
        runTest("thTagConvertToPdf");
    }

    @Test
    public void inlineWithInlineBlockAsTdChildTest() throws IOException, InterruptedException {
        runTest("inlineWithInlineBlockAsTdChild");
    }

    @Test
    public void inlineWithInlineBlockAsTdChildWrappedTest() throws IOException, InterruptedException {
        runTest("inlineWithInlineBlockAsTdChildWrapped");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE, count = 2)})
    public void emptyRowEliminationTest1() throws IOException, InterruptedException {
        runTest("emptyRowElimination1");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE)})
    public void emptyRowEliminationTest2() throws IOException, InterruptedException {
        runTest("emptyRowElimination2");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH,
                    count = 2),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)
    })
    public void breakRendererTreeOnSplitupTest() throws IOException, InterruptedException {
        runTest("breakRendererTreeOnSplitup");
    }

    private void runTest(String testName) throws IOException, InterruptedException {
        runTest(testName, false);
    }

    private void runTest(String testName, boolean tagged) throws IOException, InterruptedException {
        runTest(testName, tagged, null);
    }

    private void runTest(String testName, boolean tagged, PageSize pageSize) throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + testName + ".pdf"));
        if (null != pageSize) {
            pdfDocument.setDefaultPageSize(pageSize);
        }
        if (tagged) {
            pdfDocument.setTagged();
        }
        HtmlConverter.convertToPdf(new FileInputStream(sourceFolder + testName + ".html"), pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(sourceFolder + testName + ".html")+ "\n");
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf", sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, "diff_" + testName));
    }

    private void runConvertToElements(String testName, boolean tagged) throws IOException, InterruptedException {
        FileInputStream source = new FileInputStream(sourceFolder + testName + ".html");
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + testName + ".pdf"));
        if (tagged) {
            pdfDocument.setTagged();
        }
        Document layoutDocument = new Document(pdfDocument);
        ConverterProperties props = new ConverterProperties();

        for (IElement element : HtmlConverter.convertToElements(source, props)) {
            if (element instanceof IBlockElement)
                layoutDocument.add((IBlockElement) element);
        }
        layoutDocument.close();
        pdfDocument.close();
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf",
                sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, "diff01_"));

    }

}
