package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_origin;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-origin: border-box is not supported
// TODO DEVSIX-1457 background-position is not supported
public class OriginBorderBoxWithPositionTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "origin-border-box_with_position.html";
    }
}
