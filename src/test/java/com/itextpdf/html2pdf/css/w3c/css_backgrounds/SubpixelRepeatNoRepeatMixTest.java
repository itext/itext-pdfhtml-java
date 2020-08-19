package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4380	support background-position-x
// TODO DEVSIX-1708	Support background-size CSS property in pdfHTML
// TODO DEVSIX-4370 support background-repeat
public class SubpixelRepeatNoRepeatMixTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "subpixel-repeat-no-repeat-mix.html";
    }
}