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

import com.itextpdf.commons.utils.FileUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.logs.LayoutLogMessageConstant;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.element.SvgImage;
import com.itextpdf.svg.logs.SvgLogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

    @Test
    // TODO DEVSIX-4629 pdfHTML: simulate browsers behavior for quirks and standards modes
    public void inline_svg_percent_sizes_quirk_mode_1Test() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_percent_sizes_quirk_mode_1");
    }

    @Test
    // TODO DEVSIX-4629 pdfHTML: simulate browsers behavior for quirks and standards modes
    public void inline_svg_percent_sizes_quirk_mode_2Test() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_percent_sizes_quirk_mode_2");
    }

    @Test
    // TODO DEVSIX-4629 pdfHTML: simulate browsers behavior for quirks and standards modes
    public void inline_svg_percent_sizes_quirk_mode_3Test() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_percent_sizes_quirk_mode_3");
    }

    @Test
    public void inline_svg_percent_sizes_standard_mode_1Test() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_percent_sizes_standard_mode_1");
    }

    @Test
    public void inline_svg_percent_sizes_standard_mode_2Test() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_percent_sizes_standard_mode_2");
    }

    @Test
    public void inline_svg_percent_sizes_standard_mode_3Test() throws IOException, InterruptedException {
        convertAndCompare("inline_svg_percent_sizes_standard_mode_3");
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
    public void convert_inline_Svg_path_in_HTML() throws IOException, InterruptedException {
        convertAndCompare("HTML_with_inline_svg_path");
    }

    @Test
    // TODO: DEVSIX-2719 SVG<polygon>: tops of figures are cut
    public void convert_inline_Svg_polygon_in_HTML() throws IOException, InterruptedException {
        convertAndCompare("HTML_with_inline_svg_polygon");
    }

    @Test
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
    // TODO: DEVSIX-2719 SVG<polygon>: tops of figures are cut
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
    public void convertInlineSvgWithCustomFont() throws IOException, InterruptedException {
        String html = "inline_svg_custom_font";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DESTINATION_FOLDER + html + ".pdf"));
        pdfDoc.addNewPage();
        String string_file = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <body>\n" +
                "    <svg viewBox=\"0 0 240 80\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "      <text x=\"20\" y=\"35\" font-family=\"Noto Sans Mono\" font-size=\"1.5em\">Hello World!</text>\n" +
                "    </svg>\n" +
                "  </body>\n" +
                "</html>\n";
        HtmlConverter.convertToPdf(string_file, pdfDoc, new ConverterProperties());
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + html + ".pdf", SOURCE_FOLDER + "cmp_" + html + ".pdf", DESTINATION_FOLDER));
    }

    @Test
    public void convertSvgWithSvgConverterCustomFont() throws IOException, InterruptedException {
        String filename = "svg_custom_font";
        String svg = "<svg viewBox=\"0 0 240 80\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "<text x=\"20\" y=\"35\" font-family=\"Noto Sans Mono\" font-size=\"1.5em\">Hello World!</text>\n" +
                "</svg>\n";
        Document document = new Document(new PdfDocument(new PdfWriter(DESTINATION_FOLDER + filename + ".pdf")));
        Image image = SvgConverter.convertToImage(new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8)),
                document.getPdfDocument());
        document.add(image);
        document.close();
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + filename + ".pdf", SOURCE_FOLDER + "cmp_" + filename + ".pdf", DESTINATION_FOLDER));
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
    //TODO DEVSIX-3034 Moving the end tag of <object> causes ERROR
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
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void htmlWithSvgBackgroundTest() throws IOException, InterruptedException {
        // The diff with browser is expected, because we parse "width: 600;", when
        // browsers ignore such declaration. See htmlWithSvgBackground3Test
        convertAndCompare("HTML_with_svg_background");
    }

    @Test
    public void htmlWithSvgBackground2Test() throws IOException, InterruptedException {
        convertAndCompare("HTML_with_svg_background_2");
    }

    @Test
    public void htmlWithSvgBackground3Test() throws IOException, InterruptedException {
        convertAndCompare("HTML_with_svg_background_3");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void htmlWithSvgBackgroundNoViewbox() throws IOException, InterruptedException {
        // The diff with browser is expected, because we parse "width: 600;", when
        // browsers ignore such declaration. See htmlWithSvgBackgroundNoViewbox2
        convertAndCompare("Html_with_svg_background_no_viewbox");
    }

    @Test
    public void htmlWithSvgBackgroundNoViewbox2() throws IOException, InterruptedException {
        convertAndCompare("Html_with_svg_background_no_viewbox_2");
    }

    @Test
    public void svgWithoutDimensionsTest() throws IOException, InterruptedException {
        convertAndCompare("svg_without_dimensions");
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)
    })
    public void svgWithoutDimensionsWithViewboxTest() throws IOException, InterruptedException {
        convertAndCompare("svg_without_dimensions_with_viewbox");
    }

    @Test
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
    public void svgWithEmRemTest() throws IOException, InterruptedException {
        convertAndCompare("svgWithEmRem");
    }

    @Test
    // TODO DEVSIX-1316 make percent width doesn't affect elements min max width
    public void svgRelativeDimenstionsInInlineBlockTest() throws IOException, InterruptedException {
        convertAndCompare("svgRelativeDimenstionsInInlineBlockTest");
    }

    @Test
    // TODO DEVSIX-1316 make percent width doesn't affect elements min max width
    // TODO DEVSIX-7003 Problem with layouting image with relative size in the table
    public void svgRelativeDimenstionsInTableTest() throws IOException, InterruptedException {
        convertAndCompare("svgRelativeDimenstionsInTableTest");
    }

    @Test
    public void svgWidthHeightPercentUnitsTest() throws IOException, InterruptedException {
        convertAndCompare("svgWidthHeightPercentUnitsTest");
    }

    @Test
    public void svgSimpleWidthHeightPercentUnitsTest() throws IOException, InterruptedException {
        convertAndCompare("svgSimpleWidthHeightPercentUnitsTest");
    }

    @Test
    public void svgToElementsSimpleWidthHeightPercentUnitsTest() throws IOException, InterruptedException {
        convertToElementsAndCompare("svgSimpleWidthHeightPercentUnitsTest");
    }

    @Test
    public void svgToLayoutWidthHeightPercentTest() throws IOException, InterruptedException {
        String name = "svgToLayoutWidthHeightPercentTest";
        List<IElement> elements = HtmlConverter.convertToElements(FileUtil.getInputStreamForFile(SOURCE_FOLDER + name + ".html"));
        Document document = new Document(new PdfDocument(new PdfWriter(DESTINATION_FOLDER + name + ".pdf")));
        SvgImage svgImage = (SvgImage) ((BlockElement<Div>) elements.get(0)).getChildren().get(0);

        Div div = new Div();
        div.setWidth(100);
        div.setHeight(300);
        div.setBorder(new SolidBorder(5));
        div.add(svgImage);

        document.add(div);
        document.close();

        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + name + ".pdf", SOURCE_FOLDER + "cmp_" + name + ".pdf",
                DESTINATION_FOLDER, "diff_" + name + "_"));
    }

    @Test
    public void svgNestedWidthHeightPercentUnitsTest() throws IOException, InterruptedException {
        convertAndCompare("svgNestedWidthHeightPercentUnitsTest");
    }

    @Test
    public void relativeSvgParentWithoutWidthAndHeightTest() throws IOException, InterruptedException {
        convertAndCompare("relativeSvgParentWithoutWidthAndHeight");
    }

    @Test
    public void relativeSvgParentIsBoundedTest() throws IOException, InterruptedException {
        convertAndCompare("relativeSvgParentIsBounded");
    }

    @Test
    public void relativeSvgParentWithWidthTest() throws IOException, InterruptedException {
        convertAndCompare("relativeSvgParentWithWidth");
    }

    @Test
    public void relativeSvgParentWithHeightTest() throws IOException, InterruptedException {
        convertAndCompare("relativeSvgParentWithHeight");
    }

    @Test
    public void relativeSvgParentWithWidthViewboxTest() throws IOException, InterruptedException {
        convertAndCompare("relativeSvgParentWithWidthViewbox");
    }

    @Test
    public void relativeSvgParentWithHeightViewboxTest() throws IOException, InterruptedException {
        convertAndCompare("relativeSvgParentWithHeightViewbox");
    }

    @Test
    public void relativeSvgDifferentGrandparentTest() throws IOException, InterruptedException {
        convertAndCompare("relativeSvgDifferentGrandparent");
    }

    private static void convertAndCompare(String name)
            throws IOException, InterruptedException {
        String htmlPath = SOURCE_FOLDER + name + ".html";
        HtmlConverter.convertToPdf(new File(htmlPath), new File(DESTINATION_FOLDER + name + ".pdf"));
        System.out.println("Input html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");
        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + name + ".pdf", SOURCE_FOLDER + "cmp_" + name + ".pdf",
                DESTINATION_FOLDER, "diff_" + name + "_"));
    }

    private static void convertToElementsAndCompare(String name) throws IOException, InterruptedException {
        List<IElement> elements = HtmlConverter.convertToElements(FileUtil.getInputStreamForFile(SOURCE_FOLDER + name + ".html"));
        Document document = new Document(new PdfDocument(new PdfWriter(DESTINATION_FOLDER + name + ".pdf")));
        for (IElement elem : elements) {
            if (elem instanceof IBlockElement) {
                document.add((IBlockElement) elem);
            } else if (elem instanceof Image) {
                document.add((Image) elem);
            } else if (elem instanceof AreaBreak) {
                document.add((AreaBreak) elem);
            } else {
                Assertions.fail("The #convertToElements method gave element which is unsupported as root element, it's unexpected.");
            }
        }
        document.close();

        Assertions.assertNull(new CompareTool().compareByContent(
                DESTINATION_FOLDER + name + ".pdf", SOURCE_FOLDER + "cmp_" + name + ".pdf",
                DESTINATION_FOLDER, "diff_" + name + "_"));
    }
}
