package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class TextIndent012Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2450: max width for div with float and inline-block is not calculated properly
    @Override
    protected String getHtmlFileName() {
        return "text-indent-012.xht";
    }
}