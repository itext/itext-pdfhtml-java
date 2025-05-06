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

import com.itextpdf.commons.utils.FileUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.properties.Leading;
import com.itextpdf.layout.properties.Property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class LineHeightTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/LineHeightTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/LineHeightTest/";
    private static final String RESOURCES = SOURCE_FOLDER + "fonts/";

    @BeforeAll
    public static void init() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
        initConverterProperties();
    }

    @Test
    public void lineHeightFreeSansFontLengthTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightFreeSansFontLengthTest");
    }

    @Test
    public void lineHeightFreeSansFontMaxCoeffTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightFreeSansFontMaxCoeffTest");
    }

    @Test
    // differences with HTML due to default coefficient (see LineHeightHelper#DEFAULT_LINE_HEIGHT_COEFF)
    public void lineHeightFreeSansFontNormalTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightFreeSansFontNormalTest");
    }

    @Test
    public void lineHeightFreeSansFontNumberTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightFreeSansFontNumberTest");
    }

    @Test
    public void lineHeightFreeSansFontPercentageTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightFreeSansFontPercentageTest");
    }


    @Test
    public void lineHeightNotoSansFontMaxCoeffTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightNotoSansFontMaxCoeffTest");
    }

    @Test
    public void lineHeightNotoSansFontNormalTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightNotoSansFontNormalTest");
    }

    @Test
    public void notoSansNormalLineHeightLineBoxMinHeightTest() throws IOException, InterruptedException {
        testLineHeight("notoSansNormalLineHeightLineBoxMinHeightTest");
    }

    @Test
    public void getMaxLineHeightWhenThereAreFewInlineElementsInTheBoxTest() throws IOException, InterruptedException {
        testLineHeight("getMaxLineHeightWhenThereAreFewInlineElementsInTheBoxTest");
    }

    @Test
    public void lineHeightWithDiffFontsTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightWithDiffFontsTest");
    }

    @Test
    public void lineHeightStratFontTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightStratFontTest");
    }

    @Test
    public void imageLineHeightNormalTest() throws IOException, InterruptedException {
        testLineHeight("imageLineHeightNormalTest");
    }

    @Test
    public void imageLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("imageLineHeightTest");
    }

    @Test
    public void imageLineHeightZeroTest() throws IOException, InterruptedException {
        testLineHeight("imageLineHeightZeroTest");
    }

    @Test
    public void imageAscenderLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("imageAscenderLineHeightTest");
    }

    @Test
    public void inputLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("inputLineHeightTest");
    }

    @Test
    public void textAreaLineHeightNormalTest() throws IOException, InterruptedException {
        testLineHeight("textAreaLineHeightNormalTest");
    }

    @Test
    public void textAreaLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("textAreaLineHeightTest");
    }

    @Test
    public void textAreaLineHeightShortTest() throws IOException, InterruptedException {
        testLineHeight("textAreaLineHeightShortTest");
    }

    @Test
    // TODO DEVSIX-2485 change cmp after fixing the ticket
    public void inlineElementLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("inlineElementLineHeightTest");
    }

    @Test
    public void inlineBlockElementLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("inlineBlockElementLineHeightTest");
    }

    @Test
    public void blockElementLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("blockElementLineHeightTest");
    }

    @Test
    public void defaultLineHeightTest() {
        List<IElement> elements = HtmlConverter.convertToElements("<p>Lorem Ipsum</p>");
        Assertions.assertEquals(1.2f, elements.get(0).<Leading>getProperty(Property.LEADING).getValue(), 1e-10);
    }

    @Test
    public void lineHeightEmptyDivTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightEmptyDivTest");
    }

    @Test
    public void lineHeightUnitlessValueInSpanTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightUnitlessValueInSpan");
    }

    @Test
    public void lineHeightLengthValueInSpanTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightLengthValueInSpan");
    }

    @Test
    public void lineHeightPercentageValueInSpanTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightPercentageValueInSpan");
    }

    @Test
    public void lineHeightNormalInSpanTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightNormalInSpan");
    }

    @Test
    public void lineHeightInheritInSpanTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightInheritInSpan");
    }

    @Test
    // TODO: update cmp file after fixing DEVSIX-4604
    public void inlineBlockFloatAndLineHeightTest() throws IOException, InterruptedException {
        testLineHeight("inlineBlockFloatAndLineHeight");
    }

    @Test
    public void lineHeightMathJaxMathFontNormalTest() throws IOException, InterruptedException {
        testLineHeight("lineHeightMathJaxMathFontNormalTest");
    }

    @Test
    public void leadingInConvertToElementsTest() throws IOException, InterruptedException {
        String htmlString = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. "
                + "Cras in mi at felis aliquet congue. Ut a est eget ligula molestie gravida. Curabitur massa.</p>";

        String destinationPdf = DESTINATION_FOLDER + "leadingInConvertToElementsTest.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_leadingInConvertToElementsTest.pdf";

        PdfWriter pdfWriter = new PdfWriter(FileUtil.getFileOutputStream(destinationPdf));

        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDoc, PageSize.LETTER);
        ConverterProperties converterProperties = new ConverterProperties();
        List<IElement> elements = HtmlConverter.convertToElements("<p>" + htmlString + "</p>", converterProperties);

        for (IElement element : elements)
        {
            Paragraph p = (Paragraph)element;
            document.add(p);
        }

        for (IElement element : elements)
        {
            Paragraph p = (Paragraph)element;
            p.setFixedLeading(30);
            p.setFontSize(9);
            p.setFontColor(ColorConstants.RED);
            document.add(p);
        }
        document.close();

        Assertions.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, DESTINATION_FOLDER,
                "diff_leadingInConvertToElementsTest_"));
    }

    void testLineHeight(String name) throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + name + ".html";
        String destinationPdf = DESTINATION_FOLDER + name + ".pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_" + name + ".pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf));
        ConverterProperties converterProperties = initConverterProperties();
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProperties);
        }
        System.out.println("html: file://" + UrlUtil.toNormalizedURI(sourceHtml).getPath() + "\n");
        Assertions.assertNull(
                new CompareTool().compareByContent(destinationPdf, cmpPdf, DESTINATION_FOLDER, "diff_" + name + "_"));
    }

    static ConverterProperties initConverterProperties() {
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri(SOURCE_FOLDER);
        FontProvider fontProvider = new FontProvider();
        fontProvider.addDirectory(RESOURCES);
        fontProvider.addStandardPdfFonts();
        converterProperties.setFontProvider(fontProvider);
        return converterProperties;
    }
}
