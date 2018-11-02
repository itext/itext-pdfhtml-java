package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class WhiteSpaceMixed004Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2443: collapsing doesn't work exactly as needed
    @Override
    protected String getHtmlFileName() {
        return "white-space-mixed-004.xht";
    }
}