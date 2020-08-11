package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_position;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1708 background-size is not supported
// TODO DEVSIX-1457 background-position is not supported
// TODO DEVSIX-4395 absolute positioning: the order in which absolute-positioned elements are placed in the html is not respected: the one which is higher and/or lefter is written to the pdf first
public class SubpixelPositionCenterTentativeTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "subpixel-position-center.tentative.html";
    }
}
