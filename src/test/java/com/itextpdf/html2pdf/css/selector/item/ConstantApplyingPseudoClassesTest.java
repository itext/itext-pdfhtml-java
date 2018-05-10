package com.itextpdf.html2pdf.css.selector.item;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ConstantApplyingPseudoClassesTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/selector/item/ConstantApplyingPseudoClassesTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/selector/item/ConstantApplyingPseudoClassesTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void alwaysApplyPseudoClassesTest01() throws IOException, InterruptedException {
        convertToPdfAndCompare("alwaysApplyPseudoClassesTest01", sourceFolder, destinationFolder);
    }
}
