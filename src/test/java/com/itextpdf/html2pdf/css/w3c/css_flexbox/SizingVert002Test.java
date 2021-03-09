package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-5135 Flex item with nested floating element processed incorrectly
public class SizingVert002Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flexbox-sizing-vert-002.xhtml";
    }
}
