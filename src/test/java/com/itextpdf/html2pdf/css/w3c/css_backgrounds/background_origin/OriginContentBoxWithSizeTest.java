package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_origin;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-origin: content-box is not supported (padding for background is not supported)
// TODO DEVSIX-1708 background-size is not supported
public class OriginContentBoxWithSizeTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "origin-content-box_with_size.html";
    }
}