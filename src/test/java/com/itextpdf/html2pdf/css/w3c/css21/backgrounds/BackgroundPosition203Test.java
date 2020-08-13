package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-1457. Background-position isn't supported
@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.WAS_NOT_ABLE_TO_DEFINE_BACKGROUND_CSS_SHORTHAND_PROPERTIES)
})
public class BackgroundPosition203Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-position-203.xht";
    }
}