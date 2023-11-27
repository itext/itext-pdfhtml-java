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
package com.itextpdf.html2pdf;

import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static com.itextpdf.html2pdf.HtmlConverterTest.compareAndCheckCompliance;

@RunWith(Parameterized.class)
@Category(IntegrationTest.class)
public class HtmlConverterPdfAParameterizedTest extends ExtendedHtmlConversionITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterPdfAParameterizedTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/HtmlConverterPdfAParameterizedTest/";

    private final String htmlName;
    private final String testName;

    private final PdfAConformanceLevel conformanceLevel;

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    public HtmlConverterPdfAParameterizedTest(Object htmlName, Object testName, Object conformance) {
        this.conformanceLevel = (PdfAConformanceLevel) conformance;
        this.htmlName = (String) htmlName;
        this.testName = (String) testName;
    }

    // TODO DEVSIX-2449 z-index is not supported (zindex.html)
    @Parameterized.Parameters(name = "{1}")
    public static Iterable<Object[]> rotationRelatedProperties() {
        return Arrays.asList(new Object[][]{
                {"images.html", "pdfA4BasicImageTest", PdfAConformanceLevel.PDF_A_4},
                {"imageJpeg2000.html", "pdfA4Jpeg2000Test", PdfAConformanceLevel.PDF_A_4},
                {"basicForms.html", "pdfA4FormsTest", PdfAConformanceLevel.PDF_A_4},
                {"basicTable.html", "pdfA4TableTest", PdfAConformanceLevel.PDF_A_4},
                {"basicOutlines.html", "pdfA4OutlinesTest", PdfAConformanceLevel.PDF_A_4},
                {"opacity.html", "pdfA4OpacityTest", PdfAConformanceLevel.PDF_A_4},
                {"svg.html", "pdfA4SvgTest", PdfAConformanceLevel.PDF_A_4},
                {"float.html", "pdfA4FloatTest", PdfAConformanceLevel.PDF_A_4},
                {"flex.html", "pdfA4FlexTest", PdfAConformanceLevel.PDF_A_4},
                {"list.html", "pdfA4ListTest", PdfAConformanceLevel.PDF_A_4},
                {"borderTransparency.html", "pdfA4BorderTransparencyTest", PdfAConformanceLevel.PDF_A_4},
                {"positionAbsolute.html", "pdfA4PositionAbsoluteTest", PdfAConformanceLevel.PDF_A_4},
                {"positionAbsoluteOpacity.html", "pdfA4PositionAbsoluteOpacityTest", PdfAConformanceLevel.PDF_A_4},
                {"zIndex.html", "pdfA4ZIndexTest", PdfAConformanceLevel.PDF_A_4},

                {"images.html", "pdfA3BasicImageTest", PdfAConformanceLevel.PDF_A_3U},
                {"imageJpeg2000.html", "pdfA3Jpeg2000Test", PdfAConformanceLevel.PDF_A_3U},
                {"basicForms.html", "pdfA3FormsTest", PdfAConformanceLevel.PDF_A_3U},
                {"basicTable.html", "pdfA3TableTest", PdfAConformanceLevel.PDF_A_3U},
                {"basicOutlines.html", "pdfA3OutlinesTest", PdfAConformanceLevel.PDF_A_3U},
                {"opacity.html", "pdfA3Opacity", PdfAConformanceLevel.PDF_A_3U},
                {"svg.html", "pdfA3SvgTest", PdfAConformanceLevel.PDF_A_3U},
                {"float.html", "pdfA3FloatTest", PdfAConformanceLevel.PDF_A_3U},
                {"flex.html", "pdfA3FlexTest", PdfAConformanceLevel.PDF_A_3U},
                {"list.html", "pdfA3ListTest", PdfAConformanceLevel.PDF_A_3U},
                {"borderTransparency.html", "pdfA3BorderTransparencyTest", PdfAConformanceLevel.PDF_A_3U},
                {"positionAbsolute.html", "pdfA3PositionAbsoluteTest", PdfAConformanceLevel.PDF_A_3U},
                {"positionAbsoluteOpacity.html", "pdfA3PositionAbsoluteOpacityTest", PdfAConformanceLevel.PDF_A_3U},
                {"zIndex.html", "pdfA3ZIndexTest", PdfAConformanceLevel.PDF_A_3U},

                {"images.html", "pdfA2BasicImageTest", PdfAConformanceLevel.PDF_A_2B},
                {"imageJpeg2000.html", "pdfA2Jpeg2000Test", PdfAConformanceLevel.PDF_A_2B},
                {"basicForms.html", "pdfA2FormsTest", PdfAConformanceLevel.PDF_A_2B},
                {"basicTable.html", "pdfA2TableTest", PdfAConformanceLevel.PDF_A_2B},
                {"basicOutlines.html", "pdfA2OutlinesTest", PdfAConformanceLevel.PDF_A_2B},
                {"opacity.html", "pdfA2OpacityTest", PdfAConformanceLevel.PDF_A_2B},
                {"svg.html", "pdfA2SvgTest", PdfAConformanceLevel.PDF_A_2B},
                {"float.html", "pdfA2FloatTest", PdfAConformanceLevel.PDF_A_2B},
                {"flex.html", "pdfA2FlexTest", PdfAConformanceLevel.PDF_A_2B},
                {"list.html", "pdfA2ListTest", PdfAConformanceLevel.PDF_A_2B},
                {"borderTransparency.html", "pdfA2BorderTransparencyTest", PdfAConformanceLevel.PDF_A_2B},
                {"positionAbsolute.html", "pdfA2PositionAbsoluteTest", PdfAConformanceLevel.PDF_A_2B},
                {"positionAbsoluteOpacity.html", "pdfA2PositionAbsoluteOpacityTest", PdfAConformanceLevel.PDF_A_2B},
                {"zIndex.html", "pdfA2ZIndexTest", PdfAConformanceLevel.PDF_A_2B},

                {"imageJpeg2000.html", "pdfA1Jpeg2000Test", PdfAConformanceLevel.PDF_A_1B},
                {"basicForms.html", "pdfA1FormsTest", PdfAConformanceLevel.PDF_A_1B},
                {"basicTable.html", "pdfA1TableTest", PdfAConformanceLevel.PDF_A_1B},
                {"basicOutlines.html", "pdfA1OutlinesTest", PdfAConformanceLevel.PDF_A_1B},
                {"svg.html", "pdfA1SvgTest", PdfAConformanceLevel.PDF_A_1B},
                {"float.html", "pdfA1FloatTest", PdfAConformanceLevel.PDF_A_1B},
                {"flex.html", "pdfA1FlexTest", PdfAConformanceLevel.PDF_A_1B},
                {"list.html", "pdfA1ListTest", PdfAConformanceLevel.PDF_A_1B},
                {"positionAbsolute.html", "pdfA1PositionAbsoluteTest", PdfAConformanceLevel.PDF_A_1B},
                {"zIndex.html", "pdfA1ZIndexTest", PdfAConformanceLevel.PDF_A_1B}
        });
    }

    @Test
    public void convertToPdfA4Test() throws IOException, InterruptedException {
        String sourceHtml = SOURCE_FOLDER + htmlName;
        String destinationPdf = DESTINATION_FOLDER + testName + ".pdf";
        String cmpPdf = SOURCE_FOLDER + "cmp_" + testName + ".pdf";
        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);
        converterProperties.setPdfAConformanceLevel(conformanceLevel);
        converterProperties.setDocumentOutputIntent(new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1",
                new FileInputStream(SOURCE_FOLDER + "sRGB Color Space Profile.icm")));
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            HtmlConverter.convertToPdf(fileInputStream, new FileOutputStream(destinationPdf), converterProperties);
        }

        compareAndCheckCompliance(destinationPdf, cmpPdf);
    }
}