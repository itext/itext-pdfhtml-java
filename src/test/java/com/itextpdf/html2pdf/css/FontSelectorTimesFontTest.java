package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedFontPropertiesTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FontSelectorTimesFontTest extends ExtendedFontPropertiesTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/FontSelectorTimesFontTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/FontSelectorTimesFontTest/";

    private static String[] FONT_WEIGHTS = {"normal", "bold", "100", "300",
            "500", "600", "700", "900"}; // TODO DEVSIX-2114 Add bolder/lighter font-weights once they are supported
    private static String[] FONT_STYLES = {"normal", "italic", "oblique"};

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void timesFontFamilyTest() throws IOException, InterruptedException {

        String text = "quick brown fox jumps over the lazy dog";
        String[] fontFamilies = {"times", "roman", "times roman", "times-roman", "times bold", "times-bold",
                "times-italic", "times-italic", "times roman italic", "times-roman italic", "times roman bold", "times-roman bold"};
        String fileName = "timesFontFamilyTest";
        String htmlString = buildDocumentTree(fontFamilies, FONT_WEIGHTS, FONT_STYLES, null, text);

        runTest(htmlString, sourceFolder, destinationFolder, fileName, fileName);
    }

    @Test
    public void timesFontFamilyTest02() throws IOException, InterruptedException {
        String text = "quick brown fox jumps over the lazy dog";
        String[] fontFamilies = {"times", "new", "roman",
                "times new", "times roman", "new roman",
                "times new roman",
                "times new toman", "times mew roman", "mimes new roman"};
        String fileName = "timesFontFamilyTest02";
        String htmlString = buildDocumentTree(fontFamilies, new String[]{"normal"}, new String[]{"normal"}, null, text);

        runTest(htmlString, sourceFolder, destinationFolder, fileName, fileName);
    }
}
