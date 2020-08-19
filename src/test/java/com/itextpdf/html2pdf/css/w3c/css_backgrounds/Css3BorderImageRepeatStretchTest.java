package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4382 border-image-source is not supported
// TODO DEVSIX-4434 support border-image-slice
// TODO DEVSIX-4436 support border-image-repeat
public class Css3BorderImageRepeatStretchTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css3-border-image-repeat-stretch.html";
    }
}