package com.itextpdf.html2pdf.css;

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
public class CssRootSelectorTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssRootSelectorTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssRootSelectorTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void rootSelectorTest01() throws IOException, InterruptedException {
        runTest("rootSelectorTest01", "diffRoot01_");
    }

    @Test
    public void rootAndNotRootTest() throws IOException, InterruptedException {
        runTest("rootAndNotRootTest", "diffRoot02_");
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
