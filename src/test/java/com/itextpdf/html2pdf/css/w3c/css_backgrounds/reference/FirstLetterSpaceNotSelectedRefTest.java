package com.itextpdf.html2pdf.css.w3c.css_backgrounds.reference;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4387 &nbsp; &ensp; &emsp; &thinsp; are not processed correctly
public class FirstLetterSpaceNotSelectedRefTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "first-letter-space-not-selected-ref.html";
    }
}
