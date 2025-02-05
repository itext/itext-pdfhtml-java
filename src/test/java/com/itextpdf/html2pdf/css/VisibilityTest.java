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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class VisibilityTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/VisibilityTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/VisibilityTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyLastPageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyLastPageTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyTableTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertySvgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertySvgTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyLinkTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyLinkTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyImagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyImagesTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormsTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormFieldTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormFieldTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormRadioButtonTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormRadioButtonTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090
    public void visiblePropertyInFormDropdownListTest() throws IOException, InterruptedException {
        String htmlFile = sourceFolder + "visiblePropertyInFormDropdownListTest.html";
        String outAcroPdf = destinationFolder + "visiblePropertyInFormDropdownListTest.pdf";

        ConverterProperties properties = new ConverterProperties();
        properties.setCreateAcroForm(true);
        HtmlConverter.convertToPdf(new File(htmlFile), new File(outAcroPdf), properties);
        Assertions.assertNull(new CompareTool().compareByContent(outAcroPdf, sourceFolder +
                "cmp_visiblePropertyInFormDropdownListTest.pdf", destinationFolder, "diff_dropdown"));
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormCheckBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormCheckBoxTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyDivTest", sourceFolder, destinationFolder);
    }
}
