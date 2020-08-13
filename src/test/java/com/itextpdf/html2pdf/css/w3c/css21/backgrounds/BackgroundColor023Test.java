package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
})
public class BackgroundColor023Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-color-023.xht";
    }
}