package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_clip;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 background-clip is not supported
// TODO DEVSIX-1708 background-size is not supported
public class ClipPaddingBoxWithSizeTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "clip-padding-box_with_size.html";
    }
}