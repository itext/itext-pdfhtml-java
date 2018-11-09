package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class CssCaseSensitivityTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssCaseSensitivityTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssCaseSensitivityTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-2430")
    public void listTypeTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("listType01", sourceFolder, destinationFolder);
    }

}
