package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.BodyHtmlStylesContainer;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
import com.itextpdf.kernel.colors.gradients.StrategyBasedLinearGradientBuilder;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.BackgroundImage;
import com.itextpdf.layout.property.Property;
import com.itextpdf.styledxmlparser.css.util.CssGradientUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Category(UnitTest.class)
public class BackgroundApplierUtilTest extends ExtendedITextTest {
    private static final double EPS = 0.000001;

    public static final String sourceFolder =
            "./src/test/resources/com/itextpdf/html2pdf/css/apply/util/BackgroundApplierUtilTest";

    @Test
    public void backgroundColorTest() {
        IPropertyContainer container = new BodyHtmlStylesContainer(){
            @Override
            public void setProperty(int property, Object value) {
                Assert.assertEquals(Property.BACKGROUND, property);
                Assert.assertTrue(value instanceof Background);
                Background backgroundValue = (Background) value;
                Assert.assertEquals(new DeviceRgb(1.0f, 0.0f, 0.0f), backgroundValue.getColor());
                Assert.assertEquals(1.0f, backgroundValue.getOpacity(), EPS);
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_COLOR, "red");
        BackgroundApplierUtil.applyBackground(props, new ProcessorContext(new ConverterProperties()), container);
    }

    @Test
    public void backgroundImageTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(sourceFolder));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final ProcessorContext innerContext = context;
            final String innerImage = image;

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(1, values.size());
                for (Object value : values) {
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    PdfImageXObject pdfImage = image.getImage();
                    Assert.assertNotNull(pdfImage);
                    PdfXObject expectedImage = innerContext.getResourceResolver().retrieveImageExtended(
                            CssUtils.extractUrl(innerImage));
                    Assert.assertTrue(expectedImage instanceof PdfImageXObject);
                    Assert.assertEquals(Arrays.toString(((PdfImageXObject) expectedImage).getImageBytes()),
                            Arrays.toString(pdfImage.getImageBytes()));
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void backgroundInvalidImageTest() {
        final String image = "url(img.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(sourceFolder));
        IPropertyContainer container = new BodyHtmlStylesContainer() {

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.fail();
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImageRepeatTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(sourceFolder));
        IPropertyContainer container = new BodyHtmlStylesContainer() {

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(1, values.size());
                for (Object value : values) {
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertFalse(image.isRepeatX());
                    Assert.assertFalse(image.isRepeatY());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.BACKGROUND_REPEAT, "no-repeat");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(sourceFolder));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final ProcessorContext innerContext = context;
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                for (int i = 0; i < values.size(); i++) {
                    Object value = values.get(i);
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    PdfImageXObject pdfImage = image.getImage();
                    Assert.assertNotNull(pdfImage);
                    PdfXObject expectedImage = innerContext.getResourceResolver().retrieveImageExtended(
                            CssUtils.extractUrl(imagesArray[i]));
                    Assert.assertTrue(expectedImage instanceof PdfImageXObject);
                    Assert.assertEquals(Arrays.toString(((PdfImageXObject) expectedImage).getImageBytes()),
                            Arrays.toString(pdfImage.getImageBytes()));
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesRepeatTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(sourceFolder));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                for (int i = 0; i < values.size(); i++) {
                    Object value = values.get(i);
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertFalse(image.isRepeatX());
                    Assert.assertFalse(image.isRepeatY());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_REPEAT, "no-repeat");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesRepeatsTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(sourceFolder));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                for (int i = 0; i < values.size(); i++) {
                    Object value = values.get(i);
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertNotEquals((i == 0), image.isRepeatX());
                    Assert.assertNotEquals((i == 0), image.isRepeatY());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_REPEAT, "no-repeat,repeat");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundLinearGradientsTest() {
        final String gradients = "linear-gradient(red),linear-gradient(green),linear-gradient(blue)";
        final String otterFontSize = "15px";
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] gradientsArray = BackgroundApplierUtil.splitStringWithComma(gradients);
            final float fontSize = CssUtils.parseAbsoluteLength(otterFontSize);

            @Override
            public void setProperty(int property, Object value) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(value instanceof List);
                List values = (List) value;
                Assert.assertEquals(gradientsArray.length, values.size());
                for (int i = 0; i < values.size(); ++i) {
                    Assert.assertTrue(values.get(i) instanceof BackgroundImage);
                    AbstractLinearGradientBuilder builder =
                            ((BackgroundImage) values.get(i)).getLinearGradientBuilder();
                    Assert.assertTrue(builder instanceof StrategyBasedLinearGradientBuilder);

                    StrategyBasedLinearGradientBuilder expectedGradientBuilder =
                            CssGradientUtil.parseCssLinearGradient(gradientsArray[i], fontSize, fontSize);
                    Assert.assertNotNull(expectedGradientBuilder);
                    StrategyBasedLinearGradientBuilder actualGradientBuilder =
                            (StrategyBasedLinearGradientBuilder) builder;
                    Assert.assertEquals(expectedGradientBuilder.getSpreadMethod(),
                            actualGradientBuilder.getSpreadMethod());
                    Assert.assertEquals(expectedGradientBuilder.getColorStops(), actualGradientBuilder.getColorStops());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, gradients);
        props.put(CssConstants.FONT_SIZE, "15px");
        BackgroundApplierUtil.applyBackground(props, new ProcessorContext(new ConverterProperties()), container);
    }

    @Test
    public void backgroundLinearGradientTest() {
        final String otterGradient = "linear-gradient(red)";
        final String otterFontSize = "15px";
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String gradient = otterGradient;
            final float fontSize = CssUtils.parseAbsoluteLength(otterFontSize);

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(1, values.size());
                for (Object value : values) {
                    Assert.assertTrue(value instanceof BackgroundImage);
                    AbstractLinearGradientBuilder builder = ((BackgroundImage) value).getLinearGradientBuilder();
                    Assert.assertTrue(builder instanceof StrategyBasedLinearGradientBuilder);

                    StrategyBasedLinearGradientBuilder expectedGradientBuilder =
                            CssGradientUtil.parseCssLinearGradient(gradient, fontSize, fontSize);
                    Assert.assertNotNull(expectedGradientBuilder);
                    StrategyBasedLinearGradientBuilder actualGradientBuilder =
                            (StrategyBasedLinearGradientBuilder) builder;
                    Assert.assertEquals(expectedGradientBuilder.getSpreadMethod(),
                            actualGradientBuilder.getSpreadMethod());
                    Assert.assertEquals(expectedGradientBuilder.getColorStops(), actualGradientBuilder.getColorStops());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, otterGradient);
        props.put(CssConstants.FONT_SIZE, "15px");
        BackgroundApplierUtil.applyBackground(props, new ProcessorContext(new ConverterProperties()), container);
    }

    @Test
    public void splitStringWithCommaTest() {
        Assert.assertEquals(new ArrayList<String>(), Arrays.asList(BackgroundApplierUtil.splitStringWithComma(null)));
        Assert.assertEquals(Arrays.asList("value1", "value2", "value3"),
                Arrays.asList(BackgroundApplierUtil.splitStringWithComma("value1,value2,value3")));
        Assert.assertEquals(Arrays.asList("value1", " value2", " value3"),
                Arrays.asList(BackgroundApplierUtil.splitStringWithComma("value1, value2, value3")));
        Assert.assertEquals(Arrays.asList("value1", "(value,with,comma)", "value3"),
                Arrays.asList(BackgroundApplierUtil.splitStringWithComma("value1,(value,with,comma),value3")));
        Assert.assertEquals(Arrays.asList("value1", "(val(ue,with,comma),value3"),
                Arrays.asList(BackgroundApplierUtil.splitStringWithComma("value1,(val(ue,with,comma),value3")));
        Assert.assertEquals(Arrays.asList("value1", "(value,with)", "comma)", "value3"),
                Arrays.asList(BackgroundApplierUtil.splitStringWithComma("value1,(value,with),comma),value3")));
        Assert.assertEquals(Arrays.asList("value1", "( v2,v3)", "(v4, v5)", "value3"),
                Arrays.asList(BackgroundApplierUtil.splitStringWithComma("value1,( v2,v3),(v4, v5),value3")));
        Assert.assertEquals(Arrays.asList("v.al*ue1\"", "( v2,v3)", "\"(v4,v5;);", "value3"),
                Arrays.asList(BackgroundApplierUtil.splitStringWithComma("v.al*ue1\",( v2,v3),\"(v4,v5;);,value3")));
    }
}
