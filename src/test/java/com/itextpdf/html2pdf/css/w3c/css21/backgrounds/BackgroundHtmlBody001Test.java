package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4390. Background of the body element overrides the html background
public class BackgroundHtmlBody001Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-html-body-001.xht";
    }
}