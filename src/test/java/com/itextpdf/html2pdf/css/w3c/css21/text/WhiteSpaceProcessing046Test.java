package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class WhiteSpaceProcessing046Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-2443 Space is not removed at the end of the line when 'white-space' is set to 'pre'
    @Override
    protected String getHtmlFileName() {
        return "white-space-processing-046.xht";
    }
}