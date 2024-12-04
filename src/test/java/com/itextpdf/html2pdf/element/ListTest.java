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
package com.itextpdf.html2pdf.element;

import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.pdf.PdfAConformance;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.pdfa.PdfADocument; // Android-Conversion-Skip-Line (TODO DEVSIX-7372 investigate why a few tests related to PdfA in iTextCore and PdfHtml were cut)
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class ListTest extends ExtendedHtmlConversionITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/ListTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/ListTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void listTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest03() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest03", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest04() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest04", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.NOT_SUPPORTED_LIST_STYLE_TYPE, count = 32)})
    public void listTest05() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest05", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest06() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest06", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest07() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest07", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest08() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest08", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest09() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest09", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest10() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest10", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest11() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest11", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest12() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest12", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest13() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest13", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest14() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest14", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest15() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest15", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest16() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest16", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest17() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest17", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest18() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest18", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest19() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest19", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listTest20() throws IOException, InterruptedException {
        convertToPdfAndCompare("listTest20", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listLiValuePropertyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("listLiValuePropertyTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listStartPropertyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("listStartPropertyTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listItemValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("listItemValueTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void listItemValueTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("listItemValueTest01", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    public void listItemValueTest02() throws IOException, InterruptedException {
        convertToPdfAndCompare("listItemValueTest02", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    public void descendingListTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("descendingListTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-2431 Positioned elements (e.g. absolute positioning) are lost when block is split across pages
    public void listItemAbsolutePositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("list-item-absolute", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: update after fix of DEVSIX-2537
    //http://www.timrivera.com/tests/ol-start.html
    public void checkOrderedListStartAndValue() throws IOException, InterruptedException {
        convertToPdfAndCompare("checkOrderedListStartAndValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
    @Test
    //TODO: update after fix of DEVSIX-2538
    public void checkOrderedListNestedLists() {
        String expectedMessage = MessageFormatUtil.format("The parameter must be a positive integer");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> convertToPdfAndCompare("checkOrderedListNestedLists", SOURCE_FOLDER, DESTINATION_FOLDER));
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 6)
    })
    //TODO: update after DEVSIX-2093, DEVSIX-2092, DEVSIX-2091 fixes
    public void listsWithInlineChildren() throws IOException, InterruptedException {
        convertToPdfAndCompare("listsWithInlineChildren", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void inlineWithInlineBlockAsLiChildTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("inlineWithInlineBlockAsLiChild", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void convertingListOver2PagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("listOver2Pages", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    // Android-Conversion-Skip-Block-Start (TODO DEVSIX-7372 investigate why a few tests related to PdfA in iTextCore and PdfHtml were cut)
    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = Html2PdfLogMessageConstant.NOT_SUPPORTED_LIST_STYLE_TYPE, count = 32)})
    public void listToPdfaTest() throws IOException, InterruptedException {
        InputStream is = new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm");
        PdfADocument pdfADocument = new PdfADocument(new PdfWriter(DESTINATION_FOLDER + "listToPdfa.pdf"), PdfAConformance.PDF_A_1B, new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is));
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE_FOLDER + "listToPdfa.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfADocument, new ConverterProperties()
                    .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT))
                    .setFontProvider(new BasicFontProvider(false, true, false)));
        }
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "listToPdfa.pdf", SOURCE_FOLDER + "cmp_listToPdfa.pdf",
                DESTINATION_FOLDER, "diff99_"));
    }
    // Android-Conversion-Skip-Block-End
}
