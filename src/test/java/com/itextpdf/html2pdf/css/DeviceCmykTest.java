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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
import com.itextpdf.styledxmlparser.css.validate.impl.CssDefaultValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.CssDeviceCmykAwareValidator;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class DeviceCmykTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/DeviceCmykTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/DeviceCmykTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
        CssDeclarationValidationMaster.setValidator(new CssDeviceCmykAwareValidator());
    }

    @AfterClass
    public static void after() {
        CssDeclarationValidationMaster.setValidator(new CssDefaultValidator());
    }

    @Test
    public void bodyBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("bodyBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void spanBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spanBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void divBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("divBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void headerColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void headerBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("headerBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphBgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphBgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void borderColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.LAST_ROW_IS_NOT_COMPLETE, count = 3))
    public void borderShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("borderShorthand", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.ELEMENT_DOES_NOT_FIT_CURRENT_AREA)})
    public void simpleSvgColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleSvgColor", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
