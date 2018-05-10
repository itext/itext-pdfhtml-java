package com.itextpdf.html2pdf;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.test.ExtendedITextTest;
import org.junit.Assert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * This class is used for testing of pdfHTML conversion cases
 * extends ExtendedITextTest test class
 */
public abstract class ExtendedHtmlConversionITextTest extends ExtendedITextTest {

    public void convertToPdfAndCompare(String name, String sourceFolder, String destinationFolder) throws IOException, InterruptedException {
        convertToPdfAndCompare(name, sourceFolder, destinationFolder, false, sourceFolder);
    }

    public void convertToPdfAndCompare(String name, String sourceFolder, String destinationFolder, boolean tagged) throws IOException, InterruptedException {
        convertToPdfAndCompare(name, sourceFolder, destinationFolder, tagged, sourceFolder);
    }

    public void convertToPdfAndCompare(String name, String sourceFolder, String destinationFolder, boolean tagged, String fontsFolder) throws IOException, InterruptedException {

        String sourceHtml = sourceFolder + name + ".html";
        String cmpPdf = sourceFolder + "cmp_" + name + ".pdf";
        String destinationPdf = destinationFolder + name + ".pdf";

        ConverterProperties converterProperties = getConverterProperties(fontsFolder);
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf));
        if (tagged) {
            pdfDocument.setTagged();
        }
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProperties);
        }
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceHtml).getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, destinationFolder, "diff_" + name + "_"));
    }

    public void convertToElementsAndCompare(String name, String sourceFolder, String destinationFolder) throws IOException, InterruptedException {
        convertToElementsAndCompare(name, sourceFolder, destinationFolder, false, sourceFolder);
    }

    public void convertToElementsAndCompare(String name, String sourceFolder, String destinationFolder, boolean tagged) throws IOException, InterruptedException {
        convertToElementsAndCompare(name, sourceFolder, destinationFolder, tagged, sourceFolder);
    }

    public void convertToElementsAndCompare(String name, String sourceFolder, String destinationFolder, boolean tagged, String fontsFolder) throws IOException, InterruptedException {

        String sourceHtml = sourceFolder + name + ".html";
        String cmpPdf = sourceFolder + "cmp_" + name + ".pdf";
        String destinationPdf = destinationFolder + name + ".pdf";

        ConverterProperties converterProperties = getConverterProperties(fontsFolder);
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationPdf));
        if (tagged) {
            pdfDocument.setTagged();
        }
        Document document = new Document(pdfDocument);
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            for (IElement element : HtmlConverter.convertToElements(fileInputStream, converterProperties)) {
                document.add((IBlockElement) element);
            }
        }
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceHtml).getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(destinationPdf, cmpPdf, destinationFolder, "diff_" + name + "_"));
    }

    public void convertToPdfAcroformFlattenAndCompare(String name, String sourceFolder, String destinationFolder, boolean tagged) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        String sourceHtml = sourceFolder + name + ".html";
        if (tagged) {
            name = name + "Tagged";
        }
        String outPdfPath = destinationFolder + name + ".pdf";
        String outPdfPathAcro = destinationFolder + name + "_acro.pdf";
        String outPdfPathFlatted = destinationFolder + name + "_acro_flatten.pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String cmpPdfPathAcro = sourceFolder + "cmp_" + name + "_acro.pdf";
        String cmpPdfPathAcroFlatten = sourceFolder + "cmp_" + name + "_acro_flatten.pdf";
        String diff1 = "diff1_" + name;
        String diff2 = "diff2_" + name;
        String diff3 = "diff3_" + name;

        //convert tagged PDF without acroform (from html with form elements)
        PdfWriter taggedWriter = new PdfWriter(outPdfPath);
        PdfDocument pdfTagged = new PdfDocument(taggedWriter);
        if (tagged) {
            pdfTagged.setTagged();
        }
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfTagged, new ConverterProperties().setBaseUri(sourceFolder));

        //convert PDF with acroform
        PdfWriter writerAcro = new PdfWriter(outPdfPathAcro);
        PdfDocument pdfTaggedAcro = new PdfDocument(writerAcro);
        if (tagged) {
            pdfTaggedAcro.setTagged();
        }
        ConverterProperties converterPropertiesAcro = new ConverterProperties();
        converterPropertiesAcro.setBaseUri(sourceFolder);
        converterPropertiesAcro.setCreateAcroForm(true);
        HtmlConverter.convertToPdf(new FileInputStream(sourceHtml), pdfTaggedAcro, converterPropertiesAcro);
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(sourceHtml).getPath() + "\n");

        //flatted created tagged PDF with acroform
        PdfDocument document = new PdfDocument(new PdfReader(outPdfPathAcro), new PdfWriter(outPdfPathFlatted));
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(document, false);
        acroForm.flattenFields();
        document.close();

        //compare with cmp
        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff1));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPathAcro, cmpPdfPathAcro, destinationFolder, diff2));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPathFlatted, cmpPdfPathAcroFlatten, destinationFolder, diff3));

        //compare tags structure if tagged
        if (tagged) {
            compareTagStructure(outPdfPath, cmpPdfPath);
            compareTagStructure(outPdfPathAcro, cmpPdfPathAcro);
            compareTagStructure(outPdfPathFlatted, cmpPdfPathAcroFlatten);
        }
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

    private ConverterProperties getConverterProperties(String fontsFolder) {
        ConverterProperties properties = new ConverterProperties().setBaseUri(fontsFolder);
        return properties;
    }
}
