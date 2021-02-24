package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-5096 support flex-direction: column
//TODO DEVSIX-5040 layout: support justify-content and align-items
//TODO DEVSIX-5004 improve support of flex-items with intrinsic aspect ratio
public class FlexAspectRatioImgColumn012Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flex-aspect-ratio-img-column-012.html";
    }
}
