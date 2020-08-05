package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1948, DEVSIX-4370. Test passes if there is a green rectangle on top of a blue stripe, actually it's below
public class Background090Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-090.xht";
    }
}