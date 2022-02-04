package com.itextpdf.html2pdf.attach.impl.layout.form.renderer;

import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.Button;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.InputButton;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.Background;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TransparentColor;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.FileOutputStream;
import java.io.IOException;

@Category(IntegrationTest.class)
public class ButtonColorTest extends ExtendedITextTest {

    public static final String SOURCE_FOLDER =
            "./src/test/resources/com/itextpdf/html2pdf/attach/impl/layout/form/renderer/ButtonColorTest/";
    public static final String DESTINATION_FOLDER =
            "./target/test/com/itextpdf/html2pdf/attach/impl/layout/form/renderer/ButtonColorTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void buttonsWithColorTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "buttonsWithColor.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_buttonsWithColor.pdf";

        drawButtons(outPdf, cmpPdf, ColorConstants.RED);
    }

    @Test
    public void buttonsWithoutColorTest() throws IOException, InterruptedException {
        String outPdf = DESTINATION_FOLDER + "buttonsWithoutColor.pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_buttonsWithoutColor.pdf";

        drawButtons(outPdf, cmpPdf, null);
    }

    private static void drawButtons(String outPdf, String cmpPdf, Color color) throws IOException, InterruptedException {
        try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outPdf)));
             Document document = new Document(pdfDocument)) {

            Button button = new Button("button");
            button.add(new Paragraph("button child"));
            InputButton inputButton = new InputButton("input button");
            button.setProperty(Html2PdfProperty.FORM_FIELD_FLATTEN, false);
            inputButton.setProperty(Html2PdfProperty.FORM_FIELD_FLATTEN, false);
            button.setProperty(Html2PdfProperty.FORM_FIELD_VALUE, "button value");
            inputButton.setProperty(Html2PdfProperty.FORM_FIELD_VALUE, "input button value");
            button.setProperty(Property.FONT_COLOR, color == null ? null : new TransparentColor(color));
            inputButton.setProperty(Property.BACKGROUND, color == null ? null : new Background(color));

            document.add(button);
            document.add(inputButton);
        }

        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER));
    }
}
