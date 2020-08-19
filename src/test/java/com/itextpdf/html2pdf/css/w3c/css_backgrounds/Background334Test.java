package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-1708 support background size
// TODO DEVSIX-1457 support background-position
@LogMessages(messages = {
        @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.WAS_NOT_ABLE_TO_DEFINE_BACKGROUND_CSS_SHORTHAND_PROPERTIES)})
public class Background334Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-334.html";
    }
}