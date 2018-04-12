package com.itextpdf.html2pdf.css.w3c.css3_selectors;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG))
public class Css3ModselD1bTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css3-modsel-d1b.html";
    }
}