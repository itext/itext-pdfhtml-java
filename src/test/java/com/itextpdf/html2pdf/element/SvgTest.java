/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class SvgTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/SvgTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/SvgTest/";


    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void inlineSvgTest() throws IOException, InterruptedException {
        String name = "inline_svg";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void inlineNestedSvgTest() throws IOException, InterruptedException {
        String name = "inline_nested_svg";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void inlineSvgExternalFontRelativeTest() throws IOException, InterruptedException {
        String name = "inline_svg_external_font_relative";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void inlineSvgExternalFontUrlTest() throws IOException, InterruptedException {
        // TODO DEVSIX-2264 external font loading in SVG via @import
        String name = "inline_svg_external_font_url";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA),
    })
    public void convert_inline_Svg_path_in_HTML() throws IOException, InterruptedException {
        String name = "HTML_with_inline_svg_path";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA),
    })
    // TODO: Update cmp_ file when DEVSIX-2719 resolved
    public void convert_inline_Svg_polygon_in_HTML() throws IOException, InterruptedException {
        String name = "HTML_with_inline_svg_polygon";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA),
    })
    public void convert_namespace_Svg_in_HTML() throws IOException, InterruptedException {
        String name = "namespace_svg";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void convertInlineSvgCircle() throws IOException, InterruptedException {
      String html = "inline_svg_circle";
      PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + html + ".pdf"));
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
      Assert.assertNull(new CompareTool().compareByContent(destinationFolder + html + ".pdf", sourceFolder + "cmp_" + html + ".pdf", destinationFolder));
    }


    @Test
    public void convertInlineSvgRectangle() throws IOException, InterruptedException {
        String html = "inline_svg_rectangle";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + html + ".pdf"));
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
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + html + ".pdf", sourceFolder + "cmp_" + html + ".pdf", destinationFolder));
    }

    @Test
    public void convertInlineSvgRoundedRectangle() throws IOException, InterruptedException {
        String html = "inline_svg_rounded_rect";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + html + ".pdf"));
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
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + html + ".pdf", sourceFolder + "cmp_" + html + ".pdf", destinationFolder));
    }

    @Test
    // TODO: Update cmp_ file when DEVSIX-2719 resolved
    public void convertInlineSvgStar() throws IOException, InterruptedException {
        String html = "inline_svg_star";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + html + ".pdf"));
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
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + html + ".pdf", sourceFolder + "cmp_" + html + ".pdf", destinationFolder));
    }

    @Test
    public void convertInlineSvgLogo() throws IOException, InterruptedException {
        String html = "inline_svg_logo";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationFolder + html + ".pdf"));
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
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + html + ".pdf", sourceFolder + "cmp_" + html + ".pdf", destinationFolder));
    }

    @Test
    public void externalImageSuccessTest() throws IOException, InterruptedException {
        String name = "external_img";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));

    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalImageNonExistentRefTest() throws IOException, InterruptedException {
        String name = "external_img_nonExistentRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));

    }

    @Test
    //TODO update after DEVSIX-3034
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2)
    })
    public void externalObjectSuccessTest() throws IOException, InterruptedException {
        String name = "external_object";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void externalObjectWithResourceTest() throws IOException, InterruptedException {
        //TODO update after DEVSIX-2239
        String name = "external_object_with_resource";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LayoutLogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA, count = 66),
    })
    public void externalObjectWithGoogleCharts() throws IOException, InterruptedException {
        //TODO update after DEVSIX-2239
        String name = "inlineSvg_googleCharts";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalObjectNonExistentRefTest() throws IOException, InterruptedException {
        String name = "external_objectNonExistentRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    //TODO: Update cmp_ file when DEVSIX-2731 resolved
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void htmlWithSvgBackground() throws IOException, InterruptedException {
        String name = "HTML_with_svg_background";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    //TODO: Update cmp_ file when DEVSIX-2731 resolved
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void htmlWithSvgBackgroundNoViewbox() throws IOException, InterruptedException {
        String name = "Html_with_svg_background_no_viewbox";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH),
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_HEIGHT),
    })
    public void svgWithoutDimensionsTest() throws IOException, InterruptedException {
        String name = "svg_without_dimensions";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void svgWithoutDimensionsWithViewboxTest() throws IOException, InterruptedException {
        String name = "svg_without_dimensions_with_viewbox";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH, count = 2),
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_HEIGHT, count = 2),
    })
    public void svgWithoutDimensionsImageAndObjectRef() throws IOException, InterruptedException {
        String name = "svgWithoutDimensionsImageAndObjectRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

}
