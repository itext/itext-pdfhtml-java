package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FontSelectorArialFontTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FontSelectorArialFontTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FontSelectorArialFontTest/";

    public static final String SOURCE_HTML_NAME = "arialTest";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
        createDestinationFolder(sourceFolder);
    }

    @Test
    public void testArial() throws IOException, InterruptedException {
        String fileName = "testArial";
        ConverterProperties converterProperties = new ConverterProperties()
                .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT))
                .setFontProvider(new DefaultFontProvider());
        runTest(fileName, converterProperties);
    }

    @Test
    public void testArialWithHelveticaAsAnAlias() throws IOException, InterruptedException {
        String fileName = "testArialWithHelveticaAsAnAlias";
        FontProvider fontProvider = new DefaultFontProvider();
        fontProvider.getFontSet().addFont(sourceFolder + "FreeSans.ttf", null, "Arial");
        ConverterProperties converterProperties = new ConverterProperties()
                .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT))
                .setFontProvider(fontProvider);
        runTest(fileName, converterProperties);
    }

    private void runTest(String name, ConverterProperties converterProperties) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + SOURCE_HTML_NAME + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }
}
