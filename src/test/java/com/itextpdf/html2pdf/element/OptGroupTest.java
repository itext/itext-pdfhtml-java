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
public class OptGroupTest extends ExtendedITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/OptGroupTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/OptGroupTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void optGroupBasicTest01() throws IOException, InterruptedException {
        runTest("optGroupBasicTest01");
    }

    @Test
    public void optGroupBasicTest02() throws IOException, InterruptedException {
        runTest("optGroupBasicTest02");
    }

    @Test
    public void optGroupEmptyTest01() throws IOException, InterruptedException {
        runTest("optGroupEmptyTest01");
    }

    @Test
    public void optGroupNestedTest01() throws IOException, InterruptedException {
        runTest("optGroupNestedTest01");
    }

    @Test
    public void optGroupNestedTest02() throws IOException, InterruptedException {
        runTest("optGroupNestedTest02");
    }

    @Test
    public void optGroupNoSelectTest01() throws IOException, InterruptedException {
        runTest("optGroupNoSelectTest01");
    }

    @Test
    public void optGroupStylesTest01() throws IOException, InterruptedException {
        runTest("optGroupStylesTest01");
    }

    @Test
    public void optGroupHeightTest01() throws IOException, InterruptedException {
        runTest("optGroupHeightTest01");
    }

    @Test
    public void optGroupWidthTest01() throws IOException, InterruptedException {
        // TODO DEVSIX-1896 Support "nowrap" value of "white-space" css property value
        runTest("optGroupWidthTest01");
    }

    @Test
    public void optGroupOverflowTest01() throws IOException, InterruptedException {
        runTest("optGroupOverflowTest01");
    }

    @Test
    public void optGroupOverflowTest02() throws IOException, InterruptedException {
        // TODO DEVSIX-1896 Support "nowrap" value of "white-space" css property value
        runTest("optGroupOverflowTest02");
    }

    @Test
    public void optGroupPseudoTest01() throws IOException, InterruptedException {
        runTest("optGroupPseudoTest01");
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
