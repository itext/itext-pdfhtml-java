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

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FormTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/FormTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/FormTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simpleTextFieldTest() throws IOException, InterruptedException {
        runTest("simpleTextField");
    }

    @Test
    public void splitTextFieldTest() throws IOException, InterruptedException {
        runTest("splitTextField");
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.INLINE_BLOCK_ELEMENT_WILL_BE_CLIPPED, count = 2),
            @LogMessage(messageTemplate = LogMessageConstant.INPUT_FIELD_DOES_NOT_FIT, count = 2)})
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
    @Ignore("DEVSIX-1316")
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
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 2),
    })
    public void buttonSplit02Test() throws IOException, InterruptedException {
        runTest("buttonSplit02");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 2),
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
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.ACROFORM_NOT_SUPPORTED_FOR_SELECT, count = 2))
    public void selectTest01() throws IOException, InterruptedException {
        runTest("select01", false);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.ACROFORM_NOT_SUPPORTED_FOR_SELECT, count = 3))
    public void selectTest02() throws IOException, InterruptedException {
        runTest("select02", false);
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
        HtmlConverter.convertToPdf(new File(htmlPath), new File(outAcroPdfPath), new ConverterProperties().setCreateAcroForm(true));
        if (flattenPdfAcroFormFields) {
            PdfDocument document = new PdfDocument(new PdfReader(outAcroPdfPath), new PdfWriter(outAcroFlattenPdfPath));
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(document, false);
            acroForm.flattenFields();
            document.close();
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff));
        Assert.assertNull(new CompareTool().compareByContent(outAcroPdfPath, cmpAcroPdfPath, destinationFolder, diff));
        if (flattenPdfAcroFormFields) {
            Assert.assertNull(new CompareTool().compareByContent(outAcroFlattenPdfPath, cmpAcroFlattenPdfPath, destinationFolder, diff));
        }
    }
}
