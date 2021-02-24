package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-5096 support flex-direction: column
//TODO DEVSIX-5004 pdfHTML: improve support of flex-items with intrinsic aspect ratio
//TODO DEVSIX-5137 support margin collapse
public class FlexAspectRatioImgColumn005Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flex-aspect-ratio-img-column-005.html";
    }
}
