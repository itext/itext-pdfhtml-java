/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.renderer.FlexContainerRenderer;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FloatAndFlexTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/FloatAndFlexTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/FloatAndFlexTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    //TODO DEVSIX-5087 remove this test when working on the ticket
    public void floatAtFlexContainerTest() throws IOException {
        String name = "floatAtFlexContainer";
        String sourceHtml = SOURCE_FOLDER + name + ".html";

        ConverterProperties converterProperties = new ConverterProperties().setBaseUri(SOURCE_FOLDER);

        List<IElement> elements;
        try (FileInputStream fileInputStream = new FileInputStream(sourceHtml)) {
            elements = HtmlConverter.convertToElements(fileInputStream, converterProperties);
        }

        IElement flexContainer = elements.get(0);
        Assert.assertTrue(flexContainer.getRenderer() instanceof FlexContainerRenderer);
        Assert.assertFalse(flexContainer.hasProperty(Property.FLOAT));
        Assert.assertFalse(flexContainer.hasProperty(Property.CLEAR));
    }

    @Test
    public void clearAtFlexItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clearAtFlexItem", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void flexContainerHeightTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("flexContainerHeight", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void floatAtFlexItemTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatAtFlexItem", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void floatAtFlexItemNestedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatAtFlexItemNested", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void floatsPositioningInsideAndOutsideFlexTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatsPositioningInsideAndOutsideFlex", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void floatFlexContainerTest() throws IOException, InterruptedException {
        // TODO DEVSIX-7603 Flex container float property is ignored, so width is incorrect
        convertToPdfAndCompare("floatFlexContainer", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
