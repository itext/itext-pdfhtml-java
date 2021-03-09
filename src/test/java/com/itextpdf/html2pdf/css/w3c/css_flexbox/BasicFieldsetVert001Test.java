package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

//TODO DEVSIX-5096 support flex-direction: column
//TODO DEVSIX-5163 Support more complex justify-content values
public class BasicFieldsetVert001Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flexbox-basic-fieldset-vert-001.xhtml";
    }
}
