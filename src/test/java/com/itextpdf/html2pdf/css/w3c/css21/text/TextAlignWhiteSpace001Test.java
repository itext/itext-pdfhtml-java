package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class TextAlignWhiteSpace001Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2446 text-align: justify + non-collapsible spaces whitespace property
    @Override
    protected String getHtmlFileName() {
        return "text-align-white-space-001.xht";
    }
}