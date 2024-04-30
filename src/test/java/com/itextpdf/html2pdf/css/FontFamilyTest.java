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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class FontFamilyTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FontFamilyTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FontFamilyTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }
    @Test
    public void hugeFontFamilyForDosAttackTest() throws IOException, InterruptedException {
        String htmlPath = SOURCE_FOLDER + "hugeFontFamilyForDosAttackTest.html";
        String pdfPath = DESTINATION_FOLDER + "hugeFontFamilyForDosAttackTest.pdf";
        String cmpPdfPath = SOURCE_FOLDER + "cmp_hugeFontFamilyForDosAttackTest.pdf";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath));
        Assertions.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, DESTINATION_FOLDER, "diff_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_FONT,
                    count = 1)
    })
    public void selectFontFromTTCTest() throws IOException, InterruptedException {
        String htmlPath = SOURCE_FOLDER + "selectFontInGroup.html";
        String pdfPath = DESTINATION_FOLDER + "selectFontInGroup.pdf";
        String cmpPdfPath = SOURCE_FOLDER + "cmp_selectFontInGroup.pdf";

        HtmlConverter.convertToPdf(new File(htmlPath), new File(pdfPath));
        //TODO DEVSIX-1104: Change cmp file after supporting ttc#id when selecting font from ttc
        //Currently it will look for a font file where #{id} is part of the font path.
        Assertions.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, DESTINATION_FOLDER, "diff_"));
    }
}
