package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_size;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// DEVSIX-1708 TODO background-size is not supported
public class BackgroundSizeNearZeroSvgTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-size-near-zero-svg.html";
    }
}
