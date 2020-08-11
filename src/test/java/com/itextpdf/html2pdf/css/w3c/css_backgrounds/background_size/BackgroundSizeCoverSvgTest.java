package com.itextpdf.html2pdf.css.w3c.css_backgrounds.background_size;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1708 background-size is not supported
// TODO DEVSIX-1708 the height of the background in browsers is less than in pdf (as if the svg is shorten on some level)
public class BackgroundSizeCoverSvgTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-size-cover-svg.html";
    }
}
