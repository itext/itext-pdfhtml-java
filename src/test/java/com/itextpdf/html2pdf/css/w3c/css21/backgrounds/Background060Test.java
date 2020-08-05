package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1948, DEVSIX-4370. Failed. blue stripe inside the hollow black square, positioned at the top (instead of bottom) of the black square.
public class Background060Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-060.xht";
    }
}