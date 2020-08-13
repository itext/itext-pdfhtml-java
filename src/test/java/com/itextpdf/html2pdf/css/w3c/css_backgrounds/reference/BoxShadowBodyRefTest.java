package com.itextpdf.html2pdf.css.w3c.css_backgrounds.reference;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.utils.CompareTool;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// TODO DEVSIX-4383 html files with empty body (and css properties on body and html tags) are not processed
// TODO DEVSIX-4384 box-shadow is not supported
public class BoxShadowBodyRefTest extends W3CCssTest {
    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Override
    protected String getHtmlFileName() {
        return "box-shadow-body-ref.html";
    }

    @Test
    public void test() throws IOException, InterruptedException {
        junitExpectedException.expect(PdfException.class);
        super.test();
    }
}
