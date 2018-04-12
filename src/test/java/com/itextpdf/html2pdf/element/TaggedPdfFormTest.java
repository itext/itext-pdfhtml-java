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
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(document, false);
        acroForm.flattenFields();
        document.close();

        //compare with cmp
        String compResult1 = new CompareTool().compareByContent(outTaggedPdfPath, cmpPdfPath, destinationFolder, diff1);
        String compResult2 = new CompareTool().compareByContent(outTaggedPdfPathAcro, cmpPdfPathAcro, destinationFolder, diff2);
        String compResult3 = new CompareTool().compareByContent(outTaggedPdfPathFlatted, cmpPdfPathAcroFlatten, destinationFolder, diff3);

        Assert.assertNull(compResult1);
        Assert.assertNull(compResult2);
        Assert.assertNull(compResult3);
    }
}
