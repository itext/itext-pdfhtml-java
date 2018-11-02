package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class WhiteSpaceProcessing042Test extends W3CCssAhemFontTest {
    // TODO tab stops are not processed when tab characters are encountered
    @Override
    protected String getHtmlFileName() {
        return "white-space-processing-042.xht";
    }
}