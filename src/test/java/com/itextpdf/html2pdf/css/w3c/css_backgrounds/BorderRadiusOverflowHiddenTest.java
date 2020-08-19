package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4400 overflow: hidden is not working with border-radius
public class BorderRadiusOverflowHiddenTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "border-radius-overflow-hidden.html";
    }
}