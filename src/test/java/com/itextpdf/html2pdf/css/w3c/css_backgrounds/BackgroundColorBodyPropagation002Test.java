package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4390 background of the body element overrides the html background
public class BackgroundColorBodyPropagation002Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-color-body-propagation-002.html";
    }
}