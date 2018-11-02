package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED),
        @LogMessage(messageTemplate = LogMessageConstant.MARGIN_VALUE_IN_PERCENT_NOT_SUPPORTED)})
public class TextIndent103Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-1101, DEVSIX-1989: margins and text-indent in percents not supported
    // TODO DEVSIX-1880: negative margins are poorly supported in some cases
    @Override
    protected String getHtmlFileName() {
        return "text-indent-103.xht";
    }
}