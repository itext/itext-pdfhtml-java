package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4384 box-shadow is not supported
public class Css3BoxShadowTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css3-box-shadow.html";
    }
}