package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.FileInputStream;
import java.io.IOException;

@Category(IntegrationTest.class)
public class BoldItalicSimulationTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/BoldItalicSimulationTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/BoldItalicSimulationTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simulationTest01() throws IOException, InterruptedException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + "simulationTest01.pdf"));
        FontProvider dfp = new DefaultFontProvider(false, false, false);
        dfp.addFont(StandardFonts.HELVETICA);
        dfp.addFont(StandardFonts.HELVETICA_BOLD);
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "simulationTest01.html")) {
            HtmlConverter.convertToPdf(fileInputStream, pdfDocument, new ConverterProperties().setFontProvider(dfp).setBoldSimulation(true));
        }
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "simulationTest01.pdf", sourceFolder + "cmp_simulationTest01.pdf", destinationFolder, "diff01_"));
    }
}
