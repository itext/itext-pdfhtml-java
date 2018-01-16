package com.itextpdf.html2pdf.attach.impl.util;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class WhiteSpaceCollapsingAndTrimmingTest extends ExtendedITextTest {
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attacher/impl/WhiteSpaceCollapsingAndTrimmingTest/";
    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attacher/impl/WhiteSpaceCollapsingAndTrimmingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void emptyElementsTest01() throws IOException, InterruptedException {
        runTest("emptyElementsTest01");
    }

    @Test
    public void emptyElementsTest02() throws IOException, InterruptedException {
        runTest("emptyElementsTest02");
    }

    @Test
    public void emptyElementsTest03() throws IOException, InterruptedException {
        runTest("emptyElementsTest03");
    }

    @Test
    public void floatingInlineBlockInsideLinkTest01() throws IOException, InterruptedException {
        runTest("floatingInlineBlockInsideLinkTest01");
    }

    private void runTest(String name) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String pdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath));
        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, destinationFolder, diffPrefix));
    }
}
