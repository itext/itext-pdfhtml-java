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
package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.impl.DefaultHtmlProcessor;
import com.itextpdf.kernel.exceptions.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.styledxmlparser.IXmlParser;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupHtmlParser;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.ExtendedITextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

@Tag("IntegrationTest")
public class ProcessorContextTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/ProcessorContextTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/ProcessorContextTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void doNotResetFontProviderTest() throws IOException {
        Assertions.assertThrows(PdfException.class, () -> {
            FileInputStream fileInputStream = new FileInputStream(sourceFolder + "justHelloWorld.html");

            IXmlParser parser = new JsoupHtmlParser();

            IDocumentNode documentNode = parser.parse(fileInputStream, null);

            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(new BasicFontProvider(false, true, false) {
                @Override
                public void reset() {
                    // Do nothing here. That should result in an exception.
                }
            });

            IHtmlProcessor processor = new DefaultHtmlProcessor(converterProperties);
            Document doc1 = processor.processDocument(documentNode, new PdfDocument(new PdfWriter(new ByteArrayOutputStream())));
            doc1.close();
            Document doc2 = processor.processDocument(documentNode, new PdfDocument(new PdfWriter(new ByteArrayOutputStream())));
            doc2.close();

            Assertions.assertTrue(false, "The test should have failed before that assert, since it's strictly forbidden not to reset the FontProvider instance after each html to pdf conversion.");
        });

    }
}
