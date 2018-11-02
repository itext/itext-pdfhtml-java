package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class WhiteSpaceProcessing049Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2459: absolutely positioned blue box width is not set correctly to its intrinsic max-width
    @Override
    protected String getHtmlFileName() {
        return "white-space-processing-049.xht";
    }
}