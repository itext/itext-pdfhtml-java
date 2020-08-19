package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4384 box-shadow is not supported
// Note that html and cmp_pdf look pretty the same, but html has some properties that are currently not supported.
// It seems that the same result is due to coincidence.
public class BoxShadow004Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "box-shadow-004.htm";
    }
}