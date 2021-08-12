package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class HtmlDocumentRendererIntegrationTest extends ExtendedHtmlConversionITextTest {
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/attach/impl/layout/HtmlDocumentRendererIntegrationTest/";
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/attach/impl/layout/HtmlDocumentRendererIntegrationTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void emptyPageRelayoutCausedByPagesCounterTest() throws IOException, InterruptedException {
        // HtmlDocumentRenderer#getNextRenderer can be called when currentArea == null
        convertToPdfAndCompare("emptyPageRelayoutCausedByPagesCounter", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

}
