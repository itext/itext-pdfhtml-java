package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4417. For iText body takes the whole page area, but for browsers body takes just the number
//  of space which is needed for its elements.
public class BackgroundRoot003Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-root-003.xht";
    }
}