package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_clip;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1708 background-size is not supported
public class ClipBorderBoxWithSizeTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "clip-border-box_with_size.html";
    }
}
