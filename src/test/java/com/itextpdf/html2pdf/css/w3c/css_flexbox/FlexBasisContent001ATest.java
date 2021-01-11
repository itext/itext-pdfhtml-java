package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

//TODO DEVSIX-1315 Initial support for flex display:flex CSS property
@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 3)
})
public class FlexBasisContent001ATest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flexbox-flex-basis-content-001a.html";
    }
}
