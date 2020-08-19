package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// HTML doesn't correspond its description at least in google chrome.
// There are red rectangles behind the letters. But the resulting PDF looks correct.
public class FirstLetterSpaceNotSelectedTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "first-letter-space-not-selected.html";
    }
}