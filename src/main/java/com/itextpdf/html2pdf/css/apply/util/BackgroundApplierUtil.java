/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.
    
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS
    
    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/
    
    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.
    
    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.
    
    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.
    
    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.kernel.colors.gradients.StrategyBasedLinearGradientBuilder;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.BackgroundImage;
import com.itextpdf.layout.property.BackgroundRepeat;
import com.itextpdf.layout.property.Property;
import com.itextpdf.styledxmlparser.css.util.CssGradientUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class to apply backgrounds.
 */
public final class BackgroundApplierUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundApplierUtil.class);

    /**
     * Creates a new {@link BackgroundApplierUtil} instance.
     */
    private BackgroundApplierUtil() {
    }

    /**
     * Applies background to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyBackground(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        final String backgroundColorStr = cssProps.get(CssConstants.BACKGROUND_COLOR);
        applyBackgroundColor(backgroundColorStr, element);

        final String backgroundImagesStr = cssProps.get(CssConstants.BACKGROUND_IMAGE);
        final String backgroundRepeatStr = cssProps.get(CssConstants.BACKGROUND_REPEAT);

        final List<BackgroundImage> backgroundImagesList = new ArrayList<>();
        final String[] backgroundImagesArray = splitStringWithComma(backgroundImagesStr);
        final String[] backgroundRepeatArray = splitStringWithComma(backgroundRepeatStr);
        for (int i = 0; i < backgroundImagesArray.length; ++i) {
            if (backgroundImagesArray[i] == null || CssConstants.NONE.equals(backgroundImagesArray[i])) {
                continue;
            }

            if (CssGradientUtil.isCssLinearGradientValue(backgroundImagesArray[i])) {
                applyLinearGradient(cssProps, context, backgroundImagesArray, backgroundImagesList, i);
            } else {
                final BackgroundRepeat repeat = applyBackgroundRepeat(backgroundRepeatArray, i);
                applyBackgroundImage(context, backgroundImagesArray, backgroundImagesList, i, repeat);
            }
        }
        if (!backgroundImagesList.isEmpty()) {
            element.setProperty(Property.BACKGROUND_IMAGE, backgroundImagesList);
        }
    }

    static String[] splitStringWithComma(final String value) {
        if (value == null) {
            return new String[0];
        }
        final List<String> resultList = new ArrayList<>();
        int lastComma = 0;
        int notClosedBrackets = 0;
        for (int i = 0; i < value.length(); ++i) {
            if (value.charAt(i) == ',' && notClosedBrackets == 0) {
                resultList.add(value.substring(lastComma, i));
                lastComma = i + 1;
            }
            if (value.charAt(i) == '(') {
                ++notClosedBrackets;
            }
            if (value.charAt(i) == ')') {
                --notClosedBrackets;
                notClosedBrackets = Math.max(notClosedBrackets, 0);
            }
        }
        final String lastToken = value.substring(lastComma);
        if (!lastToken.isEmpty()) {
            resultList.add(lastToken);
        }
        return resultList.toArray(new String[0]);
    }

    private static BackgroundRepeat applyBackgroundRepeat(final String[] backgroundRepeatArray, final int iteration) {
        final int index = getBackgroundSidePropertyIndex(backgroundRepeatArray, iteration);
        if (index != -1) {
            final boolean repeatX = CssConstants.REPEAT.equals(backgroundRepeatArray[index]) ||
                    CssConstants.REPEAT_X.equals(backgroundRepeatArray[index]);
            final boolean repeatY = CssConstants.REPEAT.equals(backgroundRepeatArray[index]) ||
                    CssConstants.REPEAT_Y.equals(backgroundRepeatArray[index]);
            return new BackgroundRepeat(repeatX, repeatY);
        }
        return new BackgroundRepeat(true, true);
    }

    private static int getBackgroundSidePropertyIndex(final String[] backgroundPropertyArray, final int iteration) {
        if (backgroundPropertyArray.length > 0) {
            if (backgroundPropertyArray.length > iteration) {
                return iteration;
            } else {
                return 0;
            }
        }
        return -1;
    }

    private static void applyBackgroundColor(final String backgroundColorStr, final IPropertyContainer element) {
        if (backgroundColorStr != null && !CssConstants.TRANSPARENT.equals(backgroundColorStr)) {
            float[] rgbaColor = CssUtils.parseRgbaColor(backgroundColorStr);
            Color color = new DeviceRgb(rgbaColor[0], rgbaColor[1], rgbaColor[2]);
            float opacity = rgbaColor[3];
            Background backgroundColor = new Background(color, opacity);
            element.setProperty(Property.BACKGROUND, backgroundColor);
        }
    }

    private static void applyBackgroundImage(final ProcessorContext context, final String[] backgroundImagesArray,
                                             final List<BackgroundImage> backgroundImagesList, final int i,
                                             final BackgroundRepeat repeat) {
        final PdfXObject image = context.getResourceResolver().retrieveImageExtended(
                CssUtils.extractUrl(backgroundImagesArray[i]));
        if (image != null) {
            if (image instanceof PdfImageXObject) {
                backgroundImagesList.add(new HtmlBackgroundImage((PdfImageXObject) image, repeat));
            } else if (image instanceof PdfFormXObject) {
                backgroundImagesList.add(new HtmlBackgroundImage((PdfFormXObject) image, repeat));
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private static void applyLinearGradient(final Map<String, String> cssProps, final ProcessorContext context,
                                            final String[] backgroundImagesArray,
                                            final List<BackgroundImage> backgroundImagesList, final int i) {
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        try {
            StrategyBasedLinearGradientBuilder gradientBuilder =
                    CssGradientUtil.parseCssLinearGradient(backgroundImagesArray[i], em, rem);
            if (gradientBuilder != null) {
                backgroundImagesList.add(new BackgroundImage(gradientBuilder));
            }
        } catch (StyledXMLParserException e) {
            LOGGER.warn(MessageFormatUtil.format(
                    LogMessageConstant.INVALID_GRADIENT_DECLARATION, backgroundImagesArray[i]));
        }
    }

    /**
     * Implementation of the Image class when used in the context of HTML to PDF conversion.
     */
    private static class HtmlBackgroundImage extends BackgroundImage {

        private static final double PX_TO_PT_MULTIPLIER = 0.75;

        /**
         * In iText, we use user unit for the image sizes (and by default
         * one user unit = one point), whereas images are usually measured
         * in pixels.
         */
        private double dimensionMultiplier = 1;

        /**
         * Creates a new {@link HtmlBackgroundImage} instance.
         *
         * @param xObject background image property. {@link PdfImageXObject} instance.
         * @param repeat background repeat property. {@link BackgroundRepeat} instance.
         */
        public HtmlBackgroundImage(final PdfImageXObject xObject, final BackgroundRepeat repeat) {
            super(xObject, repeat);
            dimensionMultiplier = PX_TO_PT_MULTIPLIER;
        }

        /**
         * Creates a new {@link HtmlBackgroundImage} instance.
         *
         * @param xObject background-image property. {@link PdfFormXObject} instance.
         * @param repeat background-repeat property. {@link BackgroundRepeat} instance.
         */
        public HtmlBackgroundImage(final PdfFormXObject xObject, final BackgroundRepeat repeat) {
            super(xObject, repeat);
        }

        /**
         * Creates a new {@link HtmlBackgroundImage} instance.
         *
         * @param xObject background image property. {@link PdfImageXObject} instance.
         * @param repeatX is background is repeated in x dimension.
         * @param repeatY is background is repeated in y dimension.
         * @deprecated Remove this constructor in 7.2.
         */
        @Deprecated
        public HtmlBackgroundImage(final PdfImageXObject xObject, final boolean repeatX, final boolean repeatY) {
            this(xObject, new BackgroundRepeat(repeatX, repeatY));
        }

        /**
         * Creates a new {@link HtmlBackgroundImage} instance.
         *
         * @param xObject background-image property. {@link PdfFormXObject} instance.
         * @param repeatX is background is repeated in x dimension.
         * @param repeatY is background is repeated in y dimension.
         * @deprecated Remove this constructor in 7.2.
         */
        @Deprecated
        public HtmlBackgroundImage(final PdfFormXObject xObject, final boolean repeatX, final boolean repeatY) {
            this(xObject, new BackgroundRepeat(repeatX, repeatY));
        }

        @Override
        public float getWidth() {
            return (float) (image.getWidth() * dimensionMultiplier);
        }

        @Override
        public float getHeight() {
            return (float) (image.getHeight() * dimensionMultiplier);
        }
    }
}
