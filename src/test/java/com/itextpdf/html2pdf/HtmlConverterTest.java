package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(IntegrationTest.class)
public class HtmlConverterTest extends ExtendedITextTest {

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Test
    public void cannotConvertHtmlToDocumentInReadingModeTest() throws IOException {
        junitExpectedException.expect(Html2PdfException.class);
        junitExpectedException.expectMessage(Html2PdfException.PDF_DOCUMENT_SHOULD_BE_IN_WRITING_MODE);

        PdfDocument pdfDocument = createTempDoc();
        ConverterProperties properties = new ConverterProperties();
        Document document = HtmlConverter.convertToDocument("", pdfDocument, properties);
    }

    private static PdfDocument createTempDoc() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        pdfDocument.addNewPage();
        pdfDocument.close();
        pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(outputStream.toByteArray())));
        return pdfDocument;
    }
}
