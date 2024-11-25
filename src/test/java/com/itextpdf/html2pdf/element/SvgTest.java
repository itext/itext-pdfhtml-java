/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.svg.logs.SvgLogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class SvgTest extends ExtendedITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/element/SvgTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/element/SvgTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void inlineSvgTest() throws IOException, InterruptedException {
        convertAndCompare("inline_svg");
    }

    //TODO: DEVSIX-8775 support percent values for root svg
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNKNOWN_ABSOLUTE_METRIC_LENGTH_PARSED, count = 4),
    })
    public void inlineSvgWithPercentSizesTest() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_percent_sizes");
    }

    @Test
    public void inlineNestedSvgTest() throws IOException, InterruptedException {
        convertAndCompare("inline_nested_svg");
    }

    @Test
    public void inlineSvgExternalFontRelativeTest() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_external_font_relative");
    }

    @Test
    public void inlineSvgExternalFontUrlTest() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_external_font_url");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA),
    })
    public void convert_inline_Svg_path_in_HTML() throws IOException, InterruptedException {
        convertAndCompare("HTML_with_inline_svg_path");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA),
    })
    // TODO: Update cmp_ file when DEVSIX-2719 resolved
    public void convert_inline_Svg_polygon_in_HTML() throws IOException, InterruptedException {
        convertAndCompare("HTML_with_inline_svg_polygon");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA),
    })
    public void convert_namespace_Svg_in_HTML() throws IOException, InterruptedException {
        convertAndCompare("namespace_svg");
    }

    @Test
    public void convertInlineSvgCircle() throws IOException, InterruptedException {
      String html = "inline_svg_circle";
      PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + html + ".pdf"));
      pdfDoc.addNewPage();
      String string_file = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<svg width=\"100\" height=\"100\">\n" +
                "  <circle cx=\"50\" cy=\"50\" r=\"40\" stroke=\"green\" stroke-width=\"4\" fill=\"yellow\" />\n" +
                "</svg>\n" +
                "\n" +
                "</body>\n" +

                "</html>";
      HtmlConverter.convertToPdf(string_file, pdfDoc, new ConverterProperties());
      Assertions.assertNull(new CompareTool().compareByContent(
              DESTINATION_FOLDER + html + ".pdf", SOURCE_FOLDER + "cmp_" + html + ".pdf", DESTINATION_FOLDER));
    }


    @Test
    public void convertInlineSvgRectangle() throws IOException, InterruptedException {
        String html = "inline_svg_rectangle";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + html + ".pdf"));
        pdfDoc.addNewPage();
        String string_file = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<svg width=\"400\" height=\"100\">\n" +
                "  <rect width=\"400\" height=\"100\" \n" +
                "  style=\"fill:rgb(0,0,255);stroke-width:10;stroke:rgb(0,0,0)\" />\n" +
                "Sorry, your browser does not support inline SVG.\n" +
                "</svg>\n" +
                " \n" +
                "</body>\n" +
                "</html>\n";

        HtmlConverter.convertToPdf(string_file, pdfDoc, new ConverterProperties());
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + html + ".pdf", SOURCE_FOLDER + "cmp_" + html + ".pdf", DESTINATION_FOLDER));
    }

    @Test
    public void convertInlineSvgRoundedRectangle() throws IOException, InterruptedException {
        String html = "inline_svg_rounded_rect";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + html + ".pdf"));
        pdfDoc.addNewPage();
        String string_file = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<svg width=\"400\" height=\"180\">\n" +
                "  <rect x=\"50\" y=\"20\" rx=\"20\" ry=\"20\" width=\"150\" height=\"150\"\n" +
                "  style=\"fill:red;stroke:black;stroke-width:5;opacity:0.5\" />\n" +
                "Sorry, your browser does not support inline SVG.\n" +
                "</svg>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";

        HtmlConverter.convertToPdf(string_file, pdfDoc, new ConverterProperties());
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + html + ".pdf", SOURCE_FOLDER + "cmp_" + html + ".pdf", DESTINATION_FOLDER));
    }

    @Test
    // TODO: Update cmp_ file when DEVSIX-2719 resolved
    public void convertInlineSvgStar() throws IOException, InterruptedException {
        String html = "inline_svg_star";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + html + ".pdf"));
        pdfDoc.addNewPage();
        String string_file = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<svg width=\"300\" height=\"200\">\n" +
                "  <polygon points=\"100,10 40,198 190,78 10,78 160,198\"\n" +
                "  style=\"fill:lime;stroke:purple;stroke-width:5;fill-rule:evenodd;\" />\n" +
                "Sorry, your browser does not support inline SVG.\n" +
                "</svg>\n" +
                " \n" +
                "</body>\n" +
                "</html>\n";

        HtmlConverter.convertToPdf(string_file, pdfDoc, new ConverterProperties());
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + html + ".pdf", SOURCE_FOLDER + "cmp_" + html + ".pdf", DESTINATION_FOLDER));
    }

    @Test
    public void convertInlineSvgLogo() throws IOException, InterruptedException {
        String html = "inline_svg_logo";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + html + ".pdf"));
        pdfDoc.addNewPage();
        String string_file = "<!DOCTYPE html>\n" +
                "  <html>\n" +
                "  <body>\n" +
                "\n" +
                "  <svg height=\"130\" width=\"500\">\n" +
                "    <defs>\n" +
                "      <linearGradient id=\"grad1\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"0%\">\n" +
                "        <stop offset=\"0%\"\n" +
                "        style=\"stop-color:rgb(255,255,0);stop-opacity:1\" />\n" +
                "        <stop offset=\"100%\"\n" +
                "        style=\"stop-color:rgb(255,0,0);stop-opacity:1\" />\n" +
                "      </linearGradient>\n" +
                "    </defs>\n" +
                "    <ellipse cx=\"100\" cy=\"70\" rx=\"85\" ry=\"55\" fill=\"url(#grad1)\" />\n" +
                "    <text fill=\"#ffffff\" font-size=\"45\" font-family=\"Verdana\"\n" +
                "    x=\"50\" y=\"86\">SVG</text>\n" +
                "  Sorry, your browser does not support inline SVG.\n" +
                "  </svg>\n" +
                "\n" +
                "  </body>\n" +
                "  </html>\n";

        HtmlConverter.convertToPdf(string_file, pdfDoc, new ConverterProperties());
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + html + ".pdf", SOURCE_FOLDER + "cmp_" + html + ".pdf", DESTINATION_FOLDER));
    }

    @Test
    public void externalImageSuccessTest() throws IOException, InterruptedException {
        convertAndCompare("external_img");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalImageNonExistentRefTest() throws IOException, InterruptedException {
        convertAndCompare("external_img_nonExistentRef");
    }

    @Test
    //TODO update after DEVSIX-3034
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2)
    })
    public void externalObjectSuccessTest() throws IOException, InterruptedException {
        convertAndCompare("external_object");
    }

    @Test
    public void externalObjectWithResourceTest() throws IOException, InterruptedException {
        convertAndCompare("external_object_with_resource");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 66),
    })
    public void externalObjectWithGoogleCharts() throws IOException, InterruptedException {
        convertAndCompare("inlineSvg_googleCharts");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalObjectNonExistentRefTest() throws IOException, InterruptedException {
        convertAndCompare("external_objectNonExistentRef");
    }

    @Test
    //TODO: Update cmp_ file when DEVSIX-2731 resolved
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void htmlWithSvgBackground() throws IOException, InterruptedException {
        convertAndCompare("HTML_with_svg_background");
    }

    @Test
    //TODO: Update cmp_ file when DEVSIX-2731 resolved
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void htmlWithSvgBackgroundNoViewbox() throws IOException, InterruptedException {
        convertAndCompare("Html_with_svg_background_no_viewbox");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH),
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_HEIGHT),
    })
    public void svgWithoutDimensionsTest() throws IOException, InterruptedException {
        convertAndCompare("svg_without_dimensions");
    }

    @Test
    public void svgWithoutDimensionsWithViewboxTest() throws IOException, InterruptedException {
        convertAndCompare("svg_without_dimensions_with_viewbox");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH, count = 2),
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_HEIGHT, count = 2),
    })
    public void svgWithoutDimensionsImageAndObjectRef() throws IOException, InterruptedException {
        convertAndCompare("svgWithoutDimensionsImageAndObjectRef");
    }

    @Test
    public void inlineSvgWithExternalCssTest() throws IOException, InterruptedException {
        convertAndCompare("inlineSvgWithExternalCss");
    }

    @Test
    public void inlineSvgStyleResolvingOrder1Test() throws IOException, InterruptedException {
        convertAndCompare("inlineSvgStyleResolvingOrder1");
    }

    @Test
    public void inlineSvgStyleResolvingOrder2Test() throws IOException, InterruptedException {
        convertAndCompare("inlineSvgStyleResolvingOrder2");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = SvgLogMessageConstant.UNMAPPED_TAG, logLevel = LogLevelConstants.WARN))
    public void inlineSvgStyleResolvingOrder3Test() throws IOException, InterruptedException {
        convertAndCompare("inlineSvgStyleResolvingOrder3");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = SvgLogMessageConstant.UNMAPPED_TAG, logLevel = LogLevelConstants.WARN))
    public void inlineSvgStyleResolvingOrder4Test() throws IOException, InterruptedException {
        convertAndCompare("inlineSvgStyleResolvingOrder4");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = SvgLogMessageConstant.UNMAPPED_TAG, logLevel = LogLevelConstants.WARN))
    public void inlineSvgStyleResolvingOrder5Test() throws IOException, InterruptedException {
        convertAndCompare("inlineSvgStyleResolvingOrder5");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNKNOWN_ABSOLUTE_METRIC_LENGTH_PARSED, count = 2),
    })
    //TODO: DEVSIX-8775 resolve problem with AbstractContainerSvgNodeRenderer#calculateViewPort()
    public void svgWithEmRemTest() throws IOException, InterruptedException {
        convertAndCompare("svgWithEmRem");
    }

    private static void convertAndCompare(String name)
            throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + name + ".html"), new File(DESTINATION_FOLDER + name + ".pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + name + ".pdf", SOURCE_FOLDER + "cmp_" + name + ".pdf",
                DESTINATION_FOLDER, "diff_" + name + "_"));
    }
}
