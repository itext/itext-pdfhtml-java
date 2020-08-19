package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 support background-clip: content-box
public class BackgroundClipContentBoxTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-clip-content-box.html";
    }
}