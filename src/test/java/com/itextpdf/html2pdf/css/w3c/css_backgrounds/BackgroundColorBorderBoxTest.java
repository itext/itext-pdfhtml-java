package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2105 support background-clip
// TODO DEVSIX-1457 support background-position
public class BackgroundColorBorderBoxTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background-color-border-box.htm";
    }
}