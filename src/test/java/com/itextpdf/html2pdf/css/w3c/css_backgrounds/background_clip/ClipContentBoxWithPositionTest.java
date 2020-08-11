package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_clip;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-clip is not supported; content-box is not supported (padding for background is not supported)
// TODO DEVSIX-1457 background-position is not supported
public class ClipContentBoxWithPositionTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "clip-content-box_with_position.html";
    }
}