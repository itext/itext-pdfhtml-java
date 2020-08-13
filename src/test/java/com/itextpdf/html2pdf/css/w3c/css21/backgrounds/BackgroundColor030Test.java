package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION),
        @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.UNKNOWN_COLOR_FORMAT_MUST_BE_RGB_OR_RRGGBB)
})
public class BackgroundColor030Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-color-030.xht";
    }
}