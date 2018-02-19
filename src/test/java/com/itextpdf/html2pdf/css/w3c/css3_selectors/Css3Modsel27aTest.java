package com.itextpdf.html2pdf.css.w3c.css3_selectors;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.ERROR_PARSING_CSS_SELECTOR, count = 6))
public class Css3Modsel27aTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css3-modsel-27a.html";
    }
}