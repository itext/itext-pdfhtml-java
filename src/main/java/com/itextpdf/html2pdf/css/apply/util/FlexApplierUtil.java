/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.AlignmentPropertyValue;
import com.itextpdf.layout.properties.JustifyContent;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utilities class to apply flex properties.
 */
final public class FlexApplierUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlexApplierUtil.class);

    private FlexApplierUtil() {
    }

    /**
     * Applies properties to a flex item.
     *
     * @param cssProps the map of the CSS properties
     * @param context  the context of the converter processor
     * @param element  the element to set the properties
     */
    public static void applyFlexItemProperties(Map<String, String> cssProps, ProcessorContext context,
            IPropertyContainer element) {
        element.setProperty(Property.COLLAPSING_MARGINS, null);

        logWarningIfThereAreNotSupportedPropertyValues(createSupportedFlexItemPropertiesAndValuesMap(), cssProps);

        final String flexGrow = cssProps.get(CommonCssConstants.FLEX_GROW);
        if (flexGrow != null) {
            final Float flexGrowValue = CssDimensionParsingUtils.parseFloat(flexGrow);
            element.setProperty(Property.FLEX_GROW, flexGrowValue);
        }

        final String flexShrink = cssProps.get(CommonCssConstants.FLEX_SHRINK);
        if (flexShrink != null) {
            final Float flexShrinkValue = CssDimensionParsingUtils.parseFloat(flexShrink);
            element.setProperty(Property.FLEX_SHRINK, flexShrinkValue);
        }

        final String flexBasis = cssProps.get(CommonCssConstants.FLEX_BASIS);
        if (flexBasis == null || CommonCssConstants.AUTO.equals(flexBasis)) {
            // TODO DEVSIX-5003 use height as the main size if flex-direction: column.
            // we use main size property as a flex-basis value (when flex-basis: auto) in
            // corresponding with documentation https://www.w3.org/TR/css-flexbox-1/#valdef-flex-flex-basis
            final String flexElementWidth = cssProps.get(CommonCssConstants.WIDTH);
            if (flexElementWidth != null) {
                final float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
                final float rem = context.getCssContext().getRootFontSize();
                final UnitValue flexElementWidthAbsoluteLength = CssDimensionParsingUtils
                        .parseLengthValueToPt(flexElementWidth, em, rem);
                element.setProperty(Property.FLEX_BASIS, flexElementWidthAbsoluteLength);
            }
        } else if (!CommonCssConstants.CONTENT.equals(flexBasis)) {
            final float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
            final float rem = context.getCssContext().getRootFontSize();
            final UnitValue flexBasisAbsoluteLength = CssDimensionParsingUtils
                    .parseLengthValueToPt(flexBasis, em, rem);
            element.setProperty(Property.FLEX_BASIS, flexBasisAbsoluteLength);
        } else {
            // The case when we don't set the flex-basis property should be identified
            // as flex-basis: content
            LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET,
                    CommonCssConstants.FLEX_BASIS, CommonCssConstants.CONTENT));
        }
    }

    /**
     * Applies properties to a flex container.
     *
     * @param cssProps the CSS properties
     * @param element  the element
     */
    public static void applyFlexContainerProperties(Map<String, String> cssProps, IPropertyContainer element) {
        logWarningIfThereAreNotSupportedPropertyValues(createSupportedFlexContainerPropertiesAndValuesMap(), cssProps);
        applyAlignItems(cssProps, element);
        applyJustifyContent(cssProps, element);
    }

    private static void applyAlignItems(Map<String, String> cssProps, IPropertyContainer element) {
        final String alignItemsString = cssProps.get(CommonCssConstants.ALIGN_ITEMS);
        if (alignItemsString != null) {
            AlignmentPropertyValue alignItems;
            switch (alignItemsString) {
                case CommonCssConstants.NORMAL:
                    alignItems = AlignmentPropertyValue.NORMAL;
                    break;
                case CommonCssConstants.START:
                    alignItems = AlignmentPropertyValue.START;
                    break;
                case CommonCssConstants.END:
                    alignItems = AlignmentPropertyValue.END;
                    break;
                case CommonCssConstants.FLEX_START:
                    alignItems = AlignmentPropertyValue.FLEX_START;
                    break;
                case CommonCssConstants.FLEX_END:
                    alignItems = AlignmentPropertyValue.FLEX_END;
                    break;
                case CommonCssConstants.CENTER:
                    alignItems = AlignmentPropertyValue.CENTER;
                    break;
                case CommonCssConstants.SELF_START:
                    alignItems = AlignmentPropertyValue.SELF_START;
                    break;
                case CommonCssConstants.SELF_END:
                    alignItems = AlignmentPropertyValue.SELF_END;
                    break;
                case CommonCssConstants.STRETCH:
                    alignItems = AlignmentPropertyValue.STRETCH;
                    break;
                default:
                    LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET,
                            CommonCssConstants.ALIGN_ITEMS, alignItemsString));
                    alignItems = AlignmentPropertyValue.STRETCH;
                    break;
            }
            element.setProperty(Property.ALIGN_ITEMS, alignItems);
        }
    }

    private static void applyJustifyContent(Map<String, String> cssProps, IPropertyContainer element) {
        final String justifyContentString = cssProps.get(CommonCssConstants.JUSTIFY_CONTENT);
        if (justifyContentString != null) {
            JustifyContent justifyContent;
            switch (justifyContentString) {
                case CommonCssConstants.NORMAL:
                    justifyContent = JustifyContent.NORMAL;
                    break;
                case CommonCssConstants.START:
                    justifyContent = JustifyContent.START;
                    break;
                case CommonCssConstants.END:
                    justifyContent = JustifyContent.END;
                    break;
                case CommonCssConstants.FLEX_END:
                    justifyContent = JustifyContent.FLEX_END;
                    break;
                case CommonCssConstants.SELF_START:
                    justifyContent = JustifyContent.SELF_START;
                    break;
                case CommonCssConstants.SELF_END:
                    justifyContent = JustifyContent.SELF_END;
                    break;
                case CommonCssConstants.LEFT:
                    justifyContent = JustifyContent.LEFT;
                    break;
                case CommonCssConstants.RIGHT:
                    justifyContent = JustifyContent.RIGHT;
                    break;
                case CommonCssConstants.CENTER:
                    justifyContent = JustifyContent.CENTER;
                    break;
                case CommonCssConstants.STRETCH:
                    justifyContent = JustifyContent.STRETCH;
                    break;
                case CommonCssConstants.FLEX_START:
                    justifyContent = JustifyContent.FLEX_START;
                    break;
                default:
                    LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET,
                        CommonCssConstants.JUSTIFY_CONTENT, justifyContentString));
                    justifyContent = JustifyContent.FLEX_START;
                    break;
            }
            element.setProperty(Property.JUSTIFY_CONTENT, justifyContent);
        }
    }

    private static void logWarningIfThereAreNotSupportedPropertyValues(Map<String, Set<String>> supportedPairs,
                                                                       Map<String, String> cssProps) {
        for (Map.Entry<String, Set<String>> entry : supportedPairs.entrySet()) {
            String supportedPair = entry.getKey();
            Set<String> supportedValues = entry.getValue();
            String propertyValue = cssProps.get(supportedPair);
            if (propertyValue != null && !supportedValues.contains(propertyValue)) {
                LOGGER.warn(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, supportedPair, propertyValue));
            }
        }
    }

    private static Map<String, Set<String>> createSupportedFlexItemPropertiesAndValuesMap() {
        final Map<String, Set<String>> supportedPairs = new HashMap<>();

        final Set<String> supportedAlignSelfValues = new HashSet<>();
        supportedAlignSelfValues.add(CommonCssConstants.AUTO);

        supportedPairs.put(CommonCssConstants.ALIGN_SELF, supportedAlignSelfValues);

        final Set<String> supportedOrderValues = new HashSet<>();

        supportedPairs.put(CommonCssConstants.ORDER, supportedOrderValues);

        return supportedPairs;
    }

    private static Map<String, Set<String>> createSupportedFlexContainerPropertiesAndValuesMap() {
        final Map<String, Set<String>> supportedPairs = new HashMap<>();

        final Set<String> supportedFlexDirectionValues = new HashSet<>();
        supportedFlexDirectionValues.add(CommonCssConstants.ROW);

        supportedPairs.put(CommonCssConstants.FLEX_DIRECTION, supportedFlexDirectionValues);

        final Set<String> supportedFlexWrapValues = new HashSet<>();
        supportedFlexWrapValues.add(CommonCssConstants.NOWRAP);

        supportedPairs.put(CommonCssConstants.FLEX_WRAP, supportedFlexWrapValues);

        final Set<String> supportedAlignContentValues = new HashSet<>();
        supportedAlignContentValues.add(CommonCssConstants.STRETCH);
        supportedAlignContentValues.add(CommonCssConstants.NORMAL);

        supportedPairs.put(CommonCssConstants.ALIGN_CONTENT, supportedAlignContentValues);

        final Set<String> supportedRowGapValues = new HashSet<>();
        supportedRowGapValues.add(CommonCssConstants.NORMAL);

        supportedPairs.put(CommonCssConstants.ROW_GAP, supportedRowGapValues);

        final Set<String> supportedColumnGapValues = new HashSet<>();
        supportedColumnGapValues.add(CommonCssConstants.NORMAL);

        supportedPairs.put(CommonCssConstants.COLUMN_GAP, supportedColumnGapValues);

        return supportedPairs;
    }
}
