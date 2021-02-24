package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {
        @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 1),
})
//TODO DEVSIX-5004 improve support of flex-items with intrinsic aspect ratio
public class FlexAspectRatioImgRow003Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flex-aspect-ratio-img-row-003.html";
    }
}
