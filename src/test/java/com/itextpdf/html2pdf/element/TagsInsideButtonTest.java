package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


@Category(IntegrationTest.class)
public class TagsInsideButtonTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TagsInsideButtonTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TagsInsideButtonTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void buttonWithImageInside() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithImageInside", sourceFolder, destinationFolder, false);
    }

    @Test
    public void buttonWithImageInsideTagged() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithImageInside", sourceFolder, destinationFolder, true);
    }

    @Test
    public void buttonWithPInside() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithPInside", sourceFolder, destinationFolder, false);
    }

    @Test
    public void buttonWithPInsideTagged() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithPInside", sourceFolder, destinationFolder, true);
    }
}
