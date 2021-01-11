package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

//TODO DEVSIX-1315 Initial support for flex display:flex CSS property
@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 18)
})
public class FlexWrapWrapTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flexbox-flex-wrap-wrap.htm";
    }
}
