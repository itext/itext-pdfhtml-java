package com.itextpdf.html2pdf.css.w3c.css_backgrounds;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.kernel.PdfException;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// TODO DEVSIX-4383 html files with empty body are not processed. Remove expected exception after fixing
public class BackgroundSizePercentageRootTest extends W3CCssTest {
    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Override
    protected String getHtmlFileName() {
        return "background-size-percentage-root.html";
    }

    @Test
    @Override
    public void test() throws IOException, InterruptedException {
        junitExpectedException.expect(PdfException.class);
        junitExpectedException.expectMessage(PdfException.DocumentHasNoPages);
        super.test();
    }
}