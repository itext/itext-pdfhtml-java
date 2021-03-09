package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-4443 improve segment frequency for dashed border
//TODO DEVSIX-5198 Fix a bug with incorrect processing of the width property in the flex algorithm
//TODO DEVSIX-5181 Support calc function for width
public class MinWidthAuto001Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flexbox-min-width-auto-001.html";
    }
}
