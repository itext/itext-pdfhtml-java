package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4433 process horizontal padding correctly
public class BackgroundGradientSubpixelFillsAreaTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-gradient-subpixel-fills-area.html";
    }
}