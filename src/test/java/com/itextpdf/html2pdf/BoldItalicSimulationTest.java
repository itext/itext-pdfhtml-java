package com.itextpdf.html2pdf;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

@Category(IntegrationTest.class)
public class BoldItalicSimulationTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/BoldItalicSimulationTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/BoldItalicSimulationTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simulationBoldTest01() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "simulationTest01.pdf"));
        FontProvider dfp = new DefaultFontProvider(false, false, false);
        dfp.addFont(StandardFonts.HELVETICA);
        dfp.addFont(StandardFonts.HELVETICA_BOLD);
        ConverterProperties converterProp = new ConverterProperties().setFontProvider(dfp).enableBoldSimulation();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "simulationTest01.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProp);
        }
        Assert.assertFalse(converterProp.isBoldSimulationEnabled());
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simulationTest01.pdf", sourceFolder + "cmp_simulationTest01.pdf", destinationFolder, "diff01_"));
    }
    
    @Test
    public void simulationBoldTest02() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "simulationTest01.pdf"));
        FontProvider dfp = new DefaultFontProvider(false, false, false);
        dfp.addFont(StandardFonts.HELVETICA);
        dfp.addFont(StandardFonts.HELVETICA_BOLD);
        ConverterProperties converterProp = new ConverterProperties().enableBoldSimulation().setFontProvider(dfp);
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "simulationTest01.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProp);
        }
        Assert.assertFalse(converterProp.isBoldSimulationEnabled());
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simulationTest01.pdf", sourceFolder + "cmp_simulationTest01.pdf", destinationFolder, "diff01_"));
    }
    
    @Test
    public void simulationItalicTest03() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "simulationTest01.pdf"));
        FontProvider dfp = new DefaultFontProvider(false, false, false);
        dfp.addFont(StandardFonts.TIMES_ROMAN);
        dfp.addFont(StandardFonts.TIMES_ITALIC);
        ConverterProperties converterProp = new ConverterProperties().setFontProvider(dfp).enableItalicSimulation();
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "simulationTest01.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProp);
        }
        Assert.assertFalse(converterProp.isItalicSimulationEnabled());
        // TODO Prepare a simulationTest02.html to compareByContent considering italic simulation
        // Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simulationTest01.pdf", sourceFolder + "cmp_simulationTest01.pdf", destinationFolder, "diff01_"));
    }
    
    @Test
    public void simulationItalicTest04() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "simulationTest01.pdf"));
        FontProvider dfp = new DefaultFontProvider(false, false, false);
        dfp.addFont(StandardFonts.TIMES_ROMAN);
        dfp.addFont(StandardFonts.TIMES_ITALIC);
        ConverterProperties converterProp = new ConverterProperties().enableItalicSimulation().setFontProvider(dfp);
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "simulationTest01.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, converterProp);
        }
        Assert.assertFalse(converterProp.isItalicSimulationEnabled());
        // TODO Prepare a simulationTest02.html to compareByContent considering italic simulation
        // Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simulationTest01.pdf", sourceFolder + "cmp_simulationTest01.pdf", destinationFolder, "diff01_"));
    }

}
