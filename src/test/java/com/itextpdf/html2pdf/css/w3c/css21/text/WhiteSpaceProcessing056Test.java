package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class WhiteSpaceProcessing056Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2443 The 'ideographic space' character is not collapsed by the white space processing model
    @Override
    protected String getHtmlFileName() {
        return "white-space-processing-056.xht";
    }
}