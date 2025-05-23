/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.utils.ImageSizeMeasuringListener;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;

@Tag("IntegrationTest")
public class FigureTest extends ExtendedITextTest {
    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/FigureTest/";
    private static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/FigureTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void figureFileDocumentTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hello_figure_file.html"), new File(destinationFolder + "hello_figure_file.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "hello_figure_file.pdf", sourceFolder + "cmp_hello_figure_file.pdf", destinationFolder, "diff01_"));
    }

    @Test
    public void smallFigureTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "smallFigureTest.html"), new File(destinationFolder + "smallFigureTest.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "smallFigureTest.pdf", sourceFolder + "cmp_smallFigureTest.pdf", destinationFolder, "diff03_"));
    }

    @Test
    public void figureInSpanTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "figureInSpan.html"), new File(destinationFolder + "figureInSpan.pdf"));
        Assertions.assertNull(new CompareTool().compareByContent(destinationFolder + "figureInSpan.pdf", sourceFolder + "cmp_figureInSpan.pdf", destinationFolder, "diff04_"));
    }

    @Test
    public void checkImageRemainsUncutWithFigureTagTest() throws IOException {
        File pdfFile = new File(destinationFolder + "imageInFigure.pdf");
        HtmlConverter.convertToPdf(new File(sourceFolder + "imageInFigure.html"), pdfFile);
        try (PdfDocument doc = new PdfDocument(new PdfReader(pdfFile))) {
            final int pageNr = 2;
            PdfImageXObject image = doc.getPage(pageNr).getResources().getImage(new PdfName("Im1"));
            Assertions.assertNotNull(image);
            ImageSizeMeasuringListener listener = new ImageSizeMeasuringListener(pageNr);
            PdfCanvasProcessor processor = new PdfCanvasProcessor(listener);
            processor.processPageContent(doc.getPage(pageNr));
            boolean isImageCropped = listener.bbox.getY() < 0;
            Assertions.assertFalse(isImageCropped);
        }
    }
}
