package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2027, DEVSIX-4371. Expected: there is a short blue stripe above a tall orange rectangle. Actual: rectangles are equal
public class BackgroundAttachmentAppliesTo008Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-attachment-applies-to-008.xht";
    }
}