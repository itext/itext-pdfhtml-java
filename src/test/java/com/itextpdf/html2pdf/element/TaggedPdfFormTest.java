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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.exceptions.KernelExceptionMessageConstant;
import com.itextpdf.kernel.exceptions.PdfException;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.xml.sax.SAXException;

@Tag("IntegrationTest")
public class TaggedPdfFormTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TaggedPdfFormTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TaggedPdfFormTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void simpleTextFieldTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("simpleTextField", sourceFolder, destinationFolder, true);
    }

    @Test
    public void simpleTextareaTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("simpleTextarea", sourceFolder, destinationFolder, true);
    }

    @Test
    public void simpleButtonTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("simpleButton", sourceFolder, destinationFolder, true);
    }

    @Test
    public void simpleLabelTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("simpleLabel", sourceFolder, destinationFolder, true);
    }

    @Test
    public void simpleCheckboxTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("simpleCheckbox", sourceFolder, destinationFolder, true);
    }

    @Test
    public void simpleSelectTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("simpleSelect", sourceFolder, destinationFolder, true);
    }

    @Test
    public void listBoxSelectTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("listBoxSelect", sourceFolder, destinationFolder, true);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.OPTGROUP_NOT_SUPPORTED_IN_INTERACTIVE_SELECT,
                    count = 2)
    })
    public void listBoxOptGroupSelectTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("listBoxOptGroupSelect", sourceFolder,
                destinationFolder, true);
    }

    @Test
    public void simpleRadioFormTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("simpleRadioForm", sourceFolder, destinationFolder, true);
    }

    @Test
    @Disabled("DEVSIX-980. DefaultHtmlProcessor ERROR No worker found for tag datalist")
    public void dataListFormTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("dataListForm", sourceFolder, destinationFolder, true);
    }

    @Test
    public void fieldSetFormTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("fieldSetForm", sourceFolder, destinationFolder, true);
    }

    @Test
    @Disabled("DEVSIX-4601 exception is thrown on \"convert tagged PDF with acroform\" stage")
    public void inputFormPrematureFlush() {
        Exception exception = Assertions.assertThrows(PdfException.class,
                () -> convertToPdfAcroformFlattenAndCompare("inputFormPrematureFlush",
                        sourceFolder, destinationFolder, true));
        Assertions.assertEquals(KernelExceptionMessageConstant.TAG_STRUCTURE_FLUSHING_FAILED_IT_MIGHT_BE_CORRUPTED,
                exception);
    }
}
