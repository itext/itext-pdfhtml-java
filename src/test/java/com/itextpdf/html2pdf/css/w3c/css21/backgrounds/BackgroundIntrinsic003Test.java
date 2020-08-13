package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-2654. Svg width values in percents aren't supported
@LogMessages(messages = {
        @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH, count = 2),
        @LogMessage(messageTemplate = LogMessageConstant.UNKNOWN_ABSOLUTE_METRIC_LENGTH_PARSED, count = 4),
})
public class BackgroundIntrinsic003Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-intrinsic-003.xht";
    }
}