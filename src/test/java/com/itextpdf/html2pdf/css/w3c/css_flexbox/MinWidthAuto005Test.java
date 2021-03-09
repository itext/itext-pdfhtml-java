package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-5004 pdfHTML: improve support of flex-items with intrinsic aspect ratio
//TODO DEVSIX-5087 Support layout properties for FlexContainerRenderer
public class MinWidthAuto005Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flexbox-min-width-auto-005.html";
    }
}
