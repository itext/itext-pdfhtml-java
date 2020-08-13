package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-4391. Iframe is not supported.
@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG))
public class BackgroundIframes001Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-iframes-001.xht";
    }
}