package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

// TODO DEVSIX-4384 box-shadow is not supported
// Note that the font Ahem must be installed on your system to view valid HTML
public class BoxShadowOverlapping002Test extends W3CCssAhemFontTest {
    @Override
    protected String getHtmlFileName() {
        return "box-shadow-overlapping-002.html";
    }
}