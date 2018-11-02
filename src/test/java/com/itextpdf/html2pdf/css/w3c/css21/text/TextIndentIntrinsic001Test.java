package com.itextpdf.html2pdf.css.w3c.css21.text;

import com.itextpdf.html2pdf.css.w3c.W3CCssAhemFontTest;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

public class TextIndentIntrinsic001Test extends W3CCssAhemFontTest {
    // TODO 5th and 6th blocks min width is still different from what's in browsers. -- their min-width is bigger than required.
    @Override
    protected String getHtmlFileName() {
        return "text-indent-intrinsic-001.xht";
    }
}