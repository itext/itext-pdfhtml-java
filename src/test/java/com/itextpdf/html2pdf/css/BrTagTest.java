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
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.test.ExtendedITextTest;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

/**
 * This test handles the case where a br tag causes the pdf to no longer be pdf/A compliant
 * The underlying problem turns out to be that the inserted Text IElement has no font, and uses the default (Helvetica) font.
 * The font does not get embedded, and as such, it breaks the compliancy.
 */
@Tag("IntegrationTest")
public class BrTagTest extends ExtendedITextTest {

    @BeforeAll
    public static void beforeClass() {
    }

    @Test
    public void test() {
        String input = "<html>\n" +
                "<head><title>Test</title></head>" +
                "<body style=\"font-family: FreeSans;\">" +
                "<h1>Test</h1>" +
                "<br />" +
                "<p>Hello World</p>" +
                "</body>" +
                "</html>";

        List<IElement> elements = HtmlConverter.convertToElements(input);
        Assertions.assertEquals(3, elements.size());
        Assertions.assertTrue(elements.get(1) instanceof Paragraph);
        Assertions.assertEquals(1, ((Paragraph)elements.get(1)).getChildren().size());
        IElement iElement = ((Paragraph) elements.get(1)).getChildren().get(0);
        Assertions.assertArrayEquals(new String[] {"freesans"}, iElement.<String[]>getProperty(Property.FONT));
    }
}
