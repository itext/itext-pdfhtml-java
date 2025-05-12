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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IntegrationTest")
public class ATagWorkerTest extends ExtendedHtmlConversionITextTest {

    private static final String DESTINATION_FOLDER =  "./target/test/com/itextpdf/html2pdf/ATagWorkerTest/";
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/ATagWorkerTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void aTagHrefRelativeLinkTest() throws IOException, InterruptedException {

        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri("https://not_existing_url.com/");
        PdfWriter writer = new PdfWriter(DESTINATION_FOLDER + "aTagHrefRelativeLink.pdf");
        PdfDocument pdf = new PdfDocument(writer);

        HtmlConverter.convertToPdf(new FileInputStream(SOURCE_FOLDER + "aTagHrefRelativeLink.html"), pdf, properties);
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "aTagHrefRelativeLink.pdf", SOURCE_FOLDER + "cmp_aTagHrefRelativeLink.pdf", DESTINATION_FOLDER, "diff12_"));
    }

    @Test
    public void aTagHrefAbsoluteLinkTest() throws IOException, InterruptedException {

        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri("https://not_existing_url.com/");
        PdfWriter writer = new PdfWriter(DESTINATION_FOLDER + "aTagHrefAbsoluteLink.pdf");
        PdfDocument pdf = new PdfDocument(writer);

        HtmlConverter.convertToPdf(new FileInputStream(SOURCE_FOLDER + "aTagHrefAbsoluteLink.html"), pdf, properties);
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "aTagHrefAbsoluteLink.pdf", SOURCE_FOLDER + "cmp_aTagHrefAbsoluteLink.pdf", DESTINATION_FOLDER, "diff12_"));
    }

    @Test
    public void aTagHrefLocalFileLinkTest() throws IOException, InterruptedException {

        ConverterProperties properties = new ConverterProperties();
        PdfWriter writer = new PdfWriter(DESTINATION_FOLDER + "aTagHrefLocalFileLink.pdf");
        PdfDocument pdf = new PdfDocument(writer);

        HtmlConverter.convertToPdf(new FileInputStream(SOURCE_FOLDER + "aTagHrefLocalFileLink.html"), pdf, properties);
        Assertions.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "aTagHrefLocalFileLink.pdf", SOURCE_FOLDER + "cmp_aTagHrefLocalFileLink.pdf", DESTINATION_FOLDER, "diff12_"));
    }
}
