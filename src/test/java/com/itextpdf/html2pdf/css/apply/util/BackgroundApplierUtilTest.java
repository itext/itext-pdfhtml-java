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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.BodyHtmlStylesContainer;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
import com.itextpdf.kernel.colors.gradients.StrategyBasedLinearGradientBuilder;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Background;
import com.itextpdf.layout.properties.BackgroundBox;
import com.itextpdf.layout.properties.BackgroundImage;
import com.itextpdf.layout.properties.BackgroundPosition;
import com.itextpdf.layout.properties.BackgroundRepeat.BackgroundRepeatValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssGradientUtil;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Category(UnitTest.class)
public class BackgroundApplierUtilTest extends ExtendedITextTest {
    private static final double EPS = 0.000001;

    public static final String SOURCE_FOLDER =
            "./src/test/resources/com/itextpdf/html2pdf/css/apply/util/BackgroundApplierUtilTest";

    @Test
    public void backgroundColorTest() {
        IPropertyContainer container = new BodyHtmlStylesContainer() {
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
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    PdfXObject expectedImage = innerContext.getResourceResolver().retrieveImage(
                            CssUtils.extractUrl(innerImage));
                    Assert.assertTrue(expectedImage instanceof PdfImageXObject);
                    Assert.assertEquals(Arrays.toString(((PdfImageXObject) expectedImage).getImageBytes()),
                            Arrays.toString(pdfImage.getImageBytes()));
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI)
    })
    public void backgroundInvalidImageTest() {
        final String image = "url(img.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer() {

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.fail();
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImageRepeatTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundRepeatValue.NO_REPEAT, image.getRepeat().getXAxisRepeat());
                    Assert.assertEquals(BackgroundRepeatValue.NO_REPEAT, image.getRepeat().getYAxisRepeat());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.BACKGROUND_REPEAT, "no-repeat");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImageInvalidRepeatTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundRepeatValue.REPEAT, image.getRepeat().getXAxisRepeat());
                    Assert.assertEquals(BackgroundRepeatValue.REPEAT, image.getRepeat().getYAxisRepeat());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.BACKGROUND_REPEAT, "j");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    PdfXObject expectedImage = innerContext.getResourceResolver().retrieveImage(
                            CssUtils.extractUrl(imagesArray[i]));
                    Assert.assertTrue(expectedImage instanceof PdfImageXObject);
                    Assert.assertEquals(Arrays.toString(((PdfImageXObject) expectedImage).getImageBytes()),
                            Arrays.toString(pdfImage.getImageBytes()));
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesRepeatTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundRepeatValue.NO_REPEAT, image.getRepeat().getXAxisRepeat());
                    Assert.assertEquals(BackgroundRepeatValue.NO_REPEAT, image.getRepeat().getYAxisRepeat());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_REPEAT, "no-repeat");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesRepeatsTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    if (i == 0) {
                        Assert.assertEquals(BackgroundRepeatValue.NO_REPEAT, image.getRepeat().getXAxisRepeat());
                        Assert.assertEquals(BackgroundRepeatValue.NO_REPEAT, image.getRepeat().getYAxisRepeat());
                    } else {
                        Assert.assertEquals(BackgroundRepeatValue.REPEAT, image.getRepeat().getXAxisRepeat());
                        Assert.assertEquals(BackgroundRepeatValue.REPEAT, image.getRepeat().getYAxisRepeat());
                    }

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
            final List<String> gradientsArray = CssUtils.splitStringWithComma(gradients);
            final float fontSize = CssDimensionParsingUtils.parseAbsoluteLength(otterFontSize);

            @Override
            public void setProperty(int property, Object value) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(value instanceof List);
                List values = (List) value;
                Assert.assertEquals(gradientsArray.size(), values.size());
                for (int i = 0; i < values.size(); ++i) {
                    Assert.assertTrue(values.get(i) instanceof BackgroundImage);
                    AbstractLinearGradientBuilder builder =
                            ((BackgroundImage) values.get(i)).getLinearGradientBuilder();
                    Assert.assertTrue(builder instanceof StrategyBasedLinearGradientBuilder);

                    StrategyBasedLinearGradientBuilder expectedGradientBuilder =
                            CssGradientUtil.parseCssLinearGradient(gradientsArray.get(i), fontSize, fontSize);
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
            final float fontSize = CssDimensionParsingUtils.parseAbsoluteLength(otterFontSize);

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
    public void backgroundImagePositionTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundPosition.PositionX.RIGHT,
                            image.getBackgroundPosition().getPositionX());
                    Assert.assertEquals(BackgroundPosition.PositionY.CENTER,
                            image.getBackgroundPosition().getPositionY());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.BACKGROUND_POSITION_X, "right");
        props.put(CssConstants.BACKGROUND_POSITION_Y, "center");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImageInvalidPositionTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundPosition.PositionX.LEFT,
                            image.getBackgroundPosition().getPositionX());
                    Assert.assertEquals(BackgroundPosition.PositionY.TOP, image.getBackgroundPosition().getPositionY());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.BACKGROUND_POSITION_X, "j");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImageEmptyPositionTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundPosition.PositionX.LEFT,
                            image.getBackgroundPosition().getPositionX());
                    Assert.assertEquals(BackgroundPosition.PositionY.TOP, image.getBackgroundPosition().getPositionY());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.BACKGROUND_POSITION_X, "");
        props.put(CssConstants.BACKGROUND_POSITION_Y, "");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesLeftBottomPositionTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg),url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                BackgroundPosition position = new BackgroundPosition().setPositionX(BackgroundPosition.PositionX.LEFT)
                        .setPositionY(BackgroundPosition.PositionY.BOTTOM).setYShift(UnitValue.createPointValue(20));
                for (Object value : values) {
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertEquals(position, image.getBackgroundPosition());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_POSITION_X, "left");
        props.put(CssConstants.BACKGROUND_POSITION_Y, "bottom 20pt");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesRightTopPositionTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg),url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                BackgroundPosition position = new BackgroundPosition().setPositionX(BackgroundPosition.PositionX.RIGHT)
                        .setPositionY(BackgroundPosition.PositionY.TOP).setXShift(UnitValue.createPointValue(30));
                for (Object value : values) {
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertEquals(position, image.getBackgroundPosition());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_POSITION_X, "right 30pt");
        props.put(CssConstants.BACKGROUND_POSITION_Y, "top");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesCenterCenterPositionTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg),url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                BackgroundPosition position = new BackgroundPosition().setPositionX(BackgroundPosition.PositionX.CENTER)
                        .setPositionY(BackgroundPosition.PositionY.CENTER);
                for (Object value : values) {
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertEquals(position, image.getBackgroundPosition());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_POSITION_X, "center");
        props.put(CssConstants.BACKGROUND_POSITION_Y, "center");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesPositionMissedTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg),url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                BackgroundPosition[] positions = new BackgroundPosition[] {
                        new BackgroundPosition().setPositionX(BackgroundPosition.PositionX.LEFT).setPositionY(
                                BackgroundPosition.PositionY.CENTER),
                        new BackgroundPosition().setPositionX(BackgroundPosition.PositionX.CENTER).setPositionY(
                                BackgroundPosition.PositionY.BOTTOM),
                        new BackgroundPosition().setPositionX(BackgroundPosition.PositionX.LEFT).setPositionY(
                                BackgroundPosition.PositionY.CENTER)
                };
                for (int i = 0; i < values.size(); i++) {
                    Object value = values.get(i);
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertEquals(positions[i], image.getBackgroundPosition());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_POSITION_X, "left, center");
        props.put(CssConstants.BACKGROUND_POSITION_Y, "center, bottom");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundImagesPositionsTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg),url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer() {
            final String[] imagesArray = images.split(",");

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND_IMAGE, property);
                Assert.assertTrue(propertyValue instanceof List);
                List values = (List) propertyValue;
                Assert.assertEquals(imagesArray.length, values.size());
                BackgroundPosition[] positions = new BackgroundPosition[] {
                        new BackgroundPosition(),
                        new BackgroundPosition().setPositionY(BackgroundPosition.PositionY.BOTTOM),
                        new BackgroundPosition().setPositionX(BackgroundPosition.PositionX.RIGHT)
                };
                for (int i = 0; i < values.size(); i++) {
                    Object value = values.get(i);
                    Assert.assertTrue(value instanceof BackgroundImage);
                    BackgroundImage image = (BackgroundImage) value;
                    Assert.assertEquals(positions[i], image.getBackgroundPosition());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_POSITION_X, "left,left,right");
        props.put(CssConstants.BACKGROUND_POSITION_Y, "top, bottom,top");
        props.put(CssConstants.FONT_SIZE, "15pt");
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundClipOriginImageTest() {
        final String image = "url(rock_texture.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundBox.CONTENT_BOX, image.getBackgroundClip());
                    Assert.assertEquals(BackgroundBox.PADDING_BOX, image.getBackgroundOrigin());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, image);
        props.put(CssConstants.BACKGROUND_CLIP, CssConstants.CONTENT_BOX);
        props.put(CssConstants.BACKGROUND_ORIGIN, CssConstants.PADDING_BOX);
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundClipColorTest() {
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer() {

            @Override
            public void setProperty(int property, Object propertyValue) {
                Assert.assertEquals(Property.BACKGROUND, property);
                Assert.assertTrue(propertyValue instanceof Background);
                Background color = (Background) propertyValue;
                Assert.assertEquals(BackgroundBox.CONTENT_BOX, color.getBackgroundClip());
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_COLOR, "blue");
        props.put(CssConstants.BACKGROUND_CLIP, CssConstants.CONTENT_BOX);
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundClipOriginImagesTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
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
                    Assert.assertEquals(BackgroundBox.CONTENT_BOX, image.getBackgroundClip());
                    Assert.assertEquals(BackgroundBox.BORDER_BOX, image.getBackgroundOrigin());
                }
            }
        };
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_CLIP, CssConstants.CONTENT_BOX);
        props.put(CssConstants.BACKGROUND_ORIGIN, CssConstants.BORDER_BOX);
        BackgroundApplierUtil.applyBackground(props, context, container);
    }

    @Test
    public void backgroundMultipleClipOriginImagesTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final String clips = "content-box,padding-box";
        final String origins = "border-box,content-box";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer();
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_CLIP, clips);
        props.put(CssConstants.BACKGROUND_ORIGIN, origins);
        BackgroundApplierUtil.applyBackground(props, context, container);
        List<BackgroundImage> backgroundImages = container.<List<BackgroundImage>>getProperty(
                Property.BACKGROUND_IMAGE);
        Assert.assertNotNull(backgroundImages);
        Assert.assertEquals(2, backgroundImages.size());
        BackgroundImage imageObj1 = backgroundImages.get(0);
        Assert.assertNotNull(imageObj1);
        Assert.assertEquals(BackgroundBox.CONTENT_BOX, imageObj1.getBackgroundClip());
        Assert.assertEquals(BackgroundBox.BORDER_BOX, imageObj1.getBackgroundOrigin());
        BackgroundImage imageObj2 = backgroundImages.get(1);
        Assert.assertNotNull(imageObj2);
        Assert.assertEquals(BackgroundBox.PADDING_BOX, imageObj2.getBackgroundClip());
        Assert.assertEquals(BackgroundBox.CONTENT_BOX, imageObj2.getBackgroundOrigin());
    }

    @Test
    public void backgroundClipOriginImagesColorTest() {
        final String images = "url(rock_texture.jpg),url(rock_texture2.jpg)";
        final String clips = "content-box,padding-box";
        final String origins = "border-box,content-box";
        final ProcessorContext context = new ProcessorContext(new ConverterProperties().setBaseUri(SOURCE_FOLDER));
        IPropertyContainer container = new BodyHtmlStylesContainer();
        Map<String, String> props = new HashMap<>();
        props.put(CssConstants.BACKGROUND_IMAGE, images);
        props.put(CssConstants.BACKGROUND_COLOR, "blue");
        props.put(CssConstants.BACKGROUND_CLIP, clips);
        props.put(CssConstants.BACKGROUND_ORIGIN, origins);
        BackgroundApplierUtil.applyBackground(props, context, container);
        Background background = container.<Background>getProperty(Property.BACKGROUND);
        Assert.assertNotNull(background);
        Assert.assertEquals(BackgroundBox.PADDING_BOX, background.getBackgroundClip());
        List<BackgroundImage> backgroundImages =  container.<List<BackgroundImage>>getProperty(
                Property.BACKGROUND_IMAGE);
        Assert.assertNotNull(backgroundImages);
        Assert.assertEquals(2, backgroundImages.size());
        BackgroundImage imageObj1 = backgroundImages.get(0);
        Assert.assertNotNull(imageObj1);
        Assert.assertEquals(BackgroundBox.CONTENT_BOX, imageObj1.getBackgroundClip());
        Assert.assertEquals(BackgroundBox.BORDER_BOX, imageObj1.getBackgroundOrigin());
        BackgroundImage imageObj2 = backgroundImages.get(1);
        Assert.assertNotNull(imageObj2);
        Assert.assertEquals(BackgroundBox.PADDING_BOX, imageObj2.getBackgroundClip());
        Assert.assertEquals(BackgroundBox.CONTENT_BOX, imageObj2.getBackgroundOrigin());
    }
}
