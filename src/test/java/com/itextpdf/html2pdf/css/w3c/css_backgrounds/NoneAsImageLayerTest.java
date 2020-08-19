package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-2027 process multiple backgrounds
// TODO DEVSIX-1457	support background-position
// TODO DEVSIX-4370 support background-repeat
public class NoneAsImageLayerTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "none-as-image-layer.htm";
    }
}