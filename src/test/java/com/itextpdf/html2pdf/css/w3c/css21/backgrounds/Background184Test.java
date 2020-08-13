package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1457. Background-position is not supported: currently we apply background from the top
//  and not from the bottom of the area
public class Background184Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-184.xht";
    }
}