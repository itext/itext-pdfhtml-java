package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-1708 support background-size
public class BackgroundSizeAppliesToBlockTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-size-applies-to-block.htm";
    }
}