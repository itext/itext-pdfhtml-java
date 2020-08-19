package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-4423 support repeating-radial-gradient, change cmp and remove log message
// TODO DEVSIX-1457 support background-position
@LogMessages(messages = {
        @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)})
public class BackgroundImageCenteredTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-image-centered.html";
    }
}