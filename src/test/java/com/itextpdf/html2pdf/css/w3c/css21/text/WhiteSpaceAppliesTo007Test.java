package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH))
public class WhiteSpaceAppliesTo007Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2444 in tables, non-collapsing spaces are not affecting table cell contents..
    @Override
    protected String getHtmlFileName() {
        return "white-space-applies-to-007.xht";
    }
}