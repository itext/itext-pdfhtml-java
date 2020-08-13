package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

// TODO DEVSIX-2027. There are NO multiple pages and there is NO a blue box on all pages.
public class BackgroundAttachment010Test extends W3CCssTest {
    private static final String SRC_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/w3c/css21/backgrounds/";

    @Override
    protected String getHtmlFileName() {
        return "background-attachment-010.xht";
    }

    // iText sets "enable printing background images" by default, but "paged media view" enables below in the method
    @Override
    protected ConverterProperties getConverterProperties() {
        return new ConverterProperties().setBaseUri(SRC_FOLDER + "background-attachment-010.xht")
                .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT));
    }
}