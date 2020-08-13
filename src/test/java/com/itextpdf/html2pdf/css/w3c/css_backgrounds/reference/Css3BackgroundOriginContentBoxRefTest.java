package com.itextpdf.html2pdf.css.w3c.css_backgrounds.reference;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED)})
public class Css3BackgroundOriginContentBoxRefTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css3-background-origin-content-box-ref.html";
    }
}
