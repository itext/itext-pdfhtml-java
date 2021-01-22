package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class FigureTest extends ExtendedITextTest {
    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/FigureTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/FigureTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void figureFileDocumentTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hello_figure_file.html"), new File(destinationFolder + "hello_figure_file.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hello_figure_file.pdf", sourceFolder + "cmp_hello_figure_file.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void smallFigureTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "smallFigureTest.html"), new File(destinationFolder + "smallFigureTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "smallFigureTest.pdf", sourceFolder + "cmp_smallFigureTest.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void figureInSpanTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "figureInSpan.html"), new File(destinationFolder + "figureInSpan.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "figureInSpan.pdf", sourceFolder + "cmp_figureInSpan.pdf", destinationFolder, "diff04_"));
    }
}
