package com.itextpdf.html2pdf.css.w3c.css21.positioning;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 10))
public class AbsoluteNonReplacedWidth023Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "absolute-non-replaced-width-023.xht";
    }
}
