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

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormCreator;
import com.itextpdf.forms.logs.FormsLogMessageConstants;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class FormTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/FormTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/FormTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simpleTextFieldTest() throws IOException, InterruptedException {
        runTest("simpleTextField");
    }

    @Test
    public void textFieldWithPlaceholderTest() throws IOException, InterruptedException {
        runTest("textFieldWithPlaceholder");
    }

    @Test
    public void textFieldHeadersFootersTest() throws IOException, InterruptedException {
        runTest("textFieldHeadersFooters");
    }

    @Test
    public void textFieldHeadersFootersWithValueTest() throws IOException, InterruptedException {
        runTest("textFieldHeadersFootersWithValue");
    }

    @Test
    public void textAreaHeadersFootersWithValueTest() throws IOException, InterruptedException {
        runTest("textAreaHeadersFooters");
    }

    @Test
    public void checkBoxHeadersFootersWithValueTest() throws IOException, InterruptedException {
        runTest("checkBoxHeadersFooters");
    }

    @Test
    public void runningFormFieldsInHeader() throws IOException, InterruptedException {
        runTest("runningFormFieldsInHeader");
    }

    @Test
    public void radioHeadersFootersWithValueTest() throws IOException, InterruptedException {
        runTest("radioHeadersFooters");
    }


    @Test
    public void dropDownHeadersFootersWithValueTest() throws IOException, InterruptedException {
        runTest("dropDownHeadersFooters");
    }


    @Test
    public void splitTextFieldTest() throws IOException, InterruptedException {
        runTest("splitTextField");
    }

    @Test
    public void forcedSplitTextFieldTest() throws IOException, InterruptedException {
        runTest("forcedSplitTextField");
    }

    @Test
    public void textFieldHeight1Test() throws IOException, InterruptedException {
        runTest("textFieldHeight1");
    }

    @Test
    public void textFieldHeight2Test() throws IOException, InterruptedException {
        runTest("textFieldHeight2");
    }

    @Test
    public void textFieldHeight3Test() throws IOException, InterruptedException {
        runTest("textFieldHeight3");
    }

    @Test
    public void simpleButtonTest() throws IOException, InterruptedException {
        runTest("simpleButton");
    }

    @Test
    public void fieldsetTest() throws IOException, InterruptedException {
        runTest("fieldset");
    }

    @Test
    public void fieldsetLegendTest() throws IOException, InterruptedException {
        runTest("fieldsetLegend");
    }

    @Test
    public void labelTest() throws IOException, InterruptedException {
        runTest("label");
    }

    @Test
    @Disabled("DEVSIX-1316")
    public void fieldInTablePercent() throws IOException, InterruptedException {
        runTest("fieldInTablePercent");
    }

    @Test
    public void inputDisplayTest() throws IOException, InterruptedException {
        runTest("inputDisplay");
    }

    @Test
    public void textareaDisplayTest() throws IOException, InterruptedException {
        runTest("textareaDisplay");
    }

    @Test
    public void colsAttributeInTextareaTest() throws IOException, InterruptedException {
        runTest("colsTextArea01");
    }

    @Test
    public void colsAttributeWithBigValueInTextareaTest() throws IOException, InterruptedException {
        runTest("colsTextArea02");
    }

    @Test
    public void colsAttributeWithSmallValueInTextareaTest() throws IOException, InterruptedException {
        runTest("colsTextArea03");
    }

    @Test
    public void checkbox1Test() throws IOException, InterruptedException {
        runTest("checkbox1");
    }

    @Test
    public void buttonWithChildrenTest() throws IOException, InterruptedException {
        runTest("buttonWithChildren");
    }

    @Test
    public void buttonSplit01Test() throws IOException, InterruptedException {
        runTest("buttonSplit01");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 4),
            @LogMessage(messageTemplate = FormsLogMessageConstants.INPUT_FIELD_DOES_NOT_FIT, count = 2)
    })
    public void buttonSplit02Test() throws IOException, InterruptedException {
        runTest("buttonSplit02");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 2),
            @LogMessage(messageTemplate = FormsLogMessageConstants.INPUT_FIELD_DOES_NOT_FIT, count = 2)
    })
    public void buttonSplit03Test() throws IOException, InterruptedException {
        runTest("buttonSplit03");
    }

    @Test
    public void radiobox1Test() throws IOException, InterruptedException {
        runTest("radiobox1");
    }

    @Test
    public void radiobox2Test() throws IOException, InterruptedException {
        runTest("radiobox2");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.MULTIPLE_VALUES_ON_A_NON_MULTISELECT_FIELD)})
    public void selectTest01() throws IOException, InterruptedException {
        runTest("select01", true);
    }

    @Test
    public void selectTest02() throws IOException, InterruptedException {
        runTest("select02", true);
    }

    @Test
    //TODO: update cmpfile after DEVSIX-4772 will be fixed
    public void radioButtonWithPageCounterAtBottonTest() throws IOException, InterruptedException {
        String html = sourceFolder + "radioButtonWithPageCounterAtBotton.html";
        String pdf = destinationFolder + "radioButtonWithPageCounterAtBotton.pdf";

        HtmlConverter.convertToPdf(new File(html), new File(pdf), new ConverterProperties().setCreateAcroForm(true));

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(html) + "\n");

        Assertions.assertNull(new CompareTool().compareByContent(
                pdf, sourceFolder + "cmp_radioButtonWithPageCounterAtBotton.pdf", destinationFolder));
    }

    @Test
    //TODO: update cmpfile after DEVSIX-4772 will be fixed
    public void radioButtonWithPageCounterOnTopTest() throws IOException, InterruptedException {
        String html = sourceFolder + "radioButtonWithPageCounterOnTop.html";
        String pdf = destinationFolder + "radioButtonWithPageCounterOnTop.pdf";

        HtmlConverter.convertToPdf(new File(html), new File(pdf), new ConverterProperties().setCreateAcroForm(true));

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(html) + "\n");

        Assertions.assertNull(new CompareTool().compareByContent(
                pdf, sourceFolder + "cmp_radioButtonWithPageCounterOnTop.pdf", destinationFolder));
    }

    @Test
    public void radioButtonNoPageCounterTest() throws IOException, InterruptedException {
        String html = sourceFolder + "radioButtonNoPageCounter.html";
        String pdf = destinationFolder + "radioButtonNoPageCounter.pdf";

        HtmlConverter.convertToPdf(new File(html), new File(pdf), new ConverterProperties().setCreateAcroForm(true));

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(html) + "\n");

        Assertions.assertNull(new CompareTool().compareByContent(
                pdf, sourceFolder + "cmp_radioButtonNoPageCounter.pdf", destinationFolder));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.IMMEDIATE_FLUSH_DISABLED, logLevel =
                    LogLevelConstants.INFO)})
    public void checkLogInfo() throws IOException {
        String html = sourceFolder + "radiobox1.html";
        try (FileInputStream fileInputStream = new FileInputStream(html);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(fileInputStream, baos,
                    new ConverterProperties().setCreateAcroForm(true));
        }
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.IMMEDIATE_FLUSH_DISABLED, logLevel =
                    LogLevelConstants.INFO, count = 0)})
    public void checkLogInfoNoAcroForm() throws IOException {
        String html = sourceFolder + "radiobox1.html";
        try (FileInputStream fileInputStream = new FileInputStream(html);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(fileInputStream, baos,
                    new ConverterProperties().setCreateAcroForm(false));
        }
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.IMMEDIATE_FLUSH_DISABLED, logLevel =
                    LogLevelConstants.INFO, count = 0)})
    public void checkLogInfoAcroFormFlushDisabled() throws IOException {
        String html = sourceFolder + "radiobox1.html";
        try (FileInputStream fileInputStream = new FileInputStream(html);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(fileInputStream, baos,
                    new ConverterProperties().setCreateAcroForm(true).setImmediateFlush(false));
        }
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.IMMEDIATE_FLUSH_DISABLED, logLevel =
                    LogLevelConstants.INFO, count = 0)})
    public void checkLogInfoDefault() throws IOException {
        String html = sourceFolder + "radiobox1.html";
        try (FileInputStream fileInputStream = new FileInputStream(html);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(fileInputStream, baos);
        }
    }

    private void runTest(String name) throws IOException, InterruptedException {
        runTest(name, true);
    }

    private void runTest(String name, boolean flattenPdfAcroFormFields) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String outPdfPath = destinationFolder + name + ".pdf";
        String outAcroPdfPath = destinationFolder + name + "_acro.pdf";
        String outAcroFlattenPdfPath = destinationFolder + name + "_acro_flatten.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String cmpAcroPdfPath = sourceFolder + "cmp_" + name + "_acro.pdf";
        String cmpAcroFlattenPdfPath = sourceFolder + "cmp_" + name + "_acro_flatten.pdf";
        String diff = "diff_" + name + "_";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdfPath));
        HtmlConverter.convertToPdf(new File(htmlPath), new File(outAcroPdfPath),
                new ConverterProperties().setCreateAcroForm(true));
        if (flattenPdfAcroFormFields) {
            PdfDocument document = new PdfDocument(new PdfReader(outAcroPdfPath), new PdfWriter(outAcroFlattenPdfPath));
            PdfAcroForm acroForm = PdfFormCreator.getAcroForm(document, false);
            acroForm.flattenFields();
            document.close();
        }

        Assertions.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff));
        Assertions.assertNull(new CompareTool().compareByContent(outAcroPdfPath, cmpAcroPdfPath, destinationFolder, diff));
        if (flattenPdfAcroFormFields) {
            Assertions.assertNull(
                    new CompareTool().compareByContent(outAcroFlattenPdfPath, cmpAcroFlattenPdfPath, destinationFolder,
                            diff));
        }
    }
}
