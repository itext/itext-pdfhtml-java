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
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
public class TagsInsideButtonTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TagsInsideButtonTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TagsInsideButtonTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void buttonWithImageInside() throws IOException, InterruptedException {
        runConversionAcroformAndFlatten("buttonWithImageInside");
    }

    @Test
    public void buttonWithImageInsideTagged() throws IOException, InterruptedException, ParserConfigurationException, SAXException {

        runTaggedConversionAcroformAndFlatten("buttonWithImageInside");
    }

    @Test
    public void buttonWithPInside() throws IOException, InterruptedException {
        runConversionAcroformAndFlatten("buttonWithPInside");
    }

    @Test
    public void buttonWithPInsideTagged() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        runTaggedConversionAcroformAndFlatten("buttonWithPInside");
    }

    private void runConversionAcroformAndFlatten(String name) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String outPdfPath = destinationFolder + name + ".pdf";
        String outAcroPdfPath = destinationFolder + name + "_acro.pdf";
        String outAcroFlattenPdfPath = destinationFolder + name + "_acro_flatten.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String cmpAcroPdfPath = sourceFolder + "cmp_" + name + "_acro.pdf";
        String cmpAcroFlattenPdfPath = sourceFolder + "cmp_" + name + "_acro_flatten.pdf";
        String diff = "diff_" + name + "_";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdfPath), new ConverterProperties().setBaseUri(sourceFolder));
        HtmlConverter.convertToPdf(new File(htmlPath), new File(outAcroPdfPath), new ConverterProperties().setCreateAcroForm(true).setBaseUri(sourceFolder));
        PdfDocument document = new PdfDocument(new PdfReader(outAcroPdfPath), new PdfWriter(outAcroFlattenPdfPath));
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(document, false);
        acroForm.flattenFields();
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff));
        Assert.assertNull(new CompareTool().compareByContent(outAcroPdfPath, cmpAcroPdfPath, destinationFolder, diff));
        Assert.assertNull(new CompareTool().compareByContent(outAcroFlattenPdfPath, cmpAcroFlattenPdfPath, destinationFolder, diff));
    }

    private void runTaggedConversionAcroformAndFlatten(String name) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        String htmlPath = sourceFolder + name + ".html";
        name = name+"Tagged";
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
        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), pdfTagged, new ConverterProperties().setBaseUri(sourceFolder));

        //convert tagged PDF with acroform
        PdfWriter taggedWriterAcro = new PdfWriter(outTaggedPdfPathAcro);
        PdfDocument pdfTaggedAcro = new PdfDocument(taggedWriterAcro);
        pdfTaggedAcro.setTagged();
        ConverterProperties converterPropertiesAcro = new ConverterProperties();
        converterPropertiesAcro.setBaseUri(sourceFolder);
        converterPropertiesAcro.setCreateAcroForm(true);
        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), pdfTaggedAcro, converterPropertiesAcro);

        //flatted created tagged PDF with acroform
        PdfDocument document = new PdfDocument(new PdfReader(outTaggedPdfPathAcro), new PdfWriter(outTaggedPdfPathFlatted));
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(document, false);
        acroForm.flattenFields();
        document.close();

        //compare with cmp
        Assert.assertNull(new CompareTool().compareByContent(outTaggedPdfPath, cmpPdfPath, destinationFolder, diff1));
        Assert.assertNull(new CompareTool().compareByContent(outTaggedPdfPathAcro, cmpPdfPathAcro, destinationFolder, diff2));
        Assert.assertNull(new CompareTool().compareByContent(outTaggedPdfPathFlatted, cmpPdfPathAcroFlatten, destinationFolder, diff3));

        //compare tags structure
        compareTagStructure(outTaggedPdfPath, cmpPdfPath);
        compareTagStructure(outTaggedPdfPathAcro, cmpPdfPathAcro);
        compareTagStructure(outTaggedPdfPathFlatted, cmpPdfPathAcroFlatten);
    }

    private void compareTagStructure(String outPath, String cmpPath) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        CompareTool compareTool = new CompareTool();
        String tagStructureErrors = compareTool.compareTagStructures(outPath, cmpPath);
        String resultMessage = "";
        if (tagStructureErrors != null) {
            resultMessage += tagStructureErrors + "\n";
        }
        assertTrue(resultMessage, tagStructureErrors == null);
    }
}
