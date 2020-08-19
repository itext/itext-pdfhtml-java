package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;

// TODO DEVSIX-4370 support background repeat
// TODO DEVSIX-2105	support background-origin
public class BackgroundRepeatBpaceBontentBoxTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "background_repeat_space_content_box.htm";
    }
}