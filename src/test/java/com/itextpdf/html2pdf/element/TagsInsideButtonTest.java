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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.xml.sax.SAXException;

@Tag("IntegrationTest")
public class TagsInsideButtonTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/TagsInsideButtonTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/TagsInsideButtonTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void buttonWithImageInside()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithImageInside",
                sourceFolder, destinationFolder, false);
    }

    @Test
    public void buttonWithImageInsideTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithImageInside",
                sourceFolder, destinationFolder, true);
    }

    @Test
    public void buttonWithPInside()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithPInside",
                sourceFolder, destinationFolder, false);
    }

    @Test
    public void imageNotFinishedTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("imageNotFinishedTagged", sourceFolder,
                destinationFolder, true);
    }

    @Test
    public void buttonWithPInsideTagged()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonWithPInside", sourceFolder,
                destinationFolder, true);
    }

    @Test
    public void buttonInsideMoreThanTwoAreas()
            throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        convertToPdfAcroformFlattenAndCompare("buttonInsideMoreThanTwoAreas", sourceFolder,
                destinationFolder, true);
    }
}
