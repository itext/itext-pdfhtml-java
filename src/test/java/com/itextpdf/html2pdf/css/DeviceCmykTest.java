package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
import com.itextpdf.styledxmlparser.css.validate.impl.CssDefaultValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.CssDeviceCmykAwareValidator;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class DeviceCmykTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/DeviceCmykTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/DeviceCmykTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
        CssDeclarationValidationMaster.setValidator(new CssDeviceCmykAwareValidator());
    }

    @AfterClass
    public static void after() {
        CssDeclarationValidationMaster.setValidator(new CssDefaultValidator());
    }

    @Test
    public void bodyBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void headerColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void headerBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void borderColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void simpleSvgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleSvgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
