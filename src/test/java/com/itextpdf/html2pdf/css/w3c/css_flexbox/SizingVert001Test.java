package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-5096 support flex-direction: column
//TODO DEVSIX-5135 Flex item with nested floating element processed incorrectly
//TODO DEVSIX-5178 Incorrect handling of min-height and max-height
public class SizingVert001Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flexbox-sizing-vert-001.xhtml";
    }
}
