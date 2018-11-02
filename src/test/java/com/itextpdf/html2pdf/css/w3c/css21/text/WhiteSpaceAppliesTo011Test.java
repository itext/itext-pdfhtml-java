package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class WhiteSpaceAppliesTo011Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2444: in tables, non-collapsing spaces are not affecting table cell contents..
    @Override
    protected String getHtmlFileName() {
        return "white-space-applies-to-011.xht";
    }
}