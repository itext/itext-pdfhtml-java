package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.kernel.PdfException;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// TODO DEVSIX-4440 html with display: none throws exceptions. Remove expected exception after fixing
public class BackgroundColorBodyPropagation004Test extends W3CCssTest {
    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Override
    protected String getHtmlFileName() {
        return "background-color-body-propagation-004.html";
    }

    @Test
    @Override
    public void test() throws IOException, InterruptedException {
        junitExpectedException.expect(PdfException.class);
        junitExpectedException.expectMessage(PdfException.DocumentHasNoPages);
        super.test();
    }
}