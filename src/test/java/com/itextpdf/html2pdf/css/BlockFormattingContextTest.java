package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BlockFormattingContextTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BlockFormattingContextTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BlockFormattingContextTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void bfcOwnerFloat_floatsAndClear() throws IOException, InterruptedException {
        runTest("bfcOwnerFloat_floatsAndClear", "diff_");
    }

    @Test
    public void bfcOwnerFloat_marginsCollapse() throws IOException, InterruptedException {
        runTest("bfcOwnerFloat_marginsCollapse", "diff_");
    }

    @Test
    public void bfcOwnerAbsolute_floatsAndClear() throws IOException, InterruptedException {
        // Positioning and handling floats and clearance is exactly correct,
        // however TODO absolutely positioned elements shall be drawn on the same z-level as floats.
        runTest("bfcOwnerAbsolute_floatsAndClear", "diff_");
    }

    @Test
    public void bfcOwnerAbsolute_marginsCollapse() throws IOException, InterruptedException {
        // Margins don't collapse here which is correct,
        // however absolute positioning works a bit wrong from css point of view.
        runTest("bfcOwnerAbsolute_marginsCollapse", "diff_");
    }

    @Test
    public void bfcOwnerInlineBlock_floatsAndClear() throws IOException, InterruptedException {
        runTest("bfcOwnerInlineBlock_floatsAndClear", "diff_");
    }

    @Test
    public void bfcOwnerInlineBlock_marginsCollapse() throws IOException, InterruptedException {
        runTest("bfcOwnerInlineBlock_marginsCollapse", "diff_");
    }

    @Test
    public void bfcOwnerOverflowHidden_floatsAndClear() throws IOException, InterruptedException {
        // TODO overflow:hidden with display:block behaves curiously: it completely moves away from float horizontally.
        // We don't handle it in such way and it's unclear right now, based on what it behaves like this.
        // How would it behave if it would have 100% width or width:auto?
        //
        // Now, we basically working incorrectly, since overflow:hidden requires it's inner floats to be placed
        // not taking into account any other floats outside parent. However right now this would result in
        // content overlap if we would behave like this.
        runTest("bfcOwnerOverflowHidden_floatsAndClear", "diff_");
    }

    @Test
    public void bfcOwnerOverflowHidden_marginsCollapse() throws IOException, InterruptedException {
        runTest("bfcOwnerOverflowHidden_marginsCollapse", "diff_");
    }

    private void runTest(String testName, String diff) throws IOException, InterruptedException {
        String htmlName = sourceFolder + testName + ".html";
        String outFileName = destinationFolder + testName + ".pdf";
        String cmpFileName = sourceFolder + "cmp_" + testName + ".pdf";
        HtmlConverter.convertToPdf(new File(htmlName), new File(outFileName));
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlName).getPath() + "\n");
        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, diff));
    }
}
