package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class TextTransformAppliesTo003Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2448 transform: capitalize not supported
    @Override
    protected String getHtmlFileName() {
        return "text-transform-applies-to-003.xht";
    }
}