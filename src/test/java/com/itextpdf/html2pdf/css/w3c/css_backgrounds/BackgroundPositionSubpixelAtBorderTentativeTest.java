package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1457 support background-position
// PDF and HTML look pretty the same but background-position is not supported
public class BackgroundPositionSubpixelAtBorderTentativeTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-position-subpixel-at-border.tentative.html";
    }
}