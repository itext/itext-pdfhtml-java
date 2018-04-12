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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;


import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class TableTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TableTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TableTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void helloTableDocumentTest() throws IOException, InterruptedException {
        runTest("hello_table");
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

    @Test  //TODO this test could be improved, somehow.
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

    @Test //TODO this test should be improved, incorrect widths. Each cell shall have its max width.
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

    @Test //TODO this test should be improved, incorrect widths.
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
    @Ignore("DEVSIX-1370")
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
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NOT_SUPPORTED_TH_SCOPE_TYPE, count = 2))
    public void thTagTest() throws IOException, InterruptedException {
        runTest("thTag", true);
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
    // TODO this test currently does not work like in browsers. Cell heights are treated in a very special way in browsers,
    // but they are considered when deciding whether to expand the table.
    // Due to the mechanism layout currently works, we do not pass heights from html to layout for cells because otherwise
    // the content would be clipped if it does not fit, whereas the cell height should be expanted in html in this case.
    // This is the reason why we do not know on layout level if a height was set to an html cell.
    // There is a possibility to work around this problem by extending from TableRenderer for case of thml tables.
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

    private void runTest(String testName) throws IOException, InterruptedException {
        runTest(testName, false);
    }

    private void runTest(String testName, boolean tagged) throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + testName + ".pdf"));
        if (tagged) {
            pdfDocument.setTagged();
        }
        HtmlConverter.convertToPdf(new FileInputStream(sourceFolder + testName + ".html"), pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceFolder + testName + ".html").getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf", sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, "diff_" + testName));
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
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf",
                sourceFolder + "cmp_" + testName + ".pdf", destinationFolder, "diff01_"));

    }

}
