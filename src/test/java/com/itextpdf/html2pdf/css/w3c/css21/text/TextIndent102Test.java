package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED))
public class TextIndent102Test extends W3CCssAhemFontTest {
    @Override
    protected String getHtmlFileName() {
        return "text-indent-102.xht";
    }
}