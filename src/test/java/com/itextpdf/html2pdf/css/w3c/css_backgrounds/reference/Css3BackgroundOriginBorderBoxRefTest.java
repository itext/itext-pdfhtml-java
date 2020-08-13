package com.itextpdf.html2pdf.css.w3c.css_backgrounds.reference;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-2449 z-index is not supported
@LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED)})
public class Css3BackgroundOriginBorderBoxRefTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css3-background-origin-border-box-ref.html";
    }
}
