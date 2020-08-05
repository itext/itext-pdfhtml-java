package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1948, DEVSIX-4370. Test passes if there is a filled green rectangle above a short blue stripe, actually it's bellow
public class Background130Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-130.xht";
    }
}