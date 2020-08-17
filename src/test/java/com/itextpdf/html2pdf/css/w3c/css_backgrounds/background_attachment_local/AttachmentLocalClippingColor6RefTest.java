package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_attachment_local;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

// TODO DEVSIX-4398 border-radius is not supported for double borders
// TODO DEVSIX-4400 overflow: hidden is not working with border-radius
@LogMessages(messages = {
        @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.METHOD_IS_NOT_IMPLEMENTED_BY_DEFAULT_OTHER_METHOD_WILL_BE_USED, count = 4)
})
public class AttachmentLocalClippingColor6RefTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "attachment-local-clipping-color-6-ref.html";
    }
}
