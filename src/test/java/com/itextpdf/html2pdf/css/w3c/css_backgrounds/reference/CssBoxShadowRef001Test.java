package com.itextpdf.html2pdf.css.w3c.css_backgrounds.reference;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG)})
public class CssBoxShadowRef001Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css-box-shadow-ref-001.html";
    }
}
