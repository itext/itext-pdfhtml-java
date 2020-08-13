package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2027, DEVSIX-4371. Expected: a blue stripe should be short
public class BackgroundAttachmentAppliesTo007Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-attachment-applies-to-007.xht";
    }
}