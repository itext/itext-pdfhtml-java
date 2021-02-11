package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.INVALID_GRADIENT_DECLARATION, logLevel =
                LogLevelConstants.WARN)
})
//TODO DEVSIX-1315 Initial support for flex display:flex CSS property
public class AlignItems003Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "align-items-003.htm";
    }
}
