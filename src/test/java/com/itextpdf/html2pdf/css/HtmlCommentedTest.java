package com.itextpdf.html2pdf.css;

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
public class HtmlCommentedTest extends ExtendedITextTest{

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/HtmlCommentedTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/HtmlCommentedTest/";

    @BeforeClass
    public static void initDestinationFolder() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void commentedHtmlToPdf() throws IOException, InterruptedException {

        String htmlSource = sourceFolder+"commentedHtmlToPdf.html";
        String outPdf = destinationFolder+"commentedHtmlToPdf.pdf";

        String cmpPdf = sourceFolder+"cmp_commentedHtmlToPdf.pdf";
        File htmlInput = new File(htmlSource);
        File pdfOutput = new File(outPdf);

        HtmlConverter.convertToPdf(htmlInput, pdfOutput);

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff_"));
    }
}
