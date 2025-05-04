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

import com.itextpdf.forms.form.element.TextArea;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlPageBreak;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.FlexContainerRenderer;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class DisplayFlexTest extends ExtendedHtmlConversionITextTest {

    private static final float EPS = 1e-6f;
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/DisplayFlexTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/DisplayFlexTest/";


    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void displayFlexCommonTest() throws IOException {
        String name = "displayFlexCommon";
        List<IElement> elements = convertToElements(name);
        IElement flexContainer = elements.get(0);

        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        List<IElement> flexContainerChildren = ((Div) flexContainer).getChildren();
        Assertions.assertEquals(11, flexContainerChildren.size());

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
        Assertions.assertTrue(element5 instanceof Image);

        IElement element6 = flexContainerChildren.get(6);
        Assertions.assertTrue(element6 instanceof Div);
        Assertions.assertEquals(1, ((Div) element6).getChildren().size());
        Assertions.assertTrue(((Div) element6).getChildren().get(0) instanceof Paragraph);
        Assertions.assertEquals(3, ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().size());
        Assertions.assertTrue(((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(0) instanceof Text);
        Assertions.assertTrue(((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(1) instanceof Text);
        Assertions.assertTrue(((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(2) instanceof Text);
        Assertions.assertEquals("text with",
                ((Text) ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(0)).getText());
        Assertions.assertEquals("\n",
                ((Text) ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(1)).getText());
        Assertions.assertEquals("br tag",
                ((Text) ((Paragraph) ((Div) element6).getChildren().get(0)).getChildren().get(2)).getText());

        IElement element7 = flexContainerChildren.get(7);
        Assertions.assertTrue(element7 instanceof Image);

        IElement element8 = flexContainerChildren.get(8);
        assertDiv(element8, "div with page break");

        IElement element9 = flexContainerChildren.get(9);
        Assertions.assertTrue(element9 instanceof HtmlPageBreak);

        IElement element10 = flexContainerChildren.get(10);
        Assertions.assertTrue(element10 instanceof TextArea);
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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertEquals(1, ((Div) flexContainer).getChildren().size());

        IElement element = ((Div) flexContainer).getChildren().get(0);
        Assertions.assertTrue(element instanceof Div);
        Assertions.assertEquals(3, ((Div) element).getChildren().size());
        Assertions.assertTrue(((Div) element).getChildren().get(0) instanceof Paragraph);
        Assertions.assertTrue(((Div) element).getChildren().get(1) instanceof Div);
        Assertions.assertTrue(((Div) element).getChildren().get(2) instanceof Paragraph);
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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertEquals(1, ((Div) flexContainer).getChildren().size());

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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertEquals(2, ((Div) flexContainer).getChildren().size());

        Assertions.assertTrue(((Div) flexContainer).getChildren().get(0) instanceof Div);

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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertEquals(1, ((Div) flexContainer).getChildren().size());
        IElement element = ((Div) flexContainer).getChildren().get(0);
        assertDiv(element, "hello");
    }

    @Test
    public void flexWrapTest() throws IOException {
        String name = "flexWrap";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertTrue(flexContainer.hasProperty(Property.FLEX_WRAP));
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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertFalse(flexContainer.hasProperty(Property.OVERFLOW_X));
        Assertions.assertFalse(flexContainer.hasProperty(Property.OVERFLOW_Y));
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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertTrue(flexContainer.hasProperty(Property.COLLAPSING_MARGINS));
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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertEquals(1, ((Div) flexContainer).getChildren().size());

        Assertions.assertTrue(((Div) flexContainer).getChildren().get(0).hasProperty(Property.OVERFLOW_X));
        Assertions.assertTrue(((Div) flexContainer).getChildren().get(0).hasProperty(Property.OVERFLOW_Y));
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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
    }

    @Test
    //TODO DEVSIX-5087 remove this test when working on the ticket
    public void tempDisablePropertiesTest() throws IOException {
        List<IElement> elements = convertToElements("tempDisableProperties");
        Assertions.assertEquals(1, elements.size());
        Assertions.assertTrue(elements.get(0).getRenderer() instanceof FlexContainerRenderer);

        Assertions.assertFalse(elements.get(0).hasProperty(Property.OVERFLOW_X));
        Assertions.assertFalse(elements.get(0).hasProperty(Property.OVERFLOW_Y));
        Assertions.assertFalse(elements.get(0).hasProperty(Property.FLOAT));
        Assertions.assertFalse(elements.get(0).hasProperty(Property.CLEAR));
    }

    @Test
    public void disableFlexItemPropertiesTest() throws IOException {
        List<IElement> elements = convertToElements("disableFlexItemProperties");
        IElement flexItem = ((Div) elements.get(0)).getChildren().get(0);
        Assertions.assertFalse(flexItem.hasProperty(Property.FLOAT));
        Assertions.assertFalse(flexItem.hasProperty(Property.CLEAR));
        Assertions.assertFalse(flexItem.hasProperty(Property.VERTICAL_ALIGNMENT));
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
        Assertions.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assertions.assertEquals(1, ((Div) flexContainer).getChildren().size());
        IElement flexItem = ((Div) flexContainer).getChildren().get(0);
        Float flexGrow = flexItem.<Float>getProperty(Property.FLEX_GROW);
        Float flexShrink = flexItem.<Float>getProperty(Property.FLEX_SHRINK);
        Assertions.assertEquals(2f, (float) flexGrow, EPS);
        Assertions.assertEquals(3f, (float) flexShrink, EPS);
        Assertions.assertEquals(UnitValue.createPointValue(200.338f),
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
    @LogMessages(messages = @LogMessage(messageTemplate = IoLogMessageConstant.CLIP_ELEMENT))
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

    @Test
    public void resolveStylesIfParentHasDisplayFlexStyleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("displayNoneTest", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    private static void assertDiv(IElement element, String text) {
        Assertions.assertTrue(element instanceof Div);
        Assertions.assertEquals(1, ((Div) element).getChildren().size());
        Assertions.assertTrue(((Div) element).getChildren().get(0) instanceof Paragraph);
        Assertions.assertEquals(1, ((Paragraph) ((Div) element).getChildren().get(0)).getChildren().size());
        Assertions.assertTrue(((Paragraph) ((Div) element).getChildren().get(0)).getChildren().get(0) instanceof Text);
        Assertions.assertEquals(text,
                ((Text) ((Paragraph) ((Div) element).getChildren().get(0)).getChildren().get(0)).getText());
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE)
    })
    public void resultOccupiedAreaNullSplitRenderersNotTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("resultOccupiedAreaNullSplitRenderersNot", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitFlexContainersTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest1() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedFlexStretchSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest2() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedFlexStartSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest3() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedFlexEndSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest4() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedFlexCenterSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest5() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedReverseFlexStartSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest6() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedReverseFlexEndSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest7() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedRowReverseFlexStartSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void splitWrappedFlexContainersTest8() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedRowReverseFlexEndSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, ignore = true)
    })
    public void splitWrappedFlexContainersTest9() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedRowReverseRtlFlexStartSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, ignore = true)
    })
    public void splitWrappedFlexContainersTest10() throws IOException, InterruptedException {
        convertToPdfAndCompare("wrappedRowRtlFlexStartSplit", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.FONT_PROPERTY_MUST_BE_PDF_FONT_OBJECT,
                    logLevel = LogLevelConstants.ERROR, count = 3)
    })
    @Test
    public void endlessColumnFlexContainerWithPercentFlexBasisTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("endlessColumnFlexContainerWithPercentFlexBasis", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.FONT_PROPERTY_MUST_BE_PDF_FONT_OBJECT,
                    logLevel = LogLevelConstants.ERROR, count = 3)
    })
    @Test
    public void definiteMainSizeColumnFlexContainerWithPercentFlexBasisTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("definiteMainSizeColumnFlexContainerWithPercentFlexBasis", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.FONT_PROPERTY_MUST_BE_PDF_FONT_OBJECT,
                    logLevel = LogLevelConstants.ERROR, count = 3)
    })
    @Test
    public void imageStretchColumnFlexContainerTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("imageStretchColumnFlexContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    //TODO: DEVSIX-8730 bullet is not drawn
    public void unorderedListFlexTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("UnorderedListWithFlex", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void displayFlexWithRobotoFontTest() throws IOException, InterruptedException {
        String outFile = DESTINATION_FOLDER + "displayFlexWithRobotoFont.pdf";
        String cmpFile = SOURCE_FOLDER + "cmp_displayFlexWithRobotoFont.pdf";
        String htmlFile = SOURCE_FOLDER + "displayFlexWithRobotoFont.html";
        String robotoFont ="./src/test/resources/com/itextpdf/html2pdf/fonts/Roboto-Regular.ttf";

        PdfWriter writer = new PdfWriter(new File(outFile));
        PdfDocument pdfDocument = new PdfDocument(writer);
        ConverterProperties props = new ConverterProperties();
        FontProvider fontProvider = new BasicFontProvider(false, false, false);
        fontProvider.addFont(robotoFont);
        props.setFontProvider(fontProvider);

        HtmlConverter.convertToPdf(new FileInputStream(htmlFile), pdfDocument, props);

        Assertions.assertNull(new CompareTool().compareByContent(outFile, cmpFile, DESTINATION_FOLDER,
                "diff_displayFlexWithRobotoFont_"));
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
