package com.itextpdf.html2pdf.attribute;

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
public class AlignAttributeTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute/AlignAttributeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/AlignAttributeTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void alignImgTest01() throws IOException, InterruptedException {
        // vertical-alignment values top, middle and bottom are not currently supported for inline-block elements and images
        runTest("alignImgTest01");
    }

    public void runTest(String name) throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_"  + name + ".pdf", destinationFolder, "diff_"));
    }
}
