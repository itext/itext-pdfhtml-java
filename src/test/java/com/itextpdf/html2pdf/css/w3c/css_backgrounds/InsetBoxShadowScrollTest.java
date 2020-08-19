package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4384 box-shadow is not supported
public class InsetBoxShadowScrollTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "inset-box-shadow-scroll.html";
    }
}