package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Category(IntegrationTest.class)
public class FontSelectorPerformanceTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FontSelectorPerformanceTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FontSelectorPerformanceTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    @Ignore("Each machine has different set of fonts")
    public void performanceTest01() throws IOException, InterruptedException {
        String name = "performanceTest01";

        String htmlPath = sourceFolder + name + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";

        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlPath).getPath() + "\n");

        ConverterProperties converterProperties = new ConverterProperties()
                .setFontProvider(new BasicFontProvider(true, true));

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }

    @Test
    @Ignore("Each machine has different set of fonts")
    public void performanceTest02() throws IOException, InterruptedException {
        String name = "performanceTest02";

        String htmlPath = sourceFolder + "performanceTest01" + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + "performanceTest01" + ".pdf";
        String diffPrefix = "diff_" + name + "_";

        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlPath).getPath() + "\n");

        ConverterProperties converterProperties = new ConverterProperties()
                .setFontProvider(new BasicFontProvider(true, true));

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);
        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);

        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }

    @Test
    public void performanceTest03() throws IOException, InterruptedException {
        String name = "performanceTest03";

        String htmlPath = sourceFolder + name + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";

        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlPath).getPath() + "\n");

        ConverterProperties converterProperties = new ConverterProperties()
                .setFontProvider(new BasicFontProvider(true, true));

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath), converterProperties);

        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }

}
