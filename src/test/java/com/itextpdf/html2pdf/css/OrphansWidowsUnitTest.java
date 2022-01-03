/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.impl.BlockCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.ParagraphOrphansControl;
import com.itextpdf.layout.properties.ParagraphWidowsControl;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class OrphansWidowsUnitTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/OrphansWidowsUnitTest/";

    @Test
    public void orphansDefaultValue() throws IOException {
        List<IElement> elements = convertToElements("orphansDefaultValue");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertNotNull(paragraph);

        ParagraphOrphansControl orphansControl = paragraph.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(orphansControl);
        Assert.assertEquals(2, orphansControl.getMinOrphans());
    }

    @Test
    public void orphansPropertyPresent() throws IOException {
       List<IElement> elements = convertToElements("orphansPropertyPresent");

       Paragraph paragraph = (Paragraph) elements.get(0);
       Assert.assertNotNull(paragraph);

       ParagraphOrphansControl orphansControl = paragraph.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
       Assert.assertNotNull(orphansControl);
       Assert.assertEquals(3, orphansControl.getMinOrphans());
    }

    @Test
    public void orphansPropertyInherited() throws IOException {
        List<IElement> elements = convertToElements("orphansPropertyInherited");

        Div div = (Div) elements.get(0);
        Assert.assertNotNull(div);
        ParagraphOrphansControl divOrphansControl = div.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(divOrphansControl);
        Assert.assertEquals(3, divOrphansControl.getMinOrphans());

        Paragraph paragraph = (Paragraph) div.getChildren().get(0);
        Assert.assertNotNull(paragraph);

        ParagraphOrphansControl paragraphOrphansControl = paragraph.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(paragraphOrphansControl);
        Assert.assertEquals(3, paragraphOrphansControl.getMinOrphans());

    }

    @Test
    public void orphansPropertyOneInheritedOneRedefined() throws IOException {
        List<IElement> elements = convertToElements("orphansPropertyOneInheritedOneRedefined");

        Div div = (Div) elements.get(0);
        Assert.assertNotNull(div);
        ParagraphOrphansControl divOrphansControl = div.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(divOrphansControl);
        Assert.assertEquals(3, divOrphansControl.getMinOrphans());

        Paragraph paragraph = (Paragraph) div.getChildren().get(0);
        Assert.assertNotNull(paragraph);

        ParagraphOrphansControl paragraphOrphansControl = paragraph.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(paragraphOrphansControl);
        Assert.assertEquals(3, paragraphOrphansControl.getMinOrphans());

        Paragraph anotherParagraph = (Paragraph) div.getChildren().get(1);
        Assert.assertNotNull(anotherParagraph);

        ParagraphOrphansControl anotherParagraphOrphansControl = anotherParagraph.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(anotherParagraphOrphansControl);
        Assert.assertEquals(4, anotherParagraphOrphansControl.getMinOrphans());
    }


    @Test
    public void widowsDefaultValue() throws IOException {
        List<IElement> elements = convertToElements("widowsDefaultValue");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertNotNull(paragraph);

        ParagraphWidowsControl widowsControl = paragraph.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(widowsControl);
        Assert.assertEquals(2, widowsControl.getMinWidows());
    }

    @Test
    public void widowsPropertyPresent() throws IOException {
        List<IElement> elements = convertToElements("widowsPropertyPresent");

        Paragraph paragraph = (Paragraph) elements.get(0);
        Assert.assertNotNull(paragraph);

        ParagraphWidowsControl widowsControl = paragraph.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(widowsControl);
        Assert.assertEquals(3, widowsControl.getMinWidows());
    }

    @Test
    public void widowsPropertyInherited() throws IOException {
        List<IElement> elements = convertToElements("widowsPropertyInherited");

        Div div = (Div) elements.get(0);
        Assert.assertNotNull(div);
        ParagraphWidowsControl divWidowsControl = div.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(divWidowsControl);
        Assert.assertEquals(3, divWidowsControl.getMinWidows());

        Paragraph paragraph = (Paragraph) div.getChildren().get(0);
        Assert.assertNotNull(paragraph);

        ParagraphWidowsControl paragraphWidowsControl = paragraph.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(paragraphWidowsControl);
        Assert.assertEquals(3, paragraphWidowsControl.getMinWidows());
    }

    @Test
    public void widowsPropertyOneInheritedOneRedefined() throws IOException {
        List<IElement> elements = convertToElements("widowsPropertyOneInheritedOneRedefined");

        Div div = (Div) elements.get(0);
        Assert.assertNotNull(div);
        ParagraphWidowsControl divWidowsControl = div.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(divWidowsControl);
        Assert.assertEquals(3, divWidowsControl.getMinWidows());

        Paragraph paragraph = (Paragraph) div.getChildren().get(0);
        Assert.assertNotNull(paragraph);

        ParagraphWidowsControl paragraphWidowsControl = paragraph.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(paragraphWidowsControl);
        Assert.assertEquals(3, paragraphWidowsControl.getMinWidows());

        Paragraph anotherParagraph = (Paragraph) div.getChildren().get(1);
        Assert.assertNotNull(anotherParagraph);

        ParagraphWidowsControl anotherParagraphWidowsControl = anotherParagraph.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(anotherParagraphWidowsControl);
        Assert.assertEquals(4, anotherParagraphWidowsControl.getMinWidows());
    }

    @Test
    public void orphansWidowsParallelInheritance() throws IOException {
        List<IElement> elements = convertToElements("orphansWidowsParallelInheritance");

        Div level1Div = (Div) elements.get(0);
        Assert.assertNotNull(level1Div);
        ParagraphOrphansControl level1DivOrphansControl = level1Div.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(level1DivOrphansControl);
        Assert.assertEquals(3, level1DivOrphansControl.getMinOrphans());
        ParagraphWidowsControl level1DivWidowsControl = level1Div.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNull(level1DivWidowsControl);


        Div level2Div = (Div) level1Div.getChildren().get(0);
        Assert.assertNotNull(level2Div);
        ParagraphOrphansControl level2DivOrphansControl = level2Div.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(level2DivOrphansControl);
        Assert.assertEquals(3, level2DivOrphansControl.getMinOrphans());
        ParagraphWidowsControl level2DivWidowsControl = level2Div.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(level2DivWidowsControl);
        Assert.assertEquals(5, level2DivWidowsControl.getMinWidows());

        Paragraph paragraph1 = (Paragraph) level2Div.getChildren().get(0);
        Assert.assertNotNull(paragraph1);
        ParagraphOrphansControl paragraph1OrphansControl = paragraph1.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(paragraph1OrphansControl);
        Assert.assertEquals(3, paragraph1OrphansControl.getMinOrphans());
        ParagraphWidowsControl paragraph1WidowsControl = paragraph1.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(paragraph1WidowsControl);
        Assert.assertEquals(5, paragraph1WidowsControl.getMinWidows());

        Paragraph paragraph2 = (Paragraph) level2Div.getChildren().get(1);
        Assert.assertNotNull(paragraph2);
        ParagraphOrphansControl paragraph2OrphansControl = paragraph2.<ParagraphOrphansControl>getProperty(Property.ORPHANS_CONTROL);
        Assert.assertNotNull(paragraph2OrphansControl);
        Assert.assertEquals(4, paragraph2OrphansControl.getMinOrphans());
        ParagraphWidowsControl paragraph2WidowsControl = paragraph2.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(paragraph2WidowsControl);
        Assert.assertEquals(5, paragraph2WidowsControl.getMinWidows());
    }

    @Test
    public void alterOrphansWidowsTest() throws IOException {
        ConverterProperties converterProperties = new ConverterProperties();

        DefaultCssApplierFactory cssApplierFactory = new CustomBlockCssApplierFactory();
        converterProperties.setCssApplierFactory(cssApplierFactory);

        List<IElement> elements = HtmlConverter.convertToElements(new FileInputStream(sourceFolder + "orphansWidows.html"), converterProperties);

        Div div = (Div) elements.get(0);
        Assert.assertNotNull(div);

        Paragraph paragraph = (Paragraph) div.getChildren().get(0);
        Assert.assertNotNull(paragraph);

        ParagraphWidowsControl paragraphWidowsControl = paragraph.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
        Assert.assertNotNull(paragraphWidowsControl);
        Assert.assertEquals(3, paragraphWidowsControl.getMaxLinesToMove());
        Assert.assertTrue(paragraphWidowsControl.isOverflowOnWidowsViolation());
    }

    private List<IElement> convertToElements(String name) throws IOException {
        String sourceHtml = sourceFolder + name + ".html";
        return HtmlConverter.convertToElements(new FileInputStream(sourceHtml));
    }

    static class CustomBlockCssApplierFactory extends DefaultCssApplierFactory {

        @Override
        public ICssApplier getCustomCssApplier(IElementNode tag) {
            if(TagConstants.P.equals(tag.name())){
                return new CustomBlockCssApplier();
            }
            return null;
        }
    }

    static class CustomBlockCssApplier  extends BlockCssApplier {

        @Override
        public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
            super.apply(context, stylesContainer, tagWorker);

            Paragraph paragraph = (Paragraph) tagWorker.getElementResult();

            ParagraphWidowsControl widowsControl = paragraph.<ParagraphWidowsControl>getProperty(Property.WIDOWS_CONTROL);
            if(widowsControl != null) {
                widowsControl.setMinAllowedWidows(widowsControl.getMinWidows(), 3,
                        true);
                paragraph.setProperty(Property.WIDOWS_CONTROL, widowsControl);
            }
        }
    }
}
