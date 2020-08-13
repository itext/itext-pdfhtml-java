package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2027, DEVSIX-4371. Expected: there is a short blue stripe above a tall orange rectangle. Actual: nothing.
public class BackgroundAttachmentAppliesTo005Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-attachment-applies-to-005.xht";
    }
}