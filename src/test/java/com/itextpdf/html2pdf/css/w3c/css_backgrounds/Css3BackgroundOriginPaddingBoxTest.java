package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4443 improve segment frequency for dashed border
// TODO DEVSIX-2105 check that PDF is correct after supporting of background-origin
// Note that currently PDF looks correct cause background-origin: padding-box is default value
// and it matches to our default implementation
public class Css3BackgroundOriginPaddingBoxTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "css3-background-origin-padding-box.html";
    }
}