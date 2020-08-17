package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_attachment_local;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4401 background: no-repeat is aligned to the bottom-right corner instead of the top-left one
public class AttachmentLocalPositioning3RefTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "attachment-local-positioning-3-ref.html";
    }
}
