package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class WhiteSpace007Test extends W3CCssAhemFontTest {
    // TODO https://wiki.itextsupport.com/display/IT7/HTML-CSS+inline+context+limitations: ignores nowrap on inline elements
    @Override
    protected String getHtmlFileName() {
        return "white-space-007.xht";
    }
}