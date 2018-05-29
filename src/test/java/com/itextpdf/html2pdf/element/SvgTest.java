package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class SvgTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/SvgTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/SvgTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Ignore("RND-982")
    @Test
    public void InlineSvgTest() throws IOException, InterruptedException {
        String name = "inline";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name+".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name +".pdf", sourceFolder + "cmp_" + name+".pdf", destinationFolder, "diff_"+name+"_"));

    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.ERROR_PARSING_COULD_NOT_MAP_NODE)})
    public void externalImageSuccessTest() throws IOException, InterruptedException {
        String name = "external_img";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name+".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name +".pdf", sourceFolder + "cmp_" + name+".pdf", destinationFolder, "diff_"+name+"_"));

    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalImageNonExistentRefTest() throws IOException, InterruptedException {
        String name = "external_img_nonExistentRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name+".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name +".pdf", sourceFolder + "cmp_" + name+".pdf", destinationFolder, "diff_"+name+"_"));

    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.ERROR_PARSING_COULD_NOT_MAP_NODE)})
    public void externalObjectSuccessTest() throws IOException, InterruptedException {
        String name = "external_object";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name+".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name +".pdf", sourceFolder + "cmp_" + name+".pdf", destinationFolder, "diff_"+name+"_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalObjectNonExistentRefTest() throws IOException, InterruptedException {
        String name = "external_objectNonExistentRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name+".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name +".pdf", sourceFolder + "cmp_" + name+".pdf", destinationFolder, "diff_"+name+"_"));
    }
}
