package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;

public class TextIndent014Test extends W3CCssAhemFontTest {
    // TODO DEVSIX-1880: negative margin boxes are poorly supported

    // TODO In HTML+CSS, text-indent affects only the first line of a block container if that line is also the first formatted line of an element.
    //      However we don't check if the line is the first formatted line of the container, and moreover we lose this information, if raw text data is intermittent with block elements.
    @Override
    protected String getHtmlFileName() {
        return "text-indent-014.xht";
    }
}