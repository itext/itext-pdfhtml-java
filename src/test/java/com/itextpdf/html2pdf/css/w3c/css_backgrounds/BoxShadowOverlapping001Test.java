package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4384 box-shadow is not supported
public class BoxShadowOverlapping001Test extends W3CCssAhemFontTest {
    @Override
    protected String getHtmlFileName() {
        return "box-shadow-overlapping-001.html";
    }
}