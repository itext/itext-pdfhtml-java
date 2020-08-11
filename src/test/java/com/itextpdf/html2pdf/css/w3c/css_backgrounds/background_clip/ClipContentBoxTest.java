package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_clip;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-clip is not supported; content-box is not supported (padding for background is not supported)
public class ClipContentBoxTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "clip-content-box.html";
    }
}
