package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class TextAlignAppliesTo003Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2445 display: list-item is not supported for spans
    @Override
    protected String getHtmlFileName() {
        return "text-align-applies-to-003.xht";
    }
}