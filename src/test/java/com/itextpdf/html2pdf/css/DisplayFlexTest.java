/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlPageBreak;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.TextArea;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.FlexContainerRenderer;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(IntegrationTest.class)
public class DisplayFlexTest extends ExtendedHtmlConversionITextTest {

    private static final float EPS = 1e-6f;
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/DisplayFlexTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/DisplayFlexTest/";

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void displayFlexCommonTest() throws IOException {
        String name = "displayFlexCommon";
        List<IElement> elements = convertToElements(name);
        IElement flexContainer = elements.get(0);

        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        List<IElement> flexContainerChildren = ((Div) flexContainer).getChildren();
        Assert.assertEquals(11, flexContainerChildren.size());

        IElement element0 = flexContainerChildren.get(0);
        assertDiv(element0, "block");

        IElement element1 = flexContainerChildren.get(1);
        assertDiv(element1, "div with display inline");

        IElement element2 = flexContainerChildren.get(2);
        assertDiv(element2, "float");

        IElement element3 = flexContainerChildren.get(3);
        assertDiv(element3, "anonymous item");

        IElement element4 = flexContainerChildren.get(4);
        assertDiv(element4, "span");

        IElement element5 = flexContainerChildren.get(5);
        Assert.assertTrue(element5 instanceof Image);

        IElement element6 = flexContainerChildren.get(6);
        Assert.assertTrue(element6 instanceof Div);
        Assert.assertEquals(1, ((Div) element6).getChildren().size());
        Assert.assertTrue(((Div) element6).getChildren().get(0) instanceof Paragraph);
        Assert.assertEquals(3, ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().size());
        Assert.assertTrue(((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(0) instanceof Text);
        Assert.assertTrue(((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(1) instanceof Text);
        Assert.assertTrue(((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(2) instanceof Text);
        Assert.assertEquals("text with",
                ((Text) ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(0)).getText());
        Assert.assertEquals("\n",
                ((Text) ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(1)).getText());
        Assert.assertEquals("br tag",
                ((Text) ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(2)).getText());

        IElement element7 = flexContainerChildren.get(7);
        Assert.assertTrue(element7 instanceof Image);

        IElement element8 = flexContainerChildren.get(8);
        assertDiv(element8, "div with page break");

        IElement element9 = flexContainerChildren.get(9);
        Assert.assertTrue(element9 instanceof HtmlPageBreak);

        IElement element10 = flexContainerChildren.get(10);
        Assert.assertTrue(element10 instanceof TextArea);
    }

    @Test
    public void nestedDivTest() throws IOException {
        String name = "nestedDiv";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertEquals(1, ((Div) flexContainer).getChildren().size());

        IElement element = ((Div) flexContainer).getChildren().get(0);
        Assert.assertTrue(element instanceof Div);
        Assert.assertEquals(3, ((Div) element).getChildren().size());
        Assert.assertTrue(((Div) element).getChildren().get(0) instanceof Paragraph);
        Assert.assertTrue(((Div) element).getChildren().get(1) instanceof Div);
        Assert.assertTrue(((Div) element).getChildren().get(2) instanceof Paragraph);
    }

    @Test
    public void flexItemWhiteSpacePreTest() throws IOException {
        String name = "flexItemWhiteSpacePre";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertEquals(1, ((Div) flexContainer).getChildren().size());

        IElement element = ((Div) flexContainer).getChildren().get(0);
        assertDiv(element, "\u200Dthe best   world");
    }

    @Test
    public void anonymousBlockInTheEndTest() throws IOException {
        String name = "anonymousBlockInTheEnd";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertEquals(2, ((Div) flexContainer).getChildren().size());

        Assert.assertTrue(((Div) flexContainer).getChildren().get(0) instanceof Div);

        IElement element = ((Div) flexContainer).getChildren().get(1);
        assertDiv(element, "anonymous block");
    }

    @Test
    public void brTagTest() throws IOException {
        String name = "brTag";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertEquals(1, ((Div) flexContainer).getChildren().size());
        IElement element = ((Div) flexContainer).getChildren().get(0);
        assertDiv(element, "hello");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET))
    //TODO DEVSIX-5086 change this test when working on the ticket
    public void flexWrapTest() throws IOException {
        String name = "flexWrap";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertFalse(flexContainer.hasProperty(Property.FLEX_WRAP));
    }

    @Test
    //TODO DEVSIX-5087 remove this test when working on the ticket
    public void overflowAtFlexContainerTest() throws IOException {
        String name = "overflowAtFlexContainer";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertFalse(flexContainer.hasProperty(Property.OVERFLOW_X));
        Assert.assertFalse(flexContainer.hasProperty(Property.OVERFLOW_Y));
    }

    @Test
    public void collapsingMarginsAtFlexContainerTest() throws IOException {
        String name = "collapsingMarginsAtFlexContainer";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertTrue(flexContainer.hasProperty(Property.COLLAPSING_MARGINS));
    }

    @Test
    public void overflowAtFlexItemTest() throws IOException {
        String name = "overflowAtFlexItem";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertEquals(1, ((Div) flexContainer).getChildren().size());

        Assert.assertTrue(((Div) flexContainer).getChildren().get(0).hasProperty(Property.OVERFLOW_X));
        Assert.assertTrue(((Div) flexContainer).getChildren().get(0).hasProperty(Property.OVERFLOW_Y));
    }

    @Test
    public void displayFlexSpanContainerTest() throws IOException {
        String name = "displayFlexSpanContainer";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
    }

    @Test
    //TODO DEVSIX-5087 remove this test when working on the ticket
    public void tempDisablePropertiesTest() throws IOException {
        List<IElement> elements = convertToElements("tempDisableProperties");
        Assert.assertEquals(1, elements.size());
        Assert.assertTrue(elements.get(0).getRenderer() instanceof FlexContainerRenderer);

        Assert.assertFalse(elements.get(0).hasProperty(Property.OVERFLOW_X));
        Assert.assertFalse(elements.get(0).hasProperty(Property.OVERFLOW_Y));
        Assert.assertFalse(elements.get(0).hasProperty(Property.FLOAT));
        Assert.assertFalse(elements.get(0).hasProperty(Property.CLEAR));
    }

    @Test
    public void disableFlexItemPropertiesTest() throws IOException {
        List<IElement> elements = convertToElements("disableFlexItemProperties");
        IElement flexItem = ((Div) elements.get(0)).getChildren().get(0);
        Assert.assertFalse(flexItem.hasProperty(Property.FLOAT));
        Assert.assertFalse(flexItem.hasProperty(Property.CLEAR));
        Assert.assertFalse(flexItem.hasProperty(Property.VERTICAL_ALIGNMENT));
    }

    @Test
    public void flexItemPropertiesTest() throws IOException {
        String name = "flexItemProperties";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertEquals(1, ((Div) flexContainer).getChildren().size());
        IElement flexItem = ((Div) flexContainer).getChildren().get(0);
        Float flexGrow = flexItem.<Float>getProperty(Property.FLEX_GROW);
        Float flexShrink = flexItem.<Float>getProperty(Property.FLEX_SHRINK);
        Assert.assertEquals(2f, (float) flexGrow, EPS);
        Assert.assertEquals(3f, (float) flexShrink, EPS);
        Assert.assertEquals(UnitValue.createPointValue(200.338f),
                flexItem.<UnitValue>getProperty(Property.FLEX_BASIS));
    }

    @Test
    public void flexGrowTest() throws IOException, InterruptedException {
        String name = "flexGrow";
        convertToPdfAndCompare(name, SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexShrinkTest() throws IOException, InterruptedException {
        String name = "flexShrink";
        convertToPdfAndCompare(name, SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexBasisAuto() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisAuto", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-5091 change cmp file when working on the thicket
    public void flexBasisContentMaxWidth() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexBasisContentMaxWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void nestedFlexContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("nestedFlexContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexJustifyContentAlignItemsFlexStartTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexJustifyContentAlignItemsFlexStart", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexJustifyContentAlignItemsFlexEndTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexJustifyContentAlignItemsFlexEnd", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexJustifyContentAlignItemsCenterTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexJustifyContentAlignItemsCenter", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexAlignItemsStretchTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexAlignItemsStretch", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void checkboxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("checkbox", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemContentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemContent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemMinWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemMinWidth", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemEmptyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemEmpty", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemEmptyFlexBasisTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemEmptyFlexBasis", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemsContentHeightBiggerThanContainersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemsContentHeightBiggerThanContainers", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexItemsOccupyByWidthMoreThanContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexItemsOccupyByWidthMoreThanContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexEndOnFlexItemResultsInTopBeingOverflownTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexEndOnFlexItemResultsInTopBeingOverflown", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void paragraphAndDivItemsOverflowBottomTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphAndDivItemsOverflowBottom", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void smallHeightAndBigMaxHeightOnContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallHeightAndBigMaxHeightOnContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void smallHeightAndBigMaxHeightOnContainerAnonymousFlexItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("smallHeightAndBigMaxHeightOnContainerAnonymousFlexItem",
                SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void marginsCollapseFlexContainerAndFlexItemStretchTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("marginsCollapseFlexContainerAndFlexItemStretch", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void marginsCollapseFlexContainerAndSiblingsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("marginsCollapseFlexContainerAndSiblings", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.CLIP_ELEMENT))
    public void marginsCollapseFlexContainerAndParentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("marginsCollapseFlexContainerAndParent", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void marginsCollapseInsideFlexContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("marginsCollapseInsideFlexContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void marginsCollapseFlexContainerAndItsChildTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("marginsCollapseFlexContainerAndItsChild", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    // TODO DEVSIX-5196 Support collapsing margins for flex item's children
    public void marginsCollapseInsideFlexItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("marginsCollapseInsideFlexItem", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    private static void assertDiv(IElement element, String text) {
        Assert.assertTrue(element instanceof Div);
        Assert.assertEquals(1, ((Div) element).getChildren().size());
        Assert.assertTrue(((Div) element).getChildren().get(0) instanceof Paragraph);
        Assert.assertEquals(1, ((Paragraph) ((Div) element).getChildren().get(0)).getChildren().size());
        Assert.assertTrue(((Paragraph) ((Div) element).getChildren().get(0)).getChildren().get(0) instanceof Text);
        Assert.assertEquals(text,
                ((Text) ((Paragraph) ((Div) element).getChildren().get(0)).getChildren().get(0)).getText());
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE)
    })
    public void resultOccupiedAreaNullSplitRenderersNotTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resultOccupiedAreaNullSplitRenderersNot", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    private static List<IElement> convertToElements(String name) throws IOException {
        String sourceHtml = SOURCE_FOLDER + name + ".html";
        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);
        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }
        return elements;
    }
}
