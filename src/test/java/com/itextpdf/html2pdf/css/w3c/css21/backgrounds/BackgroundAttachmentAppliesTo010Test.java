package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2027, DEVSIX-4371. 1) blue stripe should be short 2) there is NO a marker bullet on the left-hand side of the boxes.
public class BackgroundAttachmentAppliesTo010Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-attachment-applies-to-010.xht";
    }
}