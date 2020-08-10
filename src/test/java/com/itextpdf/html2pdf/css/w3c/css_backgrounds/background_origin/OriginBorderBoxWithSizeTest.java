package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_origin;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-origin: border-box is not supported
// TODO DEVSIX-1708 background-size is not supported
public class OriginBorderBoxWithSizeTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "origin-border-box_with_size.html";
    }
}
