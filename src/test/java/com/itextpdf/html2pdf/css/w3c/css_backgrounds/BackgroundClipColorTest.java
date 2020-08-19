package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-2105 support background-clip: padding-box;
public class BackgroundClipColorTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-clip-color.html";
    }
}