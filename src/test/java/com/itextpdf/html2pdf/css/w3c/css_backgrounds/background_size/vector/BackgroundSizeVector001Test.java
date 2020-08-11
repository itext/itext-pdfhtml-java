package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_size.vector;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-4388 percentage is not supported for rect's x, y, width and height
// TODO DEVSIX-1708 background-size is not supported
@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.UNKNOWN_ABSOLUTE_METRIC_LENGTH_PARSED, count = 5)
})
public class BackgroundSizeVector001Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-size-vector-001.html";
    }
}
