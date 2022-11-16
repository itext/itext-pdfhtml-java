package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ObjectTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/ObjectTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/ObjectTest/";


    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void base64svgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_base64svg", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate =
                    StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI, count = 1),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 1)})
    public void htmlObjectIncorrectBase64Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_incorrectBase64svg", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: update after DEVSIX-1346
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT,
                    count = 1),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2),
    })
    public void htmlObjectAltTextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_altText", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER,
                    count = 1),})
    public void htmlObjectNestedObjectTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("objectTag_nestedTag", sourceFolder, destinationFolder);
    }

}
