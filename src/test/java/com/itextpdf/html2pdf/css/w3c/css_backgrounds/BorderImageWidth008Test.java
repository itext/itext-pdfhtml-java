package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4435 support border-image-width
// TODO DEVSIX-4434 support border-image-slice
// TODO DEVSIX-4382 support border-image-source
// Note that html and cmp_pdf look pretty the same, but html has some properties that are currently not supported.
// It seems that the same result is due to coincidence.
public class BorderImageWidth008Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "border-image-width-008.html";
    }
}