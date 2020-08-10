package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_origin;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-origin: content-box is not supported (padding for background is not supported)
public class OriginContentBoxWithRadiusTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "origin-content-box_with_radius.html";
    }
}