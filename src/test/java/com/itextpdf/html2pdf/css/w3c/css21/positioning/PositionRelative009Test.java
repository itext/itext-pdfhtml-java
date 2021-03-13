package com.itextpdf.html2pdf.css.w3c.css21.positioning;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 2))
public class PositionRelative009Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "position-relative-009.xht";
    }
}
