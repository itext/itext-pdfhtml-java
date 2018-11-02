package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED))
public class TextIndent011Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-1989 text-indent in percents is not supported
    @Override
    protected String getHtmlFileName() {
        return "text-indent-011.xht";
    }
}