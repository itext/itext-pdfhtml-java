package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1948, DEVSIX-4370. Test passes if there is an orange rectangle above a blue stripe, actually BELOW
public class Background087Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-087.xht";
    }
}