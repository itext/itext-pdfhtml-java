package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_repeat;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-4396 background: radial-gradient is not supported
@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
})public class GradientRepeatSpacedWithBordersTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "gradient-repeat-spaced-with-borders.html";
    }
}