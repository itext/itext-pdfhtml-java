/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
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
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.FileInputStream;
import java.io.IOException;

@Category(IntegrationTest.class)
public class TaggedPdfFormTest extends ExtendedITextTest {


    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TaggedPdfFormTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TaggedPdfFormTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simpleTextFieldTagged() throws IOException, InterruptedException {
        runTest("simpleTextFieldTagged");
    }

    @Test
    public void simpleTextareaTagged() throws IOException, InterruptedException {
        runTest("simpleTextareaTagged");
    }

    @Test
    public void simpleButtonTagged() throws IOException, InterruptedException {
        runTest("simpleButtonTagged");
    }

    @Test
    public void simpleLabelTagged() throws IOException, InterruptedException {
        runTest("simpleLabelTagged");
    }

    @Test
    public void simpleCheckboxTagged() throws IOException, InterruptedException {
        runTest("simpleCheckboxTagged");
    }

    @Test
    @Ignore("DEVSIX-1901")
    public void simpleSelectTagged() throws IOException, InterruptedException {
        runTest("simpleSelectTagged");
    }

    @Test
    @Ignore("DEVSIX-1901")
    public void listBoxSelectTagged() throws IOException, InterruptedException {
        runTest("listBoxSelectTagged");
    }

    @Test
    @Ignore("DEVSIX-1901")
    public void listBoxOptGroupSelectTagged() throws IOException, InterruptedException {
        runTest("listBoxOptGroupSelectTagged");
    }

    @Test
    @Ignore("DEVSIX-1901")
    public void simpleRadioFormTagged() throws IOException, InterruptedException {
        runTest("simpleRadioFormTagged");
    }

    @Test
    @Ignore("DefaultHtmlProcessor ERROR No worker found for tag datalist")
    public void datalistFormTagged() throws IOException, InterruptedException {
        runTest("datalistFormTagged");
    }

    @Test
    public void fieldsetFormTagged() throws IOException, InterruptedException {
        runTest("fieldsetFormTagged");
    }

    private void runTest(String name) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String outTaggedPdfPath = destinationFolder + name + ".pdf";
        String outTaggedPdfPathAcro = destinationFolder + name + "_acro.pdf";
        String outTaggedPdfPathFlatted = destinationFolder + name + "_acro_flatten.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String cmpPdfPathAcro = sourceFolder + "cmp_" + name + "_acro.pdf";
        String cmpPdfPathAcroFlatten = sourceFolder + "cmp_" + name + "_acro_flatten.pdf";
        String diff1 = "diff1_" + name;
        String diff2 = "diff2_" + name;
        String diff3 = "diff3_" + name;

        //convert tagged PDF without acroform (from html with form elements)
        PdfWriter taggedWriter = new PdfWriter(outTaggedPdfPath);
        PdfDocument pdfTagged = new PdfDocument(taggedWriter);
        pdfTagged.setTagged();
        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), pdfTagged);

        //convert tagged PDF with acroform
        PdfWriter taggedWriterAcro = new PdfWriter(outTaggedPdfPathAcro);
        PdfDocument pdfTaggedAcro = new PdfDocument(taggedWriterAcro);
        pdfTaggedAcro.setTagged();
        ConverterProperties converterPropertiesAcro = new ConverterProperties();
        converterPropertiesAcro.setCreateAcroForm(true);
        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), pdfTaggedAcro, converterPropertiesAcro);

        //flatted created tagged PDF with acroform
        PdfDocument document = new PdfDocument(new PdfReader(outTaggedPdfPathAcro), new PdfWriter(outTaggedPdfPathFlatted));
        PdfAcroForm.getAcroForm(document, false).flattenFields();
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outTaggedPdfPath, cmpPdfPath, destinationFolder, diff1));
        Assert.assertNull(new CompareTool().compareByContent(outTaggedPdfPathAcro, cmpPdfPathAcro, destinationFolder, diff2));
        Assert.assertNull(new CompareTool().compareByContent(outTaggedPdfPathFlatted, cmpPdfPathAcroFlatten, destinationFolder, diff3));
    }
}
