package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1948, DEVSIX-4370. Test passes if there is a green rectangle on top of a blue stripe, actually at the bottom
public class Background093Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-093.xht";
    }
}