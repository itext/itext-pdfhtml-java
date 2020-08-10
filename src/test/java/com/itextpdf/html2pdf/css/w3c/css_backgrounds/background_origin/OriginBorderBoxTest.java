package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_origin;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-origin: border-box is not supported
public class OriginBorderBoxTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "origin-border-box.html";
    }
}
