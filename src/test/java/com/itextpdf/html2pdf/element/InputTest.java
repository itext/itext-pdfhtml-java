/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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

import com.itextpdf.forms.form.element.InputField;
import com.itextpdf.forms.logs.FormsLogMessageConstants;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import com.itextpdf.test.pdfa.VeraPdfValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class InputTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/InputTest/";
    public static final String sourceFolderResources = "./src/test/resources/com/itextpdf/html2pdf/fonts/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/InputTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void input01Test() throws IOException, InterruptedException {
        runTest("inputTest01");
    }

    @Test
    public void input02Test() throws IOException, InterruptedException {
        runTest("inputTest02");
    }

    @Test
    public void input03Test() throws IOException, InterruptedException {
        runTest("inputTest03");
    }

    @Test
    public void input04Test() throws IOException, InterruptedException {
        runTest("inputTest04");
    }

    @Test
    public void input05Test() throws IOException, InterruptedException {
        runTest("inputTest05");
    }

    @Test
    @LogMessages(ignore = true, messages = {
            @LogMessage(messageTemplate = FormsLogMessageConstants.INPUT_FIELD_DOES_NOT_FIT),
    })
    public void input06Test() throws IOException, InterruptedException {
        String htmlPath = sourceFolder + "inputTest06.html";
        String outPdfPath = destinationFolder + "inputTest06.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + "inputTest06.pdf";
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outPdfPath));
        pdfDoc.setDefaultPageSize(PageSize.A8);
        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), pdfDoc, new ConverterProperties().setCreateAcroForm(true));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, "diff_inputTest06_"));
    }

    @Test
    public void input07Test() throws IOException, InterruptedException {
        // TODO DEVSIX-1777: if not explicitly specified, <input> border default value should be different from the one
        // specified in user agent css. Also user agent css should not specify default color
        // and should use 'initial' instead.
        runTest("inputTest07");
    }

    @Test
    public void input08Test() throws IOException, InterruptedException {
        runTest("inputTest08");
    }

    @Test
    public void input09Test() throws IOException, InterruptedException {
        runTest("inputTest09");
    }

    @Test
    public void input10Test() throws IOException, InterruptedException {
        runTest("inputTest10");
    }

    @Test
    public void textareaRowsHeightTest() throws IOException, InterruptedException {
        runTest("textareaRowsHeight");
    }

    @Test
    public void blockHeightTest() throws IOException, InterruptedException {
        runTest("blockHeightTest");
    }

    @Test
    public void smallPercentWidthTest() throws IOException, InterruptedException {
        runTest("smallPercentWidth");
    }

    @Test
    public void button01Test() throws IOException, InterruptedException {
        runTest("buttonTest01");
    }

    @Test
    public void button02Test() throws IOException, InterruptedException {
        runTest("buttonTest02");
    }

    @Test
    public void flexWithButtonsTest() throws IOException, InterruptedException {
        runTest("flexWithButtons");
    }

    @Test
    public void buttonWithDisplayBlockTest() throws IOException, InterruptedException {
        runTest("buttonWithDisplayBlock");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INPUT_TYPE_IS_INVALID))
    public void inputDefaultTest01() throws IOException, InterruptedException {
        runTest("inputDefaultTest01");
    }

    @Test
    public void placeholderTest01() throws IOException, InterruptedException {
        runTest("placeholderTest01");
    }

    @Test
    public void placeholderTest02() throws IOException, InterruptedException {
        runTest("placeholderTest02");
    }

    @Test
    public void placeholderTest02A() throws IOException, InterruptedException {
        runTest("placeholderTest02A");
    }

    @Test
    public void placeholderTest03() throws IOException, InterruptedException {
        runTest("placeholderTest03");
    }

    @Test
    @LogMessages(ignore = true, messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.INPUT_TYPE_IS_NOT_SUPPORTED),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void placeholderTest04() throws IOException, InterruptedException {
        runTest("placeholderTest04");
    }

    @Test
    public void inputDisabled01AcroTest() throws IOException, InterruptedException {
        String htmlPath = sourceFolder + "inputDisabled01Test.html";
        String outPdfPath = destinationFolder + "inputDisabled01AcroTest.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + "inputDisabled01AcroTest.pdf";
        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdfPath), new ConverterProperties().setCreateAcroForm(true));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder));
    }

    @Test
    public void inputDisabled01Test() throws IOException, InterruptedException {
        runTest("inputDisabled01Test");
    }

    @Test
    public void placeholderTest05() throws IOException, InterruptedException {
        String htmlPath = sourceFolder + "placeholderTest05.html";
        String outPdfPath = destinationFolder + "placeholderTest05.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + "placeholderTest05.pdf";
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");

        List<IElement> elements = HtmlConverter.convertToElements(new FileInputStream(htmlPath));
        Paragraph placeholderToBeSet = new Paragraph("bazinga").setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.YELLOW);
        List<IElement> children = ((Paragraph) elements.get(0)).getChildren();
        for (IElement child : children) {
            if (child instanceof InputField) {
                ((InputField) child).setPlaceholder(placeholderToBeSet);
            }
        }
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outPdfPath));
        Document doc = new Document(pdfDoc);
        for (IElement child : elements) {
            if (child instanceof IBlockElement) {
                doc.add((IBlockElement) child);
            }
        }
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, "diff_placeholderTest05_"));
    }

    @Test
    public void placeholderTest05A() throws IOException, InterruptedException {
        runTest("placeholderTest05A");
    }

    @Test
    // TODO fix after DEVSIX-3461 is done
    public void checkboxTaggingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("checkboxTagging", sourceFolder, destinationFolder, true);
    }

    @Test
    public void checkboxInTableTest() throws IOException, InterruptedException {
        runTest("checkboxInTable");
    }

    @Test
    public void checkboxDisplayBlockInTableTest() throws IOException, InterruptedException {
        runTest("checkboxDisplayBlockInTable");
    }

    @Test
    public void checkboxFullWidthDisplayBlockInTableTest() throws IOException, InterruptedException {
        runTest("checkboxFullWidthDisplayBlockInTable");
    }

    @Test
    public void checkboxDiffWidthDisplayBlockInTableTest() throws IOException, InterruptedException {
        runTest("checkboxDiffWidthDisplayBlockInTable");
    }

    @Test
    public void checkboxTest() throws IOException, InterruptedException {
        runTest("checkbox");
    }

    @Test
    public void checkboxDisplayBlockTest() throws IOException, InterruptedException {
        runTest("checkboxDisplayBlock");
    }

    @Test
    public void checkboxFullWidthDisplayBlockTest() throws IOException, InterruptedException {
        runTest("checkboxFullWidthDisplayBlock");
    }

    @Test
    public void checkboxDiffWidthDisplayBlockTest() throws IOException, InterruptedException {
        runTest("checkboxDiffWidthDisplayBlock");
    }

    @Test
    @LogMessages(ignore = true, messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)
    })
    public void longInputValueCausesNothingTest() throws IOException, InterruptedException {
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setTagWorkerFactory(new CustomTextInputTagWorkerFactory());
        convertToPdfAndCompare("longInputValueCausesNothingTest", sourceFolder, destinationFolder,
                false, converterProperties);
    }

    @Test
    public void inputMinWidthTest() throws IOException, InterruptedException {
       runTest("inputMinWidth");
    }

    @Test
    public void inputPDFATest() throws IOException, InterruptedException {
        runPDFATest("inputpdfa");
    }

    @Test
    public void buttonPDFATest() throws IOException, InterruptedException {
        runPDFATest("buttonpdfa");
    }


    @Test
    public void inputPDFACheckbox() throws IOException, InterruptedException {
        runPDFATest("inputpdfcheckbox");
    }

    @Test
    public void inputPDFASelectTest() throws IOException, InterruptedException {
        runPDFATest("inputpdfaselect");
    }

    @Test
    public void inputPDFAradioTest() throws IOException, InterruptedException {
        runPDFATest("inputpdfradio");
    }

    private void runPDFATest(String name) throws IOException, InterruptedException {
        String sourceHtml = sourceFolder + name + ".html";
        String cmpPdf = sourceFolder + "cmp_" + name + ".pdf";
        String destinationPdf = destinationFolder + name + ".pdf";

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setPdfAConformanceLevel(PdfAConformanceLevel.PDF_A_4);
        converterProperties.setOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new FileOutputStream(destinationPdf), converterProperties);
        }

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(sourceHtml) + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, destinationFolder,
                "diff_" + name + "_"));

        VeraPdfValidator veraPdfValidator = new VeraPdfValidator();
        Assert.assertNull(veraPdfValidator.validate(destinationPdf));

    }

    private static class CustomTextInputTagWorkerFactory extends DefaultTagWorkerFactory {
        @Override
        public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
            switch (tag.name().toLowerCase()) {
                case "input":
                    switch (tag.getAttribute("type").toLowerCase()) {
                        case "text":
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("page-break-inside", "avoid");
                            tag.addAdditionalHtmlStyles(map);
                            return new CustomInputDivTagWorker(tag, context);
                    }
                    break;
            }
            return null;
        }
    }

    private static class CustomInputDivTagWorker extends DivTagWorker {
        public CustomInputDivTagWorker(IElementNode element, ProcessorContext context) {
            super(element, context);
            String value = element.getAttribute("value");
            processContent(value, context);
        }
    }


    private void runTest(String name) throws IOException, InterruptedException {
        convertToPdfAndCompare(name, sourceFolder, destinationFolder);
    }

    private static class PdfAFontProvider extends FontProvider {

    }
}
