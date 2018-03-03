package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
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
public class OptionTest extends ExtendedITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/OptionTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/OptionTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void optionBasicTest01() throws IOException, InterruptedException {
        runTest("optionBasicTest01");
    }

    @Test
    public void optionBasicTest02() throws IOException, InterruptedException {
        runTest("optionBasicTest02");
    }


    @Test
    public void optionEmptyTest01() throws IOException, InterruptedException {
        runTest("optionEmptyTest01");
    }

    @Test
    public void optionLabelValueTest01() throws IOException, InterruptedException {
        runTest("optionLabelValueTest01");
    }

    @Test
    public void optionStylesTest01() throws IOException, InterruptedException {
        runTest("optionStylesTest01");
    }

    @Test
    public void optionStylesTest02() throws IOException, InterruptedException {
        runTest("optionStylesTest02");
    }

    @Test
    public void optionHeightTest01() throws IOException, InterruptedException {
        runTest("optionHeightTest01");
    }

    @Test
    public void optionWidthTest01() throws IOException, InterruptedException {
        runTest("optionWidthTest01");
    }

    @Test
    public void optionOverflowTest01() throws IOException, InterruptedException {
        runTest("optionOverflowTest01");
    }

    @Test
    public void optionOverflowTest02() throws IOException, InterruptedException {
        runTest("optionOverflowTest02");
    }

    @Test
    public void optionPseudoTest01() throws IOException, InterruptedException {
        runTest("optionPseudoTest01");
    }

    @Test
    public void optionPseudoTest02() throws IOException, InterruptedException {
        runTest("optionPseudoTest02");
    }

    private void runTest(String name) throws IOException, InterruptedException {
        String htmlPath = sourceFolder + name + ".html";
        String outPdfPath = destinationFolder + name + ".pdf";
        String cmpPdfPath = sourceFolder + "cmp_" + name + ".pdf";
        String diff = "diff_" + name + "_";
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlPath).getPath() + "\n");

        HtmlConverter.convertToPdf(new File(htmlPath), new File(outPdfPath));
        Assert.assertNull(new CompareTool().compareByContent(outPdfPath, cmpPdfPath, destinationFolder, diff));
    }
}
