package com.itextpdf.html2pdf.css.selector.item;

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
public class NonStandardNodesMatchingTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/selector/item/NonStandardNodesMatchingTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/selector/item/NonStandardNodesMatchingTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void pseudoElementMatchingTest01() throws IOException, InterruptedException {
        runTest("pseudoElementMatchingTest01", "diffPseudo01_");
    }

    @Test
    public void pseudoElementMatchingTest02() throws IOException, InterruptedException {
        runTest("pseudoElementMatchingTest02", "diffPseudo02_");
    }

    @Test
    public void documentNodeMatchingTest01() throws IOException, InterruptedException {
        runTest("documentNodeMatchingTest01", "diffDocument01_");
    }

    private void runTest(String testName, String diff) throws IOException, InterruptedException {
        String htmlName = sourceFolder + testName + ".html";
        String outFileName = destinationFolder + testName + ".pdf";
        String cmpFileName = sourceFolder + "cmp_" + testName + ".pdf";
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlName).getPath() + "\n");
        HtmlConverter.convertToPdf(new File(htmlName), new File(outFileName));
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, diff));
    }
}
