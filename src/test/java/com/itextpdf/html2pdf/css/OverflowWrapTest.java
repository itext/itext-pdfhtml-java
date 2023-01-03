/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
    Authors: iText Software.

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
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.OverflowWrapPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OverflowWrapTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/OverflowWrapTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/OverflowWrapTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void overflowWrapColoredBackgroundTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowWrapColoredBackground.html"),
                new File(destinationFolder + "overflowWrapColoredBackground.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "overflowWrapColoredBackground.pdf",
                sourceFolder + "cmp_overflowWrapColoredBackground.pdf", destinationFolder));
    }

    @Test
    public void overflowXOverflowWrapTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowXOverflowWrap.html"),
                new File(destinationFolder + "overflowXOverflowWrap.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "overflowXOverflowWrap.pdf",
                sourceFolder + "cmp_overflowXOverflowWrap.pdf", destinationFolder));
    }

    @Test
    public void whiteSpaceAndOverflowWrapTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "whiteSpaceAndOverflowWrap.html"),
                new File(destinationFolder + "whiteSpaceAndOverflowWrap.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "whiteSpaceAndOverflowWrap.pdf",
                sourceFolder + "cmp_whiteSpaceAndOverflowWrap.pdf", destinationFolder));
    }

    @Test
    public void whiteSpaceOnParentAndOverflowWrapOnChildTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "whiteSpaceOnParentAndOverflowWrapOnChildTest.html"),
                new File(destinationFolder + "whiteSpaceOnParentAndOverflowWrapOnChildTest.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "whiteSpaceOnParentAndOverflowWrapOnChildTest.pdf",
                sourceFolder + "cmp_whiteSpaceOnParentAndOverflowWrapOnChildTest.pdf", destinationFolder));
    }

    @Test
    // TODO DEVSIX-2482
    public void overflowWrapAndFloatTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowWrapAndFloat.html"),
                new File(destinationFolder + "overflowWrapAndFloat.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "overflowWrapAndFloat.pdf",
                        sourceFolder + "cmp_overflowWrapAndFloat.pdf", destinationFolder));
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = IoLogMessageConstant
            .TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH, count = 2)})
    public void overflowWrapTableScenarioTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowWrapTableScenario.html"),
                new File(destinationFolder + "overflowWrapTableScenario.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "overflowWrapTableScenario.pdf",
                        sourceFolder + "cmp_overflowWrapTableScenario.pdf", destinationFolder));
    }

    @Test
    public void overflowWrapWordWrapInheritance() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "overflowWrapWordWrapInheritance.html"),
                new File(destinationFolder + "overflowWrapWordWrapInheritance.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "overflowWrapWordWrapInheritance.pdf",
                        sourceFolder + "cmp_overflowWrapWordWrapInheritance.pdf", destinationFolder));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void chosenOverflowWrapValue01() throws IOException {
        List<IElement> elements = convertToElements("chosenOverflowWrapValue01");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertNotNull(paragraph);

        OverflowWrapPropertyValue overflowWrapPropertyValue = paragraph
                .<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP);
        Assert.assertNotNull(overflowWrapPropertyValue);
        Assert.assertEquals(OverflowWrapPropertyValue.BREAK_WORD, overflowWrapPropertyValue);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, count = 2)
    })
    public void chosenOverflowWrapValue02() throws IOException {
        List<IElement> elements = convertToElements("chosenOverflowWrapValue02");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertNotNull(paragraph);

        OverflowWrapPropertyValue overflowWrapPropertyValue = paragraph
                .<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP);
        Assert.assertNotNull(overflowWrapPropertyValue);
        Assert.assertEquals(OverflowWrapPropertyValue.NORMAL, overflowWrapPropertyValue);
    }

    @Test
    public void chosenOverflowWrapValue03() throws IOException {
        List<IElement> elements = convertToElements("chosenOverflowWrapValue03");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertNotNull(paragraph);

        OverflowWrapPropertyValue overflowWrapPropertyValue = paragraph
                .<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP);
        Assert.assertNotNull(overflowWrapPropertyValue);
        Assert.assertEquals(OverflowWrapPropertyValue.NORMAL, overflowWrapPropertyValue);
    }

    @Test
    public void chosenOverflowWrapValueUnset01() throws IOException {
        List<IElement> elements = convertToElements("chosenOverflowWrapValueUnset01");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertEquals(OverflowWrapPropertyValue.BREAK_WORD,
                paragraph.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));

        Div divAndNestedParagraph = (Div) elements.get(1);
        Assert.assertEquals(OverflowWrapPropertyValue.ANYWHERE,
                divAndNestedParagraph.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));

        paragraph = (Paragraph) divAndNestedParagraph.getChildren().get(0);
        Assert.assertNull(paragraph.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));

        // todo DEVSIX-4723 replace assertNull above with the commented lines below
//        Assert.assertEquals(OverflowWrapPropertyValue.ANYWHERE,
//                paragraph.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));
    }

    @Test
    public void chosenOverflowWrapValueUnset02() throws IOException {
        List<IElement> elements = convertToElements("chosenOverflowWrapValueUnset02");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertEquals(OverflowWrapPropertyValue.NORMAL,
                paragraph.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)
    })
    public void overflowWrapWordWrapInheritanceAndInvalidValues() throws IOException {
        List<IElement> elements = convertToElements("overflowWrapWordWrapInheritanceAndInvalidValues");

        Div div = (Div) elements.get(0);
        Assert.assertEquals(OverflowWrapPropertyValue.ANYWHERE,
                div.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));

        List<IElement> children = div.getChildren();
        Assert.assertEquals(2, children.size());

        Paragraph firstChild = (Paragraph) children.get(0);
        Assert.assertNull(firstChild.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));

        Paragraph secondChild = (Paragraph) children.get(1);
        Assert.assertEquals(OverflowWrapPropertyValue.BREAK_WORD,
                secondChild.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));

        List<IElement> innerChildren = secondChild.getChildren();
        Assert.assertEquals(2, children.size());
        Text innerChild = (Text) innerChildren.get(1);
        Assert.assertEquals(OverflowWrapPropertyValue.BREAK_WORD,
                innerChild.<OverflowWrapPropertyValue>getProperty(Property.OVERFLOW_WRAP));
    }

    private List<IElement> convertToElements(String name) throws IOException {
        String sourceHtml = sourceFolder + name + ".html";
        return HtmlConverter.convertToElements(new FileInputStream(sourceHtml));
    }
}
