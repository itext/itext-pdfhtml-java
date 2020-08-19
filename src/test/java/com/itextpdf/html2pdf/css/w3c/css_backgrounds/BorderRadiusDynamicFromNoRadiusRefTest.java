package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4439 border-radius works incorectly in the inner div
public class BorderRadiusDynamicFromNoRadiusRefTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "border-radius-dynamic-from-no-radius-ref.html";
    }
}